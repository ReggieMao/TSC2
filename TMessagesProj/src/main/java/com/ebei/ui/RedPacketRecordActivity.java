package com.ebei.ui;

import android.content.res.Configuration;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebei.adapter.RedInAdapter;
import com.ebei.adapter.RedOutAdapter;
import com.ebei.library.AutoPullAbleGridView;
import com.ebei.library.BaseActivity;
import com.ebei.library.DialogUtil;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.RecieveBagIn;
import com.ebei.pojo.RecieveBagOut;
import com.ebei.pojo.RedBagFind1;
import com.ebei.pojo.RedBagFind2;
import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.R;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.UserObject;
import com.ebei.ui.ActionBar.Theme;
import com.ebei.ui.Components.AvatarDrawable;
import com.ebei.ui.Components.BackupImageView;
import com.ebei.ui.Components.CustomDatePicker1;

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
 * 红包记录页面
 */

public class RedPacketRecordActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SendRedPacketActivity";
    public static String COIN_TYPE = "coin_type";
    private ArrayList<View> mPageList;
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.view_pager)
    ViewPager mViewPage;
    @Bind(R.id.tv_red_in)
    TextView mTvRedIn;
    @Bind(R.id.tv_red_out)
    TextView mTvRedOut;
    @Bind(R.id.pb)
    ProgressBar mPb;
    private View mView1;
    private View mView2;
    private View mViewHead1;
    private View mViewHead2;
    private AutoPullAbleGridView mListView1;
    private AutoPullAbleGridView mListView2;
    private LinearLayout mTvNoData1;
    private LinearLayout mTvNoData2;
    private RedInAdapter mAdapter1;
    private RedOutAdapter mAdapter2;
    private TextView mTvYear1;
    private TextView mTvYear2;
    private TextView mTvNumber1;
    private TextView mTvNumber2;
    private TextView mTvAmount1;
    private TextView mTvAmount2;
    private String mNowYear1;
    private String mNowYear2;
    private CustomDatePicker1 mCustomDatePicker1;
    private CustomDatePicker1 mCustomDatePicker2;
    private List<RecieveBagIn> redInList;
    private List<RecieveBagOut> redOutList;
    private LinearLayout mLlYearSelect1;
    private LinearLayout mLlYearSelect2;
    private String now;
    private int mCurrentSelect = 0;
    private String mCoinType;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_red_packet_record;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        mCoinType = getIntent().getStringExtra(COIN_TYPE);

        LayoutInflater inflater = getLayoutInflater();
        mView1 = inflater.inflate(R.layout.view_red_in, null);
        mView2 = inflater.inflate(R.layout.view_red_out, null);
        mViewHead1 = inflater.inflate(R.layout.view_red_head, null);
        mViewHead2 = inflater.inflate(R.layout.view_red_head, null);
        BackupImageView mImgAvatar1 = mViewHead1.findViewById(R.id.img_avatar);
        BackupImageView mImgAvatar2 = mViewHead2.findViewById(R.id.img_avatar);
        TextView mTvName1 = mViewHead1.findViewById(R.id.tv_name);
        TextView mTvName2 = mViewHead2.findViewById(R.id.tv_name);
        mTvNumber1 = mViewHead1.findViewById(R.id.tv_number);
        mTvNumber2 = mViewHead2.findViewById(R.id.tv_number);
        mTvAmount1 = mViewHead1.findViewById(R.id.tv_amount);
        mTvAmount2 = mViewHead2.findViewById(R.id.tv_amount);
        mTvYear1 = mViewHead1.findViewById(R.id.tv_year);
        mTvYear2 = mViewHead2.findViewById(R.id.tv_year);
        mLlYearSelect1 = mViewHead1.findViewById(R.id.ll_year);
        mLlYearSelect2 = mViewHead2.findViewById(R.id.ll_year);

        TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId());
        TLRPC.FileLocation photo = null;
        TLRPC.FileLocation photoBig = null;
        if (user != null && user.photo != null) {
            photo = user.photo.photo_small;
            photoBig = user.photo.photo_big;
        }
        AvatarDrawable avatarDrawable = new AvatarDrawable(user, true);
        avatarDrawable.setColor(Theme.getColor(Theme.key_avatar_backgroundInProfileBlue));
        mImgAvatar1.setRoundRadius(AndroidUtilities.dp(200));
        mImgAvatar2.setRoundRadius(AndroidUtilities.dp(200));
        if (mImgAvatar1 != null) {
            mImgAvatar1.setImage(photo, "50_50", avatarDrawable);
            mImgAvatar1.getImageReceiver().setVisible(!PhotoViewer.isShowingImage(photoBig), false);
        }
        if (mImgAvatar2 != null) {
            mImgAvatar2.setImage(photo, "50_50", avatarDrawable);
            mImgAvatar2.getImageReceiver().setVisible(!PhotoViewer.isShowingImage(photoBig), false);
        }
        mTvName1.setText(getString(R.string.red_who1).replace("xxx", UserObject.getUserName(user)));
        mTvName2.setText(getString(R.string.red_who2).replace("xxx", UserObject.getUserName(user)));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());
        mNowYear1 = now.split("-")[0];
        mNowYear2 = now.split("-")[0];
        mTvYear1.setText(mNowYear1);
        mTvYear2.setText(mNowYear2);
        getRedInList();
        getRedOutList();

        mTvRedIn.setOnClickListener(this);
        mTvRedOut.setOnClickListener(this);

        mPageList = new ArrayList<View>();
        mPageList.add(mView1);
        mPageList.add(mView2);

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mPageList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mPageList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mPageList.get(position));
                return mPageList.get(position);
            }
        };

        //绑定适配器
        mViewPage.setAdapter(pagerAdapter);
        //设置viewPager的初始界面为第1个界面
        mViewPage.setCurrentItem(0);
        //添加切换界面的监听器
        mViewPage.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.FIND_REDBAG_RECEIVE_LIST, new Action1<RedBagFind1>() {
            @Override
            public void call(RedBagFind1 o) {
                redInList = o.getRedbagList();
                redPacketInView();
                mCustomDatePicker1 = new CustomDatePicker1(RedPacketRecordActivity.this, new CustomDatePicker1.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        mNowYear1 = time.split("-")[0];
                        mTvYear1.setText(mNowYear1);
                        selectYearRedIn(mNowYear1);
                    }
                }, "1900-01-01 00:00", now);
                mCustomDatePicker1.showSpecificTime(false);
                mCustomDatePicker1.setIsLoop(false);
                mLlYearSelect1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCustomDatePicker1.show(mNowYear1);
                    }
                });
            }
        });

        rxManage.on(Event.FIND_REDBAG_LIST, new Action1<RedBagFind2>() {
            @Override
            public void call(RedBagFind2 o) {
                redOutList = o.getRedbagList();
                redPacketOutView();
                mCustomDatePicker2 = new CustomDatePicker1(RedPacketRecordActivity.this, new CustomDatePicker1.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        mNowYear2 = time.split("-")[0];
                        mTvYear2.setText(mNowYear2);
                        selectYearRedOut(mNowYear2);
                    }
                }, "1900-01-01 00:00", now);
                mCustomDatePicker2.showSpecificTime(false);
                mCustomDatePicker2.setIsLoop(false);
                mLlYearSelect2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCustomDatePicker2.show(mNowYear2);
                    }
                });
            }
        });
    }

    private void selectYearRedIn(String year) {
        List<RecieveBagIn> needList = new ArrayList<>();
        for (int i = 0; i < redInList.size(); i ++) {
            if (redInList.get(i).getCreateDate().startsWith(year))
                needList.add(redInList.get(i));
        }
        mTvNumber1.setText(needList.size() + "");
        double amount = 0;
        for (int d = 0; d < needList.size(); d ++) {
            amount = amount + needList.get(d).getAmount();
        }
        String coinName = "";
        switch (mCoinType) {
            case "2":
                coinName = " TSC";
                break;
            case "10":
                coinName = " T5C-T";
                break;
            case "11":
                coinName = " NTT";
                break;
        }
        mTvAmount1.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(amount, 6))) + coinName);
        showRedInList(needList);
    }

    private void selectYearRedOut(String year) {
        List<RecieveBagOut> needList = new ArrayList<>();
        for (int i = 0; i < redOutList.size(); i ++) {
            if (redOutList.get(i).getCreateDate().startsWith(year))
                needList.add(redOutList.get(i));
        }
        mTvNumber2.setText(needList.size() + "");
        double amount = 0;
        for (int d = 0; d < needList.size(); d ++) {
            amount = amount + needList.get(d).getTotalMoney();
        }
        String coinName = "";
        switch (mCoinType) {
            case "2":
                coinName = " TSC";
                break;
            case "10":
                coinName = " T5C-T";
                break;
            case "11":
                coinName = " NTT";
                break;
        }
        mTvAmount2.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(amount, 6))) + coinName);
        showRedOutList(needList);
    }

    private void redPacketInView() {
        mListView1 = mView1.findViewById(R.id.gv_red_in_list);
        mTvNoData1 = mView1.findViewById(R.id.ll_no_data);
        if (mListView1.getHeaderViewsCount() == 0)
            mListView1.addHeaderView(mViewHead1);
        selectYearRedIn(mNowYear1);
    }

    private void redPacketOutView() {
        mListView2 = mView2.findViewById(R.id.gv_red_out_list);
        mTvNoData2 = mView2.findViewById(R.id.ll_no_data);
        if (mListView2.getHeaderViewsCount() == 0)
            mListView2.addHeaderView(mViewHead2);
        selectYearRedOut(mNowYear2);
    }

    private void getRedInList() {
        mPb.setVisibility(View.VISIBLE);
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "coinType=" + mCoinType + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        TSCApi.getInstance().findRedbagReceiveList(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign, mCoinType);
    }

    private void getRedOutList() {
        mPb.setVisibility(View.VISIBLE);
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "coinType=" + mCoinType + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        TSCApi.getInstance().findRedbagList(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign, mCoinType);
    }

    private void showRedInList(List<RecieveBagIn> list) {
        mPb.setVisibility(View.GONE);
        if (null != list && list.size() > 0)
            mTvNoData1.setVisibility(View.GONE);
        else
            mTvNoData1.setVisibility(View.VISIBLE);
//        if (null == mAdapter1) {
            mAdapter1 = new RedInAdapter(this, list, mCoinType);
            mListView1.setAdapter(mAdapter1);
//        } else {
//            mAdapter1.setItems(list);
//        }
    }

    private void showRedOutList(List<RecieveBagOut> list) {
        mPb.setVisibility(View.GONE);
        if (null != list && list.size() > 0)
            mTvNoData2.setVisibility(View.GONE);
        else
            mTvNoData2.setVisibility(View.VISIBLE);
//        if (null == mAdapter2) {
            mAdapter2 = new RedOutAdapter(this, list, mCoinType);
            mListView2.setAdapter(mAdapter2);
//        } else {
//            mAdapter2.setItems(list);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_red_in:
                mViewPage.setCurrentItem(0);
                styleChange(0);
                break;
            case R.id.tv_red_out:
                mViewPage.setCurrentItem(1);
                styleChange(1);
                break;
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int index) {
            styleChange(index);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void styleChange(int index) {
        mCurrentSelect = index;
        if (index == 0) {
            mTvRedIn.setBackgroundColor(getResources().getColor(R.color.cRed));
            mTvRedIn.setTextColor(getResources().getColor(R.color.cEECC90));
            mTvRedOut.setBackgroundColor(getResources().getColor(R.color.white));
            mTvRedOut.setTextColor(getResources().getColor(R.color.c33));
        } else if (index == 1) {
            mTvRedIn.setBackgroundColor(getResources().getColor(R.color.white));
            mTvRedIn.setTextColor(getResources().getColor(R.color.c33));
            mTvRedOut.setBackgroundColor(getResources().getColor(R.color.cRed));
            mTvRedOut.setTextColor(getResources().getColor(R.color.cEECC90));
        }
    }

    @OnClick({R.id.img_back, R.id.rl_title, R.id.tv_other})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_title:
                if (mCurrentSelect == 0)
                    mListView1.smoothScrollToPosition(0);
                else
                    mListView2.smoothScrollToPosition(0);
                break;
            case R.id.tv_other:
                DialogUtil.selectCoinDialog(this, new DialogUtil.OnResultListener4() {
                    @Override
                    public void select1() {
                        mCoinType = "2";
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                        now = sdf.format(new Date());
                        mNowYear1 = now.split("-")[0];
                        mNowYear2 = now.split("-")[0];
                        mTvYear1.setText(mNowYear1);
                        mTvYear2.setText(mNowYear2);
                        getRedInList();
                        getRedOutList();
                    }

                    @Override
                    public void select2() {
                        mCoinType = "10";
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                        now = sdf.format(new Date());
                        mNowYear1 = now.split("-")[0];
                        mNowYear2 = now.split("-")[0];
                        mTvYear1.setText(mNowYear1);
                        mTvYear2.setText(mNowYear2);
                        getRedInList();
                        getRedOutList();
                    }

                    @Override
                    public void select3() {
                        mCoinType = "11";
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                        now = sdf.format(new Date());
                        mNowYear1 = now.split("-")[0];
                        mNowYear2 = now.split("-")[0];
                        mTvYear1.setText(mNowYear1);
                        mTvYear2.setText(mNowYear2);
                        getRedInList();
                        getRedOutList();
                    }
                });
                break;
        }
    }

}
