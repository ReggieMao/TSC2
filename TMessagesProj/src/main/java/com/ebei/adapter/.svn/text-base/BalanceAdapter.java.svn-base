package com.ebei.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebei.library.CommonVH;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.Balance;
import com.ebei.tsc.R;
import com.ebei.ui.RemainInfoActivity;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 余额明细适配器
 */

public class BalanceAdapter extends CommonListViewAdapter<Balance> {

    private static final String TAG = "BalanceAdapter";
    private Activity mActivity;
    public BalanceAdapter(Activity activity, List<Balance> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_balance, position);

        LinearLayout layout = holder.getView(R.id.layout);
        TextView outIn = holder.getView(R.id.tv_out_in);
        TextView time = holder.getView(R.id.tv_time);
        TextView remain = holder.getView(R.id.tv_balance);

        Balance balance = mDatas.get(position);

        if (!balance.getReceiveAddress().equals(UserPreference.getString(UserPreference.ADDRESS, ""))) {
            remain.setText("-" + Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(balance.getChangeValue(), 6))));
            remain.setTextColor(mActivity.getResources().getColor(R.color.cE0));
        } else {
            remain.setText("+" + Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(balance.getChangeValue(), 6))));
            remain.setTextColor(mActivity.getResources().getColor(R.color.c6E));
        }
        outIn.setText(balance.getChangeAction());
        time.setText(balance.getUpdateDate());
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, RemainInfoActivity.class);
                intent.putExtra(RemainInfoActivity.BALANCE, balance);
                mActivity.startActivity(intent);
            }
        });

        return holder.getConvertView();
    }

}
