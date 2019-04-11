package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebei.library.BaseActivity;
import com.ebei.library.Constants;
import com.ebei.library.DialogUtil;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.Login;
import com.ebei.pojo.Market;
import com.ebei.pojo.TValue1;
import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.R;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.UserObject;
import com.ebei.ui.ActionBar.Theme;
import com.ebei.ui.Components.AvatarDrawable;
import com.ebei.ui.Components.BackupImageView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/11/1.
 * 我的页面
 */

public class MineActivity extends BaseActivity {

    private static final String TAG = "MineActivity";
    public static String FROM_LAUNCH = "from_launch";
    public static String NAME = "name";
    public static String MOBILE = "mobile";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.img_avatar)
    BackupImageView mImgAvatar;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_mobile)
    TextView mTvMobil;
    @Bind(R.id.tv_t)
    TextView mTvTValue;
    @Bind(R.id.img_message)
    ImageView mImgCircle;
    private String mobile;
    @Bind(R.id.tv_version)
    TextView mTvVersion;
    @Bind(R.id.view_qiquan)
    View mViewQiquan;
    @Bind(R.id.rl9)
    RelativeLayout mRl9;
    private boolean hasRegWallet = false;
    private boolean hasNoticeUnread = false;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_mine;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        if (getIntent().getIntExtra(FROM_LAUNCH, 0) == 1)
            startActivityForResult(new Intent(this, ChatManagerActivity.class), 1001);
        mTvName.setText(getIntent().getStringExtra(NAME));
        mobile = getIntent().getStringExtra(MOBILE);
        mobile = mobile.substring(2, mobile.length());
        mTvMobil.setText(mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length()));
        TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId());
        TLRPC.FileLocation photo = null;
        TLRPC.FileLocation photoBig = null;
        if (user != null && user.photo != null) {
            photo = user.photo.photo_small;
            photoBig = user.photo.photo_big;
        }
        AvatarDrawable avatarDrawable = new AvatarDrawable(user, true);
        avatarDrawable.setColor(Theme.getColor(Theme.key_avatar_backgroundInProfileBlue));
        mImgAvatar.setRoundRadius(AndroidUtilities.dp(200));
        if (mImgAvatar != null) {
            mImgAvatar.setImage(photo, "50_50", avatarDrawable);
            mImgAvatar.getImageReceiver().setVisible(!PhotoViewer.isShowingImage(photoBig), false);
        }
        mTvVersion.setText(getString(R.string.now_version1) + Constants.APP_VERSION);
        String sign = Constants.SALT_CIPHER + "loginAccount=" + mobile + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
        sign = Util.encrypt(sign);
        TSCApi.getInstance().checkLoginAccount(1, mobile, sign, Util.getNowTime());
        String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign1 = Util.encrypt(sign1);
        TSCApi.getInstance().findNewsList(0, Util.getNowTime(), sign1, UserPreference.getString(UserPreference.SESSION_ID, ""));
        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
            Integer uid = UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId();
            TLRPC.User userTele = MessagesController.getInstance(UserConfig.selectedAccount).getUser(uid);
            String name = UserObject.getUserName(userTele);
            String signTele = UserPreference.getString(UserPreference.SECRET, "") + "nickName=" + name + "&openId=" + uid + "&portraitImgUrl=&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
            signTele = Util.encrypt(signTele);
            TSCApi.getInstance().addTeleInfo(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), signTele, uid + "", name, "");
        }
        if (UserPreference.getInt(UserPreference.IS_MX_USER, 0) == 1) {
            mViewQiquan.setVisibility(View.GONE);
            mRl9.setVisibility(View.GONE);
        } else {
            mViewQiquan.setVisibility(View.VISIBLE);
            mRl9.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.SYSTEM_UNREAD, new Action1() {
            @Override
            public void call(Object o) {
                mImgCircle.setVisibility(View.VISIBLE);
            }
        });

        rxManage.on(Event.RE_LOGIN, new Action1() {
            @Override
            public void call(Object o) {
                UserPreference.putString(UserPreference.SESSION_ID, "");
                UserPreference.putString(UserPreference.SECRET, "");
                UserPreference.putString(UserPreference.ACCOUNT, "");
                UserPreference.putInt(UserPreference.SEND_MESSAGE_COUNT, 0);
                mTvTValue.setText("_____");
            }
        });

        rxManage.on(Event.CHECK_ACCOUNT1, new Action1<String>() {
            @Override
            public void call(String o) {
                if (o.equals("用户名已存在")) {
                    hasRegWallet = true;
                    if (Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                        mTvTValue.setText("_____");
                    } else {
                        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                        sign = Util.encrypt(sign);
                        TSCApi.getInstance().findMonthRecord(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign);
                    }
                } else if (o.equals("用户名不存在")) {
                    hasRegWallet = false;
                    mTvTValue.setText("_____");
                }
            }
        });

        rxManage.on(Event.FIND_NEWS_LIST, new Action1<Market>() {
            @Override
            public void call(Market o) {
                hasNoticeUnread = o.getUnreadNum() != 0;
                if (hasNoticeUnread)
                    mImgCircle.setVisibility(View.VISIBLE);
                else
                    mImgCircle.setVisibility(View.GONE);
            }
        });

        rxManage.on(Event.FIND_MONTH_RECORD, new Action1<List<TValue1>>() {
            @Override
            public void call(List<TValue1> o) {
                if (o.size() == 0)
                    mTvTValue.setText("0");
                else {
                    int count = 0;
                    for (int i = 0; i < o.size(); i ++) {
                        if (o.get(i).getBelongMonth().equals(Util.getNowTime1()))
                            count = count + o.get(i).getTValue();
                    }
                    mTvTValue.setText(count + "");
                }
            }
        });

        rxManage.on(Event.LOGIN_PWD, new Action1<Login>() {
            @Override
            public void call(Login o) {
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, o.getLoginAccount());
                String signA = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(MineActivity.this) + "&submitTime=" +
                        Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signA = Util.encrypt(signA);
                TSCApi.getInstance().saveId(signA, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(MineActivity.this));
                String sign = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=0&submitTime=" + Util.getNowTime() + "&type=1" + UserPreference.getString(UserPreference.SECRET, "");
                sign = Util.encrypt(sign);
                TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign, 1, 0);
                switch (UserPreference.getInt(UserPreference.SEND_MESSAGE_COUNT, 0)) {
                    case 5:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign9 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=5&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign9 = Util.encrypt(sign9);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign9, 2, 5);
                        }
                        break;
                    case 10:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=10&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign1 = Util.encrypt(sign1);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign1, 2, 10);
                        }
                        break;
                    case 15:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign2 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=15&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign2 = Util.encrypt(sign2);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign2, 2, 15);
                        }
                        break;
                    case 20:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign3 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=20&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign3 = Util.encrypt(sign3);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign3, 2, 20);
                        }
                        break;
                    case 25:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign4 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=25&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign4 = Util.encrypt(sign4);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign4, 2, 25);
                        }
                        break;
                    case 30:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign5 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=30&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign5 = Util.encrypt(sign5);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign5, 2, 30);
                        }
                        break;
                    case 40:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign6 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=40&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign6 = Util.encrypt(sign6);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign6, 2, 40);
                        }
                        break;
                    case 50:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign7 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=50&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign7 = Util.encrypt(sign7);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign7, 2, 50);
                        }
                        break;
                    case 60:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign8 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=60&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign8 = Util.encrypt(sign8);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign8, 2, 60);
                        }
                        break;
                }
                Integer uid = UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId();
                TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(uid);
                String name = UserObject.getUserName(user);
                String signTele = UserPreference.getString(UserPreference.SECRET, "") + "nickName=" + name + "&openId=" + uid + "&portraitImgUrl=&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signTele = Util.encrypt(signTele);
                TSCApi.getInstance().addTeleInfo(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), signTele, uid + "", name, "");
                // 进入钱包
                startActivity(new Intent(MineActivity.this, WalletActivity.class));
            }
        });

        rxManage.on(Event.LOGIN_PWD1, new Action1<Login>() {
            @Override
            public void call(Login o) {
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, o.getLoginAccount());
                String signA = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(MineActivity.this) + "&submitTime=" +
                        Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signA = Util.encrypt(signA);
                TSCApi.getInstance().saveId(signA, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(MineActivity.this));
                String sign = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=0&submitTime=" + Util.getNowTime() + "&type=1" + UserPreference.getString(UserPreference.SECRET, "");
                sign = Util.encrypt(sign);
                TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign, 1, 0);
                switch (UserPreference.getInt(UserPreference.SEND_MESSAGE_COUNT, 0)) {
                    case 5:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign9 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=5&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign9 = Util.encrypt(sign9);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign9, 2, 5);
                        }
                        break;
                    case 10:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=10&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign1 = Util.encrypt(sign1);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign1, 2, 10);
                        }
                        break;
                    case 15:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign2 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=15&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign2 = Util.encrypt(sign2);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign2, 2, 15);
                        }
                        break;
                    case 20:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign3 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=20&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign3 = Util.encrypt(sign3);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign3, 2, 20);
                        }
                        break;
                    case 25:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign4 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=25&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign4 = Util.encrypt(sign4);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign4, 2, 25);
                        }
                        break;
                    case 30:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign5 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=30&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign5 = Util.encrypt(sign5);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign5, 2, 30);
                        }
                        break;
                    case 40:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign6 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=40&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign6 = Util.encrypt(sign6);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign6, 2, 40);
                        }
                        break;
                    case 50:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign7 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=50&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign7 = Util.encrypt(sign7);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign7, 2, 50);
                        }
                        break;
                    case 60:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign8 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=60&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign8 = Util.encrypt(sign8);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign8, 2, 60);
                        }
                        break;
                }
                Integer uid = UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId();
                TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(uid);
                String name = UserObject.getUserName(user);
                String signTele = UserPreference.getString(UserPreference.SECRET, "") + "nickName=" + name + "&openId=" + uid + "&portraitImgUrl=&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signTele = Util.encrypt(signTele);
                TSCApi.getInstance().addTeleInfo(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), signTele, uid + "", name, "");
                // 进入矿池
                startActivity(new Intent(MineActivity.this, TSCMineralActivity.class));
            }
        });

        rxManage.on(Event.LOGIN_PWD13, new Action1<Login>() {
            @Override
            public void call(Login o) {
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, o.getLoginAccount());
                String signA = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(MineActivity.this) + "&submitTime=" +
                        Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signA = Util.encrypt(signA);
                TSCApi.getInstance().saveId(signA, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(MineActivity.this));
                String sign = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=0&submitTime=" + Util.getNowTime() + "&type=1" + UserPreference.getString(UserPreference.SECRET, "");
                sign = Util.encrypt(sign);
                TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign, 1, 0);
                switch (UserPreference.getInt(UserPreference.SEND_MESSAGE_COUNT, 0)) {
                    case 5:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign9 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=5&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign9 = Util.encrypt(sign9);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign9, 2, 5);
                        }
                        break;
                    case 10:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=10&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign1 = Util.encrypt(sign1);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign1, 2, 10);
                        }
                        break;
                    case 15:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign2 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=15&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign2 = Util.encrypt(sign2);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign2, 2, 15);
                        }
                        break;
                    case 20:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign3 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=20&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign3 = Util.encrypt(sign3);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign3, 2, 20);
                        }
                        break;
                    case 25:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign4 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=25&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign4 = Util.encrypt(sign4);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign4, 2, 25);
                        }
                        break;
                    case 30:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign5 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=30&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign5 = Util.encrypt(sign5);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign5, 2, 30);
                        }
                        break;
                    case 40:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign6 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=40&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign6 = Util.encrypt(sign6);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign6, 2, 40);
                        }
                        break;
                    case 50:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign7 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=50&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign7 = Util.encrypt(sign7);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign7, 2, 50);
                        }
                        break;
                    case 60:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign8 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=60&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign8 = Util.encrypt(sign8);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign8, 2, 60);
                        }
                        break;
                }
                Integer uid = UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId();
                TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(uid);
                String name = UserObject.getUserName(user);
                String signTele = UserPreference.getString(UserPreference.SECRET, "") + "nickName=" + name + "&openId=" + uid + "&portraitImgUrl=&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signTele = Util.encrypt(signTele);
                TSCApi.getInstance().addTeleInfo(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), signTele, uid + "", name, "");
                // 进入期权
                startActivity(new Intent(MineActivity.this, DigitalOptionActivity.class));
            }
        });
    }

    @OnClick({R.id.rl9, R.id.rb_chats, R.id.rb_contacts, R.id.rb_found, R.id.img_t, R.id.ll_t, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.rl_info, R.id.rl6, R.id.rl7, R.id.rl8})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl9:
                if (!hasRegWallet) {
                    DialogUtil.setPayPwdDialog(this, new DialogUtil.OnResultListener0() {
                        @Override
                        public void onOK() {
                            Intent intent = new Intent(MineActivity.this, PayPwd1Activity.class);
                            intent.putExtra(PayPwd1Activity.MOBILE, mobile);
                            intent.putExtra(PayPwd1Activity.WHERE, 13);
                            startActivity(intent);
                        }
                    });
                } else {
                    if (Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                        DialogUtil.transactionDialog(this, new DialogUtil.OnResultListener2() {
                            @Override
                            public void onOk(String... params) {
                                String sign = Constants.SALT_CIPHER + "loginAccount=" + mobile + "&password=" + Util.encrypt(params[0]) + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
                                sign = Util.encrypt(sign);
                                TSCApi.getInstance().userLogin(14, mobile, Util.encrypt(params[0]), sign, Util.getNowTime());
                            }

                            @Override
                            public void onForget() {
                                startActivity(new Intent(MineActivity.this, UpdatePayPwdActivity.class));
                            }
                        });
                    } else {
                        // 进入期权
                        startActivity(new Intent(this, DigitalOptionActivity.class));
                    }
                }
                break;
            case R.id.rb_chats: // 聊天
                Intent intent = new Intent();
                intent.putExtra("result", 1);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.rb_contacts: // 通讯录
                Intent intent1 = new Intent();
                intent1.putExtra("result", 2);
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case R.id.rb_found: // 发现
                Intent intent2 = new Intent();
                intent2.putExtra("result", 3);
                setResult(RESULT_OK, intent2);
                finish();
                break;
            case R.id.img_t: // T值介绍
                startActivity(new Intent(this, TValueActivity.class));
                break;
            case R.id.ll_t: // T值明细
                if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                    startActivity(new Intent(this, TRecordActivity.class));
                }
                break;
            case R.id.rl1: // 钱包
                if (!hasRegWallet) {
                    DialogUtil.setPayPwdDialog(this, new DialogUtil.OnResultListener0() {
                        @Override
                        public void onOK() {
                            Intent intent = new Intent(MineActivity.this, PayPwd1Activity.class);
                            intent.putExtra(PayPwd1Activity.MOBILE, mobile);
                            intent.putExtra(PayPwd1Activity.WHERE, 3);
                            startActivity(intent);
                        }
                    });
                } else {
                    if (Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                        DialogUtil.transactionDialog(this, new DialogUtil.OnResultListener2() {
                            @Override
                            public void onOk(String... params) {
                                String sign = Constants.SALT_CIPHER + "loginAccount=" + mobile + "&password=" + Util.encrypt(params[0]) + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
                                sign = Util.encrypt(sign);
                                TSCApi.getInstance().userLogin(1, mobile, Util.encrypt(params[0]), sign, Util.getNowTime());
                            }

                            @Override
                            public void onForget() {
                                startActivity(new Intent(MineActivity.this, UpdatePayPwdActivity.class));
                            }
                        });
                    } else {
                        // 进入钱包
                        startActivity(new Intent(MineActivity.this, WalletActivity.class));
                    }
                }
                break;
            case R.id.rl2: // 矿池
                if (!hasRegWallet) {
                    DialogUtil.setPayPwdDialog(this, new DialogUtil.OnResultListener0() {
                        @Override
                        public void onOK() {
                            Intent intent = new Intent(MineActivity.this, PayPwd1Activity.class);
                            intent.putExtra(PayPwd1Activity.MOBILE, mobile);
                            intent.putExtra(PayPwd1Activity.WHERE, 4);
                            startActivity(intent);
                        }
                    });
                } else {
                    if (Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                        DialogUtil.transactionDialog(this, new DialogUtil.OnResultListener2() {
                            @Override
                            public void onOk(String... params) {
                                String sign = Constants.SALT_CIPHER + "loginAccount=" + mobile + "&password=" + Util.encrypt(params[0]) + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
                                sign = Util.encrypt(sign);
                                TSCApi.getInstance().userLogin(2, mobile, Util.encrypt(params[0]), sign, Util.getNowTime());
                            }

                            @Override
                            public void onForget() {
                                startActivity(new Intent(MineActivity.this, UpdatePayPwdActivity.class));
                            }
                        });
                    } else {
                        // 进入矿池
                        startActivity(new Intent(MineActivity.this, TSCMineralActivity.class));
                    }
                }
                break;
            case R.id.rl3: // 支付密码
                if (!hasRegWallet) {
                    Intent intent3 = new Intent(this, PayPwd1Activity.class);
                    intent3.putExtra(PayPwd1Activity.MOBILE, mobile);
                    intent3.putExtra(PayPwd1Activity.WHERE, 5);
                    startActivity(intent3);
                } else {
                    startActivity(new Intent(this, UpdatePayPwdActivity.class));
                }
                break;
            case R.id.rl4: // 聊天管理
                startActivityForResult(new Intent(this, ChatManagerActivity.class), 1001);
                break;
            case R.id.rl5: // 系统公告
                startActivity(new Intent(this, SystemNoticeActivity.class));
                break;
            case R.id.rl6: // 常见问题
                startActivity(new Intent(this, CommonQuestionActivity.class));
                break;
            case R.id.rl7: // 意见反馈
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.rl8: // 版本更新
                startActivity(new Intent(this, VersionActivity.class));
                break;
            case R.id.rl_info: // 个人中心
                Intent intent3 = new Intent();
                intent3.putExtra("result", 9);
                setResult(RESULT_OK, intent3);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            int result = data.getExtras().getInt("result");
            switch (result) {
                case 666:
                    Intent intent = new Intent();
                    intent.putExtra("result", 6);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case 777:
                    Intent intent1 = new Intent();
                    intent1.putExtra("result", 7);
                    setResult(RESULT_OK, intent1);
                    finish();
                    break;
                case 888:
                    Intent intent2 = new Intent();
                    intent2.putExtra("result", 8);
                    setResult(RESULT_OK, intent2);
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("我的");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String sign = Constants.SALT_CIPHER + "loginAccount=" + mobile + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
        sign = Util.encrypt(sign);
        TSCApi.getInstance().checkLoginAccount(1, mobile, sign, Util.getNowTime());
        String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign1 = Util.encrypt(sign1);
        TSCApi.getInstance().findNewsList(0, Util.getNowTime(), sign1, UserPreference.getString(UserPreference.SESSION_ID, ""));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            UserPreference.putInt(UserPreference.HAS_UPDATE_TIP, 0);
            Intent intent = new Intent();
            intent.putExtra("result", 5);
            setResult(RESULT_OK, intent);
            finish();
        }
        return false;
    }

}
