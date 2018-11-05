package com.zjwam.zkw.exam;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.customview.BasicDialog;
import com.zjwam.zkw.customview.QuestionViewPager;
import com.zjwam.zkw.entity.ExamDetailsBean;
import com.zjwam.zkw.mvp.presenter.ExamDetailsPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IExamDetailsPresenter;
import com.zjwam.zkw.mvp.view.IExamDetailsView;
import com.zjwam.zkw.util.DateUtil;
import com.zjwam.zkw.util.ZkwPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExamDetailsActivity extends BaseActivity implements QuestionFragment.OnModifyQuestionListener, View.OnClickListener, IExamDetailsView {
    private ImageView shadowView;
    private QuestionViewPager questionViewPager;
    private List<ExamDetailsBean> dataBeans;
    private int curPosition2;
    private IExamDetailsPresenter examDetailsPresenter;
    private String id, title, resultId;
    private TextView titleName;
    private JSONObject datas;
    private BasicDialog basicDialog;
    private long begin_time, end_time;
    private RelativeLayout progress_exam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details);
        id = getIntent().getExtras().getString("id");
        title = getIntent().getExtras().getString("title");
        examDetailsPresenter = new ExamDetailsPresenter(this, this);
        examDetailsPresenter.getExamDetails(id);
        progress_exam = findViewById(R.id.progress_exam);
        progress_exam.getBackground().setAlpha(100);
        progress_exam.setOnClickListener(null);
        progress_exam.setVisibility(View.VISIBLE);
        findViewById(R.id.bt_pre).setOnClickListener(this);
        findViewById(R.id.bt_next).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        titleName = findViewById(R.id.title);
        titleName.setText(title);
        findViewById(R.id.exam_up).setOnClickListener(this);
    }

    private void initReadViewPager() {
        shadowView = findViewById(R.id.shadowView);
        questionViewPager = findViewById(R.id.readerViewPager);
        questionViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                ExamDetailsBean subDataBean = dataBeans.get(position);
                QuestionFragment fragment = QuestionFragment.newInstance(subDataBean, position, getBaseContext());
                fragment.setModifyQuestionListener(ExamDetailsActivity.this);
                return fragment;
            }

            @Override
            public int getCount() {
                return dataBeans.size();
            }
        });
        questionViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                shadowView.setTranslationX(questionViewPager.getWidth() - positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                curPosition2 = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 上一题
     */
    private synchronized void preQuestion() {
        int currentItem = questionViewPager.getCurrentItem();
        currentItem = currentItem - 1;
        if (currentItem > dataBeans.size() - 1) {
            currentItem = dataBeans.size() - 1;
        }
        questionViewPager.setCurrentItem(currentItem);
    }

    /**
     * 下一题
     */
    private synchronized void nextQuestion() {
        int currentItem = questionViewPager.getCurrentItem();
        if (currentItem == (dataBeans.size()-1) ){
            error("没有更多了！");
        }else {
            currentItem = currentItem + 1;
            if (currentItem < 0) {
                currentItem = 0;
            }
            questionViewPager.setCurrentItem(currentItem);
        }

    }

    @Override
    public void modifyQuestion(String selectId, final int position) {
        ExamDetailsBean dataBeanTemp = dataBeans.get(position);
        /**
         * 未做过的题目 单项选择题选择后直接选择答案后延时进入下一题 ； 多项选择/题目选择后修改的  需要自行滑动活着点击下一题
         */
        if ("-1".equals(dataBeanTemp.getQuestion_select()) && dataBeanTemp.getFlag() != ExamDetailsBean.TYPE_Multiple_Choice) {
            //延时下一题
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position == curPosition2) {
                        //execute the task
                        nextQuestion();
                    }
                }
            }, 500);
        }
        dataBeanTemp.setQuestion_select(selectId);
    }

    @Override
    public synchronized void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pre:
                preQuestion();
                break;
            case R.id.bt_next:
                nextQuestion();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.exam_up:
                final List<String> data;
                data = new ArrayList<>();
                for (int i = 0; i < dataBeans.size(); i++) {
                    String aaa = dataBeans.get(i).getQuestion_select();
                    data.add(aaa);
                }
                if (data.contains("-1")) {
                    error("试卷未完成！");
                } else {
                    basicDialog = new BasicDialog(this, "确认提交试卷吗?");
                    basicDialog.setDialog(new BasicDialog.BasicDialogListener() {
                        @Override
                        public void confirm() {
                            end_time = DateUtil.getCurTimeLong();
                            try {
                                JSONArray jsonArray = StringList2Json(data);
                                datas = new JSONObject();
                                datas.put("data", jsonArray);
                                datas.put("uid", ZkwPreference.getInstance(getBaseContext()).getUid());
                                datas.put("id", id);
                                datas.put("begin_time", begin_time);
                                datas.put("end_time", end_time);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            examDetailsPresenter.upExam("", datas);
                        }

                        @Override
                        public void cancel() {
                            basicDialog.dismiss();
                        }
                    });
                    basicDialog.show();
                }
                break;
        }
    }

    @Override
    public void setExam(List<ExamDetailsBean> list) {
        progress_exam.setVisibility(View.GONE);
        dataBeans = list;
        initReadViewPager();
        begin_time = DateUtil.getCurTimeLong();
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
        progress_exam.setVisibility(View.GONE);
        finish();
    }

    @Override
    public void jump2Details(String resultId) {
        basicDialog.dismiss();
        this.resultId = resultId;
        Bundle bundle = new Bundle();
        bundle.putString("eid", resultId);
        bundle.putString("id", id);
        startActivity(new Intent(getBaseContext(), ExamResultActivity.class).putExtras(bundle));
        finish();
    }

    @Override
    public void finished() {
        finish();
    }


    public static JSONArray StringList2Json(List<String> list) {
        JSONArray json = new JSONArray();
        for (String pLog : list) {
            json.put(pLog);
        }
        return json;
    }
}
