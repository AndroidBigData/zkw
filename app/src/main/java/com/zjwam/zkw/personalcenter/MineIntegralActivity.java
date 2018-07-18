package com.zjwam.zkw.personalcenter;

import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.PersonalMineIntegralAdapter;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.MineIntegralBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.NetworkUtils;
import com.zjwam.zkw.util.Reflex;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.List;

public class MineIntegralActivity extends BaseActivity {

    private TextView all_integral;
    private TabLayout mine_integral_tablayout;
    private ImageView mine_integral_back,mine_integral_nodata;
    private LRecyclerView integral_recyclerview;
    private PersonalMineIntegralAdapter mineIntegralAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1,mCurrentCounter = 0,max_items,type = 0;
    private String uid;
    private boolean isRefresh = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_integral);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        mine_integral_tablayout.addTab(mine_integral_tablayout.newTab().setText("获取记录"));
        mine_integral_tablayout.addTab(mine_integral_tablayout.newTab().setText("消费记录"));
        Reflex.setReflex(mine_integral_tablayout,56);
        mine_integral_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getPosition();
                integral_recyclerview.refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mine_integral_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mineIntegralAdapter  = new PersonalMineIntegralAdapter(getBaseContext());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(mineIntegralAdapter);
        integral_recyclerview.setAdapter(lRecyclerViewAdapter);
        integral_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        integral_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        integral_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        integral_recyclerview.setFooterViewHint("拼命加载中...", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        integral_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                getData(uid,type,page);
            }
        });
        integral_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    getData(uid,type,page);
                } else {
                    integral_recyclerview.setNoMore(true);
                }
            }
        });

        integral_recyclerview.refresh();
    }

    private void getData(final String uid, final int type, final int page) {
        OkGo.<ResponseBean<MineIntegralBean>>post(Config.URL + "api/user/jifen")
                .params("uid",uid)
                .params("type",type)
                .params("page",page)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<MineIntegralBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<MineIntegralBean>> response) {
                        if (isRefresh){
                            mineIntegralAdapter.clear();
                        }
                        ResponseBean<MineIntegralBean> data = response.body();
                        max_items = data.data.getCount();
                        all_integral.setText(String.valueOf(data.data.getAllfen()));
                        if (data.data.getJifen().size()>0){
                            addItems(data.data.getJifen());
                            mine_integral_nodata.setVisibility(View.GONE);
                        }else{
                            mine_integral_nodata.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onError(Response<ResponseBean<MineIntegralBean>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException){
                            Toast.makeText(getBaseContext(),((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        integral_recyclerview.refreshComplete(10);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        if (!NetworkUtils.isNetAvailable(getBaseContext())){
                            integral_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                                @Override
                                public void reload() {
                                    getData(uid,type,page);
                                }
                            });
                        }
                    }
                });
    }

    private void addItems(List<MineIntegralBean.getIntegralItem> jifen) {
        mineIntegralAdapter.addAll(jifen);
        mCurrentCounter += jifen.size();
    }

    private void initView() {
        all_integral = findViewById(R.id.all_integral);
        mine_integral_tablayout = findViewById(R.id.mine_integral_tablayout);
        integral_recyclerview = findViewById(R.id.integral_recyclerview);
        mine_integral_back = findViewById(R.id.mine_integral_back);
        mine_integral_nodata = findViewById(R.id.mine_integral_nodata);
    }
}
