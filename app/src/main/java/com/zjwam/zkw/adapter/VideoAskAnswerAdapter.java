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
import com.zjwam.zkw.entity.VideoAskAnswerBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;

public class VideoAskAnswerAdapter extends ListBaseAdapter<VideoAskAnswerBean.getVideoAnswerItems> {
    private LayoutInflater mLayoutInflater;
    private RequestOptions options;

    public VideoAskAnswerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        options = RequestOptionsUtils.circleTransform();
}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.video_answer_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        VideoAskAnswerBean.getVideoAnswerItems  items = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.video_answer_nickname.setText(items.getNick());
        viewHolder.video_answer_time.setText(items.getAddtime());
        viewHolder.video_answer_content.setText(items.getContent());
        viewHolder.video_ask_count.setText(String.valueOf(items.getAnswer()));
        GlideImageUtil.setImageView(mContext,items.getPic(),viewHolder.video_answer_img,options);
    }
    private class ViewHolder extends RecyclerView.ViewHolder{

        private TextView video_answer_nickname,video_answer_time,video_answer_content,video_ask_count;
        private ImageView video_answer_img;

        public ViewHolder(View itemView) {
            super(itemView);
            video_answer_nickname = itemView.findViewById(R.id.video_answer_nickname);
            video_answer_time = itemView.findViewById(R.id.video_answer_time);
            video_answer_content = itemView.findViewById(R.id.video_answer_content);
            video_ask_count = itemView.findViewById(R.id.video_ask_count);
            video_answer_img = itemView.findViewById(R.id.video_answer_img);
        }
    }
}
