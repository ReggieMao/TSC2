package com.ebei.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ebei.library.CommonVH;
import com.ebei.library.Util;
import com.ebei.pojo.RecieveBag;
import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.R;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.UserObject;
import com.ebei.ui.Components.BackupImageView;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 红包领取记录适配器
 */

public class RedReceiveAdapter extends CommonListViewAdapter<RecieveBag> {

    private static final String TAG = "RedReceiveAdapter";
    private Activity mActivity;
    private String mCoinName;
    public RedReceiveAdapter(Activity activity, List<RecieveBag> datas, String name) {
        super(activity, datas);
        this.mActivity = activity;
        this.mCoinName = name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_red_receive, position);

        TextView name = holder.getView(R.id.tv_name);
        TextView time = holder.getView(R.id.tv_time);
        TextView count = holder.getView(R.id.tv_count);
        BackupImageView avatar = holder.getView(R.id.img_avatar);

        RecieveBag redReceive = mDatas.get(position);

        time.setText(redReceive.getCreateDate());
        if (!Util.isEmpty(redReceive.getOpenId())) {
            TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Integer.parseInt(redReceive.getOpenId()));
            name.setText(UserObject.getUserName(user));
            Util.setTeleInfo(redReceive.getOpenId(), avatar);
        }
        count.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(redReceive.getAmount(), 6))) + mCoinName);

        return holder.getConvertView();
    }

}
