package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.dao.PickUpAddress;
import com.ruoyi.system.domain.qo.PickUpAddressQo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;


/**
 * 取货地址 数据层
 *
 * @author chenzhongxin
 * @date 2024/11/30
 */

@Mapper
public interface PickUpAddressMapper extends BaseMapper<PickUpAddress> {

    /**
     * 分页查询
     *
     * @param qo 查询参数
     */
    default Page<PickUpAddress> selectPageList(PickUpAddressQo qo) {
        Page<PickUpAddress> page = new Page<>(qo.getPageNo(), qo.getPageSize());
        // 查询条件（可选）
        QueryWrapper<PickUpAddress> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(qo.getAddressName())) {
            queryWrapper.like("address_name", qo.getAddressName());
        }
        if (null != qo.getCanValid()) {
            queryWrapper.eq("can_valid", qo.getCanValid());
        }

        // 执行分页查询
        return selectPage(page, queryWrapper);
    }

    /**
     * 查询取货地址信息 By addressName
     *
     * @param addressName 取货地址
     */
    default PickUpAddress selectOneByAddressName(String addressName) {
        return selectOne(new QueryWrapper<PickUpAddress>().eq("address_name", addressName));
    }

    /**
     * 添加取货地址信息
     *
     * @param entity 取货地址信息
     */
    default void insertAddress(PickUpAddress entity) {
        insert(entity);
    }

    /**
     * 更新取货地址信息
     *
     * @param entity 取货地址信息
     */
    default void updateAddressByCode(PickUpAddress entity) {
        update(entity, new QueryWrapper<PickUpAddress>().eq("address_code", entity.getAddressCode()));
    }

    /**
     * 更新取货地址信息
     *
     * @param code 取货地址code
     * @param canValid 取货地址状态
     */
    default void updateStatusByCode(String code, Integer canValid) {
        PickUpAddress pickUpAddress = selectOne(new QueryWrapper<PickUpAddress>().eq("address_code", code));
        pickUpAddress.setCanValid(canValid);
        update(pickUpAddress, new QueryWrapper<PickUpAddress>().eq("address_code", pickUpAddress.getAddressCode()));
    }


    /**
     * 删除取货地址信息
     *
     * @param addressCode 取货地址code
     */
    default void deleteAddressByCode(String addressCode) {
        delete(new QueryWrapper<PickUpAddress>().eq("address_code", addressCode));
    }

}