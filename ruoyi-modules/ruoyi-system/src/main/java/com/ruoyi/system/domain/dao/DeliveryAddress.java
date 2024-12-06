package com.ruoyi.system.domain.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.BaseV1Entity;
import lombok.*;

/**
 * 收货地址管理 DAO
 *
 * @description 版权所有 万事屋
 * @author chenzhongxin
 * @date 2024/12/05
 */
@TableName("delivery_address")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddress extends BaseV1Entity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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