package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.miaomi.fenbei.base.R;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LevelBigView extends FrameLayout{
    private ImageView imageView;
    private StrokeTextView textView;

    private TextView textView1;
    private Context context;
    private List<String> charmDes = new ArrayList<>();
    private List<String> wealthDes = new ArrayList<>();
    private int preLevel;
    private int level;

    public LevelBigView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LevelBigView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LevelBigView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        charmDes.add("初见锋芒");
        charmDes.add("见习爱豆");
        charmDes.add("名声大振");
        charmDes.add("人气偶像");
        charmDes.add("一线明星");
        charmDes.add("超级巨星");
        charmDes.add("璀璨之星");
        charmDes.add("流量天王");

        wealthDes.add("养猪厂长");
        wealthDes.add("鱼塘塘主");
        wealthDes.add("豪气盟主");
        wealthDes.add("显赫荣耀");
        wealthDes.add("富可敌国");
        wealthDes.add("堆金积玉");
        wealthDes.add("挥金如土");
        wealthDes.add("荣耀至尊");


        this.context = context;
        imageView = new ImageView(context);
        textView = new StrokeTextView(context);
        textView1 = new TextView(context);
        textView1.setTextColor(Color.parseColor("#FFFFFF"));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END|Gravity.BOTTOM;
        FrameLayout.LayoutParams paramsIv = new FrameLayout.LayoutParams(DensityUtil.dp2px( 36f), DensityUtil.dp2px( 36f));
        imageView.setLayoutParams(paramsIv);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        textView.setPadding(0,0,DensityUtil.dp2px( 4f),0);
//        textView.setLayoutParams(paramsIv);
        textView.setTextSize(14f);
        textView.setLayoutParams(params);

        textView1.setPadding(0,0,DensityUtil.dp2px( 4f),0);
//        textView.setLayoutParams(paramsIv);
        textView1.setTextSize(14f);
        textView1.setLayoutParams(params);
//        textView.setGravity(Gravity.END|Gravity.BOTTOM);
        addView(imageView);
        addView(textView);
        addView(textView1);
    }
    public void setCharmLevel(int curlevel){
        this.level = curlevel;
        if (curlevel == 0){
            preLevel = 1;
        }else{
            preLevel = (curlevel - 1)/10+1;
        }
        curlevel %= 10;
//        if (curlevel == 0 && level !=0){
//            preLevel = preLevel + 1;
//        }
        if (this.level != 0){
            setVisibility(VISIBLE);
            if(curlevel == 0){
                textView.setText("10");
                textView1.setText("10");
            }else{
                textView.setText(String.valueOf(curlevel));
                textView1.setText(String.valueOf(curlevel));
            }
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.common_bg_level_big_charm));
            imageView.setImageLevel(preLevel);
        } else {
            textView.setText("");
            setVisibility(GONE);
        }
    }

    public String getCharmDes(){
        if (level == 0){
            return "";
        }
        return charmDes.get(preLevel-1);
    }
    public String getWealthDes(){
        if (level == 0){
            return "";
        }
        return wealthDes.get(preLevel-1);
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
                textView1.setText("10");
            }else{
                textView.setText(String.valueOf(curlevel));
                textView1.setText(String.valueOf(curlevel));
            }
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.common_bg_level_big_wealth));
            imageView.setImageLevel(preLevel);
        } else {
            textView.setText("");
            setVisibility(GONE);
        }
    }
}
