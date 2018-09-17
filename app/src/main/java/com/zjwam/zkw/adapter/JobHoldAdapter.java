package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.customview.FlowLayout;
import com.zjwam.zkw.entity.JobHomeBean;

public class JobHoldAdapter extends ListBaseAdapter<JobHomeBean.Resume> {
    private LayoutInflater mLayoutInflater;
    private ViewHolder viewHolder;
    private JobHomeBean.Resume item;
    private Hold hold;

    public JobHoldAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.job_hold_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        item = mDataList.get(position);
        viewHolder = (ViewHolder) holder;
        viewHolder.job_item_title.setText(item.getJob_name());
        viewHolder.job_item_money.setText(item.getSalary());
        viewHolder.job_item_company.setText(item.getCompany_name());
        viewHolder.job_item_time.setText(item.getCreate_time());
        viewHolder.job_item_position.setText(item.getArea());
        viewHolder.job_item_type.setText(item.getType());
        initBenefit();
        viewHolder.job_item_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hold.cancelHold(position);
            }
        });
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView job_item_title, job_item_money,job_item_company,job_item_time,job_item_type,job_item_position,job_item_hold;
        private FlowLayout job_item_benefit;
        public ViewHolder(View itemView) {
            super(itemView);
            job_item_title = itemView.findViewById(R.id.job_item_title);
            job_item_money = itemView.findViewById(R.id.job_item_money);
            job_item_company = itemView.findViewById(R.id.job_item_company);
            job_item_time = itemView.findViewById(R.id.job_item_time);
            job_item_type = itemView.findViewById(R.id.job_item_type);
            job_item_position = itemView.findViewById(R.id.job_item_position);
            job_item_benefit = itemView.findViewById(R.id.job_item_benefit);
            job_item_hold = itemView.findViewById(R.id.job_item_hold);
        }
    }

    private void initBenefit() {
        viewHolder.job_item_benefit.removeAllViews();
        for (int i = 0; i < item.getBenefit().size(); i++) {
            JobHomeBean.Benefit option = item.getBenefit().get(i);
            TextView checkboxView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.job_benefit, viewHolder.job_item_benefit,false);
            checkboxView.setText("  "+option.getType()+"  ");
            viewHolder.job_item_benefit.addView(checkboxView);
        }
    }

    public void setHold(Hold hold) {
        this.hold = hold;
    }

    public interface Hold{
        void cancelHold(int position);
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);

        if(position != (getDataList().size())){ // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position,this.mDataList.size()-position);
        }
    }
}
