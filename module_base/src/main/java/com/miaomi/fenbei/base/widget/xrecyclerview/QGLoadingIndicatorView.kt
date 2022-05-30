//package com.miaomi.fenbei.base.view.xrecyclerview
//
//import android.annotation.TargetApi
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.os.Build
//import android.util.AttributeSet
//import android.view.View
//
//import com.miaomi.fenbei.base.R
//
//
//class QGLoadingIndicatorView : View {
//
//    //attrs
//    internal var mIndicatorColor: Int = 0
//
//    internal var mPaint: Paint? = null
//
//    internal var mIndicatorController: BallSpinFadeLoaderIndicator? = null
//
//    private var mHasAnimation: Boolean = false
//
//    constructor(context: Context) : super(context) {
//        init(null, 0)
//    }
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        init(attrs, 0)
//    }
//
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        init(attrs, defStyleAttr)
//    }
//
//    fun destroy() {
//        mHasAnimation = true
//        if (mIndicatorController != null) {
//            mIndicatorController!!.destroy()
//            mIndicatorController = null
//        }
//        mPaint = null
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
//        init(attrs, defStyleAttr)
//    }
//
//    private fun init(attrs: AttributeSet?, defStyle: Int) {
//        val a = context.obtainStyledAttributes(attrs, R.styleable.QGLoadingIndicatorView)
//        mIndicatorColor = a.getColor(R.styleable.QGLoadingIndicatorView_indicator_color, Color.RED)
//        a.recycle()
//        mPaint = Paint()
//        mPaint!!.color = Color.RED
//        mPaint!!.style = Paint.Style.FILL
//        mPaint!!.isAntiAlias = true
//        applyIndicator()
//    }
//
//    fun setIndicatorColor(color: Int) {
//        mIndicatorColor = color
//        mPaint!!.color = mIndicatorColor
//        this.invalidate()
//    }
//
//    private fun applyIndicator() {
//        mIndicatorController = BallSpinFadeLoaderIndicator()
//        mIndicatorController!!.target = this
//    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val width = measureDimension(dp2px(DEFAULT_SIZE), widthMeasureSpec)
//        val height = measureDimension(dp2px(DEFAULT_SIZE), heightMeasureSpec)
//        setMeasuredDimension(width, height)
//    }
//
//    private fun measureDimension(defaultSize: Int, measureSpec: Int): Int {
//        var result = defaultSize
//        val specMode = View.MeasureSpec.getMode(measureSpec)
//        val specSize = View.MeasureSpec.getSize(measureSpec)
//        if (specMode == View.MeasureSpec.EXACTLY) {
//            result = specSize
//        } else if (specMode == View.MeasureSpec.AT_MOST) {
//            result = Math.min(defaultSize, specSize)
//        } else {
//            result = defaultSize
//        }
//        return result
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        drawIndicator(canvas)
//    }
//
//    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        super.onLayout(changed, left, top, right, bottom)
//        if (!mHasAnimation) {
//            mHasAnimation = true
//            applyAnimation()
//        }
//    }
//
//    override fun setVisibility(v: Int) {
//        if (visibility != v) {
//            super.setVisibility(v)
//            if (mIndicatorController == null)
//                return
//            if (v == View.GONE || v == View.INVISIBLE) {
//                mIndicatorController!!.setAnimationStatus(BaseIndicatorController.AnimStatus.END)
//            } else {
//                mIndicatorController!!.setAnimationStatus(BaseIndicatorController.AnimStatus.START)
//            }
//        }
//    }
//
//    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        if (mIndicatorController == null)
//            return
//        mIndicatorController!!.setAnimationStatus(BaseIndicatorController.AnimStatus.CANCEL)
//    }
//
//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//        if (mIndicatorController == null)
//            return
//        mIndicatorController!!.setAnimationStatus(BaseIndicatorController.AnimStatus.START)
//    }
//
//    internal fun drawIndicator(canvas: Canvas) {
//        if (mIndicatorController == null)
//            return
//        mIndicatorController!!.draw(canvas, mPaint!!)
//    }
//
//    internal fun applyAnimation() {
//        if (mIndicatorController == null)
//            return
//        mIndicatorController!!.initAnimation()
//    }
//
//    private fun dp2px(dpValue: Int): Int {
//        return context.resources.displayMetrics.density.toInt() * dpValue
//    }
//
//    companion object {
//
//
//        //Sizes (with defaults in DP)
//        val DEFAULT_SIZE = 30
//    }
//
//
//}
