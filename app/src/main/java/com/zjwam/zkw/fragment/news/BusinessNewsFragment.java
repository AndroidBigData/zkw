package com.zjwam.zkw.fragment.news;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.NewsAdapter;
import com.zjwam.zkw.entity.NewsBean;
import com.zjwam.zkw.mvp.presenter.BusinessNewsPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IBusinessNewsPresenter;
import com.zjwam.zkw.mvp.view.IBusinessNewsView;
import com.zjwam.zkw.news.NewsActivity;
import com.zjwam.zkw.news.NewsMoreActivity;
import com.zjwam.zkw.news.NewsWebActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessNewsFragment extends Fragment implements IBusinessNewsView {
    private Context context;
    private LRecyclerView business_zmfhq_recyclerview, business_zmft_recyclerview, business_yxxm_recyclerview, business_lxxm_recyclerview, business_ldxm_recyclerview;
    private TextView business_zmfhq_more, business_zmft_more, business_yxxm_more, business_lxxm_more, business_ldxm_more;
    private NewsAdapter zmfhqAdapter, zmftAdapter, yxxmAdapter, lxxmAdapter, ldxmAdapter;
    private LRecyclerViewAdapter zmfhqRecyclerViewAdapter, zmftRecyclerViewAdapter, yxxmRecyclerViewAdapter, lxxmRecyclerViewAdapter, ldxmRecyclerViewAdapter;
    private IBusinessNewsPresenter businessNewsPresenter;
    private ImageView zmfhq_nodata,zmft_nodata,yxxm_nodata,lxxm_nodata,ldxm_nodata;
    public BusinessNewsFragment() {
        // Required empty public constructor
    }

    public static BusinessNewsFragment newInstance(Context context) {
        BusinessNewsFragment fragment = new BusinessNewsFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_business_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        businessNewsPresenter = new BusinessNewsPresenter(context,this);
        businessNewsPresenter.getNews();
        zmfhqAdapter = new NewsAdapter(context);
        zmftAdapter = new NewsAdapter(context);
        yxxmAdapter = new NewsAdapter(context);
        lxxmAdapter = new NewsAdapter(context);
        ldxmAdapter = new NewsAdapter(context);
        zmfhqRecyclerViewAdapter = new LRecyclerViewAdapter(zmfhqAdapter);
        zmftRecyclerViewAdapter = new LRecyclerViewAdapter(zmftAdapter);
        yxxmRecyclerViewAdapter = new LRecyclerViewAdapter(yxxmAdapter);
        lxxmRecyclerViewAdapter = new LRecyclerViewAdapter(lxxmAdapter);
        ldxmRecyclerViewAdapter = new LRecyclerViewAdapter(ldxmAdapter);
        business_zmfhq_recyclerview.setAdapter(zmfhqRecyclerViewAdapter);
        business_zmft_recyclerview.setAdapter(zmftRecyclerViewAdapter);
        business_yxxm_recyclerview.setAdapter(yxxmRecyclerViewAdapter);
        business_lxxm_recyclerview.setAdapter(lxxmRecyclerViewAdapter);
        business_ldxm_recyclerview.setAdapter(ldxmRecyclerViewAdapter);
        business_zmfhq_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        business_zmft_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        business_yxxm_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        business_lxxm_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        business_ldxm_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        business_zmfhq_recyclerview.setPullRefreshEnabled(false);
        business_zmfhq_recyclerview.setLoadMoreEnabled(false);
        business_zmft_recyclerview.setPullRefreshEnabled(false);
        business_zmft_recyclerview.setLoadMoreEnabled(false);
        business_yxxm_recyclerview.setPullRefreshEnabled(false);
        business_yxxm_recyclerview.setLoadMoreEnabled(false);
        business_lxxm_recyclerview.setPullRefreshEnabled(false);
        business_lxxm_recyclerview.setLoadMoreEnabled(false);
        business_ldxm_recyclerview.setPullRefreshEnabled(false);
        business_ldxm_recyclerview.setLoadMoreEnabled(false);

        zmfhqRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url",zmfhqAdapter.getDataList().get(position).getUrl());
                startActivity(new Intent(getActivity(),NewsWebActivity.class).putExtras(bundle));
            }
        });
        zmftRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url",zmftAdapter.getDataList().get(position).getUrl());
                startActivity(new Intent(getActivity(),NewsWebActivity.class).putExtras(bundle));
            }
        });
        yxxmRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url",yxxmAdapter.getDataList().get(position).getUrl());
                startActivity(new Intent(getActivity(),NewsWebActivity.class).putExtras(bundle));
            }
        });
        lxxmRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url",lxxmAdapter.getDataList().get(position).getUrl());
                startActivity(new Intent(getActivity(),NewsWebActivity.class).putExtras(bundle));
            }
        });
        ldxmRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url",ldxmAdapter.getDataList().get(position).getUrl());
                startActivity(new Intent(getActivity(),NewsWebActivity.class).putExtras(bundle));
            }
        });

        business_zmfhq_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zmfhqAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(zmfhqAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","著名孵化器");
                    startActivity(new Intent(getActivity(), NewsMoreActivity.class).putExtras(bundle));
                }
            }
        });
        business_zmft_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zmftAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(zmftAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","著名风投公司");
                    startActivity(new Intent(getActivity(), NewsMoreActivity.class).putExtras(bundle));
                }
            }
        });
        business_yxxm_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yxxmAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(yxxmAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","优秀项目");
                    startActivity(new Intent(getActivity(), NewsMoreActivity.class).putExtras(bundle));
                }
            }
        });
        business_lxxm_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lxxmAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(lxxmAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","立项项目");
                    startActivity(new Intent(getActivity(), NewsMoreActivity.class).putExtras(bundle));
                }
            }
        });
        business_ldxm_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ldxmAdapter.getDataList().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(ldxmAdapter.getDataList().get(0).getCid()));
                    bundle.putString("title","落地项目");
                    startActivity(new Intent(getActivity(), NewsMoreActivity.class).putExtras(bundle));
                }
            }
        });
    }

    private void initView() {
        business_zmfhq_recyclerview = getActivity().findViewById(R.id.business_zmfhq_recyclerview);
        business_zmft_recyclerview = getActivity().findViewById(R.id.business_zmft_recyclerview);
        business_yxxm_recyclerview = getActivity().findViewById(R.id.business_yxxm_recyclerview);
        business_lxxm_recyclerview = getActivity().findViewById(R.id.business_lxxm_recyclerview);
        business_ldxm_recyclerview = getActivity().findViewById(R.id.business_ldxm_recyclerview);
        business_zmfhq_more = getActivity().findViewById(R.id.business_zmfhq_more);
        business_zmft_more = getActivity().findViewById(R.id.business_zmft_more);
        business_yxxm_more = getActivity().findViewById(R.id.business_yxxm_more);
        business_lxxm_more = getActivity().findViewById(R.id.business_lxxm_more);
        business_ldxm_more = getActivity().findViewById(R.id.business_ldxm_more);
        zmfhq_nodata = getActivity().findViewById(R.id.zmfhq_nodata);
        zmft_nodata = getActivity().findViewById(R.id.zmft_nodata);
        yxxm_nodata = getActivity().findViewById(R.id.yxxm_nodata);
        lxxm_nodata = getActivity().findViewById(R.id.lxxm_nodata);
        ldxm_nodata = getActivity().findViewById(R.id.ldxm_nodata);
    }

    @Override
    public void setNews(List<NewsBean> zmfhq, List<NewsBean> zmft, List<NewsBean> yxxm, List<NewsBean> lxxm, List<NewsBean> ldxm) {
        if (zmfhq.size()>0){
            zmfhqAdapter.addAll(zmfhq);
            zmfhq_nodata.setVisibility(View.GONE);
        }else {
            zmfhq_nodata.setVisibility(View.VISIBLE);
        }
        if (zmft.size()>0){
            zmftAdapter.addAll(zmft);
            zmft_nodata.setVisibility(View.GONE);
        }else {
            zmft_nodata.setVisibility(View.VISIBLE);
        }
        if (yxxm.size()>0){
            yxxmAdapter.addAll(yxxm);
            yxxm_nodata.setVisibility(View.GONE);
        }else {
            yxxm_nodata.setVisibility(View.VISIBLE);
        }
        if (lxxm.size()>0){
            lxxmAdapter.addAll(lxxm);
            lxxm_nodata.setVisibility(View.GONE);
        }else {
            lxxm_nodata.setVisibility(View.VISIBLE);
        }
        if (ldxm.size()>0){
            ldxmAdapter.addAll(ldxm);
            ldxm_nodata.setVisibility(View.GONE);
        }else {
            ldxm_nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (context instanceof NewsActivity){
            ((NewsActivity) context).error(msg);
        }
    }
}
