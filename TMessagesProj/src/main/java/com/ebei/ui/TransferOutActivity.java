package com.ebei.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
 * 转到对方账户页面
 */

public class TransferOutActivity extends BaseActivity {

    private static final String TAG = "TransferOutActivity";
    public static String COIN_NAME = "coin_name";
    public static String COIN_TYPE = "coin_type";
    private int REQUEST_CODE_SCAN = 1001;
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_now)
    TextView mTvNow;
    @Bind(R.id.tv_sure)
    TextView mTvSure;
    @Bind(R.id.et_address)
    EditText mEtAddress;
    @Bind(R.id.et_count)
    EditText mEtCount;
    @Bind(R.id.tv_can_pay)
    TextView mTvCanPay;
    @Bind(R.id.pb)
    ProgressBar mPb;
    private double beforeCount;
    private String mType;
    private double tscBalance = 0;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_transfer_out;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        mTvNow.setText(getString(R.string.now_remain).replace("xxx", getIntent().getStringExtra(COIN_NAME)));
        Util.setPoint(mEtCount);
        String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "coinType=" + getIntent().getStringExtra(COIN_TYPE) + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign1 = Util.encrypt(sign1);
        TSCApi.getInstance().getUserBookNew(1, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign1, getIntent().getStringExtra(COIN_TYPE));
        mType = getIntent().getStringExtra(COIN_TYPE);
        if (getIntent().getStringExtra(COIN_TYPE).equals("10")) {
            String sign2 = UserPreference.getString(UserPreference.SECRET, "") + "coinType=2" + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
            sign2 = Util.encrypt(sign2);
            TSCApi.getInstance().getUserBookNew(3, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign2, "2");
        }
        // 利用RxJava防抖动防止多次点击确认按钮
        RxView.clicks(mTvSure).throttleFirst(2, TimeUnit.SECONDS);
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.GET_COIN_INFO3, new Action1<Book>() {
            @Override
            public void call(Book o) {
                tscBalance = o.getBookBalance();
            }
        });

        rxManage.on(Event.GET_COIN_INFO1, new Action1<Book>() {
            @Override
            public void call(Book o) {
                beforeCount = o.getBookBalance();
                mTvCanPay.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(beforeCount, 6))));
            }
        });

        rxManage.on(Event.TRANSFER_TO_OTHERS, new Action1() {
            @Override
            public void call(Object o) {
                ToastUtil.toast(TransferOutActivity.this, getString(R.string.toast_transfer_success));
                mPb.setVisibility(View.GONE);
                mEtAddress.setText("");
                mEtCount.setText("");
                String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "coinType=" + getIntent().getStringExtra(COIN_TYPE) + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                sign1 = Util.encrypt(sign1);
                TSCApi.getInstance().getUserBookNew(1, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign1, getIntent().getStringExtra(COIN_TYPE));
                if (getIntent().getStringExtra(COIN_TYPE).equals("10")) {
                    String sign2 = UserPreference.getString(UserPreference.SECRET, "") + "coinType=2" + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                    sign2 = Util.encrypt(sign2);
                    TSCApi.getInstance().getUserBookNew(3, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign2, "2");
                }
            }
        });

        rxManage.on(Event.PAY_PWD_ERROR, new Action1<String>() {
            @Override
            public void call(String o) {
                ToastUtil.toast(TransferOutActivity.this, o);
            }
        });
    }

    private boolean keyboardIsShow() {
        final Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        final int screenHeight = getWindow().getDecorView().getRootView().getHeight();
        final int heightDifference = screenHeight - rect.bottom;
        return heightDifference > screenHeight / 3;
    }

    @OnClick({R.id.img_back, R.id.et_address, R.id.img_sweep, R.id.tv_all, R.id.tv_sure})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.et_address:
                mEtAddress.setCursorVisible(true);
                break;
            case R.id.img_sweep:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int checkPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
                    if (checkPermission != PackageManager.PERMISSION_GRANTED) {//没有给权限
                        ActivityCompat.requestPermissions(TransferOutActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1010);
                    } else
                        startActivityForResult(new Intent(this, ScanCodeActivity.class), REQUEST_CODE_SCAN);
                } else
                    startActivityForResult(new Intent(this, ScanCodeActivity.class), REQUEST_CODE_SCAN);
                break;
            case R.id.tv_all:
                mEtCount.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(beforeCount, 6))));
                break;
            case R.id.tv_sure:
                if (Util.isEmpty(mEtAddress.getText().toString())) {
                    ToastUtil.toast(this, getString(R.string.hint_input_address));
                    return;
                }
                if (mEtAddress.getText().toString().equals(UserPreference.getString(UserPreference.ADDRESS, ""))) {
                    ToastUtil.toast(this, getString(R.string.hint_input_address1));
                    return;
                }
                if (Util.isEmpty(mEtCount.getText().toString()) || beforeCount < 0 ||
                        beforeCount == 0 || Double.parseDouble(mEtCount.getText().toString()) > beforeCount || Double.parseDouble(mEtCount.getText().toString()) == 0) {
                    ToastUtil.toast(this, getString(R.string.toast_count_error));
                    return;
                }
                if (getIntent().getStringExtra(COIN_TYPE).equals("10") && tscBalance < 1) {
                    ToastUtil.toast(this, getString(R.string.toast_count_error1));
                    return;
                }
                if (keyboardIsShow()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                DialogUtil.transferConfirmDialog(this, getIntent().getStringExtra(COIN_NAME), mEtCount.getText().toString(), new DialogUtil.OnResultListener0() {
                    @Override
                    public void onOK() {
                        DialogUtil.transactionDialog(TransferOutActivity.this, new DialogUtil.OnResultListener2() {
                            @Override
                            public void onOk(String... params) {
                                mPb.setVisibility(View.VISIBLE);
                                String sign = UserPreference.getString(UserPreference.SECRET, "") + "outAddress=" + mEtAddress.getText().toString() + "&payPassword=" + Util.encrypt(params[0])
                                        + "&submitTime=" + Util.getNowTime() + "&transferAmount=" + mEtCount.getText().toString() + "&userWalletType=" + mType + UserPreference.getString(UserPreference.SECRET, "");
                                sign = Util.encrypt(sign);
                                TSCApi.getInstance().transferToOthers(0, sign, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), mType, mEtAddress.getText()
                                        .toString(), mEtCount.getText().toString(), Util.encrypt(params[0]));
                            }

                            @Override
                            public void onForget() {
                                startActivity(new Intent(TransferOutActivity.this, UpdatePayPwdActivity.class));
                            }
                        });
                    }
                });
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1010) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(this, ScanCodeActivity.class), REQUEST_CODE_SCAN);
            } else {
                ToastUtil.toast(TransferOutActivity.this, getString(R.string.camera_permission));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra("get_scan_result");
//                if (content.startsWith("tsc:")) {
//                    if (!content.contains("?"))
//                        content = content.substring(4, content.length());
//                    else {
//                        if (!content.contains("amount="))
//                            content = content.substring(4, content.indexOf("?"));
//                        else
//                            content = content.substring(4, content.indexOf("?")) + "-" + content.substring(content.indexOf("=") + 1, content.indexOf(".") + 7);
//                    }
//                }
//                String address;
//                String count = "";
//                if (content.contains("-")) {
//                    address = content.substring(0, content.indexOf("-"));
//                    count = content.substring(content.indexOf("-") + 1, content.length());
//                } else {
//                    address = content;
//                }
                String address = content.substring(4, content.length());
                mEtAddress.setText(address);
                if (content.startsWith("TSC"))
                    mType = "2";
                else if (content.startsWith("T5C-T"))
                    mType = "10";
                else if (content.startsWith("NTT"))
                    mType = "11";
//                mEtCount.setText(count);
            }
        }
    }

}
