package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.MineJobResumeBean;

public class ChoiceResumeAdapter extends ListBaseAdapter<MineJobResumeBean> {
    private LayoutInflater mLayoutInflater;

    public ChoiceResumeAdapter(Context context) {
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
        viewHolder.choice_gone.setVisibility(View.GONE);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView resume_name, resume_to,resume_time;
        private RelativeLayout choice_gone;
        public ViewHolder(View itemView) {
            super(itemView);
            resume_name = itemView.findViewById(R.id.resume_name);
            resume_to = itemView.findViewById(R.id.resume_to);
            resume_time = itemView.findViewById(R.id.resume_time);
            choice_gone = itemView.findViewById(R.id.choice_gone);
        }
    }
}
