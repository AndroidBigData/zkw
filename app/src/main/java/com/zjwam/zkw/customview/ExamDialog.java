package com.zjwam.zkw.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.CurriculumGridViewAdapter;
import com.zjwam.zkw.adapter.ExamChoiceAdapter;
import com.zjwam.zkw.entity.ClassSearchBean;
import com.zjwam.zkw.entity.CurriculumSelectBean;

import java.util.ArrayList;
import java.util.List;

public class ExamDialog extends Dialog {

    private Context context;
    private LinearLayout exam_dialog_one,exam_dialog_two,exam_dialog_three;
    private ImageView exam_dialog_img;
    private TextView exam_dialog_title,exam_dialog_one_text,exam_dialog_two_text,exam_dialog_three_text;
    private Spinner exam_dialog_one_sipnner,exam_dialog_two_sipnner,exam_dialog_three_sipnner;
    private Button exam_dialog_up;
    private int type;
    private ClickListenerInterface clickListenerInterface;
    private ClassSearchBean classSearchBean;
    private List<CurriculumSelectBean> one,two,three;
    private List<CurriculumSelectBean> list_one = new ArrayList<>();
    private List<List<CurriculumSelectBean>> list_two = new ArrayList<>();
    private List<List<List<CurriculumSelectBean>>> list_three = new ArrayList<>();
    private ExamChoiceAdapter adapter_one, adapter_two, adapter_three;
    private int id_one ;
    private int id_two ;
    private int id_three ;
    private int number = 0;
    private String title_one,title_two,title_three;

    public ExamDialog(@NonNull Context context, int type, ClassSearchBean classSearchBean) {
        super(context, R.style.dialog);
        this.context = context;
        this.type = type;
        this.classSearchBean = classSearchBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.exam_dialog, null);
        setContentView(view);
        exam_dialog_one = view.findViewById(R.id.exam_dialog_one);
        exam_dialog_two = view.findViewById(R.id.exam_dialog_two);
        exam_dialog_three = view.findViewById(R.id.exam_dialog_three);
        exam_dialog_img = view.findViewById(R.id.exam_dialog_img);
        exam_dialog_title = view.findViewById(R.id.exam_dialog_title);
        exam_dialog_one_sipnner = view.findViewById(R.id.exam_dialog_one_sipnner);
        exam_dialog_two_sipnner = view.findViewById(R.id.exam_dialog_two_sipnner);
        exam_dialog_three_sipnner = view.findViewById(R.id.exam_dialog_three_sipnner);
        exam_dialog_up = view.findViewById(R.id.exam_dialog_up);
        exam_dialog_one_text = view.findViewById(R.id.exam_dialog_one_text);
        exam_dialog_two_text = view.findViewById(R.id.exam_dialog_two_text);
        exam_dialog_three_text = view.findViewById(R.id.exam_dialog_three_text);
        if (2 == type){
            exam_dialog_img.setImageResource(R.drawable.xcg_exam);
            exam_dialog_title.setText("小初高");
        }else if (1== type){
            exam_dialog_img.setImageResource(R.drawable.zgz_exam);
            exam_dialog_title.setText("资格证");
        }else if (3 == type){
            exam_dialog_img.setImageResource(R.drawable.cjw_exam);
            exam_dialog_title.setText("成教网");
        }else if (4 == type){
            exam_dialog_img.setImageResource(R.drawable.yjs_exam);
            exam_dialog_title.setText("研究生");
        }

        exam_dialog_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenerInterface.doConfirm("first"+id_one+"_"+id_two+"_"+id_three,title_one+"/"+title_two+"/"+title_three);
            }
        });
        exam_dialog_one_text.setText(classSearchBean.getName1());
        if (classSearchBean.getName2().length()>0){
            exam_dialog_two_text.setText(classSearchBean.getName2());
        }else {
            exam_dialog_two.setVisibility(View.GONE);
        }
        if (classSearchBean.getName3().length()>0){
            exam_dialog_three_text.setText(classSearchBean.getName3());
        }else {
            exam_dialog_three.setVisibility(View.GONE);
        }


        one = new ArrayList<>();
        two = new ArrayList<>();
        three = new ArrayList<>();

        adapter_one = new ExamChoiceAdapter(context, one);
        adapter_two = new ExamChoiceAdapter(context, two);
        adapter_three = new ExamChoiceAdapter(context, three);

        exam_dialog_one_sipnner.setAdapter(adapter_one);
        exam_dialog_two_sipnner.setAdapter(adapter_two);
        exam_dialog_three_sipnner.setAdapter(adapter_three);
        initData();
    }

    private void initData() {
        for (int i=0;i<classSearchBean.getList().size();i++){
            List<CurriculumSelectBean> list = new ArrayList<>();
            List<List<CurriculumSelectBean>> list_m = new ArrayList<>();
            if (classSearchBean.getList().get(i).getCont().size()>0){
                for (int j=0;j<classSearchBean.getList().get(i).getCont().size();j++){
                    List<CurriculumSelectBean> list_a = new ArrayList<>();
                    CurriculumSelectBean curriculumSelectBean = new CurriculumSelectBean();
                    curriculumSelectBean.setName(classSearchBean.getList().get(i).getCont().get(j).getName());
                    curriculumSelectBean.setId(classSearchBean.getList().get(i).getCont().get(j).getId());
                    list.add(curriculumSelectBean);
                    if (classSearchBean.getList().get(i).getCont().get(j).getCont().size()>0){
                        for (int k = 0;k<classSearchBean.getList().get(i).getCont().get(j).getCont().size();k++){
                            CurriculumSelectBean beans = new CurriculumSelectBean();
                            beans.setName(classSearchBean.getList().get(i).getCont().get(j).getCont().get(k).getName());
                            beans.setId(classSearchBean.getList().get(i).getCont().get(j).getCont().get(k).getId());
                            list_a.add(beans);//获取到科目
                        }
                        list_m.add(list_a);
                    }
                }
            }
            CurriculumSelectBean curriculumSelectBean = new CurriculumSelectBean();
            curriculumSelectBean.setName(classSearchBean.getList().get(i).getName());
            curriculumSelectBean.setId(classSearchBean.getList().get(i).getId());
            list_one.add(curriculumSelectBean); //学习层次
            list_two.add(list);//年级/
            list_three.add(list_m);//科目
        }

        one.addAll(list_one); //学习层次第一条数据
        adapter_one.reloadData(one);
        if (one.size() > 0) {
            id_one = one.get(0).getId();//学习层次第一条ID
            title_one = one.get(0).getName();
        }
        for (int w = 0; w < list_two.get(0).size(); w++) {
            two.add(list_two.get(0).get(w)); //年级第一条数据
        }
        id_two = two.get(0).getId();//年级第一条ID
        title_two = two.get(0).getName();
        adapter_two.reloadData(two);

        for (int t = 0; t < list_three.get(0).get(0).size(); t++) {
            three.add(list_three.get(0).get(0).get(t));//科目第一条数据
        }
        id_three = three.get(0).getId();//科目第一条ID
        title_three = three.get(0).getName();
        adapter_three.reloadData(three);

        exam_dialog_one_sipnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                number = postion;
                id_one = one.get(postion).getId();
                title_one = one.get(postion).getName();
                if (postion <= list_two.size() - 1) {
                    adapter_two.clearList();
                    for (int w = 0; w < list_two.get(postion).size(); w++) {
                        two.add(list_two.get(postion).get(w));
                    }
                }
                adapter_two = new ExamChoiceAdapter(context,two);
                exam_dialog_two_sipnner.setAdapter(adapter_two);
                id_two = two.get(0).getId();
                title_two = two.get(0).getName();
                if (postion <= list_three.size() - 1) {
                    adapter_three.clearList();
                    if (list_three.get(postion).size() > 0) {
                        for (int t = 0; t < list_three.get(postion).get(0).size(); t++) {
                            three.add(list_three.get(postion).get(0).get(t));
                        }
                    }
                    adapter_three = new ExamChoiceAdapter(context,three);
                    exam_dialog_three_sipnner.setAdapter(adapter_three);
                    id_three = three.get(0).getId();
                    title_three = three.get(0).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        exam_dialog_two_sipnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                id_two = two.get(postion).getId();
                title_two = two.get(postion).getName();
                if (postion <= list_three.get(number).size() - 1) {
                    adapter_three.clearList();
                    for (int t = 0; t < list_three.get(number).get(postion).size(); t++) {
                        three.add(list_three.get(number).get(postion).get(t));
                    }
                }
                adapter_three = new ExamChoiceAdapter(context,three);
                exam_dialog_three_sipnner.setAdapter(adapter_three);
                id_three = three.get(0).getId();
                title_three = three.get(0).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        exam_dialog_three_sipnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_three = three.get(i).getId();
                title_three = three.get(i).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setOnClickListerer(ClickListenerInterface clickListenerInterface){
        this.clickListenerInterface = clickListenerInterface;
    }

    public interface ClickListenerInterface {
        void doConfirm(String id,String title);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        layoutParams.width = (int) (d.widthPixels * 0.8); // 宽度度设置为屏幕的0.8
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
