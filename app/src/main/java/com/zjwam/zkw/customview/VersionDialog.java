package com.zjwam.zkw.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zjwam.zkw.R;

public class VersionDialog extends Dialog {
    private Context context;
    private TextView version_msg,version_size,version_qx,version_qd;
    private DialogListener dialogListener;
    private String versionMsg,versionSize;
    public VersionDialog(@NonNull Context context,String versionMsg,String versionSize) {
        super(context, R.style.dialog);
        this.context = context;
        this.versionMsg = versionMsg;
        this.versionSize = versionSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.version_dialog,null,false);
        setContentView(view);
        version_msg = view.findViewById(R.id.version_msg);
        version_size = view.findViewById(R.id.version_size);
        version_qx = view.findViewById(R.id.version_qx);
        version_qd = view.findViewById(R.id.version_qd);
        version_msg.setText(versionMsg);
        version_size.setText("更新包大小：" + versionSize);
        version_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListener.cancel();
            }
        });
        version_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListener.confirm();
            }
        });
    }

    public void setDialogListener(DialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    public interface DialogListener{
        void confirm();
        void cancel();
    }
}
