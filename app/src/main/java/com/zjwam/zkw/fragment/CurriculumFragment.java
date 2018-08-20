package com.zjwam.zkw.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.httputils.MainActivityHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.CurriculumCheckedAdapter;
import com.zjwam.zkw.adapter.SearchListAdapter;
import com.zjwam.zkw.entity.CateDatasBean;
import com.zjwam.zkw.entity.ClassInfo;
import com.zjwam.zkw.entity.ClassSearchBean;
import com.zjwam.zkw.entity.CurriculumLnitializationBean;
import com.zjwam.zkw.search.SearchActivity;
import com.zjwam.zkw.customview.CurriculumPopupWindow;
import com.zjwam.zkw.util.NetworkUtils;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurriculumFragment extends Fragment {

    private LRecyclerView curriculum_recyclerview;
    private SearchListAdapter searchListAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int page = 1;
    private TabLayout curriculum_tablayout;
    private String wid = "", id1, id2, id3;
    private int mCurrentCounter = 0;
    private int max_items;
    private List<CateDatasBean> titles;
    private RelativeLayout curriculum_checked;
    private ImageView curriculum_checked_choice;
    private CurriculumCheckedAdapter curriculumCheckedAdapter;
    private List<String> classNames, classIds;
    private RecyclerView curriculum_checked_recyclerview;
    private boolean isTitleRefresh = true, isTitleLoadMore = true;
    private String id = "", getwid = "", getid = "", getname = "";
    private TextView search_title_curriculum, curriculum_checked_text;
    private Context context;
    private MainActivityHttp mainActivityHttp;

    public CurriculumFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CurriculumFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curriculum, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.size() > 0) {
            getwid = bundle.getString("wid");
            getid = bundle.getString("id");
            getname = bundle.getString("name");
            bundle.clear();
        }
        initView();
        initData();
    }

    private void initView() {
        curriculum_tablayout = getActivity().findViewById(R.id.curriculum_tablayout);
        curriculum_recyclerview = getActivity().findViewById(R.id.curriculum_recyclerview);
        curriculum_checked = getActivity().findViewById(R.id.curriculum_checked);
        curriculum_checked_choice = getActivity().findViewById(R.id.curriculum_checked_choice);
        curriculum_checked_recyclerview = getActivity().findViewById(R.id.curriculum_checked_recyclerview);
        search_title_curriculum = getActivity().findViewById(R.id.search_title_curriculum);
        curriculum_checked_text = getActivity().findViewById(R.id.curriculum_checked_text);
    }

    private void initData() {
        mainActivityHttp = new MainActivityHttp(context);
        search_title_curriculum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        curriculum_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        classNames = new ArrayList<>();
        classIds = new ArrayList<>();
        mainActivityHttp.getInitialization();
        searchListAdapter = new SearchListAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(searchListAdapter);
        curriculum_recyclerview.setAdapter(lRecyclerViewAdapter);
        curriculum_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        curriculum_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        curriculum_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        curriculum_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        curriculum_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCurrentCounter = 0;
                if (isTitleRefresh) {
                    mainActivityHttp.getData(wid, page);
                } else {
                    mainActivityHttp.getChoiceListData(id);
                }
                searchListAdapter.clear();
            }
        });
        curriculum_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < max_items) {
                    page++;
                    if (isTitleLoadMore) {
                        mainActivityHttp.getData(wid, page);
                    } else {
                        mainActivityHttp.getChoiceListData(id);
                    }
                } else {
                    curriculum_recyclerview.setNoMore(true);
                }
            }
        });

        curriculum_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (classNames.size() > 0 || classIds.size() > 0) {
                    classNames.clear();
                    classIds.clear();
                    curriculumCheckedAdapter.notifyDataSetChanged();
                }
                if (getwid.length() > 0) {
                    wid = getwid;
                    if (getid.length() > 0 && getname.length() > 0) {
                        if ("0".equals(wid)) {
                            curriculum_checked.setVisibility(View.GONE);
                        } else {
                            curriculum_checked.setVisibility(View.VISIBLE);
                        }
                        classNames.clear();
                        classIds.clear();
                        classNames.add(getname);
                        classIds.add("first" + getid);
                        initRefreshData();
                        return;
                    }
                } else {
                    wid = String.valueOf(titles.get(tab.getPosition()).getId());
                    if (classNames.size() > 0) {
                        curriculum_checked_text.setVisibility(View.GONE);
                    } else {
                        curriculum_checked_text.setVisibility(View.VISIBLE);
                    }
                }
                isTitleLoadMore = true;
                isTitleRefresh = true;
                curriculum_recyclerview.refresh();
                if ("0".equals(wid)) {
                    curriculum_checked.setVisibility(View.GONE);
                } else {
                    curriculum_checked.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (getid.length() > 0 && getname.length() > 0) {
                    classNames.clear();
                    classIds.clear();
                    classNames.add(getname);
                    classIds.add("first" + getid);
                    initRefreshData();
                    getid = "";
                    getname = "";
                }
            }
        });

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(searchListAdapter.getDataList().get(position).getId()));
                bundle.putString("bg", searchListAdapter.getDataList().get(position).getImg());
                bundle.putString("title", searchListAdapter.getDataList().get(position).getName());
                Intent intent = new Intent(getActivity(), Video2PlayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        curriculum_checked_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityHttp.getChoiceData(wid);
                curriculum_checked_choice.setClickable(false);
            }
        });

    }

    public void getChoiceData(Response<ClassSearchBean> response) {
        ClassSearchBean classSearchBean = response.body();
        CurriculumPopupWindow curriculumPopupWindow = new CurriculumPopupWindow(getActivity(), classSearchBean);
        curriculumPopupWindow.showAsDropDown(curriculum_tablayout);
        curriculumPopupWindow.setOnClickListener(new CurriculumPopupWindow.onClickListener() {
            @Override
            public void onClick(final List<String> className, List<String> classId) {
                Log.i("---className:", className.toString());
                Log.i("---classId:", classId.toString());
                classNames.clear();
                classIds.clear();
                classNames = className;
                classIds = classId;
                initRefreshData();
            }
        });

    }

    public void getChoiceDatFinish() {
        curriculum_checked_choice.setClickable(true);
    }

    private void initRefreshData() {
        isTitleLoadMore = false;
        isTitleRefresh = false;
        if (classNames.size() > 0) {
            curriculum_checked_text.setVisibility(View.GONE);
        } else {
            curriculum_checked_text.setVisibility(View.VISIBLE);
        }
        curriculumCheckedAdapter = new CurriculumCheckedAdapter(getActivity(), classNames);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        curriculum_checked_recyclerview.setLayoutManager(linearLayoutManager);
        curriculum_checked_recyclerview.setAdapter(curriculumCheckedAdapter);
        getId(classIds);
        curriculumCheckedAdapter.setItemClickListener(new CurriculumCheckedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                classNames.remove(position);
                classIds.remove(position);
                curriculumCheckedAdapter.notifyDataSetChanged();
                getId(classIds);
            }
        });
    }

    private void getId(List<String> classIds) {
        id = "";
        if (classIds.size() > 0) {
            for (int i = 0; i < classIds.size(); i++) {
                id = id + classIds.get(i) + "_";
            }
            curriculum_recyclerview.refresh();
        } else {
            isTitleLoadMore = true;
            isTitleRefresh = true;
            if (classNames.size() > 0) {
                curriculum_checked_text.setVisibility(View.GONE);
            } else {
                curriculum_checked_text.setVisibility(View.VISIBLE);
            }
            curriculum_recyclerview.refresh();
        }
    }

    public void getChoiceListData(Response<CurriculumLnitializationBean> response) {
        addItems(response.body().getClass_list());
        max_items = response.body().getCount();
    }

    public void getChoiceListDataFinish() {
        curriculum_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
        if (!NetworkUtils.isNetAvailable(context)) {
            curriculum_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    mainActivityHttp.getChoiceListData(id);
                }
            });
        }
    }


    public void getData(Response<CurriculumLnitializationBean> response) {
        addItems(response.body().getClass_list());
        max_items = response.body().getCount();
    }

    public void getDataFinish() {
        curriculum_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();

        if (!NetworkUtils.isNetAvailable(context)) {
            curriculum_recyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    mainActivityHttp.getData(wid, page);
                }
            });
        }
    }

    private void addItems(List<ClassInfo> list) {

        searchListAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    public void getInitialization(Response<CurriculumLnitializationBean> response) {
        titles = response.body().getCate();
        for (int i = 0; i < titles.size(); i++) {
            curriculum_tablayout.addTab(curriculum_tablayout.newTab().setText(titles.get(i).getName()));
        }
        if (getwid.length() > 0) {
            List<String> ids = new ArrayList<>();
            for (int i = 0; i < titles.size(); i++) {
                ids.add(String.valueOf(titles.get(i).getId()));
            }
            int position = ids.indexOf(getwid);
            curriculum_tablayout.getTabAt(position).select();
            getwid = "";

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Bundle bundle = getArguments();
            if (bundle != null && bundle.size() > 0) {
                getwid = bundle.getString("wid");
                getid = bundle.getString("id");
                getname = bundle.getString("name");
                bundle.clear();
                if (getwid.length() > 0) {
                    List<String> ids = new ArrayList<>();
                    for (int i = 0; i < titles.size(); i++) {
                        ids.add(String.valueOf(titles.get(i).getId()));
                    }
                    int position = ids.indexOf(getwid);
                    curriculum_tablayout.getTabAt(position).select();
                    getwid = "";
                }
            }
        }
    }
}
