package com.ebei.ui;

import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebei.library.BaseActivity;
import com.ebei.library.Constants;
import com.ebei.library.DialogUtil;
import com.ebei.library.Event;
import com.ebei.library.TSCApi;
import com.ebei.library.ToastUtil;
import com.ebei.library.UserPreference;
import com.ebei.library.Util;
import com.ebei.pojo.Login;
import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.R;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.UserObject;
import com.ebei.tsc.browser.Browser;
import com.umeng.analytics.MobclickAgent;

import java.util.Stack;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/11/1.
 * 发现页面
 */

public class DiscoverActivity extends BaseActivity {

    private static final String TAG = "DiscoverActivity";
    public static String URL = "url";
    public static String MOBILE = "mobile";
    private String mobile;
    @Bind(R.id.view_title)
    View mViewTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.web)
    WebView mWebView;
    @Bind(R.id.img_back)
    RelativeLayout mImgBack;
    @Bind(R.id.tv_record)
    TextView mTvRecord;
    @Bind(R.id.tv_mx)
    LinearLayout mTvMx;
    private MyWebViewClient mWebViewClient;
    private String mCurrentPageUrl;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_discover;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, false);
        mTvTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        mTvTitle.setTextSize(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewTitle.getLayoutParams();
        params.height = Util.getStatusBarHeight(this) == 0 ? 20 : Util.getStatusBarHeight(this);
        mViewTitle.setLayoutParams(params);

        if (UserPreference.getInt(UserPreference.IS_MX_USER, 0) == 1) {
            mTvMx.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
        } else {
            mTvMx.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
            mobile = getIntent().getStringExtra(MOBILE);
            mobile = mobile.substring(2, mobile.length());
            String sign = Constants.SALT_CIPHER + "loginAccount=" + mobile + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
            sign = Util.encrypt(sign);
            TSCApi.getInstance().checkLoginAccount(0, mobile, sign, Util.getNowTime());
        }
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.RE_LOGIN, new Action1() {
            @Override
            public void call(Object o) {
                UserPreference.putString(UserPreference.SESSION_ID, "");
                UserPreference.putString(UserPreference.SECRET, "");
                UserPreference.putString(UserPreference.ACCOUNT, "");
                UserPreference.putInt(UserPreference.SEND_MESSAGE_COUNT, 0);
                DialogUtil.transactionDialog(DiscoverActivity.this, new DialogUtil.OnResultListener2() {
                    @Override
                    public void onOk(String... params) {
                        String sign = Constants.SALT_CIPHER + "loginAccount=" + mobile + "&password=" + Util.encrypt(params[0]) + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
                        sign = Util.encrypt(sign);
                        TSCApi.getInstance().userLogin(7, mobile, Util.encrypt(params[0]), sign, Util.getNowTime());
                    }

                    @Override
                    public void onForget() {
                        startActivity(new Intent(DiscoverActivity.this, UpdatePayPwdActivity.class));
                    }
                });
            }
        });

        rxManage.on(Event.CHECK_ACCOUNT, new Action1<String>() {
            @Override
            public void call(String o) {
                if (o.equals("用户名已存在")) {
                    if (Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                        DialogUtil.transactionDialog(DiscoverActivity.this, new DialogUtil.OnResultListener2() {
                            @Override
                            public void onOk(String... params) {
                                String sign = Constants.SALT_CIPHER + "loginAccount=" + mobile + "&password=" + Util.encrypt(params[0]) + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
                                sign = Util.encrypt(sign);
                                TSCApi.getInstance().userLogin(7, mobile, Util.encrypt(params[0]), sign, Util.getNowTime());
                            }

                            @Override
                            public void onForget() {
                                startActivity(new Intent(DiscoverActivity.this, UpdatePayPwdActivity.class));
                            }
                        });
                    } else {
                        loadUrl();
                    }
                } else if (o.equals("用户名不存在")) {
                    DialogUtil.setPayPwdDialog(DiscoverActivity.this, new DialogUtil.OnResultListener0() {
                        @Override
                        public void onOK() {
                            Intent intent = new Intent(DiscoverActivity.this, PayPwd1Activity.class);
                            intent.putExtra(PayPwd1Activity.MOBILE, mobile);
                            intent.putExtra(PayPwd1Activity.WHERE, 6);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        rxManage.on(Event.LOGIN_PWD6, new Action1<Login>() {
            @Override
            public void call(Login o) {
                ToastUtil.toast(DiscoverActivity.this, getString(R.string.validate_success));
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, o.getLoginAccount());
                String signA = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(DiscoverActivity.this) + "&submitTime=" +
                        Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signA = Util.encrypt(signA);
                TSCApi.getInstance().saveId(signA, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(DiscoverActivity.this));
                String sign = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=0&submitTime=" + Util.getNowTime() + "&type=1" + UserPreference.getString(UserPreference.SECRET, "");
                sign = Util.encrypt(sign);
                TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign, 1, 0);
                switch (UserPreference.getInt(UserPreference.SEND_MESSAGE_COUNT, 0)) {
                    case 5:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign9 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=5&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign9 = Util.encrypt(sign9);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign9, 2, 5);
                        }
                        break;
                    case 10:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign1 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=10&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign1 = Util.encrypt(sign1);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign1, 2, 10);
                        }
                        break;
                    case 15:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign2 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=15&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign2 = Util.encrypt(sign2);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign2, 2, 15);
                        }
                        break;
                    case 20:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign3 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=20&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign3 = Util.encrypt(sign3);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign3, 2, 20);
                        }
                        break;
                    case 25:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign4 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=25&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign4 = Util.encrypt(sign4);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign4, 2, 25);
                        }
                        break;
                    case 30:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign5 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=30&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign5 = Util.encrypt(sign5);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign5, 2, 30);
                        }
                        break;
                    case 40:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign6 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=40&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign6 = Util.encrypt(sign6);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign6, 2, 40);
                        }
                        break;
                    case 50:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign7 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=50&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign7 = Util.encrypt(sign7);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign7, 2, 50);
                        }
                        break;
                    case 60:
                        if (!Util.isEmpty(UserPreference.getString(UserPreference.SESSION_ID, ""))) {
                            String sign8 = UserPreference.getString(UserPreference.SECRET, "") + "chatNum=60&submitTime=" + Util.getNowTime() + "&type=2" + UserPreference.getString(UserPreference.SECRET, "");
                            sign8 = Util.encrypt(sign8);
                            TSCApi.getInstance().addTValue(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), sign8, 2, 60);
                        }
                        break;
                }
                Integer uid = UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId();
                TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(uid);
                String name = UserObject.getUserName(user);
                String signTele = UserPreference.getString(UserPreference.SECRET, "") + "nickName=" + name + "&openId=" + uid + "&portraitImgUrl=&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                signTele = Util.encrypt(signTele);
                TSCApi.getInstance().addTeleInfo(UserPreference.getString(UserPreference.SESSION_ID, ""), Util.getNowTime(), signTele, uid + "", name, "");
                loadUrl();
            }
        });
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

    public boolean pageGoBack(WebView web, MyWebViewClient client) {
        if (null != client) {
            final String url = client.popLastPageUrl();
            Log.d(TAG, "close page url: " + url);
            if (url != null) {
                try {
                    web.loadUrl(url);
                } catch (Exception e) {
                    Browser.openUrl(this, url);
                }
                if (url.equals(getIntent().getStringExtra(URL))) {
                    mImgBack.setVisibility(View.GONE);
                    mTvRecord.setVisibility(View.GONE);
                }
                return true;
            }
        }
        UserPreference.putInt(UserPreference.HAS_UPDATE_TIP, 0);
        Intent intent = new Intent();
        intent.putExtra("result", 5);
        setResult(RESULT_OK, intent);
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
            mImgBack.setVisibility(View.VISIBLE);
            Log.d(TAG, "open page url: " + url);
            if (!url.equals("data:text/plain,")) {
                mCurrentPageUrl = url;
            }
            if (url.startsWith(Constants.BASE_URL + "d-app/guess/index.html?appSessionId=")) {
                mTvRecord.setVisibility(View.VISIBLE);
            }
            if (url.startsWith(Constants.BASE_URL + "game/rule.html?appSessionId=")) {
                Intent intent = new Intent(DiscoverActivity.this, GamePlaneActivity.class);
                intent.putExtra(GamePlaneActivity.URL, url);
                startActivity(intent);
            } else
                view.loadUrl(mCurrentPageUrl);
            return true;
        }
    }

    @OnClick({R.id.rb_chats, R.id.rb_contacts, R.id.rb_mine, R.id.img_back, R.id.tv_record})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rb_chats:
                Intent intent = new Intent();
                intent.putExtra("result", 1);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.rb_contacts:
                Intent intent1 = new Intent();
                intent1.putExtra("result", 2);
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case R.id.rb_mine:
                Intent intent2 = new Intent();
                intent2.putExtra("result", 4);
                setResult(RESULT_OK, intent2);
                finish();
                break;
            case R.id.img_back:
                pageGoBack(mWebView, mWebViewClient);
                break;
            case R.id.tv_record:
                Intent intent3 = new Intent(this, GuessRecordActivity.class);
                intent3.putExtra(GuessRecordActivity.URL, Constants.GUESS_RECORD + "appSessionId=" + UserPreference.getString(UserPreference.SESSION_ID, "") + "&secret=" + UserPreference.getString(UserPreference.SECRET, ""));
                startActivity(intent3);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mImgBack.setVisibility(View.GONE);
        MobclickAgent.onPageStart("探索/返还");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("探索/返还");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mTvRecord.setVisibility(View.GONE);
        String sign = Constants.SALT_CIPHER + "loginAccount=" + mobile + "&submitTime=" + Util.getNowTime() + Constants.SALT_CIPHER;
        sign = Util.encrypt(sign);
        TSCApi.getInstance().checkLoginAccount(0, mobile, sign, Util.getNowTime());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && pageGoBack(mWebView, mWebViewClient);
    }

}
