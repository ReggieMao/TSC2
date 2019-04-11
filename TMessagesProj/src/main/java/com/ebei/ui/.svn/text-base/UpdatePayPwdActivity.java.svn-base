package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebei.library.ActivityUtil;
import com.ebei.library.BaseActivity;
import com.ebei.library.Constants;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.ToastUtil;
import com.ebei.library.Util;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/9/3.
 * 修改交易密码页面
 */

public class UpdatePayPwdActivity extends BaseActivity {

    private static final String TAG = "UpdatePayPwdActivity";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_next)
    TextView mTvNext;
    @Bind(R.id.ll_main)
    LinearLayout mLlMain;
    @Bind(R.id.et_mobile)
    EditText mEtMobile;
    @Bind(R.id.et_code)
    EditText mEtCode;
    @Bind(R.id.tv_send)
    TextView mTvSend;
    private boolean mobileYes = false;
    private boolean codeYes = false;
    private TimeCount time;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_update_pay_pwd;
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
        time = new TimeCount(60000, 1000);
        mEtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 11) {
                    mobileYes = true;
                    mTvSend.setBackground(getResources().getDrawable(R.drawable.bg_round_text_blue));
                } else {
                    mobileYes = false;
                    mTvSend.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray));
                }
                canLogin(mobileYes && codeYes);
            }
        });
        mEtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                codeYes = editable.length() == 4;
                canLogin(mobileYes && codeYes);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.GET_AUTH_CODE, new Action1() {
            @Override
            public void call(Object o) {
                ToastUtil.toast(UpdatePayPwdActivity.this, getString(R.string.toast_code_success));
                time.start();
            }
        });

        rxManage.on(Event.CHECK_VERIFY_CODE, new Action1<String>() {
            @Override
            public void call(String o) {
                Intent intent = new Intent(UpdatePayPwdActivity.this, PayPwd3Activity.class);
                intent.putExtra(PayPwd3Activity.MOBILE, mEtMobile.getText().toString());
                intent.putExtra(PayPwd3Activity.CODE, mEtCode.getText().toString());
                startActivity(intent);
            }
        });
    }

    // 倒计时任务
    private class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTvSend.setClickable(false);
            mTvSend.setText(millisUntilFinished / 1000 + "s");
            mTvSend.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray));
        }

        @Override
        public void onFinish() {
            mTvSend.setText(R.string.send);
            mTvSend.setClickable(true);
            mTvSend.setBackground(getResources().getDrawable(R.drawable.bg_round_text_blue));
        }
    }

    private void canLogin(boolean can) {
        if (can)
            mTvNext.setBackground(getResources().getDrawable(R.drawable.bg_round_text_blue));
        else
            mTvNext.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray));
    }

    @OnClick({R.id.img_back, R.id.et_mobile, R.id.tv_send, R.id.tv_next})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.et_mobile:
                mEtMobile.setCursorVisible(true);
                break;
            case R.id.tv_send:
                if (mEtMobile.getText().toString().length() != 11) {
                    ToastUtil.toast(this, getString(R.string.toast_mobile));
                    return;
                }
                String sign = Constants.SALT_CIPHER + "mobile=" + mEtMobile.getText().toString() + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
                sign = Util.encrypt(sign);
                TSCApi.getInstance().getAuthCode(mEtMobile.getText().toString(), sign, Util.getNowTime());
                break;
            case R.id.tv_next:
                if (mEtMobile.getText().toString().length() != 11) {
                    ToastUtil.toast(this, getString(R.string.toast_mobile));
                    return;
                }
                if (mEtCode.getText().toString().length() != 4) {
                    ToastUtil.toast(this, getString(R.string.toast_code));
                    return;
                }
                String sign1 = Constants.SALT_CIPHER + "loginAccount=" + mEtMobile.getText().toString() + "&submitTime=" + Util.getNowTime() + "&verifyCode=" + mEtCode.getText().toString() + Constants.SALT_CIPHER;
                sign1 = Util.encrypt(sign1);
                TSCApi.getInstance().checkVerifyCode(mEtMobile.getText().toString(), mEtCode.getText().toString(), sign1, Util.getNowTime());
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("其他");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("其他");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time.cancel();
    }

}
