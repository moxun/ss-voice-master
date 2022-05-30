package com.miaomi.fenbei.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.miaomi.fenbei.base.core.CommonLib;

public class ScreenUtil {

    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
    public static int getNavigationBarHeight(Context context) {
        if (!isShowNavBar((Activity) context)){
            return 0;
        }
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    /**
     * 判断是否显示了导航栏
     * (说明这里的context 一定要是activity的context 否则类型转换失败)
     *
     * @param context
     * @return
     */
    public static boolean isShowNavBar(Context context) {
        if (null == context) {
            return false;
        }
        /**
         * 获取应用区域高度
         */
        Rect outRect1 = new Rect();
        try {
            ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return false;
        }
        int activityHeight = outRect1.height();
        /**
         * 获取状态栏高度
         */
        int statuBarHeight = getStatusBarHeight(CommonLib.mContext);
        /**
         * 屏幕物理高度 减去 状态栏高度
         */
        int remainHeight = getRealHeight() - statuBarHeight;
        /**
         * 剩余高度跟应用区域高度相等 说明导航栏没有显示 否则相反
         */
        if (activityHeight == remainHeight) {
            return false;
        } else {
            return true;
        }



    }

    /**
     * 活动屏幕信息
     */
    private static WindowManager wm;
    /**
     * 获取真实屏幕高度
     *
     * @return
     */
    public static int getRealHeight() {
        if (null == wm) {
            wm = (WindowManager)
                    CommonLib.mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) CommonLib.mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            screenWidth = outMetrics.widthPixels;
        }
        return screenWidth;
    }


    /**
     * 获得屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) CommonLib.mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            screenHeight = outMetrics.heightPixels;
        }
        return screenHeight;
    }


}
