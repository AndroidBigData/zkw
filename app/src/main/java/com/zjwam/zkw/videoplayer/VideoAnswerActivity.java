package com.zjwam.zkw.videoplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.HttpUtils.VideoAnswersHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.VideoAnswersAdapter;
import com.zjwam.zkw.customview.ReplayDialog;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.VideoAnswersBean;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.NetworkUtils;
import com.zjwam.zkw.util.RequestOptionsUtils;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.List;

public class VideoAnswerActivity extends BaseActivity {

    private ImageView video_answers_back,header_answer_img;
    private LRecyclerView video_answers_recyclerview;
    private View headerView;
    private TextView mine_answer_nickname,header_answer_time,header_answer_content,header_answer_say;
    private VideoAnswersAdapter videoAnswersAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1,mCurrentCounter = 0,max_items;
    private VideoAnswersHttp videoAnswersHttp;
    private boolean isRefresh = false,isNetWork=false;
    private String id = "",uid="";
    private ReplayDialog replayDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_answer);
        initView();
        initData();
    }

    public void getAnswers(Response<ResponseBean<VideoAnswersBean>> response){
        if (isRefresh){
            videoAnswersAdapter.clear();
        }
        VideoAnswersBean data = response.body().data;
        max_items = data.getCount();
        if (data.getChild().size()>0){
            addItems(data.getChild());
        }
        VideoAnswersBean.getQuestion question = data.getQuestion();
        mine_answer_nickname.setText(question.getNick());
        header_answer_time.setText(question.getAddtime());
        header_answer_content.setText(question.getContent());
        GlideImageUtil.setImageView(getBaseContext(),question.getPic(),header_answer_img, RequestOptionsUtils.circleTransform());
    }

    public void getAnswersError(Response<ResponseBean<VideoAnswersBean>> response){
        Throwable exception = response.getException();
        if (exception instanceof MyException){
            Toast.makeText(this,((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
        }
    }
    public void getAnswersFinish(){
        video_answers_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
        isRefresh = false;
    }

    private void addItems(List<VideoAnswersBean.getAnswers> child) {
        videoAnswersAdapter.addAll(child);
        mCurrentCounter += child.size();
    }

    private void initData() {
        id = getIntent().getStringExtra("id");
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        videoAnswersHttp = new VideoAnswersHttp(this);
        videoAnswersAdapter = new VideoAnswersAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(videoAnswersAdapter);
        lRecyclerViewAdapter.addHeaderView(headerView);
        video_answers_recyclerview.setAdapter(lRecyclerViewAdapter);
        video_answers_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        video_answers_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        video_answers_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        video_answers_recyclerview.setFooterViewHint("拼命加载中...", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        video_answers_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                isRefresh = true;
                videoAnswersHttp.getAnswers(id,page);
            }
        });
        video_answers_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isNetWork = NetworkUtils.isNetworkAvailable(VideoAnswerActivity.this);
                if (isNetWork){
                    if (mCurrentCounter<max_items){
                        page++;
                        videoAnswersHttp.getAnswers(id,page);
                    }else {
                        video_answers_recyclerview.setNoMore(true);
                    }
                }else {
                    video_answers_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            videoAnswersHttp.getAnswers(id,page);
                        }
                    });
                }
            }
        });
        video_answers_recyclerview.refresh();
        video_answers_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        header_answer_say.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ZkwPreference.getInstance(getBaseContext()).IsFlag()){
                    replayDialog = new ReplayDialog(VideoAnswerActivity.this);
                    replayDialog.show();
                }else {
                    Toast.makeText(getBaseContext(),"请先登录！",Toast.LENGTH_SHORT).show();
                }
                replayDialog.setOnBtnCommitClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (replayDialog.getContent().trim().length()>0){
                            videoAnswersHttp.getAnswersReply(id,uid,replayDialog.getContent());
                        }else {
                            Toast.makeText(getBaseContext(),"输入内容不得为空！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void getAnswersReply(Response<ResponseBean<EmptyBean>> response){
        replayDialog.dismiss();
        replayDialog.setContent("");
        Toast.makeText(getBaseContext(),response.body().msg,Toast.LENGTH_SHORT).show();
        video_answers_recyclerview.refresh();
    }
    public void getAnswersReplyError(Response<ResponseBean<EmptyBean>> response){
        Throwable exception = response.getException();
        if (exception instanceof MyException){
            Toast.makeText(getBaseContext(),((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
        }
        replayDialog.dismiss();
    }

    private void initView() {
        video_answers_back = findViewById(R.id.video_answers_back);
        video_answers_recyclerview = findViewById(R.id.video_answers_recyclerview);
        headerView = LayoutInflater.from(this).inflate(R.layout.video_answer_ask_header,(ViewGroup)findViewById(android.R.id.content),false);
        header_answer_img = headerView.findViewById(R.id.header_answer_img);
        mine_answer_nickname = headerView.findViewById(R.id.mine_answer_nickname);
        header_answer_time = headerView.findViewById(R.id.header_answer_time);
        header_answer_content = headerView.findViewById(R.id.header_answer_content);
        header_answer_say = headerView.findViewById(R.id.header_answer_say);
    }
}
