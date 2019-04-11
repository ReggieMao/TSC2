package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebei.adapter.IncomeAdapter;
import com.ebei.library.ActivityUtil;
import com.ebei.library.AutoPullAbleGridView;
import com.ebei.library.BaseActivity;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.Miner;
import com.ebei.pojo.MineralAsset;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.ebei.ui.Components.CustomDatePicker;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/11/23.
 * TSC矿池页面
 */

public class TSCMineralActivity extends BaseActivity {

    private static final String TAG = "TSCMineralActivity";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_all_asset)
    TextView mTvAllAsset;
    @Bind(R.id.tv_yesterday)
    TextView mTvYesterday;
    @Bind(R.id.tv_all)
    TextView mTvAll;
    @Bind(R.id.gv_income_list)
    AutoPullAbleGridView mListView;
    @Bind(R.id.ll_no_data)
    LinearLayout mLlNoData;
    @Bind(R.id.pb)
    ProgressBar mPb;
    @Bind(R.id.tv_month)
    TextView mTvMonth;
    private IncomeAdapter mAdapter;
    private CustomDatePicker mCustomDatePicker;
    private String nowMonth;
    private List<Miner> mIncomeList;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_tsc_mineral;
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
        TSCApi.getInstance().minerDetail(0, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign);
        TSCApi.getInstance().mineralAsset(1, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        nowMonth = now.split("-")[0] + "." + now.split("-")[1];
        mTvMonth.setText(nowMonth);
        mCustomDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                nowMonth = time.split("-")[0] + "." + time.split("-")[1];
                mTvMonth.setText(nowMonth);
                selectMonthBalance(nowMonth);
            }
        }, "1900-01-01 00:00", now);
        mCustomDatePicker.showSpecificTime(false);
        mCustomDatePicker.setIsLoop(false);
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.MINERAL_ASSET1, new Action1<MineralAsset>() {
            @Override
            public void call(MineralAsset o) {
                mTvYesterday.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(o.getOreAmount(), 6))));
                mTvAll.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(o.getTotalOreAmount(), 6))));
                mTvAllAsset.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(o.getTotalIncomeInf(), 6))));
            }
        });

        rxManage.on(Event.MINER_DETAIL, new Action1<List<Miner>>() {
            @Override
            public void call(List<Miner> o) {
                mIncomeList = o;
                selectMonthBalance(nowMonth);
            }
        });
    }

    private void selectMonthBalance(String month) {
        List<Miner> miners = new ArrayList<>();
        for (int i = 0; i < mIncomeList.size(); i++) {
            if (mIncomeList.get(i).getCreateDate().startsWith(month.replace(".", "-"))) {
                miners.add(mIncomeList.get(i));
            }
        }
        showIncomeList(miners);
    }

    private void showIncomeList(List<Miner> list) {
        mPb.setVisibility(View.GONE);
        if (null != list && list.size() > 0) {
            mLlNoData.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            if (null == mAdapter) {
                mAdapter = new IncomeAdapter(this, list);
                mListView.setAdapter(mAdapter);
            } else {
                mAdapter.setItems(list);
            }
        } else {
            mLlNoData.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            if (null != mAdapter) mAdapter.setItems(list);
        }
    }

    @OnClick({R.id.ll_in, R.id.ll_out, R.id.img_back, R.id.date_select})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_in:
                startActivity(new Intent(this, MineralCashActivity.class));
                break;
            case R.id.ll_out:
                startActivity(new Intent(this, TransToMineralActivity.class));
                break;
            case R.id.img_back:
                ActivityUtil.finishAll();
                break;
            case R.id.date_select:
                mCustomDatePicker.show(nowMonth.replace(".", "-"));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("矿池");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("矿池");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        TSCApi.getInstance().minerDetail(0, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign);
        TSCApi.getInstance().mineralAsset(1, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityUtil.finishAll();
        }
        return false;
    }

}
