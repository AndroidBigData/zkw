package com.zjwam.zkw.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class BadNetWork {
    public void isBadNetWork(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isAvailable()) {
            Toast.makeText(context,"请保持网络畅通!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"地址错误!", Toast.LENGTH_SHORT).show();
        }
    }
}
