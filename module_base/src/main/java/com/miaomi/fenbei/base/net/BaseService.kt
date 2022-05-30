package com.miaomi.fenbei.base.net

import android.content.Context
import com.miaomi.fenbei.base.util.DataHelper
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

class BaseService private constructor(context: Context) {

    companion object {
        @Volatile
        private var instance: BaseService? = null
        fun getInstance(context: Context): BaseService {
            if (instance == null) {
                synchronized(BaseService::class) {
                    if (instance == null) {
                        instance = BaseService(context)
                    }
                }
            }
            return instance!!
        }
    }


    var mHttpClient: OkHttpClient
    var mRetrofit: Retrofit

    init {
        mHttpClient = OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.SECONDS)
                .addInterceptor(getLogInterceptor())
                .cache(Cache(File(context.cacheDir, "net"), (10 shl 20).toLong()))
                .build()

        mRetrofit = Retrofit.Builder()
                .baseUrl(DataHelper.getIMDevelop().new_main+"/apis/")
                .client(mHttpClient)
                .addConverterFactory(MrRequestFactory.create())
                .validateEagerly(true)
                .build()
    }

    private fun getLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }


}