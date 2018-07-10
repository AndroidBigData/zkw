package com.zjwam.zkw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ClassInfo;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;



public class SearchListAdapter extends ListBaseAdapter<ClassInfo> {

    private LayoutInflater mLayoutInflater;
    private RequestOptions options;

    public SearchListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        options = RequestOptionsUtils.roundTransform(10);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.class_search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ClassInfo item = mDataList.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.class_name.setText(item.getName());
        viewHolder.class_label.setText(item.getAbstracts());
        viewHolder.class_rating.setNumStars(item.getStar());
        viewHolder.class_learn.setText(item.getNum()+"课时  " + item.getSnum() + "人已学习");
        viewHolder.class_type.setText(item.getType());
        GlideImageUtil.setImageView(mContext,item.getImg(),viewHolder.class_img,options);
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  class_name, class_label,class_learn, class_type;
        private RatingBar class_rating;
        private ImageView class_img;
        public ViewHolder(View itemView) {
            super(itemView);
            class_img = itemView.findViewById(R.id.class_img);
            class_name = itemView.findViewById(R.id.class_name);
            class_label = itemView.findViewById(R.id.class_label);
            class_rating = itemView.findViewById(R.id.class_rating);
            class_learn = itemView.findViewById(R.id.class_learn);
            class_type = itemView.findViewById(R.id.class_type);

        }
    }


}
