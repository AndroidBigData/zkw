package com.zjwam.zkw.register;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.DialogInfo;
import com.zjwam.zkw.jsondata.Dialog2Json;
import com.zjwam.zkw.personalcenter.addinformation.AddCompanyInformationActivity;
import com.zjwam.zkw.personalcenter.addinformation.AddStudentInformationActivity;
import com.zjwam.zkw.personalcenter.addinformation.AddTeacherInformationActivity;

import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.ZkwPreference;


public class RegisterActivity extends BaseActivity {

    private Button register_getYZM,makesure_regster;
    private EditText register_phone,register_yzm,register_nickname,register_emali,register_passworld;
    private RelativeLayout register_back;
    private String type;
    private DialogInfo dialogInfo;
    private RelativeLayout progress_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
    }

    private void initData() {
        type = ZkwPreference.getInstance(getBaseContext()).getRegisterType();
        register_getYZM.setOnClickListener(onClickListener);
        makesure_regster.setOnClickListener(onClickListener);
        register_back.setOnClickListener(onClickListener);
    }

    private void initView() {
        register_getYZM = findViewById(R.id.register_getYZM);
        makesure_regster = findViewById(R.id.makesure_regster);
        register_phone = findViewById(R.id.register_phone);
        register_yzm = findViewById(R.id.register_yzm);
        register_nickname = findViewById(R.id.register_nickname);
        register_emali = findViewById(R.id.register_emali);
        register_passworld = findViewById(R.id.register_passworld);
        register_back = findViewById(R.id.register_back);
        progress_register = findViewById(R.id.progress_register);
        progress_register.getBackground().setAlpha(100);
        progress_register.setOnClickListener(null);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.register_getYZM:
                    if (register_phone.getText().toString().trim().length() == 0){
                        Toast.makeText(getBaseContext(),"请填写手机号码",Toast.LENGTH_SHORT).show();
                    }else {
                        TimeCount timeCount=new TimeCount(60000,1000);
                        timeCount.start();
                        getYZM(Config.URL + "api/Login/getCode?tel=" + register_phone.getText().toString().trim());
                    }
                    break;
                case R.id.makesure_regster:
                    if ( register_phone.getText().toString().trim().length() != 0 && register_yzm.getText().toString().trim().length() != 0 && register_nickname.getText().toString().trim().length() != 0 &&
                            register_emali.getText().toString().trim().length() != 0 && register_passworld.getText().toString().trim().length() != 0 ){
                        if(register_passworld.getText().toString().trim().length() < 6){
                            Toast.makeText(getBaseContext(),"密码必须大于6位",Toast.LENGTH_SHORT).show();
                        }else {
                            registerZkw(Config.URL + "api/Login/register",register_phone.getText().toString().trim(),register_yzm.getText().toString().trim(),register_nickname.getText().toString().trim(),
                                    register_emali.getText().toString().trim(), register_passworld.getText().toString().trim());
                        }
                    }else {
                        Toast.makeText(getBaseContext(),"请完善注册信息",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.register_back:
                    finish();
                    break;
            }
        }
    };

    private void registerZkw(String url, final String tel, String code, String nick, String email, final String pwd) {

        OkGo.<String>post(url)
                .params("tel",tel)
                .params("code",code)
                .params("nick",nick)
                .params("email",email)
                .params("pwd",pwd)
                .params("type", type)
                .cacheMode(CacheMode.NO_CACHE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        progress_register.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onSuccess(Response<String> response) {
                        dialogInfo = Dialog2Json.getDialogInfo(response.body());
                        if ("1".equals(dialogInfo.getCode())){
                            Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
                            ZkwPreference.getInstance(getBaseContext()).SetIsFlag(true);
                            ZkwPreference.getInstance(getBaseContext()).SetUid(dialogInfo.getUid());
                            ZkwPreference.getInstance(getBaseContext()).SetPassword(pwd);
                            if ("0".equals(type)){
                                startActivity(new Intent(getBaseContext(), AddStudentInformationActivity.class));
                            }else if ("1".equals(type)){
                                startActivity(new Intent(getBaseContext(), AddTeacherInformationActivity.class));
                            }else if ("2".equals(type)){
                                startActivity(new Intent(getBaseContext(), AddCompanyInformationActivity.class));
                            }
                            finish();
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
                        progress_register.setVisibility(View.GONE);
                    }
                });


//        RequestParams params = new RequestParams(url);
//        params.addBodyParameter("tel",tel);
//        params.addBodyParameter("code",code);
//        params.addBodyParameter("nick",nick);
//        params.addBodyParameter("email",email);
//        params.addBodyParameter("pwd",pwd);
//        params.addBodyParameter("type", type);
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                dialogInfo = Dialog2Json.getDialogInfo(result);
//                if ("1".equals(dialogInfo.getCode())){
//                    Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
//                    ZkwPreference.getInstance(getBaseContext()).SetIsFlag(true);
//                    ZkwPreference.getInstance(getBaseContext()).SetUid(dialogInfo.getUid());
//                    ZkwPreference.getInstance(getBaseContext()).SetPassword(pwd);
//                    if ("0".equals(type)){
//                        startActivity(new Intent(getBaseContext(), AddStudentInformationActivity.class));
//                    }else if ("1".equals(type)){
//                        startActivity(new Intent(getBaseContext(), AddTeacherInformationActivity.class));
//                    }else if ("2".equals(type)){
//                        startActivity(new Intent(getBaseContext(), AddCompanyInformationActivity.class));
//                    }
//                    finish();
//                }else {
//                    Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//                progress_register.setVisibility(View.GONE);
//            }
//        });
    }

    private void getYZM(String url) {

        OkGo.<String>get(url)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dialogInfo = Dialog2Json.getDialogInfo(response.body());
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
                });

//        RequestParams params = new RequestParams(url);
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                dialogInfo = Dialog2Json.getDialogInfo(result);
//                if ("1".equals(dialogInfo.getCode())){
//                    Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
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

    class TimeCount extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            register_getYZM.setClickable(false);
            register_getYZM.setText(millisUntilFinished / 1000 + "秒后获取");
        }

        @Override
        public void onFinish() {
            register_getYZM.setClickable(true);
            register_getYZM.setText("重新获取");
        }
    }

}
