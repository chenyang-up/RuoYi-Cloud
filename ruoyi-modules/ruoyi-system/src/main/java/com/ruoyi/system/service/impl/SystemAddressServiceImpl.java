package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.bean.BeanV1Utils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.domain.dao.SystemAddress;
import com.ruoyi.system.domain.po.SystemAddressPo;
import com.ruoyi.system.domain.qo.SystemAddressQo;
import com.ruoyi.system.domain.vo.SystemAddressVo;
import com.ruoyi.system.mapper.SystemAddressMapper;
import com.ruoyi.system.service.ISystemAddressService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 地址管理 服务层实现
 *
 * @author chenzhongxin
 * @date 2024/12/02
 */

@Service
public class SystemAddressServiceImpl implements ISystemAddressService {

    @Resource
    private SystemAddressMapper systemAddressMapper;


    @Override
    public List<SystemAddressVo> selectList(SystemAddressQo qo) {
        List<SystemAddress> systemAddresses = systemAddressMapper.selectList(qo);
        List<SystemAddressVo> voList = BeanV1Utils.toBean(systemAddresses, SystemAddressVo.class);
        List<String> parentCodes = voList.stream().map(SystemAddressVo::getParentCode).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(parentCodes)) {
            List<SystemAddress> parentInfos = systemAddressMapper.selectListByCodes(parentCodes);
            Map<String, String> addressTypeMap = parentInfos.stream().collect(Collectors.toMap(SystemAddress::getCode, SystemAddress::getShortName));
            // 父级名称赋值
            voList.forEach(vo ->
                    vo.setParentName(addressTypeMap.getOrDefault(vo.getParentCode(), ""))
            );
        }
//        return buildTree(voList);
        return voList;
    }

    /**
     * 获取所有地址节点(除开传入节点code的节点)
     *
     * @author chenzhongxin
     * @date 2024/12/03
     * */
    @Override
    public List<SystemAddressVo> selectExcludeList(String code) {
        List<SystemAddress> systemAddresses = systemAddressMapper.selectExcludeList(code);
        List<SystemAddressVo> voList = BeanV1Utils.toBean(systemAddresses, SystemAddressVo.class);
        List<String> parentCodes = voList.stream().map(SystemAddressVo::getParentCode).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(parentCodes)) {
            List<SystemAddress> parentInfos = systemAddressMapper.selectListByCodes(parentCodes);
            Map<String, String> addressTypeMap = parentInfos.stream().collect(Collectors.toMap(SystemAddress::getCode, SystemAddress::getShortName));
            // 父级名称赋值
            voList.forEach(vo ->
                    vo.setParentName(addressTypeMap.getOrDefault(vo.getParentCode(), ""))
            );
        }
        return voList;
    }

    @Override
    public SystemAddressVo getInfo(String addressCode) {
        SystemAddress systemAddress = systemAddressMapper.selectOneByCode(addressCode);
        SystemAddressVo vo = BeanV1Utils.toBean(systemAddress, SystemAddressVo.class);

        // 父节点
        SystemAddress parentInfo = systemAddressMapper.selectOneByCode(vo.getParentCode());
        vo.setParentName(null == parentInfo ? "" : parentInfo.getShortName());
        return vo;
    }

    @Override
    public void insert(SystemAddressPo entity) {
        SystemAddress oldSystemAddress = systemAddressMapper.selectOneByShortNameAndType(entity.getShortName(), entity.getType());
        if (oldSystemAddress != null) {
            throw new ServiceException("地址已存在! 请重新添加.");
        }

        // 转换数据
        SystemAddress systemAddress = BeanV1Utils.toBean(entity, SystemAddress.class);
        systemAddress.setCode(UUID.randomUUID().toString().replace("-", ""));
        // 设置默认状态为1 启用
        systemAddress.setAddressStatus("1");
        systemAddress.setCreateBy(SecurityUtils.getUsername());
        systemAddress.setCreateTime(new Date());
        systemAddressMapper.insert(systemAddress);
    }

    @Override
    public void update(SystemAddressPo entity) {
        SystemAddress oldSystemAddress = systemAddressMapper.selectOneByShortNameAndType(entity.getShortName(), entity.getType());
        if (oldSystemAddress != null && !oldSystemAddress.getCode().equals(entity.getCode())) {
            throw new ServiceException("地址已存在! 请重新添加.");
        }
        // 转换数据
        SystemAddress systemAddress = BeanV1Utils.toBean(entity, SystemAddress.class);
        systemAddress.setUpdateBy(SecurityUtils.getUsername());
        systemAddress.setUpdateTime(new Date());
        systemAddressMapper.updateAddressByCode(systemAddress);
    }

    @Override
    public void updateStatusByCode(String code, String addressStatus) {
        systemAddressMapper.updateStatusByCode(code, addressStatus);
    }

    @Override
    public void deleteByCode(String addressCode) {
        systemAddressMapper.deleteAddressByCode(addressCode);
    }


    /**
     * 构建树形结构
     *
     * @param addressList 扁平化的地址列表
     * @return 树形结构的地址列表
     */
    public static List<SystemAddressVo> buildTree(List<SystemAddressVo> addressList) {
        if (addressList == null || addressList.isEmpty()) {
            return Collections.emptyList();
        }

        // 将地址列表转换为以 code 为键的 Map，便于快速查找
        Map<String, SystemAddressVo> addressMap = addressList.stream()
                .collect(Collectors.toMap(SystemAddressVo::getCode, address -> address));

        List<SystemAddressVo> roots = new ArrayList<>();

        for (SystemAddressVo address : addressList) {
            String parentCode = address.getParentCode();
            if (parentCode == null || parentCode.isEmpty() || "0".equals(parentCode)) {
                // 如果没有父级，说明是根节点
                roots.add(address);
            } else {
                // 找到父级节点，将当前节点加入父级的 children 列表
                SystemAddressVo parent = addressMap.get(parentCode);
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(address);
                }
            }
        }
        return roots;
    }
}
