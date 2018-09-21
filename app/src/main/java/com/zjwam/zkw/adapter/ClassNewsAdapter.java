package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ClassNewsBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;

public class ClassNewsAdapter extends ListBaseAdapter<ClassNewsBean.Item> {
    private LayoutInflater mLayoutInflater;
    private RequestOptions optionClass,optionTeacher;
    private int type;
    private OnClickListener onClickListener;

    public ClassNewsAdapter(Context context,int type) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.type = type;
        optionClass = RequestOptionsUtils.roundTransform(10);
        optionTeacher = RequestOptions.circleCropTransform();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        RecyclerView.ViewHolder viewHolder = null;
        if (type == 0){
            itemView = mLayoutInflater.inflate(R.layout.class_news_item1,parent,false);
            viewHolder = new ClassViewHolder(itemView);
        }else if (type == 1){
            itemView = mLayoutInflater.inflate(R.layout.class_news_item2,parent,false);
            viewHolder = new TeacherViewHolder(itemView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final ClassNewsBean.Item data = mDataList.get(position);
        if (holder instanceof ClassViewHolder){
            ClassViewHolder viewHolder = (ClassViewHolder) holder;
            GlideImageUtil.setImageView(mContext,data.getImg(),viewHolder.class_img,optionClass);
            if (data.getCate_id() == 25){
                viewHolder.class_bg.setText("最新");
                viewHolder.class_bg.setBackgroundResource(R.drawable.class_news_updata_bg);
            }else if (data.getCate_id() == 27){
                viewHolder.class_bg.setText("精品");
                viewHolder.class_bg.setBackgroundResource(R.drawable.class_news_fine_bg);
            }else if (data.getCate_id() == 28){
                viewHolder.class_bg.setText("热门");
                viewHolder.class_bg.setBackgroundResource(R.drawable.class_news_hot_bg);
            }
            viewHolder.class_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClick(position);
                }
            });
        }else if (holder instanceof TeacherViewHolder){
            TeacherViewHolder viewHolder = (TeacherViewHolder) holder;
            GlideImageUtil.setImageView(mContext,data.getImg(),viewHolder.teacher_img,optionTeacher);
            viewHolder.teacher_name.setText(data.getName());
            viewHolder.teacher_school.setText(data.getGrade());
            viewHolder.teacher_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClick(position);
                }
            });
        }
    }

    private class ClassViewHolder extends RecyclerView.ViewHolder{
        private ImageView class_img;
        private TextView class_bg;
        private RelativeLayout class_item;
        public ClassViewHolder(View itemView) {
            super(itemView);
            class_img = itemView.findViewById(R.id.class_img);
            class_bg = itemView.findViewById(R.id.class_bg);
            class_item = itemView.findViewById(R.id.class_item);
        }
    }
    private class TeacherViewHolder extends RecyclerView.ViewHolder{
        private ImageView teacher_img;
        private TextView teacher_name,teacher_school;
        private LinearLayout teacher_item;
        public TeacherViewHolder(View itemView) {
            super(itemView);
            teacher_img = itemView.findViewById(R.id.teacher_img);
            teacher_name = itemView.findViewById(R.id.teacher_name);
            teacher_school = itemView.findViewById(R.id.teacher_school);
            teacher_item = itemView.findViewById(R.id.teacher_item);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onItemClick(int position);
    }
}
