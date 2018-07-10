package com.zjwam.zkw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.MineShopCartBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;



import java.util.List;

public class PersonalMineShopCardAdapter extends BaseAdapter {
    private RequestOptions options;
    private Context context;
    private List<MineShopCartBean.getShopCartItems> data;


    public PersonalMineShopCardAdapter(Context context, List<MineShopCartBean.getShopCartItems> data) {
        this.context = context;
        this.data = data;
        options = RequestOptionsUtils.roundTransform(10);
    }

    @Override
    public int getCount() {
        return data.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.mine_shopcart_item,null);
            viewHolder = new ViewHolder();
            viewHolder.shopcard_classname = view.findViewById(R.id.shopcard_classname);
            viewHolder.shopcard_price = view.findViewById(R.id.shopcard_price);
            viewHolder.shopcard_classtime = view.findViewById(R.id.shopcard_classtime);
            viewHolder.shopcard_img = view.findViewById(R.id.shopcard_img);
            viewHolder.shopcard_checkbok = view.findViewById(R.id.shopcard_checkbok);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        MineShopCartBean.getShopCartItems items = data.get(i);
        viewHolder.shopcard_classname.setText(items.getName());
        viewHolder.shopcard_price.setText("￥"+items.getPrice());
        viewHolder.shopcard_classtime.setText(items.getNum() + "课时，时长" + items.getAlltime());
        GlideImageUtil.setImageView(context,items.getImg(),viewHolder.shopcard_img,options);
        viewHolder.shopcard_checkbok.setChecked(items.isChecked());
        return view;
    }

    public class ViewHolder {

        public TextView shopcard_classname, shopcard_price, shopcard_classtime;
        public ImageView shopcard_img;
        public CheckBox shopcard_checkbok;

    }
}
