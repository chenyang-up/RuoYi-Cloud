package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 收货地址管理 Vo
 *
 * @description 版权所有 万事屋
 * @author chenzhongxin
 * @date 2024/12/05
 */
@Data
public class DeliveryAddressVo {

    private Long id;

    /**
     * 收货地址code
     */
    private String code;

    /**
     * 这个地址绑定的用户账号
     * */
    private String userName;

    /**
     * 这个地址绑定的用户账号(账号文本)
     * */
    private String userNameStr;


    /**
     * 收货用户名称
     * */
    private String deliveryName;

    /**
     * 收货地址-电话号码
     * */
    private String phoneNum;

    /**
     * 收件地址Code (和地址管理功能关联)
     * */
    private String systemAddressCode;

    /**
     * 收件地址fullName
     * */
    private String systemAddressFullName;

    /**
     * 详细地址(文本输入)
     * */
    private String fullAddress;

    /**
     * 是否是默认地址 0非默认 1默认
     * */
    private String isDefaultAddress;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}