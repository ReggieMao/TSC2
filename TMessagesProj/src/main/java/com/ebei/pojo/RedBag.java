package com.ebei.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/9/29.
 * 红包
 */

@Data
public class RedBag implements Serializable {

    private String redbagId;

    private String receiveAmout;

}
