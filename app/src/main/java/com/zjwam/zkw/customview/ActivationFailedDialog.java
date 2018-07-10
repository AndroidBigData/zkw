package com.zjwam.zkw.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zjwam.zkw.R;

public class ActivationFailedDialog extends Dialog {
    private Context context;
    private String title, msg;
    private TextView failed_msg, failed_goon, failed_qx, failed_title;
    private ClickListenerInterface clickListenerInterface;

    public ActivationFailedDialog(@NonNull Context context, String title, String msg) {
        super(context,R.style.dialog);
        this.context = context;
        this.title = title;
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.adtivation_failed_dialog, null);
        setContentView(view);
        failed_msg = view.findViewById(R.id.failed_msg);
        failed_goon = view.findViewById(R.id.failed_goon);
        failed_qx = view.findViewById(R.id.failed_qx);
        failed_title = view.findViewById(R.id.failed_title);
        failed_msg.setText(msg);
        failed_title.setText(title);
        failed_goon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenerInterface.doConfirm(view);
            }
        });
        failed_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenerInterface.doCancel(view);
            }
        });
    }

    public void setClickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public interface ClickListenerInterface {
        void doConfirm(View view);
        void doCancel(View view);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        layoutParams.width = (int) (d.widthPixels * 0.8); // 宽度度设置为屏幕的0.8
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }
}
