package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.PayPreviewBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;

public class PayPreviewAdapter extends ListBaseAdapter<PayPreviewBean.getOrderItems> {
    private LayoutInflater mLayoutInflater;
    private RequestOptions optionsUtils;

    public PayPreviewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        optionsUtils = RequestOptionsUtils.roundTransform(10);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.preview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final PayPreviewBean.getOrderItems items = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.preview_item_title.setText(items.getName());
        viewHolder.preview_item_class.setText(items.getAbstracts());
        viewHolder.preview_item_price.setText("￥"+String.valueOf(items.getPrice()));
        viewHolder.preview_item_oldprice.setText("原价￥"+String.valueOf(items.getOld_price()));
        viewHolder.preview_item_num.setText(items.getNum()+"课时    时长" + items.getAlltime() + "分钟");
        GlideImageUtil.setImageView(mContext,items.getImg(),viewHolder.preview_item_img,optionsUtils );
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView preview_item_title, preview_item_class, preview_item_price, preview_item_oldprice, preview_item_num;
        private ImageView preview_item_img;

        public ViewHolder(View itemView) {
            super(itemView);
            preview_item_title = itemView.findViewById(R.id.preview_item_title);
            preview_item_class = itemView.findViewById(R.id.preview_item_class);
            preview_item_price = itemView.findViewById(R.id.preview_item_price);
            preview_item_oldprice = itemView.findViewById(R.id.preview_item_oldprice);
            preview_item_num = itemView.findViewById(R.id.preview_item_num);
            preview_item_img = itemView.findViewById(R.id.preview_item_img);
        }
    }
}
