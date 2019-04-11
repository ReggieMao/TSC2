package com.ebei.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
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
import com.ebei.pojo.RedBag;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.jakewharton.rxbinding.view.RxView;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/11/22.
 * 发送红包页面
 */

public class SendRedPacketActivity extends BaseActivity {

    private static final String TAG = "SendRedPacketActivity";
    public static String RED_PACKET_TYPE = "red_packet_type";
    public static String CHAT_OR_USER_ID = "chat_or_user_id";
    public static String GROUP_MEMBER_NUM = "group_member_num";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_asset_type)
    TextView mTvAssetType;
    @Bind(R.id.ll_change)
    LinearLayout mLlChange;
    @Bind(R.id.ll_amount)
    LinearLayout mLlAmount;
    @Bind(R.id.tv_packet0)
    TextView mTvPacket0;
    @Bind(R.id.tv_packet)
    TextView mTvPacket;
    @Bind(R.id.tv_group)
    TextView mTvGroup;
    @Bind(R.id.et_count)
    EditText mEtCount;
    @Bind(R.id.et_amount)
    EditText mEtAmount;
    @Bind(R.id.et_red_content)
    EditText mEtContent;
    @Bind(R.id.tv_sure)
    TextView mTvSure;
    @Bind(R.id.view1)
    View mView1;
    @Bind(R.id.view2)
    View mView2;
    @Bind(R.id.pb)
    ProgressBar mPb;
    @Bind(R.id.tv_coin_name)
    TextView mTvCoinName;
    @Bind(R.id.tv_select)
    TextView mTvSelect;
    @Bind(R.id.tv_all)
    TextView mTvAll;
    private int mRedPacketType;
    private boolean mCountRight = false;
    private boolean mAmountRight = false;
    private String mAsset = "";
    private String mNumber = "";
    private String mCoinType = "2";
    private double tscBalance = 0;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_send_red_packet;
    }

    @Override
    public void initView() {
        if (UserPreference.getInt(UserPreference.HAD_IN_RED, 0) == 1) {
            finish();
        }
        Util.immersiveStatus(this, false);
        Util.setRedContent(mEtContent);
        mRedPacketType = getIntent().getIntExtra(RED_PACKET_TYPE, 0);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        Util.setPoint(mEtCount);
        // 利用RxJava防抖动防止多次点击确认按钮
        RxView.clicks(mTvSure).throttleFirst(2, TimeUnit.SECONDS);
        initPage();
        mEtCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCountRight = s.length() != 0;
                nextBack();
                mAsset = s.toString();
                totalAsset();
            }
        });
        mEtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAmountRight = s.length() != 0;
                nextBack();
                mNumber = s.toString();
                totalAsset();
            }
        });
        mTvCoinName.setText("TSC");
        String sign2 = UserPreference.getString(UserPreference.SECRET, "") + "coinType=2" + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign2 = Util.encrypt(sign2);
        TSCApi.getInstance().getUserBookNew(4, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign2, "2");
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.GET_COIN_INFO4, new Action1<Book>() {
            @Override
            public void call(Book o) {
                tscBalance = o.getBookBalance();
            }
        });

        rxManage.on(Event.SEND_REDBAG, new Action1<RedBag>() {
            @Override
            public void call(RedBag o) {
                UserPreference.putInt(UserPreference.HAD_IN_RED, 1);
                ToastUtil.toast(SendRedPacketActivity.this, getString(R.string.red_in));
                mPb.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.putExtra("result", 10);
                intent.putExtra("chat_or_user_id", getIntent().getIntExtra(CHAT_OR_USER_ID, 0));
                intent.putExtra("red_bag_id", o.getRedbagId());
                intent.putExtra("red_bag_describe", Util.isEmpty(mEtContent.getText().toString()) ? getString(R.string.red_content): mEtContent.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initPage() {
        switch (mRedPacketType) {
            case 0:
                mTvTitle.setText(getString(R.string.red_packet_type0)); //群红包（拼手气）
                mTvAssetType.setText(getString(R.string.asset_type0));
                mLlChange.setVisibility(View.VISIBLE);
                mTvPacket0.setText(getString(R.string.packet_type0));
                mTvPacket.setText(getString(R.string.red_packet_type1));
                mTvGroup.setVisibility(View.VISIBLE);
                mTvGroup.setText(getString(R.string.group_count).replace("xx", (getIntent().getIntExtra(GROUP_MEMBER_NUM, 0) + "")));
                mLlAmount.setVisibility(View.VISIBLE);
                mEtCount.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        keyboardIsShow();
                    }
                });
                break;
            case 1:
                mTvTitle.setText(getString(R.string.red_packet_type1)); //群红包（普通）
                mTvAssetType.setText(getString(R.string.asset_type1));
                mLlChange.setVisibility(View.VISIBLE);
                mTvPacket0.setText(getString(R.string.packet_type1));
                mTvPacket.setText(getString(R.string.red_packet_type));
                mTvGroup.setVisibility(View.VISIBLE);
                mTvGroup.setText(getString(R.string.group_count).replace("xx", (getIntent().getIntExtra(GROUP_MEMBER_NUM, 0) + "")));
                mLlAmount.setVisibility(View.VISIBLE);
                mEtCount.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        keyboardIsShow();
                    }
                });
                break;
            case 2:
                mTvTitle.setText(getString(R.string.red_packet_type2)); //点对点红包
                mTvAssetType.setText(getString(R.string.asset_type2));
                mLlChange.setVisibility(View.GONE);
                mTvGroup.setVisibility(View.GONE);
                mLlAmount.setVisibility(View.GONE);
                break;
        }
    }

    private void nextBack() {
        if (mRedPacketType != 2) {
            if (mCountRight && mAmountRight) {
                mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_red));
                mTvSure.setTextColor(getResources().getColor(R.color.cEECC90));
            } else {
                mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray3));
                mTvSure.setTextColor(getResources().getColor(R.color.white));
            }
        } else {
            if (mCountRight) {
                mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_red));
                mTvSure.setTextColor(getResources().getColor(R.color.cEECC90));
            } else {
                mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray3));
                mTvSure.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    private void totalAsset() {
        String coinName = "";
        switch (mCoinType) {
            case "2":
                coinName = " TSC";
                break;
            case "10":
                coinName = " T5C-T";
                break;
            case "11":
                coinName = " NTT";
                break;
        }
        switch (mRedPacketType) {
            case 0:
                if (Util.isEmpty(mAsset))
                    mTvAll.setText("0" + coinName);
                else {
                    BigDecimal a = new BigDecimal(mAsset);
                    BigDecimal b = new BigDecimal("1");
                    double need = a.multiply(b).doubleValue();
                    mTvAll.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(need, 6))) + coinName);
                }
                break;
            case 1:
                if (Util.isEmpty(mAsset) || Util.isEmpty(mNumber))
                    mTvAll.setText("0" + coinName);
                else {
                    BigDecimal a = new BigDecimal(mAsset);
                    BigDecimal b = new BigDecimal(mNumber);
                    double need = a.multiply(b).doubleValue();
                    mTvAll.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(need, 6))) + coinName);
                }
                break;
            case 2:
                if (Util.isEmpty(mAsset))
                    mTvAll.setText("0" + coinName);
                else {
                    BigDecimal a = new BigDecimal(mAsset);
                    BigDecimal b = new BigDecimal("1");
                    double need = a.multiply(b).doubleValue();
                    mTvAll.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(need, 6))) + coinName);
                }
                break;
        }
    }

    private void keyboardIsShow() {
        final Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        final int screenHeight = getWindow().getDecorView().getRootView().getHeight();
        final int heightDifference = screenHeight - rect.bottom;
        boolean visible = heightDifference > screenHeight / 3;
        if (!visible) {
            mView1.setVisibility(View.VISIBLE);
            mView2.setVisibility(View.VISIBLE);
        } else {
            mView1.setVisibility(View.GONE);
            mView2.setVisibility(View.GONE);
        }
    }

    private boolean keyboardIsShow1() {
        final Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        final int screenHeight = getWindow().getDecorView().getRootView().getHeight();
        final int heightDifference = screenHeight - rect.bottom;
        return heightDifference > screenHeight / 3;
    }

    @OnClick({R.id.img_back, R.id.tv_record, R.id.tv_packet, R.id.tv_tip, R.id.tv_sure, R.id.ll_coin_type})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                UserPreference.putInt(UserPreference.HAD_IN_RED, 1);
                finish();
                break;
            case R.id.tv_record:
                Intent intent = new Intent(this, RedPacketRecordActivity.class);
                intent.putExtra(RedPacketRecordActivity.COIN_TYPE, mCoinType);
                startActivity(intent);
                break;
            case R.id.tv_packet:
                if (mRedPacketType == 0) { //从拼手气红包切换到普通红包
                    mRedPacketType = 1;
                    initPage();
                } else if (mRedPacketType == 1) { //从普通红包切换到拼手气红包
                    mRedPacketType = 0;
                    initPage();
                }
                mEtCount.setText("");
                mEtAmount.setText("");
                break;
            case R.id.tv_tip:

                break;
            case R.id.tv_sure:
                switch (mRedPacketType) {
                    case 0:
                        if (mEtCount.getText().toString().equals("")) {
                            ToastUtil.toast(this, getString(R.string.toast_count1));
                            return;
                        }
                        if (mEtAmount.getText().toString().equals("")) {
                            ToastUtil.toast(this, getString(R.string.toast_count2));
                            return;
                        }
                        if (Double.parseDouble(mEtCount.getText().toString()) > 500000 * 1000) {
                            ToastUtil.toast(this, getString(R.string.toast_count4));
                            return;
                        }
                        if (Double.parseDouble(mEtAmount.getText().toString()) > 1000) {
                            ToastUtil.toast(this, getString(R.string.toast_count3));
                            return;
                        }
                        break;
                    case 1:
                        if (mEtCount.getText().toString().equals("")) {
                            ToastUtil.toast(this, getString(R.string.toast_count1));
                            return;
                        }
                        if (mEtAmount.getText().toString().equals("")) {
                            ToastUtil.toast(this, getString(R.string.toast_count2));
                            return;
                        }
                        if (Double.parseDouble(mEtCount.getText().toString()) > 500000) {
                            ToastUtil.toast(this, getString(R.string.toast_count4));
                            return;
                        }
                        if (Double.parseDouble(mEtAmount.getText().toString()) > 1000) {
                            ToastUtil.toast(this, getString(R.string.toast_count3));
                            return;
                        }
                        break;
                    case 2:
                        if (mEtCount.getText().toString().equals("")) {
                            ToastUtil.toast(this, getString(R.string.toast_count1));
                            return;
                        }
                        if (Double.parseDouble(mEtCount.getText().toString()) > 500000) {
                            ToastUtil.toast(this, getString(R.string.toast_count4));
                            return;
                        }
                        break;
                }
                if (mCoinType.equals("10") && tscBalance < 1) {
                    ToastUtil.toast(this, getString(R.string.toast_count_error1));
                    return;
                }
                DialogUtil.transactionDialog(SendRedPacketActivity.this, new DialogUtil.OnResultListener2() {
                    @Override
                    public void onOk(String... params) {
                        mPb.setVisibility(View.VISIBLE);
                        int type = 0;
                        String money = mEtCount.getText().toString();
                        String number = "";
                        switch (mRedPacketType) {
                            case 0:
                                type = 2;
                                number = mEtAmount.getText().toString();
                                break;
                            case 1:
                                type = 1;
                                number = mEtAmount.getText().toString();
                                break;
                            case 2:
                                type = 1;
                                number = "1";
                                break;
                        }
                        String sign = UserPreference.getString(UserPreference.SECRET, "") + "coinType=" + mCoinType + "&number=" + number + "&payPassword=" + Util.encrypt(params[0]) + "&submitTime=" + Util.getNowTime() + "&totalMoney=" + money + "&type=" + type + UserPreference.getString(UserPreference.SECRET, "");
                        sign = Util.encrypt(sign);
                        TSCApi.getInstance().sendRedbag(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign, type, money, number, Util.encrypt(params[0]), mCoinType);
                    }

                    @Override
                    public void onForget() {
                        startActivity(new Intent(SendRedPacketActivity.this, UpdatePayPwdActivity.class));
                    }
                });
                break;
            case R.id.ll_coin_type:
                if (keyboardIsShow1()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                DialogUtil.selectCoinDialog(this, new DialogUtil.OnResultListener4() {
                    @Override
                    public void select1() {
                        if (mEtAmount.getText().toString().equals("") && mEtCount.getText().toString().equals(""))
                            mTvAll.setText("0 TSC");
                        mCoinType = "2";
                        mTvCoinName.setText("TSC");
                        mTvSelect.setText("TSC");
                        mEtCount.setText("");
                        mEtAmount.setText("");
                    }

                    @Override
                    public void select2() {
                        if (mEtAmount.getText().toString().equals("") && mEtCount.getText().toString().equals(""))
                            mTvAll.setText("0 T5C-T");
                        mCoinType = "10";
                        mTvCoinName.setText("T5C-T");
                        mTvSelect.setText("T5C-T");
                        mEtCount.setText("");
                        mEtAmount.setText("");
                    }

                    @Override
                    public void select3() {
                        if (mEtAmount.getText().toString().equals("") && mEtCount.getText().toString().equals(""))
                            mTvAll.setText("0 NTT");
                        mCoinType = "11";
                        mTvCoinName.setText("NTT");
                        mTvSelect.setText("NTT");
                        mEtCount.setText("");
                        mEtAmount.setText("");
                    }
                });
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            UserPreference.putInt(UserPreference.HAD_IN_RED, 1);
        }
        return super.onKeyDown(keyCode, event);
    }

}
