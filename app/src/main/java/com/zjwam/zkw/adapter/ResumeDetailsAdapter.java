package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ResumeDetailsBean;

public class ResumeDetailsAdapter extends ListBaseAdapter<ResumeDetailsBean.Other> {
    private LayoutInflater mLayoutInflater;

    public ResumeDetailsAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.resume_details_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ResumeDetailsBean.Other item = mDataList.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.resume_type.setText(item.getType_msg());
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView resume_type;
        public ViewHolder(View itemView) {
            super(itemView);
            resume_type = itemView.findViewById(R.id.resume_type);
        }
    }
}
