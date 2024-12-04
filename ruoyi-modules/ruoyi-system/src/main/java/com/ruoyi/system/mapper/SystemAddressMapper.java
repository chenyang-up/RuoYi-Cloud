package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.dao.SystemAddress;
import com.ruoyi.system.domain.qo.SystemAddressQo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 地址管理 数据层
 *
 * @author chenzhongxin
 * @date 2024/12/02
 */
@Mapper
public interface SystemAddressMapper extends BaseMapper<SystemAddress> {

    /**
     * 获取地址列表
     *
     * @param qo 查询参数
     * */
    default List<SystemAddress> selectList(SystemAddressQo qo) {
        QueryWrapper<SystemAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(qo.getShortName()), "short_name", qo.getShortName())
                .eq(StringUtils.isNotBlank(qo.getAddressStatus()), "address_status", qo.getAddressStatus())
                .orderByDesc("create_time");
        return selectList(queryWrapper);
    }

    /**
     * 获取地址列表(排出传入节点)
     *
     * @author chenzhongxin
     * @date 2024/12/03
     * */
    default List<SystemAddress> selectExcludeList(String code) {
        QueryWrapper<SystemAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(StringUtils.isNotBlank(code), "code", code).orderByDesc("create_time");;
        return selectList(queryWrapper);
    }

    /**
     * 通过code获取地址信息
     * */
    default SystemAddress selectOneByCode(String addressCode) {
        return selectOne(new QueryWrapper<SystemAddress>()
                .eq("code", addressCode)
        );
    }

    /**
     * 通过地址层级类型和地址简称-获取地址信息
     *
     * @param shortName 地址简称
     * @param type 地址层级类型
     * */
    default SystemAddress selectOneByShortNameAndType(String shortName ,String type) {
        return selectOne(new QueryWrapper<SystemAddress>()
                .eq("short_name", shortName)
                .eq("type", type)
        );
    }

    /**
     * 查询地址信息 By shortName
     *
     * @param shortName 地址简称
     */
    default SystemAddress selectOneByShortName(String shortName) {
        return selectOne(new QueryWrapper<SystemAddress>().eq("short_name", shortName));
    }


    /**
     * 更新地址信息 By code
     *
     * @param entity 地址信息
     */
    default void updateAddressByCode(SystemAddress entity) {
        update(entity, new QueryWrapper<SystemAddress>().eq("code", entity.getCode()));
    }

    /**
     * 更新地址信息状态
     *
     * @param code 地址code
     * @param addressStatus 地址状态
     */
    default void updateStatusByCode(String code, String addressStatus) {
        SystemAddress systemAddress = selectOne(new QueryWrapper<SystemAddress>().eq("code", code));
        systemAddress.setAddressStatus(addressStatus);
        update(systemAddress, new QueryWrapper<SystemAddress>().eq("code", systemAddress.getCode()));
    }


    /**
     * 删除地址信息
     *
     * @param code 地址code
     */
    default void deleteAddressByCode(String code) {
        delete(new QueryWrapper<SystemAddress>().eq("code", code));
    }

    /**
     * 通过codes 获取地址列表信息
     * */
    default List<SystemAddress> selectListByCodes (List<String> codes) {
        return selectList(new QueryWrapper<SystemAddress>().in("code", codes).orderByDesc("create_time"));
    }

}