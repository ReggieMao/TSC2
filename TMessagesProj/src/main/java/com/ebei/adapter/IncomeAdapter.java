package com.ebei.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ebei.library.CommonVH;
import com.ebei.library.Util;
import com.ebei.tsc.R;
import com.ebei.pojo.Miner;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 收益明细适配器
 */

public class IncomeAdapter extends CommonListViewAdapter<Miner> {

    private static final String TAG = "IncomeAdapter";
    private Activity mActivity;
    public IncomeAdapter(Activity activity, List<Miner> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_income, position);

        TextView time = holder.getView(R.id.tv_time);
        TextView balance = holder.getView(R.id.tv_balance);
        TextView type = holder.getView(R.id.tv_type);
        TextView state = holder.getView(R.id.tv_state);

        Miner income = mDatas.get(position);

        if (income.getChangeValue() > 0)
            balance.setText("+" + Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(income.getChangeValue(), 6))));
        else
            balance.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(income.getChangeValue(), 6))));
        time.setText(income.getCreateDate());
        switch (income.getWithdrawInfStatus()) {
            case -2:
                type.setText(mActivity.getString(R.string.miner_get));
                break;
            case -1:
                type.setText(mActivity.getString(R.string.in_miner));
                break;
            case 0:
            case 1:
            case 2:
                type.setText(mActivity.getString(R.string.out_miner));
                break;
        }
        switch (income.getWithdrawInfStatus()) {
            case -2:
            case -1:
                state.setText("");
                break;
            case 0:
            case 1:
                state.setText(mActivity.getString(R.string.verifying));
                state.setTextColor(mActivity.getResources().getColor(R.color.textBlack1));
                break;
            case 2:
                state.setText(mActivity.getString(R.string.has_done));
                state.setTextColor(mActivity.getResources().getColor(R.color.textBlue));
                break;
        }
//        switch (income.getWithdrawInfStatus()) {
//            case -2:
//            case -1:
//                view.setBackgroundResource(R.drawable.bg_round_text_blue);
//                break;
//            case 0:
//            case 1:
//            case 2:
//                view.setBackgroundResource(R.drawable.bg_round_text_green);
//                break;
//        }

        return holder.getConvertView();
    }

}
