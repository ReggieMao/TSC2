package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebei.library.ActivityUtil;
import com.ebei.library.BaseActivity;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.ToastUtil;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.FotaLogin;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.fota.option.OptionManager;
import com.fota.option.OptionSdkActivity;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/11/22.
 * 数字期权页面
 */

public class DigitalOptionActivity extends BaseActivity {

    private static final String TAG = "DigitalOptionActivity";
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.img_yes)
    ImageView mImgYes;
    @Bind(R.id.tv_sure)
    TextView mTvSure;
    @Bind(R.id.pb)
    ProgressBar mPb;
    private boolean isYesShow = false;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_digital_option;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        ActivityUtil.add(this);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        TSCApi.getInstance().checkFotaUserExit(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign);
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.CHECK_FOTA_USER_EXIT, new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o.equals("用户没有fota账户")) {
                    mImgYes.setVisibility(View.GONE);
                    mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray3));
                    isYesShow = false;
                } else {
                    mImgYes.setVisibility(View.VISIBLE);
                    mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_blue3));
                    isYesShow = true;
                }
            }
        });

        rxManage.on(Event.FOTA_AUTH_LOGIN, new Action1<FotaLogin>() {
            @Override
            public void call(FotaLogin o) {
                UserPreference.putString(UserPreference.FOTA_PASSWORD, o.getPassword());
                OptionManager.setUserIdAndToken(o.getUid(), o.getToken());
                startActivity(new Intent(DigitalOptionActivity.this, OptionSdkActivity.class));
            }
        });
    }

    @OnClick({R.id.img_back, R.id.ll_read, R.id.tv_read, R.id.tv_sure})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                ActivityUtil.finishAll();
                break;
            case R.id.ll_read:
                if (!isYesShow) {
                    mImgYes.setVisibility(View.VISIBLE);
                    mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_blue3));
                    isYesShow = true;
                } else {
                    mImgYes.setVisibility(View.GONE);
                    mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray3));
                    isYesShow = false;
                }
                break;
            case R.id.tv_read:
                startActivity(new Intent(this, DigitalInfoActivity.class));
                break;
            case R.id.tv_sure:
                if (!isYesShow) {
                    ToastUtil.toast(this, getString(R.string.read_agree2));
                    return;
                }
                ToastUtil.toast(this, getString(R.string.horizontal_phone));
                mPb.setVisibility(View.VISIBLE);
                String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                sign = Util.encrypt(sign);
                TSCApi.getInstance().fotaLogin(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPb.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityUtil.finishAll();
        }
        return false;
    }

}