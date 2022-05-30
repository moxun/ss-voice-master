package com.miaomi.fenbei.base.web;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.util.DataHelper;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Mycroft on 2016/3/1.
 */
@Route(path = "/common/web")
public final class WebActivity extends BaseActivity {

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";
    private ProgressBar progressBar;
    private WebView mWebView;
    @Autowired(name = EXTRA_TITLE)
    public String title = "";
    @Autowired(name = EXTRA_URL)
    public String mUrl;
    private TextView titleTv;
    private boolean isRetrun = false;


    public static void start(@NonNull Context context, @NonNull String url,String title) {
        final Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        if (isRetrun){
            mWebView.loadUrl("javascript:h5ReturnPage()");
        }
        isRetrun = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.loadUrl("javascript:h5MusicPauseJs()");
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mWebView.destroy();
    }

    private final WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.toLowerCase().startsWith("kmi://")){
                ARouter.getInstance().build(Uri.parse(url)).navigation();
            }

            try {
                if (url.startsWith("weixin://") || url.startsWith("alipays://")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            } catch (Exception e) {
                return false;
            }

            if (url.contains("https://wx.tenpay.com")) {
                Map<String, String> extraHeaders = new HashMap<>();
                extraHeaders.put("Referer", "http://www.cnciyin.com");
                view.loadUrl(url, extraHeaders);
                return true;
            }else  if (url.startsWith("http") || url.startsWith("https")) {
                view.loadUrl(url);
            }
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            if(mWebView.getUrl().contains("nciyin.com/flipCard")){
                finish();
                return true;
            }
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public int getLayoutId() {
        return R.layout.common_activity_web;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        final Intent intent = getIntent();
        mUrl = intent.getStringExtra(EXTRA_URL);
        title = intent.getStringExtra(EXTRA_TITLE);
        mWebView = findViewById(R.id.web_view);
        titleTv = findViewById(R.id.main_tv);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTv.setText(title);
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
        webSetting.setTextZoom(100);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWebView.getUrl().contains("nciyin.com/flipCard")){
                    finish();
                    return;
                }
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }else{
                    finish();
                }
            }
        });

        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.loadUrl(mUrl);
//        mWebView.setDownloadListener(new DownloadListener() {
//
//            @Override
//            public void onDownloadStart(String url, String userAgent,
//                                        String contentDisposition, String mimetype,
//                                        long contentLength) {
//                //通过下面的方法进行启动浏览器的活动
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//        });
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
                        finish();
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
