package com.zjwam.zkw.customview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.SearchJobDetailsPopAdapter;
import com.zjwam.zkw.entity.SearchJobDetailsPopBean;
import java.util.List;

public class SearchJobDetailsPop extends PopupWindow {
    private Context context;
    private RecyclerView search_pop_recyclerview;
    private SearchJobDetailsPopAdapter jobDetailsPopAdapter;
    private List<SearchJobDetailsPopBean.BasicBean> list;
    private OnItemClickListener itemClickListener;


    public SearchJobDetailsPop(Context context, List<SearchJobDetailsPopBean.BasicBean> list) {
        super(context);
        this.context = context;
        this.list = list;
        initPop();
    }

    private void initPop() {
        View popView = View.inflate(context, R.layout.searchjobdetails_pop, null);
        //设置view
        setContentView(popView);
        //设置宽高（也可设置为LinearLayout.LayoutParams.MATCH_PARENT或者LinearLayout.LayoutParams.MATCH_PARENT）
        setWidth(-1);
        setHeight(-2);
        //设置PopupWindow的焦点
        setFocusable(true);
        //设置窗口以外的地方点击可关闭pop
        setOutsideTouchable(false);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0x33000000));
        //这个控制PopupWindow内部控件的点击事件
        setTouchable(true);
        initView(popView);
        initData();
    }

    private void initData() {
        jobDetailsPopAdapter = new SearchJobDetailsPopAdapter(context, list);
        search_pop_recyclerview.setAdapter(jobDetailsPopAdapter);
        search_pop_recyclerview.setLayoutManager(new GridLayoutManager(context, 3));
        jobDetailsPopAdapter.setItemClickListener(new SearchJobDetailsPopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (itemClickListener != null){
                    itemClickListener.onItemClick(position,list);
                    dismiss();
                }
            }
        });
    }

    private void initView(View popView) {
        search_pop_recyclerview = popView.findViewById(R.id.search_pop_recyclerview);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position,List<SearchJobDetailsPopBean.BasicBean> list);
    }
}
