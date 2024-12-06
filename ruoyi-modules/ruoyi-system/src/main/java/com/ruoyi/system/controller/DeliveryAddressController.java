package com.ruoyi.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.domain.po.DeliveryAddressPo;
import com.ruoyi.system.domain.qo.DeliveryAddressQo;
import com.ruoyi.system.domain.vo.DeliveryAddressVo;
import com.ruoyi.system.service.DeliveryAddressService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 收货地址 信息操作处理
 *
 * @author chenzhongxin
 * @date 2024/12/06
 */
@RestController
@RequestMapping("/delivery_address")
public class DeliveryAddressController extends BaseController {

    @Resource
    private DeliveryAddressService deliveryAddressService;

    /**
     * 收货地址分页查询
     */
    @RequiresPermissions("server:delivery_address:list")
    @GetMapping("/getPageList")
    public AjaxResult page(DeliveryAddressQo qo) {
        Page<DeliveryAddressVo> pageList = deliveryAddressService.selectPageList(qo);
        return success(pageList);
    }

    /**
     * 收货地址列表查询
     */
    @RequiresPermissions("server:delivery_address:list")
    @GetMapping("/getList")
    public AjaxResult getList(DeliveryAddressQo qo) {
        List<DeliveryAddressVo> pageList = deliveryAddressService.selectListByQo(qo);
        return success(pageList);
    }

    /**
     * 获取收货地址信息
     */
    @GetMapping("/getInfoByCode/{code}")
    public AjaxResult getInfo(@PathVariable String code) {
        return success(deliveryAddressService.selectInfoByCode(code));
    }

    /**
     * 新增收货地址
     */
    @RequiresPermissions("server:delivery_address:add")
    @Log(title = "收货地址管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DeliveryAddressPo po) {
        deliveryAddressService.insert(po);
        return success();
    }

    /**
     * 修改取货地址
     */
    @RequiresPermissions("server:delivery_address:edit")
    @Log(title = "收货地址管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DeliveryAddressPo po) {
        deliveryAddressService.update(po);
        return success();
    }

    /**
     * 删除取货地址
     */
    @RequiresPermissions("server:delivery_address:remove")
    @Log(title = "收货地址管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{code}")
    public AjaxResult delete(@PathVariable String code) {
        if (StringUtils.isBlank(code)) {
            return error("code不能为空!");
        }
        deliveryAddressService.deleteByCode(code);
        return success();
    }

}
