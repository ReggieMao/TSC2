package com.ebei.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2019/1/21.
 * 红包
 */

@Data
public class RecieveBagIn implements Serializable {

    private String createDate;

    private String createDateString;

    private int haveMoney;

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

    private String type;

    private int userId;

    private double amount;

    private int redbagId;

    private boolean isMaxReceiver;

    private String loginAccount;

    private String maxRedbagReceive;

    private int newRegister;

    private String redbagReceiveCount;

    private String redbagReceiveCountMoney;

    private String redbagreceivePerson;

    private String nickName;

    private String openId;

    private String portraitImgUrl;

}
