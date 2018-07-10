package com.zjwam.zkw.pay;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.MainActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.PayPreviewAdapter;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.PayMsgBean;
import com.zjwam.zkw.entity.PayPreviewBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.personalcenter.MineShopCartActivity;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.ZkwPreference;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

import java.util.List;
import java.util.Map;

public class PayPreviewActivity extends BaseActivity {

    private ImageView preview_back;
    private LRecyclerView preview_recyclerview;
    private CheckBox zfb_check, wx_check;
    private TextView preview_pay;
    private PayPreviewAdapter previewAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private String id = "",clid = "",uid,ids = "",title = "";
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_preview);
        initView();
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        clid = bundle.getString("clid");
        title = bundle.getString("title");
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        previewAdapter = new PayPreviewAdapter(getBaseContext());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(previewAdapter);
        preview_recyclerview.setAdapter(lRecyclerViewAdapter);
        preview_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        preview_recyclerview.setPullRefreshEnabled(false);
        preview_recyclerview.setLoadMoreEnabled(false);
        preview_back.setOnClickListener(onClickListener);
        zfb_check.setOnClickListener(onClickListener);
        wx_check.setOnClickListener(onClickListener);
        preview_pay.setOnClickListener(onClickListener);
        if (id != null && id.length()>0){
            getPreviewMsg(Config.URL + "api/pay/pay_verify","id",id);
        }
        if ( clid != null&& clid.length()>0){
            getPreviewMsg(Config.URL + "api/pay/pay_verify","clid",clid);
        }

    }

    private void getPreviewMsg(String url,String idtype,String id) {
        OkGo.<ResponseBean<PayPreviewBean>>post(url)
                .params(idtype,id)
                .params("uid",uid)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<ResponseBean<PayPreviewBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<PayPreviewBean>> response) {
                        List<PayPreviewBean.getOrderItems> item = response.body().data.getOrder();
                        previewAdapter.addAll(item);
                        for (int i= 0; i<item.size() ;i++){
                            ids = ids +item.get(i).getId() + "_";
                        }
                    }

                    @Override
                    public void onError(Response<ResponseBean<PayPreviewBean>> response) {
                        super.onError(response);
                        Throwable exception = response.getException();
                        if (exception instanceof MyException){
                            Toast.makeText(getBaseContext(),((MyException) exception).getErrorBean().msg,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.preview_back:
                    finish();
                    break;
                case R.id.zfb_check:
                    zfb_check.setChecked(true);
                    wx_check.setChecked(false);
                    break;
                case R.id.wx_check:
                    wx_check.setChecked(true);
                    zfb_check.setChecked(false);
                    break;
                case R.id.preview_pay:
                    if (zfb_check.isChecked()){
                        payMsg(Config.URL + "api/pay/pay",ids);
                    }else if (wx_check.isChecked()){
                        Toast.makeText(getBaseContext(),"等待接入",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getBaseContext(),"请选择支付方式",Toast.LENGTH_SHORT).show();
                    }

                    break;
                default:
                    break;
            }
        }
    };


    private void payMsg(String url,String id) {
        OkGo.<ResponseBean<PayMsgBean>>post(url)
                .params("uid",uid)
                .params("id",id)
                .cacheMode(CacheMode.NO_CACHE)
                .tag(this)
                .execute(new JsonCallback<ResponseBean<PayMsgBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<PayMsgBean>> response) {
                        final ResponseBean<PayMsgBean> orderInfo = response.body();
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(PayPreviewActivity.this);
                                Map<String, String> result = alipay.payV2(orderInfo.data.getOrderinfo(), true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };

                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                });
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     *对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(getBaseContext(), "支付成功", Toast.LENGTH_SHORT).show();
                        if (id != null && id.length()>0){

                            finish();
                        }
                        if ( clid != null&& clid.length()>0){
                            Bundle bundle = new Bundle();
                            bundle.putString("id",clid);
                            bundle.putString("title",title);
                            startActivity(new Intent(getBaseContext(),Video2PlayActivity.class).putExtras(bundle));
                            finish();
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getBaseContext(), "支付失败", Toast.LENGTH_SHORT).show();
                        if (id != null && id.length()>0){
                            startActivity(new Intent(getBaseContext(), MineShopCartActivity.class));
                            finish();
                        }
                        if ( clid != null&& clid.length()>0){
                            Bundle bundle = new Bundle();
                            bundle.putString("id",clid);
                            bundle.putString("title",title);
                            startActivity(new Intent(getBaseContext(),Video2PlayActivity.class).putExtras(bundle));
                            finish();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private void initView() {
        preview_back = findViewById(R.id.preview_back);
        preview_recyclerview = findViewById(R.id.preview_recyclerview);
        zfb_check = findViewById(R.id.zfb_check);
        wx_check = findViewById(R.id.wx_check);
        preview_pay = findViewById(R.id.preview_pay);
    }

}
