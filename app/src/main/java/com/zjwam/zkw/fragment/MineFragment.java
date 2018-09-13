package com.zjwam.zkw.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.httputils.MainActivityHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.customview.CustomToast;
import com.zjwam.zkw.entity.PersoanlMessage;
import com.zjwam.zkw.entity.PersonalQDBean;
import com.zjwam.zkw.fragment.login.LoginFragment;
import com.zjwam.zkw.fragment.login.PersonalFragment;
import com.zjwam.zkw.personalcenter.CourseAnswerActivity;
import com.zjwam.zkw.personalcenter.MineClassActivity;
import com.zjwam.zkw.personalcenter.MineCollectionActivity;
import com.zjwam.zkw.personalcenter.MineCommentActivity;
import com.zjwam.zkw.personalcenter.MineExamBankActivity;
import com.zjwam.zkw.personalcenter.MineIntegralActivity;
import com.zjwam.zkw.personalcenter.job.MineJobActivity;
import com.zjwam.zkw.personalcenter.MineLearnCardActivity;
import com.zjwam.zkw.personalcenter.MineNoteBookActivity;
import com.zjwam.zkw.personalcenter.MineOrderActivity;
import com.zjwam.zkw.personalcenter.MineShopCartActivity;
import com.zjwam.zkw.personalcenter.SetUpActivity;
import com.zjwam.zkw.util.GlideImageUtil;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.util.ZkwPreference;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    private FragmentManager manager;// Fragment碎片管理器
    private FragmentTransaction transaction;
    private LoginFragment loginFragment;
    private PersonalFragment centerFragment;
    private boolean flag;
    private ListView personal_list;
    private List<Map<String, Object>> data;
    private Map<String, Object> item;
    private View headerView, footerView;
    private ImageView personal_tx, personal_message;
    private TextView personal_nickname, personal_num, personal_qd;
    private LinearLayout personal_mine_class, personal_mine_note_book, personal_mine_error, personal_mine_sc, jifen;
    private boolean isInitCache = false, isFlag, isSame = true;
    private RelativeLayout personal_footer_item1, personal_footer_item2;
    private String uid = "";
    private CustomToast customToast;
    private PersoanlMessage persoanlMessage;
    private Context context;
    private MainActivityHttp mainActivityHttp;

    public MineFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public MineFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mainActivityHttp = new MainActivityHttp(context);
        data = new ArrayList<>();
        item = new HashMap<>();
        item.put("img", R.drawable.personal_jifen);
        item.put("title", "我的积分");
        data.add(item);

        item = new HashMap<>();
        item.put("img", R.drawable.personal_dingdan);
        item.put("title", "我的订单");
        data.add(item);

        item = new HashMap<>();
        item.put("img", R.drawable.personal_gouwuche);
        item.put("title", "购物车");
        data.add(item);

        item = new HashMap<>();
        item.put("img", R.drawable.personal_dayi);
        item.put("title", "课程答疑");
        data.add(item);

//        item = new HashMap<>();
//        item.put("img", R.drawable.personal_dongtai);
//        item.put("title", "论坛动态");
//        data.add(item);

        item = new HashMap<>();
        item.put("img", R.drawable.personal_pingjia);
        item.put("title", "我的评价");
        data.add(item);

        item = new HashMap<>();
        item.put("img", R.drawable.personal_learncard);
        item.put("title", "我的学习卡");
        data.add(item);

        item = new HashMap<>();
        item.put("img", R.drawable.personal_qiuzhi);
        item.put("title", "我的求职");
        data.add(item);

//        item = new HashMap<>();
//        item.put("img", R.drawable.personal_baoming);
//        item.put("title", "考试报名");
//        data.add(item);

        String[] from = {"img", "title"};
        int[] to = {R.id.personal_item_img, R.id.personal_item_title};
        SimpleAdapter personalAdapter = new SimpleAdapter(getActivity(), data, R.layout.personal_list_item, from, to);
        personal_list.addHeaderView(headerView, null, false);
        personal_list.addFooterView(footerView, null, false);
        personal_list.setAdapter(personalAdapter);

        customToast = new CustomToast(context);
        personal_nickname.setOnClickListener(onClickListener);
        personal_mine_class.setOnClickListener(onClickListener);
        personal_mine_note_book.setOnClickListener(onClickListener);
        personal_mine_error.setOnClickListener(onClickListener);
        personal_mine_sc.setOnClickListener(onClickListener);
//        personal_footer_item1.setOnClickListener(onClickListener);
        personal_footer_item2.setOnClickListener(onClickListener);
        personal_tx.setOnClickListener(onClickListener);
        personal_num.setOnClickListener(onClickListener);
        personal_qd.setOnClickListener(onClickListener);
        personal_message.setOnClickListener(onClickListener);

        personal_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 1:
//                        Toast.makeText(getActivity(),"我的积分",Toast.LENGTH_SHORT).show();
                        if (isFlag) {
                            startActivity(new Intent(getActivity(), MineIntegralActivity.class));
                        } else {
                            showDialog();
                        }

                        break;
                    case 2:
//                        Toast.makeText(getActivity(),"我的订单",Toast.LENGTH_SHORT).show();
                        if (isFlag) {
                            startActivity(new Intent(getActivity(), MineOrderActivity.class));
                        } else {
                            showDialog();
                        }

                        break;
                    case 3:
//                        Toast.makeText(getActivity(),"购物车",Toast.LENGTH_SHORT).show();
                        if (isFlag) {
                            startActivity(new Intent(getActivity(), MineShopCartActivity.class));
                        } else {
                            showDialog();
                        }

                        break;
                    case 4:
//                        Toast.makeText(getActivity(),"课程答疑",Toast.LENGTH_SHORT).show();
                        if (isFlag) {
                            startActivity(new Intent(getActivity(), CourseAnswerActivity.class));
                        } else {
                            showDialog();
                        }

                        break;
//                    case 5:
//                        Toast.makeText(getActivity(),"论坛动态",Toast.LENGTH_SHORT).show();
//                        break;
                    case 5:
//                        Toast.makeText(getActivity(),"我的评价",Toast.LENGTH_SHORT).show();
                        if (isFlag) {
                            startActivity(new Intent(getActivity(), MineCommentActivity.class));
                        } else {
                            showDialog();
                        }

                        break;
                    case 6:
                        if (isFlag) {
                            startActivity(new Intent(getActivity(), MineLearnCardActivity.class));
                        } else {
                            showDialog();
                        }
                        break;
                    case 7:
                        if (isFlag) {
                            startActivity(new Intent(getActivity(), MineJobActivity.class));
                        } else {
                            showDialog();
                        }
                        break;
//                    case 8:
//                        Toast.makeText(getActivity(),"考试报名",Toast.LENGTH_SHORT).show();
//                        break;

                }
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.personal_tx:
                    if (!isFlag) {
                        startActivity(new Intent(getActivity(), LoginFragment.class));
                    }
                    break;
                case R.id.personal_nickname:
                    if (!isFlag) {
                        startActivity(new Intent(getActivity(), LoginFragment.class));
                    }
                    break;
                case R.id.personal_num:
                    break;
                case R.id.personal_qd:
                    if (isFlag) {
                        mainActivityHttp.qdMessage(uid);
                        personal_qd.setClickable(false);
                    } else {
                        showDialog();
                    }

                    break;
                case R.id.personal_message:
                    break;
                case R.id.personal_mine_class:
                    if (isFlag) {
                        startActivity(new Intent(getActivity(), MineClassActivity.class));
                    } else {
                        showDialog();
                    }

                    break;
                case R.id.personal_mine_note_book:
                    if (isFlag) {
                        startActivity(new Intent(getActivity(), MineNoteBookActivity.class));
                    } else {
                        showDialog();
                    }

                    break;
                case R.id.personal_mine_error:
                    if (isFlag) {
                        startActivity(new Intent(getActivity(), MineExamBankActivity.class));
                    } else {
                        showDialog();
                    }
                    break;
                case R.id.personal_mine_sc:
//                    Toast.makeText(getActivity(), "4444", Toast.LENGTH_SHORT).show();
                    if (isFlag) {
                        startActivity(new Intent(getActivity(), MineCollectionActivity.class));
                    } else {
                        showDialog();
                    }

                    break;
//                case R.id.personal_footer_item1:
//                    Toast.makeText(getActivity(), "5555", Toast.LENGTH_SHORT).show();
//                    break;
                case R.id.personal_footer_item2:
//                    Toast.makeText(getActivity(), "6666", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), SetUpActivity.class));
                    break;
            }
        }
    };

    public void qdMessage(Response<PersonalQDBean> response) {
        PersonalQDBean personalQDBean = response.body();
        if ("1".equals(personalQDBean.getCode())) {
            customToast.setsToast(personalQDBean.getMsg() + ",获得" + personalQDBean.getData().getJifen() + "积分", R.drawable.personal_smile);
            personal_num.setText(String.valueOf(Integer.parseInt(personal_num.getText().toString()) + Integer.parseInt(personalQDBean.getData().getJifen())));
        } else {
            customToast.setsToast(personalQDBean.getMsg(), R.drawable.personal_hard);
        }
    }

    public void qdMessageError(Response<PersonalQDBean> response) {
//        if (!NetworkUtils.isNetAvailable(context)) {
//            customToast = new CustomToast(getActivity());
//            customToast.setsToast("签到失败，网络不可用", R.drawable.personal_hard);
//        }
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        customToast.setsToast(error, R.drawable.personal_hard);

    }

    public void qdMessageFinish() {
        personal_qd.setClickable(true);
    }

    public void getPersonalMessage(Response<PersoanlMessage> response) {
        persoanlMessage = response.body();
        if (isFlag) {
            GlideImageUtil.setImageView(getActivity(), persoanlMessage.getPic(), personal_tx, GlideImageUtil.circleTransform());
            personal_nickname.setText(persoanlMessage.getNickname());
            personal_num.setText(String.valueOf(persoanlMessage.getJifen()));
        } else {
            GlideImageUtil.setImageView(getActivity(), persoanlMessage.getPic(), personal_tx, GlideImageUtil.circleTransform());
        }
    }

    private void initView() {
        personal_list = getActivity().findViewById(R.id.personal_list);
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.personal_header, null);
        footerView = LayoutInflater.from(getActivity()).inflate(R.layout.personal_footer, null);
        personal_tx = headerView.findViewById(R.id.personal_tx);
        personal_message = headerView.findViewById(R.id.personal_message);
        personal_nickname = headerView.findViewById(R.id.personal_nickname);
        personal_num = headerView.findViewById(R.id.personal_num);
        personal_qd = headerView.findViewById(R.id.personal_qd);
        personal_mine_class = headerView.findViewById(R.id.personal_mine_class);
        personal_mine_note_book = headerView.findViewById(R.id.personal_mine_note_book);
        personal_mine_error = headerView.findViewById(R.id.personal_mine_error);
        personal_mine_sc = headerView.findViewById(R.id.personal_mine_sc);
        jifen = headerView.findViewById(R.id.jifen);
//        personal_footer_item1 = footerView.findViewById(R.id.personal_footer_item1);
        personal_footer_item2 = footerView.findViewById(R.id.personal_footer_item2);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        manager = getChildFragmentManager();// 获取Fragment管理器
//        transaction = manager.beginTransaction();// 从管理器中得到一个Fragment事务
//        flag = ZkwPreference.getInstance(getActivity()).IsFlag();
//        if (flag == false) {
//            if (loginFragment==null){
//                loginFragment = new LoginFragment();
//            }
//            if (!loginFragment.isAdded()){
//                transaction.add(R.id.fragment_mine,loginFragment);//将得到的fragment替换当前的viewGroup内
//            }
//
//        }else {
//            if (centerFragment==null){
//                centerFragment = new PersonalFragment();
//            }
//            if (!centerFragment.isAdded()){
//                transaction.add(R.id.fragment_mine,centerFragment);//将得到的fragment替换当前的viewGroup内
//            }
//
//        }
//        transaction.commit();
//    }


    @Override
    public void onResume() {
        super.onResume();
        isFlag = ZkwPreference.getInstance(getActivity()).IsFlag();
        uid = ZkwPreference.getInstance(getActivity()).getUid();

        if (isFlag && isFlag == isSame) {
            mainActivityHttp.getPersonalMessage(uid);
            jifen.setVisibility(View.VISIBLE);
            isSame = false;
        } else if (!isFlag) {
            jifen.setVisibility(View.INVISIBLE);
            personal_nickname.setText("点击登录");
//            personal_tx.setImageResource(R.drawable.zkw_r);
            mainActivityHttp.getPersonalMessage(uid);
            isSame = true;
        }
    }


    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                startActivity(new Intent(getActivity(), LoginFragment.class));
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
