package com.zjwam.zkw.adapter;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;

public class TeacherMoreRYAdapter extends ListBaseAdapter<String> {
    private LayoutInflater mLayoutInflater;
    private RequestOptions option;

    public TeacherMoreRYAdapter(Context context, @Nullable String type) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        if (type != null){
            option = RequestOptions.circleCropTransform();
        }else {
            option = RequestOptionsUtils.roundTransform(10);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.teacher_more_rongyu_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        String data = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        GlideImageUtil.setImageView(mContext,data,viewHolder.more_img_item,option);
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView more_img_item;
        public ViewHolder(View itemView) {
            super(itemView);
            more_img_item = itemView.findViewById(R.id.more_img_item);
        }
    }
}
