package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ExamRecordBean;


public class ExamRecordAdapter extends ListBaseAdapter<ExamRecordBean.Exam> {
    private LayoutInflater mLayoutInflater;
    private ExamRecordBean.Exam item;
    private ViewHolder viewHolder;
    private OpenedItem openedItem;
    private int position;

    public ExamRecordAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.exam_record_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        item = mDataList.get(position);
        viewHolder = (ViewHolder) holder;
        viewHolder.exam_record_item_title.setText(item.getExam_name());
        viewHolder.exam_record_item_fl.setText("分类："+ item.getCate());
        viewHolder.exam_record_item_time.setText("测试时间：" + item.getAddtime());
        updateCheckBoxView();
        if (item.isOpen()){
            viewHolder.exam_record_item_add.setVisibility(View.VISIBLE);
        }else {
            viewHolder.exam_record_item_add.setVisibility(View.GONE);
        }

    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView exam_record_item_title, exam_record_item_fl,exam_record_item_time;
        private LinearLayout exam_record_item_add;
        public ViewHolder(View itemView) {
            super(itemView);
            exam_record_item_title = itemView.findViewById(R.id.exam_record_item_title);
            exam_record_item_fl = itemView.findViewById(R.id.exam_record_item_fl);
            exam_record_item_time = itemView.findViewById(R.id.exam_record_item_time);
            exam_record_item_add = itemView.findViewById(R.id.exam_record_item_add);
        }
    }


    /**
     * 添加多选按钮
     */
    private void updateCheckBoxView() {
        viewHolder.exam_record_item_add.removeAllViews();
        for (int i = 0; i < item.getDetial().size(); i++) {
            final ExamRecordBean.Detial option = item.getDetial().get(i);
            RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.exam_record_child_item, null);
            relativeLayout.setTag(i);
            TextView child_num = relativeLayout.findViewById(R.id.child_num);
            TextView child_rate = relativeLayout.findViewById(R.id.child_rate);
            TextView child_exam_details = relativeLayout.findViewById(R.id.child_exam_details);
            child_num.setText( option.getName());
            child_rate.setText("正确率："+option.getRate()+"%");
            child_exam_details.setText("正确："+ option.getRnum()+"   "+"错误："+option.getWnum());
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openedItem.openItem(view,option);
                }
            });
            viewHolder.exam_record_item_add.addView(relativeLayout);
        }
    }


    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);

        if(position != (getDataList().size())){ // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position,this.mDataList.size()-position);
        }
    }

    public void setOpenItem(OpenedItem openItem){
        this.openedItem = openItem;
    }

    public interface OpenedItem{
        void openItem(View view,ExamRecordBean.Detial options);
    }
}
