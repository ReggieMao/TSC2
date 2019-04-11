package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebei.library.BaseActivity;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.FotaBalance;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2019/1/25.
 * 期权账户页面
 */

public class DigitalAccountActivity extends BaseActivity {

    private static final String TAG = "DigitalAccountActivity";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_balance)
    TextView mTvBalance;
    private String mTSCBalance;
    private String mFOTABalance;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_digital_account;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        getBalance();
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.GET_FOTA_BALANCE, new Action1<FotaBalance>() {
            @Override
            public void call(FotaBalance o) {
                mTSCBalance = o.getTSC();
                mFOTABalance = o.getTSC_fota();
                mTvBalance.setText(mFOTABalance);
            }
        });
    }

    private void getBalance() {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        TSCApi.getInstance().getFotaBalance(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign);
    }

    @OnClick({R.id.tv_title, R.id.img_back, R.id.rl_recharge, R.id.rl_withdraw})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                startActivity(new Intent(this, DigitalRecordActivity.class));
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_recharge:
                Intent intent = new Intent(this, DigitalCoinActivity.class);
                intent.putExtra(DigitalCoinActivity.TRADE_TYPE, 1);
                intent.putExtra(DigitalCoinActivity.TSC_BALANCE, mTSCBalance);
                startActivity(intent);
                break;
            case R.id.rl_withdraw:
                Intent intent1 = new Intent(this, DigitalCoinActivity.class);
                intent1.putExtra(DigitalCoinActivity.TRADE_TYPE, 2);
                intent1.putExtra(DigitalCoinActivity.FOTA_BALANCE, mFOTABalance);
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getBalance();
    }

}
