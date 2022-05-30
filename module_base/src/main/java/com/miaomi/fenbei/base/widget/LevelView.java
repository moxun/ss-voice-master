package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.miaomi.fenbei.base.R;
import com.scwang.smartrefresh.layout.util.DensityUtil;

public class LevelView extends FrameLayout {

    private ImageView imageView;
    private DCBTextView textView;
    private Context context;
    private int preLevel;
    private int level;

    public LevelView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LevelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LevelView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        this.context = context;
        imageView = new ImageView(context);
        textView = new DCBTextView(context);
        textView.setTextColor(Color.parseColor("#FFFFFF"));

        FrameLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        FrameLayout.LayoutParams paramsIv = new LayoutParams(DensityUtil.dp2px( 32f), DensityUtil.dp2px( 20f));
        imageView.setLayoutParams(paramsIv);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        textView.setPadding(DensityUtil.dp2px( 14f),DensityUtil.dp2px( 3f),0,0);
//        textView.setLayoutParams(paramsIv);
        textView.setTextSize(9f);
        textView.setLayoutParams(params);
//        textView.setGravity(Gravity.END|Gravity.BOTTOM);
        addView(imageView);
        addView(textView);
    }
    public void setCharmLevel(int curlevel){
        this.level = curlevel;
        if (curlevel == 0){
            preLevel = 1;
        }else{
            preLevel = (curlevel - 1)/10+1;
        }
        curlevel %= 10;
        if (this.level != 0){
            setVisibility(VISIBLE);
            if(curlevel == 0){
                textView.setText("10");
            }else{
                textView.setText(String.valueOf(curlevel));
            }
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.common_bg_level_charm));
            imageView.setImageLevel(preLevel);
        } else {
            setVisibility(GONE);
        }
    }
    public void setWealthLevel(int curlevel){
        this.level = curlevel;
        if (curlevel == 0){
            preLevel = 1;
        }else{
            preLevel = (curlevel - 1)/10+1;
        }
        curlevel %= 10;
        if (this.level != 0){
            setVisibility(VISIBLE);
            if(curlevel == 0){
                textView.setText("10");
            }else{
                textView.setText(String.valueOf(curlevel));
            }
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.common_bg_level_wealth));
            imageView.setImageLevel(preLevel);
        } else {
            setVisibility(GONE);
        }
    }
}
