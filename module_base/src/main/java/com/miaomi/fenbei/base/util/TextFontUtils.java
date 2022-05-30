package com.miaomi.fenbei.base.util;

import android.text.Html;
import android.widget.TextView;

public class TextFontUtils {

    /**
     * 高亮字体的颜色
     */
    public static String HIGHLIGHT_COLOR = "#C274FF";

    /**
     * 使指定的字符串显示不同的颜色
     * @param regexStr  高亮字符串
     * @param targetStr 原字符串
     * @param textView  文本框
     */
    public static void setHighlightFont(String regexStr, String targetStr, TextView textView) {
        targetStr = targetStr.replaceAll(regexStr, "<font color='" + HIGHLIGHT_COLOR + "'><big>" + regexStr + "</big></font>");
        textView.setText(Html.fromHtml(targetStr));
    }

}
