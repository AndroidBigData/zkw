package com.zjwam.zkw.util;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zjwam.zkw.R;
import com.zjwam.zkw.personalcenter.AddJobEducationalActivity;

import java.util.Date;
import java.util.List;

public class BasicPickerView {
    private Selctor selctor;
    private TimePicker timePicker;
    public <T>void pickerVIew(Context context, String title, final List<T> item1, List<List<T>> item2, List<List<List<T>>> item3){
        OptionsPickerView pvOptions =  new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selctor.Options(options1,options2,options3,v);
            }
        })
                .setTitleText(title)
                .setContentTextSize(18)//设置滚轮文字大小
                .setTitleBgColor(context.getResources().getColor(R.color.white))
                .setCancelColor(context.getResources().getColor(R.color.text_color_gray))
                .setSubmitColor(context.getResources().getColor(R.color.colorAccent))
                .setTextColorCenter(context.getResources().getColor(R.color.black))
                .setDividerColor(context.getResources().getColor(R.color.colorAccent))
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x30000000) //设置外部遮罩颜色
                .build();
        pvOptions.setPicker(item1,item2,item3);
        pvOptions.show();
    }

    public void timePicker(Context context){
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                timePicker.Picker(DateUtil.getDateToString(date,"yyyy-MM-dd"));
            }
        }).build();
        pvTime.show();
    }
    public void getSelctor(Selctor selctor) {
        this.selctor = selctor;
    }
    public void setTimePicker(TimePicker timePicker){
        this.timePicker = timePicker;
    }
    public interface Selctor{
        void Options(int options1, int options2, int options3,View view);
    }
    public interface TimePicker{
        void Picker(String date);
    }
}
