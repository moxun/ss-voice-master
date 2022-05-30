package com.miaomi.fenbei.base.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Toast-pc on 2016/3/22.
 */
public class CopyUtil {
    public static void copy(String content, Context context) {
// 得到剪贴板管理器
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", content + "");
        cm.setPrimaryClip(mClipData);
        ToastUtil.INSTANCE.suc(context, "复制成功");
    }

    public static CharSequence getTmpString(Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText();
    }
}
