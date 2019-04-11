package com.ebei.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/7/23.
 * 期权交易记录
 */

@Data
public class DigitalRecord implements Serializable {

    private List<FotaRecord> item;

    private int total;

    private int pageNo;

    private int pageSize;

}
