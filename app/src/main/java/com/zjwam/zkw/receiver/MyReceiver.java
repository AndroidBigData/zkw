package com.zjwam.zkw.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.zjwam.zkw.news.NewsActivity;
import com.zjwam.zkw.news.NewsMoreActivity;
import com.zjwam.zkw.util.AppHelper;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;
import com.zjwam.zkw.view.LogoutNotifiActivity;
import com.zjwam.zkw.view.MainActivity;
import com.zjwam.zkw.webview.NewsWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.e(TAG, "JPush用户注册成功id: " + regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            if ("wrong".equals(bundle.getString(JPushInterface.EXTRA_MESSAGE))) {
                Intent i = new Intent(context, LogoutNotifiActivity.class);  //单点登录
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.e(TAG, "接受到推送下来的通知id: " + notifactionId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            String data = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (AppHelper.isAppAlive(context,context.getPackageName()) == 0){
                //不存在
                Intent launchIntent = context.getPackageManager().
                        getLaunchIntentForPackage(context.getPackageName());
                launchIntent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Bundle args = new Bundle();
                args.putString("info", data);
                launchIntent.putExtra("jpush", args);
                context.startActivity(launchIntent);
            }else {
                //前台or后台
                try {
                    JSONObject object = new JSONObject(data);
                    if (object.toString().contains("url")) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("url", object.getString("url"));
                        Intent i = new Intent(context, NewsWebActivity.class).putExtras(bundle1);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    } else if (object.toString().contains("id")) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("id", object.getString("id"));
                        Intent i = new Intent(context, Video2PlayActivity.class).putExtras(bundle1);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {

            Log.e(TAG, "onReceive: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);

            Log.e(TAG, "onReceive: " + intent.getAction() + " 连接状态变化 " + connected);
        } else {
            Log.e(TAG, "onReceive:  未处理的意图- " + intent.getAction());
        }
    }
}
