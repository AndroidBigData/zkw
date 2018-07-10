package com.zjwam.zkw.customview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.CurriculumGridViewAdapter;
import com.zjwam.zkw.entity.ClassSearchBean;
import com.zjwam.zkw.entity.CurriculumSelectBean;

import java.util.ArrayList;
import java.util.List;

public class CurriculumPopupWindow extends PopupWindow {
    private CurriculumGridView one, two, three;
    private List<CurriculumSelectBean> list_one = new ArrayList<>();
    private List<List<CurriculumSelectBean>> list_two = new ArrayList<>();
    private List<List<List<CurriculumSelectBean>>> list_three = new ArrayList<>();
    private List<CurriculumSelectBean> ones = new ArrayList<>();
    private List<CurriculumSelectBean> twos = new ArrayList<>();
    private List<CurriculumSelectBean> threes = new ArrayList<>();
    private CurriculumGridViewAdapter adapter_one, adapter_two, adapter_three;
    private int number = 0;
    private String id_one = "";
    private String id_two = "";
    private String id_three = "";
    private TextView textview;
    private ClassSearchBean classSearchBean;
    private Context context;
    private Button up_data;
    private onClickListener onClickListener;
    private View view;
    private TextView item1_title,item2_title,item3_title;
    private List<String> select_name;
    private List<String> select_id;

    public CurriculumPopupWindow(Context context, ClassSearchBean classSearchBean) {
        this.context = context;
        this.classSearchBean = classSearchBean;
        initPop();
    }

    private void initPop() {
        View popView = View.inflate(context, R.layout.pop_layout, null);
        //设置view
        this.setContentView(popView);
        //设置宽高（也可设置为LinearLayout.LayoutParams.MATCH_PARENT或者LinearLayout.LayoutParams.MATCH_PARENT）
        this.setWidth(-1);
        this.setHeight(-2);
        //设置PopupWindow的焦点
        this.setFocusable(true);
        //设置窗口以外的地方点击可关闭pop
        this.setOutsideTouchable(true);
        //设置背景透明
        this.setBackgroundDrawable(new ColorDrawable(0x33000000));
        initView(popView);
        initData();
    }

    private void initData() {
        if (classSearchBean.getList().size() > 0) {
            item1_title.setText(classSearchBean.getName1());
            item2_title.setText(classSearchBean.getName2());
            item3_title.setText(classSearchBean.getName3());
            for (int i = 0; i < classSearchBean.getList().size(); i++) {
                List<CurriculumSelectBean> list = new ArrayList<>();
                List<List<CurriculumSelectBean>> list_m = new ArrayList<>();
                if (!TextUtils.isEmpty(classSearchBean.getList().get(i).getName())) {
                    if (classSearchBean.getList().get(i).getCont().size() > 0) {
                        for (int d = 0; d < classSearchBean.getList().get(i).getCont().size(); d++) {
                            List<CurriculumSelectBean> list_a = new ArrayList<>();//6
                            CurriculumSelectBean curriculumSelectBean = new CurriculumSelectBean();
                            curriculumSelectBean.setName(classSearchBean.getList().get(i).getCont().get(d).getName());
                            curriculumSelectBean.setId(classSearchBean.getList().get(i).getCont().get(d).getId());
                            list.add(curriculumSelectBean);
                            if (!TextUtils.isEmpty(classSearchBean.getList().get(i).getCont().get(d).getName())) {
                                if (classSearchBean.getList().get(i).getCont().get(d).getCont().size() > 0) {
                                    for (int a = 0; a < classSearchBean.getList().get(i).getCont().get(d).getCont().size(); a++) {
                                        CurriculumSelectBean beans = new CurriculumSelectBean();
                                        beans.setName(classSearchBean.getList().get(i).getCont().get(d).getCont().get(a).getName());
                                        beans.setId(classSearchBean.getList().get(i).getCont().get(d).getCont().get(a).getId());
                                        list_a.add(beans);//获取到科目
                                    }
                                    list_m.add(list_a);
                                }
                            }
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

            ones.addAll(list_one); //学习层次第一条数据
            adapter_one.reloadData(ones);
            if (ones.size() > 0) {
                id_one = ones.get(0).getId() + "--" + ones.get(0).getName();//学习层次第一条ID
            }
            for (int w = 0; w < list_two.get(0).size(); w++) {
                twos.add(list_two.get(0).get(w)); //年级第一条数据
                id_two = twos.get(0).getId() + "--" + twos.get(0).getName();//年级第一条ID
            }
            adapter_two.reloadData(twos);

            for (int t = 0; t < list_three.get(0).get(0).size(); t++) {
                threes.add(list_three.get(0).get(0).get(t));//科目第一条数据
                id_three = threes.get(0).getId() + "--" + threes.get(0).getName();//科目第一条ID
            }
            adapter_three.reloadData(threes);
        }
    }

    private void initView(View popView) {
        one = popView.findViewById(R.id.item1_grid);
        two = popView.findViewById(R.id.item2_grid);
        three = popView.findViewById(R.id.item3_grid);
        up_data = popView.findViewById(R.id.up_data);
        view = popView.findViewById(R.id.view);
        item1_title = popView.findViewById(R.id.item1_title);
        item2_title = popView.findViewById(R.id.item2_title);
        item3_title = popView.findViewById(R.id.item3_title);
        adapter_one = new CurriculumGridViewAdapter(context, ones, new CurriculumGridViewAdapter.changeTextBacs() {
            @Override
            public void changeTextBack(int postion) {
                number = postion;
                id_one = "";
                id_two = "";
                id_three = "";
                id_one = ones.get(postion).getId() + "--" + ones.get(postion).getName();//学习层次第一条ID
                adapter_one.selectItem(postion);
                setICitem(adapter_two, twos, 0);
                setICitem(adapter_three, threes, 0);
                if (postion <= list_two.size() - 1) {
                    for (int w = 0; w < list_two.get(postion).size(); w++) {
                        twos.add(list_two.get(postion).get(w));
                        id_two = twos.get(0).getId() + "--" + twos.get(0).getName();//年级第一条ID
                    }
                    adapter_two.reloadData(twos);
                    if (twos.isEmpty()){
                        item2_title.setVisibility(View.GONE);
                        item3_title.setVisibility(View.GONE);
                    }else {
                        item2_title.setVisibility(View.VISIBLE);
                        item3_title.setVisibility(View.VISIBLE);
                    }
                }
                if (postion <= list_three.size() - 1) {
                    if (list_three.get(postion).size() > 0) {
                        for (int t = 0; t < list_three.get(postion).get(0).size(); t++) {
                            threes.add(list_three.get(postion).get(0).get(t));
                            id_three = threes.get(0).getId() + "--" + threes.get(0).getName();//科目第一条ID
                        }
                        adapter_three.reloadData(threes);
                    }
                }
            }
        });
        adapter_two = new CurriculumGridViewAdapter(context, twos, new CurriculumGridViewAdapter.changeTextBacs() {
            @Override
            public void changeTextBack(int postion) {
                adapter_two.selectItem(postion);
                id_two = "";
                id_three = "";
                id_two = twos.get(postion).getId() + "--" + twos.get(postion).getName();//年级第一条ID
                setICitem(adapter_three, threes, 0);
                if (postion <= list_three.get(number).size() - 1) {
                    for (int t = 0; t < list_three.get(number).get(postion).size(); t++) {
                        threes.add(list_three.get(number).get(postion).get(t));
                        id_three = threes.get(0).getId() + "--" + threes.get(0).getName();//年级第一条ID
                    }
                    adapter_three.reloadData(threes);
                    if (threes.isEmpty()){
                        item3_title.setVisibility(View.GONE);
                    }else {
                        item3_title.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        adapter_three = new CurriculumGridViewAdapter(context, threes, new CurriculumGridViewAdapter.changeTextBacs() {
            @Override
            public void changeTextBack(int postion) {
                adapter_three.selectItem(postion);
                id_three = threes.get(postion).getId() + "--" + threes.get(postion).getName();//科目ID
            }
        });
        one.setAdapter(adapter_one);
        two.setAdapter(adapter_two);
        three.setAdapter(adapter_three);

        up_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] one = id_one.split("--");
                String[] two = id_two.split("--");
                String[] three = id_three.split("--");
                if (!id_one.isEmpty()){
                    select_name.add(one[1]);
                    select_id.add("first"+one[0]);
                }
                if (!id_two.isEmpty()){
                    select_name.add(two[1]);
                    select_id.add(two[0]);
                }
                if (!id_three.isEmpty()){
                    select_name.add(three[1]);
                    select_id.add(three[0]);
                }

                onClickListener.onClick(select_name,select_id);
                dismiss();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        select_name = new ArrayList<>();
        select_id = new ArrayList<>();
    }

    public void setICitem(CurriculumGridViewAdapter adapter, List<CurriculumSelectBean> list_s, int number) {
        list_s.clear();
        adapter.selectItem(number);
    }

    public void setOnClickListener(CurriculumPopupWindow.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickListener{
        void onClick(List<String> className, List<String> classId);
    }
}
