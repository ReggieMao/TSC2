package com.ebei.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2019/1/29.
 * 聊天转账
 */

@Data
public class ChatTransfer implements Serializable {

    private String userWalletType;

    private double balance;

    private String modifyDate;

    private String userWalletAddress;

    private String coinName;

    private String hexAddress;

    private int id;

    private int userId;

    private String createDate;

    private String status;

}
