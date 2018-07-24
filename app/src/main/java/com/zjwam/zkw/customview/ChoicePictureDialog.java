package com.zjwam.zkw.customview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zjwam.zkw.R;

public class ChoicePictureDialog extends Dialog {
    private Context context;
    private TextView tv_popup_photograph,tv_popup_album,tv_popup_cancel;
    private View view_cancel;
    private PopOnClick popOnClick;
    public ChoicePictureDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.choice_picture_dialog,null,false);
        setContentView(view);
        tv_popup_photograph = view.findViewById(R.id.tv_popup_photograph);
        tv_popup_album = view.findViewById(R.id.tv_popup_album);
        tv_popup_cancel = view.findViewById(R.id.tv_popup_cancel);
        view_cancel = view.findViewById(R.id.view_cancel);

        tv_popup_photograph.setOnClickListener(onClickListener);
        tv_popup_album.setOnClickListener(onClickListener);
        tv_popup_cancel.setOnClickListener(onClickListener);
        view_cancel.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_popup_photograph:
                    popOnClick.OnClickPhotograph();
                    break;
                case R.id.tv_popup_album:
                    popOnClick.OnClickalbum();
                    break;
                case R.id.tv_popup_cancel:
                    popOnClick.OnClickcancel();
                    break;
                case R.id.view_cancel:
                    popOnClick.OnClickcancel();
                    break;
            }
        }
    };

    public void setOnClickListener(PopOnClick popOnClick){
        this.popOnClick = popOnClick;
    }

    public interface PopOnClick{
        void OnClickPhotograph();
        void OnClickalbum();
        void OnClickcancel();
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
