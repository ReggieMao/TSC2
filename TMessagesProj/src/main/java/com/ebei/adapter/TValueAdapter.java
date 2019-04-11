package com.ebei.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ebei.library.CommonVH;
import com.ebei.tsc.R;
import com.ebei.pojo.TValue;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * T值适配器
 */

public class TValueAdapter extends CommonListViewAdapter<TValue> {

    private static final String TAG = "TValueAdapter";
    private Activity mActivity;
    public TValueAdapter(Activity activity, List<TValue> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_t_value, position);

        TextView type = holder.getView(R.id.tv_type);
        TextView time = holder.getView(R.id.tv_time);
        TextView value = holder.getView(R.id.tv_value);

        TValue tValue = mDatas.get(position);

        if (tValue.getTType() == 1)
            type.setText(mActivity.getString(R.string.t_value_type1));
        else
            type.setText(mActivity.getString(R.string.t_value_type2));
        time.setText(tValue.getBelongDate().substring(0, 10));
        value.setText("+" + tValue.getTValue());

        return holder.getConvertView();
    }

}
