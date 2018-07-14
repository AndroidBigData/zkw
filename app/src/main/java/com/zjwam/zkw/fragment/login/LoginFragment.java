package com.zjwam.zkw.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.DialogInfo;
import com.zjwam.zkw.jsondata.Dialog2Json;
import com.zjwam.zkw.personalcenter.addinformation.AddCompanyInformationActivity;
import com.zjwam.zkw.personalcenter.addinformation.AddStudentInformationActivity;
import com.zjwam.zkw.personalcenter.addinformation.AddTeacherInformationActivity;
import com.zjwam.zkw.register.RegisterChoiceActivity;
import com.zjwam.zkw.util.BadNetWork;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.ZkwPreference;


public class LoginFragment extends BaseActivity {

    private TextView btn_login;
    private TextView getback_passworld, register_name;
    private EditText login_name, login_passworld;
    private ImageView see_passworld,login_back;
    private Boolean isSee = false;
    private RelativeLayout progress_login;
    private FragmentManager manager;// Fragment碎片管理器
    private FragmentTransaction transaction;
    private PersonalFragment personalFragment;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        initView();
        initData();
    }

    private void initData() {
        btn_login.setOnClickListener(onClickListener);
        getback_passworld.setOnClickListener(onClickListener);
        register_name.setOnClickListener(onClickListener);
        see_passworld.setOnClickListener(onClickListener);
        login_back.setOnClickListener(onClickListener);
    }

    private void initView() {
        btn_login = findViewById(R.id.btn_login);
        getback_passworld = findViewById(R.id.getback_passworld);
        register_name = findViewById(R.id.register_name);
        login_name = findViewById(R.id.login_name);
        login_passworld = findViewById(R.id.login_passworld);
        see_passworld = findViewById(R.id.see_passworld);
        login_back = findViewById(R.id.login_back);
        progress_login = findViewById(R.id.progress_login);
        progress_login.getBackground().setAlpha(100);
        progress_login.setOnClickListener(null);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_login:
                    if (login_name.getText().toString().trim().length() == 0
                            && login_passworld.getText().toString().trim().length() == 0) {
                        Toast.makeText(getBaseContext(), "请完善登录信息！", Toast.LENGTH_SHORT).show();
                    } else {
                        login(Config.URL + "api/login/login?name=" + login_name.getText().toString().trim() + "&pass=" + login_passworld.getText().toString().trim());
                    }
                    break;
                case R.id.getback_passworld:
                    break;
                case R.id.register_name:
                    Intent intent = new Intent(getBaseContext(), RegisterChoiceActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.see_passworld:
                    if (isSee) {
                        login_passworld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        isSee = false;
                    } else {
                        login_passworld.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        isSee = true;
                    }
                    break;
                case R.id.login_back:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };


    private void login(String url) {
        OkGo.<String>get(url)
                .cacheMode(CacheMode.NO_CACHE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        progress_login.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        DialogInfo dialogInfo = Dialog2Json.getDialogInfo(response.body());
                        if ("1".equals(dialogInfo.getCode())) {
                            //保存登录状态
                            ZkwPreference.getInstance(getBaseContext()).SetIsFlag(true);
                            //保存uid
                            ZkwPreference.getInstance(getBaseContext()).SetUid(dialogInfo.getUid());
                            ZkwPreference.getInstance(getBaseContext()).SetRegisterType(dialogInfo.getType());
                            ZkwPreference.getInstance(getBaseContext()).SetPassword(login_passworld.getText().toString().trim());
                            login_name.setText("");
                            login_passworld.setText("");
                            finish();
//                            //登陆界面替换
//                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
//                                    .beginTransaction();
//                            fragmentTransaction.replace(R.id.fragment_mine, new PersonalFragment()).commit();

                        } else {
                            Toast.makeText(getBaseContext(), dialogInfo.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        progress_login.setVisibility(View.GONE);
                    }
                });

//        RequestParams params = new RequestParams(url);
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                DialogInfo dialogInfo = Dialog2Json.getDialogInfo(result);
//                if ("1".equals(dialogInfo.getCode())) {
//                    //保存登录状态
//                    ZkwPreference.getInstance(getActivity()).SetIsFlag(true);
//                    //保存uid
//                    ZkwPreference.getInstance(getActivity()).SetUid(dialogInfo.getUid());
//                    ZkwPreference.getInstance(getActivity()).SetRegisterType(dialogInfo.getType());
//                    ZkwPreference.getInstance(getActivity()).SetPassword(login_passworld.getText().toString().trim());
//                    login_name.setText("");
//                    login_passworld.setText("");
//                    //登陆界面替换
//                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
//                            .beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_mine, new PersonalFragment()).commit();
//
//                } else {
//                    Toast.makeText(getActivity(), dialogInfo.getMsg(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                new BadNetWork().isBadNetWork(getActivity());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//                progress_login.setVisibility(View.GONE);
//            }
//        });
    }

}
