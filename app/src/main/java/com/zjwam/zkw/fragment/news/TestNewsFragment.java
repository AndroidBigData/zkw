package com.zjwam.zkw.fragment.news;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.NewsAdapter;
import com.zjwam.zkw.customview.TestQueryResultDialog;
import com.zjwam.zkw.entity.ClassTypeInfo;
import com.zjwam.zkw.entity.NewsBean;
import com.zjwam.zkw.entity.TestQueryResultDialogBean;
import com.zjwam.zkw.mvp.presenter.TestNewsPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.ITestNewsPresenter;
import com.zjwam.zkw.mvp.view.ITestNewsView;
import com.zjwam.zkw.news.NewsActivity;
import com.zjwam.zkw.news.NewsMoreActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestNewsFragment extends Fragment implements ITestNewsView {
    private Context context;
    private Spinner test_query_spinner;
    private LRecyclerView test_hyjj_recyclerview,test_ksdt_recyclerview,test_rdzt_recyclerview,test_jcdg_recyclerview,test_jqxd_recyclerview;
    private TextView test_hyjj_more,test_ksdt_more,test_rdzt_more,test_jcdg_more,test_jqxd_more,query;
    private NewsAdapter hyjjAdapter,ksdtAdapter,rdztAdapter,jcdgAdapter,jqxdAdapter;
    private LRecyclerViewAdapter hyjjRecyclerViewAdapter,ksdtRecyclerViewAdapter,rdztRecyclerViewAdapter,jcdgRecyclerViewAdapter,jqxdRecyclerViewAdapter;
    private ITestNewsPresenter testNewsPresenter;
    private String cid;
    private List<ClassTypeInfo> newsInfo;
    private TestQueryResultDialog queryResultDialog;

    public TestNewsFragment() {
        // Required empty public constructor
    }

    public static TestNewsFragment newInstance(Context context) {

        TestNewsFragment fragment = new TestNewsFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        testNewsPresenter = new TestNewsPresenter(context,this);
        testNewsPresenter.getInfo();
        test_query_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cid = newsInfo.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cid != null){
                    testNewsPresenter.queryResult(cid);
                }
            }
        });
        testNewsPresenter.getNews("");
        hyjjAdapter = new NewsAdapter(context);
        ksdtAdapter = new NewsAdapter(context);
        rdztAdapter = new NewsAdapter(context);
        jcdgAdapter = new NewsAdapter(context);
        jqxdAdapter = new NewsAdapter(context);
        hyjjRecyclerViewAdapter = new LRecyclerViewAdapter(hyjjAdapter);
        ksdtRecyclerViewAdapter = new LRecyclerViewAdapter(ksdtAdapter);
        rdztRecyclerViewAdapter = new LRecyclerViewAdapter(rdztAdapter);
        jcdgRecyclerViewAdapter = new LRecyclerViewAdapter(jcdgAdapter);
        jqxdRecyclerViewAdapter = new LRecyclerViewAdapter(jqxdAdapter);
        test_hyjj_recyclerview.setAdapter(hyjjRecyclerViewAdapter);
        test_ksdt_recyclerview.setAdapter(ksdtRecyclerViewAdapter);
        test_rdzt_recyclerview.setAdapter(rdztRecyclerViewAdapter);
        test_jcdg_recyclerview.setAdapter(jcdgRecyclerViewAdapter);
        test_jqxd_recyclerview.setAdapter(jqxdRecyclerViewAdapter);
        test_hyjj_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        test_ksdt_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        test_rdzt_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        test_jcdg_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        test_jqxd_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        test_hyjj_recyclerview.setPullRefreshEnabled(false);
        test_hyjj_recyclerview.setLoadMoreEnabled(false);
        test_ksdt_recyclerview.setPullRefreshEnabled(false);
        test_ksdt_recyclerview.setLoadMoreEnabled(false);
        test_rdzt_recyclerview.setPullRefreshEnabled(false);
        test_rdzt_recyclerview.setLoadMoreEnabled(false);
        test_jcdg_recyclerview.setPullRefreshEnabled(false);
        test_jcdg_recyclerview.setLoadMoreEnabled(false);
        test_jqxd_recyclerview.setPullRefreshEnabled(false);
        test_jqxd_recyclerview.setLoadMoreEnabled(false);
        hyjjRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("position:",""+position);
            }
        });
        ksdtRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("position:",""+position);
            }
        });
        rdztRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("position:",""+position);
            }
        });
        jcdgRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("position:",""+position);
            }
        });
        jqxdRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("position:",""+position);
            }
        });
        test_hyjj_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hyjjAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(hyjjAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","行业焦点");
                    startActivity(new Intent(getActivity(),NewsMoreActivity.class).putExtras(bundle));
                }

            }
        });
        test_ksdt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hyjjAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(hyjjAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","考试动态");
                    startActivity(new Intent(getActivity(),NewsMoreActivity.class).putExtras(bundle));
                }

            }
        });
        test_rdzt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hyjjAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(hyjjAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","热点专题");
                    startActivity(new Intent(getActivity(),NewsMoreActivity.class).putExtras(bundle));
                }

            }
        });
        test_jcdg_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hyjjAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(hyjjAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","教材大纲");
                    startActivity(new Intent(getActivity(),NewsMoreActivity.class).putExtras(bundle));
                }

            }
        });
        test_jqxd_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hyjjAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(hyjjAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","技巧心得");
                    startActivity(new Intent(getActivity(),NewsMoreActivity.class).putExtras(bundle));
                }

            }
        });
    }
    private void initView() {
        test_query_spinner = getActivity().findViewById(R.id.test_query_spinner);
        test_hyjj_recyclerview = getActivity().findViewById(R.id.test_hyjj_recyclerview);
        test_ksdt_recyclerview = getActivity().findViewById(R.id.test_ksdt_recyclerview);
        test_rdzt_recyclerview = getActivity().findViewById(R.id.test_rdzt_recyclerview);
        test_jcdg_recyclerview = getActivity().findViewById(R.id.test_jcdg_recyclerview);
        test_jqxd_recyclerview = getActivity().findViewById(R.id.test_jqxd_recyclerview);
        query = getActivity().findViewById(R.id.query);
        test_hyjj_more = getActivity().findViewById(R.id.test_hyjj_more);
        test_ksdt_more = getActivity().findViewById(R.id.test_ksdt_more);
        test_rdzt_more = getActivity().findViewById(R.id.test_rdzt_more);
        test_jcdg_more = getActivity().findViewById(R.id.test_jcdg_more);
        test_jqxd_more = getActivity().findViewById(R.id.test_jqxd_more);
    }

    @Override
    public void setNews(List<NewsBean> hyjj, List<NewsBean> ksdt, List<NewsBean> rdzt, List<NewsBean> jcdg, List<NewsBean> jqxd) {
        hyjjAdapter.addAll(hyjj);
        ksdtAdapter.addAll(ksdt);
        rdztAdapter.addAll(rdzt);
        jcdgAdapter.addAll(jcdg);
        jqxdAdapter.addAll(jqxd);
    }

    @Override
    public void setInfo(List<ClassTypeInfo> newsInfo) {
        List<String> data = new ArrayList<>();
        this.newsInfo = newsInfo;
        for (ClassTypeInfo item:newsInfo) {
            data.add(item.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        test_query_spinner.setAdapter(adapter);
        test_query_spinner.setSelection(0);
    }

    @Override
    public void showMsg(String msg) {
        if (context instanceof NewsActivity){
            ((NewsActivity) context).error(msg);
        }
    }

    @Override
    public void showDialog(List<TestQueryResultDialogBean> data) {
        queryResultDialog = new TestQueryResultDialog(context,data);
        queryResultDialog.show();
    }
}
