package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class StrokeTextView extends AppCompatTextView {
    private int mStrokeColor = Color.parseColor("#B2640B");
    private float mStrokeWidth = 8f;

    public StrokeTextView(Context context) {
        this(context, null);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        if (attrs != null) {
//            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
//            mStrokeColor = a.getColor(R.styleable.StrokeTextView_strokeColor, Color.parseColor("#D144FF"));
//            mStrokeWidth = a.getDimension(R.styleable.StrokeTextView_strokeWidth, 1f);
//            a.recycle();
//        }

        setTextColor(mStrokeColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 只有描边
        TextPaint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(mStrokeColor);
        paint.setStrokeWidth(mStrokeWidth);
        super.onDraw(canvas);
    }
}

