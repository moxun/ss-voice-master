package com.miaomi.fenbei.base.util;

import android.os.Environment;

import com.miaomi.fenbei.base.core.CommonLib;

import java.io.File;
import java.text.DecimalFormat;

public class FileUtil {
    private static String pathDiv = "/";
    private static File cacheDir = !isExternalStorageWritable()?CommonLib.mContext.getFilesDir(): CommonLib.mContext.getExternalCacheDir();

    /**
     * 判断外部存储是否可用
     *
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    /**
     * 获取缓存文件地址
     */
    public static String getCacheFilePath(String fileName){
        return cacheDir.getAbsolutePath()+pathDiv+fileName;
    }


    /**
     * 判断缓存文件是否存在
     */
    public static boolean isCacheFileExist(String fileName){
        File file = new File(getCacheFilePath(fileName));
        return file.exists();
    }

    public static String getFileMB(long len){
        DecimalFormat df = new DecimalFormat("#.00");
        if (len<1048576){
            return "<1";
        }else{
            return df.format((double) len / 1048576);
        }
    }
}
