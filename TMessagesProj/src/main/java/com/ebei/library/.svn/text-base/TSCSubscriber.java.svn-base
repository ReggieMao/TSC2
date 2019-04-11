package com.ebei.library;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by MaoLJ on 2018/7/18.
 *
 */

public abstract class TSCSubscriber<T> extends Subscriber<T>{

    private final static String TAG = TSCSubscriber.class.getSimpleName();

    @Override
    public void onCompleted() {
        Log.d(TAG, "Subscriber onCompleted!");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "Subscriber onError == " + e.getMessage());
        new RxManage().post(Event.SIMPLE_EXCEPTION_HANDLE, (ApiException)e);
    }

}
