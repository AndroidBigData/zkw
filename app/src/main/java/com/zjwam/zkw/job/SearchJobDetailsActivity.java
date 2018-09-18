package com.zjwam.zkw.job;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.JobRecommendAdapter;
import com.zjwam.zkw.customview.SearchJobDetailsPop;
import com.zjwam.zkw.entity.JobHomeBean;
import com.zjwam.zkw.entity.SearchJobDetailsPopBean;
import com.zjwam.zkw.mvp.presenter.SearchJobDetailsPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.ISearchJobDetailsPresenter;
import com.zjwam.zkw.mvp.view.ISearchJobDetailsView;
import com.zjwam.zkw.util.KeyboardUtils;

import java.util.List;

public class SearchJobDetailsActivity extends BaseActivity implements ISearchJobDetailsView {

    private SearchJobDetailsPop searchJobDetailsPop;
    private View search_details_view;
    private TextView search_details_dq, search_details_xz, search_details_xl, search_details_gl;
    private ImageView search_details_back, search_details_nodata;
    private EditText search_details_edit;
    private List<SearchJobDetailsPopBean.BasicBean> areaList, educationList, workyearList, moneyList;
    private ISearchJobDetailsPresenter searchJobDetailsPresenter;
    private String city, name, industry, profession, dqId, xzId, xlId, glId, searchText = "";
    private JobRecommendAdapter jobRecommendAdapter;
    private int mCurrentCounter, max_items, page;
    private boolean isRefresh;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private LRecyclerView search_details_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job_result);
        initView();
        initData();
    }

    private void initData() {
        city = getIntent().getExtras().getString("city");
        name = getIntent().getExtras().getString("name");
        industry = getIntent().getExtras().getString("industry");
        profession = getIntent().getExtras().getString("profession");
        search_details_edit.setHint(name);
        searchJobDetailsPresenter = new SearchJobDetailsPresenter(this, this);
        searchJobDetailsPresenter.getSearchPop(city);

        jobRecommendAdapter = new JobRecommendAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(jobRecommendAdapter);
        search_details_recyclerview.setAdapter(lRecyclerViewAdapter);
        search_details_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        search_details_recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        search_details_recyclerview.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
        search_details_recyclerview.setFooterViewHint("拼命加载中", "-----我是有底线的-----", "网络不给力啊，点击再试一次吧");
        search_details_recyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mCurrentCounter = 0;
                if (searchText.length() > 0) {
                    searchJobDetailsPresenter.getSearchJobText(searchText, city, dqId, xzId, xlId, glId, isRefresh, String.valueOf(page));
                } else {
                    searchJobDetailsPresenter.getSearchJob(name, city, industry, profession, dqId, xzId, xlId, glId, isRefresh, String.valueOf(page));
                }
            }
        });
        search_details_recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < max_items) {
                    page++;
                    if (searchText.length() > 0) {
                        searchJobDetailsPresenter.getSearchJobText(searchText, city, dqId, xzId, xlId, glId, isRefresh, String.valueOf(page));
                    } else {
                        searchJobDetailsPresenter.getSearchJob(name, city, industry, profession, dqId, xzId, xlId, glId, isRefresh, String.valueOf(page));
                    }
                } else {
                    search_details_recyclerview.setNoMore(true);
                }
            }
        });

        search_details_recyclerview.refresh();

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(jobRecommendAdapter.getDataList().get(position).getId()));
                startActivity(new Intent(getBaseContext(), JobDetailsActivity.class).putExtras(bundle));
            }
        });

        search_details_dq.setOnClickListener(onClickListener);
        search_details_xz.setOnClickListener(onClickListener);
        search_details_xl.setOnClickListener(onClickListener);
        search_details_gl.setOnClickListener(onClickListener);
        search_details_back.setOnClickListener(onClickListener);
        search_details_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (search_details_edit.getText().toString().trim().length() > 0) {
                        searchText = search_details_edit.getText().toString();
                        search_details_recyclerview.refresh();
                    } else {
                        error("输入内容不得为空！");
                    }
                    KeyboardUtils.hideKeyboard(search_details_edit);
                }
                return false;
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.search_details_dq:
                    if (areaList != null && areaList.size() > 0) {
                        searchJobDetailsPop = new SearchJobDetailsPop(SearchJobDetailsActivity.this, areaList);
                        search_details_dq.setTextColor(getResources().getColor(R.color.colorAccent));
                        search_details_dq.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_up), null);
                        popListener();
                        searchJobDetailsPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                search_details_dq.setTextColor(getResources().getColor(R.color.personal_header));
                                search_details_dq.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_down), null);
                            }
                        });
                    }
                    break;
                case R.id.search_details_xz:
                    if (educationList != null && educationList.size() > 0) {
                        searchJobDetailsPop = new SearchJobDetailsPop(SearchJobDetailsActivity.this, moneyList);
                        search_details_xz.setTextColor(getResources().getColor(R.color.colorAccent));
                        search_details_xz.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_up), null);
                        popListener();
                        searchJobDetailsPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                search_details_xz.setTextColor(getResources().getColor(R.color.personal_header));
                                search_details_xz.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_down), null);
                            }
                        });
                    }
                    break;
                case R.id.search_details_xl:
                    if (moneyList != null && moneyList.size() > 0) {
                        searchJobDetailsPop = new SearchJobDetailsPop(SearchJobDetailsActivity.this, educationList);
                        search_details_xl.setTextColor(getResources().getColor(R.color.colorAccent));
                        search_details_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_up), null);
                        popListener();
                        searchJobDetailsPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                search_details_xl.setTextColor(getResources().getColor(R.color.personal_header));
                                search_details_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_down), null);
                            }
                        });
                    }
                    break;
                case R.id.search_details_gl:
                    if (workyearList != null && workyearList.size() > 0) {
                        searchJobDetailsPop = new SearchJobDetailsPop(SearchJobDetailsActivity.this, workyearList);
                        search_details_gl.setTextColor(getResources().getColor(R.color.colorAccent));
                        search_details_gl.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_up), null);
                        popListener();
                        searchJobDetailsPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                search_details_gl.setTextColor(getResources().getColor(R.color.personal_header));
                                search_details_gl.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_down), null);
                            }
                        });
                    }
                    break;
                case R.id.search_details_back:
                    finish();
                    break;

            }
        }
    };

    private void popListener() {
        searchJobDetailsPop.showAsDropDown(search_details_view);
        searchJobDetailsPop.setItemClickListener(new SearchJobDetailsPop.OnItemClickListener() {
            @Override
            public void onItemClick(int position, List<SearchJobDetailsPopBean.BasicBean> list) {
                if (list.containsAll(areaList)) {
                    search_details_dq.setText(list.get(position).getName());
                    dqId = String.valueOf(list.get(position).getId());
                } else if (list.containsAll(moneyList)) {
                    search_details_xz.setText(list.get(position).getName());
                    xzId = String.valueOf(list.get(position).getId());
                } else if (list.containsAll(educationList)) {
                    search_details_xl.setText(list.get(position).getName());
                    xlId = String.valueOf(list.get(position).getId());
                } else if (list.containsAll(workyearList)) {
                    search_details_gl.setText(list.get(position).getName());
                    glId = String.valueOf(list.get(position).getId());
                }
                search_details_recyclerview.refresh();
                search_details_dq.setTextColor(getResources().getColor(R.color.personal_header));
                search_details_dq.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_down), null);
                search_details_xz.setTextColor(getResources().getColor(R.color.personal_header));
                search_details_xz.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_down), null);
                search_details_xl.setTextColor(getResources().getColor(R.color.personal_header));
                search_details_xl.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_down), null);
                search_details_gl.setTextColor(getResources().getColor(R.color.personal_header));
                search_details_gl.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.jt_down), null);
            }
        });
    }

    private void initView() {
        search_details_view = findViewById(R.id.search_details_view);
        search_details_dq = findViewById(R.id.search_details_dq);
        search_details_xz = findViewById(R.id.search_details_xz);
        search_details_xl = findViewById(R.id.search_details_xl);
        search_details_gl = findViewById(R.id.search_details_gl);
        search_details_back = findViewById(R.id.search_details_back);
        search_details_edit = findViewById(R.id.search_details_edit);
        search_details_recyclerview = findViewById(R.id.search_details_recyclerview);
        search_details_nodata = findViewById(R.id.search_details_nodata);
    }

    @Override
    public void setSearch(List<SearchJobDetailsPopBean.BasicBean> area, List<SearchJobDetailsPopBean.BasicBean> education, List<SearchJobDetailsPopBean.BasicBean> workyear, List<SearchJobDetailsPopBean.BasicBean> money) {
        areaList = area;
        moneyList = money;
        educationList = education;
        workyearList = workyear;
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }

    @Override
    public void setSearchJob(List<JobHomeBean.Resume> list, int count) {
        max_items = count;
        if (count > 0) {
            jobRecommendAdapter.addAll(list);
            mCurrentCounter += list.size();
            search_details_nodata.setVisibility(View.GONE);
        } else {
            search_details_nodata.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void refresh() {
        jobRecommendAdapter.clear();
    }

    @Override
    public void freshComplete() {
        search_details_recyclerview.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

}
