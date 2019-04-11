package com.ebei.ui;

import android.content.res.Configuration;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebei.library.BaseActivity;
import com.ebei.library.Util;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/11/1.
 * T值页面
 */

public class TValueActivity extends BaseActivity {

    private static final String TAG = "TValueActivity";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_t_value;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
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
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("T值活动");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("T值活动");
    }

}
