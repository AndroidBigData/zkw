package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.customview.FlowLayout;
import com.zjwam.zkw.entity.MineJobDeliveryBean;

public class MineJobDeliveryAdapter extends ListBaseAdapter<MineJobDeliveryBean.Resume> {
    private LayoutInflater mLayoutInflater;
    private MineJobDeliveryBean.Resume item;
    private ViewHolder viewHolder;

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
        item = mDataList.get(position);
        viewHolder = (ViewHolder) holder;
        viewHolder.delivery_title.setText(item.getJob_name());
        viewHolder.delivery_result.setText(item.getType());
        viewHolder.delivery_money.setText(item.getSalary());
        viewHolder.delivery_company.setText(item.getCompany_name());
        viewHolder.delivery_time.setText(item.getCreate_time());
        viewHolder.delivery_item_type.setText(item.getSty());
        viewHolder.delivery_item_area.setText(item.getArea());
        initBenefit();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView delivery_title, delivery_result,delivery_money,delivery_company,delivery_time,delivery_item_type,delivery_item_area;
        private FlowLayout delivery_item_benefit;
        public ViewHolder(View itemView) {
            super(itemView);
            delivery_title = itemView.findViewById(R.id.delivery_title);
            delivery_result = itemView.findViewById(R.id.delivery_result);
            delivery_money = itemView.findViewById(R.id.delivery_money);
            delivery_company = itemView.findViewById(R.id.delivery_company);
            delivery_time = itemView.findViewById(R.id.delivery_time);
            delivery_item_type = itemView.findViewById(R.id.delivery_item_type);
            delivery_item_area = itemView.findViewById(R.id.delivery_item_area);
            delivery_item_benefit = itemView.findViewById(R.id.delivery_item_benefit);
        }
    }

    private void initBenefit() {
        viewHolder.delivery_item_benefit.removeAllViews();
        for (int i = 0; i < item.getBenefit().size(); i++) {
            MineJobDeliveryBean.Benefit option = item.getBenefit().get(i);
            TextView checkboxView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.job_benefit, viewHolder.delivery_item_benefit,false);
            checkboxView.setText("  "+option.getType()+"  ");
            viewHolder.delivery_item_benefit.addView(checkboxView);
        }
    }
}
