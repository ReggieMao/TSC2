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
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/11/1.
 * 聊添管理页面
 */

public class ChatManagerActivity extends BaseActivity {

    private static final String TAG = "ChatManagerActivity";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_chat_manager;
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

    @OnClick({R.id.img_back, R.id.rl1, R.id.rl2, R.id.rl3})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back: // 返回
                finish();
                break;
            case R.id.rl1: // 通知与音效
                Intent intent = new Intent();
                intent.putExtra("result", 777);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.rl3: // 数据管理
//                startActivity(new Intent(this, DataManagerActivity.class));
                Intent intent1 = new Intent();
                intent1.putExtra("result", 888);
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case R.id.rl2: // 贴纸管理
                Intent intent2 = new Intent();
                intent2.putExtra("result", 666);
                setResult(RESULT_OK, intent2);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("其他");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("其他");
    }

}
