package com.zjwam.zkw.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class CurriculumGridView extends GridView {
    public CurriculumGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurriculumGridView(Context context) {
        super(context);
    }

    public CurriculumGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
