package com.zjwam.zkw.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.CurriculumSelectBean;

import java.util.List;

public class CurriculumGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<CurriculumSelectBean> noticeCurriculumSelectBeanList;
    private LayoutInflater inflater = null;
    changeTextBacs textBac;

    public CurriculumGridViewAdapter(Context context, List<CurriculumSelectBean> list, changeTextBacs textBac) {
        this.context = context;
        this.noticeCurriculumSelectBeanList = list;
        this.textBac = textBac;
        this.inflater = LayoutInflater.from(context);
    }

    int selectItem = 0;


    @Override
    public int getCount() {
        return noticeCurriculumSelectBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeCurriculumSelectBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_layout, null);
            holder.textview = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textview.setText(noticeCurriculumSelectBeanList.get(position).getName());
        holder.textview.setOnClickListener(new setOnClickListener(context, position));
        if (position == selectItem) {
            holder.textview.setBackgroundResource(R.drawable.a123);
            holder.textview.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            holder.textview.setBackgroundResource(R.drawable.b321);
            holder.textview.setTextColor(context.getResources().getColor(R.color.main_blue));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView textview;
    }

    public void reloadData(List<CurriculumSelectBean> list_CurriculumSelect_bean) {
        // TODO Auto-generated method stub
        this.noticeCurriculumSelectBeanList = list_CurriculumSelect_bean;
        notifyDataSetChanged();
    }


    public void selectItem(int selectItem) {
        this.selectItem = selectItem;
        notifyDataSetChanged();
    }

    class setOnClickListener implements View.OnClickListener {

        Context context;
        int arg0;

        public setOnClickListener(Context context, int arg0) {
            this.context = context;
            this.arg0 = arg0;
        }

        @Override
        public void onClick(View v) {
            textBac.changeTextBack(arg0);
        }

    }

    public interface changeTextBacs {
        void changeTextBack(int postion);
    }

}