package com.miaomi.fenbei.base.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import android.content.pm.PackageManager;

public class ChannelUtil {

	public static String getAppChannel(Context context){
        String appMV = "guanwang";
        try{
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			appMV = appInfo.metaData.getString("CHANNEL");
        }catch(NameNotFoundException e) {
            e.printStackTrace();
        }
		return appMV;
	}
}
