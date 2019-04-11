package com.ebei.library;

import com.ebei.pojo.RxResult;

import rx.functions.Func1;

/**
 * Created by MaoLJ on 2018/7/18.
 *
 */

public class ServerResultFunc<T> implements Func1<RxResult<T>, T> {
    @Override
    public T call(RxResult<T> tRxResult) {
        if (!tRxResult.getErrCode().equals(Constants.API_SUCCESS)) {
            switch (tRxResult.getErrDesc()) {
                case "红包领完":
                    new RxManage().post(Event.RED_NOTHING, tRxResult.getResult());
                    break;
                case "请稍后重试":
                    new RxManage().post(Event.TRY_AGAIN_LATER, "请稍后重试");
                    break;
                case "支付密码错误":
                    new RxManage().post(Event.PAY_PWD_ERROR, "支付密码错误");
                    break;
                case "openId不存在":
                    new RxManage().post(Event.OPEN_ID_ERROR, "对方版本过低或未登陆过TSC");
                    break;
            }
            throw new ServerException(TSCCode.BUSSINESS_EXCEPTION, tRxResult.getErrDesc());
        } else if (tRxResult.getErrCode().equals(Constants.API_SUCCESS) && tRxResult.getErrDesc().equals("领取成功")) {
            new RxManage().post(Event.HAD_RECEIVE_RED, null);
        }
        return tRxResult.getResult();
    }
}

