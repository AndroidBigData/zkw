package com.zjwam.zkw.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zjwam.zkw.view.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.ZkwPreference;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wxpay_layout);
        api = WXAPIFactory.createWXAPI(this, Config.APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
//                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                ZkwPreference.getInstance(getBaseContext()).setIsRefresh(true);//仅仅作为标识，代表支付成功；
            } else {
                Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }
}
