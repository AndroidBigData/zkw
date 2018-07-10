package com.zjwam.zkw.personalcenter.addinformation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.AddCompanyInfo;
import com.zjwam.zkw.entity.DialogInfo;
import com.zjwam.zkw.entity.JsonBean;
import com.zjwam.zkw.jsondata.AddCompanyData2Json;
import com.zjwam.zkw.jsondata.Dialog2Json;
import com.zjwam.zkw.util.AddChoiceInfo;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.GetJsonDataUtil;
import com.zjwam.zkw.util.GlideImageLoader;
import com.zjwam.zkw.util.KeyboardUtils;
import com.zjwam.zkw.util.ZkwPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddCompanyInformationActivity extends BaseActivity {

    private Button add_company_type,add_company_industry,add_company_address,add_company_identity,image_up,makesure_company;
    private EditText add_company_name,add_company_dbname,add_company_phone,add_company_address_more,add_company_num;
    private RelativeLayout add_company_jump;
    private ImageView add_company_image;
    private OptionsPickerView pvOptions;
    private String choiceInfo;
    private AddChoiceInfo addChoiceInfo;
    private List<JsonBean> province_item;
    private List<List<String>> city_item;
    private List<List<List<String>>> area_item;
    private Thread thread;
    private JSONObject data;
    private static final int MSG_LOAD_DATA = 0x0001;
    private RelativeLayout company_progress;
    private ArrayList<ImageItem> images = null;
    private ImagePicker imagePicker;
    private String img_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company_information);
        initView();
        initData();
    }

    private void initData() {
        addChoiceInfo = new AddChoiceInfo();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        add_company_jump.setOnClickListener(onClickListener);
        add_company_type.setOnClickListener(onClickListener);
        add_company_industry.setOnClickListener(onClickListener);
        add_company_address.setOnClickListener(onClickListener);
        add_company_identity.setOnClickListener(onClickListener);
        image_up.setOnClickListener(onClickListener);
        makesure_company.setOnClickListener(onClickListener);
    }

    private void initView() {
        add_company_jump = findViewById(R.id.add_company_jump);
        add_company_name = findViewById(R.id.add_company_name);
        add_company_type = findViewById(R.id.add_company_type);
        add_company_industry = findViewById(R.id.add_company_industry);
        add_company_dbname = findViewById(R.id.add_company_dbname);
        add_company_phone = findViewById(R.id.add_company_phone);
        add_company_address = findViewById(R.id.add_company_address);
        add_company_address_more = findViewById(R.id.add_company_address_more);
        add_company_identity = findViewById(R.id.add_company_identity);
        add_company_num = findViewById(R.id.add_company_num);
        add_company_image = findViewById(R.id.add_company_image);
        image_up = findViewById(R.id.image_up);
        makesure_company = findViewById(R.id.makesure_company);
        company_progress = findViewById(R.id.progress_add_company);
        company_progress.getBackground().setAlpha(100);

        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());  //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
        imagePicker.setMultiMode(false);//是否多选

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_company_jump:
                    finish();
                    break;
                case R.id.add_company_type:
                    KeyboardUtils.hideKeyboard(view);
                    List<String> company_type = addChoiceInfo.getCompanyType();
                    initPicker(company_type,null,view);
                    break;
                case R.id.add_company_industry:
                    KeyboardUtils.hideKeyboard(view);
                    List<String> item1 = addChoiceInfo.getCompanyIndustry();
                    List<List<String>> item2 = addChoiceInfo.getCompanyIndustry_item();
                    initPicker(item1,item2,view);
                    break;
                case R.id.add_company_address:
                    KeyboardUtils.hideKeyboard(view);
                    pvOptions = new OptionsPickerBuilder(AddCompanyInformationActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            //返回的分别是三个级别的选中位置
                            String tx = province_item.get(options1).getPickerViewText() +"-"+
                                    city_item.get(options1).get(options2) + "-" +
                                    area_item.get(options1).get(options2).get(options3);
                            add_company_address.setText(tx);
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
                case R.id.add_company_identity:
                    KeyboardUtils.hideKeyboard(view);
                    List<String> company_identity = addChoiceInfo.getIdentity();
                    initPicker(company_identity,null,view);
                    break;
                case R.id.image_up:
                    Intent intent = new Intent(getBaseContext(), ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                    startActivityForResult(intent, 100);
                    break;
                case R.id.makesure_company:
                    AddCompanyInfo addCompanyInfo = new AddCompanyInfo();
                    addCompanyInfo.setName(add_company_name.getText().toString().trim());
                    addCompanyInfo.setCom_type(add_company_type.getText().toString());
                    if (add_company_industry.getText().toString().split("-").length == 2){
                        addCompanyInfo.setHangye(add_company_industry.getText().toString().split("-")[0]);
                        addCompanyInfo.setLeibie(add_company_industry.getText().toString().split("-")[1]);
                    }else {
                        addCompanyInfo.setHangye("");
                        addCompanyInfo.setLeibie("");
                    }
                    addCompanyInfo.setLegal_person(add_company_dbname.getText().toString().trim());
                    addCompanyInfo.setPhone(add_company_phone.getText().toString().trim());
                    if (add_company_address.getText().toString().split("-").length == 3){
                        addCompanyInfo.setSch_sheng(add_company_address.getText().toString().split("-")[0]);
                        addCompanyInfo.setSch_shi(add_company_address.getText().toString().split("-")[1]);
                        addCompanyInfo.setSch_qu(add_company_address.getText().toString().split("-")[2]);
                    }else {
                        addCompanyInfo.setSch_sheng("");
                        addCompanyInfo.setSch_shi("");
                        addCompanyInfo.setSch_qu("");
                    }
                    addCompanyInfo.setCom_address(add_company_address_more.getText().toString().trim());
                    addCompanyInfo.setCom_identity(add_company_identity.getText().toString());
                    addCompanyInfo.setCredit_cade(add_company_num.getText().toString().trim());
                    data = AddCompanyData2Json.getCompanyData2Json(addCompanyInfo,getBaseContext());
                    Log.i("---data:",data.toString());
                    upData(Config.URL + "api/info/complete_company");

                    break;
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                img_path = images.get(0).path;
                imagePicker.getImageLoader().displayImage(AddCompanyInformationActivity.this,img_path,add_company_image,235,340);
                Log.i("---path:",images.get(0).path);
                upImg(Config.URL+"api/info/company_img",ZkwPreference.getInstance(getBaseContext()).getUid(),img_path);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void upImg(String url,String uid, String img_path) {

        OkGo.<String>post(url)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .params("uid","12868")
                .params("test",new File(img_path))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        DialogInfo dialogInfo = Dialog2Json.getDialogInfo(response.body());
                        if ("1".equals(dialogInfo.getCode())){
                            Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
                            add_company_image.setImageResource(R.drawable.license_demo);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

//        RequestParams params = new RequestParams(url);
//        params.setMultipart(true);
//        params.addBodyParameter("test",new File(img_path));
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.i("---result:",result.toString());
//                DialogInfo dialogInfo = Dialog2Json.getDialogInfo(result);
//                if ("1".equals(dialogInfo.getCode())){
//                    Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
//                    add_company_image.setImageResource(R.drawable.license_demo);
//                }
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
//
//            }
//        });
    }

    private void upData(String url) {

        OkGo.<String>post(url)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .upJson(data)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        company_progress.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        DialogInfo dialogInfo = Dialog2Json.getDialogInfo(response.body());
                        if ("1".equals(dialogInfo.getCode())){
                            Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
                        }else {
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
                        company_progress.setVisibility(View.GONE);
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
//                }else {
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
//                company_progress.setVisibility(View.GONE);
//            }
//        });
    }

    /**
     * 选择器
     */
    public void initPicker(final List<String> item1, final List<List<String>> item2, final View view) {
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (item2 != null){
                    choiceInfo = item1.get(options1) + "-" +item2.get(options1).get(options2) ;
                    add_company_industry.setText(choiceInfo);

                }else {
                    choiceInfo = item1.get(options1);
                    if (view.getId() == R.id.add_company_type){
                        add_company_type.setText(choiceInfo);
                    }
                    if (view.getId() == R.id.add_company_identity){
                        add_company_identity.setText(choiceInfo);
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


