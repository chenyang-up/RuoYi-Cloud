package com.ruoyi.system.domain.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.BaseV1Entity;
import lombok.*;

/**
 * 取货地址 DAO
 *
 * @description 版权所有 万事屋
 * @author chenzhongxinß
 * @date 2024/11/29 11:26
 */
@TableName("pick_up_address")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PickUpAddress extends BaseV1Entity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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

}