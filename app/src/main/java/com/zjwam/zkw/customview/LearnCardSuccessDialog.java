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

public class LearnCardSuccessDialog extends Dialog {
    private Context context;
    private TextView learncard_success,learncard_look,learncard_see,learncard_title;
    private String msg,moreMsg,title;
    private ClickListenerInterface clickListenerInterface;

    public LearnCardSuccessDialog(@NonNull Context context,String title,String msg,String moreMsg) {
        super(context,R.style.dialog);
        this.context = context;
        this.msg = msg;
        this.moreMsg = moreMsg;
        this.title = title;
    }

    public void setClickListener(ClickListenerInterface clickListenerInterface){
        this.clickListenerInterface = clickListenerInterface;
    }

    public interface ClickListenerInterface {
        public void doConfirm(View view);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.learncardsueecss_dialog,null);
        setContentView(view);
        learncard_success = view.findViewById(R.id.learncard_success);
        learncard_look = view.findViewById(R.id.learncard_look);
        learncard_see = view.findViewById(R.id.learncard_see);
        learncard_title = view.findViewById(R.id.failed_title);
        learncard_success.setText(msg);
        learncard_title.setText(title);
        if (moreMsg != null){
            learncard_look.setText(moreMsg);
        }else {
            learncard_look.setVisibility(View.GONE);
        }
        learncard_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenerInterface.doConfirm(view);
            }
        });
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
