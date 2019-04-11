package com.ebei.ui;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ebei.library.BaseActivity;
import com.ebei.library.Util;
import com.ebei.tsc.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by MaoLJ on 2018/11/13.
 * 引导页
 */

public class GuideActivity extends BaseActivity {

    private static final String TAG = "GuideActivity";
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.view_title)
    View mViewTitle;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        List<View> viewList = new ArrayList<>();
        LayoutInflater lf = getLayoutInflater().from(this);
        View view1 = lf.inflate(R.layout.view_guide1, null);
        View view2 = lf.inflate(R.layout.view_guide2, null);
        View view3 = lf.inflate(R.layout.view_guide3, null);
        ImageView click = view3.findViewById(R.id.view_click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(GuideActivity.this, LaunchActivity.class);
                intent2.putExtra("fromIntro", true);
                startActivity(intent2);
                finish();
            }
        });
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        mViewPager.setAdapter(new ViewPagerAdapter(viewList));
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private List<View> mViewList ;

        public ViewPagerAdapter(List<View> mViewList ) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }

}
