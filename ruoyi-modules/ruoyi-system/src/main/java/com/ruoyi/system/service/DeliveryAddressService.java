package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.dao.DeliveryAddress;
import com.ruoyi.system.domain.po.DeliveryAddressPo;
import com.ruoyi.system.domain.qo.DeliveryAddressQo;
import com.ruoyi.system.domain.vo.DeliveryAddressVo;

import java.util.List;

/**
 * 收货地址管理 Service接口
 *
 * @author chenzhongxin
 * @description 版权所有 万事屋
 * @date 2024/12/05 10:41
 */

public interface DeliveryAddressService {

    /**
     * 后台管理系统的分页查询
     *
     * @author chenzhongxin
     * @date 2024/12/05
     */
    Page<DeliveryAddressVo> selectPageList(DeliveryAddressQo qo);

    /**
     * 通过查询条件去获取地址列表
     *
     * @author chenzhongxin
     * @date 2024/12/05
     */
    List<DeliveryAddressVo> selectListByQo(DeliveryAddressQo qo);

    /**
     * 通过code获取到地址详情
     *
     * @author chenzhongxin
     * @date 2024/12/05 01:48
     */
    DeliveryAddressVo selectInfoByCode(String code);

    /**
     * 通过code删除地址数据
     *
     * @author chenzhongxin
     * @date 2024/12/05 01:58
     */
    void deleteByCode(String code);

    int deleteByPrimaryKey(Long id);

    int insert(DeliveryAddressPo po);

    int update(DeliveryAddressPo po);

    int insertSelective(DeliveryAddress record);

    DeliveryAddress selectByPrimaryKey(Long id);
}
