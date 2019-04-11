package com.ebei.ui;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebei.library.ActivityUtil;
import com.ebei.library.BaseActivity;
import com.ebei.library.PasswordInputEdt;
import com.ebei.library.ToastUtil;
import com.ebei.library.Util;
import com.ebei.tsc.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/9/3.
 * 交易密码页面1
 */

public class PayPwd1Activity extends BaseActivity {

    private static final String TAG = "PayPwd1Activity";
    public static String MOBILE = "mobile";
    public static String WHERE = "where";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_next)
    TextView mTvNext;
    @Bind(R.id.et_pwd)
    PasswordInputEdt mEtPwd;
    private String mPayPwd = "";

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_pay_pwd1;
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
                    mTvNext.setBackground(getResources().getDrawable(R.drawable.bg_round_text_blue));
                } else
                    mTvNext.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray));
            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_next})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_next:
                if (mPayPwd.length() != 6) {
                    ToastUtil.toast(this, getString(R.string.toast_pay_pwd));
                    return;
                }
                Intent intent = new Intent(this, PayPwd2Activity.class);
                intent.putExtra(PayPwd2Activity.MOBILE, getIntent().getStringExtra(MOBILE));
                intent.putExtra(PayPwd2Activity.WHERE, getIntent().getIntExtra(WHERE, 0));
                intent.putExtra(PayPwd2Activity.PAY_PWD, mPayPwd);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            mPayPwd = "";
            mTvNext.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray));
        }
        return super.onKeyDown(keyCode, event);
    }

}
