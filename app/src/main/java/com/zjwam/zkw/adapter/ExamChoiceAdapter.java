package com.zjwam.zkw.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.CurriculumSelectBean;

import java.util.List;

public class ExamChoiceAdapter extends BaseAdapter {
    private Context context;
    private List<CurriculumSelectBean> noticeCurriculumSelectBeanList;
    private LayoutInflater inflater = null;

    public ExamChoiceAdapter(Context context, List<CurriculumSelectBean> list) {
        this.context = context;
        this.noticeCurriculumSelectBeanList = list;
        this.inflater = LayoutInflater.from(context);
    }

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
        CurriculumGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new CurriculumGridViewAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.exam_select_item, null);
            holder.textview = convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        } else {
            holder = (CurriculumGridViewAdapter.ViewHolder) convertView.getTag();
        }
        holder.textview.setText(noticeCurriculumSelectBeanList.get(position).getName());
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

    public void clearList(){
        noticeCurriculumSelectBeanList.clear();
    }

}
