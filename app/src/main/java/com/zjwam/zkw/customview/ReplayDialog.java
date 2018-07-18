package com.zjwam.zkw.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zjwam.zkw.R;


/**
 * 回复对话框
 */

public class ReplayDialog extends Dialog {
    private EditText etContent;
    private LinearLayout llBtnReply;
    private Context mContext;

    public ReplayDialog(Context context) {
        super(context, R.style.MyNoFrame_Dialog);
        mContext = context;
        init();
    }

    private ReplayDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_replyform);
        // 设置宽度
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        etContent = findViewById(R.id.dialog_reply_etContent);
        llBtnReply = findViewById(R.id.dialog_reply_llBtnReply);
        // 弹出键盘
        etContent.setFocusable(true);
        etContent.setFocusableInTouchMode(true);
        etContent.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mContext
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etContent, 0);
            }
        }, 200);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ReplayDialog setContent(String content) {
        etContent.setText(content);
        return this;
    }

    public ReplayDialog setHintText(String hint) {
        etContent.setHint(hint);
        return this;
    }

    public String getContent() {
        return etContent.getText().toString();
    }

    public ReplayDialog setOnBtnCommitClickListener(
            android.view.View.OnClickListener onClickListener) {
        llBtnReply.setOnClickListener(onClickListener);
        return this;
    }
}
