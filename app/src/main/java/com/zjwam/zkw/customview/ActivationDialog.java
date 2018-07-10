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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjwam.zkw.R;

public class ActivationDialog extends Dialog {
    private Context context;
    private ImageView dialog_close;
    private TextView make_go;
    private EditText learncard_num, learncard_world;
    private ClickListenerInterface clickListenerInterface;

    public ActivationDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.activation_dialog, null);
        setContentView(view);
        dialog_close = view.findViewById(R.id.dialog_close);
        make_go = view.findViewById(R.id.make_go);
        learncard_num = view.findViewById(R.id.learncard_num);
        learncard_world = view.findViewById(R.id.learncard_world);

        dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenerInterface.doConfirm(view);
            }
        });
        make_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = learncard_num.getText().toString().trim();
                String world = learncard_world.getText().toString().trim();
                clickListenerInterface.makeGo(num, world);
            }
        });

    }

    public void setClickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public interface ClickListenerInterface {
        public void doConfirm(View view);

        public void makeGo(String num, String world);
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
