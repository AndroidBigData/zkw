package com.zjwam.zkw.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ExamResultBean;
import com.zjwam.zkw.util.QuestionAnswerUtils;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

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
    private HoldExamTest holdExamTest;
    private int positions;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        positions = position;
        item = mDataList.get(positions);
        viewHolder = (ViewHolder) holder;
        viewHolder.exam_result_item_title.setHtml(item.getContent(),new HtmlHttpImageGetter(viewHolder.exam_result_item_title));
        updateCheckBoxView();
        viewHolder.exam_result_item_right.setText("正确答案:" + item.getAnswer());
        if (item.getAnalyze()!=null && item.getAnalyze().length()>0){
            viewHolder.exam_result_item_text.setHtml(item.getAnalyze(),new HtmlHttpImageGetter(viewHolder.exam_result_item_text));
        }else {
            viewHolder.exam_result_item_text.setHtml("略");
        }
        if (item.getHold() == 1) {
            viewHolder.exam_test_hold.setImageResource(R.drawable.exam_hold);
        } else {
            viewHolder.exam_test_hold.setImageResource(R.drawable.exam_unhold);
        }
        viewHolder.exam_test_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holdExamTest.setOnClick(view, positions);
            }
        });
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  exam_result_item_right;
        private LinearLayout exam_result_item_options;
        private ImageView exam_test_hold;
        private HtmlTextView exam_result_item_title,exam_result_item_text;
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
            RelativeLayout checkBox_layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.item_checkbox, null);
            CheckBox checkboxView = checkBox_layout.findViewById(R.id.checkboxView);
            HtmlTextView html_textview = checkBox_layout.findViewById(R.id.html_textview);
            if (option.getContent().contains("\n")) {
                String item = option.getContent().replace("\n", "");
                html_textview.setHtml(QuestionAnswerUtils.getAnswerStr(i) + "：" + item,new HtmlHttpImageGetter(html_textview));
            } else {
                html_textview.setHtml(QuestionAnswerUtils.getAnswerStr(i) + "：" + option.getContent(),new HtmlHttpImageGetter(html_textview));
            }
            checkboxView.setEnabled(false);
            checkboxView.setTextColor(mContext.getResources().getColor(R.color.black));
            if (op.contains(QuestionAnswerUtils.getAnswerStr(i))) {
                if (item.getIsright() == 0) {
//                    checkboxView.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.answer_result_wrong),null,null,null);
                    checkboxView.setButtonDrawable(R.drawable.answer_result_wrong);
                }
                checkboxView.setChecked(true);
            } else {
                checkboxView.setChecked(false);
            }
            viewHolder.exam_result_item_options.addView(checkBox_layout);
        }
    }

    public void setHoldExamTest(HoldExamTest holdExamTest) {
        this.holdExamTest = holdExamTest;
    }

    public interface HoldExamTest {
        void setOnClick(View view, int positon);
    }

}
