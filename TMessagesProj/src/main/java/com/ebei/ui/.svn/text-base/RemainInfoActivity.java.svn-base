package com.ebei.ui;

import android.content.res.Configuration;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebei.library.BaseActivity;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.ebei.pojo.Balance;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/9/5.
 * 交易详情页面
 */

public class RemainInfoActivity extends BaseActivity {

    private static final String TAG = "RemainInfoActivity";
    public static String FROM_PUSH = "from_push";
    public static String BALANCE = "balance";
    public static String COUNT = "count";
    public static String ADDRESS = "address";
    public static String FEE = "fee";
    public static String TIME = "time";
    public static String TRADE_ID = "trade_id";
    public static String COIN_NAME = "coin_name";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.img_type)
    ImageView mImgView;
    @Bind(R.id.tv_type)
    TextView mTvOutIn;
    @Bind(R.id.tv_count)
    TextView mTvCount;
    @Bind(R.id.rl_status_fail)
    RelativeLayout mRlFail;
    @Bind(R.id.rl_status_ing)
    RelativeLayout mRlSureIng;
    @Bind(R.id.rl_status_success)
    RelativeLayout mRlSuccess;
    @Bind(R.id.collect_address)
    TextView mTvInAddress;
    @Bind(R.id.send_address)
    TextView mTvOutAddress;
    @Bind(R.id.hand_fee)
    TextView mTvFee;
    @Bind(R.id.trade_address)
    TextView mTvTradeCode;
    @Bind(R.id.trader_time)
    TextView mTvTime;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_remain_info;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        if (!getIntent().getBooleanExtra(FROM_PUSH, false)) {
            Balance balance = (Balance) getIntent().getSerializableExtra(BALANCE);
            String coinName = "";
            switch (balance.getBookCode()) {
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
            if (balance.getReceiveAddress().equals(UserPreference.getString(UserPreference.ADDRESS, ""))) {
                mImgView.setImageResource(R.mipmap.shou_icon);
                mTvOutIn.setText(getString(R.string.tsc_in));
                mTvCount.setText("+" + Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(balance.getChangeValue(), 6))) + " (" + coinName + ")");
            } else {
                mImgView.setImageResource(R.mipmap.zhuan_icon);
                mTvOutIn.setText(getString(R.string.tsc_out));
                mTvCount.setText("-" + Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(balance.getChangeValue(), 6))) + " (" + coinName + ")");
            }
            if (balance.getBlockHeight() != 0) {
                mRlSuccess.setVisibility(View.VISIBLE);
                mRlSureIng.setVisibility(View.GONE);
                mRlFail.setVisibility(View.GONE);
            } else {
                mRlSuccess.setVisibility(View.GONE);
                mRlSureIng.setVisibility(View.VISIBLE);
                mRlFail.setVisibility(View.GONE);
            }
            mTvInAddress.setText(balance.getReceiveAddress());
            mTvOutAddress.setText(balance.getSendAddress());
            mTvFee.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(balance.getServiceFee(), 6))) + " " + coinName);
            mTvTradeCode.setText(balance.getTransationId());
            mTvTime.setText(balance.getUpdateDate());
        } else {
            mImgView.setImageResource(R.mipmap.shou_icon);
            mTvOutIn.setText(getString(R.string.tsc_in));
            mTvCount.setText("+" + Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(Double.parseDouble(getIntent().getStringExtra(COUNT)), 6))) + " (" + getIntent().getStringExtra(COIN_NAME) + ")");
            mRlSuccess.setVisibility(View.VISIBLE);
            mRlSureIng.setVisibility(View.GONE);
            mRlFail.setVisibility(View.GONE);
            mTvInAddress.setText(UserPreference.getString(UserPreference.ADDRESS, ""));
            mTvOutAddress.setText(getIntent().getStringExtra(ADDRESS));
            mTvFee.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(Double.parseDouble(getIntent().getStringExtra(FEE)), 6))) + " " + getIntent().getStringExtra(COIN_NAME));
            mTvTradeCode.setText(getIntent().getStringExtra(TRADE_ID));
            mTvTime.setText(getIntent().getStringExtra(TIME));
        }
    }

    @OnClick({R.id.img_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

}
