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

public class LearnCardUnuseDialog extends Dialog {
    private Context context;
    private TextView learncardunuse_num,learncardunuse_world,learncardunuse_go;
    private String num,world,title;
    private ClickListenerInterface clickListenerInterface;
    public LearnCardUnuseDialog(@NonNull Context context,String num,String world,String title) {
        super(context, R.style.dialog);
        this.context = context;
        this.num = num;
        this.world = world;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.learncardunuse_dialog,null);
        setContentView(view);
        learncardunuse_num = view.findViewById(R.id.learncardunuse_num);
        learncardunuse_world = view.findViewById(R.id.learncardunuse_world);
        learncardunuse_go = view.findViewById(R.id.learncardunuse_go);
        learncardunuse_num.setText(num);
        learncardunuse_world.setText(world);
        learncardunuse_go.setText(title);
        learncardunuse_go.setOnClickListener(new View.OnClickListener() {
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
