package com.ebei.ui;

import android.content.Intent;
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
 * 常见问题页面
 */

public class CommonQuestionActivity extends BaseActivity {

    private static final String TAG = "CommonQuestionActivity";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_common_question;
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

    @OnClick({R.id.img_back, R.id.rl_question1, R.id.rl_question2, R.id.rl_question3, R.id.rl_question4, R.id.rl_question5, R.id.rl_question6})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_question1:
                Intent intent1 = new Intent(this, QuestionInfoActivity.class);
                intent1.putExtra(QuestionInfoActivity.TITLE, getString(R.string.question1));
                startActivity(intent1);
                break;
            case R.id.rl_question2:
                Intent intent2 = new Intent(this, QuestionInfoActivity.class);
                intent2.putExtra(QuestionInfoActivity.TITLE, getString(R.string.question2));
                startActivity(intent2);
                break;
            case R.id.rl_question3:
                Intent intent3 = new Intent(this, QuestionInfoActivity.class);
                intent3.putExtra(QuestionInfoActivity.TITLE, getString(R.string.question3));
                startActivity(intent3);
                break;
            case R.id.rl_question4:
                Intent intent4 = new Intent(this, QuestionInfoActivity.class);
                intent4.putExtra(QuestionInfoActivity.TITLE, getString(R.string.question4));
                startActivity(intent4);
                break;
            case R.id.rl_question5:
                Intent intent5 = new Intent(this, QuestionInfoActivity.class);
                intent5.putExtra(QuestionInfoActivity.TITLE, getString(R.string.question5));
                startActivity(intent5);
                break;
            case R.id.rl_question6:
                Intent intent6 = new Intent(this, QuestionInfoActivity.class);
                intent6.putExtra(QuestionInfoActivity.TITLE, getString(R.string.question6));
                startActivity(intent6);
                break;
        }
    }

}
