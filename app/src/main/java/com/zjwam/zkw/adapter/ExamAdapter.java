package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ExamBean;

public class ExamAdapter extends ListBaseAdapter<ExamBean.Exam>{
    private LayoutInflater mLayoutInflater;
    private HoldExam holdExam;

    public ExamAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.exam_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ExamBean.Exam item = mDataList.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.exam_title.setText(item.getExam_name());
        viewHolder.exam_fl.setText("分类:"+item.getCate());
        viewHolder.exam_num.setText("已做题人数:"+item.getStudy_num());
        if (item.getHold() == 1){
            viewHolder.exam_hold.setImageResource(R.drawable.exam_hold);
        }else {
            viewHolder.exam_hold.setImageResource(R.drawable.exam_unhold);
        }
        viewHolder.exam_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holdExam.onClick(view,position);
            }
        });
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView exam_title, exam_fl,exam_num;
        private ImageView exam_hold;
        public ViewHolder(View itemView) {
            super(itemView);
            exam_title = itemView.findViewById(R.id.exam_title);
            exam_fl = itemView.findViewById(R.id.exam_fl);
            exam_num = itemView.findViewById(R.id.exam_num);
            exam_hold = itemView.findViewById(R.id.exam_hold);
        }
    }

    public void setHoldExam(HoldExam holdExam){
        this.holdExam = holdExam;
    }

    public interface HoldExam{
        void onClick(View view,int position);
    }
}


