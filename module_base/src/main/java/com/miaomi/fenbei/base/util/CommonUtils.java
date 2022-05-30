package com.miaomi.fenbei.base.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isAppInstall(Context context,String packName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packName)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static List<String> getRegEx(String input, String regex) {
        List<String> stringList = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        while (m.find())
            stringList.add(m.group());

        return stringList;
    }
    public static boolean isNull(String checkStr) {

        boolean result = false;

        if (null == checkStr){

            result = true;
        } else {
            if (checkStr.length() == 0) {

                result = true;
            }
        }
        return result;
    }

    public static boolean isNull(List<?> list) {

        boolean result = false;

        if (null == list){

            result = true;
        } else {
            if (list.size() == 0) {

                result = true;
            }
        }
        return result;
    }
}
