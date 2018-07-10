package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.MineClassBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;



public class MineClassAdapter extends ListBaseAdapter<MineClassBean.itemBean> {
    private LayoutInflater mLayoutInflater;
    private RequestOptions options;
    private learnOnClick learnOnClick;

    public MineClassAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        options = RequestOptionsUtils.roundTransform(10);
    }

    @Override
    public int getItemViewType(int position) {
       return mDataList.get(position).getBegin();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 1){
            itemView = mLayoutInflater.inflate(R.layout.mine_learning_layout,parent,false);
            viewHolder = new LearningViewHolder(itemView);
        }else if (viewType == 2){
            itemView = mLayoutInflater.inflate(R.layout.mine_learn_over_layout,parent,false);
            viewHolder = new LearnoverViewHolder(itemView);
        }else if (viewType == 0){
            itemView = mLayoutInflater.inflate(R.layout.mine_learning_layout,parent,false);
            viewHolder = new LearnBeginViewHolder(itemView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final MineClassBean.itemBean data = mDataList.get(position);
        if (holder instanceof LearningViewHolder){
            LearningViewHolder viewHolder = (LearningViewHolder) holder;
            GlideImageUtil.setImageView(mContext,data.getImg(),viewHolder.learning_img,options);
            viewHolder.learning_type.setText(data.getType());
            viewHolder.learning_name.setText(data.getName());
            viewHolder.learning_label.setText(data.getAbstracts().trim());
            viewHolder.learning_pro_text.setText("已完成 " + data.getRatio() + "%");
            viewHolder.learning_pro.setProgress((int)(Double.parseDouble(data.getRatio()))*10);
            viewHolder.class_learning.setText(data.getNum() + "课时  " + "共" + data.getCtime() + "分钟");
            viewHolder.learning_goon.setText("继续学习");
            viewHolder.learning_goon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (learnOnClick!=null){
                        learnOnClick.onLearnClick(data.getId(),data.getName());
                    }
                }
            });
        }else if (holder instanceof LearnoverViewHolder){
            LearnoverViewHolder viewHolder = (LearnoverViewHolder) holder;
            GlideImageUtil.setImageView(mContext,data.getImg(),viewHolder.learnover_img,options);
            viewHolder.learnover_type.setText(data.getType());
            viewHolder.learnover_name.setText(data.getName());
            viewHolder.learnover_label.setText(data.getAbstracts().trim());
            viewHolder.learnover_pro_text.setText("已完成 " + data.getRatio()+ "%");
            viewHolder.class_learnover.setText(data.getNum() + "课时  " + "共" + data.getCtime() + "分钟");

        }else if (holder instanceof LearnBeginViewHolder){
            LearnBeginViewHolder viewHolder = (LearnBeginViewHolder) holder;
            GlideImageUtil.setImageView(mContext,data.getImg(),viewHolder.learning_img,options);
            viewHolder.learning_type.setText(data.getType());
            viewHolder.learning_name.setText(data.getName());
            viewHolder.learning_label.setText(data.getAbstracts().trim());
            viewHolder.learning_pro_text.setText("已完成 " + data.getRatio()+ "%");
            viewHolder.learning_pro.setProgress((int)(Double.parseDouble(data.getRatio()))*10);
            viewHolder.class_learning.setText(data.getNum() + "课时  " + "共" + data.getCtime() + "分钟");
            viewHolder.learning_goon.setText("开始学习");
            viewHolder.learning_goon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    learnOnClick.onLearnClick(data.getId(),data.getName());
                }
            });
        }
    }

    private class LearningViewHolder extends RecyclerView.ViewHolder{
        private ImageView learning_img;
        private TextView learning_type,learning_name,learning_label,learning_pro_text,class_learning,learning_goon;
        private ProgressBar learning_pro;
        public LearningViewHolder(View itemView) {
            super(itemView);
            learning_img = itemView.findViewById(R.id.learning_img);
            learning_type = itemView.findViewById(R.id.learning_type);
            learning_name = itemView.findViewById(R.id.learning_name);
            learning_label = itemView.findViewById(R.id.learning_label);
            learning_pro_text = itemView.findViewById(R.id.learning_pro_text);
            class_learning = itemView.findViewById(R.id.class_learning);
            learning_goon = itemView.findViewById(R.id.learning_goon);
            learning_pro = itemView.findViewById(R.id.learning_pro);
        }
    }
    private class LearnoverViewHolder extends RecyclerView.ViewHolder{
        private ImageView learnover_img;
        private TextView learnover_type,learnover_name,learnover_label,class_learnover,learnover_pro_text;
        public LearnoverViewHolder(View itemView) {
            super(itemView);
            learnover_img = itemView.findViewById(R.id.learnover_img);
            learnover_type = itemView.findViewById(R.id.learnover_type);
            learnover_name = itemView.findViewById(R.id.learnover_name);
            learnover_label = itemView.findViewById(R.id.learnover_label);
            class_learnover = itemView.findViewById(R.id.class_learnover);
            learnover_pro_text = itemView.findViewById(R.id.learnover_pro_text);
        }
    }

    private class LearnBeginViewHolder extends RecyclerView.ViewHolder{
        private ImageView learning_img;
        private TextView learning_type,learning_name,learning_label,learning_pro_text,class_learning,learning_goon;
        private ProgressBar learning_pro;
        public LearnBeginViewHolder(View itemView) {
            super(itemView);
            learning_img = itemView.findViewById(R.id.learning_img);
            learning_type = itemView.findViewById(R.id.learning_type);
            learning_name = itemView.findViewById(R.id.learning_name);
            learning_label = itemView.findViewById(R.id.learning_label);
            learning_pro_text = itemView.findViewById(R.id.learning_pro_text);
            class_learning = itemView.findViewById(R.id.class_learning);
            learning_goon = itemView.findViewById(R.id.learning_goon);
            learning_pro = itemView.findViewById(R.id.learning_pro);
        }
    }
    public void setOnClick(learnOnClick learnOnClick){
        this.learnOnClick = learnOnClick;
    }

    public interface learnOnClick{
        void onLearnClick(int classid,String title);
    }

}
