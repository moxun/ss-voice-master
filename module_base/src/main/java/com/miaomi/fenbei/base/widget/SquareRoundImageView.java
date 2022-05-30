package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.util.DensityUtil;

import androidx.annotation.Nullable;

public class SquareRoundImageView extends androidx.appcompat.widget.AppCompatImageView {

    private int radius = DensityUtil.dp2px(8f);

    public SquareRoundImageView(Context context) {
        this(context, null);
    }

    public SquareRoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareRoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), radius, radius, Path.Direction.CW);
        canvas.clipPath(path);//设置可显示的区域，canvas四个角会被剪裁掉
        super.onDraw(canvas);
    }
}

