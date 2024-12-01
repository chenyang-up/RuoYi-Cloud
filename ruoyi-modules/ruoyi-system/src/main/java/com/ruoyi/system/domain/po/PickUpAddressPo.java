package com.ruoyi.system.domain.po;

import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 取货地址 PO
 *
 * @description 版权所有 万事屋
 * @author chenzhongxin
 * @date 2024/11/29 11:26
 */
@Data
public class PickUpAddressPo{

    private Long id;

    /**
    * 取货地址code
    */
    private String addressCode;

    /**
    * 取货地址
    */
    private String addressName;

    /**
     *  是否有效 server_address_status
     * */
    private Integer canValid;

    /**
     * 取货地址code 列表
     * */
    private List<String> addressCodes;
}