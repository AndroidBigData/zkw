package com.zjwam.zkw.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.MineLearnCardBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.RequestOptionsUtils;

import java.util.List;

public class PersonalMineLearnCardAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> grouplist;
    private List<List<MineLearnCardBean.getLearnCard>> childrenlist;
    private RequestOptions optionsUtils;

    public PersonalMineLearnCardAdapter(Context context, List<String> grouplist, List<List<MineLearnCardBean.getLearnCard>> childrenlist) {
        this.context = context;
        this.grouplist = grouplist;
        this.childrenlist = childrenlist;
        optionsUtils = RequestOptionsUtils.commonTransform();
    }

    @Override
    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childrenlist.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return grouplist.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childrenlist.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupHolder groupHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.learn_card_title,viewGroup,false);
            groupHolder = new GroupHolder();
            groupHolder.learn_card_title = view.findViewById(R.id.learn_card_title);
            groupHolder.group_img = view.findViewById(R.id.group_img);
            view.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) view.getTag();
        }
        String title = grouplist.get(i);
        groupHolder.learn_card_title.setText(title);
        if (title.contains("使用中")){
            groupHolder.group_img.setImageResource(R.drawable.learncarding);
        }else if (title.contains("未使用")){
            groupHolder.group_img.setImageResource(R.drawable.round_msg);
        }else if (title.contains("已失效")){
            groupHolder.group_img.setImageResource(R.drawable.learncardover);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildrenHolder childrenHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.learn_card_cards,viewGroup,false);
            childrenHolder = new ChildrenHolder();
            childrenHolder.learn_card_img = view.findViewById(R.id.learn_card_img);
            view.setTag(childrenHolder);
        }else {
            childrenHolder = (ChildrenHolder) view.getTag();
        }
        MineLearnCardBean.getLearnCard item = (MineLearnCardBean.getLearnCard) getChild(i,i1);
        GlideImageUtil.setImageView(context,item.getImg(),childrenHolder.learn_card_img,optionsUtils);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    class GroupHolder{
        private TextView learn_card_title;
        private ImageView group_img;
    }
    class ChildrenHolder{
        private ImageView learn_card_img;
    }
}
