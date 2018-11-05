package com.zjwam.zkw.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class AppHelper {
    /**
     * 返回app运行状态
     * 1:前台 2:后台 0:不存在
     */
    public static int isAppAlive(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> listInfos = activityManager.getRunningTasks(20);
        // 判断程序是否在栈顶
        if (listInfos.get(0).topActivity.getPackageName().equals(packageName)) {
            return 1;
        } else {            // 判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : listInfos) {
                if (info.topActivity.getPackageName().equals(packageName)) {
                    return 2;
                }
            }
            return 0;// 栈里找不到，返回0
        }
    }
}
