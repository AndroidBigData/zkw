package com.zjwam.zkw.pay;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.httputils.PayPreviewHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.PayPreviewAdapter;
import com.zjwam.zkw.entity.PayMsgBean;
import com.zjwam.zkw.entity.PayPreviewBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.WxPayBean;
import com.zjwam.zkw.personalcenter.MineShopCartActivity;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.ZkwPreference;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

import java.util.List;
import java.util.Map;

public class PayPreviewActivity extends BaseActivity{

    private ImageView preview_back;
    private LRecyclerView preview_recyclerview;
    private CheckBox zfb_check, wx_check;
    private TextView preview_pay;
    private PayPreviewAdapter previewAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private String id = "",clid = "",uid,ids = "",title = "";
    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI msgApi;
    private PayPreviewHttp payPreviewHttp;
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
        payPreviewHttp = new PayPreviewHttp(this);
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
            payPreviewHttp.getPreviewMsg(uid,"id",id);
        }
        if ( clid != null&& clid.length()>0){
            payPreviewHttp.getPreviewMsg(uid,"clid",clid);
        }
    }
    public void getPreviewMsg(Response<ResponseBean<PayPreviewBean>> response){
        List<PayPreviewBean.getOrderItems> item = response.body().data.getOrder();
        previewAdapter.addAll(item);
        for (int i= 0; i<item.size() ;i++){
            ids = ids +item.get(i).getId() + "_";
        }
    }
    public void getPreviewMsgError(Response<ResponseBean<PayPreviewBean>> response){
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        error(error);
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
                        payPreviewHttp.aliPay(uid,ids,"alipay");
                    }else if (wx_check.isChecked()){
                        msgApi = WXAPIFactory.createWXAPI(getBaseContext(), Config.APPID,false);
                        msgApi.registerApp(Config.APPID);
                        payPreviewHttp.wxPay(uid,ids,"wechart");
                    }else {
                        Toast.makeText(getBaseContext(),"请选择支付方式",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void wxPay(Response<ResponseBean<WxPayBean>> response){
        final WxPayBean.getWxPay wxPay = response.body().data.getOrderinfo();
        Runnable payRunnable = new Runnable() {
            //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq();
                request.appId = wxPay.getAppid();
                request.partnerId = wxPay.getPartnerid();
                request.prepayId = wxPay.getPrepayid();
                request.packageValue = wxPay.getPackages();
                request.nonceStr = wxPay.getNoncestr();
                request.timeStamp = wxPay.getTimestamp();
                request.sign = wxPay.getSign();
                msgApi.sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    public void wxPayError(Response<ResponseBean<WxPayBean>> response){
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        error(error);
    }
    public void aliPay(Response<ResponseBean<PayMsgBean>> response){
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
    public void aliPayError(Response<ResponseBean<PayMsgBean>> response){
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        error(error);
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
                        paySuccess();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getBaseContext(), "支付失败", Toast.LENGTH_SHORT).show();
                        payError();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    public void paySuccess(){
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
    }
    public void payError(){
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

    private void initView() {
        preview_back = findViewById(R.id.preview_back);
        preview_recyclerview = findViewById(R.id.preview_recyclerview);
        zfb_check = findViewById(R.id.zfb_check);
        wx_check = findViewById(R.id.wx_check);
        preview_pay = findViewById(R.id.preview_pay);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ZkwPreference.getInstance(getBaseContext()).IsRefresh()){
            paySuccess();
            ZkwPreference.getInstance(getBaseContext()).setIsRefresh(false);
        }
    }
}
