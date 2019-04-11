package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebei.adapter.BalanceAdapter;
import com.ebei.library.AutoPullAbleGridView;
import com.ebei.library.BaseActivity;
import com.ebei.library.DialogUtil;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.Balance;
import com.ebei.pojo.Book;
import com.ebei.pojo.MineralAsset;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.ebei.ui.Components.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/11/22.
 * 我的TSC页面
 */

public class MyTSCActivity extends BaseActivity {

    private static final String TAG = "MyTSCActivity";
    public static String COIN_NAME = "coin_name";
    public static String COIN_TYPE = "coin_type";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_all)
    TextView mTvAll;
    @Bind(R.id.tv_asset)
    TextView mTvAsset;
    @Bind(R.id.tv_all_get)
    TextView mTvAllGet;
    @Bind(R.id.gv_remain_list)
    AutoPullAbleGridView mListView;
    @Bind(R.id.ll_no_data)
    LinearLayout mLlNoData;
    @Bind(R.id.pb)
    ProgressBar mPb;
    @Bind(R.id.tv_month)
    TextView mTvMonth;
    @Bind(R.id.tv_mineral)
    TextView mTvMineral;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    private BalanceAdapter mAdapter;
    private String mAddress;
    private CustomDatePicker mCustomDatePicker;
    private String nowMonth;
    private List<Balance> mBalanceList;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_my_tsc;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setText(getString(R.string.my_tsc).replace("xxx", getIntent().getStringExtra(COIN_NAME)));
        mTvAll.setText(getString(R.string.all_asset).replace("xxx", getIntent().getStringExtra(COIN_NAME)));
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "coinType=" + getIntent().getStringExtra(COIN_TYPE) + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign1 = Util.encrypt(sign1);
        TSCApi.getInstance().getUserBookNew(0, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign1, getIntent().getStringExtra(COIN_TYPE));
        if (getIntent().getStringExtra(COIN_TYPE).equals("2")) {
            String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
            sign = Util.encrypt(sign);
            TSCApi.getInstance().mineralAsset(0, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign);
            mTvMineral.setText(getString(R.string.wakuang));
            mTvAllGet.setVisibility(View.VISIBLE);
            mTvAddress.setVisibility(View.VISIBLE);
        } else {
            mTvMineral.setText(getString(R.string.in));
            mTvAllGet.setVisibility(View.GONE);
            mTvAddress.setVisibility(View.GONE);
        }
        getRemainList();

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
        rxManage.on(Event.GET_COIN_INFO, new Action1<Book>() {
            @Override
            public void call(Book o) {
                UserPreference.putString(UserPreference.ADDRESS, o.getWalletAddress());
                mTvAsset.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(o.getBookBalance(), 6))));
                mAddress = o.getWalletAddress();
            }
        });

        rxManage.on(Event.MINERAL_ASSET, new Action1<MineralAsset>() {
            @Override
            public void call(MineralAsset o) {
                mTvAllGet.setText(getString(R.string.all_get) + Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(o.getTotalOreAmount(), 6))));
            }
        });

        rxManage.on(Event.BALANCE_LIST, new Action1<List<Balance>>() {
            @Override
            public void call(List<Balance> o) {
                mBalanceList = o;
                selectMonthBalance(nowMonth);
            }
        });
    }

    private void getRemainList() {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + "&userWalletType=" + getIntent().getStringExtra(COIN_TYPE) + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        TSCApi.getInstance().findBalanceList(sign, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), getIntent().getStringExtra(COIN_TYPE));
    }

    private void selectMonthBalance(String month) {
        List<Balance> balances = new ArrayList<>();
        for (int i = 0; i < mBalanceList.size(); i++) {
            if (mBalanceList.get(i).getUpdateDate().startsWith(month.replace(".", "-"))) {
                balances.add(mBalanceList.get(i));
            }
        }
        showBalanceList(balances);
    }

    private void showBalanceList(List<Balance> list) {
        mPb.setVisibility(View.GONE);
        if (null != list && list.size() > 0) {
            mLlNoData.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            if (null == mAdapter) {
                mAdapter = new BalanceAdapter(this, list);
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

    @OnClick({R.id.img_back, R.id.tv_address, R.id.tv_transfer, R.id.tv_mineral, R.id.date_select})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_address:
                DialogUtil.walletAddressDialog(this, getIntent().getStringExtra(COIN_NAME) + " " + mAddress, getIntent().getStringExtra(COIN_TYPE));
                break;
            case R.id.tv_transfer:
                Intent intent = new Intent(this, TransferOutActivity.class);
                intent.putExtra(TransferOutActivity.COIN_TYPE, getIntent().getStringExtra(COIN_TYPE));
                intent.putExtra(TransferOutActivity.COIN_NAME, getIntent().getStringExtra(COIN_NAME));
                startActivity(intent);
                break;
            case R.id.tv_mineral:
                if (getIntent().getStringExtra(COIN_TYPE).equals("2"))
                    startActivity(new Intent(this, TransToMineralActivity.class));
                else
                    DialogUtil.walletAddressDialog(this, getIntent().getStringExtra(COIN_NAME) + " " + mAddress, getIntent().getStringExtra(COIN_TYPE));
                break;
            case R.id.date_select:
                mCustomDatePicker.show(nowMonth.replace(".", "-"));
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "coinType=" + getIntent().getStringExtra(COIN_TYPE) + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign1 = Util.encrypt(sign1);
        TSCApi.getInstance().getUserBookNew(0, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign1, getIntent().getStringExtra(COIN_TYPE));
        if (getIntent().getStringExtra(COIN_TYPE).equals("2")) {
            String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
            sign = Util.encrypt(sign);
            TSCApi.getInstance().mineralAsset(0, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign);
        }
        getRemainList();
    }

}
