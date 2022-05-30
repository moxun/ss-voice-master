package com.miaomi.fenbei.room.widget

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet


/**
 * Created by 
 * on 2019-07-04.
 */
class MarqueeTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun isFocused(): Boolean {//必须重写，且返回值是true，表示始终获取焦点
        return true
    }
}