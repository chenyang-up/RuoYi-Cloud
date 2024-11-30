package com.ruoyi.common.core.web.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity基类V1
 * 原来的BaseEntity在我后续业务上有不需要的字段remark,params,searchValue
 * 并且后续使用的是mybtis-plus提供的方法去进行数据库操作，所以这里去掉了这三个字段
 * 
 * @author chenzhongxin
 * @date 2024/11/30
 */
@Data
public class BaseV1Entity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
