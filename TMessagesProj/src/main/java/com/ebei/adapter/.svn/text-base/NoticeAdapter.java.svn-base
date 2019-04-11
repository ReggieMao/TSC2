package com.ebei.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebei.library.CommonVH;
import com.ebei.pojo.Notice;
import com.ebei.tsc.R;
import com.ebei.ui.NoticeInfoActivity;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 公告适配器
 */

public class NoticeAdapter extends CommonListViewAdapter<Notice> {

    private static final String TAG = "NoticeAdapter";
    private Activity mActivity;
    public NoticeAdapter(Activity activity, List<Notice> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_system_notice, position);

        LinearLayout layout = holder.getView(R.id.layout);
        TextView tvTitle = holder.getView(R.id.tv_content);
        TextView tvTime = holder.getView(R.id.tv_time);
        ImageView point = holder.getView(R.id.img_point);

        Notice notice = mDatas.get(position);

        tvTime.setText(notice.getBeginTime());
        tvTitle.setText(notice.getNoticeTitle());
        if (notice.isRead())
            point.setVisibility(View.GONE);
        else
            point.setVisibility(View.VISIBLE);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, NoticeInfoActivity.class);
                intent.putExtra(NoticeInfoActivity.URL, notice.getNoticeUrl());
                mActivity.startActivity(intent);
            }
        });

        return holder.getConvertView();
    }

}
