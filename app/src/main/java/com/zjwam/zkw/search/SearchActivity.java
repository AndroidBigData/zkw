package com.zjwam.zkw.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.zjwam.zkw.httputils.SearchViewHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.SearchListAdapter;
import com.zjwam.zkw.entity.ClassInfo;
import com.zjwam.zkw.entity.ClassTypeInfo;
import com.zjwam.zkw.entity.SearchClassBean;
import com.zjwam.zkw.util.KeyboardUtils;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {
    private RelativeLayout all_class;
    private ListView all_class_listview;
    private TextView search_see_all,all_class_none;
    private EditText search_title_text;
    private Button search_qx;
    private LRecyclerView search_recycler;
    private SearchListAdapter searchListAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1;
    private String classId = "",classname="";
    private List<ClassInfo> data;
    private boolean isInitCache = false,isRefresh = true;
    private SearchViewHttp searchHttp;
    private int mCurrentCounter = 0,max_items;
    private ImageView search_nodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
    }

    private void initData() {
        searchHttp = new SearchViewHttp(this);
        all_class.setOnClickListener(onClickListener);
        search_see_all.setOnClickListener(onClickListener);
        search_qx.setOnClickListener(onClickListener);
        search_title_text.setOnClickListener(onClickListener);
        search_title_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    KeyboardUtils.hideKeyboard(search_title_text);
                    searchListAdapter.clear();
                    classname = search_title_text.getText().toString().trim();
                    search_see_all.setText("查看全部课程");
                    searchHttp.searInfo(classname,"", String.valueOf(page));
                }
                return false;
            }
        });

        searchListAdapter = new SearchListAdapter(getBaseContext());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(searchListAdapter);
        search_recycler.setAdapter(lRecyclerViewAdapter);
        search_recycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        search_recycler.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //设置底部加载颜色
        search_recycler.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        //设置底部加载文字提示
        search_recycler.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");

        search_recycler.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                classname = search_title_text.getText().toString().trim();
                searchHttp.searInfo(classname,classId, String.valueOf(page));
            }
        });
        search_recycler.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < max_items) {
                    page++;
                    searchHttp.searInfo(classname,classId, String.valueOf(page));
                } else {
                    search_recycler.setNoMore(true);
                }
            }
        });
        classId = getIntent().getStringExtra("id");
        search_recycler.refresh();

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(getBaseContext(),searchListAdapter.getDataList().get(position).getId(),Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(searchListAdapter.getDataList().get(position).getId()));
                bundle.putString("bg",searchListAdapter.getDataList().get(position).getImg());
                bundle.putString("title",searchListAdapter.getDataList().get(position).getName());
                Intent intent = new Intent(getBaseContext(),Video2PlayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void addItems(List<ClassInfo> list) {
        searchListAdapter.addAll(list);
        mCurrentCounter += list.size();
    }


    public void getData(Response<SearchClassBean> response){
        max_items = response.body().getCount();
        if (isRefresh){
            searchListAdapter.clear();
        }
        data = response.body().getClass_list();
        if (max_items>0){
            addItems(data);
            search_nodata.setVisibility(View.GONE);
        }else {
            search_nodata.setVisibility(View.VISIBLE);
        }

        if (response.body().getWeb() != null){
            setClassType(response.body().getWeb());
        }

    }
    public void netError(Response<SearchClassBean> response){
        Throwable exception = response.getException();
        if (exception instanceof MyException){
            Toast.makeText(getBaseContext(),((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
        }else {
            search_recycler.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    searchHttp.searInfo(classname,classId, String.valueOf(page));
                }
            });
        }
    }
    public void loadFinish(){
        search_recycler.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
        isRefresh = false;
    }

    private void setClassType(final List<ClassTypeInfo> typeInfos) {
        if (typeInfos.size()>0||!typeInfos.isEmpty()){
            all_class_none.setVisibility(View.GONE);
            all_class_listview.setVisibility(View.VISIBLE);
            final List<String> data = new ArrayList<>();
            for (int i = 0;i<typeInfos.size();i++){
                String item = typeInfos.get(i).getName();
                data.add(item);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_list_item_1,data);
            all_class_listview.setAdapter(adapter);
            all_class_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    search_see_all.setText(data.get(i));
                    all_class.setVisibility(View.GONE);
                    searchListAdapter.clear();
                    lRecyclerViewAdapter.notifyDataSetChanged();
                    classId = typeInfos.get(i).getId();
                    searchHttp.searInfo(classname,classId, String.valueOf(page));
                }
            });
        }
    }

    private void initView() {
        all_class = findViewById(R.id.all_class);
        all_class_listview = findViewById(R.id.all_class_listview);
        search_see_all = findViewById(R.id.search_see_all);
        search_title_text = findViewById(R.id.search_title_text);
        search_qx = findViewById(R.id.search_qx);
        search_recycler = findViewById(R.id.search_lrecyclerview);
        all_class_none = findViewById(R.id.all_class_none);
        search_nodata = findViewById(R.id.search_nodata);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.all_class:
                    all_class.setVisibility(View.GONE);
                    break;
                case R.id.search_see_all:
                    all_class.setVisibility(View.VISIBLE);
                    break;
                case R.id.search_title_text:
                    all_class.setVisibility(View.GONE);
                    break;
                case R.id.search_qx:
                    finish();
                    break;
            }
        }
    };
}
