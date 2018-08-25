package com.zjwam.zkw.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ExamResultBean;
import com.zjwam.zkw.util.QuestionAnswerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class ExamResultAdapter extends ListBaseAdapter<ExamResultBean> {
    private LayoutInflater mLayoutInflater;
    private ExamResultBean item;
    private ViewHolder viewHolder;
    private Map<String, Drawable> drawableMap = new HashMap<>();
    private Drawable drawables;
    private HoldExamTest holdExamTest;

    public ExamResultAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.exam_result_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        item = mDataList.get(position);
        viewHolder = (ViewHolder) holder;
        if (Build.VERSION.SDK_INT >= 24) {
            viewHolder.exam_result_item_title.setText(Html.fromHtml(item.getContent(), Html.FROM_HTML_MODE_COMPACT, imageGetter, null));
        } else {
            viewHolder.exam_result_item_title.setText(Html.fromHtml(item.getContent(), imageGetter, null));
        }
        updateCheckBoxView();
        viewHolder.exam_result_item_right.setText("正确答案:" + item.getAnswer());
        viewHolder.exam_result_item_text.setText(item.getAnalyze());
        if (item.getHold() == 1) {
            viewHolder.exam_test_hold.setImageResource(R.drawable.exam_hold);
        } else {
            viewHolder.exam_test_hold.setImageResource(R.drawable.exam_unhold);
        }
        viewHolder.exam_test_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holdExamTest.setOnClick(view, position);
            }
        });
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView exam_result_item_title, exam_result_item_right, exam_result_item_text;
        private LinearLayout exam_result_item_options;
        private ImageView exam_test_hold;

        public ViewHolder(View itemView) {
            super(itemView);
            exam_result_item_title = itemView.findViewById(R.id.exam_result_item_title);
            exam_result_item_right = itemView.findViewById(R.id.exam_result_item_right);
            exam_result_item_text = itemView.findViewById(R.id.exam_result_item_text);
            exam_result_item_options = itemView.findViewById(R.id.exam_result_item_options);
            exam_test_hold = itemView.findViewById(R.id.exam_test_hold);
        }
    }


    /**
     * 添加多选按钮
     */
    private void updateCheckBoxView() {
        viewHolder.exam_result_item_options.removeAllViews();
        List<String> op = new ArrayList<>();
        for (int j = 0; j < item.getUanswer().size(); j++) {
            op.add(item.getUanswer().get(j).getContent());
        }
        for (int i = 0; i < item.getOptions().size(); i++) {
            ExamResultBean.Options option = item.getOptions().get(i);
            CheckBox checkboxView = (CheckBox) LayoutInflater.from(mContext).inflate(R.layout.item_checkbox, null);
            checkboxView.setText(QuestionAnswerUtils.getAnswerStr(i) + "：" + option.getContent());
            checkboxView.setEnabled(false);
            checkboxView.setTextColor(mContext.getResources().getColor(R.color.black));
            if (op.contains(QuestionAnswerUtils.getAnswerStr(i))) {
                if (item.getIsright() == 0) {
                    checkboxView.setButtonDrawable(R.drawable.answer_result_wrong);
                }
                checkboxView.setChecked(true);
            } else {
                checkboxView.setChecked(false);
            }
            viewHolder.exam_result_item_options.addView(checkboxView);
        }
    }


    Html.ImageGetter imageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String s) {
            //多张图片情况根据drawableMap.get(s)获取drawable
            if (drawables != null)
                return drawableMap.get(s);
            else
                initDrawable(s);
            return null;
        }
    };

    private void initDrawable(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Drawable drawable = Glide.with(mContext).load(s).submit().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (drawable != null) {
                                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                                drawableMap.put(s, drawable);
                                drawables = drawable;
                                if (Build.VERSION.SDK_INT >= 24)
                                    viewHolder.exam_result_item_title.setText(Html.fromHtml(item.getContent(), Html.FROM_HTML_MODE_COMPACT, imageGetter, null));
                                else
                                    viewHolder.exam_result_item_title.setText(Html.fromHtml(item.getContent(), imageGetter, null));
                            }
                        }
                    });
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setHoldExamTest(HoldExamTest holdExamTest) {
        this.holdExamTest = holdExamTest;
    }

    public interface HoldExamTest {
        void setOnClick(View view, int positon);
    }

}
