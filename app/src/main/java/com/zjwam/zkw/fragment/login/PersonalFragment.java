package com.zjwam.zkw.fragment.login;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.R;
import com.zjwam.zkw.personalcenter.XGPasswordActivity;
import com.zjwam.zkw.personalcenter.addinformation.AddCompanyInformationActivity;
import com.zjwam.zkw.personalcenter.addinformation.AddStudentInformationActivity;
import com.zjwam.zkw.personalcenter.addinformation.AddTeacherInformationActivity;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.util.ZkwPreference;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {

    private ImageView personal_tx;
    private TextView personal_xgmm, personal_addmore, personal_logout, personal_nickname;
    private View rootView;
    private FragmentManager manager;// Fragment碎片管理器
    private FragmentTransaction transaction;
    private LoginFragment loginFragment;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_personal, container, false);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_personal, null);
            Log.i("---1", "111");
        } else {
            //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        initView(rootView);
        return rootView;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Log.i("---2","222");
//
//    }

    private void initData() {
        personal_tx.setOnClickListener(onClickListener);
        personal_xgmm.setOnClickListener(onClickListener);
        personal_addmore.setOnClickListener(onClickListener);
        personal_logout.setOnClickListener(onClickListener);
        getData();
    }

    private void getData() {

        OkGo.<String>get(Config.URL + "api/user/index?uid=" + ZkwPreference.getInstance(getActivity()).getUid())
                .tag(this)
                .cacheKey("PersonalFragment")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject data = new JSONObject(response.body());
                            String name = data.getString("nickname");
                            String tx = data.getString("pic");
                            personal_nickname.setText(name);
                            if (!tx.isEmpty()) {
                                GlideImageUtil.setImageView(getActivity(),tx,personal_tx,GlideImageUtil.circleTransform());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
//        RequestParams params = new RequestParams(Config.URL + "api/user/index?uid=" + ZkwPreference.getInstance(getActivity()).getUid());
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    JSONObject data = new JSONObject(result);
//                    String name = data.getString("nickname");
//                    String tx = data.getString("pic");
//                    personal_nickname.setText(name);
//                    if (!tx.isEmpty()) {
//                        x.image().bind(personal_tx, tx, options);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.personal_tx:
                    break;
                case R.id.personal_xgmm:
                    startActivity(new Intent(getActivity(), XGPasswordActivity.class));
                    break;
                case R.id.personal_addmore:
                    String type = ZkwPreference.getInstance(getActivity()).getRegisterType();
                    if ("0".equals(type)) {
                        startActivity(new Intent(getActivity(), AddStudentInformationActivity.class));
                    }
                    if ("1".equals(type)) {
                        startActivity(new Intent(getActivity(), AddTeacherInformationActivity.class));
                    }
                    if ("2".equals(type)) {
                        startActivity(new Intent(getActivity(), AddCompanyInformationActivity.class));
                    }

                    break;
                case R.id.personal_logout:
                    ZkwPreference.getInstance(getActivity()).SetIsFlag(false);
                    ZkwPreference.getInstance(getActivity()).SetLoginName("");
                    ZkwPreference.getInstance(getActivity()).SetPassword("");
                    ZkwPreference.getInstance(getActivity()).SetUid("");
                    ZkwPreference.getInstance(getActivity()).SetRegisterType("");

//                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
//                            .beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_mine, new LoginFragment()).commit();
                    break;
            }
        }
    };


    private void initView(View rootView) {
        personal_tx = rootView.findViewById(R.id.personal_tx);
        personal_xgmm = rootView.findViewById(R.id.personal_xgmm);
        personal_addmore = rootView.findViewById(R.id.personal_addmore);
        personal_logout = rootView.findViewById(R.id.personal_logout);
        personal_nickname = rootView.findViewById(R.id.personal_nickname);
        initData();
    }
}
