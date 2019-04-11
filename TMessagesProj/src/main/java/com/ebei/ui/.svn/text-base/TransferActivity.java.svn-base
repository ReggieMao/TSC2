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
import com.ebei.pojo.ChatTransfer;
import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.R;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.UserObject;
import com.ebei.ui.Components.BackupImageView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2019/1/29.
 * 聊天转账页面
 */

public class TransferActivity extends BaseActivity {

    private static final String TAG = "TransferActivity";
    public static String UID = "uid";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.et_count)
    EditText mEtCount;
    @Bind(R.id.tv_sure)
    TextView mTvSure;
    @Bind(R.id.view1)
    View mView1;
    @Bind(R.id.view2)
    View mView2;
    @Bind(R.id.img_avatar)
    BackupImageView mAvatar;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_select)
    TextView mTvSelect;
    @Bind(R.id.pb)
    ProgressBar mPb;
    private String mCoinType = "2";
    private boolean canTransfer = false;
    private String tscAddress = "";
    private String t5cAddress = "";
    private double tscBalance = 0;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_transfer;
    }

    @Override
    public void initView() {
        if (UserPreference.getInt(UserPreference.HAD_IN_TRANSFER, 0) == 1) {
            finish();
        }
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        Util.setPoint(mEtCount);
        // 利用RxJava防抖动防止多次点击确认按钮
        RxView.clicks(mTvSure).throttleFirst(2, TimeUnit.SECONDS);
        mEtCount.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                keyboardIsShow();
            }
        });
        mEtCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Util.isEmpty(s.toString())) {
                    mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray3));
                } else {
                    mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_blue3));
                }
            }
        });
        if (!Util.isEmpty(getIntent().getStringExtra(UID))) {
            Util.setTeleInfo(getIntent().getStringExtra(UID), mAvatar);
            TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Integer.parseInt(getIntent().getStringExtra(UID)));
            if (user != null)
                mTvName.setText(getString(R.string.transfer_to).replace("xxx", UserObject.getUserName(user)));
            if (user != null && user.phone != null) {
                String sign = UserPreference.getString(UserPreference.SECRET, "") + "mobile=" + user.phone.substring(user.phone.length() - 11, user.phone.length()) + "&openId=" + getIntent().getStringExtra(UID) + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                sign = Util.encrypt(sign);
                TSCApi.getInstance().findWalletListByOpenId(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign, getIntent().getStringExtra(UID), user.phone.substring(user.phone.length() - 11, user.phone.length()));
            }
        }
        String sign2 = UserPreference.getString(UserPreference.SECRET, "") + "coinType=2" + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign2 = Util.encrypt(sign2);
        TSCApi.getInstance().getUserBookNew(5, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign2, "2");
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.GET_COIN_INFO5, new Action1<Book>() {
            @Override
            public void call(Book o) {
                tscBalance = o.getBookBalance();
            }
        });

        rxManage.on(Event.CHAT_TRANSFER, new Action1<List<ChatTransfer>>() {
            @Override
            public void call(List<ChatTransfer> o) {
                canTransfer = true;
                for (int i = 0; i < o.size(); i ++) {
                    if (o.get(i).getUserWalletType().equals("2"))
                        tscAddress = o.get(i).getUserWalletAddress();
                    else if (o.get(i).getUserWalletType().equals("10"))
                        t5cAddress = o.get(i).getUserWalletAddress();
                }
            }
        });

        rxManage.on(Event.TRANSFER_TO_OTHERS1, new Action1() {
            @Override
            public void call(Object o) {
                UserPreference.putInt(UserPreference.HAD_IN_TRANSFER, 1);
                ToastUtil.toast(TransferActivity.this, getString(R.string.toast_transfer_success));
                mPb.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.putExtra("send_success", 1);
                intent.putExtra("result", 11);
                String coinName = "";
                switch (mCoinType) {
                    case "2":
                        coinName = "TSC";
                        break;
                    case "10":
                        coinName = "T5C-T";
                        break;
                    case "11":
                        coinName = "NTT";
                        break;
                }
                intent.putExtra("coin_name", coinName);
                intent.putExtra("coin_amount", mEtCount.getText().toString());
                intent.putExtra("user_id", getIntent().getStringExtra(UID));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        rxManage.on(Event.PAY_PWD_ERROR, new Action1<String>() {
            @Override
            public void call(String o) {
                ToastUtil.toast(TransferActivity.this, o);
                mPb.setVisibility(View.GONE);
            }
        });

        rxManage.on(Event.OPEN_ID_ERROR, new Action1<String>() {
            @Override
            public void call(String o) {
                ToastUtil.toast(TransferActivity.this, o);
                finish();
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

    @OnClick({R.id.img_back, R.id.tv_sure, R.id.ll_coin})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                UserPreference.putInt(UserPreference.HAD_IN_TRANSFER, 1);
                finish();
                break;
            case R.id.tv_sure:
                if (Util.isEmpty(mEtCount.getText().toString())) {
                    ToastUtil.toast(this, getString(R.string.toast_count5));
                    return;
                }
                if (canTransfer) {
                    if (mCoinType.equals("10") && tscBalance < 1) {
                        ToastUtil.toast(this, getString(R.string.toast_count_error1));
                        return;
                    }
                    DialogUtil.transactionDialog(this, new DialogUtil.OnResultListener2() {
                        @Override
                        public void onOk(String... params) {
                            mPb.setVisibility(View.VISIBLE);
                            String sign = UserPreference.getString(UserPreference.SECRET, "") + "outAddress=" + (mCoinType.equals("2") ? tscAddress : t5cAddress) + "&payPassword=" + Util.encrypt(params[0])
                                    + "&submitTime=" + Util.getNowTime() + "&transferAmount=" + mEtCount.getText().toString() + "&userWalletType=" + mCoinType + UserPreference.getString(UserPreference.SECRET, "");
                            sign = Util.encrypt(sign);
                            TSCApi.getInstance().transferToOthers(1, sign, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), mCoinType,(mCoinType.equals("2") ? tscAddress : t5cAddress), mEtCount.getText().toString(), Util.encrypt(params[0]));
                        }

                        @Override
                        public void onForget() {
                            startActivity(new Intent(TransferActivity.this, UpdatePayPwdActivity.class));
                        }
                    });
                }
                break;
            case R.id.ll_coin:
                if (keyboardIsShow1()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                DialogUtil.selectCoinDialog(this, new DialogUtil.OnResultListener4() {
                    @Override
                    public void select1() {
                        mCoinType = "2";
                        mTvSelect.setText("TSC");
                        mEtCount.setText("");
                    }

                    @Override
                    public void select2() {
                        mCoinType = "10";
                        mTvSelect.setText("T5C-T");
                        mEtCount.setText("");
                    }

                    @Override
                    public void select3() {
                        mCoinType = "11";
                        mTvSelect.setText("NTT");
                        mEtCount.setText("");
                    }
                });
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            UserPreference.putInt(UserPreference.HAD_IN_TRANSFER, 1);
        }
        return super.onKeyDown(keyCode, event);
    }

}
