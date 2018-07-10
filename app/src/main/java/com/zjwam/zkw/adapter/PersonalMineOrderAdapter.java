package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.PersonalOrderBean;

public class PersonalMineOrderAdapter extends ListBaseAdapter<PersonalOrderBean.getOrderItems> {
    private LayoutInflater mLayoutInflater;

    public PersonalMineOrderAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.mine_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final PersonalOrderBean.getOrderItems items = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.order_cname.setText(items.getCname());
        viewHolder.order_price.setText("￥" + items.getPrice());
        viewHolder.order_ordernum.setText("订单编号：" + items.getOrderno());
        viewHolder.order_addtime.setText("创建时间：" + items.getAddtime());
        viewHolder.order_paytime.setText("支付时间：" + items.getPaytime());
        viewHolder.order_payway.setText("支付方式：" + items.getPayment());
        if (items.getPaytime().length() > 0 && items.getPayment().length() > 0) {
            viewHolder.order_ispay.setText("已付款");
            viewHolder.order_paytime.setVisibility(View.VISIBLE);
            viewHolder.order_payway.setVisibility(View.VISIBLE);
        } else {
            viewHolder.order_ispay.setText("未付款");
            viewHolder.order_paytime.setVisibility(View.GONE);
            viewHolder.order_payway.setVisibility(View.GONE);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView order_cname, order_ispay, order_price, order_ordernum, order_addtime, order_paytime, order_payway;

        public ViewHolder(View itemView) {
            super(itemView);
            order_cname = itemView.findViewById(R.id.order_cname);
            order_ispay = itemView.findViewById(R.id.order_ispay);
            order_price = itemView.findViewById(R.id.order_price);
            order_ordernum = itemView.findViewById(R.id.order_ordernum);
            order_addtime = itemView.findViewById(R.id.order_addtime);
            order_paytime = itemView.findViewById(R.id.order_paytime);
            order_payway = itemView.findViewById(R.id.order_payway);
        }
    }
}
