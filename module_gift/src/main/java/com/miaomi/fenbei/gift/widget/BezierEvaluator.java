package com.miaomi.fenbei.gift.widget;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class BezierEvaluator implements TypeEvaluator<PointF> {

    private int mWidthPixels;
    private int mHeightPixels;

    public BezierEvaluator(int mWidthPixels, int mHeightPixels) {
        this.mWidthPixels = mWidthPixels;
        this.mHeightPixels = mHeightPixels;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float oneMinusT = 1.0f - fraction;

        //startValue;    //开始出现的点
        //endValue;      //结束终点

        PointF controlPoint = new PointF();    //贝塞尔曲线控制点
        controlPoint.set(mWidthPixels / 2 + 600, mHeightPixels / 2 - 300);

        PointF point = new PointF();    //返回计算好的点
        point.x = oneMinusT * oneMinusT * (startValue.x) + 2 * oneMinusT * fraction * (controlPoint.x) + fraction * fraction * (endValue.x);
        point.y = oneMinusT * oneMinusT * (startValue.y) + 2 * oneMinusT * fraction * (controlPoint.y) + fraction * fraction * (endValue.y);
        return point;
    }
}
