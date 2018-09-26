package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.TestQueryResultDialogBean;

public class TestQueryResultDialogAdapter extends ListBaseAdapter<TestQueryResultDialogBean> {
    private LayoutInflater mLayoutInflater;
    private OnClickListener onClickListener;

    public TestQueryResultDialogAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.test_query_result_dialog_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        TestQueryResultDialogBean data = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.query_item_type.setText(data.getTitle());
        viewHolder.query_item_time.setText(data.getTest_time());
        viewHolder.query_item_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onItemClick(position);
            }
        });
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView query_item_type,query_item_time,query_item_query;
        public ViewHolder(View itemView) {
            super(itemView);
            query_item_type = itemView.findViewById(R.id.query_item_type);
            query_item_time = itemView.findViewById(R.id.query_item_time);
            query_item_query = itemView.findViewById(R.id.query_item_query);
        }
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onItemClick(int position);
    }
}
