package com.ebei.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/9/29.
 * 红包
 */

@Data
public class RedBag1 implements Serializable {

    private int coinType;

    private String createDate;

    private String createDateString;

    private double haveMoney;

    private int haveNumber;

    private int id;

    private String loseDate;

    private String modifyDate;

    private String modifyDateSring;

    private int number;

    private String receiveStatus;

    private String redbagCountMoney;

    private String redbagSource;

    private String redbagSum;

    private String redbagTotalNumber;

    private double restOfMoney;

    private int restOfNumber;

    private int status;

    private double totalMoney;

    private int type;

    private int userId;

    private String nickName;

    private String openId;

    private String portraitImgUrl;

}
