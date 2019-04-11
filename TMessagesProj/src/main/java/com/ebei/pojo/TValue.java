package com.ebei.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/9/29.
 * T值
 */

@Data
public class TValue implements Serializable {

    private long userId;

    private int tType;

    private int chatNum;

    private int tValue;

    private String belongDate;

    private String createDate;

    private int id;

    private int status;

    private String updateDate;

}
