package com.ruoyi.system.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 地址管理 Po
 *
 * @description 版权所有 万事屋
 * @author chenzhongxin
 * @date 2024/12/02 11:26
 */
@Data
public class SystemAddressPo{

    /**
     * id
     * */
    private Long id;

    /**
    * 地址code
    */
    private String code;

    /**
     * 地址类型
     * 地址类型 ("country", "province", "city", "county")
     * 国家,省份,市,县/区
     * */
    private String type;

    /**
     * 地址(简称)
     */
    private String shortName;

    /**
     * 地址(全称)
     */
    private String fullName;

    /**
     * 父级地址code
     */
    private String parentCode;

    /**
     * 地址状态
     * */
    private String addressStatus;

}