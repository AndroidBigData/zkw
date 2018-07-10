package com.zjwam.zkw.personalcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import com.zjwam.zkw.adapter.PersonalMineNoteBookAdapter;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.PersonalNoteBookBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SimpleResponse;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.NetworkUtils;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.List;

public class MineNoteBookActivity extends BaseActivity {

    private ImageView mine_notebook_back,mine_notebook_nodata;
    private LRecyclerView mine_notebook_recyclerview;
    private PersonalMineNoteBookAdapter noteBookAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1, positions;
    private int mCurrentCounter = 0;
    private int max_items;
    private boolean isChecked = true, isRefresh = true;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_note_book);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        noteBookAdapter = new PersonalMineNoteBookAdapter(getBaseContext());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(noteBookAdapter);
        mine_notebook_recyclerview.setAdapter(lRecyclerViewAdapter);
        mine_notebook_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mine_notebook_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mine_notebook_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        mine_notebook_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        mine_notebook_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                getNoteBookData(uid, page);
            }
        });
        mine_notebook_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    getNoteBookData(uid, page);
                } else {
                    mine_notebook_recyclerview.setNoMore(true);
                }
            }
        });
        mine_notebook_recyclerview.refresh();
        noteBookAdapter.onDeleteNoteBook(new PersonalMineNoteBookAdapter.deleteNoteBook() {
            @Override
            public void OnDeleteNoteBook(int noteId, int position) {
                if (isChecked){
                    positions = position;
                    deleNoteBook(noteId);
                    isChecked = false;
                }

            }
        });
        mine_notebook_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void deleNoteBook(int noteId) {
        OkGo.<ResponseBean<SimpleResponse>>post(Config.URL + "api/user/note_del")
                .params("uid",uid)
                .params("id",noteId)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<SimpleResponse>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<SimpleResponse>> response) {
                        noteBookAdapter.remove(positions);
                        ResponseBean<SimpleResponse> msg = response.body();
                        Toast.makeText(getBaseContext(),msg.msg,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<ResponseBean<SimpleResponse>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException){
                            Toast.makeText(getBaseContext(),((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        isChecked = true;
                    }
                });
    }

    private void getNoteBookData(final String uid, final int page) {
        OkGo.<ResponseBean<PersonalNoteBookBean>>post(Config.URL + "api/user/note")
                .params("uid", uid)
                .params("page", page)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<PersonalNoteBookBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<PersonalNoteBookBean>> response) {
                        if (isRefresh) {
                            noteBookAdapter.clear();
                        }
                        ResponseBean<PersonalNoteBookBean> data = response.body();
                        max_items = data.data.getCount();
                        if (data.data.getNote().size() > 0){
                            addItems(data.data.getNote());
                            mine_notebook_nodata.setVisibility(View.GONE);
                        }else {
                            mine_notebook_nodata.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onError(Response<ResponseBean<PersonalNoteBookBean>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException) {
                            Toast.makeText(getBaseContext(), ((MyException) exception).getErrorBean().msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mine_notebook_recyclerview.refreshComplete(10);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        if (!NetworkUtils.isNetAvailable(getBaseContext())) {
                            mine_notebook_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                                @Override
                                public void reload() {
                                    getNoteBookData(uid, page);
                                }
                            });
                        }
                    }
                });
    }

    private void addItems(List<PersonalNoteBookBean.getNoteBookItem> note) {
        noteBookAdapter.addAll(note);
        mCurrentCounter += note.size();
    }

    private void initView() {
        mine_notebook_back = findViewById(R.id.mine_notebook_back);
        mine_notebook_recyclerview = findViewById(R.id.mine_notebook_recyclerview);
        mine_notebook_nodata = findViewById(R.id.mine_notebook_nodata);
    }
}
