package com.zjwam.zkw.personalcenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
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
import com.zjwam.zkw.adapter.PersonalCollectionAdapter;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.PersonalCollectionBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SimpleResponse;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.NetworkUtils;
import com.zjwam.zkw.util.ZkwPreference;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

import java.util.List;

public class MineCollectionActivity extends BaseActivity {
    private LRecyclerView mine_collection_recyclerview;
    private ImageView mine_collection_back,collection_nodata;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private PersonalCollectionAdapter collectionAdapter;
    private int page = 1,positions;
    private int mCurrentCounter = 0;
    private int max_items;
    private boolean isChecked = true;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_collection);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        collectionAdapter = new PersonalCollectionAdapter(getBaseContext());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(collectionAdapter);
        mine_collection_recyclerview.setAdapter(lRecyclerViewAdapter);
        mine_collection_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mine_collection_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mine_collection_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        mine_collection_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        mine_collection_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                getCollectionData(uid,page);
                collectionAdapter.clear();
            }
        });
        mine_collection_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < max_items) {
                    page++;
                    getCollectionData(uid,page);
                } else {
                    mine_collection_recyclerview.setNoMore(true);
                }
            }
        });
        mine_collection_recyclerview.refresh();
        mine_collection_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        collectionAdapter.setOffCollection(new PersonalCollectionAdapter.offCollection() {
            @Override
            public void onOffCollection(int classId,int position) {
                if (isChecked){
                    positions = position;
                    offCollection(uid,classId);
                    isChecked = false;
                }
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(collectionAdapter.getDataList().get(position).getId()));
                bundle.putString("title",collectionAdapter.getDataList().get(position).getName());
                Intent intent = new Intent(getBaseContext(), Video2PlayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void offCollection(String uid, int classId) {
        OkGo.<ResponseBean<SimpleResponse>>post(Config.URL + "api/user/run_hold")
                .params("uid",uid)
                .params("id",classId)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<SimpleResponse>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<SimpleResponse>> response) {
                        ResponseBean<SimpleResponse> msg = response.body();
                        Toast.makeText(getBaseContext(),msg.msg,Toast.LENGTH_SHORT).show();
                        collectionAdapter.remove(positions);
                        if (collectionAdapter.getDataList().size() > 0){
                            collection_nodata.setVisibility(View.GONE);
                        }else {
                            collection_nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Response<ResponseBean<SimpleResponse>> response) {
                        super.onError(response);
                        Throwable throwable = response.getException();
                        if (throwable instanceof MyException){
                            Toast.makeText(getBaseContext(),((MyException) throwable).getErrorBean().msg,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        isChecked = true;
                    }
                });
    }

    private void getCollectionData(final String uid, final int page) {
        OkGo.<ResponseBean<PersonalCollectionBean>>post(Config.URL + "api/user/hold")
                .params("uid",uid)
                .params("page",page)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<PersonalCollectionBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<PersonalCollectionBean>> response) {
                        ResponseBean<PersonalCollectionBean> data = response.body();
                        max_items = data.data.getCount();
                        if (data.data.getClass_list().size() > 0){
                            addItems(data.data.getClass_list());
                            collection_nodata.setVisibility(View.GONE);
                        }else {
                            collection_nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Response<ResponseBean<PersonalCollectionBean>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException){
                            Toast.makeText(getBaseContext(),((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mine_collection_recyclerview.refreshComplete(10);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        if (!NetworkUtils.isNetAvailable(getBaseContext())) {
                            mine_collection_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                                @Override
                                public void reload() {
                                    getCollectionData(uid,page);
                                }
                            });
                        }
                    }
                });
    }

    private void addItems(List<PersonalCollectionBean.CollectionItems> class_list) {
        collectionAdapter.addAll(class_list);
        mCurrentCounter += class_list.size();
    }

    private void initView() {
        mine_collection_recyclerview = findViewById(R.id.mine_collection_recyclerview);
        mine_collection_back = findViewById(R.id.mine_collection_back);
        collection_nodata = findViewById(R.id.collection_nodata);
    }
}
