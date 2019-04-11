package com.ebei.ui;

import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebei.adapter.RecordAdapter;
import com.ebei.library.AutoPullAbleGridView;
import com.ebei.library.AutoPullToRefreshLayout;
import com.ebei.library.BaseActivity;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.DigitalRecord;
import com.ebei.pojo.FotaRecord;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/11/1.
 * 期权交易记录页面
 */

public class DigitalRecordActivity extends BaseActivity {

    private static final String TAG = "DigitalRecordActivity";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.gv_record_list)
    AutoPullAbleGridView mListView;
    @Bind(R.id.refresh_view)
    AutoPullToRefreshLayout mPullLayout;
    @Bind(R.id.ll_no_data)
    LinearLayout mLlNoData;
    @Bind(R.id.pb)
    ProgressBar mPb;
    private RecordAdapter mAdapter;
    private int mCurrentPage = 1;
    private boolean mFlag = false;
    private boolean mDownFlag = false;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_digital_record;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        mPullLayout.setOnRefreshListener(listener);
        mListView.setOnLoadListener(autoListener);
        mListView.finishLoading(AutoPullAbleGridView.INIT);
        mLlNoData.setVisibility(View.GONE);
        getRecordList(mCurrentPage);
    }

    private void getRecordList(int page) {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "pageNo=" + page + "&pageSize=10&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        TSCApi.getInstance().fotaFindOrderPage(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign, page, 10);
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.FOTA_FIND_ORDER_PAGE, new Action1<DigitalRecord>() {
            @Override
            public void call(DigitalRecord o) {
                showRecordList(o.getItem(), mCurrentPage, mFlag);
            }
        });
    }

    private AutoPullToRefreshLayout.OnRefreshListener listener = new AutoPullToRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(AutoPullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mDownFlag = true;
                    mCurrentPage = 1;
                    getRecordList(mCurrentPage);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }
    };

    AutoPullAbleGridView.OnLoadListener autoListener = new AutoPullAbleGridView.OnLoadListener() {
        @Override
        public void onLoad(AutoPullAbleGridView pullAbleListView) {
            // 上拉加载
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mFlag = true;
                    mCurrentPage ++;
                    getRecordList(mCurrentPage);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }
    };

    private void showRecordList(List<FotaRecord> list, int page, boolean flag) {
        mPb.setVisibility(View.GONE);
        if (mDownFlag) {
            mDownFlag = false;
            mPullLayout.refreshFinish(AutoPullToRefreshLayout.SUCCEED);
        }
        if (null != list && list.size() > 0) {
            mLlNoData.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            mListView.setLoadDataFlag(true);
            mListView.finishLoading(AutoPullAbleGridView.INIT);
            if (null == mAdapter) {
                mAdapter = new RecordAdapter(this, list);
                mListView.setAdapter(mAdapter);
            } else {
                if (page == 1) {
                    mAdapter.setItems(list);
                } else {
                    mAdapter.addItems(list);
                }
            }
        } else {
            mListView.setLoadDataFlag(false);
            mListView.finishLoading(AutoPullAbleGridView.FINISH);
            if (flag) {
                if (page == 1) {
                    mLlNoData.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                }
            } else {
                if (null != mAdapter) mAdapter.setItems(list);
                if (page == 1) {
                    mLlNoData.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                }
            }
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

    @Override
    protected void onRestart() {
        super.onRestart();
        getRecordList(mCurrentPage);
    }

}
