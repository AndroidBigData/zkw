package com.zjwam.zkw.personalcenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.httputils.PersonalCenterHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.ResponseBean;

import com.zjwam.zkw.util.ZkwPreference;


public class XGPasswordActivity extends BaseActivity {

    private EditText ggmm_ymm, ggmm_xmm, ggmm_xmm2;
    private RelativeLayout back;
    private Button makesure_button;
    private String ymm, xmm, xmm2;
    private PersonalCenterHttp personalCenterHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xgpassword);
        initView();
        initData();
    }

    private void initData() {
        personalCenterHttp = new PersonalCenterHttp(this);
        makesure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ymm = ggmm_ymm.getText().toString().trim();
                xmm = ggmm_xmm.getText().toString().trim();
                xmm2 = ggmm_xmm2.getText().toString().trim();
                if (ymm.length() > 0 && xmm.length() >= 6 && xmm2.length() >= 6) {
                    if (ymm.equals(ZkwPreference.getInstance(getBaseContext()).getPassword())) {
                        if (!xmm.equals(xmm2)) {
                            Toast.makeText(getBaseContext(), "新密码不一致", Toast.LENGTH_SHORT).show();
                        } else {
                            personalCenterHttp.XGPassword(ZkwPreference.getInstance(getBaseContext()).getUid(), xmm);
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "旧密码错误！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "密码必须大于六位！", Toast.LENGTH_SHORT).show();
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

    public void xgPassword(Response<ResponseBean<EmptyBean>> response) {
        ggmm_ymm.setText("");
        ggmm_xmm.setText("");
        ggmm_xmm2.setText("");
        String msg = response.body().msg;
        error(msg);
    }

    public void xgPasswordError(Response<ResponseBean<EmptyBean>> response) {
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        error(error);
    }

    private void initView() {
        back = findViewById(R.id.xg_back);
        ggmm_ymm = findViewById(R.id.ggmm_ymm);
        ggmm_xmm = findViewById(R.id.ggmm_xmm);
        ggmm_xmm2 = findViewById(R.id.ggmm_xmm2);
        makesure_button = findViewById(R.id.makesure_button);
    }
}
