package com.ebei.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ebei.library.BaseActivity;
import com.ebei.library.Constants;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.tsc.R;
import com.ebei.tsc.browser.Browser;

import java.util.Stack;

import butterknife.Bind;

/**
 * Created by MaoLj on 2019/1/7.
 * 飞机大战游戏页面
 */

public class GamePlaneActivity extends BaseActivity {

    private static final String TAG = "GamePlaneActivity";
    public static String URL = "url";
    @Bind(R.id.web)
    WebView mWebView;
    private String mCurrentPageUrl;
    private MyWebViewClient mWebViewClient;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_game_plane;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        loadUrl();
    }

    private void loadUrl() {
        mCurrentPageUrl = getIntent().getStringExtra(URL);
        try {
            mWebView.clearCache(true);
            mWebView.loadUrl(mCurrentPageUrl);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.getSettings().setAllowFileAccess(true);
            mWebView.getSettings().setSupportZoom(true);
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setDatabaseEnabled(false);
            mWebView.getSettings().setLoadWithOverviewMode(true);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.getSettings().setAppCacheEnabled(false);
            mWebView.setWebChromeClient(new WebChromeClient());
            mWebViewClient = new MyWebViewClient();
            mWebView.setWebViewClient(mWebViewClient);
        } catch (Exception e) {
            Browser.openUrl(this, mCurrentPageUrl);
        }
    }

    private class MyWebViewClient extends WebViewClient {

        private final Stack<String> mUrls = new Stack<>();
        private boolean mIsLoading;
        private String mUrlBeforeRedirect;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mIsLoading && mUrls.size() > 0) {
                mUrlBeforeRedirect = mUrls.pop();
            }
            recordUrl(url);
            this.mIsLoading = true;
        }

        private void recordUrl(String url) {
            if (!TextUtils.isEmpty(url) && !url.equalsIgnoreCase(getLastPageUrl())) {
                mUrls.push(url);
            } else if (!TextUtils.isEmpty(mUrlBeforeRedirect)) {
                mUrls.push(mUrlBeforeRedirect);
                mUrlBeforeRedirect = null;
            }
        }

        private synchronized String getLastPageUrl() {
            return mUrls.size() > 0 ? mUrls.peek() : null;
        }

        public String popLastPageUrl() {
            if (mUrls.size() >= 2) {
                mUrls.pop();
                return mUrls.pop();
            }
            return null;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (this.mIsLoading) {
                this.mIsLoading = false;
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            mImgBack.setVisibility(View.VISIBLE);
            Log.d(TAG, "open page url: " + url);
            if (!url.equals("data:text/plain,")) {
                mCurrentPageUrl = url;
            }
            view.loadUrl(mCurrentPageUrl);
            return true;
        }
    }

    public boolean pageGoBack(WebView web, MyWebViewClient client) {
        if (null != client) {
            final String url = client.popLastPageUrl();
            Log.d(TAG, "close page url: " + url);
            if (url != null) {
                if (!url.startsWith(Constants.BASE_URL + "game/rule.html?appSessionId=")) {
                    try {
                        web.loadUrl(url);
                    } catch (Exception e) {
                        Browser.openUrl(this, url);
                    }
//                    if (url.equals(getIntent().getStringExtra(URL))) {
//                        mImgBack.setVisibility(View.GONE);
//                    }
                } else {
                    finish();
                }
                return true;
            }
        }
        finish();
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && pageGoBack(mWebView, mWebViewClient);
    }

}
