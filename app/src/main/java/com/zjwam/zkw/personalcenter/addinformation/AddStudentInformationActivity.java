package com.zjwam.zkw.personalcenter.addinformation;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.AddInfo;
import com.zjwam.zkw.entity.DialogInfo;
import com.zjwam.zkw.entity.JsonBean;
import com.zjwam.zkw.jsondata.AddInfoData2Json;
import com.zjwam.zkw.jsondata.Dialog2Json;
import com.zjwam.zkw.util.AddChoiceInfo;

import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.GetJsonDataUtil;
import com.zjwam.zkw.util.KeyboardUtils;
import com.zjwam.zkw.util.ZkwPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;;

import java.util.ArrayList;
import java.util.List;

public class AddStudentInformationActivity extends BaseActivity {
    //                选择性别         学历-年级           提交                省-市-区
    private Button add_student_sex,add_student_grade,add_student_up,add_student_school_address;
    private List<String> sex_item,xueli_item;
    private List<JsonBean> province_item;
    private List<List<String>> grade_item,city_item;
    private List<List<List<String>>> area_item;
    private AddChoiceInfo addChoiceInfo;
    private OptionsPickerView pvOptions;
    private String choiceInfo;

    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
                    //  姓名             身份证号          地址                 微信          qq              学校名称                 详细地址                专业              联系方式
    private EditText add_student_name,add_student_num,add_student_address,add_student_wx,add_student_qq,add_student_school_name,add_student_address_more,add_student_subject,add_student_phone;
    private RelativeLayout add_student_jump,progress_add_student;
    private ConstraintLayout isIndexOf;
    private JSONObject data;
    private AddInfo addInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_information);

        initView();
        initData();

    }

    private void initData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        add_student_sex.setOnClickListener(onClickListener);
        add_student_grade.setOnClickListener(onClickListener);
        add_student_jump.setOnClickListener(onClickListener);
        add_student_up.setOnClickListener(onClickListener);
        add_student_school_address.setOnClickListener(onClickListener);
        progress_add_student.setOnClickListener(null);

    }

    private void initView() {
        add_student_sex = findViewById(R.id.add_student_sex);
        add_student_grade = findViewById(R.id.add_student_grade);
        add_student_name = findViewById(R.id.add_student_name);
        add_student_num = findViewById(R.id.add_student_num);
        add_student_address = findViewById(R.id.add_student_address);
        add_student_wx = findViewById(R.id.add_student_wx);
        add_student_qq = findViewById(R.id.add_student_qq);
        add_student_school_name = findViewById(R.id.add_student_school_name);
        add_student_school_address = findViewById(R.id.add_student_school_address);
        add_student_subject = findViewById(R.id.add_student_subject);
        add_student_jump = findViewById(R.id.add_student_jump);
        add_student_up = findViewById(R.id.add_student_up);
        isIndexOf = findViewById(R.id.isIndexOf);
        add_student_address_more = findViewById(R.id.add_student_school_address_more);
        add_student_phone = findViewById(R.id.add_student_phone);
        addChoiceInfo = new AddChoiceInfo();
        progress_add_student = findViewById(R.id.progress_add_student);
        progress_add_student.getBackground().setAlpha(100);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_student_sex:
                    KeyboardUtils.hideKeyboard(view);
                    sex_item = addChoiceInfo.addSex();
                    initSexPicker(sex_item,null,view);
                    break;
                case R.id.add_student_grade:
                    KeyboardUtils.hideKeyboard(view);
                    xueli_item = addChoiceInfo.addXueli();
                    grade_item = addChoiceInfo.addGrade(0);
                    initSexPicker(xueli_item,grade_item,view);
                    break;
                case R.id.add_student_jump:
                    finish();
                    break;
                case R.id.add_student_up:

                    addInfo = new AddInfo();
                    addInfo.setName(add_student_name.getText().toString().trim());
                    addInfo.setSex(add_student_sex.getText().toString().trim());
                    addInfo.setPhone(add_student_phone.getText().toString().trim());
                    addInfo.setNum(add_student_num.getText().toString().trim());
                    addInfo.setAdress(add_student_address.getText().toString().trim());
                    addInfo.setWx(add_student_wx.getText().toString().trim());
                    addInfo.setQq(add_student_qq.getText().toString().trim());
                    addInfo.setSchool_name(add_student_school_name.getText().toString().trim());
                    if (add_student_school_address.getText().toString().split("-").length == 3){
                        addInfo.setSchool_adress_province(add_student_school_address.getText().toString().split("-")[0]);
                        addInfo.setSchool_adress_city(add_student_school_address.getText().toString().split("-")[1]);
                        addInfo.setSchool_adress_area(add_student_school_address.getText().toString().split("-")[2]);
                    }else {
                        addInfo.setSchool_adress_province("");
                        addInfo.setSchool_adress_city("");
                        addInfo.setSchool_adress_area("");
                    }
                    addInfo.setSchool_adress_more(add_student_address_more.getText().toString().trim());
                    if (add_student_grade.getText().toString().trim().split("-").length == 2){
                        addInfo.setSchool_type(add_student_grade.getText().toString().trim().split("-")[0]);
                        addInfo.setGrade(add_student_grade.getText().toString().trim().split("-")[1]);
                    }else {
                        addInfo.setSchool_type("");
                        addInfo.setGrade("");
                    }

                    addInfo.setSubject(add_student_subject.getText().toString().trim());

                    data = new AddInfoData2Json().addInfoData2Json(addInfo,getBaseContext());
                    upData(Config.URL + "api/info/complete");

                    break;
                case R.id.add_student_school_address:
                    pvOptions = new OptionsPickerBuilder(AddStudentInformationActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            //返回的分别是三个级别的选中位置
                            String tx = province_item.get(options1).getPickerViewText() +"-"+
                                    city_item.get(options1).get(options2) + "-" +
                                    area_item.get(options1).get(options2).get(options3);
                            add_student_school_address.setText(tx);
                        }
                    })
                            .setTitleText("城市选择")
                            .setDividerColor(Color.BLACK)
                            .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                            .setContentTextSize(20)
                            .build();
                    pvOptions.setPicker(province_item, city_item, area_item);//三级选择器
                    pvOptions.show();
                    break;
            }
        }
    };

    private void upData(String url) {

        OkGo.<String>post(url)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .upJson(data)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        progress_add_student.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        DialogInfo dialogInfo = Dialog2Json.getDialogInfo(response.body());
                        if ("1".equals(dialogInfo.getCode())){
                            Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        progress_add_student.setVisibility(View.GONE);
                    }
                });
    }

    /**
     * 选择器
     */
    public void initSexPicker(final List<String> item1, final List<List<String>> item2, final View view) {
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (item2 != null){
                    choiceInfo = item1.get(options1) + "-" +item2.get(options1).get(options2) ;
                    add_student_grade.setText(choiceInfo);

                }else {
                    choiceInfo = item1.get(options1);
                    add_student_sex.setText(choiceInfo);
                }
            }
        })
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setTitleBgColor(Color.WHITE)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {

                            if ("中专 专科 本科 研究生 博士".indexOf(item1.get(options1)) != -1){
                                //包含
                                isIndexOf.setVisibility(View.VISIBLE);
                            }else {
                                add_student_subject.setText("");
                                isIndexOf.setVisibility(View.GONE);
                            }
                    }
                })
                .build();
        pvOptions.setPicker(item1,item2);
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        province_item = new ArrayList<>();
        city_item = new ArrayList<>();
        area_item = new ArrayList<>();

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(getBaseContext(), "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        province_item = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            List<List<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            city_item.add(CityList);

            /**
             * 添加地区数据
             */
            area_item.add(Province_AreaList);
        }

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
//                case MSG_LOAD_SUCCESS:
//                    break;
//                case MSG_LOAD_FAILED:
//                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

}
