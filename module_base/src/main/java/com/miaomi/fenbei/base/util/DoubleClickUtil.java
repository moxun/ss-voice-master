package com.miaomi.fenbei.base.util;

/**
 * Created by
 * on 2020/5/8.
 */
public class DoubleClickUtil {

    private static long lastClickTime = System.currentTimeMillis();

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        return timeD >= 1000;
    }

}
