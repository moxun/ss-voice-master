package com.miaomi.fenbei.base.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.miaomi.fenbei.base.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import androidx.core.content.ContextCompat;


public class Gif500CoinView extends RelativeLayout {

    /**
     * 最低产生金币的底数
     */
    public static final int MIN_COIN_COUNT_ONE_TIME = 120;

    /**
     * 产生金币的随机数量底数
     */
    public static final int RANDOM_COIN = 260;
    /**
     * 抛撒金币的离500倍数的高度随机数
     */
    public static final int LIMIT_COIN_Y_START = 350;


    public int minCoinCount = MIN_COIN_COUNT_ONE_TIME;
    public int randomCoin = RANDOM_COIN;
    public int limitCoinY = LIMIT_COIN_Y_START;

    public Gif500CoinView(Context context) {
        super(context);
    }

    public Gif500CoinView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Gif500CoinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public boolean isShowing(PointF targetPointF){
//        for(int i = 0; i< pointFS.size();i++ ){
//            if(pointFS.get(i).x == targetPointF.x){
//                return true;
//            }
//        }
        return  false;
    }
    private List<PointF> pointFS = new ArrayList<>();

    public void showAnima(final PointF targetPointF, PointF endPointF,Animator.AnimatorListener animatorListener) {
        pointFS.add(targetPointF);

        int totalCoin = (int) (minCoinCount + Math.random() * randomCoin);

        AnimatorSet set = new AnimatorSet();
        Collection<Animator> valueAnimators = new ArrayList<>();
        //开启每一个金币
        int dy = 60 + (int) (Math.random() * limitCoinY);
        for (int i = 0; i < totalCoin; i++) {
            // int y = (int) (targetPointF.y - dy);
            int y = (int) targetPointF.y;//(int) (targetPointF.y - dy);
            int x = (int) targetPointF.x;

            ImageView imageView = new ImageView(getContext());
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_km_pay_kd);
            imageView.setImageDrawable(drawable);
            addView(imageView);
            imageView.setX(x );
            imageView.setY(y);

            //  imageView.setVisibility(INVISIBLE);
            valueAnimators.add(getBezierValueAnimator(imageView, targetPointF, endPointF));
            // getBezierValueAnimator(imageView,targetPointF,endPointF).start();
        }
        if(animatorListener!=null){
            set.addListener(animatorListener);
        }
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Iterator<PointF> pointFIterator = pointFS.iterator();
                while (pointFIterator.hasNext()){
                    PointF next = pointFIterator.next();
                    if(next.x == targetPointF.x){
                        pointFIterator.remove();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                Iterator<PointF> pointFIterator = pointFS.iterator();
                while (pointFIterator.hasNext()){
                    PointF next = pointFIterator.next();
                    if(next.x == targetPointF.x){
                        pointFIterator.remove();
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        set.playTogether(valueAnimators);
        set.start();

    }


    private ValueAnimator getBezierValueAnimator(final View target, PointF targetPointF, PointF endPointF) {

        //初始化一个贝塞尔计算器- - 传入
        BezierEvaluator evaluator = new BezierEvaluator(getPointF1(targetPointF, endPointF), getPointF2(targetPointF, endPointF));

        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF(target.getX(), target.getY()), endPointF);
        animator.addUpdateListener(new BezierListener(target));
        animator.setTarget(target);
        animator.setDuration((long) (Math.random() * 800 + 1000));
        animator.setStartDelay((long) (Math.random() * 300));

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                target.setVisibility(GONE);
                if(target.getParent()!=null){
                    ((ViewGroup)(target.getParent())).removeView(target);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                target.setVisibility(GONE);
                if(target.getParent()!=null){
                    ((ViewGroup)(target.getParent())).removeView(target);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return animator;
    }

    /**
     * 产生随机控制点
     *
     * @return
     */
    private PointF getPointF1(PointF targetPointF, PointF endPointF) {
        PointF pointF = new PointF();
        pointF.x = (float) (Math.random() * endPointF.x );
        pointF.y = (float) (Math.random() * targetPointF.y  -200  - (200)*Math.random());
        return pointF;
    }

    private PointF getPointF2(PointF targetPointF, PointF endPointF) {
        PointF pointF = new PointF();
        pointF.x = (float) (Math.random() * (getMeasuredWidth() - endPointF.x) + endPointF.x);
        pointF.y = (float) (Math.random() * (getMeasuredHeight() - targetPointF.y) + targetPointF.y);
        return pointF;
    }


    private class BezierListener implements ValueAnimator.AnimatorUpdateListener {

        private View target;

        public BezierListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            // 这里顺便做一个alpha动画
            target.setAlpha(1.5F - animation.getAnimatedFraction());
        }

    }


    public class BezierEvaluator implements TypeEvaluator<PointF> {


        private PointF pointF1;
        private PointF pointF2;

        public BezierEvaluator(PointF pointF1, PointF pointF2) {
            this.pointF1 = pointF1;
            this.pointF2 = pointF2;
        }

        @Override
        public PointF evaluate(float time, PointF startValue,
                               PointF endValue) {

            float timeLeft = 1.0f - time;
            PointF point = new PointF();//结果

            point.x = timeLeft * timeLeft * timeLeft * (startValue.x)
                    + 3 * timeLeft * timeLeft * time * (pointF1.x)
                    + 3 * timeLeft * time * time * (pointF2.x)
                    + time * time * time * (endValue.x);

            point.y = timeLeft * timeLeft * timeLeft * (startValue.y)
                    + 3 * timeLeft * timeLeft * time * (pointF1.y)
                    + 3 * timeLeft * time * time * (pointF2.y)
                    + time * time * time * (endValue.y);
            return point;
        }
    }

}
