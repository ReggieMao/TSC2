package com.ebei.ui;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebei.library.ActivityUtil;
import com.ebei.library.BaseActivity;
import com.ebei.library.Constants;
import com.ebei.library.Event;
import com.ebei.library.PasswordInputEdt;
import com.ebei.library.TSCApi;
import com.ebei.library.ToastUtil;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.Login;
import com.ebei.pojo.Register;
import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.R;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.UserObject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/9/3.
 * 交易密码页面2
 */

public class PayPwd2Activity extends BaseActivity {

    private static final String TAG = "PayPwd2Activity";
    public static String MOBILE = "mobile";
    public static String WHERE = "where";
    public static String PAY_PWD = "pay_pwd";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_confirm)
    TextView mTvConfirm;
    @Bind(R.id.et_pwd)
    PasswordInputEdt mEtPwd;
    private String mPayPwd = "";

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_pay_pwd2;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        ActivityUtil.add(this);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        mEtPwd.setOnInputOverListener(new PasswordInputEdt.onInputOverListener() {
            @Override
            public void onInputOver(String text) {
                if (text.length() == 6) {
                    mPayPwd = text;
                    mTvConfirm.setBackground(getResources().getDrawable(R.drawable.bg_round_text_blue));
                } else
                    mTvConfirm.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray));
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.REGISTER_NEW, new Action1<Register>() {
            @Override
            public void call(Register o) {
                String sign = Constants.SALT_CIPHER + "loginAccount=" + getIntent().getStringExtra(MOBILE) + "&password=" + Util.encrypt(getIntent().getStringExtra(PAY_PWD)) + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
                sign = Util.encrypt(sign);
                TSCApi.getInstance().userLogin(getIntent().getIntExtra(WHERE, 0), getIntent().getStringExtra(MOBILE), Util.encrypt(getIntent().getStringExtra(PAY_PWD)), sign, Util.getNowTime());
            }
        });

        rxManage.on(Event.LOGIN_PWD2, new Action1<Login>() {
            @Override
            public void call(Login o) {
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, o.getLoginAccount());
                String signA = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(PayPwd2Activity.this) + "&submitTime=" +
                        Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signA = Util.encrypt(signA);
                TSCApi.getInstance().saveId(signA, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(PayPwd2Activity.this));
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
                startActivity(new Intent(PayPwd2Activity.this, WalletActivity.class));
            }
        });

        rxManage.on(Event.LOGIN_PWD3, new Action1<Login>() {
            @Override
            public void call(Login o) {
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, o.getLoginAccount());
                String signA = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(PayPwd2Activity.this) + "&submitTime=" +
                        Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signA = Util.encrypt(signA);
                TSCApi.getInstance().saveId(signA, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(PayPwd2Activity.this));
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
                startActivity(new Intent(PayPwd2Activity.this, TSCMineralActivity.class));
            }
        });

        rxManage.on(Event.LOGIN_PWD4, new Action1<Login>() {
            @Override
            public void call(Login o) {
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, o.getLoginAccount());
                String signA = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(PayPwd2Activity.this) + "&submitTime=" +
                        Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signA = Util.encrypt(signA);
                TSCApi.getInstance().saveId(signA, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(PayPwd2Activity.this));
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
                ToastUtil.toast(PayPwd2Activity.this, getString(R.string.toast_pay_pwd_success1));
                ActivityUtil.finishAll();
            }
        });

        rxManage.on(Event.LOGIN_PWD5, new Action1<Login>() {
            @Override
            public void call(Login o) {
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, o.getLoginAccount());
                String signA = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(PayPwd2Activity.this) + "&submitTime=" +
                        Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signA = Util.encrypt(signA);
                TSCApi.getInstance().saveId(signA, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(PayPwd2Activity.this));
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
                ActivityUtil.finishAll();
            }
        });

        rxManage.on(Event.LOGIN_PWD7, new Action1<Login>() {
            @Override
            public void call(Login o) {
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, o.getLoginAccount());
                String signA = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(PayPwd2Activity.this) + "&submitTime=" +
                        Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signA = Util.encrypt(signA);
                TSCApi.getInstance().saveId(signA, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(PayPwd2Activity.this));
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
                ActivityUtil.finishAll();
            }
        });

        rxManage.on(Event.LOGIN_PWD10, new Action1<Login>() {
            @Override
            public void call(Login o) {
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, o.getLoginAccount());
                String signA = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(PayPwd2Activity.this) + "&submitTime=" +
                        Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signA = Util.encrypt(signA);
                TSCApi.getInstance().saveId(signA, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(PayPwd2Activity.this));
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
                ActivityUtil.finishAll();
            }
        });

        rxManage.on(Event.LOGIN_PWD12, new Action1<Login>() {
            @Override
            public void call(Login o) {
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, o.getLoginAccount());
                String signA = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(PayPwd2Activity.this) + "&submitTime=" +
                        Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signA = Util.encrypt(signA);
                TSCApi.getInstance().saveId(signA, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(PayPwd2Activity.this));
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
                startActivity(new Intent(PayPwd2Activity.this, DigitalOptionActivity.class));
            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_confirm})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_confirm:
                if (mPayPwd.length() != 6) {
                    ToastUtil.toast(this, getString(R.string.toast_pay_pwd));
                    return;
                }
                if (!mPayPwd.equals(getIntent().getStringExtra(PAY_PWD))) {
                    ToastUtil.toast(this, getString(R.string.toast_pay_pwd1));
                    return;
                }
                String sign = Constants.SALT_CIPHER + "confirmPayPassWord=" + mPayPwd + "&loginAccount=" + getIntent().getStringExtra(MOBILE) + "&payPassWord=" + getIntent().getStringExtra(PAY_PWD) + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
                sign = Util.encrypt(sign);
                TSCApi.getInstance().newRegister(getIntent().getStringExtra(MOBILE), getIntent().getStringExtra(PAY_PWD), mPayPwd, sign, Util.getNowTime());
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            mPayPwd = "";
            mTvConfirm.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray));
        }
        return super.onKeyDown(keyCode, event);
    }

}
