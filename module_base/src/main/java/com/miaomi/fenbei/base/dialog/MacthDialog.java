package com.miaomi.fenbei.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.WindowManager;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

public class MacthDialog extends Dialog {

    private SVGAImageView svgaImageView;
    private UserInfo toUserInfo;
    private OnAnimFinishListener onAnimFinishListener;

    public interface OnAnimFinishListener{
        void onFinish();
    }

    public MacthDialog(@NonNull Context context,UserInfo userInfo,OnAnimFinishListener onAnimFinishListener) {
        super(context, R.style.common_dialog);
        this.toUserInfo = userInfo;
        this.onAnimFinishListener = onAnimFinishListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_dialog_match);
        svgaImageView = findViewById(R.id.iv_svga);
        showSvgaGiftAnim("anim_match_success.svga");
//        svgaImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build("/imkit/privatechat")
//                        .withString("CHAT_ID", String.valueOf(toUserInfo.getUser_id()))
//                        .withString("FROM_USER_NICKNAME", toUserInfo.getNickname())
//                        .withString("FROM_USER_AVTER", toUserInfo.getFace())
//                        .navigation();
//            }
//        });
    }

    private void showSvgaGiftAnim(String name){
        SVGAParser parser = new SVGAParser(getContext());
        svgaImageView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onRepeat() {
                svgaImageView.stopAnimation();
                onAnimFinishListener.onFinish();
                dismiss();
            }

            @Override
            public void onStep(int i, double v) {

            }
        });
        parser.decodeFromAssets(name, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                final SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
//                dynamicEntity.setDynamicImage(toUserInfo.getFace(), "receiver_avatar");
                ImgUtil.INSTANCE.getCircleBitmap(getContext(), DataHelper.INSTANCE.getUserInfo().getFace(), new ImgUtil.OnGetBitmap() {
                    @Override
                    public void onGetBitmap(@NotNull Bitmap resource) {
                        dynamicEntity.setDynamicImage(resource, "sender_avatar");
                    }
                });

                ImgUtil.INSTANCE.getCircleBitmap(getContext(), toUserInfo.getFace(), new ImgUtil.OnGetBitmap() {
                    @Override
                    public void onGetBitmap(@NotNull Bitmap resource) {
                        dynamicEntity.setDynamicImage(resource, "receiver_avatar");
                    }
                });
                TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(20);
                textPaint.setARGB(0xff, 0x33, 0x33, 0x33);
                dynamicEntity.setDynamicText(toUserInfo.getNickname(), textPaint, "receiver_nickname");
                dynamicEntity.setDynamicText(DataHelper.INSTANCE.getUserInfo().getNickname(), textPaint, "sender_nickname");
                SVGADrawable drawable = new SVGADrawable(videoItem, dynamicEntity);
                svgaImageView.setImageDrawable(drawable);
                svgaImageView.startAnimation();
            }
            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

//    public Bitmap toRoundBitmap(Bitmap bitmap) {
//        bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
//        Bitmap bm = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bm);
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);// 这里需要先画出一个圆
//        canvas.drawCircle(200, 200, 200, paint);// 圆画好之后将画笔重置一下
//        paint.reset();// 设置图像合成模式，该模式为只在源图像和目标图像相交的地方绘制源图像
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, 0, 0, paint);
//        return bm;
//    }
}
