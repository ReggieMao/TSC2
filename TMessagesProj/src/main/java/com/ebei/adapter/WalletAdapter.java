package com.ebei.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebei.library.CommonVH;
import com.ebei.library.Util;
import com.ebei.pojo.Wallet;
import com.ebei.tsc.R;
import com.ebei.ui.MyTSCActivity;
import com.ebei.ui.TSCMineralActivity;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 钱包适配器
 */

public class WalletAdapter extends CommonListViewAdapter<Wallet> {

    private static final String TAG = "WalletAdapter";
    private Activity mActivity;
    private int mWhere;
    public WalletAdapter(Activity activity, List<Wallet> datas, int where) {
        super(activity, datas);
        this.mActivity = activity;
        this.mWhere = where;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_wallet, position);

        LinearLayout layout = holder.getView(R.id.layout);
        TextView coinName = holder.getView(R.id.tv_coin_name);
        TextView count = holder.getView(R.id.tv_count);
        ImageView logo = holder.getView(R.id.img_logo);

        Wallet wallet = mDatas.get(position);

        switch (wallet.getUserWalletType()) {
            case "2":
                logo.setImageResource(R.mipmap.coin_tsc);
                break;
            case "10":
                logo.setImageResource(R.mipmap.coin_t5c);
                break;
            case "11":
                logo.setImageResource(R.mipmap.coin_ntt);
                break;
        }
        coinName.setText(wallet.getCoinName());
        count.setText(Util.eliminateZero(Double.parseDouble(Util.getScientificCountingMethodAfterData(wallet.getBalance(), 6))));
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWhere == 0) {
                    Intent intent = new Intent(mActivity, MyTSCActivity.class);
                    intent.putExtra(MyTSCActivity.COIN_NAME, wallet.getCoinName());
                    intent.putExtra(MyTSCActivity.COIN_TYPE, wallet.getUserWalletType());
                    mActivity.startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, TSCMineralActivity.class);
                    intent.putExtra(MyTSCActivity.COIN_NAME, wallet.getCoinName());
                    intent.putExtra(MyTSCActivity.COIN_TYPE, wallet.getUserWalletType());
                    mActivity.startActivity(intent);
                }
            }
        });

        return holder.getConvertView();
    }

}
