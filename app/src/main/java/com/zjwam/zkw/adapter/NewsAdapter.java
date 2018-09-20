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
import com.zjwam.zkw.entity.NewsBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;

public class NewsAdapter extends ListBaseAdapter<NewsBean>{
    private LayoutInflater mLayoutInflater;
    private RequestOptions options;

    public NewsAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        options = RequestOptionsUtils.roundTransform(7);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.news_basic_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        NewsBean  items = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.news_item_title.setText(items.getTitle());
        viewHolder.news_item_time.setText(items.getCreate_time());
        if (items.getTitle_img().length()>0){
            GlideImageUtil.setImageView(mContext,items.getTitle_img(),viewHolder.news_item_img,options);
        }else {
            Glide.with(mContext)
                    .load(R.drawable.basic_news)
                    .apply(options)
                    .into(viewHolder.news_item_img);
        }
    }
    private class ViewHolder extends RecyclerView.ViewHolder{

        private TextView news_item_title,news_item_time;
        private ImageView news_item_img;

        public ViewHolder(View itemView) {
            super(itemView);
            news_item_title = itemView.findViewById(R.id.news_item_title);
            news_item_time = itemView.findViewById(R.id.news_item_time);
            news_item_img = itemView.findViewById(R.id.news_item_img);
        }
    }
}
