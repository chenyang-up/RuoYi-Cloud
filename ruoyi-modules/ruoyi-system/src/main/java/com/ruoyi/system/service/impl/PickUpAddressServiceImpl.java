package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.bean.BeanV1Utils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.domain.dao.PickUpAddress;
import com.ruoyi.system.domain.po.PickUpAddressPo;
import com.ruoyi.system.domain.qo.PickUpAddressQo;
import com.ruoyi.system.domain.vo.PickUpAddressVo;
import com.ruoyi.system.mapper.PickUpAddressMapper;
import com.ruoyi.system.service.IPickUpAddressService;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 取货地址 服务层实现
 *
 * @author chenzhongxin
 * @date 2024/11/30
 */

@Service
public class PickUpAddressServiceImpl implements IPickUpAddressService {

    @Resource
    private PickUpAddressMapper pickUpAddressMapper;

    /**
     * 词典Server
     * */
    @Resource
    private ISysDictDataService sysDictDataService;

    @Override
    public Page<PickUpAddressVo> selectPageList(PickUpAddressQo qo) {
        Page<PickUpAddress> pageList = pickUpAddressMapper.selectPageList(qo);
        Page<PickUpAddressVo> newPageList = BeanV1Utils.toBean(pageList, PickUpAddressVo.class);

        // 查询字典数据Map
        Map<String, String> dictDataMap = sysDictDataService.selectDictDataMap("server_address_status");

        newPageList.getRecords().forEach(pickUpAddressVo -> {
            String canValidStr = dictDataMap.getOrDefault(pickUpAddressVo.getCanValid().toString(), "");
            pickUpAddressVo.setCanValidText(canValidStr);
        });
        return newPageList;
    }

    @Override
    public void insert(PickUpAddressPo entity) {
        // 校验取货地址是否唯一
        PickUpAddress pickUpAddress = pickUpAddressMapper.selectOneByAddressName(entity.getAddressName());
        if (null != pickUpAddress) {
            throw new ServiceException("取货地址已存在!");
        }
        // 产生code
        String code = UUID.randomUUID().toString().replace("-", "");

        PickUpAddress daoEntity = BeanV1Utils.toBean(entity, PickUpAddress.class);
        daoEntity.setAddressCode(code);
        // 创建地址默认开启 使用词典server_address_status
        daoEntity.setCanValid(1);
        daoEntity.setCreateBy(SecurityUtils.getUsername());
        daoEntity.setCreateTime(new Date());
        pickUpAddressMapper.insertAddress(daoEntity);
    }

    @Override
    public void update(PickUpAddressPo entity) {
        PickUpAddress pickUpAddress = pickUpAddressMapper.selectOneByAddressName(entity.getAddressName());
        if (null != pickUpAddress && pickUpAddress.getAddressCode().equals(entity.getAddressCode())) {
            throw new ServiceException("取货地址已存在! 请重新修改.");
        }

        PickUpAddress daoEntity = BeanV1Utils.toBean(entity, PickUpAddress.class);
        daoEntity.setUpdateBy(SecurityUtils.getUsername());
        daoEntity.setUpdateTime(new Date());
        pickUpAddressMapper.updateAddressByCode(daoEntity);
    }

    @Override
    public void updateStatusByCode(String code, Integer canValid) {
        pickUpAddressMapper.updateStatusByCode(code, canValid);
    }


    @Override
    public void deleteByCode(String addressCode) {
        pickUpAddressMapper.deleteAddressByCode(addressCode);
    }
}
