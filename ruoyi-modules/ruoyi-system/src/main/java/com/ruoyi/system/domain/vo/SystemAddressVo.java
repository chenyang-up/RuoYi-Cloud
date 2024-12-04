package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.web.domain.BaseV1Entity;
import com.ruoyi.system.domain.dao.SystemAddress;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 地址管理 Vo
 *
 * @description 版权所有 万事屋
 * @author chenzhongxin
 * @date 2024/12/02 11:26
 */
@Data
public class SystemAddressVo {

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
     * 父级地址名称
     */
    private String parentName;

    /**
     * 地址状态
     * 使用字典
     * */
    private String addressStatus;

    /**
     * 子节点列表 (非数据库字段)
     */
    private List<SystemAddressVo> children;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}