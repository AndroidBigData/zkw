package com.zjwam.zkw.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.zjwam.zkw.R;

public class CustomToast {
    private Context context;
    private ImageView toast_img;
    private TextView toast_msg;
    public CustomToast(Context context) {
        this.context=context;
    }
    public void setsToast(String title, int imgPosition){
        LayoutInflater inflater = LayoutInflater.from(context);
        View toastView = inflater.inflate(R.layout.custom_toast_layout, null);
        toast_img = toastView.findViewById(R.id.toast_img);
        toast_img.setImageResource(imgPosition);
        toast_msg = toastView.findViewById(R.id.toast_msg);
        toast_msg.setText(title);
        Toast toast = new Toast(context);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
