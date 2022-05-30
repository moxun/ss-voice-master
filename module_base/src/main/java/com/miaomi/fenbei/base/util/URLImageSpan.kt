package com.miaomi.fenbei.base.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.drawable.Drawable
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan

/**
 * Created by
 * on 2019-06-18.
 */
class URLImageSpan : ImageSpan {

    //自定义对齐方式--与文字中间线对齐

    companion object {
        val ALIGN_FONTCENTER = 2
    }

    constructor(drawable: Drawable?) : super(drawable, ALIGN_FONTCENTER)

    constructor(context: Context?, resourceId: Int) : super(context, resourceId)


    constructor(context: Context?, resourceId: Int, verticalAlignment: Int) : super(context, resourceId, verticalAlignment)


    constructor(context: Context?, resourceId: Bitmap?, verticalAlignment: Int) : super(context, resourceId, verticalAlignment)


    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int,
                      paint: Paint) { //draw 方法是重写的ImageSpan父类 DynamicDrawableSpan中的方法，在DynamicDrawableSpan类中，虽有getCachedDrawable()，
// 但是私有的，不能被调用，所以调用ImageSpan中的getrawable()方法，该方法中 会根据传入的drawable ID ，获取该id对应的
// drawable的流对象，并最终获取drawable对象
        val drawable = drawable //调用imageSpan中的方法获取drawable对象
        canvas.save()
        //获取画笔的文字绘制时的具体测量数据
        val fm = paint.fontMetricsInt
        //系统原有方法，默认是Bottom模式)
        var transY = bottom - drawable.bounds.bottom
        if (mVerticalAlignment == DynamicDrawableSpan.ALIGN_BASELINE) {
            transY -= fm.descent
        } else if (mVerticalAlignment == ALIGN_FONTCENTER) { //此处加入判断， 如果是自定义的居中对齐
//与文字的中间线对齐（这种方式不论是否设置行间距都能保障文字的中间线和图片的中间线是对齐的）
// y+ascent得到文字内容的顶部坐标，y+descent得到文字的底部坐标，（顶部坐标+底部坐标）/2=文字内容中间线坐标
            transY = (y + fm.descent + (y + fm.ascent)) / 2 - drawable.bounds.bottom / 2
        }
        canvas.translate(x, transY.toFloat())
        drawable.draw(canvas)
        canvas.restore()
    }

    /**
     * 重写getSize方法，只有重写该方法后，才能保证不论是图片大于文字还是文字大于图片，都能实现中间对齐
     */
    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: FontMetricsInt?): Int {
        val d = drawable
        val rect = d.bounds
        if (fm != null) {
            val fmPaint = paint.fontMetricsInt
            val fontHeight = fmPaint.bottom - fmPaint.top
            val drHeight = rect.bottom - rect.top
            val top = drHeight / 2 - fontHeight / 4
            val bottom = drHeight / 2 + fontHeight / 4
            fm.ascent = -bottom
            fm.top = -bottom
            fm.bottom = top
            fm.descent = top
        }
        return rect.right
    }
}