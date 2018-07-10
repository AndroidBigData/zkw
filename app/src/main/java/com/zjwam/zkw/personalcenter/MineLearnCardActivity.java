package com.zjwam.zkw.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.customview.ActivationDialog;
import com.zjwam.zkw.customview.ActivationFailedDialog;
import com.zjwam.zkw.customview.LearnCardSuccessDialog;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.MineLearnCardBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.ZkwPreference;
import com.zjwam.zkw.webview.WebViewActivity;

import java.util.List;

import static com.lzy.okgo.OkGo.post;

public class MineLearnCardActivity extends BaseActivity {

    private ImageView learncard_nodata,mine_learncard_back;
    private TextView learncard_buy, learncard_activation;
    private ExpandableListView learncard_listview;
    private ActivationDialog activationDialog;
    private ActivationFailedDialog activationFailedDialog;
    private LearnCardSuccessDialog learnCardSuccessDialog;
    private String uid = "", error_msg = "";
    private List<MineLearnCardBean.getLearnCard> learnCards;
    private MineLearnCardBean.getLearnCardItems card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_learn_card);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        learncard_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", "");
                bundle.putString("title", "");
                bundle.putString("type", "type");
                startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtras(bundle));
            }
        });
        mine_learncard_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        activationDialog = new ActivationDialog(MineLearnCardActivity.this);
        learnCardSuccessDialog = new LearnCardSuccessDialog(MineLearnCardActivity.this, "激活成功", "  恭喜！激活成功！", null);


        learncard_activation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activationDialog.show();
            }
        });

        activationDialog.setClickListener(new ActivationDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(View view) {
                activationDialog.dismiss();
            }

            @Override
            public void makeGo(String num, String world) {
                if (num.length() > 0 && world.length() > 0) {
                    getActivationMsg(num, world);
                } else {
                    Toast.makeText(getBaseContext(), "请完善信息", Toast.LENGTH_SHORT).show();
                }
            }
        });

        learnCardSuccessDialog.setClickListener(new LearnCardSuccessDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(View view) {
                learnCardSuccessDialog.dismiss();
            }
        });

        getLearnCardMsg();

    }

    private void getLearnCardMsg() {
        OkGo.<ResponseBean<MineLearnCardBean>>post(Config.URL+"api/user/combo_list ")
                .params("uid",uid)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<MineLearnCardBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<MineLearnCardBean>> response) {
                        ResponseBean<MineLearnCardBean> data = response.body();
                        card  = data.data.getCard();
                        if (card.getOn().size()>0){
                            learnCards.addAll(card.getOn());
                        }
                        if (card.getStart().size()>0){
                            learnCards.addAll(card.getStart());
                        }
                        if (card.getEnd().size()>0){
                            learnCards.addAll(card.getEnd());
                        }
                    }
                });
    }




    private void getActivationMsg(String card_num, String card_pwd) {
        OkGo.<ResponseBean<EmptyBean>>post(Config.URL + "api/user/combo")
                .params("uid", uid)
                .params("card_num", card_num)
                .params("card_pwd", card_pwd)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<EmptyBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                        activationDialog.dismiss();
                        learnCardSuccessDialog.show();
                    }

                    @Override
                    public void onError(Response<ResponseBean<EmptyBean>> response) {
                        super.onError(response);
                        activationDialog.dismiss();
                        Throwable exception = response.getException();
                        if (exception instanceof MyException) {
                            error_msg = ((MyException) exception).getErrorBean().msg;
                            activationFailedDialog = new ActivationFailedDialog(MineLearnCardActivity.this, "激活失败", error_msg);
                            activationFailedDialog.show();
                            activationFailedDialog.setClickListener(new ActivationFailedDialog.ClickListenerInterface() {
                                @Override
                                public void doConfirm(View view) {
                                    activationDialog.show();
                                    activationFailedDialog.dismiss();
                                }

                                @Override
                                public void doCancel(View view) {
                                    activationFailedDialog.dismiss();
                                }
                            });
                        }

                    }
                });
    }

    private void initView() {
        learncard_nodata = findViewById(R.id.learncard_nodata);
        learncard_buy = findViewById(R.id.learncard_buy);
        learncard_activation = findViewById(R.id.learncard_activation);
        learncard_listview = findViewById(R.id.learncard_listview);
        mine_learncard_back = findViewById(R.id.mine_learncard_back);
    }
}
