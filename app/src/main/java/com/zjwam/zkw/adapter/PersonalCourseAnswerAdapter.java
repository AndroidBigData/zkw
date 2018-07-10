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
import com.zjwam.zkw.customview.CustomImageView;
import com.zjwam.zkw.entity.PersonalMineAnswerBean;
import com.zjwam.zkw.entity.PersonalMineAskBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;



public class PersonalCourseAnswerAdapter extends ListBaseAdapter<PersonalMineAnswerBean.getAnswerItems> {
    private LayoutInflater mLayoutInflater;
    private RequestOptions options;

    public PersonalCourseAnswerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        options = RequestOptionsUtils.circleTransform();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.mine_answer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final PersonalMineAnswerBean.getAnswerItems data = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        GlideImageUtil.setImageView(mContext,data.getPic(),viewHolder.mine_answer_img,options);
        viewHolder.mine_answer_nickname.setText(data.getNickname());
        viewHolder.mine_answer_time.setText(data.getAddtime());
        viewHolder.mine_answer_title.setText(data.getName());
        viewHolder.mine_answer_content.setText(data.getContent());
        viewHolder.mine_answer_content_nickname.setText(data.getQuestion().getNickname());
        viewHolder.mine_answer_content_time.setText("发布于" + data.getQuestion().getAddtime());
        viewHolder.mine_answer_content_content.setText(data.getQuestion().getContent());
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mine_answer_img;
        private TextView mine_answer_nickname, mine_answer_time, mine_answer_title, mine_answer_content_nickname, mine_answer_content_time, mine_answer_content_content, mine_answer_content;

        public ViewHolder(View itemView) {
            super(itemView);
            mine_answer_img = itemView.findViewById(R.id.mine_answer_img);
            mine_answer_nickname = itemView.findViewById(R.id.mine_answer_nickname);
            mine_answer_time = itemView.findViewById(R.id.mine_answer_time);
            mine_answer_title = itemView.findViewById(R.id.mine_answer_title);
            mine_answer_content_nickname = itemView.findViewById(R.id.mine_answer_content_nickname);
            mine_answer_content_time = itemView.findViewById(R.id.mine_answer_content_time);
            mine_answer_content_content = itemView.findViewById(R.id.mine_answer_content_content);
            mine_answer_content = itemView.findViewById(R.id.mine_answer_content);
        }
    }
}
