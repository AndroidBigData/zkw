package com.zjwam.zkw.exam;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ExamDetailsBean;
import com.zjwam.zkw.util.DensityUtils;
import com.zjwam.zkw.util.QuestionAnswerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ExamDetailsBean subDataBean;
    private int position;
    private View view;
    private OnModifyQuestionListener modifyQuestionListener;
    private Map<String, Drawable> drawableMap = new HashMap<>();
    private Drawable drawables;
    private TextView textView, exam_type;
    private Context context;
    private String empty = "\u3000\u3000\u3000\u3000", op = "";
    private List<String> ops = new ArrayList<>();

    public void setModifyQuestionListener(OnModifyQuestionListener modifyQuestionListener) {
        this.modifyQuestionListener = modifyQuestionListener;
    }

    public interface OnModifyQuestionListener {
        void modifyQuestion(String answerSelect, int position);
    }

    public QuestionFragment() {
    }

    public static QuestionFragment newInstance(ExamDetailsBean subDataBean, int position, Context context) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, subDataBean);
        args.putSerializable(ARG_PARAM2, position);
        fragment.context = context;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subDataBean = (ExamDetailsBean) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question, container, false);
        initView();
        return view;
    }

    private void initView() {
        textView = view.findViewById(R.id.tv_title);
        exam_type = view.findViewById(R.id.exam_type);
        String tag = ExamDetailsBean.getQuestionTypeStr(subDataBean.getFlag());
        exam_type.setText(tag);
//        CharSequence content=tag+"  "+(position + 1) + ". " + subDataBean.getContent();
//        SpannableStringBuilder builder = new SpannableStringBuilder(content);
//        builder.setSpan(new ImageSpan(getActivity(),R.drawable.text_background){
//            @Override
//            public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
//                paint.setColor(Color.parseColor("#436EEE"));
//                paint.setTextSize(DensityUtils.sp2px(getContext(), 11));
//                canvas.drawText(text.subSequence(start, end).toString(), x+DensityUtils.dp2px(getContext(), 5), y-DensityUtils.dp2px(getContext(), 3), paint);
//                super.draw(canvas, text, start, end, x, top, y, bottom-DensityUtils.dp2px(getContext(), 1), paint);
//            }
//        }, 0, tag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (Build.VERSION.SDK_INT >= 24)
            textView.setText(Html.fromHtml(empty + (position + 1) + ". " + subDataBean.getContent(), Html.FROM_HTML_MODE_COMPACT, imageGetter, null));
        else
            textView.setText(Html.fromHtml(empty + (position + 1) + ". " + subDataBean.getContent(), imageGetter, null));

        if (subDataBean.getFlag() == ExamDetailsBean.TYPE_Multiple_Choice) {//多项选择题
            updateCheckBoxView();
        } else {
            updateRadioView();
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
                    final Drawable drawable = Glide.with(context).load(s).submit().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (drawable != null) {
                                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                                drawableMap.put(s, drawable);
                                drawables = drawable;
                                if (Build.VERSION.SDK_INT >= 24)
                                    textView.setText(Html.fromHtml(empty + (position + 1) + ". " + subDataBean.getContent(), Html.FROM_HTML_MODE_COMPACT, imageGetter, null));
                                else
                                    textView.setText(Html.fromHtml(empty + (position + 1) + ". " + subDataBean.getContent(), imageGetter, null));
                            }
                        }
                    });
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 添加多选按钮
     */
    private void updateCheckBoxView() {
        LinearLayout layout = view.findViewById(R.id.check_options);
        for (int i = 0; i < subDataBean.getOptions().size(); i++) {
            ExamDetailsBean.Options option = subDataBean.getOptions().get(i);
            CheckBox checkboxView = (CheckBox) LayoutInflater.from(getActivity()).inflate(R.layout.item_checkbox, null);
            checkboxView.setText(QuestionAnswerUtils.getAnswerStr(i) + "：" + option.getContent());
            checkboxView.setTag(i);
            if (Arrays.asList(subDataBean.getQuestion_select().split("_")).contains(QuestionAnswerUtils.getAnswerStr(i))){
                checkboxView.setChecked(true);
            } else {
                checkboxView.setChecked(false);
            }
            checkboxView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        ops.add(QuestionAnswerUtils.getAnswerStr((int) buttonView.getTag()));
                    } else {
                        for (int k = 0; k < ops.size(); k++) {
                            if (QuestionAnswerUtils.getAnswerStr((int) buttonView.getTag()).equals(ops.get(k))) {
                                ops.remove(k);
                            }
                        }
                    }
                    if (ops.size() > 0) {
                        op = "";
                        for (int j = 0; j < ops.size(); j++) {
                            op = op + ops.get(j) + "_";
                        }
                    } else {
                        op = "-1";
                    }
                    modifyQuestionListener.modifyQuestion(op, position);
                }
            });
            layout.addView(checkboxView);
        }
    }

    /**
     * 添加单选按钮
     */
    private void updateRadioView() {
        final RadioGroup layout = view.findViewById(R.id.rg_options);
        layout.removeAllViews();
        int checkId = -1;
        for (int i = 0; i < subDataBean.getOptions().size(); i++) {
            ExamDetailsBean.Options option = subDataBean.getOptions().get(i);
            final RadioButton radioView = (RadioButton) LayoutInflater.from(getActivity()).inflate(R.layout.item_radio, null, true);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            radioView.setLayoutParams(layoutParams);
            radioView.setText(QuestionAnswerUtils.getAnswerStr(i) + "：" + option.getContent());
            radioView.setTag(i);
            radioView.setId(i);
            if (QuestionAnswerUtils.getAnswerStr(i).equals(subDataBean.getQuestion_select())) {
                checkId = i;
            }
            radioView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        modifyQuestionListener.modifyQuestion(QuestionAnswerUtils.getAnswerStr((int) buttonView.getTag()), position);
                    }
                }
            });
            layout.addView(radioView);
        }
        if (checkId != -1) {
            layout.check(checkId);
        }
    }
}
