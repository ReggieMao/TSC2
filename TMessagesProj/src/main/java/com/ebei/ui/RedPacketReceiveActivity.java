package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebei.adapter.RedReceiveAdapter;
import com.ebei.library.AutoPullAbleGridView;
import com.ebei.library.BaseActivity;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.RecieveBag;
import com.ebei.pojo.RedBagDetail;
import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.R;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.UserObject;
import com.ebei.ui.Components.BackupImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/11/22.
 * 红包领取页面
 */

public class RedPacketReceiveActivity extends BaseActivity {

    private static final String TAG = "RedPacketReceiveActivity";
    public static String RED_ID = "red_id";
    public static String RED_CONTENT = "red_content";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.gv_red_list)
    AutoPullAbleGridView mListView;
    @Bind(R.id.ll_no_data)
    LinearLayout mLlNoData;
    @Bind(R.id.pb)
    ProgressBar mPb;
    private RedReceiveAdapter mAdapter;
    private TextView name;
    private TextView name1;
    private TextView amount;
    private TextView money;
    private TextView toWallet;
    private BackupImageView avatar;
    private BackupImageView avatar1;
    private RelativeLayout layout1;
    private RelativeLayout layout2;
    private String mCoinName;
    private String mCoinType;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_red_packet_receive;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);

        LayoutInflater inflater = getLayoutInflater();
        View headView = inflater.inflate(R.layout.view_red_receive_head, null);
        layout1 = headView.findViewById(R.id.rl1);
        layout2 = headView.findViewById(R.id.rl2);
        avatar = headView.findViewById(R.id.img_avatar);
        avatar1 = headView.findViewById(R.id.img_avatar1);
        name = headView.findViewById(R.id.tv_name);
        amount = headView.findViewById(R.id.tv_amount);
        name1 = headView.findViewById(R.id.tv_name1);
        money = headView.findViewById(R.id.tv_count);
        TextView content = headView.findViewById(R.id.tv_content);
        TextView content1 = headView.findViewById(R.id.tv_content1);
        toWallet = headView.findViewById(R.id.tv_tip);
        content.setText(getIntent().getStringExtra(RED_CONTENT));
        content1.setText(getIntent().getStringExtra(RED_CONTENT));

        mListView.addHeaderView(headView);
        getRedReceiveList();
        mPb.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.GET_REDBAG_DETAILS, new Action1<RedBagDetail>() {
            @Override
            public void call(RedBagDetail o) {
                mPb.setVisibility(View.GONE);
                String coinName = "";
                switch (o.getRedBag().getCoinType()) {
                    case 2:
                        coinName = "TSC";
                        break;
                    case 10:
                        coinName = "T5C-T";
                        break;
                    case 11:
                        coinName = "NTT";
                        break;
                }
                String coinName1 = "";
                switch (o.getRedBag().getCoinType()) {
                    case 2:
                        coinName1 = " TSC";
                        break;
                    case 10:
                        coinName1 = " T5C-T";
                        break;
                    case 11:
                        coinName1 = " NTT";
                        break;
                }
                mTvTitle.setText(getString(R.string.red_tsc).replace("xxx", coinName));
                mCoinName = coinName1;
                if (!Util.isEmpty(o.getRedBag().getOpenId())) {
                    Util.setTeleInfo(o.getRedBag().getOpenId(), avatar);
                    Util.setTeleInfo(o.getRedBag().getOpenId(), avatar1);
                    TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Integer.parseInt(o.getRedBag().getOpenId()));
                    name.setText(getString(R.string.red_who).replace("xxx", UserObject.getUserName(user)));
                    name1.setText(getString(R.string.red_who).replace("xxx", UserObject.getUserName(user)));
                }
                amount.setText(getString(R.string.red_number).replace("xxx", o.getRedBag().getNumber() + "") + getString(R.string.red_rest).replace("xxx", o.getRedBag().getRestOfNumber() + ""));
                if (o.getCurrentUser() == null) {
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                } else {
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.GONE);
                    money.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(o.getCurrentUser().getAmount(), 6))) + coinName1);
                }
                mCoinType = o.getRedBag().getCoinType() + "";
                toWallet.setText(getString(R.string.red_tip1).replace("xxx", coinName));
                toWallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RedPacketReceiveActivity.this, MyTSCActivity.class);
                        if (o.getRedBag().getCoinType() == 2) {
                            intent.putExtra(MyTSCActivity.COIN_NAME, "TSC");
                            intent.putExtra(MyTSCActivity.COIN_TYPE, "2");
                        } else if (o.getRedBag().getCoinType() == 10){
                            intent.putExtra(MyTSCActivity.COIN_NAME, "T5C-T");
                            intent.putExtra(MyTSCActivity.COIN_TYPE, "10");
                        } else if (o.getRedBag().getCoinType() == 11){
                            intent.putExtra(MyTSCActivity.COIN_NAME, "NTT");
                            intent.putExtra(MyTSCActivity.COIN_TYPE, "11");
                        }
                        startActivity(intent);
                    }
                });
                showRedReceiveList(o.getRecieveBagList());
            }
        });
    }

    private void getRedReceiveList() {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "redbagId=" + getIntent().getStringExtra(RED_ID) + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        TSCApi.getInstance().getRedBagDetails(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign, getIntent().getStringExtra(RED_ID));
    }

    private void showRedReceiveList(List<RecieveBag> list) {
        if (null != list && list.size() > 0)
            mLlNoData.setVisibility(View.GONE);
        else
            mLlNoData.setVisibility(View.VISIBLE);
        if (null == mAdapter) {
            mAdapter = new RedReceiveAdapter(this, list, mCoinName);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(list);
        }
    }

    @OnClick({R.id.img_back, R.id.tv_record})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_record:
                Intent intent = new Intent(this, RedPacketRecordActivity.class);
                intent.putExtra(RedPacketRecordActivity.COIN_TYPE, mCoinType);
                startActivity(intent);
                break;
        }
    }

}
