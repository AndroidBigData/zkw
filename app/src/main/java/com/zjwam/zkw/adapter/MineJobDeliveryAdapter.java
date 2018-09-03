package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.MineJobDeliveryBean;

public class MineJobDeliveryAdapter extends ListBaseAdapter<MineJobDeliveryBean.Resume> {
    private LayoutInflater mLayoutInflater;

    public MineJobDeliveryAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.mine_job_delivery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        MineJobDeliveryBean.Resume item = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.delivery_title.setText(item.getJob_name());
        viewHolder.delivery_result.setText(item.getType());
        viewHolder.delivery_money.setText(item.getSalary());
        viewHolder.delivery_company.setText(item.getCompany_name());
        viewHolder.delivery_time.setText(item.getCreate_time());
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView delivery_title, delivery_result,delivery_money,delivery_company,delivery_time;
        public ViewHolder(View itemView) {
            super(itemView);
            delivery_title = itemView.findViewById(R.id.delivery_title);
            delivery_result = itemView.findViewById(R.id.delivery_result);
            delivery_money = itemView.findViewById(R.id.delivery_money);
            delivery_company = itemView.findViewById(R.id.delivery_company);
            delivery_time = itemView.findViewById(R.id.delivery_time);
        }
    }
}
