package com.zjwam.zkw.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.fragment.login.LoginFragment;
import com.zjwam.zkw.util.ZkwPreference;

import cn.jpush.android.api.JPushInterface;

public class LogoutNotifiActivity extends Activity {

    private TextView dialog_qx,dialog_qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_notifi);
        initView();
        initData();
    }

    private void initData() {
        JPushInterface.setAlias(getBaseContext(), 1, "");
        JPushInterface.deleteAlias(getBaseContext(), 1);
        ZkwPreference.getInstance(getBaseContext()).SetIsFlag(false);
        ZkwPreference.getInstance(getBaseContext()).SetLoginName("");
        ZkwPreference.getInstance(getBaseContext()).SetPassword("");
        ZkwPreference.getInstance(getBaseContext()).SetUid("");
        ZkwPreference.getInstance(getBaseContext()).SetRegisterType("");
        ZkwPreference.getInstance(getBaseContext()).setCity("");
        ZkwPreference.getInstance(getBaseContext()).setVideoId("");
        ZkwPreference.getInstance(getBaseContext()).setViteoTime("");
        dialog_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("exitapp");
                sendBroadcast(intent);
                finish();
            }
        });
        dialog_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("exitapp");
                sendBroadcast(intent);
                Bundle bundle = new Bundle();
                bundle.putString("logout","logout");
                startActivity(new Intent(getBaseContext(),MainActivity.class).putExtras(bundle));
                finish();
            }
        });
    }

    private void initView() {
        dialog_qx = findViewById(R.id.dialog_qx);
        dialog_qr = findViewById(R.id.dialog_qr);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
