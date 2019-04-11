package com.ebei.library;

import android.content.Context;
import android.util.Log;

import com.ebei.tsc.R;

/**
 * Created by MaoLJ on 2018/7/18.
 * 对ApiException异常进行统一处理
 */

public class ExceptionHandler {

    private static final String TAG = "ExceptionHandler";
    /** 上一次发生异常处理的时间 */
    private static long mLastHandlerStartTime = 0L;
    /** 异常处理的触发间隔时间 */
    public static long TRIGER_INTERVAL = 5000L;

    public static void handleException(Context context, ApiException e) {

        Log.d(TAG, "code == " + e.getCode());
        mLastHandlerStartTime = System.currentTimeMillis();

        if (!Util.isNetworkOpen(context)) {
            ToastUtil.toast(context, context.getString(R.string.net_error));
            return;
        }
        switch (e.getCode()) {
            case TSCCode.PARSE_ERROR:
                new RxManage().post(Event.RE_LOGIN, null);
                break;

            case TSCCode.EMPTY_DATA:
                ToastUtil.toast(context, TSCStrings.EMPTY_DATA);
                break;

            case TSCCode.BUSSINESS_EXCEPTION:
                if (e.getMessage().equals(context.getString(R.string.rob_logout)) || e.getMessage().equals("请重新登录")) {
                    new RxManage().post(Event.RE_LOGIN, null);
                } else {
                    ToastUtil.toast(context, e.getMessage());
                }
                break;

            case TSCCode.OUTDATE_OF_SESSION:
                ToastUtil.toast(context, TSCStrings.OUTDATE_OF_SESSION);
                break;

            case TSCCode.FOURCE_OUT_OF_SESSION:
                ToastUtil.toast(context, TSCStrings.FOURCE_OUT_OF_SESSION);
                break;

            case TSCCode.SECURITY_ERROR:
                ToastUtil.toast(context, TSCStrings.SECURITY_ERROR);
                break;

            case TSCCode.AUTHORIZATION_FAILURE:
                ToastUtil.toast(context, TSCStrings.AUTHORIZATION_FAILURE);
                break;

            case TSCCode.SERVER_INTERNAL_ERROR:
                ToastUtil.toast(context, TSCStrings.SERVER_INTERNAL_ERROR);
                break;

            case TSCCode.NETWORD_ERROR:
                ToastUtil.toast(context, context.getString(R.string.net_error));
                break;

            default:
                ToastUtil.toast(context, TSCStrings.SYSTEM_MAINTAINING);
                break;
        }
    }

    public static long getmLastHandlerStartTime() {
        return mLastHandlerStartTime;
    }

}
