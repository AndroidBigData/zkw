package com.zjwam.zkw.personalcenter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.zjwam.zkw.adapter.PersonalMineLearnCardAdapter;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.customview.ActivationDialog;
import com.zjwam.zkw.customview.ActivationFailedDialog;
import com.zjwam.zkw.customview.LearnCardSuccessDialog;
import com.zjwam.zkw.customview.LearnCardUnuseDialog;
import com.zjwam.zkw.customview.LearnCardUsedDialog;
import com.zjwam.zkw.customview.LearnCardingDialog;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.MineLearnCardBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.ZkwPreference;
import com.zjwam.zkw.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class MineLearnCardActivity extends BaseActivity {

    private ImageView learncard_nodata, mine_learncard_back;
    private TextView learncard_buy, learncard_activation;
    private ExpandableListView learncard_listview;
    private ActivationDialog activationDialog;
    private ActivationFailedDialog activationFailedDialog;
    private LearnCardSuccessDialog learnCardSuccessDialog;
    private String uid = "", error_msg = "", url, title, cardNum, cardWorld;
    private MineLearnCardBean.getLearnCardItems card;
    private List<String> grouplist;
    private List<List<MineLearnCardBean.getLearnCard>> childrenlist;
    private MineLearnCardBean.getLearnCard learnCard;
    private PersonalMineLearnCardAdapter mineLearnCardAdapter;
    private LearnCardingDialog learnCardingDialog;
    private LearnCardUnuseDialog learnCardUnuseDialog;
    private LearnCardUsedDialog learnCardUsedDialog;
    private boolean isUnused = false;

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
                bundle.putString("url", url);
                bundle.putString("title", title);
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

        learncard_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // TODO Auto-generated method stub
                return true;
            }
        });
        learncard_listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                learnCard = childrenlist.get(i).get(i1);
                if (learnCard.getType() == 0) {
                    learnCardingDialog = new LearnCardingDialog(MineLearnCardActivity.this, learnCard.getCard_num(), learnCard.getCard_pwd(), learnCard.getUsername(), learnCard.getOvertime());
                    learnCardingDialog.show();
                    learnCardingDialog.setClickListener(new LearnCardingDialog.ClickListenerInterface() {
                        @Override
                        public void doCancel(View view) {
                            learnCardingDialog.dismiss();
                        }
                    });
                } else if (learnCard.getType() == 1) {
                    if (card.getOn().size() > 0) {
                        learnCardUnuseDialog = new LearnCardUnuseDialog(MineLearnCardActivity.this, learnCard.getCard_num(), learnCard.getCard_pwd(), "一键复制");
                        learnCardUnuseDialog.show();
                        learnCardUnuseDialog.setClickListener(new LearnCardUnuseDialog.ClickListenerInterface() {
                            @Override
                            public void doCancel(View view) {
                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clipData = ClipData.newPlainText("text", "账号：" + learnCard.getCard_num() + "密码：" + learnCard.getCard_pwd());
                                cm.setPrimaryClip(clipData);
                                Toast.makeText(getBaseContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                                learnCardUnuseDialog.dismiss();
                            }
                        });
                    } else {
                        cardNum = learnCard.getCard_num();
                        cardWorld = learnCard.getCard_pwd();
                        learnCardUnuseDialog = new LearnCardUnuseDialog(MineLearnCardActivity.this, cardNum, cardWorld, "激 活");
                        learnCardUnuseDialog.show();
                        learnCardUnuseDialog.setClickListener(new LearnCardUnuseDialog.ClickListenerInterface() {
                            @Override
                            public void doCancel(View view) {
                                isUnused = true;
                                getActivationMsg(learnCard.getCard_num(), learnCard.getCard_pwd());
                                learnCardUnuseDialog.dismiss();
                            }
                        });
                    }
                } else if (learnCard.getType() == 2) {
                    learnCardUsedDialog = new LearnCardUsedDialog(MineLearnCardActivity.this, learnCard.getCard_num(), learnCard.getCard_pwd(), learnCard.getUsername());
                    learnCardUsedDialog.show();
                    learnCardUsedDialog.setClickListener(new LearnCardUsedDialog.ClickListenerInterface() {
                        @Override
                        public void doCancel(View view) {
                            learnCardUsedDialog.dismiss();
                        }

                        @Override
                        public void doConfirm(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putString("url", url);
                            bundle.putString("title", title);
                            bundle.putString("type", "type");
                            startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtras(bundle));
                            learnCardUsedDialog.dismiss();
                        }
                    });
                }
                return true;
            }
        });
    }

    private void getLearnCardMsg() {
        OkGo.<ResponseBean<MineLearnCardBean>>post(Config.URL + "api/user/combo_list ")
                .params("uid", uid)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<MineLearnCardBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<MineLearnCardBean>> response) {
                        ResponseBean<MineLearnCardBean> data = response.body();
                        url = data.data.getAd().getUrl();
                        title = data.data.getAd().getTitle();
                        grouplist = new ArrayList<>();
                        childrenlist = new ArrayList<>();
                        card = data.data.getCard();
                        if (card.getOn().size() > 0 || card.getStart().size() > 0 || card.getEnd().size() > 0) {
                            if (card.getOn().size() > 0) {
                                grouplist.add("使用中的卡片");
                                childrenlist.add(card.getOn());
                            }
                            if (card.getStart().size() > 0) {
                                grouplist.add("未使用的卡片");
                                childrenlist.add(card.getStart());
                            }
                            if (card.getEnd().size() > 0) {
                                grouplist.add("已失效的卡片");
                                childrenlist.add(card.getEnd());
                            }
                            mineLearnCardAdapter = new PersonalMineLearnCardAdapter(getBaseContext(), grouplist, childrenlist);
                            learncard_listview.setAdapter(mineLearnCardAdapter);
                            learncard_nodata.setVisibility(View.GONE);
                            learncard_buy.setVisibility(View.GONE);
                            learncard_activation.setVisibility(View.GONE);
                        } else {
                            learncard_nodata.setVisibility(View.VISIBLE);
                            learncard_buy.setVisibility(View.VISIBLE);
                            learncard_activation.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Response<ResponseBean<MineLearnCardBean>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException) {
                            Toast.makeText(getBaseContext(), ((MyException) exception).getErrorBean().msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (grouplist.size() > 0) {
                            for (int i = 0; i < grouplist.size(); i++) {
                                learncard_listview.expandGroup(i);
                            }
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
                        if (activationDialog != null) {
                            activationDialog.dismiss();
                        }
                        learnCardSuccessDialog.show();
                        getLearnCardMsg();

                    }

                    @Override
                    public void onError(Response<ResponseBean<EmptyBean>> response) {
                        super.onError(response);
                        if (activationDialog != null) {
                            activationDialog.dismiss();
                        }
                        Throwable exception = response.getException();
                        if (exception instanceof MyException) {
                            error_msg = ((MyException) exception).getErrorBean().msg;
                            activationFailedDialog = new ActivationFailedDialog(MineLearnCardActivity.this, "激活失败", error_msg);
                            activationFailedDialog.show();
                            activationFailedDialog.setClickListener(new ActivationFailedDialog.ClickListenerInterface() {
                                @Override
                                public void doConfirm(View view) {
                                    if (isUnused) {
                                        getActivationMsg(cardNum, cardWorld);
                                        activationFailedDialog.dismiss();
                                    } else {
                                        if (activationDialog != null) {
                                            activationDialog.show();
                                        }
                                        activationFailedDialog.dismiss();
                                    }

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
