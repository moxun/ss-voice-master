package com.miaomi.fenbei.base.util

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Animatable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.fresco.animation.backend.AnimationBackend
import com.facebook.fresco.animation.backend.AnimationBackendDelegate
import com.facebook.fresco.animation.drawable.AnimatedDrawable2
import com.facebook.fresco.animation.drawable.AnimationListener
import com.facebook.imagepipeline.image.ImageInfo
import com.miaomi.fenbei.base.R
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.core.GlideApp
import com.miaomi.fenbei.base.widget.LeftGlideRoundTransform
import com.miaomi.fenbei.base.widget.SQGlideRoundTransform
import com.miaomi.fenbei.base.widget.TopGlideRoundTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.GrayscaleTransformation
import java.io.File


object ImgUtil {

    fun loadImg(context: Context, img: Any?, target: ImageView) {
        if (img == null){
            return
        }
        GlideApp.with(context).load(img).into(target)
    }

    fun loadImg(context: Context, img: Any?, target: ImageView, placeholder: Int = R.drawable.common_default) {
        if (img == null){
            return
        }
//        GlideApp.with(context).load(img).placeholder(placeholder).transform(CenterCrop()).into(target)
        if (checkContext(context)) {
            if (placeholder == -1){
                GlideApp.with(context)
                        .load(img)
                        .transform(CenterCrop())
                        .into(target)
            }else{
                GlideApp.with(context).load(img).placeholder(placeholder).error(placeholder).transform(CenterCrop()).into(target)
            }
        }
    }

//    fun loadGiftIcon(context: Context, img: Any, target: ImageView, placeholder: Int = R.drawable.common_default) {
////        GlideApp.with(context).load(img).placeholder(placeholder).transform(CenterCrop()).into(target)
//        if (checkContext(context)) {
//            if (placeholder == -1){
//                GlideApp.with(context)
//                        .load(img)
//                        .transform(CenterCrop())
//                        .override(100,100)
//                        .into(target)
//            }else{
//                GlideApp.with(context)
//                        .load(img)
//                        .placeholder(placeholder)
//                        .error(placeholder)
//                        .transform(CenterCrop())
//                        .override(100,100)
//                        .into(target)
//            }
//        }
//    }

    fun loadBigImg(context: Context, img: Any, target: ImageView, placeholder: Int = R.drawable.common_default) {
        if (checkContext(context)) {
            if (placeholder == -1){
                GlideApp.with(context).load(img).thumbnail(0.2f).transform(CenterCrop()).into(target)
            }else{
                GlideApp.with(context).load(img).thumbnail(0.2f).placeholder(placeholder).error(placeholder).transform(CenterCrop()).into(target)
            }
        }
    }

    fun loadRoundImg(context: Context, img: Any?, target: ImageView, roundDp: Float = 8F, placeholder: Int = R.drawable.common_default) {
        if (img == null){
            return
        }
        if (checkContext(context)) {
            if (placeholder == -1) {
                GlideApp.with(context)
                        .load(img)
                        .transforms(RoundedCorners(DensityUtil.dp2px(context, roundDp)))
                        .into(target)
            }else{
                GlideApp.with(context).load(img).placeholder(getPlaceHolder(context,placeholder,2,roundDp)).error(getPlaceHolder(context,placeholder,2,roundDp)).transforms(RoundedCorners(DensityUtil.dp2px(context, roundDp))).into(target)
            }
        }
    }

    fun loadPlayUserImg(context: Context, img: Any?, target: ImageView, roundDp: Float = 8F, placeholder: Int = R.drawable.common_default) {
        if (img == null){
            return
        }
        if (checkContext(context)) {
            if (placeholder == -1) {
                GlideApp.with(context)
                        .load(img)
                        .centerCrop()
                        .into(target)
            }else{
                GlideApp.with(context)
                        .load(img)
                        .centerCrop()
                        .placeholder(getPlaceHolder(context,placeholder,2,roundDp))
                        .error(getPlaceHolder(context,placeholder,2,roundDp))
                        .into(target)
            }
        }
    }

    fun loadHotRoomRoundImg(context: Context, img: Any?, target: ImageView, roundDp: Float = 6F, placeholder: Int = R.drawable.common_default) {
        if (img == null){
            return
        }
        if (checkContext(context)) {
            if (placeholder == -1) {
                GlideApp.with(context)
                        .load(img)
                        .transforms(TopGlideRoundTransform(roundDp.toInt()))
                        .into(target)
            }else{
                GlideApp.with(context).load(img).placeholder(getPlaceHolder(context,placeholder,2,roundDp)).error(getPlaceHolder(context,placeholder,2,roundDp)).transforms(TopGlideRoundTransform(roundDp.toInt())).into(target)
            }
        }
    }

    fun loadHomeHotImg(context: Context, img: Any?, target: ImageView, roundDp: Float = 6F, placeholder: Int = R.drawable.common_default) {
        if (img == null){
            return
        }
        if (checkContext(context)) {
            if (placeholder == -1) {
                GlideApp.with(context)
                        .load(img)
                        .transforms(SQGlideRoundTransform(roundDp.toInt()))
                        .into(target)
            }else{
                GlideApp.with(context).load(img).placeholder(getPlaceHolder(context,placeholder,2,roundDp)).error(getPlaceHolder(context,placeholder,2,roundDp)).transforms(TopGlideRoundTransform(roundDp.toInt())).into(target)
            }
        }
    }

    //朦胧图片
    fun loadBlurImg(context: Context, img: Any, target: ImageView,radius: Int,sampling: Int) {
        GlideApp.with(context)
                .load(img)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .transforms(BlackWhiteTransformation())
                .apply(RequestOptions.bitmapTransform(BlurTransformation(radius, sampling)))
//                .transition(DrawableTransitionOptions.withCrossFade())
                .into(target)
    }

    //灰度处理
    fun loadGrayImg(context: Context, img: Any, target: ImageView) {
        if (checkContext(context)) {
            GlideApp.with(context)
                    .load(img)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .transforms(BlackWhiteTransformation())
                    .apply(RequestOptions.bitmapTransform(GrayscaleTransformation()))
                    .into(target)
        }
    }
    fun loadLeftRoundImg(context: Context, img: Any, target: ImageView, roundDp: Float = 10F, placeholder: Int = R.drawable.common_default) {
        if (checkContext(context)) {
            if (placeholder == -1) {
                GlideApp.with(context)
                        .load(img)
                        .transforms(LeftGlideRoundTransform(roundDp.toInt()))
                        .into(target)
            }else{
                GlideApp.with(context).load(img).placeholder(getPlaceHolder(context,placeholder,2,roundDp)).error(getPlaceHolder(context,placeholder,2,roundDp)).transforms(LeftGlideRoundTransform(roundDp.toInt())).into(target)
            }
        }
    }

    fun loadTopRoundImg(context: Context, img: Any, target: ImageView, roundDp: Float = 8F, placeholder: Int = R.drawable.common_default) {
        if (checkContext(context)) {
            if (placeholder == -1) {
                GlideApp.with(context)
                        .load(img)
                        .transforms(TopGlideRoundTransform(roundDp.toInt()))
                        .into(target)
            }else{
                GlideApp.with(context).load(img).placeholder(getPlaceHolder(context,placeholder,2,roundDp)).error(getPlaceHolder(context,placeholder,2,roundDp)).transforms(TopGlideRoundTransform(roundDp.toInt())).into(target)
            }
        }
    }


    fun loadAnimRoundImg(context: Context, img: Any, target: ImageView, roundDp: Float = 8F, placeholder: Int = R.drawable.common_default) {
        if (checkContext(context)) {
            if (placeholder == -1) {
                GlideApp.with(context)
                        .load(img)
                        .transition(GenericTransitionOptions.with(R.anim.amin_glide_load))
                        .transforms(RoundedCorners(DensityUtil.dp2px(context, roundDp)))
                        .into(target)
            }else{
                GlideApp.with(context).load(img).placeholder(getPlaceHolder(context,placeholder,2,roundDp)).error(getPlaceHolder(context,placeholder,2,roundDp)).transforms(RoundedCorners(DensityUtil.dp2px(context, roundDp))).into(target)
            }
        }
    }

    fun loadRoomIcon(context: Context, img: Any?, target: ImageView, roundDp: Float = 8F, placeholder: Int = R.drawable.common_default) {
        if (img == null){
            return
        }
        if (checkContext(context)) {
            if (placeholder == -1) {
                GlideApp.with(context)
                        .load(img)
                        .transforms(CenterCrop(), RoundedCorners(DensityUtil.dp2px(context, roundDp)))
                        .override(400,400)
                        .into(target)
            }else{
                GlideApp.with(context)
                        .load(img)
                        .placeholder(getPlaceHolder(context,placeholder,2,roundDp))
                        .error(getPlaceHolder(context,placeholder,2,roundDp))
                        .transforms(CenterCrop(), RoundedCorners(DensityUtil.dp2px(context, roundDp)))
                        .override(400,400)
                        .into(target)
            }
        }
    }

    fun loadCircleImg(context: Context, img: Any?, target: ImageView, placeholder: Int = R.drawable.common_avter_placeholder) {
        if (img == null){
            return
        }
        if (checkContext(context)) {
            if (placeholder == -1) {
                GlideApp.with(context)
                        .load(img)
                        .transforms(CenterCrop(), CircleCrop())
                        .override(100,100)
                        .into(target)
            }else{
                GlideApp.with(context).load(img)
                        .placeholder(getPlaceHolder(context,placeholder,1))
                        .error(getPlaceHolder(context,placeholder,1))
                        .transforms(CenterCrop(), CircleCrop())
                        .override(100,100)
                        .into(target)
            }
        }
    }

//    fun loadRoomImg(context: Context, img: Any, image: ImageView){
//        val imageViewWeakReference = WeakReference(image)
//        val target = imageViewWeakReference.get()
//        if (target != null) {
//            GlideApp.with(context).load(img).transforms(CircleCrop())
//                    .format(DecodeFormat.PREFER_RGB_565)
//                    .placeholder(R.drawable.common_avter_placeholder)
//                    .override(100,100)
//                    .into(target)
//        }
//    }


    fun loadFaceIcon(context: Context, img: Any?, target: ImageView){
        if (img == null){
            return
        }
        if (checkContext(context)) {
            GlideApp.with(context).load(img)
                    .transforms(CircleCrop())
                    .format(DecodeFormat.PREFER_RGB_565)
                    .placeholder(R.drawable.common_avter_placeholder)
                    .override(100,100)
                    .into(target)
        }
    }

    fun loadFaceIconNoCheck(context: Context, img: Any?, target: ImageView){
        if (img == null){
            return
        }
        GlideApp.with(context).load(img)
                .transforms(CircleCrop())
                .format(DecodeFormat.PREFER_RGB_565)
                .placeholder(R.drawable.common_avter_placeholder)
                .override(100,100)
                .into(target)
    }




    fun loadGiftImg(context: Context, img: Any?, target: ImageView){
        if (img == null){
            return
        }
        if (checkContext(context)) {
            GlideApp.with(context).load(img)
                    .format(DecodeFormat.PREFER_RGB_565)
                    .placeholder(R.drawable.common_gift_default)
                    .override(100,100)
                    .into(target)
        }
    }




    fun loadGif(context: Context,img: Any,target: ImageView){
        if (checkContext(context)){
            GlideApp.with(context).asGif().load(img).into(target)
        }
    }

    fun loadGif(context: Context,img: Any,target: ImageView, loopCount: Int = 1){
        if (checkContext(context)){
            GifLoadOneTimeGif.loadOneTimeGif(context, img, target, loopCount) {
                target.visibility = View.GONE
            }
        }
    }

    fun loadGif(context: Context,img: Any,target: ImageView, loopCount: Int = 1, callback: GifLoadOneTimeGif.GifListener){
        if (checkContext(context)){
            GifLoadOneTimeGif.loadOneTimeGif(context, img, target, loopCount, callback)
        }
    }

    fun loadGifOrWebp(context: Context, img: String, target: SimpleDraweeView, loopCount: Int = 1) {
        if (checkContext(context)) {
            if (img.endsWith(".gif")) {
                loadGif(context, img, target, if (loopCount == Int.MAX_VALUE) 9999 else loopCount)
            } else {
                loadWebpGif(context, img, target, loopCount)
            }
        }
    }

    fun loadWebpDrawable(context: Context, @DrawableRes resId: Int, target: SimpleDraweeView, loopCount: Int = 1, callback: GifLoadOneTimeGif.GifListener? = null) {
        if (checkContext(context)) {
            var controller = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setUri(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +"://" +
                            context.resources.getResourcePackageName(resId) +"/" + context.resources.getResourceTypeName(resId) +"/" + context.resources.getResourceEntryName(resId)))
                    .setOldController(target.controller)
                    .setControllerListener(object : BaseControllerListener<ImageInfo>() {
                        override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                            if (animatable is AnimatedDrawable2) {
                                var animatedDrawable: AnimatedDrawable2 = animatable
                                animatedDrawable.animationBackend = LoopCountModifyingBackend(animatedDrawable!!.animationBackend!!, loopCount)
                                animatedDrawable.setAnimationListener(object : AnimationListener {
                                    override fun onAnimationRepeat(drawable: AnimatedDrawable2?) {

                                    }

                                    override fun onAnimationStart(drawable: AnimatedDrawable2?) {
                                    }

                                    override fun onAnimationFrame(drawable: AnimatedDrawable2?, frameNumber: Int) {
                                    }

                                    override fun onAnimationStop(drawable: AnimatedDrawable2?) {
                                        target.visibility = View.GONE
                                        callback?.gifPlayComplete()
                                    }

                                    override fun onAnimationReset(drawable: AnimatedDrawable2?) {
                                    }
                                })
                            }
                        }
                    }).build()
            target.controller = controller
        }
    }

    fun loadWebpGif(context: Context, img: String, target: SimpleDraweeView, loopCount: Int = 1) {
        if (checkContext(context)) {
            var controller = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setUri(Uri.parse(img))
                    .setOldController(target.controller)
                    .setControllerListener(object : BaseControllerListener<ImageInfo>() {
                        override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                            if (animatable is AnimatedDrawable2) {
                                var animatedDrawable: AnimatedDrawable2 = animatable
                                animatedDrawable.animationBackend = LoopCountModifyingBackend(animatedDrawable!!.animationBackend!!, loopCount)
                                animatedDrawable.setAnimationListener(object : AnimationListener {
                                    override fun onAnimationRepeat(drawable: AnimatedDrawable2?) {

                                    }

                                    override fun onAnimationStart(drawable: AnimatedDrawable2?) {
                                    }

                                    override fun onAnimationFrame(drawable: AnimatedDrawable2?, frameNumber: Int) {
                                    }

                                    override fun onAnimationStop(drawable: AnimatedDrawable2?) {
                                        target.visibility = View.GONE
                                    }

                                    override fun onAnimationReset(drawable: AnimatedDrawable2?) {
                                    }
                                })
                            }
                        }
                    }).build()
            target.controller = controller
        }
    }

    public fun getCircleBitmap(context:Context,url:String,onGetBitmap: OnGetBitmap){
        if (checkContext(context)){
            GlideApp.with(context).asBitmap().load(url).into(object : SimpleTarget<Bitmap>() {

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    onGetBitmap.onGetBitmap(toRoundBitmap(resource))
                }
            })
        }
    }

    fun toRoundBitmap(oldBitmap: Bitmap): Bitmap {
        var bitmap = oldBitmap
        bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true)
        val bm = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG) // 这里需要先画出一个圆
        canvas.drawCircle(200f, 200f, 200f, paint) // 圆画好之后将画笔重置一下
        paint.reset() // 设置图像合成模式，该模式为只在源图像和目标图像相交的地方绘制源图像
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return bm
    }

    interface OnGetBitmap{
        fun onGetBitmap(resource: Bitmap)
    }

    private fun getPlaceHolder(context: Context,placeholderRes: Int, shape:Int,round:Float = 8f):RoundedBitmapDrawable{
        when(shape){
            1 ->{
                val cirPH = BitmapFactory.decodeResource(context.resources, placeholderRes)
                val cirIcon = RoundedBitmapDrawableFactory.create(context.resources, cirPH)
                cirIcon.isCircular = true
                return cirIcon
            }
            2->{
                val roundPH = BitmapFactory.decodeResource(context.resources, placeholderRes)
                val roundIcon = RoundedBitmapDrawableFactory.create(context.resources, roundPH)
                roundIcon.cornerRadius = round
                return roundIcon
            }
        }


        return RoundedBitmapDrawableFactory.create(context.resources, BitmapFactory.decodeResource(context.resources, placeholderRes))
    }

    fun clearCache(context: Context) {
        val mRunnable = Runnable {
            run {
                GlideApp.get(context).clearDiskCache()
            }
        }
        Thread(mRunnable).start()

    }

    fun displayCache(context: Context): File? {
        return GlideApp.getPhotoCacheDir(context)
    }


    private fun checkContext(context: Context): Boolean {

        if (context is BaseActivity) {
            return context.isLive
        }

        if (context is BaseFragment) {
            return context.isLive
        }

        if (context is Application){
            return true
        }
        LogUtil.e("ImgUtil checkContext your context maybe leak memory " + context.javaClass.simpleName)
        return false
    }

    private class LoopCountModifyingBackend(animationBackend: AnimationBackend, loopCount: Int): AnimationBackendDelegate<AnimationBackend>(animationBackend) {

        private var mLoopCount = 1

        init {
            mLoopCount = loopCount
        }

        override fun getLoopCount(): Int {
            return mLoopCount
        }
    }
    fun clearMemory(context: Context){
        GlideApp.get(context).clearMemory()
    }
    fun trimMemory(context: Context,level:Int){
        GlideApp.get(context).trimMemory(level)
    }
}