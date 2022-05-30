package com.miaomi.fenbei.base.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.widget.TextView

import com.miaomi.fenbei.base.R
import com.miaomi.fenbei.base.core.GlideApp

/**
 * Created by 
 * on 2019-06-18.
 */
class URLImageParser
/**
 *
 * @param textView 图文混排TextView
 * @param context
 * @param imageSize 图片显示高度
 */
(private val mTextView: TextView, private val mContext: Context, private val mImageSize: Int) {

    fun getDrawable(url: String): Drawable {
        val urlDrawable = URLDrawable()
        urlDrawable.setBounds(0, 0, mImageSize, mImageSize)
        ImageGetterAsyncTask(mContext, url, urlDrawable).execute(mTextView)
        return urlDrawable
    }

    inner class ImageGetterAsyncTask(private val context: Context, private val source: String, private val urlDrawable: URLDrawable) : AsyncTask<TextView, Void, Bitmap>() {
        private var textView: TextView? = null
        override fun doInBackground(vararg params: TextView): Bitmap? {
            textView = params[0]
            try {
                // 使用Glide获取网络图片Bitmap(使用Glide获取图片bitmap还有待研究)
                return GlideApp.with(context).asBitmap().load(source).placeholder(R.drawable.common_default)
                        .into(mImageSize, mImageSize).get()
            } catch (e: Exception) {

                return BitmapFactory.decodeResource(context.resources, R.drawable.common_default)
            }

        }

        override fun onPostExecute(bitmap: Bitmap) {
            try {
                //获取图片宽高比
                val ratio = bitmap.width * 1.0f / bitmap.height
                val bitmapDrawable = BitmapDrawable(context.resources, bitmap)
                bitmapDrawable.setBounds(0, 0, (mImageSize * ratio).toInt(), mImageSize)
                //设置图片宽、高（这里传入的mImageSize为字体大小，所以，设置的高为字体大小，宽为按宽高比缩放）
                urlDrawable.setBounds(0, 0, (mImageSize * ratio).toInt(), mImageSize)
                urlDrawable.drawable = bitmapDrawable
                //两次调用invalidate才会在异步加载完图片后，刷新图文混排TextView，显示出图片
                urlDrawable.invalidateSelf()
                textView!!.invalidate()
            } catch (e: Exception) {
                /* Like a null bitmap, etc. */
            }

        }
    }
}

