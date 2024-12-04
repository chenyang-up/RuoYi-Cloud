package com.ruoyi.system.domain.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.BaseV1Entity;
import lombok.*;

/**
 * 地址管理 DAO
 *
 * @description 版权所有 万事屋
 * @author chenzhongxin
 * @date 2024/12/02 11:26
 */
@TableName("sys_address")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SystemAddress extends BaseV1Entity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 默认为0 代表顶级节点
     */
    private String parentCode = "0";

    /**
     * 地址状态 1开启 0关闭
     * */
    private String addressStatus;

}