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
import com.zjwam.zkw.entity.TeacherMoreBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;

public class TeacherMoreAdapter extends ListBaseAdapter<TeacherMoreBean.Classfor> {
    private LayoutInflater mLayoutInflater;
    private RequestOptions option;
    private OnClickListener onClickListener;

    public TeacherMoreAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        option = RequestOptionsUtils.roundTransform(10);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.teacher_more_class_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        TeacherMoreBean.Classfor data = mDataList.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            GlideImageUtil.setImageView(mContext,data.getImg(),viewHolder.class_img,option);
            viewHolder.class_bg.setText(data.getBub());
            viewHolder.class_bg.setBackgroundResource(R.drawable.class_news_fine_bg);
            viewHolder.class_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClick(position);
                }
            });
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView class_img;
        private TextView class_bg;
        private RelativeLayout class_item;
        public ViewHolder(View itemView) {
            super(itemView);
            class_img = itemView.findViewById(R.id.class_img);
            class_bg = itemView.findViewById(R.id.class_bg);
            class_item = itemView.findViewById(R.id.class_item);
        }
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onItemClick(int position);
    }
}
