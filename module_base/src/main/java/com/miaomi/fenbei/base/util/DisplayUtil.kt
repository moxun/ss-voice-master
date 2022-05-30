package com.miaomi.fenbei.base.util

import android.util.DisplayMetrics
import android.view.WindowManager
import android.app.Activity
import android.view.Display
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.TypedValue


@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
object DisplayUtil {
    /**
     * Get Screen Width
     */
    fun getScreenWidth(context: Context): Int {
        return getDisplayMetrics(context).widthPixels
    }

    /**
     * Get Screen Height
     */
    fun getScreenHeight(context: Context): Int {
        return getDisplayMetrics(context).heightPixels
    }


    /**
     * Get Screen Real Height
     *
     * @param context Context
     * @return Real Height
     */
    fun getRealHeight(context: Context): Int {
        val display = getDisplay(context) ?: return 0
        val dm = DisplayMetrics()
        display.getRealMetrics(dm)
        return dm.heightPixels
    }

    /**
     * Get Screen Real Width
     *
     * @param context Context
     * @return Real Width
     */
    fun getRealWidth(context: Context): Int {
        val display = getDisplay(context) ?: return 0
        val dm = DisplayMetrics()
        display.getRealMetrics(dm)
        return dm.widthPixels
    }

    /**
     * Get StatusBar Height
     */
    fun getStatusBarHeight(mContext: Context): Int {
        val resourceId = mContext.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            mContext.resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    /**
     * Get ActionBar Height
     */
    fun getActionBarHeight(mContext: Context): Int {
        val tv = TypedValue()
        return if (mContext.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            TypedValue.complexToDimensionPixelSize(tv.data, mContext.resources.displayMetrics)
        } else 0
    }

    /**
     * Get NavigationBar Height
     */
    fun getNavigationBarHeight(mContext: Context): Int {
        val resources = mContext.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    /**
     * Get Density
     */
    private fun getDensity(context: Context): Float {
        return getDisplayMetrics(context).density
    }

    /**
     * Get Dpi
     */
    private fun getDpi(context: Context): Int {
        return getDisplayMetrics(context).densityDpi
    }

    /**
     * Get Display
     *
     * @param context Context for get WindowManager
     * @return Display
     */
    private fun getDisplay(context: Context): Display? {
        val wm: WindowManager?
        wm = if (context is Activity) {
            val activity = context as Activity
            activity.windowManager
        } else {
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
        return wm?.defaultDisplay
    }

    /**
     * Get DisplayMetrics
     *
     * @param context Context for get Resources
     * @return DisplayMetrics
     */
    private fun getDisplayMetrics(context: Context): DisplayMetrics {
        return context.resources.displayMetrics
    }


    /**
     * Get ScreenInfo
     */
    private fun getScreenInfo(context: Context): String {
        return " \n" +
                "--------ScreenInfo--------" + "\n" +
                "Screen Width : " + getScreenWidth(context) + "px\n" +
                "Screen RealWidth :" + getRealWidth(context) + "px\n" +
                "Screen Height: " + getScreenHeight(context) + "px\n" +
                "Screen RealHeight: " + getRealHeight(context) + "px\n" +
                "Screen StatusBar Height: " + getStatusBarHeight(context) + "px\n" +
                "Screen ActionBar Height: " + getActionBarHeight(context) + "px\n" +
                "Screen NavigationBar Height: " + getNavigationBarHeight(context) + "px\n" +
                "Screen Dpi: " + getDpi(context) + "\n" +
                "Screen Density: " + getDensity(context) + "\n" +
                "--------------------------"
    }
}