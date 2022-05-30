package com.miaomi.fenbei.base.util;

import android.graphics.Color;
import androidx.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.text.NumberFormat;

public class StringUtil {
    /**
     * 搜索字符变红
     * @param content
     * @param keyWord
     * @return
     */
    public static SpannableString strToSpannableStr(String content, String keyWord){
        if (content == null){
            return new SpannableString("");
        }
        if (content.contains(keyWord)){
            SpannableString realNameStr = new SpannableString(content);
            realNameStr.setSpan(new ForegroundColorSpan(Color.parseColor("#ED52F9")), content.indexOf(keyWord), content.indexOf(keyWord)+keyWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return realNameStr;
        }else{
            return new SpannableString(content);
        }
    }

    /**
     * 移除标点符号
     * @param string
     * @return
     */
    public static String removePunctuation(@NonNull String string) {
        // 移除除中文英文字符数字和-以外的所有字符
        return string.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9-]+", "");
    }

    public static String formatDouble(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        //设置保留多少位小数
        nf.setMaximumFractionDigits(2);
        // 取消科学计数法
        nf.setGroupingUsed(false);
        //返回结果
        return nf.format(d);
    }

    public static String unicodeToString(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换
            int data = Integer.parseInt(hex[i], 16);
            // 拼接成string
            string.append((char) data);
        }

        return string.toString();
    }

}
