package com.zjwam.zkw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ClassInfo;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;



import java.util.List;

public class HomePageClassMoreAdapter extends BaseAdapter {
    private List<ClassInfo> data;
    private Context context;
    private RequestOptions options;

    public HomePageClassMoreAdapter(List<ClassInfo> data, Context context) {
        this.data = data;
        this.context = context;
        options = RequestOptionsUtils.roundTransform(10);
    }

    @Override
    public int getCount() {
        return data != null ? data.size():0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.class_search_item,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.class_name.setText(data.get(i).getName());
        viewHolder.class_label.setText(data.get(i).getAbstracts());
        viewHolder.class_rating.setNumStars(data.get(i).getStar());
        viewHolder.class_learn.setText(data.get(i).getNum()+"课时  " + data.get(i).getSnum() + "人已学习");
        viewHolder.class_type.setText(data.get(i).getType());
        GlideImageUtil.setImageView(context,data.get(i).getImg(),viewHolder.class_img,options);
        return view;
    }
    class ViewHolder {
        private TextView class_name, class_label,class_learn, class_type;
        private RatingBar class_rating;
        private ImageView class_img;
        public ViewHolder(View itemView) {
            class_img = itemView.findViewById(R.id.class_img);
            class_name = itemView.findViewById(R.id.class_name);
            class_label = itemView.findViewById(R.id.class_label);
            class_rating = itemView.findViewById(R.id.class_rating);
            class_learn = itemView.findViewById(R.id.class_learn);
            class_type = itemView.findViewById(R.id.class_type);
        }
    }
}
