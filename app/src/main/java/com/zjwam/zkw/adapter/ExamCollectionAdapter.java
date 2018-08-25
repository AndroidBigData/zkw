package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.CollectionExamBean;

public class ExamCollectionAdapter extends ListBaseAdapter<CollectionExamBean.Hold> {
    private LayoutInflater mLayoutInflater;

    public ExamCollectionAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.collection_exam_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        CollectionExamBean.Hold item = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.exam_collection_title.setText(item.getName());
        viewHolder.exam_collection__fl.setText("分类:"+item.getCate());
        viewHolder.exam_record_item_time.setText("上传时间:"+item.getAddtime()+"      "+"收藏时间:"+item.getHoldtime());
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView exam_collection_title, exam_collection__fl,exam_record_item_time;
        public ViewHolder(View itemView) {
            super(itemView);
            exam_collection_title = itemView.findViewById(R.id.exam_collection_title);
            exam_collection__fl = itemView.findViewById(R.id.exam_collection__fl);
            exam_record_item_time = itemView.findViewById(R.id.exam_record_item_time);
        }
    }
}
