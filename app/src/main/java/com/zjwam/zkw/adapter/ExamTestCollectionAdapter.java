package com.zjwam.zkw.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ExamTestCollectionBean;
import com.zjwam.zkw.util.QuestionAnswerUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class ExamTestCollectionAdapter extends ListBaseAdapter<ExamTestCollectionBean.Paper>{
    private LayoutInflater mLayoutInflater;
    private ExamTestCollectionBean.Paper item;
    private ViewHolder viewHolder;
    private Map<String, Drawable> drawableMap = new HashMap<>();
    private Drawable drawables;

    public ExamTestCollectionAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.test_collection_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        item = mDataList.get(position);
        viewHolder = (ViewHolder) holder;
        if (Build.VERSION.SDK_INT >= 24) {
            viewHolder.text_collection_title.setText(Html.fromHtml(item.getContent(), Html.FROM_HTML_MODE_COMPACT, imageGetter, null));
        }else {
            viewHolder.text_collection_title.setText(Html.fromHtml(item.getContent(), imageGetter, null));
        }
        updateCheckBoxView();
        viewHolder.text_collection_right.setText("正确答案:"+item.getAnswer());
        viewHolder.text_collection_text.setText(item.getAnalyze());
        viewHolder.text_collection_exam_title.setText(item.getName());
        viewHolder.text_collection_addtime.setText(item.getAddtime());
        if (item.isOpen()){
            viewHolder.text_collection_answer.setVisibility(View.VISIBLE);
            viewHolder.text_collection_relativeLayout.setVisibility(View.GONE);
            item.setOpen(false);
        }else {
            viewHolder.text_collection_answer.setVisibility(View.GONE);
            viewHolder.text_collection_relativeLayout.setVisibility(View.VISIBLE);
            item.setOpen(true);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_collection_title, text_collection_right,text_collection_text,text_collection_exam_title,text_collection_addtime;
        private LinearLayout text_collection_options;
        private RelativeLayout text_collection_relativeLayout,text_collection_answer;
        public ViewHolder(View itemView) {
            super(itemView);
            text_collection_title = itemView.findViewById(R.id.text_collection_title);
            text_collection_right = itemView.findViewById(R.id.text_collection_right);
            text_collection_text = itemView.findViewById(R.id.text_collection_text);
            text_collection_options = itemView.findViewById(R.id.text_collection_options);
            text_collection_exam_title = itemView.findViewById(R.id.text_collection_exam_title);
            text_collection_addtime = itemView.findViewById(R.id.text_collection_addtime);
            text_collection_relativeLayout = itemView.findViewById(R.id.text_collection_relativeLayout);
            text_collection_answer = itemView.findViewById(R.id.text_collection_answer);
        }
    }


    /**
     * 添加多选按钮
     */
    @SuppressLint("NewApi")
    private void updateCheckBoxView() {
        viewHolder.text_collection_options.removeAllViews();
        List<String> op = new ArrayList<>();
        if (item.getFlag() == 0){
            if ("正确".equals(item.getAnswer())){
                op.add("B");
            }else if ("错误".equals(item.getAnswer())){
                op.add("A");
            }
        }else {
            String[] split = item.getAnswer().split(" ");
            for (int j=0;j<split.length;j++){
                op.add(split[j]);
            }
        }
        for (int i = 0; i < item.getOptions().size(); i++) {
            ExamTestCollectionBean.Options option = item.getOptions().get(i);
            TextView checkboxView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.test_selector, null);
            checkboxView.setText("   "+QuestionAnswerUtils.getAnswerStr(i) + "：" + option.getContent());
            checkboxView.setTextColor(mContext.getResources().getColor(R.color.black));
            if (op.contains(QuestionAnswerUtils.getAnswerStr(i))){
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.answer_radio_checked);
                checkboxView.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
            } else {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.answer_radio_normal);
                checkboxView.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
            }
            viewHolder.text_collection_options.addView(checkboxView);
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
                                    viewHolder.text_collection_title.setText(Html.fromHtml(item.getContent(), Html.FROM_HTML_MODE_COMPACT, imageGetter, null));
                                else
                                    viewHolder.text_collection_title.setText(Html.fromHtml(item.getContent(), imageGetter, null));
                            }
                        }
                    });
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
