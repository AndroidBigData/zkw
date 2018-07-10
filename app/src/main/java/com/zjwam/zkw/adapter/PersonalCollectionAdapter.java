package com.zjwam.zkw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.PersonalCollectionBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;



public class PersonalCollectionAdapter extends ListBaseAdapter<PersonalCollectionBean.CollectionItems>{
    private LayoutInflater mLayoutInflater;
    private RequestOptions options;
    private offCollection offCollection;

    public PersonalCollectionAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        options = RequestOptionsUtils.roundTransform(10);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonalCollectionAdapter.ViewHolder(mLayoutInflater.inflate(R.layout.mine_collection_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final PersonalCollectionBean.CollectionItems item = mDataList.get(position);

        PersonalCollectionAdapter.ViewHolder viewHolder = (PersonalCollectionAdapter.ViewHolder) holder;
        viewHolder.class_name.setText(item.getName());
        viewHolder.class_label.setText(item.getAbstracts());
        viewHolder.class_rating.setNumStars(item.getStar());
        viewHolder.class_learn.setText(item.getNum()+"课时  " + item.getSnum() + "人已学习");
        viewHolder.class_type.setText(item.getType());
        GlideImageUtil.setImageView(mContext,item.getImg(),viewHolder.class_img,options);
        viewHolder.off_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offCollection.onOffCollection(item.getId(),position);
            }
        });
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView class_name, class_label,class_learn, class_type,off_collection;
        private RatingBar class_rating;
        private ImageView class_img;
        public ViewHolder(View itemView) {
            super(itemView);
            class_img = itemView.findViewById(R.id.class_img);
            class_name = itemView.findViewById(R.id.class_name);
            class_label = itemView.findViewById(R.id.class_label);
            class_rating = itemView.findViewById(R.id.class_rating);
            class_learn = itemView.findViewById(R.id.class_learn);
            class_type = itemView.findViewById(R.id.class_type);
            off_collection = itemView.findViewById(R.id.off_collection);
        }
    }

    public void setOffCollection(offCollection offCollection){
        this.offCollection = offCollection;
    }

    public interface offCollection{
        void onOffCollection(int classId,int position);
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);

        if(position != (getDataList().size())){ // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position,this.mDataList.size()-position);
        }
    }

}

