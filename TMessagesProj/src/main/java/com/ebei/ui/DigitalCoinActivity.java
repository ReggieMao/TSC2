package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebei.library.BaseActivity;
import com.ebei.library.DialogUtil;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.ToastUtil;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/11/22.
 * 数字期权充提币页面
 */

public class DigitalCoinActivity extends BaseActivity {

    private static final String TAG = "DigitalCoinActivity";
    public static String TRADE_TYPE = "trade_type";
    public static String TSC_BALANCE = "tsc_balance";
    public static String FOTA_BALANCE = "fota_balance";
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_all)
    TextView mTvAll;
    @Bind(R.id.tv_money)
    TextView mTvMoney;
    @Bind(R.id.tv_sure)
    TextView mTvSure;
    @Bind(R.id.et_number)
    EditText mEtNumber;
    @Bind(R.id.tv_can_pay)
    TextView mTvCanPay;
    @Bind(R.id.pb)
    ProgressBar mPb;
    private int mTradeType;
    private String beforeCount;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_digital_coin;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        mTradeType = getIntent().getIntExtra(TRADE_TYPE, 0);
        if (mTradeType == 1) {
            mTvTitle.setText(getString(R.string.coin_recharge));
            mTvAll.setText(getString(R.string.all_in));
            mTvMoney.setText(getString(R.string.in_money));
            mTvSure.setText(getString(R.string.in));
            beforeCount = getIntent().getStringExtra(TSC_BALANCE);
        } else if (mTradeType == 2) {
            mTvTitle.setText(getString(R.string.coin_withdraw));
            mTvAll.setText(getString(R.string.all_out));
            mTvMoney.setText(getString(R.string.out_money));
            mTvSure.setText(getString(R.string.out));
            beforeCount = getIntent().getStringExtra(FOTA_BALANCE);
        }
        mTvCanPay.setText(beforeCount + " TSC");
        Util.setPoint(mEtNumber);
        // 利用RxJava防抖动防止多次点击确认按钮
        RxView.clicks(mTvSure).throttleFirst(2, TimeUnit.SECONDS);
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.FOTA_TRANSFER, new Action1() {
            @Override
            public void call(Object o) {
                mPb.setVisibility(View.GONE);
                if (mTradeType == 1) {
                    ToastUtil.toast(DigitalCoinActivity.this, getString(R.string.coin_recharge_success));
                } else {
                    ToastUtil.toast(DigitalCoinActivity.this, getString(R.string.coin_withdraw_success));
                }
                finish();

            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_all, R.id.et_number, R.id.tv_sure})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_all:
                mEtNumber.setText(beforeCount);
                break;
            case R.id.et_number:
                mEtNumber.setCursorVisible(true);
                break;
            case R.id.tv_sure:
                if (Util.isEmpty(mEtNumber.getText().toString()) ||
                        beforeCount.equals("0") || Double.parseDouble(mEtNumber.getText().toString()) > Double.parseDouble(beforeCount) || Double.parseDouble(mEtNumber.getText().toString()) == 0) {
                    ToastUtil.toast(this, getString(R.string.toast_count_error));
                    return;
                }
                DialogUtil.transactionDialog(this, new DialogUtil.OnResultListener2() {
                    @Override
                    public void onOk(String... params) {
                        mPb.setVisibility(View.VISIBLE);
                        String sign = UserPreference.getString(UserPreference.SECRET, "") + "payPassword=" + Util.encrypt(params[0]) + "&submitTime=" +Util.getNowTime() +
                                "&transAmount=" + mEtNumber.getText().toString() + "&transType=" + mTradeType + UserPreference.getString(UserPreference.SECRET, "");
                        sign = Util.encrypt(sign);
                        TSCApi.getInstance().fotaTransfer(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign, mEtNumber.getText().toString(), mTradeType, Util.encrypt(params[0]));
                    }

                    @Override
                    public void onForget() {
                        startActivity(new Intent(DigitalCoinActivity.this, UpdatePayPwdActivity.class));
                    }
                });
                break;
        }
    }

}
