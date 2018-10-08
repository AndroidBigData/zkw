package com.zjwam.zkw.fragment.VideoPlayer;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.customview.BasicDialog;
import com.zjwam.zkw.entity.AdvertisementBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.VideoPlayerHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.VideoCatalogAdapter;
import com.zjwam.zkw.entity.VideoCatalogBean;

import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.ZkwPreference;
import com.zjwam.zkw.entity.ClassBean;
import com.zjwam.zkw.webview.WebViewActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends Fragment {

    private LRecyclerView video_class_list;
    private VideoCatalogAdapter videoCatalogAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private String code, msg, id;
    private List<ClassBean> data;
    private Context context;
    private String uid;
    private ImageView catalog_nodata;
    private BasicDialog basicDialog;
    private boolean isRefresh = false;

    // 2.1 定义用来与外部activity交互，获取到宿主activity
    private FragmentInteraction listterner;

    // 1 定义了所有activity必须实现的接口方法
    public interface FragmentInteraction {
        void process(String path, String title);
    }
    // 当fragment被加载到activity的时候会被回调
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteraction) {
            listterner = (FragmentInteraction) context; // 2.2 获取到宿主activity并赋值
        } else {
            throw new IllegalArgumentException("activity must implements FragmentInteraction");
        }
    }

    public CatalogFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CatalogFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) {
            id = getArguments().getString("id");
        }
        init();
    }

    private void init() {
        uid = ZkwPreference.getInstance(getActivity()).getUid();
        video_class_list = getActivity().findViewById(R.id.video_class_list);
        catalog_nodata = getActivity().findViewById(R.id.catalog_nodata);

        new VideoPlayerHttp(context).getVideoCatalog(id,uid);
    }

    public void getVideoCatalog(Response<VideoCatalogBean> response) {
        data = response.body().getVideo();
        code = String.valueOf(response.body().getCode());
        msg = response.body().getMsg();
        if (data.size()>0){


            videoCatalogAdapter = new VideoCatalogAdapter(getActivity(),code);

//            videoCatalogAdapter.clear();

            lRecyclerViewAdapter = new LRecyclerViewAdapter(videoCatalogAdapter);
            video_class_list.setAdapter(lRecyclerViewAdapter);
            video_class_list.setLayoutManager(new LinearLayoutManager(getActivity()));
            video_class_list.setLoadMoreEnabled(false);

            videoCatalogAdapter.setDataList(data);
            catalog_nodata.setVisibility(View.GONE);
        }else {
            catalog_nodata.setVisibility(View.VISIBLE);
        }

        video_class_list.setPullRefreshEnabled(false);
        video_class_list.setLoadMoreEnabled(false);

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String path = data.get(position).getAddress();
                String title = data.get(position).getVname();
                ZkwPreference.getInstance(getActivity()).setVideoId(String.valueOf(data.get(position).getId()));
                if (ZkwPreference.getInstance(getActivity()).IsFlag()) {
                    if ("1".equals(code)) {
                        listterner.process(path, title);
                        videoCatalogAdapter.setThisPosition(position);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        if (position<1){
                            listterner.process(path, title);
                            videoCatalogAdapter.setThisPosition(position);
                            lRecyclerViewAdapter.notifyDataSetChanged();
                        }else {
                            basicDialog = new BasicDialog(context,"开通学习卡后观看完整版");
                            basicDialog.show();
                            basicDialog.setDialog(new BasicDialog.BasicDialogListener() {
                                @Override
                                public void confirm() {
                                    jump2webView();
                                }

                                @Override
                                public void cancel() {
                                    basicDialog.dismiss();
                                }
                            });
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void jump2webView() {
        OkGo.<ResponseBean<AdvertisementBean>>get(Config.URL + "api/index/get_ads?type=android")
                .tag(this)
                .execute(new JsonCallback<ResponseBean<AdvertisementBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<AdvertisementBean>> response) {
                        ResponseBean<AdvertisementBean> data = response.body();
                        String url = data.data.getAd().getUrl();
                        String title = data.data.getAd().getTitle();
                        Bundle bundle = new Bundle();
                        bundle.putString("title", title);
                        bundle.putString("url", url);
                        bundle.putString("type","type");
                        startActivity(new Intent(getActivity(), WebViewActivity.class).putExtras(bundle));
                    }

                    @Override
                    public void onError(Response<ResponseBean<AdvertisementBean>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException) {
                            Toast.makeText(getActivity(), ((MyException) exception).getErrorBean().msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        basicDialog.dismiss();
                    }
                });
    }

    public void getVideoCatalogFinish() {
        video_class_list.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }
}
