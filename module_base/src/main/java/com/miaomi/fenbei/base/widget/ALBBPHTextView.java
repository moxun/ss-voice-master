package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class ALBBPHTextView extends androidx.appcompat.widget.AppCompatTextView {
    public ALBBPHTextView(Context context) {
        this(context, null);
    }
    public ALBBPHTextView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }
    public ALBBPHTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        try{
            /*
             *必须事先在assets底下创建一fonts文件夹，并放入要使用的字体文件(.ttf/.otf)
             *并提供相对路径给creatFromAsset()来创建Typeface对象
             */
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Alibaba-PuHuiTi-Heavy.otf");
            // 当使用外部字体却又发现字体没有变化的时候(以Droid Sans代替)，通常是因为这个字体android没有支持,而非你的程序发生了错误
            if(typeface != null)
                setTypeface(typeface);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
