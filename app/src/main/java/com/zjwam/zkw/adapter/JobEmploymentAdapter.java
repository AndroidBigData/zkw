package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.JobEmplomentBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;

public class JobEmploymentAdapter extends ListBaseAdapter<JobEmplomentBean.Guide> {
    private LayoutInflater mLayoutInflater;
    private RequestOptions options;

    public JobEmploymentAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        options = RequestOptionsUtils.roundTransform(10);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.employment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        JobEmplomentBean.Guide item = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.employment_item_title.setText(item.getTitle());
        viewHolder.employment_item_time.setText(item.getCreate_time());
        viewHolder.employment_item_from.setText(" "+item.getCompany());
        viewHolder.employment_item_num.setText(" "+item.getPv());
        if (item.getTitle_img().length()>0){
            GlideImageUtil.setImageView(mContext,item.getTitle_img(),viewHolder.employment_item_img,options);
        }else {
            Glide.with(mContext)
                    .load(R.drawable.basic_news)
                    .apply(options)
                    .into(viewHolder.employment_item_img);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView employment_item_title, employment_item_time,employment_item_num,employment_item_from;
        private ImageView employment_item_img;
        public ViewHolder(View itemView) {
            super(itemView);
            employment_item_title = itemView.findViewById(R.id.employment_item_title);
            employment_item_time = itemView.findViewById(R.id.employment_item_time);
            employment_item_num = itemView.findViewById(R.id.employment_item_num);
            employment_item_from = itemView.findViewById(R.id.employment_item_from);
            employment_item_img = itemView.findViewById(R.id.employment_item_img);
        }
    }
}
