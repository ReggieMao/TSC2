package com.ebei.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/9/29.
 * 代理连接信息
 */

@Data
public class VpnInfo implements Serializable {

    private String address;

    private String port;

    private String secret;

}
