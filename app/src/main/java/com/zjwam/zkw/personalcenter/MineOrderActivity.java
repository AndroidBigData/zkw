package com.zjwam.zkw.personalcenter;

import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.httputils.PersonalCenterHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.PersonalMineOrderAdapter;
import com.zjwam.zkw.entity.PersonalOrderBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.Reflex;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.List;

public class MineOrderActivity extends BaseActivity {

    private TabLayout mine_order_tablayout;
    private ImageView mine_order_back,mine_order_nodata;
    private LRecyclerView mine_order_recyclerview;
    private PersonalMineOrderAdapter mineOrderAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1,mCurrentCounter = 0,max_items,type = 0;
    private String uid;
    private boolean isRefresh = true;
    private Throwable exception;
    private PersonalCenterHttp personalCenterHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_order);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        personalCenterHttp = new PersonalCenterHttp(this);
        mine_order_tablayout.addTab(mine_order_tablayout.newTab().setText("未付款"));
        mine_order_tablayout.addTab(mine_order_tablayout.newTab().setText("已付款"));
        Reflex.setReflex(mine_order_tablayout,66);
        mine_order_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getPosition();
                mine_order_recyclerview.refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mine_order_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mineOrderAdapter  = new PersonalMineOrderAdapter(getBaseContext());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(mineOrderAdapter);
        mine_order_recyclerview.setAdapter(lRecyclerViewAdapter);
        mine_order_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        mine_order_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mine_order_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        mine_order_recyclerview.setFooterViewHint("拼命加载中...", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        mine_order_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                personalCenterHttp.getMineOrder(uid,String.valueOf(type),String.valueOf(page));
            }
        });
        mine_order_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    personalCenterHttp.getMineOrder(uid,String.valueOf(type),String.valueOf(page));
                } else {
                    mine_order_recyclerview.setNoMore(true);
                }
            }
        });

        mine_order_recyclerview.refresh();
    }

    public void getMineOrder(Response<ResponseBean<PersonalOrderBean>> response){
        if (isRefresh){
            mineOrderAdapter.clear();
        }
        ResponseBean<PersonalOrderBean> data = response.body();
        max_items = data.data.getCount();
        if (max_items > 0){
            addItems(data.data.getPay());
            mine_order_nodata.setVisibility(View.GONE);
        }else {
            mine_order_nodata.setVisibility(View.VISIBLE);
        }
    }
    public void getMineOrderError(Response<ResponseBean<PersonalOrderBean>> response){
        exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        error(error);
    }
    public void getMineOrderFinish(){
        mine_order_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
        if (!(exception instanceof MyException)) {
            mine_order_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    personalCenterHttp.getMineOrder(uid,String.valueOf(type),String.valueOf(page));
                }
            });
        }
    }

    private void addItems(List<PersonalOrderBean.getOrderItems> pay) {
        mineOrderAdapter.addAll(pay);
        mCurrentCounter += pay.size();
    }

    private void initView() {
        mine_order_tablayout = findViewById(R.id.mine_order_tablayout);
        mine_order_back = findViewById(R.id.mine_order_back);
        mine_order_nodata = findViewById(R.id.mine_order_nodata);
        mine_order_recyclerview = findViewById(R.id.mine_order_recyclerview);
    }
}
