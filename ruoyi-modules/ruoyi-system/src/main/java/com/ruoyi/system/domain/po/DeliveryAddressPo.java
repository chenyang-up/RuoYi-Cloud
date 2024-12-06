package com.ruoyi.system.domain.po;

import lombok.*;

/**
 * 收货地址管理 PO
 *
 * @description 版权所有 万事屋
 * @author chenzhongxin
 * @date 2024/12/05
 */
@Data
public class DeliveryAddressPo {

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
     * 详细地址(文本输入)
     * */
    private String fullAddress;

    /**
     * 是否是默认地址 0非默认 1默认
     * */
    private String isDefaultAddress;

}