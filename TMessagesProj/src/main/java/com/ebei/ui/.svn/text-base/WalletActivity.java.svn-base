package com.ebei.ui;

import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebei.adapter.WalletAdapter;
import com.ebei.library.ActivityUtil;
import com.ebei.library.AutoPullAbleGridView;
import com.ebei.library.BaseActivity;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.Wallet;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2019/1/25.
 * 钱包页面
 */

public class WalletActivity extends BaseActivity {

    private static final String TAG = "WalletActivity";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.gv_wallet_list)
    AutoPullAbleGridView mListView;
    @Bind(R.id.ll_no_data)
    LinearLayout mLlNoData;
    @Bind(R.id.pb)
    ProgressBar mPb;
    private WalletAdapter mAdapter;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_wallet;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        ActivityUtil.add(this);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        getWalletList();
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.GET_WALLET_LIST, new Action1<List<Wallet>>() {
            @Override
            public void call(List<Wallet> o) {
                showWalletList(o);
            }
        });
    }

    private void getWalletList() {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        TSCApi.getInstance().getWalletList(0, UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign);
    }

    private void showWalletList(List<Wallet> list) {
        mPb.setVisibility(View.GONE);
        if (null != list && list.size() > 0) {
            mLlNoData.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            if (null == mAdapter) {
                mAdapter = new WalletAdapter(this, list, 0);
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

    @OnClick({R.id.img_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                ActivityUtil.finishAll();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("钱包");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("钱包");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getWalletList();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityUtil.finishAll();
        }
        return false;
    }

}
