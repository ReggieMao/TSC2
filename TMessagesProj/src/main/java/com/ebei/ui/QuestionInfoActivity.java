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

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/11/27.
 * 问题详情页面
 */

public class QuestionInfoActivity extends BaseActivity {

    private static final String TAG = "CommonQuestionActivity";
    public static String TITLE = "title";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.layout1)
    View mLayout1;
    @Bind(R.id.layout2)
    View mLayout2;
    @Bind(R.id.layout3)
    View mLayout3;
    @Bind(R.id.layout4)
    View mLayout4;
    @Bind(R.id.layout5)
    View mLayout5;
    @Bind(R.id.layout6)
    View mLayout6;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_question_info;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        mTvTitle.setText(getIntent().getStringExtra(TITLE));
        switch (getIntent().getStringExtra(TITLE)) {
            case "登录注册相关问题":
                mLayout1.setVisibility(View.VISIBLE);
                break;
            case "群组功能":
                mLayout2.setVisibility(View.VISIBLE);
                break;
            case "个人账户":
                mLayout3.setVisibility(View.VISIBLE);
                break;
            case "钱包功能":
                mLayout4.setVisibility(View.VISIBLE);
                break;
            case "矿池功能":
                mLayout5.setVisibility(View.VISIBLE);
                break;
            case "其他问题":
                mLayout6.setVisibility(View.VISIBLE);
                break;
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
