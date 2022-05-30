package com.miaomi.fenbei.room.ui.dialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ToastUtil;


public class GameDialog extends BaseBottomDialog {

    private WebView mWebView;
    private String url;
    private ProgressBar progressBar;
    public GameDialog(String url) {
        this.url = url;
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    private final WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }

        @Override
        public void onPageFinished(WebView view, String url) {
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    };



    @Override
    public int getLayoutRes() {
        return R.layout.room_dialog_game;
    }

    @Override
    public void bindView(View v) {
        mWebView = v.findViewById(R.id.web_view);
        progressBar = v.findViewById(R.id.progress_bar);
//        mWebView.setBackgroundColor(0);
//        LayerDrawable drawable = (LayerDrawable) progressBar.getProgressDrawable();
//        GradientDrawable gradientDrawable = new GradientDrawable();
//        gradientDrawable.setColor(Color.parseColor("#FF6196"));
//        ClipDrawable clipDrawable = new ClipDrawable(gradientDrawable, Gravity.START,ClipDrawable.HORIZONTAL);
//        drawable.setDrawableByLayerId(android.R.id.progress,clipDrawable);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 960);
//        mWebView.setLayoutParams(layoutParams);
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setMediaPlaybackRequiresUserGesture(false);
        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(url);
        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                //通过下面的方法进行启动浏览器的活动
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mWebView.addJavascriptInterface(
                new Object() {

                    //调起支付
                    @JavascriptInterface
                    public void startPayActivity() {
                        ARouter.getInstance().build("/app/pay").navigation();
                    }

                    @JavascriptInterface
                    public void startActivity(String url) {
                        ARouter.getInstance().build(url).navigation();
                    }

                    @JavascriptInterface
                    public int getUid(String url) {
                        if (DataHelper.INSTANCE.getUserInfo() == null){
                            return 0;
                        }else{
                            return DataHelper.INSTANCE.getUserInfo().getUser_id();
                        }
                    }

                    @JavascriptInterface
                    public String getToken(String url) {
                        return DataHelper.INSTANCE.getLoginToken();
                    }


                    @JavascriptInterface
                    public void closeWebView() {
                        dismiss();
                    }

                    @JavascriptInterface
                    public void toast(String msg) {
                        ToastUtil.INSTANCE.suc(getContext(),msg);
                    }

                }, "JsObjectFromAndroid");
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                if (progressBar.getVisibility() == View.GONE)
                    progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
