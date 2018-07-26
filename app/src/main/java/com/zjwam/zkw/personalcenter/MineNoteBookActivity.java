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
import com.zjwam.zkw.HttpUtils.HttpErrorMsg;
import com.zjwam.zkw.HttpUtils.PersonalCenterHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.PersonalMineNoteBookAdapter;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
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
    private PersonalCenterHttp personalCenterHttp;
    private Throwable exception;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_note_book);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        personalCenterHttp = new PersonalCenterHttp(this);
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
                personalCenterHttp.getNoteBookData(uid, String.valueOf(page));
            }
        });
        mine_notebook_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    personalCenterHttp.getNoteBookData(uid, String.valueOf(page));
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
                    personalCenterHttp.deleNoteBook(uid, String.valueOf(noteId));
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

    public void deleNoteBook(Response<ResponseBean<EmptyBean>> response){
        noteBookAdapter.remove(positions);
        ResponseBean<EmptyBean> msg = response.body();
        Toast.makeText(getBaseContext(),msg.msg,Toast.LENGTH_SHORT).show();
    }
    public void deleNoteBookError(Response<ResponseBean<EmptyBean>> response){
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        error(error);
    }
    public void deleNoteBookFinish(){
        isChecked = true;
    }

    public void getNoteBookData(Response<ResponseBean<PersonalNoteBookBean>> response){
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
    public void getNoteBookDataError(Response<ResponseBean<PersonalNoteBookBean>> response){
        exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        error(error);
    }
    public void getNoteBookDataFinish(){
        mine_notebook_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
        if (!(exception instanceof MyException)){
            mine_notebook_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    personalCenterHttp.getNoteBookData(uid, String.valueOf(page));
                }
            });
        }
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
