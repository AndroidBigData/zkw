package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.PersonalMineCommentBean;

public class PersonalMineCommentAdapter extends ListBaseAdapter<PersonalMineCommentBean.getCommentItems> {
    private LayoutInflater mLayoutInflater;

    public PersonalMineCommentAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.mine_comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        PersonalMineCommentBean.getCommentItems item = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mine_comment_title.setText(item.getName());
        viewHolder.mine_comment_rating.setNumStars(item.getStar());
        viewHolder.mine_comment_time.setText(item.getAddtime());
        viewHolder.mine_comment_content.setText(item.getContent());
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mine_comment_title, mine_comment_time,mine_comment_content;
        private RatingBar mine_comment_rating;
        public ViewHolder(View itemView) {
            super(itemView);
            mine_comment_title = itemView.findViewById(R.id.mine_comment_title);
            mine_comment_time = itemView.findViewById(R.id.mine_comment_time);
            mine_comment_content = itemView.findViewById(R.id.mine_comment_content);
            mine_comment_rating = itemView.findViewById(R.id.mine_comment_rating);
        }
    }
}
