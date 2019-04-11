package com.ebei.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/9/29.
 * T值
 */

@Data
public class TReceive implements Serializable {

    private long userId;

    private int total_t_value;

    private int rev_tsc;

    private int receive;

    private String belong_date;

}
