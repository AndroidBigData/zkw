package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.SearchJobDetailsPopBean;

import java.util.List;


public class SearchJobDetailsPopAdapter extends  RecyclerView.Adapter<SearchJobDetailsPopAdapter.ViewHolder> {
    private Context context;
    private List<SearchJobDetailsPopBean.BasicBean> data;
    private OnItemClickListener mItemClickListener;
    public SearchJobDetailsPopAdapter(Context context,List<SearchJobDetailsPopBean.BasicBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchjobdetails_pop_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener!=null){
                    mItemClickListener.onItemClick((Integer) v.getTag());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchJobDetailsPopBean.BasicBean item =data.get(position);
        ViewHolder viewHolder =  holder;
        viewHolder.searchjobdetailspop_item.setText(item.getName());
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView searchjobdetailspop_item;
        public ViewHolder(View itemView) {
            super(itemView);
            searchjobdetailspop_item = itemView.findViewById(R.id.searchjobdetailspop_item);
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
