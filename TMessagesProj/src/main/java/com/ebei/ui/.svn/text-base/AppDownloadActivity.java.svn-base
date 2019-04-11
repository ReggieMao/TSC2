package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.DownloadListener;
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

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/9/7.
 * APP下载页面
 */

public class AppDownloadActivity extends BaseActivity {

    private static final String TAG = "AppDownloadActivity";
    public static String URL = "url";
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.web)
    WebView mWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_download;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        String url = getIntent().getStringExtra(URL);
        try {
            mWebView.clearCache(true);
            mWebView.loadUrl(url);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.getSettings().setAllowFileAccess(true);
            mWebView.getSettings().setSupportZoom(true);
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            mWebView.getSettings().setDomStorageEnabled(false);
            mWebView.getSettings().setDatabaseEnabled(false);
            mWebView.getSettings().setLoadWithOverviewMode(true);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.getSettings().setAppCacheEnabled(false);
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                    WebView newWebView = new WebView(view.getContext());
                    newWebView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            mWebView.clearCache(true);
                            mWebView.loadUrl(url);
                            mWebView.getSettings().setJavaScriptEnabled(true);
                            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            mWebView.getSettings().setAllowFileAccess(true);
                            mWebView.getSettings().setSupportZoom(true);
                            mWebView.getSettings().setBuiltInZoomControls(true);
                            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                            mWebView.getSettings().setDomStorageEnabled(false);
                            mWebView.getSettings().setDatabaseEnabled(false);
                            mWebView.getSettings().setLoadWithOverviewMode(true);
                            mWebView.getSettings().setUseWideViewPort(true);
                            mWebView.getSettings().setAppCacheEnabled(false);
                            return true;
                        }
                    });
                    WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                    transport.setWebView(newWebView);
                    resultMsg.sendToTarget();
                    return true;
                }
            });
            mWebView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            Browser.openUrl(this, url);
        }
    }

    @OnClick({R.id.img_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

}
