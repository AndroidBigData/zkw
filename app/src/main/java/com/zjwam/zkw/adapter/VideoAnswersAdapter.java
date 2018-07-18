package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.VideoAnswersBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;

public class VideoAnswersAdapter extends ListBaseAdapter<VideoAnswersBean.getAnswers> {
    private LayoutInflater mLayoutInflater;
    private RequestOptions options;

    public VideoAnswersAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        options = RequestOptionsUtils.circleTransform();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.video_answer_ask_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        VideoAnswersBean.getAnswers  items = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.video_ask_nickname.setText(items.getNick());
        viewHolder.video_ask_time.setText(items.getAddtime());
        viewHolder.video_ask_content.setText(items.getContent());
        GlideImageUtil.setImageView(mContext,items.getPic(),viewHolder.video_ask_img,options);
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView video_ask_nickname,video_ask_time,video_ask_content;
        private ImageView video_ask_img;

        public ViewHolder(View itemView) {
            super(itemView);
            video_ask_nickname = itemView.findViewById(R.id.video_ask_nickname);
            video_ask_time = itemView.findViewById(R.id.video_ask_time);
            video_ask_content = itemView.findViewById(R.id.video_ask_content);
            video_ask_img = itemView.findViewById(R.id.video_ask_img);
        }
    }
}
