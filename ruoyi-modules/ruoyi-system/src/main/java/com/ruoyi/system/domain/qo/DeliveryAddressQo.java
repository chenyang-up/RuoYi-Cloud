package com.ruoyi.system.domain.qo;

import com.ruoyi.common.core.web.domain.PageQueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 收货地址管理 Qo
 *
 * @description 版权所有 万事屋
 * @author chenzhongxin
 * @date 2024/12/05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeliveryAddressQo extends PageQueryEntity {

    /**
     * 选择用户列表
     * */
    private List<String> userNames;

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