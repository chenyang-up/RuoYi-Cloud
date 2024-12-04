package com.ruoyi.system.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.domain.po.SystemAddressPo;
import com.ruoyi.system.domain.qo.SystemAddressQo;
import com.ruoyi.system.domain.vo.SystemAddressVo;
import com.ruoyi.system.service.ISystemAddressService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 地址管理 信息操作处理
 *
 * @author chenzhongxin
 * @date 2024/12/02
 */
@RestController
@RequestMapping("/sys_address")
public class SystemAddressController extends BaseController {

    @Resource
    private ISystemAddressService systemAddressService;

    /**
     * 地址列表查询
     */
    @RequiresPermissions("system:sys_address:list")
    @GetMapping("/getList")
    public AjaxResult getList(SystemAddressQo qo) {
        List<SystemAddressVo> voList = systemAddressService.selectList(qo);
        return success(voList);
    }

    /**
     * 地址列表查询(排出自生节点)
     */
    @RequiresPermissions("system:sys_address:list")
    @GetMapping("/getExcludeList/{code}")
    public AjaxResult getExcludeList(@PathVariable String code) {
        List<SystemAddressVo> voList = systemAddressService.selectExcludeList(code);
        return success(voList);
    }

    /**
     * 获取取货地址信息
     */
    @GetMapping("/getInfoByCode/{code}")
    public AjaxResult getInfo(@PathVariable String code) {
        return success(systemAddressService.getInfo(code));
    }

    /**
     * 新增取货地址
     */
    @RequiresPermissions("system:sys_address:add")
    @Log(title = "地址管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SystemAddressPo entity) {
        systemAddressService.insert(entity);
        return success();
    }

    /**
     * 修改地址
     */
    @RequiresPermissions("system:sys_address:edit")
    @Log(title = "地址管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SystemAddressPo entity) {
        systemAddressService.update(entity);
        return success();
    }

    /**
     * 修改地址状态
     */
    @RequiresPermissions("system:sys_address:edit")
    @Log(title = "地址管理", businessType = BusinessType.UPDATE)
    @PutMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody SystemAddressPo entity) {
        systemAddressService.updateStatusByCode(entity.getCode(), entity.getAddressStatus());
        return success();
    }

    /**
     * 删除地址
     */
    @RequiresPermissions("system:sys_address:remove")
    @Log(title = "取货地址管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{code}")
    public AjaxResult deleteBatch(@PathVariable String code) {
        if (StringUtils.isBlank(code)) {
            return error("code为空!");
        }
        systemAddressService.deleteByCode(code);
        return success();
    }

}
