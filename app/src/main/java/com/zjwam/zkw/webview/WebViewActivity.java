package com.zjwam.zkw.webview;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.httputils.WebViewHttp;
import com.zjwam.zkw.MainActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.customview.LearnCardSuccessDialog;
import com.zjwam.zkw.entity.PayMsgBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.WxPayBean;
import com.zjwam.zkw.fragment.login.LoginFragment;
import com.zjwam.zkw.pay.PayResult;
import com.zjwam.zkw.personalcenter.MineLearnCardActivity;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.Map;

public class WebViewActivity extends BaseActivity {

    private String url = "", title = "", uid = "", type = "";
    private ImageView webview_back;
    private TextView webview_title;
    private WebView webview;
    private ProgressBar bar;
    private static final int SDK_PAY_FLAG = 1;
    private boolean isFlag = false;
    private IWXAPI msgApi;
    private WebViewHttp webViewHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        init();
    }

    private void init() {
        webview_back = findViewById(R.id.webview_back);
        webview_title = findViewById(R.id.webview_title);
        webview = findViewById(R.id.webview);
        bar = findViewById(R.id.progress_web);

        if (title.length() > 0) {
            webview_title.setText(title);
        }

        webview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == null || type.length() == 0) {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                } else {
                    finish();
                }
            }
        });
        webViewHttp = new WebViewHttp(this);

        WebSettings webSettings = webview.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //加载需要显示的网页

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.GONE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        //点击WebView中的文字链接时，继续在本WebView中执行，而不是跳到浏览器执行
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("jihuo")) {
                    if (isFlag) {
                        startActivity(new Intent(getBaseContext(), MineLearnCardActivity.class));
                    } else {
                        startActivity(new Intent(getBaseContext(), LoginFragment.class));
                    }

                } else if (url.contains("wechart")) {
                    if (isFlag) {
//                        Toast.makeText(getBaseContext(), "微信支付", Toast.LENGTH_SHORT).show();
                        msgApi = WXAPIFactory.createWXAPI(getBaseContext(), Config.APPID, false);
                        msgApi.registerApp(Config.APPID);
                        webViewHttp.getWxPay(uid, "wechart");
                    } else {
                        startActivity(new Intent(getBaseContext(), LoginFragment.class));
                    }

                } else if (url.contains("alipay")) {
                    if (isFlag) {
                        webViewHttp.getAliPay(uid, "alipay");
                    } else {
                        startActivity(new Intent(getBaseContext(), LoginFragment.class));
                    }
                } else {
                    webview.loadUrl(url);
                }
                return true;
            }
        });
    }

    public void getWxPay(Response<ResponseBean<WxPayBean>> response) {

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

    public void getWxPayError(Response<ResponseBean<WxPayBean>> response) {
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
    }

    public void getAliPay(Response<ResponseBean<PayMsgBean>> response) {

        final ResponseBean<PayMsgBean> orderInfo = response.body();
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(WebViewActivity.this);
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
    public void getAliPayError(Response<ResponseBean<PayMsgBean>> response){
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
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
                        final LearnCardSuccessDialog learnCardSuccessDialog = new LearnCardSuccessDialog(WebViewActivity.this, "购买成功", "  恭喜！购买成功！", "请在“个人中心-学习卡”页面确认激活");
                        learnCardSuccessDialog.show();
                        learnCardSuccessDialog.setClickListener(new LearnCardSuccessDialog.ClickListenerInterface() {
                            @Override
                            public void doConfirm(View view) {
                                learnCardSuccessDialog.dismiss();
                            }
                        });
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getBaseContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        } else {
            if (type == null || type.length() == 0) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        isFlag = ZkwPreference.getInstance(getBaseContext()).IsFlag();
        webview.loadUrl(url);
        if (ZkwPreference.getInstance(getBaseContext()).IsRefresh()) {
            final LearnCardSuccessDialog learnCardSuccessDialog = new LearnCardSuccessDialog(WebViewActivity.this, "购买成功", "  恭喜！购买成功！", "请在“个人中心-学习卡”页面确认激活");
            learnCardSuccessDialog.show();
            learnCardSuccessDialog.setClickListener(new LearnCardSuccessDialog.ClickListenerInterface() {
                @Override
                public void doConfirm(View view) {
                    learnCardSuccessDialog.dismiss();
                }
            });
            ZkwPreference.getInstance(getBaseContext()).setIsRefresh(false);
        }
    }
}
