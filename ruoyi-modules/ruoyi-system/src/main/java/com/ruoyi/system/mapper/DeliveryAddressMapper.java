package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.dao.DeliveryAddress;
import com.ruoyi.system.domain.qo.DeliveryAddressQo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收货地址管理 Mapper接口
 *
 * @author chenzhongxin
 * @description 版权所有 万事屋
 * @date 2024/12/05 10:51
 */

@Mapper
public interface DeliveryAddressMapper extends BaseMapper<DeliveryAddress> {

    /**
     * 通过code获取地址详情信息
     *
     * @author chenzhongxin
     * @date 2024/12/05 01:50
     */

    default DeliveryAddress selectOneByCode(String code) {
        return selectOne(new QueryWrapper<DeliveryAddress>().eq("code", code));
    }

    /**
     * 分页查询
     *
     * @param qo 查询参数
     */
    default Page<DeliveryAddress> selectPageList(DeliveryAddressQo qo) {
        Page<DeliveryAddress> page = new Page<>(qo.getPageNo(), qo.getPageSize());
        // 查询条件（可选）
        QueryWrapper<DeliveryAddress> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(qo.getDeliveryName())) {
            queryWrapper.like("delivery_name", qo.getDeliveryName());
        }
        if (StringUtils.isNotBlank(qo.getSystemAddressCode())) {
            queryWrapper.eq("system_address_code", qo.getSystemAddressCode());
        }
        if (StringUtils.isNotBlank(qo.getFullAddress())) {
            queryWrapper.eq("full_address", qo.getFullAddress());
        }
        if (StringUtils.isNotBlank(qo.getIsDefaultAddress())) {
            queryWrapper.eq("is_default_address", qo.getIsDefaultAddress());
        }
        if (CollectionUtils.isNotEmpty(qo.getUserNames())) {
            queryWrapper.in("user_name", qo.getUserNames());
        }
        queryWrapper.orderByDesc("create_time");
        // 执行分页查询
        return selectPage(page, queryWrapper);
    }

    /**
     * 通过查询条件获取地址列表
     *
     * @author chenzhongxin
     * @date 2024/12/05
     */
    default List<DeliveryAddress> selectList(DeliveryAddressQo qo) {
        QueryWrapper<DeliveryAddress> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(qo.getUserName())) {
            queryWrapper.eq("user_name", qo.getUserName());
        }
        if (StringUtils.isNotBlank(qo.getDeliveryName())) {
            queryWrapper.like("delivery_name", qo.getDeliveryName());
        }
        if (StringUtils.isNotBlank(qo.getSystemAddressCode())) {
            queryWrapper.eq("system_address_code", qo.getSystemAddressCode());
        }
        if (StringUtils.isNotBlank(qo.getFullAddress())) {
            queryWrapper.eq("full_address", qo.getFullAddress());
        }
        if (StringUtils.isNotBlank(qo.getIsDefaultAddress())) {
            queryWrapper.eq("is_default_address", qo.getIsDefaultAddress());
        }
        queryWrapper.orderByDesc("create_time");
        return selectList(queryWrapper);
    }

    int deleteByPrimaryKey(Long id);

    @Override
    int insert(DeliveryAddress record);

    int insertSelective(DeliveryAddress record);

    DeliveryAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeliveryAddress record);

    int updateByPrimaryKey(DeliveryAddress record);

    /**
     * 通过地址Code修改数据
     *
     * @author chenzhongxin
     * @date 2024/12/05
     */
    default int updateByCode(DeliveryAddress record) {
        return update(record, new QueryWrapper<DeliveryAddress>().eq("code", record.getCode()));
    }

    /**
     * 获取地址信息
     * 通过默认地址状态 和 地址绑定的用户账号
     *
     * @author chenzhonhxin
     * @date 2024/12/05
     */
    default DeliveryAddress selectOneByIsDefaultAddressAndUserName(DeliveryAddress info) {
        return selectOne(new QueryWrapper<DeliveryAddress>()
                .eq("is_default_address", info.getIsDefaultAddress())
                .eq("user_name", info.getUserName())
        );
    }

    /**
     * 更新地址的默认状态
     *
     * @author chenzhongxin
     * @date 2024/12/05
     */
    default void updateIsDefaultAddressByCode(DeliveryAddress info) {
        update(info, new QueryWrapper<DeliveryAddress>().eq("code", info.getCode()));
    }

    /**
     * 通过code删除地址信息
     *
     * @author chenzhongxin
     * @date 2024/12/05 01:59
     */
    default void deleteByCode(String code) {
        delete(new QueryWrapper<DeliveryAddress>().eq("code", code));
    }
}