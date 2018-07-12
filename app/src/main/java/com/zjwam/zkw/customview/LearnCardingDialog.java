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

public class LearnCardingDialog extends Dialog {
    private Context context;
    private TextView learncarding_num,learncarding_world,learncarding_tel,learncarding_time,learncarding_close;
    private String num,world,tel,time;
    private ClickListenerInterface clickListenerInterface;
    public LearnCardingDialog(@NonNull Context context, String num, String world, String tel, String time) {
        super(context, R.style.dialog);
        this.context = context;
        this.num = num;
        this.world = world;
        this.tel = tel;
        this.time = time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.learncarding_dialog,null);
        setContentView(view);
        learncarding_num = view.findViewById(R.id.learncarding_num);
        learncarding_world = view.findViewById(R.id.learncarding_world);
        learncarding_tel = view.findViewById(R.id.learncarding_tel);
        learncarding_time = view.findViewById(R.id.learncarding_time);
        learncarding_close = view.findViewById(R.id.learncarding_close);
        learncarding_num.setText(num);
        learncarding_world.setText(world);
        learncarding_tel.setText(tel);
        learncarding_time.setText(time);
        learncarding_close.setOnClickListener(new View.OnClickListener() {
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
