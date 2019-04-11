package com.ebei.ui;

import android.content.res.Configuration;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebei.library.BaseActivity;
import com.ebei.library.Constants;
import com.ebei.library.Util;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.R;
import com.ebei.tsc.browser.Browser;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/11/22.
 * 数字期权用户协议页面
 */

public class DigitalInfoActivity extends BaseActivity {

    private static final String TAG = "DigitalInfoActivity";
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.web)
    WebView mWebView;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_digital_info;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);
        String url = Constants.FOTA_INFO;
        try {
            mWebView.loadUrl(url);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.getSettings().setAllowFileAccess(true);
            mWebView.getSettings().setSupportZoom(true);
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setDatabaseEnabled(true);
            mWebView.getSettings().setLoadWithOverviewMode(true);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.getSettings().setAppCacheEnabled(true);
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                    WebView newWebView = new WebView(view.getContext());
                    newWebView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            mWebView.loadUrl(url);
                            return true;
                        }
                    });
                    WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                    transport.setWebView(newWebView);
                    resultMsg.sendToTarget();
                    return true;
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
