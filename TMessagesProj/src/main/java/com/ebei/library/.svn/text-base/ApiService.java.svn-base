package com.ebei.library;

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
import com.ebei.pojo.RxResult;
import com.ebei.pojo.TReceive;
import com.ebei.pojo.TValue;
import com.ebei.pojo.TValue1;
import com.ebei.pojo.VpnInfo;
import com.ebei.pojo.Wallet;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MaoLJ on 2018/7/18.
 * 接口服务类
 */

public interface ApiService {

    // 注册
    @GET("d-app/API/register")
    Observable<RxResult<Register>> register(@Query("bindMobile") String bindMobile, @Query("passWord") String passWord, @Query("payPassWord") String payPassWord, @Query("sign") String sign, @Query("submitTime") String submitTime, @Query("verifyCode") String verifyCode);

    // 发送验证码
    @GET("d-app/API/getAuthCode")
    Observable<RxResult<Object>> getAuthCode(@Query("mobile") String mobile, @Query("sign") String sign, @Query("submitTime") String submitTime);

    // 账号密码登录
    @GET("d-app/API/userLogin")
    Observable<RxResult<Login>> userLogin(@Query("loginAccount") String loginAccount, @Query("password") String password, @Query("sign") String sign, @Query("submitTime") String submitTime);

    // 修改密码（支付密码和登录密码）
    @GET("d-app/API/restPwd")
    Observable<RxResult<Object>> restPwd(@Query("loginAccount") String loginAccount, @Query("password") String password, @Query("confirmPassword") String confirmPassword, @Query("pwdType") int pwdType, @Query("verifyCode") String verifyCode, @Query("sign") String sign, @Query("submitTime") String submitTime);

    // 矿池总资产（昨日收益）
    @GET("d-app/inf-income/getIncome.json")
    Observable<RxResult<MineralAsset>> mineralAsset(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign);

    // 余额明细接口+详情
    @GET("d-app/coins/income/findBalanceList.json")
    Observable<RxResult<List<Balance>>> findBalanceList(@Query("sign") String sign, @Query("submitTime") String submitTime, @Query("appSessionId") String appSessionId, @Query("userWalletType") String userWalletType);

    // 转币
    @GET("d-app/coins/Ore/transferToOthers.do")
    Observable<RxResult<Object>> transferToOthers(@Query("sign") String sign, @Query("submitTime") String submitTime, @Query("appSessionId") String appSessionId, @Query("userWalletType") String userWalletType, @Query("outAddress") String outAddress, @Query("transferAmount") String transferAmount, @Query("payPassword") String payPassword);

    // 转入/出矿池
    @GET("d-app/ore/tranferToOre.do")
    Observable<RxResult<Object>> transferToOre(@Query("sign") String sign, @Query("submitTime") String submitTime, @Query("appSessionId") String appSessionId, @Query("transferAmount") String transferAmount, @Query("payPassword") String payPassword, @Query("operateType") int operateType);

    // app登录id保存
    @GET("d-app/API/savaAudieanceId")
    Observable<RxResult<Object>> saveId(@Query("sign") String sign, @Query("submitTime") String submitTime, @Query("appSessionId") String appSessionId, @Query("audienceId") String audienceId);

    // 公告列表
    @GET("notice/findnewsList.json")
    Observable<RxResult<Market>> findNewsList(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId);

    // 判断手机号是否已注册
    @GET("d-app/API/checkLoginAccount")
    Observable<RxResult<String>> checkLoginAccount(@Query("loginAccount") String loginAccount, @Query("submitTime") String submitTime, @Query("sign") String sign);

    // 矿池明细
    @GET("d-app/API/InfMid/findPooltransferList")
    Observable<RxResult<List<Miner>>> minerDetail(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign);

    // T值查询
    @GET("d-app/API/t/findRecord")
    Observable<RxResult<List<TValue>>> getTValue(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign);

    // 增加T值
    @GET("d-app/API/t/addRecord")
    Observable<RxResult<Object>> addTValue(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign, @Query("type") int type, @Query("chatNum") int chatNum);

    // T值对应领取的TSC查询
    @GET("d-app/API/t/findReceive")
    Observable<RxResult<TReceive>> findReceive(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign);

    // 月度累计T值
    @GET("d-app/API/t/findMonthRecord")
    Observable<RxResult<List<TValue1>>> findMonthRecord(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign);

    // 注册（新）
    @GET("d-app/API/userRegister")
    Observable<RxResult<Register>> newRegister(@Query("loginAccount") String loginAccount, @Query("payPassWord") String payPassWord, @Query("confirmPayPassWord") String confirmPayPassWord, @Query("sign") String sign, @Query("submitTime") String submitTime);

    // 验证码验证
    @GET("d-app/API/checkVerifyCode")
    Observable<RxResult<String>> checkVerifyCode(@Query("loginAccount") String loginAccount, @Query("verifyCode") String verifyCode, @Query("sign") String sign, @Query("submitTime") String submitTime);

    // 获取最新版本
    @GET("d-app/getLastestAppVersion.json")
    Observable<RxResult<AppVersion>> getLatestAppVersion(@Query("appSystem") String appSystem);

    // 领红包
    @GET("d-app/API/receiveRedbag.do")
    Observable<RxResult<RedBag2>> receiveRedbag(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign, @Query("redbagId") String redbagId);

    // 发红包
    @GET("d-app/API/sendRedbag.do")
    Observable<RxResult<RedBag>> sendRedbag(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign, @Query("type") int type, @Query("totalMoney") String totalMoney, @Query("number") String number, @Query("payPassword") String payPassword, @Query("coinType") String coinType);

    // 单个红包领取详情
    @GET("d-app/API/getRedBagDetails.json")
    Observable<RxResult<RedBagDetail>> getRedBagDetails(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign, @Query("redbagId") String redbagId);

    // 已接收的红包列表
    @GET("d-app/API/findRedbagReceiveList.json")
    Observable<RxResult<RedBagFind1>> findRedbagReceiveList(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign, @Query("coinType") String coinType);

    // 已发送的红包列表
    @GET("d-app/API/findRedbagList.json")
    Observable<RxResult<RedBagFind2>> findRedbagList(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign, @Query("coinType") String coinType);

    // 上传Telegram用户信息
    @GET("telegram/addTeleInfo")
    Observable<RxResult<Object>> addTeleInfo(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign, @Query("openId") String openId, @Query("nickName") String nickName, @Query("portraitImgUrl") String portraitImgUrl);

    // 币种列表信息
    @GET("d-app/coins/memUserWallet/findList.json")
    Observable<RxResult<List<Wallet>>> getWalletList(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign);

    // 查询单个币种余额
    @GET("d-app/coins/income/getUserBook")
    Observable<RxResult<Book>> getUserBookNew(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign, @Query("coinType") String coinType);

    // 根据telegramId获取地址
    @GET("d-app/coins/memUserWallet/findWalletListByOpenId.json")
    Observable<RxResult<List<ChatTransfer>>> findWalletListByOpenId(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign, @Query("openId") String openId, @Query("mobile") String mobile);

    // 是否签订期权协议
    @GET("d-app/fota/checkFotaUserExit")
    Observable<RxResult<Object>> checkFotaUserExit(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign);

    // 授权登录期权
    @GET("d-app/fota/authLogin")
    Observable<RxResult<FotaLogin>> fotaLogin(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign);

    // 查询期权余额
    @GET("d-app/fota/getBalance")
    Observable<RxResult<FotaBalance>> getFotaBalance(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign);

    // 期权充值提现
    @GET("d-app/fota/transfer")
    Observable<RxResult<Object>> fotaTransfer(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign, @Query("transAmount") String transAmount, @Query("transType") int transType, @Query("payPassword") String payPassword);

    // 期权交易记录
    @GET("d-app/fota/findOrderPage")
    Observable<RxResult<DigitalRecord>> fotaFindOrderPage(@Query("appSessionId") String appSessionId, @Query("submitTime") String submitTime, @Query("sign") String sign, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    // 获取代理
    @GET("d-app/vpn/findAllList")
    Observable<RxResult<List<VpnInfo>>> getVpnList(@Query("submitTime") String submitTime, @Query("sign") String sign);

    // 查询沐新通讯录
    @GET("auth/findAllBelongsUser")
    Observable<RxResult<List<MXUserInfo>>> findAllBelongsUser();

    // 通过手机号查询openid
    @GET("auth/findUserOpenId")
    Observable<RxResult<MXUserInfo>> findUserOpenId(@Query("phoneNumber") String phoneNumber);

    // 判断是否是沐新用户
    @GET("auth/checkUserBelong")
    Observable<RxResult<Boolean>> checkUserBelong(@Query("phoneNumber") String phoneNumber);

}


