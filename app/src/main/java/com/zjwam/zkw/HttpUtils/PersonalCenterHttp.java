package com.zjwam.zkw.HttpUtils;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.MineClassBean;
import com.zjwam.zkw.entity.MineIntegralBean;
import com.zjwam.zkw.entity.MineLearnCardBean;
import com.zjwam.zkw.entity.MineShopCartBean;
import com.zjwam.zkw.entity.PersonalCollectionBean;
import com.zjwam.zkw.entity.PersonalMineAnswerBean;
import com.zjwam.zkw.entity.PersonalMineAskBean;
import com.zjwam.zkw.entity.PersonalMineAskItemBean;
import com.zjwam.zkw.entity.PersonalMineCommentBean;
import com.zjwam.zkw.entity.PersonalNoteBookBean;
import com.zjwam.zkw.entity.PersonalOrderBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.personalcenter.CourseAnswerActivity;
import com.zjwam.zkw.personalcenter.MineClassActivity;
import com.zjwam.zkw.personalcenter.MineCollectionActivity;
import com.zjwam.zkw.personalcenter.MineCommentActivity;
import com.zjwam.zkw.personalcenter.MineIntegralActivity;
import com.zjwam.zkw.personalcenter.MineLearnCardActivity;
import com.zjwam.zkw.personalcenter.MineNoteBookActivity;
import com.zjwam.zkw.personalcenter.MineOrderActivity;
import com.zjwam.zkw.personalcenter.MineShopCartActivity;
import com.zjwam.zkw.personalcenter.PersonalMineAskActivity;
import com.zjwam.zkw.personalcenter.XGPasswordActivity;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class PersonalCenterHttp {
    private Context context;
    private Map<String, String> param;

    public PersonalCenterHttp(Context context) {
        this.context = context;
    }

    /**
     * 我的课程
     */
    public void getLearnAll(String uid, String type, String page) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("type", type);
        param.put("page", page);
        OkGoUtils.postRequets(Config.URL + "api/user/all_class", context, param, new Json2Callback<MineClassBean>() {
            @Override
            public void onSuccess(Response<MineClassBean> response) {
                if (context instanceof MineClassActivity && !((MineClassActivity) context).isFinishing()) {
                    ((MineClassActivity) context).getLearnAll(response);
                }
            }

            @Override
            public void onError(Response<MineClassBean> response) {
                super.onError(response);
                if (context instanceof MineClassActivity && !((MineClassActivity) context).isFinishing()) {
                    ((MineClassActivity) context).getLearnAllError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineClassActivity && !((MineClassActivity) context).isFinishing()) {
                    ((MineClassActivity) context).getLearnAllFinish();
                }
            }
        });
    }

    public void getLearning(String uid, String type, String page) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("type", type);
        param.put("page", page);
        OkGoUtils.postRequets(Config.URL + "api/user/all_class", context, param, new Json2Callback<MineClassBean>() {
            @Override
            public void onSuccess(Response<MineClassBean> response) {
                if (context instanceof MineClassActivity && !((MineClassActivity) context).isFinishing()) {
                    ((MineClassActivity) context).getLearning(response);
                }
            }

            @Override
            public void onError(Response<MineClassBean> response) {
                super.onError(response);
                if (context instanceof MineClassActivity && !((MineClassActivity) context).isFinishing()) {
                    ((MineClassActivity) context).getLearningError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineClassActivity && !((MineClassActivity) context).isFinishing()) {
                    ((MineClassActivity) context).getLearningFinish();
                }
            }
        });
    }

    public void getLearnOver(String uid, String type, String page) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("type", type);
        param.put("page", page);
        OkGoUtils.postRequets(Config.URL + "api/user/all_class", context, param, new Json2Callback<MineClassBean>() {
            @Override
            public void onSuccess(Response<MineClassBean> response) {
                if (context instanceof MineClassActivity && !((MineClassActivity) context).isFinishing()) {
                    ((MineClassActivity) context).getLearnOver(response);
                }
            }

            @Override
            public void onError(Response<MineClassBean> response) {
                super.onError(response);
                if (context instanceof MineClassActivity && !((MineClassActivity) context).isFinishing()) {
                    ((MineClassActivity) context).getLearnOverError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineClassActivity && !((MineClassActivity) context).isFinishing()) {
                    ((MineClassActivity) context).getLearnOverFinish();
                }
            }
        });
    }

    /**
     * 我的笔记
     */
    public void getNoteBookData(String uid, String page) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("page", page);
        OkGoUtils.postRequets(Config.URL + "api/user/note", context, param, new JsonCallback<ResponseBean<PersonalNoteBookBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PersonalNoteBookBean>> response) {
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()) {
                    ((MineNoteBookActivity) context).getNoteBookData(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PersonalNoteBookBean>> response) {
                super.onError(response);
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()) {
                    ((MineNoteBookActivity) context).getNoteBookDataError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()) {
                    ((MineNoteBookActivity) context).getNoteBookDataFinish();
                }
            }
        });
    }

    public void deleNoteBook(String uid, String id) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("id", id);
        OkGoUtils.postRequets(Config.URL + "api/user/note_del", context, param, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()) {
                    ((MineNoteBookActivity) context).deleNoteBook(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()) {
                    ((MineNoteBookActivity) context).deleNoteBookError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()) {
                    ((MineNoteBookActivity) context).deleNoteBookFinish();
                }
            }
        });
    }

    /**
     * 我的收藏
     */
    public void getCollection(String uid, String page) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("page", page);
        OkGoUtils.postRequets(Config.URL + "api/user/hold", context, param, new JsonCallback<ResponseBean<PersonalCollectionBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PersonalCollectionBean>> response) {
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()) {
                    ((MineCollectionActivity) context).getCollectio(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PersonalCollectionBean>> response) {
                super.onError(response);
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()) {
                    ((MineCollectionActivity) context).getCollectioError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()) {
                    ((MineCollectionActivity) context).getCollectioFinish();
                }
            }
        });
    }

    public void offCollection(String uid, String id) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("id", id);
        OkGoUtils.postRequets(Config.URL + "api/user/run_hold", context, param, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()) {
                    ((MineCollectionActivity) context).offCollection(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()) {
                    ((MineCollectionActivity) context).offCollectionError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()) {
                    ((MineCollectionActivity) context).offCollectionFinish();
                }
            }
        });
    }

    /**
     * 我的积分
     */
    public void getMineIntegral(String uid, String type, String page) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("type", type);
        param.put("page", page);
        OkGoUtils.postRequets(Config.URL + "api/user/jifen", context, param, new JsonCallback<ResponseBean<MineIntegralBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<MineIntegralBean>> response) {
                if (context instanceof MineIntegralActivity && !((MineIntegralActivity) context).isFinishing()) {
                    ((MineIntegralActivity) context).getMineIntegral(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<MineIntegralBean>> response) {
                super.onError(response);
                if (context instanceof MineIntegralActivity && !((MineIntegralActivity) context).isFinishing()) {
                    ((MineIntegralActivity) context).getMineIntegralError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineIntegralActivity && !((MineIntegralActivity) context).isFinishing()) {
                    ((MineIntegralActivity) context).getMineIntegralFinish();
                }
            }
        });
    }

    /**
     * 我的订单
     */
    public void getMineOrder(String uid, String type, String page) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("type", type);
        param.put("page", page);
        OkGoUtils.postRequets(Config.URL + "api/user/order", context, param, new JsonCallback<ResponseBean<PersonalOrderBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PersonalOrderBean>> response) {
                if (context instanceof MineOrderActivity && !((MineOrderActivity) context).isFinishing()) {
                    ((MineOrderActivity) context).getMineOrder(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PersonalOrderBean>> response) {
                super.onError(response);
                if (context instanceof MineOrderActivity && !((MineOrderActivity) context).isFinishing()) {
                    ((MineOrderActivity) context).getMineOrderError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineOrderActivity && !((MineOrderActivity) context).isFinishing()) {
                    ((MineOrderActivity) context).getMineOrderFinish();
                }
            }
        });
    }

    /**
     * 购物车
     */
    public void getMineShopCard(String uid) {
        param = new HashMap<>();
        param.put("uid", uid);
        OkGoUtils.postRequets(Config.URL + "api/user/car", context, param, new JsonCallback<ResponseBean<MineShopCartBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<MineShopCartBean>> response) {
                if (context instanceof MineShopCartActivity && !((MineShopCartActivity) context).isFinishing()) {
                    ((MineShopCartActivity) context).getMineShopCard(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<MineShopCartBean>> response) {
                super.onError(response);
                if (context instanceof MineShopCartActivity && !((MineShopCartActivity) context).isFinishing()) {
                    ((MineShopCartActivity) context).getMineShopCardError(response);
                }
            }
        });
    }

    public void deleteShopCard(String uid, String id) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("id", id);
        OkGoUtils.postRequets(Config.URL + "api/user/car_del", context, param, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof MineShopCartActivity && !((MineShopCartActivity) context).isFinishing()) {
                    ((MineShopCartActivity) context).deleteShopCard(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof MineShopCartActivity && !((MineShopCartActivity) context).isFinishing()) {
                    ((MineShopCartActivity) context).deleteShopCardError(response);
                }
            }
        });
    }

    /**
     * 课程答疑
     */
    public void mineAskQuestion(String uid, String page) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("page", page);
        OkGoUtils.postRequets(Config.URL + "api/user/question", context, param, new JsonCallback<ResponseBean<PersonalMineAskBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PersonalMineAskBean>> response) {
                if (context instanceof CourseAnswerActivity && !((CourseAnswerActivity) context).isFinishing()) {
                    ((CourseAnswerActivity) context).mineAskQuestion(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PersonalMineAskBean>> response) {
                super.onError(response);
                if (context instanceof CourseAnswerActivity && !((CourseAnswerActivity) context).isFinishing()) {
                    ((CourseAnswerActivity) context).mineAskQuestionError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof CourseAnswerActivity && !((CourseAnswerActivity) context).isFinishing()) {
                    ((CourseAnswerActivity) context).mineAskQuestionFinish();
                }
            }
        });
    }

    public void mineAnswerQuestion(String uid, String page) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("page", page);
        OkGoUtils.postRequets(Config.URL + "api/user/answer", context, param, new JsonCallback<ResponseBean<PersonalMineAnswerBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PersonalMineAnswerBean>> response) {
                if (context instanceof CourseAnswerActivity && !((CourseAnswerActivity) context).isFinishing()) {
                    ((CourseAnswerActivity) context).mineAnswerQuestion(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PersonalMineAnswerBean>> response) {
                super.onError(response);
                if (context instanceof CourseAnswerActivity && !((CourseAnswerActivity) context).isFinishing()) {
                    ((CourseAnswerActivity) context).mineAnswerQuestionError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof CourseAnswerActivity && !((CourseAnswerActivity) context).isFinishing()) {
                    ((CourseAnswerActivity) context).mineAnswerQuestionFinesh();
                }
            }
        });
    }

    /**
     * 课程答疑二级页面
     */
    public void personalMineAsk(String uid, String id, String page) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("id", id);
        param.put("page", page);
        OkGoUtils.postRequets(Config.URL + "api/user/question_detial", context, param, new JsonCallback<ResponseBean<PersonalMineAskItemBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PersonalMineAskItemBean>> response) {
                if (context instanceof PersonalMineAskActivity && !((PersonalMineAskActivity) context).isFinishing()) {
                    ((PersonalMineAskActivity) context).getMineAsk(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PersonalMineAskItemBean>> response) {
                super.onError(response);
                if (context instanceof PersonalMineAskActivity && !((PersonalMineAskActivity) context).isFinishing()) {
                    ((PersonalMineAskActivity) context).getMineAskError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof PersonalMineAskActivity && !((PersonalMineAskActivity) context).isFinishing()) {
                    ((PersonalMineAskActivity) context).getMineAskFinish();
                }
            }
        });
    }

    /**
     * 我的评价
     */
    public void mineComment(String uid, String page) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("page", page);
        OkGoUtils.postRequets(Config.URL + "api/user/comment", context, param, new JsonCallback<ResponseBean<PersonalMineCommentBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PersonalMineCommentBean>> response) {
                if (context instanceof MineCommentActivity && !((MineCommentActivity) context).isFinishing()) {
                    ((MineCommentActivity) context).mineComment(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PersonalMineCommentBean>> response) {
                super.onError(response);
                if (context instanceof MineCommentActivity && !((MineCommentActivity) context).isFinishing()) {
                    ((MineCommentActivity) context).mineCommentError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineCommentActivity && !((MineCommentActivity) context).isFinishing()) {
                    ((MineCommentActivity) context).mineCommentFinish();
                }
            }
        });
    }

    /**
     * 我的学习卡
     */
    public void mineLearnCard(String uid, String type) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("type", type);
        OkGoUtils.postRequets(Config.URL + "api/user/combo_list", context, param, new JsonCallback<ResponseBean<MineLearnCardBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<MineLearnCardBean>> response) {
                if (context instanceof MineLearnCardActivity && !((MineLearnCardActivity) context).isFinishing()) {
                    ((MineLearnCardActivity) context).mineLearnCard(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<MineLearnCardBean>> response) {
                super.onError(response);
                if (context instanceof MineLearnCardActivity && !((MineLearnCardActivity) context).isFinishing()) {
                    ((MineLearnCardActivity) context).mineLearnCardError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineLearnCardActivity && !((MineLearnCardActivity) context).isFinishing()) {
                    ((MineLearnCardActivity) context).mineLearnCardFinish();
                }
            }
        });
    }

    public void getActivation(String uid, String card_num, String card_pwd) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("card_num", card_num);
        param.put("card_pwd", card_pwd);
        OkGoUtils.postRequets(Config.URL + "api/user/combo", context, param, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof MineLearnCardActivity && !((MineLearnCardActivity) context).isFinishing()) {
                    ((MineLearnCardActivity) context).getActivation(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof MineLearnCardActivity && !((MineLearnCardActivity) context).isFinishing()) {
                    ((MineLearnCardActivity) context).getActivationError(response);
                }
            }
        });
    }

    public void XGPassword(String uid, String newpass) {
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("newpass", newpass);
        OkGoUtils.postRequets(Config.URL + "api/Login/change_pwd", context, param, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof XGPasswordActivity && !((XGPasswordActivity) context).isFinishing()) {
                    ((XGPasswordActivity) context).xgPassword(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof XGPasswordActivity && !((XGPasswordActivity) context).isFinishing()) {
                    ((XGPasswordActivity) context).xgPasswordError(response);
                }
            }
        });
    }
}
