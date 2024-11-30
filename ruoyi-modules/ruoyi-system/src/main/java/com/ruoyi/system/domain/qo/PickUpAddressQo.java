package com.ruoyi.system.domain.qo;

import com.ruoyi.common.core.web.domain.PageQueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 取货地址 Qo 查询对象
 *
 * @description 版权所有 万事屋
 * @author chenzhongxin
 * @date 2024/11/29 11:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PickUpAddressQo extends PageQueryEntity {

    /**
    * 取货地址
    */
    private String addressName;

    /**
     *  是否有效 server_address_status  1开启  0关闭
     * */
    private Integer canValid;

}