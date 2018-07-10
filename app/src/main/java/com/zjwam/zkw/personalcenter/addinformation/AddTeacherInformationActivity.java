package com.zjwam.zkw.personalcenter.addinformation;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
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
import com.zjwam.zkw.util.BadNetWork;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.GetJsonDataUtil;
import com.zjwam.zkw.util.KeyboardUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddTeacherInformationActivity extends BaseActivity {


    //                选择性别         学历-年级           确认提交                省-市-区          科目               学校类型               最高
    private Button add_teacher_sex,add_teacher_grade,add_teacher_up,add_teacher_school_address,add_teacher_subject,add_teacher_school_type,add_teacher_max_xl;
    private List<String> sex_item,xueli_item,school_type,teacher_subject;
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
                     //  姓名             身份证号          地址                 微信          qq              学校名称                 详细地址              联系方式           其他科目
    private EditText add_teacher_name,add_teacher_num,add_teacher_address,add_teacher_wx,add_teacher_qq,add_teacher_school_name,add_teacher_address_more,add_teacher_phone,add_teacher_subject_more
                                 //     毕业院校               院校地址
                             ,add_teacher_graduation,add_teacher_graduation_address;
    private RelativeLayout add_teacher_jump,progress_add_teacher;
    private ConstraintLayout isIndexOf_teacher;
    private JSONObject data;
    private AddInfo addInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher_information);
        initView();
        initData();

    }

    private void initData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        add_teacher_sex.setOnClickListener(onClickListener);
        add_teacher_grade.setOnClickListener(onClickListener);
        add_teacher_jump.setOnClickListener(onClickListener);
        add_teacher_up.setOnClickListener(onClickListener);
        add_teacher_school_address.setOnClickListener(onClickListener);
        add_teacher_subject.setOnClickListener(onClickListener);
        add_teacher_school_type.setOnClickListener(onClickListener);
        add_teacher_max_xl.setOnClickListener(onClickListener);
        progress_add_teacher.setOnClickListener(null);

    }

    private void initView() {
        add_teacher_sex = findViewById(R.id.add_teacher_sex);
        add_teacher_grade = findViewById(R.id.add_teacher_grade);
        add_teacher_name = findViewById(R.id.add_teacher_name);
        add_teacher_num = findViewById(R.id.add_teacher_num);
        add_teacher_address = findViewById(R.id.add_teacher_address);
        add_teacher_wx = findViewById(R.id.add_teacher_wx);
        add_teacher_qq = findViewById(R.id.add_teacher_qq);
        add_teacher_school_name = findViewById(R.id.add_teacher_school_name);
        add_teacher_school_address = findViewById(R.id.add_teacher_school_address);
        add_teacher_subject = findViewById(R.id.add_teacher_subject);
        add_teacher_jump = findViewById(R.id.add_teacher_jump);
        add_teacher_up = findViewById(R.id.add_teacher_up);
        isIndexOf_teacher = findViewById(R.id.isIndexOf_teacher);
        add_teacher_address_more = findViewById(R.id.add_teacher_school_address_more);
        add_teacher_phone = findViewById(R.id.add_teacher_phone);
        add_teacher_subject_more = findViewById(R.id.add_teacher_subject_more);
        progress_add_teacher = findViewById(R.id.progress_add_teacher);
        add_teacher_school_type = findViewById(R.id.add_teacher_school_type);
        add_teacher_max_xl = findViewById(R.id.add_teacher_max_xl);
        add_teacher_graduation = findViewById(R.id.add_teacher_graduation);
        add_teacher_graduation_address = findViewById(R.id.add_teacher_graduation_address);
        addChoiceInfo = new AddChoiceInfo();
        progress_add_teacher.getBackground().setAlpha(100);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_teacher_sex:
                    KeyboardUtils.hideKeyboard(view);
                    sex_item = addChoiceInfo.addSex();
                    initSexPicker(sex_item,null,view);
                    break;
                case R.id.add_teacher_grade:
                    KeyboardUtils.hideKeyboard(view);
                    xueli_item = addChoiceInfo.addXueli();
                    grade_item = addChoiceInfo.addGrade(1);
                    initSexPicker(xueli_item,grade_item,view);
                    break;
                case R.id.add_teacher_jump:
                    finish();
                    break;
                case R.id.add_teacher_up:

                    addInfo = new AddInfo();
                    addInfo.setName(add_teacher_name.getText().toString().trim());
                    addInfo.setSex(add_teacher_sex.getText().toString().trim());
                    addInfo.setNum(add_teacher_num.getText().toString().trim());
                    addInfo.setAdress(add_teacher_address.getText().toString().trim());
                    addInfo.setWx(add_teacher_wx.getText().toString().trim());
                    addInfo.setQq(add_teacher_qq.getText().toString().trim());
                    addInfo.setSchool_name(add_teacher_school_name.getText().toString().trim());
                    if (add_teacher_school_address.getText().toString().split("-").length == 3){
                        addInfo.setSchool_adress_province(add_teacher_school_address.getText().toString().split("-")[0]);
                        addInfo.setSchool_adress_city(add_teacher_school_address.getText().toString().split("-")[1]);
                        addInfo.setSchool_adress_area(add_teacher_school_address.getText().toString().split("-")[2]);
                    }else {
                        addInfo.setSchool_adress_province("");
                        addInfo.setSchool_adress_city("");
                        addInfo.setSchool_adress_area("");
                    }

                    addInfo.setSchool_adress_more(add_teacher_address_more.getText().toString().trim());
                    if (add_teacher_grade.getText().toString().trim().split("-").length == 2){
                        addInfo.setSchool_type(add_teacher_grade.getText().toString().trim().split("-")[0]);
                        addInfo.setGrade(add_teacher_grade.getText().toString().trim().split("-")[1]);
                    }else {
                        addInfo.setSchool_type("");
                        addInfo.setGrade("");
                    }

                    addInfo.setSubject(add_teacher_subject.getText().toString().trim());
                    addInfo.setPhone(add_teacher_phone.getText().toString().trim());
                    addInfo.setSchool_xz(add_teacher_school_type.getText().toString());
                    addInfo.setSubjec_more(add_teacher_subject_more.getText().toString().trim());
                    addInfo.setMax_xl(add_teacher_max_xl.getText().toString().trim());
                    addInfo.setGraduation(add_teacher_graduation.getText().toString().trim());
                    addInfo.setGraduation_address(add_teacher_graduation_address.getText().toString().trim());

                    data = new AddInfoData2Json().addInfoData2Json(addInfo,getBaseContext());
                    Log.i("---data:",data.toString());
                    upData(Config.URL + "api/info/complete_teacher");

                    break;
                case R.id.add_teacher_school_address:
                    pvOptions = new OptionsPickerBuilder(AddTeacherInformationActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            //返回的分别是三个级别的选中位置
                            String tx = province_item.get(options1).getPickerViewText() +"-"+
                                    city_item.get(options1).get(options2) + "-" +
                                    area_item.get(options1).get(options2).get(options3);
                            add_teacher_school_address.setText(tx);
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
                case R.id.add_teacher_school_type:
                    KeyboardUtils.hideKeyboard(view);
                    school_type = addChoiceInfo.getChoolType();
                    initSexPicker(school_type,null,view);
                    break;
                case R.id.add_teacher_subject:
                    KeyboardUtils.hideKeyboard(view);
                    teacher_subject = addChoiceInfo.getTeacherSubjecdt();
                    initSexPicker(teacher_subject,null,view);
                    break;
                case R.id.add_teacher_max_xl:
                    KeyboardUtils.hideKeyboard(view);
                    xueli_item = addChoiceInfo.addXueli();
                    initSexPicker(xueli_item,null,view);
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
                        progress_add_teacher.setVisibility(View.VISIBLE);
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
                        progress_add_teacher.setVisibility(View.GONE);
                    }
                });

//        RequestParams params = new RequestParams(url);
//        params.setAsJsonContent(true);
//        params.setBodyContent(data.toString());
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                DialogInfo dialogInfo = Dialog2Json.getDialogInfo(result);
//                if ("1".equals(dialogInfo.getCode())){
//                    Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                new BadNetWork().isBadNetWork(getBaseContext());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//                progress_add_teacher.setVisibility(View.GONE);
//            }
//        });
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
                    if (view.getId() == R.id.add_teacher_grade){
                        add_teacher_grade.setText(choiceInfo);
                    }

                }else {
                    choiceInfo = item1.get(options1);
                    if (view.getId() == R.id.add_teacher_sex){
                        add_teacher_sex.setText(choiceInfo);
                    }
                    if (view.getId() == R.id.add_teacher_school_type){
                        add_teacher_school_type.setText(choiceInfo);
                    }
                    if (view.getId() == R.id.add_teacher_subject){
                        add_teacher_subject.setText(choiceInfo);
                    }
                    if (view.getId() == R.id.add_teacher_max_xl){
                        add_teacher_max_xl.setText(choiceInfo);
                    }
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

                        if (view.getId() == R.id.add_teacher_subject){
                            if ("其他".equals(item1.get(options1))){
                                //包含
                                isIndexOf_teacher.setVisibility(View.VISIBLE);
                            }else {
                                isIndexOf_teacher.setVisibility(View.GONE);
                                add_teacher_subject_more.setText("");
                        }
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
