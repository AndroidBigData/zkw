package com.zjwam.zkw.personalcenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.fragment.login.LoginFragment;
import com.zjwam.zkw.personalcenter.addinformation.AddCompanyInformationActivity;
import com.zjwam.zkw.personalcenter.addinformation.AddStudentInformationActivity;
import com.zjwam.zkw.personalcenter.addinformation.AddTeacherInformationActivity;
import com.zjwam.zkw.util.ZkwPreference;

public class SetUpActivity extends BaseActivity {
private TextView mine_setup,setup_safe,logout;
private ImageView setup_back;
private boolean isFalg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        initView();
        initData();
    }

    private void initData() {
        setup_back.setOnClickListener(onClickListener);
        setup_safe.setOnClickListener(onClickListener);
        mine_setup.setOnClickListener(onClickListener);
        logout.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.mine_setup:
                    if (isFalg){
                        if ("0".equals(ZkwPreference.getInstance(getBaseContext()).getRegisterType())){
                            startActivity(new Intent(getBaseContext(), AddStudentInformationActivity.class));
                        }else if ("1".equals(ZkwPreference.getInstance(getBaseContext()).getRegisterType())){
                            startActivity(new Intent(getBaseContext(), AddTeacherInformationActivity.class));
                        }else if ("2".equals(ZkwPreference.getInstance(getBaseContext()).getRegisterType()))
                            startActivity(new Intent(getBaseContext(), AddCompanyInformationActivity.class));
                    }else {
                        showDialog();
                    }

                    break;
                case R.id.setup_safe:
                    if (isFalg){
                        startActivity(new Intent(getBaseContext(), XGPasswordActivity.class));
                    }else {
                        showDialog();
                    }

                    break;
                case R.id.logout:
                    if (isFalg){
                        ZkwPreference.getInstance(getBaseContext()).SetIsFlag(false);
                        ZkwPreference.getInstance(getBaseContext()).SetLoginName("");
                        ZkwPreference.getInstance(getBaseContext()).SetPassword("");
                        ZkwPreference.getInstance(getBaseContext()).SetUid("");
                        ZkwPreference.getInstance(getBaseContext()).SetRegisterType("");
                        finish();
                    }

                    break;
                case R.id.setup_back:
                    finish();
                    break;
            }
        }
    };

    private void initView() {
        mine_setup = findViewById(R.id.mine_setup);
        setup_safe = findViewById(R.id.setup_safe);
        logout = findViewById(R.id.logout);
        setup_back = findViewById(R.id.setup_back);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("请您先登录");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getBaseContext(), LoginFragment.class));
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onResume() {
        super.onResume();
        isFalg = ZkwPreference.getInstance(getBaseContext()).IsFlag();
        if (isFalg){
            logout.setVisibility(View.VISIBLE);
        }else {
            logout.setVisibility(View.GONE);
        }
    }
}
