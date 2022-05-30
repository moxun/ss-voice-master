package com.miaomi.fenbei.base.core

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.miaomi.fenbei.base.config.BaseConfig

@GlideModule
class GlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //指定内存缓存大小
        builder.setMemoryCache(LruResourceCache(BaseConfig.MAX_CACHE_MEMORY_SIZE))
        //全部的内存缓存用来作为图片缓存
        builder.setBitmapPool(LruBitmapPool(BaseConfig.MAX_CACHE_MEMORY_SIZE))
    }
}