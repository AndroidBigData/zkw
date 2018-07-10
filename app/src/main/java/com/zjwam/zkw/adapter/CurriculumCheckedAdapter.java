package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;

import java.util.List;


public class CurriculumCheckedAdapter extends RecyclerView.Adapter<CurriculumCheckedAdapter.ViewHolder> {

    private Context context;
    private List<String> data;
    private OnItemClickListener mItemClickListener;
    public CurriculumCheckedAdapter(Context context,List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.curriculum_checked_item,parent,false);
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
        String item =data.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.curriculum_checked_item.setText("    " + item +"    ");
        viewHolder.itemView.setTag(position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView curriculum_checked_item;
        public ViewHolder(View itemView) {
            super(itemView);
            curriculum_checked_item = itemView.findViewById(R.id.curriculum_checked_item);
        }
    }
}
