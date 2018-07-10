package com.zjwam.zkw.personalcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
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
import com.zjwam.zkw.adapter.PersonalMineCommentAdapter;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.PersonalMineCommentBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.NetworkUtils;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.List;

public class MineCommentActivity extends BaseActivity {

    private LRecyclerView mine_comment_recyclerview;
    private ImageView mine_comment_back,mine_comment_nodata;
    private PersonalMineCommentAdapter mineCommentAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1,mCurrentCounter = 0,max_items;
    private String uid;
    private boolean isRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_comment);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        mineCommentAdapter = new PersonalMineCommentAdapter(getBaseContext());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(mineCommentAdapter);
        mine_comment_recyclerview.setAdapter(lRecyclerViewAdapter);
        mine_comment_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mine_comment_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mine_comment_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        mine_comment_recyclerview.setFooterViewHint("拼命加载中...", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        mine_comment_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                getData(uid,page);
            }
        });
        mine_comment_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    getData(uid,page);
                } else {
                    mine_comment_recyclerview.setNoMore(true);
                }
            }
        });

        mine_comment_recyclerview.refresh();

        mine_comment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData(final String uid, final int page) {
        OkGo.<ResponseBean<PersonalMineCommentBean>>post(Config.URL+"api/user/comment")
                .params("uid",uid)
                .params("page",page)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<PersonalMineCommentBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<PersonalMineCommentBean>> response) {
                        if (isRefresh){
                            mineCommentAdapter.clear();
                        }
                        ResponseBean<PersonalMineCommentBean> datas = response.body();
                        if (datas.data.getComment().size()>0){
                            addItems(datas.data.getComment());
                            mine_comment_nodata.setVisibility(View.GONE);
                        }else mine_comment_nodata.setVisibility(View.VISIBLE);

                        max_items = datas.data.getCount();
                    }

                    @Override
                    public void onError(Response<ResponseBean<PersonalMineCommentBean>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException){
                            Toast.makeText(getBaseContext(),((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mine_comment_recyclerview.refreshComplete(10);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        if (!NetworkUtils.isNetAvailable(getBaseContext())){
                            mine_comment_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                                @Override
                                public void reload() {
                                    getData(uid,page);
                                }
                            });
                        }
                    }
                });
    }

    private void addItems(List<PersonalMineCommentBean.getCommentItems> comment) {
        mineCommentAdapter.addAll(comment);
        mCurrentCounter += comment.size();
    }

    private void initView() {
        mine_comment_recyclerview = findViewById(R.id.mine_comment_recyclerview);
        mine_comment_back = findViewById(R.id.mine_comment_back);
        mine_comment_nodata = findViewById(R.id.mine_comment_nodata);
    }
}
