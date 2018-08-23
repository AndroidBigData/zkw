package com.zjwam.zkw.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zjwam.zkw.R;

public class BasicDialog extends Dialog {

    private Context context;
    private String content;
    private TextView basic_dialog_content,basic_dialog_qx,basic_dialog_qr;
    private BasicDialogListener basicDialogListener;

    public BasicDialog(@NonNull Context context,String content) {
        super(context, R.style.dialog);
        this.context = context;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.basic_dialog,null,false);
        setContentView(view);
        basic_dialog_content = view.findViewById(R.id.basic_dialog_content);
        basic_dialog_qx = view.findViewById(R.id.basic_dialog_qx);
        basic_dialog_qr = view.findViewById(R.id.basic_dialog_qr);
        basic_dialog_content.setText(content);
        basic_dialog_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basicDialogListener.cancel();
            }
        });
        basic_dialog_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basicDialogListener.confirm();
            }
        });
    }
    public void setDialog(BasicDialogListener basicDialogListener){
        this.basicDialogListener = basicDialogListener;
    }
    public interface BasicDialogListener{
        void confirm();
        void cancel();
    }
}
