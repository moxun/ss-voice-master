package com.miaomi.fenbei.base.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.miaomi.fenbei.base.R
import com.miaomi.fenbei.base.util.DensityUtil
import com.noober.background.drawable.DrawableCreator

/**
 * Created by 
 * on 2019-10-30.
 */
class CommonTitleText : FrameLayout {

    private var mText = "title"
    private var mTextColor = 0xff222222.toInt()
    private var mTextSize = 18f

    private lateinit var title : TextView
    private lateinit var view : View

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleText, defStyleAttr, 0)
        mText = typedArray.getString(R.styleable.CommonTitleText_common_setText)
        mTextColor = typedArray.getColor(R.styleable.CommonTitleText_common_setTextColor, mTextColor)
        mTextSize = typedArray.getDimension(R.styleable.CommonTitleText_common_setTextSize, mTextSize)
        title = TextView(context)
        title.layoutParams = LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER)
        title.text = mText
        title.setTextColor(mTextColor)
        title.textSize = DensityUtil.px2sp(context, mTextSize)
        title.typeface = Typeface.DEFAULT_BOLD
        view = View(context)
        view.layoutParams = LayoutParams(title.paint.measureText(mText).toInt(), DensityUtil.dp2px(context, 8f), Gravity.BOTTOM)
        view.background = DrawableCreator.Builder().setCornersRadius(DensityUtil.dp2px(context, 16f).toFloat())
                .setGradientColor(Color.parseColor("#72F8DE"), Color.parseColor("#51DBFC")).setGradientAngle(-180).build()
        addView(view)
        addView(title)
        typedArray.recycle()
    }

    fun setText(title: String) {
        this.title.text = title
        view.layoutParams = LayoutParams(this.title.paint.measureText(title).toInt(), DensityUtil.dp2px(context, 8f), Gravity.BOTTOM)
    }
}