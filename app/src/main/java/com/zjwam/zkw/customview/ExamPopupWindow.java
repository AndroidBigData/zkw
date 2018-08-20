package com.zjwam.zkw.customview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zjwam.zkw.R;

public class ExamPopupWindow extends PopupWindow {

    private Context context;
    private LinearLayout pop_xcg, pop_zgz, pop_cjw, pop_yjs;
    private View view_exam1, view_exam2;
    private ItemOnclick itemOnclick;

    public ExamPopupWindow(Context context) {
        this.context = context;
        initPop();
    }

    private void initPop() {
        View popView = View.inflate(context, R.layout.exam_pop, null);
        //设置view
        this.setContentView(popView);
        //设置宽高（也可设置为LinearLayout.LayoutParams.MATCH_PARENT或者LinearLayout.LayoutParams.MATCH_PARENT）
        this.setWidth(-1);
        this.setHeight(-2);
        //设置PopupWindow的焦点
        this.setFocusable(true);
        //设置窗口以外的地方点击可关闭pop
        this.setOutsideTouchable(true);
        //设置背景透明
        this.setBackgroundDrawable(new ColorDrawable(0x33000000));
        initView(popView);
        initData();
    }

    private void initData() {
        pop_xcg.setOnClickListener(onClickListener);
        pop_zgz.setOnClickListener(onClickListener);
        pop_cjw.setOnClickListener(onClickListener);
        pop_yjs.setOnClickListener(onClickListener);
        view_exam1.setOnClickListener(onClickListener);
        view_exam2.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.pop_xcg:
                    itemOnclick.onClick(2);
                    dismiss();
                    break;
                case R.id.pop_zgz:
                    itemOnclick.onClick(1);
                    dismiss();
                    break;
                case R.id.pop_cjw:
                    itemOnclick.onClick(3);
                    dismiss();
                    break;
                case R.id.pop_yjs:
                    itemOnclick.onClick(4);
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        }
    };

    private void initView(View popView) {
        pop_xcg = popView.findViewById(R.id.pop_xcg);
        pop_zgz = popView.findViewById(R.id.pop_zgz);
        pop_cjw = popView.findViewById(R.id.pop_cjw);
        pop_yjs = popView.findViewById(R.id.pop_yjs);
        view_exam1 = popView.findViewById(R.id.view_exam1);
        view_exam2 = popView.findViewById(R.id.view_exam2);
    }

    public void setItemOnclick(ItemOnclick itemOnclick) {
        this.itemOnclick = itemOnclick;
    }

    public interface ItemOnclick {
        void onClick(int type);
    }
}
