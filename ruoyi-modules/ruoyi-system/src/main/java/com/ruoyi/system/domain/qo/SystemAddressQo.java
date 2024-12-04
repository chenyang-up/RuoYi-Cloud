package com.ruoyi.system.domain.qo;

import lombok.Data;

/**
 * 地址管理 Qo
 *
 * @description 版权所有 万事屋
 * @author chenzhongxin
 * @date 2024/12/02 11:26
 */
@Data
public class SystemAddressQo {

    /**
     * 地址(简称)
     */
    private String shortName;

    /**
     * 地址状态
     * */
    private String addressStatus;

}