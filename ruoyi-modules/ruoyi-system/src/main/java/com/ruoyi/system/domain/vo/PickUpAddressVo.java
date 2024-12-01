package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 取货地址 VO
 *
 * @description 版权所有 万事屋
 * @author chenzhongxin
 * @date 2024/11/29 11:26
 */
@Data
public class PickUpAddressVo {

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
    private String canValid;

    /**
     *  是否有效文本
     * */
    private String canValidText;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}