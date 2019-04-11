package com.ebei.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ebei.library.CommonVH;
import com.ebei.library.Util;
import com.ebei.pojo.RecieveBagOut;
import com.ebei.tsc.R;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 发出红包适配器
 */

public class RedOutAdapter extends CommonListViewAdapter<RecieveBagOut> {

    private static final String TAG = "RedOutAdapter";
    private Activity mActivity;
    private String mCoinType;
    public RedOutAdapter(Activity activity, List<RecieveBagOut> datas, String type) {
        super(activity, datas);
        this.mActivity = activity;
        this.mCoinType = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_red, position);

        TextView name = holder.getView(R.id.tv_name);
        TextView time = holder.getView(R.id.tv_time);
        TextView count = holder.getView(R.id.tv_count);

        RecieveBagOut redOut = mDatas.get(position);

        time.setText(redOut.getCreateDate());
        switch (redOut.getType()) {
            case 1:
                name.setText("普通红包");
                break;
            case 2:
                name.setText("拼手气红包");
                break;
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
        count.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(redOut.getTotalMoney(), 6))) + coinName);

        return holder.getConvertView();
    }

}
