package com.miaomi.fenbei.base.util

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager

object DensityUtil {

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    fun dp2px(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.resources.displayMetrics).toInt()
    }

    /**
     * sp转px
     *
     * @param context
     * @param sp
     * @return
     */
    fun sp2px(context: Context, sp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, context.resources.displayMetrics).toInt()
    }

    /**
     * px转dp
     *
     * @param context
     * @param px
     * @return
     */
    fun px2dp(context: Context, px: Float): Float {
        val scale = context.resources.displayMetrics.density
        return px / scale
    }

    /**
     * px转sp
     *
     * @param context
     * @param px
     * @return
     */
    fun px2sp(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.scaledDensity
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    fun hideSoftKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}