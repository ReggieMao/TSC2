package com.ebei.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/9/29.
 * 沐新用户信息
 */

@Data
public class MXUserInfo implements Serializable {

    private String loginAccount;

    private String openId;

    private String phoneNumber;

}
