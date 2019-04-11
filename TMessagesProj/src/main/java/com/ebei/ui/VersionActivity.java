package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebei.library.BaseActivity;
import com.ebei.library.Constants;
import com.ebei.library.ToastUtil;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/11/28.
 * 版本页面
 */

public class VersionActivity extends BaseActivity {

    private static final String TAG = "VersionActivity";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_code)
    TextView mTvCode;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.tv_update)
    TextView mTvUpdate;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_version;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        mTvCode.setText(getString(R.string.version_code) + UserPreference.getString(UserPreference.VERSION_CODE, ""));
        mTvContent.setText(UserPreference.getString(UserPreference.VERSION_LOG, ""));
        if (Constants.APP_VERSION.equals(UserPreference.getString(UserPreference.VERSION_CODE, "")))
            mTvUpdate.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray1));
        else
            mTvUpdate.setBackground(getResources().getDrawable(R.drawable.bg_round_text_blue3));
    }

    @OnClick({R.id.img_back, R.id.tv_update})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_update:
                if (Constants.APP_VERSION.equals(UserPreference.getString(UserPreference.VERSION_CODE, "")))
                    ToastUtil.toast(this, getString(R.string.best_new));
                else {
                    Intent intent = new Intent(this, AppDownloadActivity.class);
                    intent.putExtra(AppDownloadActivity.URL, UserPreference.getString(UserPreference.VERSION_URL, ""));
                    startActivity(intent);
                }
                break;
        }
    }

}
