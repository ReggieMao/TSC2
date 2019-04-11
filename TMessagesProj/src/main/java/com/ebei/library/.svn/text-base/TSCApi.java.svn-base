package com.ebei.library;

import android.util.Log;

import com.ebei.pojo.AppVersion;
import com.ebei.pojo.Balance;
import com.ebei.pojo.Book;
import com.ebei.pojo.ChatTransfer;
import com.ebei.pojo.DigitalRecord;
import com.ebei.pojo.FotaBalance;
import com.ebei.pojo.FotaLogin;
import com.ebei.pojo.Login;
import com.ebei.pojo.MXUserInfo;
import com.ebei.pojo.Market;
import com.ebei.pojo.Miner;
import com.ebei.pojo.MineralAsset;
import com.ebei.pojo.RedBag;
import com.ebei.pojo.RedBag2;
import com.ebei.pojo.RedBagDetail;
import com.ebei.pojo.RedBagFind1;
import com.ebei.pojo.RedBagFind2;
import com.ebei.pojo.Register;
import com.ebei.pojo.TReceive;
import com.ebei.pojo.TValue;
import com.ebei.pojo.TValue1;
import com.ebei.pojo.VpnInfo;
import com.ebei.pojo.Wallet;

import java.util.List;

import rx.schedulers.Schedulers;

/**
 * Created by MaoLj on 2018/7/26.
 * 具体api
 */

public class TSCApi {

    private static final String TAG = "TSCApi";
    private TSCApi() {}
    private static TSCApi tscApi = new TSCApi();
    public static TSCApi getInstance() {
        return tscApi;
    }

    // 发送验证码
    public void getAuthCode(String mobile, String sign, String submitTime) {
        Api.getInstance().service.getAuthCode(mobile, sign, submitTime)
                .map(new ServerResultFunc<Object>())
                .onErrorResumeNext(new ApiExceptionFunc<Object>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        new RxManage().post(Event.GET_AUTH_CODE, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 账号密码登录
    public void userLogin(int where, String mobile, String password, String sign, String submitTime) {
        Api.getInstance().service.userLogin(mobile, password, sign, submitTime)
                .map(new ServerResultFunc<Login>())
                .onErrorResumeNext(new ApiExceptionFunc<Login>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Login>() {
                    @Override
                    public void onNext(Login data) {
                        switch (where) {
                            case 1:
                                new RxManage().post(Event.LOGIN_PWD, data);
                                break;
                            case 2:
                                new RxManage().post(Event.LOGIN_PWD1, data);
                                break;
                            case 3:
                                new RxManage().post(Event.LOGIN_PWD2, data);
                                break;
                            case 4:
                                new RxManage().post(Event.LOGIN_PWD3, data);
                                break;
                            case 5:
                                new RxManage().post(Event.LOGIN_PWD4, data);
                                break;
                            case 6:
                                new RxManage().post(Event.LOGIN_PWD5, data);
                                break;
                            case 7:
                                new RxManage().post(Event.LOGIN_PWD6, data);
                                break;
                            case 8:
                                new RxManage().post(Event.LOGIN_PWD7, data);
                                break;
                            case 9:
                                new RxManage().post(Event.LOGIN_PWD8, data);
                                break;
                            case 10:
                                new RxManage().post(Event.LOGIN_PWD9, data);
                                break;
                            case 11:
                                new RxManage().post(Event.LOGIN_PWD10, data);
                                break;
                            case 12:
                                new RxManage().post(Event.LOGIN_PWD11, data);
                                break;
                            case 13:
                                new RxManage().post(Event.LOGIN_PWD12, data);
                                break;
                            case 14:
                                new RxManage().post(Event.LOGIN_PWD13, data);
                                break;
                        }
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 修改密码（支付密码和登录密码）
    public void restPwd(String mobile, String password, String confirmPassword, int pwdType, String verifyCode, String sign, String submitTime) {
        Api.getInstance().service.restPwd(mobile, password, confirmPassword, pwdType, verifyCode, sign, submitTime)
                .map(new ServerResultFunc<Object>())
                .onErrorResumeNext(new ApiExceptionFunc<Object>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        if (pwdType == 1)
                            new RxManage().post(Event.REST_LOGIN_PWD, data);
                        else
                            new RxManage().post(Event.REST_PAY_PWD, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 矿池总资产（昨日收益）
    public void mineralAsset(int where, String appSessionId, String submitTime, String sign) {
        Api.getInstance().service.mineralAsset(appSessionId, submitTime, sign)
                .map(new ServerResultFunc<MineralAsset>())
                .onErrorResumeNext(new ApiExceptionFunc<MineralAsset>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<MineralAsset>() {
                    @Override
                    public void onNext(MineralAsset data) {
                        if (where == 0)
                            new RxManage().post(Event.MINERAL_ASSET, data);
                        else if (where == 1)
                            new RxManage().post(Event.MINERAL_ASSET1, data);
                        else
                            new RxManage().post(Event.MINERAL_ASSET2, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 余额明细接口+详情
    public void findBalanceList(String sign, String submitTime, String appSessionId, String userWalletType) {
        Api.getInstance().service.findBalanceList(sign, submitTime, appSessionId, userWalletType)
                .map(new ServerResultFunc<List<Balance>>())
                .onErrorResumeNext(new ApiExceptionFunc<List<Balance>>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<List<Balance>>() {
                    @Override
                    public void onNext(List<Balance> data) {
                        new RxManage().post(Event.BALANCE_LIST, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 转币
    public void transferToOthers(int where, String sign, String submitTime, String appSessionId, String userWalletType, String outAddress, String transferAmount, String payPassword) {
        Api.getInstance().service.transferToOthers(sign, submitTime, appSessionId, userWalletType, outAddress, transferAmount, payPassword)
                .map(new ServerResultFunc<Object>())
                .onErrorResumeNext(new ApiExceptionFunc<Object>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        if (where == 0)
                            new RxManage().post(Event.TRANSFER_TO_OTHERS, data);
                        else
                            new RxManage().post(Event.TRANSFER_TO_OTHERS1, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 转入/出矿池
    public void transferToOre(String sign, String submitTime, String appSessionId, String transferAmount, String payPassword, int operateType) {
        Api.getInstance().service.transferToOre(sign, submitTime, appSessionId, transferAmount, payPassword, operateType)
                .map(new ServerResultFunc<Object>())
                .onErrorResumeNext(new ApiExceptionFunc<Object>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        if (operateType == 1)
                            new RxManage().post(Event.TRANSFER_TO_ORE, data);
                        else
                            new RxManage().post(Event.TRANSFER_TO_ORE1, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // app登录id保存
    public void saveId(String sign, String submitTime, String appSessionId, String audienceId) {
        Api.getInstance().service.saveId(sign, submitTime, appSessionId, audienceId)
                .map(new ServerResultFunc<Object>())
                .onErrorResumeNext(new ApiExceptionFunc<Object>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        new RxManage().post(Event.SAVE_ID, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 公告列表
    public void findNewsList(int where, String submitTime, String sign, String appSessionId) {
        Api.getInstance().service.findNewsList(submitTime, sign, appSessionId)
                .map(new ServerResultFunc<Market>())
                .onErrorResumeNext(new ApiExceptionFunc<Market>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Market>() {
                    @Override
                    public void onNext(Market data) {
                        if (where == 0)
                            new RxManage().post(Event.FIND_NEWS_LIST, data);
                        else if (where == 1)
                            new RxManage().post(Event.FIND_NEWS_LIST1, data);
                        else
                            new RxManage().post(Event.FIND_NEWS_LIST2, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 判断手机号是否已注册
    public void checkLoginAccount(int where, String mobile, String sign, String submitTime) {
        Api.getInstance().service.checkLoginAccount(mobile, submitTime, sign)
                .map(new ServerResultFunc<String>())
                .onErrorResumeNext(new ApiExceptionFunc<String>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<String>() {
                    @Override
                    public void onNext(String data) {
                        switch (where) {
                            case 0:
                                new RxManage().post(Event.CHECK_ACCOUNT, data);
                                break;
                            case 1:
                                new RxManage().post(Event.CHECK_ACCOUNT1, data);
                                break;
                            case 2:
                                new RxManage().post(Event.CHECK_ACCOUNT2, data);
                                break;
                            case 3:
                                new RxManage().post(Event.CHECK_ACCOUNT3, data);
                                break;
                            case 4:
                                new RxManage().post(Event.CHECK_ACCOUNT4, data);
                                break;
                        }
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 矿池明细
    public void minerDetail(int where, String appSessionId, String submitTime, String sign) {
        Api.getInstance().service.minerDetail(appSessionId, submitTime, sign)
                .map(new ServerResultFunc<List<Miner>>())
                .onErrorResumeNext(new ApiExceptionFunc<List<Miner>>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<List<Miner>>() {
                    @Override
                    public void onNext(List<Miner> data) {
                        if (where == 0)
                            new RxManage().post(Event.MINER_DETAIL, data);
                        else
                            new RxManage().post(Event.MINER_DETAIL1, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // T值查询
    public void getTValue(String appSessionId, String submitTime, String sign) {
        Api.getInstance().service.getTValue(appSessionId, submitTime, sign)
                .map(new ServerResultFunc<List<TValue>>())
                .onErrorResumeNext(new ApiExceptionFunc<List<TValue>>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<List<TValue>>() {
                    @Override
                    public void onNext(List<TValue> data) {
                        new RxManage().post(Event.GET_T_VALUE, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 增加T值
    public void addTValue(String appSessionId, String submitTime, String sign, int type, int chatNum) {
        Api.getInstance().service.addTValue(appSessionId, submitTime, sign, type, chatNum)
                .map(new ServerResultFunc<Object>())
                .onErrorResumeNext(new ApiExceptionFunc<Object>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        new RxManage().post(Event.ADD_T_VALUE, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // T值对应领取的TSC查询
    public void findReceive(String appSessionId, String submitTime, String sign) {
        Api.getInstance().service.findReceive(appSessionId, submitTime, sign)
                .map(new ServerResultFunc<TReceive>())
                .onErrorResumeNext(new ApiExceptionFunc<TReceive>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<TReceive>() {
                    @Override
                    public void onNext(TReceive data) {
                        new RxManage().post(Event.FIND_RECEIVE, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 月度累计T值
    public void findMonthRecord(String appSessionId, String submitTime, String sign) {
        Api.getInstance().service.findMonthRecord(appSessionId, submitTime, sign)
                .map(new ServerResultFunc<List<TValue1>>())
                .onErrorResumeNext(new ApiExceptionFunc<List<TValue1>>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<List<TValue1>>() {
                    @Override
                    public void onNext(List<TValue1> data) {
                        new RxManage().post(Event.FIND_MONTH_RECORD, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 注册（新）
    public void newRegister(String loginAccount, String payPassWord, String confirmPayPassWord, String sign, String submitTime) {
        Api.getInstance().service.newRegister(loginAccount, payPassWord, confirmPayPassWord, sign, submitTime)
                .map(new ServerResultFunc<Register>())
                .onErrorResumeNext(new ApiExceptionFunc<Register>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Register>() {
                    @Override
                    public void onNext(Register data) {
                        new RxManage().post(Event.REGISTER_NEW, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 验证码验证
    public void checkVerifyCode(String loginAccount, String verifyCode, String sign, String submitTime) {
        Api.getInstance().service.checkVerifyCode(loginAccount, verifyCode, sign, submitTime)
                .map(new ServerResultFunc<String>())
                .onErrorResumeNext(new ApiExceptionFunc<String>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<String>() {
                    @Override
                    public void onNext(String data) {
                        new RxManage().post(Event.CHECK_VERIFY_CODE, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 获取最新版本
    public void getLatestAppVersion(String appSystem) {
        Api.getInstance().service.getLatestAppVersion(appSystem)
                .map(new ServerResultFunc<AppVersion>())
                .onErrorResumeNext(new ApiExceptionFunc<AppVersion>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<AppVersion>() {
                    @Override
                    public void onNext(AppVersion data) {
                        new RxManage().post(Event.APP_VERSION, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 领红包
    public void receiveRedbag(String appSessionId, String submitTime, String sign, String redbagId) {
        Api.getInstance().service.receiveRedbag(appSessionId, submitTime, sign, redbagId)
                .map(new ServerResultFunc<RedBag2>())
                .onErrorResumeNext(new ApiExceptionFunc<RedBag2>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<RedBag2>() {
                    @Override
                    public void onNext(RedBag2 data) {
                        new RxManage().post(Event.RECEIVE_REDBAG, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 发红包
    public void sendRedbag(String appSessionId, String submitTime, String sign, int type, String totalMoney, String number, String payPassword, String coinType) {
        Api.getInstance().service.sendRedbag(appSessionId, submitTime, sign, type, totalMoney, number, payPassword, coinType)
                .map(new ServerResultFunc<RedBag>())
                .onErrorResumeNext(new ApiExceptionFunc<RedBag>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<RedBag>() {
                    @Override
                    public void onNext(RedBag data) {
                        new RxManage().post(Event.SEND_REDBAG, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 单个红包领取详情
    public void getRedBagDetails(String appSessionId, String submitTime, String sign, String redbagId) {
        Api.getInstance().service.getRedBagDetails(appSessionId, submitTime, sign, redbagId)
                .map(new ServerResultFunc<RedBagDetail>())
                .onErrorResumeNext(new ApiExceptionFunc<RedBagDetail>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<RedBagDetail>() {
                    @Override
                    public void onNext(RedBagDetail data) {
                        new RxManage().post(Event.GET_REDBAG_DETAILS, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 已接收的红包列表
    public void findRedbagReceiveList(String appSessionId, String submitTime, String sign, String coinType) {
        Api.getInstance().service.findRedbagReceiveList(appSessionId, submitTime, sign, coinType)
                .map(new ServerResultFunc<RedBagFind1>())
                .onErrorResumeNext(new ApiExceptionFunc<RedBagFind1>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<RedBagFind1>() {
                    @Override
                    public void onNext(RedBagFind1 data) {
                        new RxManage().post(Event.FIND_REDBAG_RECEIVE_LIST, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 已发送的红包列表
    public void findRedbagList(String appSessionId, String submitTime, String sign, String coinType) {
        Api.getInstance().service.findRedbagList(appSessionId, submitTime, sign, coinType)
                .map(new ServerResultFunc<RedBagFind2>())
                .onErrorResumeNext(new ApiExceptionFunc<RedBagFind2>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<RedBagFind2>() {
                    @Override
                    public void onNext(RedBagFind2 data) {
                        new RxManage().post(Event.FIND_REDBAG_LIST, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 上传Telegram用户信息
    public void addTeleInfo(String appSessionId, String submitTime, String sign, String openId, String nickName, String portraitImgUrl) {
        Api.getInstance().service.addTeleInfo(appSessionId, submitTime, sign, openId, nickName, portraitImgUrl)
                .map(new ServerResultFunc<Object>())
                .onErrorResumeNext(new ApiExceptionFunc<Object>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        new RxManage().post(Event.ADD_TELE_INFO, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 币种列表信息
    public void getWalletList(int where, String appSessionId, String submitTime, String sign) {
        Api.getInstance().service.getWalletList(appSessionId, submitTime, sign)
                .map(new ServerResultFunc<List<Wallet>>())
                .onErrorResumeNext(new ApiExceptionFunc<List<Wallet>>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<List<Wallet>>() {
                    @Override
                    public void onNext(List<Wallet> data) {
                        if (where == 0)
                            new RxManage().post(Event.GET_WALLET_LIST, data);
                        else
                            new RxManage().post(Event.GET_WALLET_LIST1, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 查询单个币种余额
    public void getUserBookNew(int where, String appSessionId, String submitTime, String sign, String coinType) {
        Api.getInstance().service.getUserBookNew(appSessionId, submitTime, sign, coinType)
                .map(new ServerResultFunc<Book>())
                .onErrorResumeNext(new ApiExceptionFunc<Book>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Book>() {
                    @Override
                    public void onNext(Book data) {
                        switch (where) {
                            case 0:
                                new RxManage().post(Event.GET_COIN_INFO, data);
                                break;
                            case 1:
                                new RxManage().post(Event.GET_COIN_INFO1, data);
                                break;
                            case 2:
                                new RxManage().post(Event.GET_COIN_INFO2, data);
                                break;
                            case 3:
                                new RxManage().post(Event.GET_COIN_INFO3, data);
                                break;
                            case 4:
                                new RxManage().post(Event.GET_COIN_INFO4, data);
                                break;
                            case 5:
                                new RxManage().post(Event.GET_COIN_INFO5, data);
                                break;
                        }
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 根据telegramId获取地址
    public void findWalletListByOpenId(String appSessionId, String submitTime, String sign, String openId, String mobile) {
        Api.getInstance().service.findWalletListByOpenId(appSessionId, submitTime, sign, openId, mobile)
                .map(new ServerResultFunc<List<ChatTransfer>>())
                .onErrorResumeNext(new ApiExceptionFunc<List<ChatTransfer>>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<List<ChatTransfer>>() {
                    @Override
                    public void onNext(List<ChatTransfer> data) {
                        new RxManage().post(Event.CHAT_TRANSFER, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 是否签订期权协议
    public void checkFotaUserExit(String appSessionId, String submitTime, String sign) {
        Api.getInstance().service.checkFotaUserExit(appSessionId, submitTime, sign)
                .map(new ServerResultFunc<Object>())
                .onErrorResumeNext(new ApiExceptionFunc<Object>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        new RxManage().post(Event.CHECK_FOTA_USER_EXIT, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 授权登录期权
    public void fotaLogin(String appSessionId, String submitTime, String sign) {
        Api.getInstance().service.fotaLogin(appSessionId, submitTime, sign)
                .map(new ServerResultFunc<FotaLogin>())
                .onErrorResumeNext(new ApiExceptionFunc<FotaLogin>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<FotaLogin>() {
                    @Override
                    public void onNext(FotaLogin data) {
                        new RxManage().post(Event.FOTA_AUTH_LOGIN, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 查询期权余额
    public void getFotaBalance(String appSessionId, String submitTime, String sign) {
        Api.getInstance().service.getFotaBalance(appSessionId, submitTime, sign)
                .map(new ServerResultFunc<FotaBalance>())
                .onErrorResumeNext(new ApiExceptionFunc<FotaBalance>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<FotaBalance>() {
                    @Override
                    public void onNext(FotaBalance data) {
                        new RxManage().post(Event.GET_FOTA_BALANCE, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 期权充值提现
    public void fotaTransfer(String appSessionId, String submitTime, String sign, String transAmount, int transType, String payPassword) {
        Api.getInstance().service.fotaTransfer(appSessionId, submitTime, sign, transAmount, transType, payPassword)
                .map(new ServerResultFunc<Object>())
                .onErrorResumeNext(new ApiExceptionFunc<Object>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        new RxManage().post(Event.FOTA_TRANSFER, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 期权交易记录
    public void fotaFindOrderPage(String appSessionId, String submitTime, String sign, int pageNo, int pageSize) {
        Api.getInstance().service.fotaFindOrderPage(appSessionId, submitTime, sign, pageNo, pageSize)
                .map(new ServerResultFunc<DigitalRecord>())
                .onErrorResumeNext(new ApiExceptionFunc<DigitalRecord>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<DigitalRecord>() {
                    @Override
                    public void onNext(DigitalRecord data) {
                        new RxManage().post(Event.FOTA_FIND_ORDER_PAGE, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 获取代理
    public void getVpnList(String submitTime, String sign) {
        Api.getInstance().service.getVpnList(submitTime, sign)
                .map(new ServerResultFunc<List<VpnInfo>>())
                .onErrorResumeNext(new ApiExceptionFunc<List<VpnInfo>>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<List<VpnInfo>>() {
                    @Override
                    public void onNext(List<VpnInfo> data) {
                        new RxManage().post(Event.GET_VPN_LIST, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 查询沐新通讯录
    public void findAllBelongsUser(int where) {
        Api.getInstance().service.findAllBelongsUser()
                .map(new ServerResultFunc<List<MXUserInfo>>())
                .onErrorResumeNext(new ApiExceptionFunc<List<MXUserInfo>>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<List<MXUserInfo>>() {
                    @Override
                    public void onNext(List<MXUserInfo> data) {
                        switch (where) {
                            case 0:
                                new RxManage().post(Event.GET_MX_USER_LIST, data);
                                break;
                            case 1:
                                new RxManage().post(Event.GET_MX_USER_LIST1, data);
                                break;
                            case 2:
                                new RxManage().post(Event.GET_MX_USER_LIST2, data);
                                break;
                        }
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 通过手机号查询openid
    public void findUserOpenId(String phoneNumber) {
        Api.getInstance().service.findUserOpenId(phoneNumber)
                .map(new ServerResultFunc<MXUserInfo>())
                .onErrorResumeNext(new ApiExceptionFunc<MXUserInfo>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<MXUserInfo>() {
                    @Override
                    public void onNext(MXUserInfo data) {
                        new RxManage().post(Event.FIND_MX_USER_OPENID, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

    // 判断是否是沐新用户
    public void checkUserBelong(String phoneNumber) {
        Api.getInstance().service.checkUserBelong(phoneNumber)
                .map(new ServerResultFunc<Boolean>())
                .onErrorResumeNext(new ApiExceptionFunc<Boolean>())
                .subscribeOn(Schedulers.io())
                .subscribe(new TSCSubscriber<Boolean>() {
                    @Override
                    public void onNext(Boolean data) {
                        new RxManage().post(Event.CHECK_USER_BELONG, data);
                        Log.d(TAG, "result--->success: " + data);
                    }
                });
    }

}
