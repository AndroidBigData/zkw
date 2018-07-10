package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.MineIntegralBean;

public class PersonalMineIntegralAdapter extends ListBaseAdapter<MineIntegralBean.getIntegralItem> {

    private LayoutInflater mLayoutInflater;

    public PersonalMineIntegralAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.personal_mine_integral,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MineIntegralBean.getIntegralItem  items = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mine_integral_way.setText(items.getMethod());
        viewHolder.mine_integral_time.setText(items.getAddtime());
        viewHolder.mine_integral_num.setText(items.getFen());
    }
    private class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mine_integral_way,mine_integral_time,mine_integral_num;

        public ViewHolder(View itemView) {
            super(itemView);
            mine_integral_way = itemView.findViewById(R.id.mine_integral_way);
            mine_integral_time = itemView.findViewById(R.id.mine_integral_time);
            mine_integral_num = itemView.findViewById(R.id.mine_integral_num);
        }
    }
}
