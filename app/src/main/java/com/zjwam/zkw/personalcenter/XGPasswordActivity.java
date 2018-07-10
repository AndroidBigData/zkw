package com.zjwam.zkw.personalcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.DialogInfo;
import com.zjwam.zkw.jsondata.Dialog2Json;
import com.zjwam.zkw.util.BadNetWork;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.ZkwPreference;


public class XGPasswordActivity extends BaseActivity {

    private EditText ggmm_ymm,ggmm_xmm,ggmm_xmm2;
    private RelativeLayout back;
    private Button makesure_button;
    private String ymm,xmm,xmm2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xgpassword);
        initView();
        initData();
    }

    private void initData() {
        makesure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ymm = ggmm_ymm.getText().toString().trim();
                xmm = ggmm_xmm.getText().toString().trim();
                xmm2 = ggmm_xmm2.getText().toString().trim();
                if (ymm.length()>0 && xmm.length() >= 6 && xmm2.length() >= 6){
                    Log.i("---pass2",ZkwPreference.getInstance(getBaseContext()).getPassword());
                    if (ymm.equals(ZkwPreference.getInstance(getBaseContext()).getPassword())){
                        if (!xmm.equals(xmm2)){
                            Toast.makeText(getBaseContext(),"新密码不一致",Toast.LENGTH_SHORT).show();
                        }else {
                            upData();
                        }
                    }else {
                        Toast.makeText(getBaseContext(),"旧密码错误！",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getBaseContext(),"密码必须大于六位！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void upData() {

        OkGo.<String>post(Config.URL+"api/Login/change_pwd")
                .cacheMode(CacheMode.NO_CACHE)
                .params("uid", ZkwPreference.getInstance(getBaseContext()).getUid())
                .params("newpass",xmm)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        DialogInfo dialogInfo = Dialog2Json.getDialogInfo(response.body());
                        if ("1".equals(dialogInfo.getCode())){
                            Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
                            ggmm_ymm.setText("");
                            ggmm_xmm.setText("");
                            ggmm_xmm2.setText("");
                        }else {
                            Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
//        RequestParams params = new RequestParams(Config.URL+"api/Login/change_pwd");
//        params.addBodyParameter("uid", ZkwPreference.getInstance(getBaseContext()).getUid());
//        params.addBodyParameter("newpass",xmm);
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                DialogInfo dialogInfo = Dialog2Json.getDialogInfo(result);
//                if ("1".equals(dialogInfo.getCode())){
//                    Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
//                    ggmm_ymm.setText("");
//                    ggmm_xmm.setText("");
//                    ggmm_xmm2.setText("");
//                }else {
//                    Toast.makeText(getBaseContext(),dialogInfo.getMsg(),Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
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

    private void initView() {
        back = findViewById(R.id.xg_back);
        ggmm_ymm = findViewById(R.id.ggmm_ymm);
        ggmm_xmm = findViewById(R.id.ggmm_xmm);
        ggmm_xmm2 = findViewById(R.id.ggmm_xmm2);
        makesure_button = findViewById(R.id.makesure_button);
    }
}
