package com.miaomi.fenbei.common.weigdt

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by
 * on 2019-11-21.
 * 炫彩TextView
 */

class ColorfulFontTextView : TextView {

    private var textViewWidth: Float = 0f
    private var isOpenAnim = false
    private var mViewWidth = 0
    private lateinit var mPaint: Paint
    private var mLinearGradient: LinearGradient? = null
    private var mGrandientMatrix: Matrix? = null
    private var mTranslate = 0
    private var mRankId = 0

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

//    fun openColorfulFont(isOpen: Boolean, str: String) {
//
//    }

//    fun setText(text: CharSequence?, isOpenColorful: Boolean) {
//        if (isOpenColorful) {
//            textViewWidth = paint.measureText(text.toString())
//            if (textViewWidth > 0 && isOpenColorful) {
//                setTextColor(Color.parseColor("#999999"))
//                setShadowLayer(12f,0f,0f, Color.WHITE)
//                paint.shader = LinearGradient(0f, 0f, textViewWidth, 0f,
//                        intArrayOf(Color.parseColor("#FF0000"), Color.parseColor("#FF7F00"), Color.parseColor("#FFFF00"),
//                                Color.parseColor("#00FF00"), Color.parseColor("#00FFFF"), Color.parseColor("#0000FF"),
//                                Color.parseColor("#8B00FF")), null, Shader.TileMode.CLAMP)
//            }
//        }
//        super.setText(text)
//    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
//        if (isOpenAnim && mRankId > BaseConfig.NOBLE_LEVEL_GJ){
//            if (mViewWidth == 0) {
//                mViewWidth = measuredWidth
//                if (mViewWidth > 0) {
//                    mPaint = paint
//                    mLinearGradient = LinearGradient(0f,0f, mViewWidth.toFloat(),  measuredHeight.toFloat(), intArrayOf(Color.parseColor("#FFDD7E"), Color.parseColor("#D2671E"),Color.parseColor("#FFDD7E")), floatArrayOf(0.4f,0.2f,1f), Shader.TileMode.CLAMP)
//                    mPaint.shader = mLinearGradient
//                    mGrandientMatrix = Matrix()
//                }
//            }
//        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        if (isOpenAnim && mRankId > BaseConfig.NOBLE_LEVEL_GJ){
//            if (mGrandientMatrix != null) {
//                mTranslate += mViewWidth / 5
//                if (mTranslate > 2 * mViewWidth) {
//                    mTranslate = -mViewWidth
//                }
//                mGrandientMatrix!!.setTranslate(mTranslate.toFloat(), 0f)
//                mLinearGradient!!.setLocalMatrix(mGrandientMatrix)
//                postInvalidateDelayed(100)
//            }
//        }
    }


    fun setNobleText(text: CharSequence?, rankId: Int) {
//        isOpenAnim = false
//        mRankId = rankId
//        when {
//            rankId > BaseConfig.NOBLE_LEVEL_GJ -> {
//                isOpenAnim = true
//                setTextColor(Color.parseColor("#FF999999"))
//                postInvalidate()
//            }
//            rankId >= BaseConfig.NOBLE_LEVEL_HJ -> {
//                setTextColor(Color.parseColor("#FFD2671E"))
//            }
//            else -> {
//                setTextColor(Color.parseColor("#80FFFFFF"))
//            }
//        }

        super.setText(text)
    }
}