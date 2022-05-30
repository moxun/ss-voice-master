package com.miaomi.fenbei.base.core.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.miaomi.fenbei.base.R;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public abstract class BaseDialogFragment extends DialogFragment {

    private static final String TAG = "base_dialog";

    private static final float DEFAULT_DIM = 0.2f;
    public Boolean isLive;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFullScreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Objects.requireNonNull(getDialog().getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(getCancelOutside());

        View v = inflater.inflate(getLayoutRes(), container, false);
        bindView(v);
        return v;
    }

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void bindView(View v);

    @Override
    public void onStart() {
        super.onStart();
        isLive = true;
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.dimAmount = getDimAmount();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        window.setAttributes(params);
    }

    public int getHeight() {
        return -1;
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }

    public boolean getCancelOutside() {
        return true;
    }

    public String getFragmentTag() {
        return TAG;
    }

    public void show(FragmentManager fragmentManager) {
        if (!this.isAdded() && !this.isVisible() && !this.isRemoving()){
            show(fragmentManager, getFragmentTag());
            fragmentManager.executePendingTransactions();
        } else {
            if (getDialog() != null) {
                getDialog().show();
            } else {
//                LogUtil.INSTANCE.e("show");
//                show(fragmentManager, getFragmentTag());
//                fragmentManager.executePendingTransactions();
            }
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void notUseMethods(MessageEvent event) { }

    @Override
    public void dismiss() {
        isLive = false;
        if (getFragmentManager() != null) {
//            super.dismiss();
            dismissAllowingStateLoss();
        }
    }
}
