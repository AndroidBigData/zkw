package com.zjwam.zkw.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.httputils.PersonalCenterHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.PersonalCollectionAdapter;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.PersonalCollectionBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.ZkwPreference;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

import java.util.List;

public class MineCollectionActivity extends BaseActivity {
    private LRecyclerView mine_collection_recyclerview;
    private ImageView mine_collection_back, collection_nodata;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private PersonalCollectionAdapter collectionAdapter;
    private int page = 1, positions;
    private int mCurrentCounter = 0;
    private int max_items;
    private boolean isChecked = true;
    private String uid;
    private PersonalCenterHttp personalCenterHttp;
    private Throwable exception;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_collection);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        personalCenterHttp = new PersonalCenterHttp(this);
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
                personalCenterHttp.getCollection(uid, String.valueOf(page));
                collectionAdapter.clear();
            }
        });
        mine_collection_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < max_items) {
                    page++;
                    personalCenterHttp.getCollection(uid, String.valueOf(page));
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
            public void onOffCollection(int classId, int position) {
                if (isChecked) {
                    positions = position;
                    personalCenterHttp.offCollection(uid, String.valueOf(classId));
                    isChecked = false;
                }
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(collectionAdapter.getDataList().get(position).getId()));
                bundle.putString("title", collectionAdapter.getDataList().get(position).getName());
                Intent intent = new Intent(getBaseContext(), Video2PlayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void offCollection(Response<ResponseBean<EmptyBean>> response){
        ResponseBean<EmptyBean> msg = response.body();
        Toast.makeText(getBaseContext(), msg.msg, Toast.LENGTH_SHORT).show();
        collectionAdapter.remove(positions);
        if (max_items > 0) {
            collection_nodata.setVisibility(View.GONE);
        } else {
            collection_nodata.setVisibility(View.VISIBLE);
        }
    }
    public void offCollectionError(Response<ResponseBean<EmptyBean>> response){
        Throwable throwable = response.getException();
        String error = HttpErrorMsg.getErrorMsg(throwable);
        error(error);
    }
    public void offCollectionFinish(){
        isChecked = true;
    }

    public void getCollectio(Response<ResponseBean<PersonalCollectionBean>> response) {
        ResponseBean<PersonalCollectionBean> data = response.body();
        max_items = data.data.getCount();
        if (max_items > 0) {
            addItems(data.data.getClass_list());
            collection_nodata.setVisibility(View.GONE);
        } else {
            collection_nodata.setVisibility(View.VISIBLE);
        }
    }

    public void getCollectioError(Response<ResponseBean<PersonalCollectionBean>> response) {
        exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        error(error);
    }

    public void getCollectioFinish() {
        mine_collection_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
        if (!(exception instanceof MyException)) {
            mine_collection_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    personalCenterHttp.getCollection(uid, String.valueOf(page));
                }
            });
        }
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
