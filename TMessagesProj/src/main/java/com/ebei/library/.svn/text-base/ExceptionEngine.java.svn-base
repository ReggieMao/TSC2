package com.ebei.library;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by MaoLJ on 2018/7/18.
 *
 */

public class ExceptionEngine {

    public static ApiException convertException(Throwable e) {
        ApiException ex = null;
        if (e instanceof HttpException) { //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, TSCCode.HTTP_ERROR);
            switch (httpException.code()) {
                case TSCCode.UNAUTHORIZED:
                case TSCCode.FORBIDDEN:
                case TSCCode.NOT_FOUND:
                case TSCCode.REQUEST_TIMEOUT:
                case TSCCode.GATEWAY_TIMEOUT:
                case TSCCode.INTERNAL_SERVER_ERROR:
                case TSCCode.BAD_GATEWAY:
                case TSCCode.SERVICE_UNAVAILABLE:
                default:
                    ex.setMessage(TSCStrings.NETWORK_ERROR);
                    break;
            }
            return ex;

        } else if (e instanceof ServerException) {//服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getCode());
            ex.setMessage(resultException.getMessage());
            return ex;

        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {//均视为解析错误
            ex = new ApiException(e, TSCCode.PARSE_ERROR);
            ex.setMessage(TSCStrings.PARSE_ERROR);
            return ex;

        } else if (e instanceof ConnectException) {//均视为网络错误
            ex = new ApiException(e, TSCCode.NETWORD_ERROR);
            ex.setMessage(TSCStrings.NETWORK_ERROR);
            return ex;

        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, TSCCode.SSL_ERROR);
            ex.setMessage(TSCStrings.SSL_ERROR);
            return ex;

        } else {//未知错误
            ex = new ApiException(e, TSCCode.UNKNOWN);
            ex.setMessage(TSCStrings.UNKNOWN_ERROR);
            return ex;
        }
    }

}
