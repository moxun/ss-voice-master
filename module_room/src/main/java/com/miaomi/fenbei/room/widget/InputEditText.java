package com.miaomi.fenbei.room.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;


public class InputEditText extends MentionEditText {

    private OnBackListener listener;

    public interface OnBackListener{
       void  onBack();
    }


    public InputEditText(Context context) {
        super(context);
    }

    public InputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_BACK && listener != null) {
            listener.onBack();
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public void addBackListener(OnBackListener listener){
        this.listener = listener;
    }

}
