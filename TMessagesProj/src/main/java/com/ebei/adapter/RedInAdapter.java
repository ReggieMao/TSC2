package com.ebei.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ebei.library.CommonVH;
import com.ebei.library.Util;
import com.ebei.pojo.RecieveBagIn;
import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.R;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.UserObject;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 收到红包适配器
 */

public class RedInAdapter extends CommonListViewAdapter<RecieveBagIn> {

    private static final String TAG = "RedInAdapter";
    private Activity mActivity;
    private String mCoinType;
    public RedInAdapter(Activity activity, List<RecieveBagIn> datas, String type) {
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

        RecieveBagIn redIn = mDatas.get(position);

        if (!Util.isEmpty(redIn.getOpenId())) {
            TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Integer.parseInt(redIn.getOpenId()));
            name.setText(UserObject.getUserName(user));
        }
        time.setText(redIn.getCreateDate());
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
        count.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(redIn.getAmount(), 6))) + coinName);

        return holder.getConvertView();
    }

}
