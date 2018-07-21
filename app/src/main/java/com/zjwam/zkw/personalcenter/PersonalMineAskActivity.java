package com.zjwam.zkw.personalcenter;

import android.support.v7.app.AppCompatActivity;
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
import com.zjwam.zkw.adapter.PersonalMineAskItemAdapter;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.customview.ReplayDialog;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.PersonalMineAskItemBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.NetworkUtils;
import com.zjwam.zkw.util.ZkwPreference;
import com.zjwam.zkw.videoplayer.VideoAnswerActivity;

import java.util.List;

public class PersonalMineAskActivity extends BaseActivity {

    private ImageView mine_ask_back, header_answer_img;
    private TextView mine_answer_nickname, header_answer_time, header_answer_content, header_answer_say;
    private LRecyclerView mine_ask_lrecyclerview;
    private String uid, id;
    private int page = 1, mCurrentCounter = 0, max_items;
    ;
    private boolean isNetVork = false, isRefresh = false;
    private PersonalMineAskItemAdapter personalMineAskItemAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private ReplayDialog replayDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_mine_ask);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        id = getIntent().getStringExtra("id");
        mine_ask_back.setOnClickListener(listener);
        personalMineAskItemAdapter = new PersonalMineAskItemAdapter(getBaseContext());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(personalMineAskItemAdapter);
        mine_ask_lrecyclerview.setAdapter(lRecyclerViewAdapter);
        mine_ask_lrecyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mine_ask_lrecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mine_ask_lrecyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        mine_ask_lrecyclerview.setFooterViewHint("拼命加载中...", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        getMineAsk(uid, id, page);
        mine_ask_lrecyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                isRefresh = true;
                getMineAsk(uid, id, page);
            }
        });
        mine_ask_lrecyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isNetVork = NetworkUtils.isNetworkAvailable(PersonalMineAskActivity.this);
                if (isNetVork) {
                    if (mCurrentCounter < max_items) {
                        page++;
                        getMineAsk(uid, id, page);
                    } else {
                        mine_ask_lrecyclerview.setNoMore(true);
                    }
                } else {
                    mine_ask_lrecyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            getMineAsk(uid, id, page);
                        }
                    });
                }
            }
        });
    }

    private void getMineAsk(String uid, String id, int page) {
        OkGo.<ResponseBean<PersonalMineAskItemBean>>post(Config.URL + "api/user/question_detial")
                .params("uid", uid)
                .params("id", id)
                .params("page", page)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<PersonalMineAskItemBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<PersonalMineAskItemBean>> response) {
                        if (isRefresh) {
                            personalMineAskItemAdapter.clear();
                        }
                        PersonalMineAskItemBean data = response.body().data;
                        max_items = data.getCount();
                        PersonalMineAskItemBean.getMineAsk detial = data.getDetial();
                        GlideImageUtil.setImageView(getBaseContext(), detial.getPic(), header_answer_img, GlideImageUtil.circleTransform());
                        mine_answer_nickname.setText(detial.getNickname());
                        header_answer_time.setText(detial.getAddtime());
                        header_answer_content.setText(detial.getContent());
                        addItems(data.getDetial().getAnswer());
                    }

                    @Override
                    public void onError(Response<ResponseBean<PersonalMineAskItemBean>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException) {
                            Toast.makeText(getBaseContext(), ((MyException) exception).getErrorBean().msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mine_ask_lrecyclerview.refreshComplete(10);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        isRefresh = false;
                    }
                });
    }

    private void addItems(List<PersonalMineAskItemBean.getMineAskItem> answer) {
        personalMineAskItemAdapter.addAll(answer);
        mCurrentCounter += answer.size();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mine_ask_back:
                    finish();
                    break;
            }
        }
    };

    private void initView() {
        mine_ask_back = findViewById(R.id.mine_ask_back);
        header_answer_img = findViewById(R.id.header_answer_img);
        mine_answer_nickname = findViewById(R.id.mine_answer_nickname);
        header_answer_time = findViewById(R.id.header_answer_time);
        header_answer_content = findViewById(R.id.header_answer_content);
        header_answer_say = findViewById(R.id.header_answer_say);
        header_answer_say.setVisibility(View.GONE);
        mine_ask_lrecyclerview = findViewById(R.id.mine_ask_lrecyclerview);
    }
}