package com.ebei.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/9/29.
 * 红包
 */

@Data
public class RedBagFind1 implements Serializable {

    private double totalBalance;

    private List<RecieveBagIn> redbagList;

}
