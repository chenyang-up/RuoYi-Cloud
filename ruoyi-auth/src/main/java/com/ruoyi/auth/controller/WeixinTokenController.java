package com.ruoyi.auth.controller;

import com.ruoyi.auth.form.RegisterBody;
import com.ruoyi.auth.service.SysLoginService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 微信界面提供的接口
 * 
 * @author chenzhongxin
 * @date 2024/11/28
 */
@RestController
@RequestMapping("/wx")
public class WeixinTokenController {

    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("/register")
    public R<?> register(@RequestBody SysUser sysUser)
    {
        // 用户注册
        return R.ok(sysLoginService.registerFromWeixin(sysUser));
    }
}
