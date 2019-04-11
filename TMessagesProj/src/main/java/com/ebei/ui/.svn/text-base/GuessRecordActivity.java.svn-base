package com.ebei.ui;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebei.library.BaseActivity;
import com.ebei.library.Util;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.ebei.tsc.browser.Browser;

import java.util.Stack;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/9/7.
 * 竞猜记录页面
 */

public class GuessRecordActivity extends BaseActivity {

    private static final String TAG = "GuessRecordActivity";
    public static String URL = "url";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.web)
    WebView mWebView;
    private MyWebViewClient mWebViewClient;
    private String mCurrentPageUrl;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_guess_record;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
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

    public boolean pageGoBack(WebView web, MyWebViewClient client) {
        final String url = client.popLastPageUrl();
        if (url != null) {
            try {
                web.loadUrl(url);
            } catch (Exception e) {
                Browser.openUrl(this, url);
            }
            return true;
        }
        finish();
        return false;
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
            if (!url.equals("data:text/plain,")) {
                mCurrentPageUrl = url;
            }
            view.loadUrl(mCurrentPageUrl);
            return true;
        }
    }

    @OnClick({R.id.img_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                pageGoBack(mWebView, mWebViewClient);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && pageGoBack(mWebView, mWebViewClient);
    }

}
