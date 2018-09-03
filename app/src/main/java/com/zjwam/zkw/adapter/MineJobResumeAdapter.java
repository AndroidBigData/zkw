package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.MineJobResumeBean;

public class MineJobResumeAdapter extends ListBaseAdapter<MineJobResumeBean> {
    private LayoutInflater mLayoutInflater;
    private ResumeClick resumeClick;

    public MineJobResumeAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.mine_job_resume_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        MineJobResumeBean item = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.resume_name.setText(item.getResume_name());
        viewHolder.resume_to.setText("求职意向:"+item.getCate_name());
        viewHolder.resume_time.setText("创建时间:"+item.getCreate_time());
        viewHolder.edit_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeClick.editResume(position);
            }
        });
        viewHolder.delete_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeClick.deleteResume(position);
            }
        });
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView resume_name, resume_to,resume_time,delete_resume,edit_resume;
        public ViewHolder(View itemView) {
            super(itemView);
            resume_name = itemView.findViewById(R.id.resume_name);
            resume_to = itemView.findViewById(R.id.resume_to);
            resume_time = itemView.findViewById(R.id.resume_time);
            delete_resume = itemView.findViewById(R.id.delete_resume);
            edit_resume = itemView.findViewById(R.id.edit_resume);
        }
    }

    public void setResumeClick(ResumeClick resumeClick){
        this.resumeClick = resumeClick;
    }

    public interface ResumeClick{
        void deleteResume(int position);
        void editResume(int position);
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);
        if(position != (getDataList().size())){ // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position,this.mDataList.size()-position);
        }
    }
}
