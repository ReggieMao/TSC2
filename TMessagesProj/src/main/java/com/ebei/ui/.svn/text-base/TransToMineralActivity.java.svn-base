package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebei.library.BaseActivity;
import com.ebei.library.DialogUtil;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.ToastUtil;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.Book;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/11/22.
 * 转去挖矿页面
 */

public class TransToMineralActivity extends BaseActivity {

    private static final String TAG = "TransToMineralActivity";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.view)
    ImageView mView;
    @Bind(R.id.tv_sure)
    TextView mTvSure;
    @Bind(R.id.et_count)
    EditText mEtCount;
    @Bind(R.id.tv_can_pay)
    TextView mTvCanPay;
    private double beforeCount;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_trans_to_mineral;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        Util.setPoint(mEtCount);
        String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "coinType=" + "2" + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign1 = Util.encrypt(sign1);
        TSCApi.getInstance().getUserBookNew(2, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign1, "2");
        mEtCount.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                keyboardIsShow();
            }
        });
        // 利用RxJava防抖动防止多次点击确认按钮
        RxView.clicks(mTvSure).throttleFirst(2, TimeUnit.SECONDS);
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.GET_COIN_INFO2, new Action1<Book>() {
            @Override
            public void call(Book o) {
                beforeCount = o.getBookBalance();
                mTvCanPay.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(beforeCount, 6))));
            }
        });

        rxManage.on(Event.TRANSFER_TO_ORE, new Action1() {
            @Override
            public void call(Object o) {
                ToastUtil.toast(TransToMineralActivity.this, getString(R.string.toast_miner_in_success));
                mEtCount.setText("");
                String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "coinType=" + "2" + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                sign1 = Util.encrypt(sign1);
                TSCApi.getInstance().getUserBookNew(2, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign1, "2");
            }
        });

        rxManage.on(Event.PAY_PWD_ERROR, new Action1<String>() {
            @Override
            public void call(String o) {
                ToastUtil.toast(TransToMineralActivity.this, o);
            }
        });
    }

    private void keyboardIsShow() {
        final Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        final int screenHeight = getWindow().getDecorView().getRootView().getHeight();
        final int heightDifference = screenHeight - rect.bottom;
        boolean visible = heightDifference > screenHeight / 3;
        if (!visible) {
            mView.setVisibility(View.VISIBLE);
        } else {
            mView.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_back, R.id.tv_all, R.id.tv_sure, R.id.et_count})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_all:
                mEtCount.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(beforeCount, 6))));
                break;
            case R.id.tv_sure:
                if (Util.isEmpty(mEtCount.getText().toString()) || beforeCount < 0 ||
                        beforeCount == 0 || Double.parseDouble(mEtCount.getText().toString()) > beforeCount || Double.parseDouble(mEtCount.getText().toString()) == 0) {
                    ToastUtil.toast(this, getString(R.string.toast_count_error));
                    return;
                }
                DialogUtil.transactionDialog(this, new DialogUtil.OnResultListener2() {
                    @Override
                    public void onOk(String... params) {
                        String sign = UserPreference.getString(UserPreference.SECRET, "") + "operateType=1&payPassword=" + Util.encrypt(params[0]) + "&submitTime=" +
                                Util.getNowTime() + "&transferAmount=" + mEtCount.getText().toString() + UserPreference.getString(UserPreference.SECRET, "");
                        sign = Util.encrypt(sign);
                        TSCApi.getInstance().transferToOre(sign, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), mEtCount.getText().toString(),
                                Util.encrypt(params[0]), 1);
                    }

                    @Override
                    public void onForget() {
                        startActivity(new Intent(TransToMineralActivity.this, UpdatePayPwdActivity.class));
                    }
                });
                break;
            case R.id.et_count:
                mEtCount.setCursorVisible(true);
                break;
        }
    }

}
