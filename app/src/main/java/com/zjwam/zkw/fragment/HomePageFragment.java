package com.zjwam.zkw.fragment;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.HomePageClassMoreAdapter;
import com.zjwam.zkw.adapter.HomePageKCTJAdapter;
import com.zjwam.zkw.adapter.ViewPagerAdapter;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.entity.ClassInfo;
import com.zjwam.zkw.entity.HomePageBean;
import com.zjwam.zkw.entity.HomePageKCTJInfo;
import com.zjwam.zkw.entity.HomePageKCTJItemImgs;
import com.zjwam.zkw.entity.HomePageKCTJItemInfo;
import com.zjwam.zkw.entity.LunboBean;
import com.zjwam.zkw.fragment.HomePageCHLX.Item3Fragment;
import com.zjwam.zkw.fragment.HomePageCHLX.Item4Fragment;
import com.zjwam.zkw.fragment.HomePageCKKJ.Item5Fragment;
import com.zjwam.zkw.fragment.HomePageCKKJ.Item6Fragment;
import com.zjwam.zkw.fragment.HomePageYJS.Item2Fragment;
import com.zjwam.zkw.fragment.HomePageYJS.Iten1Fragment;
import com.zjwam.zkw.jsondata.HomePageJson2Class;
import com.zjwam.zkw.search.SearchActivity;
import com.zjwam.zkw.util.BadNetWork;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.ItemCallBack;
import com.zjwam.zkw.util.PageChangeListener;
import com.zjwam.zkw.util.RequestOptionsUtils;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;



import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment {

    private ListView kctj_list,list_more;
    private HomePageKCTJAdapter homePageKCTJAdapter;
    private MZBannerView mMZBanner;;
    private List<String> lunbo_data;
    private ViewPager yjs_vp,cglx_vp,ckkj_vp;
    private RadioGroup yjs_rg,cglx_rg,ckkj_rg;
    private Fragment yjs_item1fragment,yjs_item2fragment,cglx_item1fragment,cglx_item2fragment,ckkj_item1fragment,ckkj_item2fragment;
    private RadioButton childButton;
    private List<Fragment> yjs_fragmentList,cglx_fragment,ckkj_fragment;
    private ViewPagerAdapter vpAdapter;
    private HomePageJson2Class dataclass;
    private List<LunboBean> lunboBean;
    private List<HomePageKCTJInfo> homePageKCTJInfos;
    private RelativeLayout homepage_search;
    private LinearLayout dh_xzx,dh_zkw,dh_mnw,dh_yjs;
    private List<ClassInfo> data;
    private boolean isInitCache = false;
    private onJumpListener onJumpListener;
    private String wid,id,name;
    private HomePageBean dataes;

    private ImageView yjs_item_bg,yjs_item_bg2;
    private TextView yjs_item1,yjs_item2,yjs_item3,yjs_item4,yjs_item5,yjs_item6;
    private LinearLayout yjs_item_linearlayout1,yjs_item_linearlayout2;
    private List<HomePageKCTJItemImgs> itemImgstest;
    private List<HomePageKCTJItemInfo> itemInfostest;
    public RequestOptions options;

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        loadData(Config.URL + "api/Index/index");
    }

    private void initData() {

        yjs_fragmentList = new ArrayList<>();
        yjs_item1fragment = new Iten1Fragment();
        yjs_fragmentList.add(yjs_item1fragment);
        yjs_item2fragment = new Item2Fragment();
        yjs_fragmentList.add(yjs_item2fragment);
        vpAdapter = new ViewPagerAdapter(getChildFragmentManager(),yjs_fragmentList);
        yjs_vp.setAdapter(vpAdapter);
        yjs_vp.setCurrentItem(0);
        yjs_vp.addOnPageChangeListener(new PageChangeListener(childButton,yjs_rg).onPageChangeListener);

        cglx_fragment = new ArrayList<>();
        cglx_item1fragment = new Item3Fragment();
        cglx_fragment.add(cglx_item1fragment);
        cglx_item2fragment = new Item4Fragment();
        cglx_fragment.add(cglx_item2fragment);
        vpAdapter = new ViewPagerAdapter(getChildFragmentManager(),cglx_fragment);
        cglx_vp.setAdapter(vpAdapter);
        cglx_vp.setCurrentItem(0);
        cglx_vp.addOnPageChangeListener(new PageChangeListener(childButton,cglx_rg).onPageChangeListener);

        ckkj_fragment = new ArrayList<>();
        ckkj_item1fragment = new Item5Fragment();
        ckkj_fragment.add(ckkj_item1fragment);
        ckkj_item2fragment = new Item6Fragment();
        ckkj_fragment.add(ckkj_item2fragment);
        vpAdapter = new ViewPagerAdapter(getChildFragmentManager(),ckkj_fragment);
        ckkj_vp.setAdapter(vpAdapter);
        ckkj_vp.setCurrentItem(0);
        ckkj_vp.addOnPageChangeListener(new PageChangeListener(childButton,ckkj_rg).onPageChangeListener);

        homepage_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        dh_xzx.setOnClickListener(onClickListener);
        dh_zkw.setOnClickListener(onClickListener);
        dh_mnw.setOnClickListener(onClickListener);
        dh_yjs.setOnClickListener(onClickListener);
        list_more.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(data.get(i).getId()));
                bundle.putString("bg",data.get(i).getImg());
                bundle.putString("title",data.get(i).getName());
                Intent intent = new Intent(getActivity(),Video2PlayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        yjs_item_bg.setOnClickListener(onClickListener);
        yjs_item_bg2.setOnClickListener(onClickListener);
        yjs_item1.setOnClickListener(onClickListener);
        yjs_item2.setOnClickListener(onClickListener);
        yjs_item3.setOnClickListener(onClickListener);
        yjs_item4.setOnClickListener(onClickListener);
        yjs_item5.setOnClickListener(onClickListener);
        yjs_item6.setOnClickListener(onClickListener);
    }
private View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
//        Bundle bundle = new Bundle();
//        Intent intent = new Intent(getActivity(),SearchActivity.class);
        switch (view.getId()){
            case R.id.dh_xzx:
//                bundle.putString("id","2");
//                intent.putExtras(bundle);
//                startActivity(intent);
                if (onJumpListener != null){
                    onJumpListener.onClick("2","","");
                }
                break;
            case R.id.dh_zkw:
//                bundle.putString("id","1");
//                intent.putExtras(bundle);
//                startActivity(intent);
                if (onJumpListener != null){
                    onJumpListener.onClick("1","","");
                }
                break;
            case R.id.dh_mnw:
//                bundle.putString("id","3");
//                intent.putExtras(bundle);
//                startActivity(intent);
                if (onJumpListener != null){
                    onJumpListener.onClick("3","","");
                }
                break;
            case R.id.dh_yjs:
//                bundle.putString("id","3");
//                intent.putExtras(bundle);
//                startActivity(intent);
                if (onJumpListener != null){
                    onJumpListener.onClick("4","","");
                }
                break;
            case R.id.yjs_item_bg:
                wid = itemImgstest.get(0).getWid();
                id = itemImgstest.get(0).getId();
                name = itemImgstest.get(0).getName();
                if (onJumpListener != null){
                    onJumpListener.onClick(wid,id,name);
                }
                break;
            case R.id.yjs_item_bg2:
                wid = itemImgstest.get(1).getWid();
                id = itemImgstest.get(1).getId();
                name = itemImgstest.get(1).getName();
                if (onJumpListener != null){
                    onJumpListener.onClick(wid,id,name);
                }
                break;
            case R.id.yjs_item1:
                wid = itemInfostest.get(0).getWid();
                id = itemInfostest.get(0).getId();
                name = itemInfostest.get(0).getName();
                if (onJumpListener != null){
                    onJumpListener.onClick(wid,id,name);
                }
                break;
            case R.id.yjs_item2:
                wid = itemInfostest.get(1).getWid();
                id = itemInfostest.get(1).getId();
                name = itemInfostest.get(1).getName();
                if (onJumpListener != null){
                    onJumpListener.onClick(wid,id,name);
                }
                break;
            case R.id.yjs_item3:
                wid = itemInfostest.get(2).getWid();
                id = itemInfostest.get(2).getId();
                name = itemInfostest.get(2).getName();
                if (onJumpListener != null){
                    onJumpListener.onClick(wid,id,name);
                }
                break;
            case R.id.yjs_item4:
                wid = itemInfostest.get(3).getWid();
                id = itemInfostest.get(3).getId();
                name = itemInfostest.get(3).getName();
                if (onJumpListener != null){
                    onJumpListener.onClick(wid,id,name);
                }
                break;
            case R.id.yjs_item5:
                wid = itemInfostest.get(4).getWid();
                id = itemInfostest.get(4).getId();
                name = itemInfostest.get(4).getName();
                if (onJumpListener != null){
                    onJumpListener.onClick(wid,id,name);
                }
                break;
            case R.id.yjs_item6:
                wid = itemInfostest.get(5).getWid();
                id = itemInfostest.get(5).getId();
                name = itemInfostest.get(5).getName();
                if (onJumpListener != null){
                    onJumpListener.onClick(wid,id,name);
                }
                break;
        }
    }
};
    private void loadData(String url) {

//        OkGo.<String>get(url)
//                .cacheKey("HomePageFragment")
//                .tag(this)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        dataclass = new HomePageJson2Class(response.body());
//                        initLunbo();
//                        initKCTJ();
//                        initClassMore();
//                        initData();
//                    }
//
//                    @Override
//                    public void onCacheSuccess(Response<String> response) {
//                        super.onCacheSuccess(response);
//                        if (!isInitCache) {
//                            //一般来说,缓存回调成功和网络回调成功做的事情是一样的,所以这里直接回调onSuccess
//                            onSuccess(response);
//                            isInitCache = true;
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                        new BadNetWork().isBadNetWork(getActivity());
//                    }
//                });

        OkGo.<HomePageBean>get(url)
                .tag(this)
                .cacheKey("HomePageFragment")
                .execute(new Json2Callback<HomePageBean>() {
                    @Override
                    public void onSuccess(Response<HomePageBean> response) {
                        dataes = response.body();
                        initLunbo();
                        initKCTJ();
//                        initClassMore();
                        initYJS();
                        initData();
                    }
                    @Override
                    public void onCacheSuccess(Response<HomePageBean> response) {
                        super.onCacheSuccess(response);
                        if (!isInitCache) {
                            //一般来说,缓存回调成功和网络回调成功做的事情是一样的,所以这里直接回调onSuccess
                            onSuccess(response);
                            isInitCache = true;
                        }
                    }

                    @Override
                    public void onError(Response<HomePageBean> response) {
                        super.onError(response);
                    }
                });


//        RequestParams params = new RequestParams(url);
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                dataclass = new HomePageJson2Class(result);
//                initLunbo();
//                initKCTJ();
//                initClassMore(result);
//                initData();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                new BadNetWork().isBadNetWork(getActivity());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }

    private void initYJS() {
        HomePageKCTJInfo yjs = dataes.getYjs();
        itemImgstest = yjs.getItemImgs();
        itemInfostest = yjs.getItemInfos();
        GlideImageUtil.setImageView(getActivity(),itemImgstest.get(0).getImg(),yjs_item_bg,options);
        GlideImageUtil.setImageView(getActivity(),itemImgstest.get(1).getImg(),yjs_item_bg2,options);
        if (itemInfostest.size() == 1){
            yjs_item1.setText(itemInfostest.get(0).getName());
            yjs_item2.setVisibility(View.INVISIBLE);
            yjs_item3.setVisibility(View.INVISIBLE);
            yjs_item_linearlayout2.setVisibility(View.GONE);
        }else if(itemInfostest.size() == 2){
            yjs_item1.setText(itemInfostest.get(0).getName());
            yjs_item2.setText(itemInfostest.get(1).getName());
            yjs_item3.setVisibility(View.INVISIBLE);
            yjs_item_linearlayout2.setVisibility(View.GONE);
        }else if (itemInfostest.size() == 3){
            yjs_item1.setText(itemInfostest.get(0).getName());
            yjs_item2.setText(itemInfostest.get(1).getName());
            yjs_item3.setText(itemInfostest.get(2).getName());
            yjs_item_linearlayout2.setVisibility(View.GONE);
        }else if (itemInfostest.size() == 4){
            yjs_item1.setText(itemInfostest.get(0).getName());
            yjs_item2.setText(itemInfostest.get(1).getName());
            yjs_item3.setText(itemInfostest.get(2).getName());
            yjs_item4.setText(itemInfostest.get(3).getName());
            yjs_item5.setVisibility(View.INVISIBLE);
            yjs_item6.setVisibility(View.INVISIBLE);
        }else if (itemInfostest.size() == 5){
            yjs_item1.setText(itemInfostest.get(0).getName());
            yjs_item2.setText(itemInfostest.get(1).getName());
            yjs_item3.setText(itemInfostest.get(2).getName());
            yjs_item4.setText(itemInfostest.get(3).getName());
            yjs_item5.setText(itemInfostest.get(4).getName());
            yjs_item6.setVisibility(View.INVISIBLE);
        }else if (itemInfostest.size() == 6){
            yjs_item1.setText(itemInfostest.get(0).getName());
            yjs_item2.setText(itemInfostest.get(1).getName());
            yjs_item3.setText(itemInfostest.get(2).getName());
            yjs_item4.setText(itemInfostest.get(3).getName());
            yjs_item5.setText(itemInfostest.get(4).getName());
            yjs_item6.setText(itemInfostest.get(5).getName());
        }
    }

    private void initClassMore() {
//        try {
            list_more.setFocusable(false);
//            data = new ClassInfoJson2Data().getClassItem(result);
            data = dataes.getClass_list();
            HomePageClassMoreAdapter homePageClassMoreAdapter = new HomePageClassMoreAdapter(data,getActivity());
            list_more.setAdapter(homePageClassMoreAdapter);
            setListViewHeightBasedOnChildren(list_more);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


    }

    private void initKCTJ() {
        kctj_list.setFocusable(false);
//        homePageKCTJInfos = new ArrayList<>();
//        try {
//            JSONArray kctjdatas = dataclass.getKCTJData();
//
//            List<HomePageKCTJInfo> datas = new HomePageKCTJJson2Data(kctjdatas).getKCTJInfo();
//            for (int i = 0 ; i < kctjdatas.length() ; i++){
//                HomePageKCTJInfo homePageKCTJInfo = new HomePageKCTJInfo();
//                homePageKCTJInfo.setName(datas.get(i).getName());
//                homePageKCTJInfo.setCode(datas.get(i).getCode());
//                homePageKCTJInfo.setItemInfos(datas.get(i).getItemInfos());
//                homePageKCTJInfo.setItemImgs(datas.get(i).getItemImgs());
//                Log.i("---datas:",datas.get(i).getItemImgs().toString());
//                homePageKCTJInfos.add(homePageKCTJInfo);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        homePageKCTJInfos = dataes.getClass_es();

        homePageKCTJAdapter = new HomePageKCTJAdapter(getActivity(),homePageKCTJInfos,itemCallBack);
        kctj_list.setAdapter(homePageKCTJAdapter);
        setListViewHeightBasedOnChildren(kctj_list);
    }
    private ItemCallBack itemCallBack = new ItemCallBack() {
        @Override
        public void click(View view) {
            switch (view.getId()){
                case R.id.kctj_item1:
//                    Toast.makeText(getActivity(), "item-->" + (Integer) view.getTag() + ",id是-->"
//                            + homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item1)).getId(), Toast.LENGTH_SHORT).show();

                    wid = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item1)).getWid();
                    id = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item1)).getId();
                    name = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item1)).getName();
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putString("id",homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item1)).getId());
//                    Intent intent1 = new Intent(getActivity(),Main2Activity.class);
//                    intent1.putExtras(bundle1);
//                    startActivity(intent1);
                    if (onJumpListener != null){
                        onJumpListener.onClick(wid,id,name);
                    }
                    break;
                case R.id.kctj_item2:
//                    Toast.makeText(getActivity(), "item-->" + (Integer) view.getTag() + ",id是-->"
//                            + homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item2)).getId(), Toast.LENGTH_SHORT).show();
//                    Bundle bundle2 = new Bundle();
//                    bundle2.putString("id",homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item2)).getId());
//                    Intent intent2 = new Intent(getActivity(),Main2Activity.class);
//                    intent2.putExtras(bundle2);
//                    startActivity(intent2);
                    wid = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item2)).getWid();
                    id = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item2)).getId();
                    name = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item2)).getName();
                    if (onJumpListener != null){
                        onJumpListener.onClick(wid,id,name);
                    }
                    break;
                case R.id.kctj_item3:
//                    Toast.makeText(getActivity(), "item-->" + (Integer) view.getTag() + ",id是-->"
//                            + homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item3)).getId(), Toast.LENGTH_SHORT).show();
//                    Bundle bundle3 = new Bundle();
//                    bundle3.putString("id",homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item3)).getId());
//                    Intent intent3 = new Intent(getActivity(),Main2Activity.class);
//                    intent3.putExtras(bundle3);
//                    startActivity(intent3);
                    wid = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item3)).getWid();
                    id = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item3)).getId();
                    name = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item3)).getName();
                    if (onJumpListener != null){
                        onJumpListener.onClick(wid,id,name);
                    }
                    break;
                case R.id.kctj_item4:
//                    Toast.makeText(getActivity(), "item-->" + (Integer) view.getTag() + ",id是-->"
//                            + homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item4)).getId(), Toast.LENGTH_SHORT).show();
//                    Bundle bundle4 = new Bundle();
//                    bundle4.putString("id",homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item4)).getId());
//                    Intent intent4 = new Intent(getActivity(),Main2Activity.class);
//                    intent4.putExtras(bundle4);
//                    startActivity(intent4);
                    wid = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item4)).getWid();
                    id = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item4)).getId();
                    name = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item4)).getName();
                    if (onJumpListener != null){
                        onJumpListener.onClick(wid,id,name);
                    }
                    break;
                case R.id.kctj_item5:
//                    Toast.makeText(getActivity(), "item-->" + (Integer) view.getTag() + ",id是-->"
//                            + homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item5)).getId(), Toast.LENGTH_SHORT).show();
//                    Bundle bundle5 = new Bundle();
//                    bundle5.putString("id",homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item5)).getId());
//                    Intent intent5 = new Intent(getActivity(),Main2Activity.class);
//                    intent5.putExtras(bundle5);
//                    startActivity(intent5);
                    wid = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item5)).getWid();
                    id = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item5)).getId();
                    name = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item5)).getName();
                    if (onJumpListener != null){
                        onJumpListener.onClick(wid,id,name);
                    }
                    break;
                case R.id.kctj_item6:
//                    Toast.makeText(getActivity(), "item-->" + (Integer) view.getTag() + ",id是-->"
//                            + homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item6)).getId(), Toast.LENGTH_SHORT).show();
//                    Bundle bundle6 = new Bundle();
//                    bundle6.putString("id",homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item6)).getId());
//                    Intent intent6 = new Intent(getActivity(),Main2Activity.class);
//                    intent6.putExtras(bundle6);
//                    startActivity(intent6);
                    wid = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item6)).getWid();
                    id = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item6)).getId();
                    name = homePageKCTJInfos.get((Integer) view.getTag()).getItemInfos().get((Integer) view.getTag(R.id.item6)).getName();
                    if (onJumpListener != null){
                        onJumpListener.onClick(wid,id,name);
                    }
                    break;
                case R.id.kctj_item_bg:
                    wid = homePageKCTJInfos.get((Integer) view.getTag()).getItemImgs().get((Integer) view.getTag(R.id.item7)).getWid();
                    id = homePageKCTJInfos.get((Integer) view.getTag()).getItemImgs().get((Integer) view.getTag(R.id.item7)).getId();
                    name = homePageKCTJInfos.get((Integer) view.getTag()).getItemImgs().get((Integer) view.getTag(R.id.item7)).getName();
                    if (onJumpListener != null){
                        onJumpListener.onClick(wid,id,name);
                    }
                    break;
                case R.id.kctj_item_bg2:
                    wid = homePageKCTJInfos.get((Integer) view.getTag()).getItemImgs().get((Integer) view.getTag(R.id.item8)).getWid();
                    id = homePageKCTJInfos.get((Integer) view.getTag()).getItemImgs().get((Integer) view.getTag(R.id.item8)).getId();
                    name = homePageKCTJInfos.get((Integer) view.getTag()).getItemImgs().get((Integer) view.getTag(R.id.item8)).getName();
                    if (onJumpListener != null){
                        onJumpListener.onClick(wid,id,name);
                    }
                    break;

            }
        }
    };

    private void initLunbo() {
//        JSONArray lunbodatas = null;

//        try {
//            lunbodatas = dataclass.getLunboData();
//            lunboBean = new LunboJson2Data().getLunboData(lunbodatas);
//            lunbo_data = new ArrayList<>();
//            for (int i = 0 ; i < lunbodatas.length() ; i++){
//                String url = lunboBean.get(i).getImg();
//                lunbo_data.add(Config.URL + url);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        lunbo_data = new ArrayList<>();
        final List<LunboBean> lunboBean = dataes.getBanner();
        for (int i = 0 ; i < lunboBean.size() ; i++){
                String url = lunboBean.get(i).getImg();
                lunbo_data.add(Config.URL + url);
            }
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
//                Toast.makeText(getBaseContext(),"click page:"+position,Toast.LENGTH_LONG).show();
                if (lunboBean.get(position).getClid() != 0){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(lunboBean.get(position).getClid()));
                    Intent intent = new Intent(getActivity(),Video2PlayActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
        // 设置数据
        mMZBanner.setPages(lunbo_data, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mMZBanner.start();

    }

    private void initView() {
        kctj_list = getActivity().findViewById(R.id.list_kctj);
        mMZBanner = getActivity().findViewById(R.id.banner);
        yjs_rg = getActivity().findViewById(R.id.yjs_rg);
        yjs_vp = getActivity().findViewById(R.id.yjs_vp);
        cglx_rg = getActivity().findViewById(R.id.cglx_rg);
        cglx_vp = getActivity().findViewById(R.id.cglx_vp);
        ckkj_rg = getActivity().findViewById(R.id.ckkj_rg);
        ckkj_vp = getActivity().findViewById(R.id.ckkj_vp);
        homepage_search = getActivity().findViewById(R.id.homepage_search);
        dh_xzx = getActivity().findViewById(R.id.dh_xzx);
        dh_zkw = getActivity().findViewById(R.id.dh_zkw);
        dh_mnw = getActivity().findViewById(R.id.dh_mnw);
        dh_yjs = getActivity().findViewById(R.id.dh_yjs);
        list_more = getActivity().findViewById(R.id.list_more);


        yjs_item_bg = getActivity().findViewById(R.id.yjs_item_bg);
        yjs_item_bg2 = getActivity().findViewById(R.id.yjs_item_bg2);
        yjs_item1 = getActivity().findViewById(R.id.yjs_item1);
        yjs_item2 = getActivity().findViewById(R.id.yjs_item2);
        yjs_item3 = getActivity().findViewById(R.id.yjs_item3);
        yjs_item4 = getActivity().findViewById(R.id.yjs_item4);
        yjs_item5 = getActivity().findViewById(R.id.yjs_item5);
        yjs_item6 = getActivity().findViewById(R.id.yjs_item6);
        yjs_item_linearlayout1 = getActivity().findViewById(R.id.yjs_item_linearlayout1);
        yjs_item_linearlayout2 = getActivity().findViewById(R.id.yjs_item_linearlayout2);
        options = RequestOptionsUtils.roundTransform(10);
    }

    /**
     * 解决嵌套下只显示一条的问题
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
    public void setOnJumpListener(onJumpListener onJumpListener){
        this.onJumpListener = onJumpListener;
    }
    public interface onJumpListener{
        void onClick(String wid,String id,String name);
    }
    public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;
        private RequestOptions options;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = view.findViewById(R.id.banner_image);
            options = RequestOptionsUtils.commonTransform();
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
            GlideImageUtil.setImageView(context,data,mImageView,options);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();//开始轮播
    }
}
