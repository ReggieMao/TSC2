package com.ebei.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by MaoLJ on 2018/9/29.
 * 本地联系人
 */

@Data
public class LocalContact implements Serializable {

    private int id;

    private String name;

    private String phone;

    private String email;

    private String address;

    private String organization;

}
