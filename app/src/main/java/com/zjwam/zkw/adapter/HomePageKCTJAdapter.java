package com.zjwam.zkw.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.HomePageKCTJInfo;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.ItemCallBack;
import com.zjwam.zkw.util.RequestOptionsUtils;



import java.util.ArrayList;
import java.util.List;

public class HomePageKCTJAdapter extends BaseAdapter{
    private Context context;
    private List<HomePageKCTJInfo> data;
    private HomePageKCTJInfo kctjInfo;
    private ViewHolder holder;
    private List<String> item_title,item_imgs;
    private ItemCallBack itemCallBack;
    private RequestOptions options;

    public HomePageKCTJAdapter(Context context, List<HomePageKCTJInfo> data, ItemCallBack itemCallBack) {
        this.context = context;
        this.data = data;
        this.itemCallBack = itemCallBack;
        options = RequestOptionsUtils.roundTransform(10);
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
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
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.homepage_kctj_item,null);
            holder = new ViewHolder();
            holder.kctj_item1 = view.findViewById(R.id.kctj_item1);
            holder.kctj_item2 = view.findViewById(R.id.kctj_item2);
            holder.kctj_item3 = view.findViewById(R.id.kctj_item3);
            holder.kctj_item4 = view.findViewById(R.id.kctj_item4);
            holder.kctj_item5 = view.findViewById(R.id.kctj_item5);
            holder.kctj_item6 = view.findViewById(R.id.kctj_item6);
            holder.kctj_item_bg = view.findViewById(R.id.kctj_item_bg);
            holder.kctj_item_bg2 = view.findViewById(R.id.kctj_item_bg2);
            holder.kctj_item_icon = view.findViewById(R.id.kctj_item_icon);
            holder.kctj_item_title = view.findViewById(R.id.kctj_item_title);
            holder.kctj_item_linearlayout1 = view.findViewById(R.id.kctj_item_linearlayout1);
            holder.kctj_item_linearlayout2 = view.findViewById(R.id.kctj_item_linearlayout2);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        kctjInfo = data.get(i);
        if ("zgz".equals(kctjInfo.getCode())){
//            holder.kctj_item_bg.setImageResource(R.drawable.zgz_bg);
            holder.kctj_item_title.setText(kctjInfo.getName());
            holder.kctj_item_icon.setImageResource(R.drawable.zgz_ic);
            setItem_title(initData());
            setItem_imgs(initImgs());
        }else if ("xzx".equals(kctjInfo.getCode())){
//            holder.kctj_item_bg.setImageResource(R.drawable.xcg_bg);
            holder.kctj_item_title.setText(kctjInfo.getName());
            holder.kctj_item_icon.setImageResource(R.drawable.xcg_ic);
            setItem_title(initData());
            setItem_imgs(initImgs());
        }else if ("cjw".equals(kctjInfo.getCode())){
//            holder.kctj_item_bg.setImageResource(R.drawable.cjw_bg);
            holder.kctj_item_title.setText(kctjInfo.getName());
            holder.kctj_item_icon.setImageResource(R.drawable.cjw_ic);
            setItem_title(initData());
            setItem_imgs(initImgs());
        }

        holder.kctj_item1.setOnClickListener(onClickListener);
        holder.kctj_item1.setTag(i);
        holder.kctj_item1.setTag(R.id.item1,0);
        holder.kctj_item2.setOnClickListener(onClickListener);
        holder.kctj_item2.setTag(i);
        holder.kctj_item2.setTag(R.id.item2,1);
        holder.kctj_item3.setOnClickListener(onClickListener);
        holder.kctj_item3.setTag(i);
        holder.kctj_item3.setTag(R.id.item3,2);
        holder.kctj_item4.setOnClickListener(onClickListener);
        holder.kctj_item4.setTag(i);
        holder.kctj_item4.setTag(R.id.item4,3);
        holder.kctj_item5.setOnClickListener(onClickListener);
        holder.kctj_item5.setTag(i);
        holder.kctj_item5.setTag(R.id.item5,4);
        holder.kctj_item6.setOnClickListener(onClickListener);
        holder.kctj_item6.setTag(i);
        holder.kctj_item6.setTag(R.id.item6,5);
        holder.kctj_item_bg.setOnClickListener(onClickListener);
        holder.kctj_item_bg.setTag(i);
        holder.kctj_item_bg.setTag(R.id.item7,0);
        holder.kctj_item_bg2.setOnClickListener(onClickListener);
        holder.kctj_item_bg2.setTag(i);
        holder.kctj_item_bg2.setTag(R.id.item8,1);
        return view;
    }

    private void setItem_imgs(List<String> imgs){

        GlideImageUtil.setImageView(context,imgs.get(0),holder.kctj_item_bg,options);
        GlideImageUtil.setImageView(context,imgs.get(1),holder.kctj_item_bg2,options);
    }

    private void setItem_title(List<String> item_title){
        if (item_title.size() == 1){
            holder.kctj_item1.setText(item_title.get(0));
            holder.kctj_item2.setVisibility(View.INVISIBLE);
            holder.kctj_item3.setVisibility(View.INVISIBLE);
            holder.kctj_item4.setVisibility(View.GONE);
            holder.kctj_item5.setVisibility(View.GONE);
            holder.kctj_item6.setVisibility(View.GONE);
            holder.kctj_item_linearlayout2.setVisibility(View.GONE);
            holder.kctj_item1.setBackgroundResource(R.drawable.homepage_kctj_left_bg);
            holder.kctj_item3.setBackgroundResource(R.drawable.homepage_kctj_right_bg);
        }else if (item_title.size() == 2){
            holder.kctj_item1.setText(item_title.get(0));
            holder.kctj_item2.setText(item_title.get(1));
            holder.kctj_item3.setVisibility(View.INVISIBLE);
            holder.kctj_item4.setVisibility(View.GONE);
            holder.kctj_item5.setVisibility(View.GONE);
            holder.kctj_item6.setVisibility(View.GONE);
            holder.kctj_item_linearlayout2.setVisibility(View.GONE);
            holder.kctj_item1.setBackgroundResource(R.drawable.homepage_kctj_left_bg);
            holder.kctj_item3.setBackgroundResource(R.drawable.homepage_kctj_right_bg);
        }else if (item_title.size() == 3){
            holder.kctj_item1.setText(item_title.get(0));
            holder.kctj_item2.setText(item_title.get(1));
            holder.kctj_item3.setText(item_title.get(2));
            holder.kctj_item4.setVisibility(View.GONE);
            holder.kctj_item5.setVisibility(View.GONE);
            holder.kctj_item6.setVisibility(View.GONE);
            holder.kctj_item_linearlayout2.setVisibility(View.GONE);
            holder.kctj_item1.setBackgroundResource(R.drawable.homepage_kctj_left_bg);
            holder.kctj_item3.setBackgroundResource(R.drawable.homepage_kctj_right_bg);
        }else if (item_title.size() == 4){
            holder.kctj_item1.setText(item_title.get(0));
            holder.kctj_item2.setText(item_title.get(1));
            holder.kctj_item3.setText(item_title.get(2));
            holder.kctj_item4.setText(item_title.get(3));
            holder.kctj_item5.setVisibility(View.INVISIBLE);
            holder.kctj_item6.setVisibility(View.INVISIBLE);
        } else if (item_title.size() == 5){
            holder.kctj_item1.setText(item_title.get(0));
            holder.kctj_item2.setText(item_title.get(1));
            holder.kctj_item3.setText(item_title.get(2));
            holder.kctj_item4.setText(item_title.get(3));
            holder.kctj_item5.setText(item_title.get(4));
            holder.kctj_item6.setVisibility(View.INVISIBLE);
        }else if (item_title.size() == 6){
            holder.kctj_item1.setText(item_title.get(0));
            holder.kctj_item2.setText(item_title.get(1));
            holder.kctj_item3.setText(item_title.get(2));
            holder.kctj_item4.setText(item_title.get(3));
            holder.kctj_item5.setText(item_title.get(4));
            holder.kctj_item6.setText(item_title.get(5));
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            itemCallBack.click(view);
        }
    };

    private List<String> initData() {
        item_title = new ArrayList<>();
        for (int j=0;j<kctjInfo.getItemInfos().size();j++){
            item_title.add(kctjInfo.getItemInfos().get(j).getName());
        }
        return item_title;
    }

    private List<String> initImgs() {
        item_imgs = new ArrayList<>();
        for (int j=0;j<kctjInfo.getItemImgs().size();j++){
            item_imgs.add(kctjInfo.getItemImgs().get(j).getImg());
        }
        return item_imgs;
    }

    class ViewHolder{
//        Button ;
        ImageView kctj_item_bg,kctj_item_bg2,kctj_item_icon;
        TextView kctj_item_title,kctj_item1,kctj_item2,kctj_item3,kctj_item4,kctj_item5,kctj_item6;
        LinearLayout kctj_item_linearlayout1,kctj_item_linearlayout2;
    }
}
