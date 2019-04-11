package com.ebei.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ebei.library.CommonVH;
import com.ebei.library.Util;
import com.ebei.pojo.FotaRecord;
import com.ebei.tsc.R;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 交易记录适配器
 */

public class RecordAdapter extends CommonListViewAdapter<FotaRecord> {

    private static final String TAG = "RecordAdapter";
    private Activity mActivity;
    public RecordAdapter(Activity activity, List<FotaRecord> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_digital_record, position);

        TextView time = holder.getView(R.id.tv_time);
        TextView asset = holder.getView(R.id.tv_asset);
        TextView touZi = holder.getView(R.id.tv_touzi);
        TextView shouYi = holder.getView(R.id.tv_shouyi);

        FotaRecord record = mDatas.get(position);
        time.setText(Util.stampToDate(record.getSettlementTime()));
        asset.setText(record.getAsset());
        touZi.setText(record.getPrice());
        if (!record.getProfit().startsWith("-")) {
            shouYi.setText("+" + record.getProfit());
            shouYi.setTextColor(mActivity.getResources().getColor(R.color.c6E));
        } else {
            shouYi.setText(record.getProfit());
            shouYi.setTextColor(mActivity.getResources().getColor(R.color.cE0));
        }
        if (position == 0) {
            time.setVisibility(View.VISIBLE);
        } else {
            if ((Util.stampToDate(mDatas.get(position).getSettlementTime())).equals(Util.stampToDate(mDatas.get(position - 1).getSettlementTime()))) {
                time.setVisibility(View.GONE);
            } else {
                time.setVisibility(View.VISIBLE);
            }
        }

        return holder.getConvertView();
    }

}
