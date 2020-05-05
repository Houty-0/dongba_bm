package com.dongba.sys.controller;

import com.dongba.common.util.ShiroUtil;
import com.dongba.common.vo.JsonResult;
import com.dongba.sys.entity.SysUser;
import com.dongba.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/doFindPageObjects")
    public JsonResult doFindPageObjects(String username,Integer pageCurrent) {

        return new JsonResult(sysUserService.findPageObjects(username, pageCurrent));
    }

    @RequestMapping("/doValidById")
    public JsonResult doValidById(Integer id,Integer valid){
        sysUserService.validById(id,valid, ShiroUtil.getUsername());
        return new JsonResult("update ok");
    }

    @RequestMapping("/doSaveObject")
    public JsonResult doSaveObject(SysUser entity, Integer[] roleIds){
        sysUserService.saveObject(entity,roleIds);
        return new JsonResult("save ok");
    }

    @RequestMapping("/doFindObjectById")
    public JsonResult doFindObjectById(Integer id){
        Map<String,Object> map= sysUserService.findObjectById(id);
        return new JsonResult(map);
    }

    @RequestMapping("/doUpdateObject")
    public JsonResult doUpdateObject(SysUser entity,Integer[] roleIds){
        sysUserService.updateObject(entity,roleIds);
        return new JsonResult("update ok");
    }

    @PostMapping("/doDeleteObject")
    public JsonResult doDeleteObject(SysUser sysUser){
        sysUserService.deleteObjectById(sysUser);
        return new JsonResult("delete ok");
    }

    @RequestMapping("/doUpdatePassword")
    public JsonResult doUpdatePassword(String pwd, String newPwd, String cfgPwd) {
        sysUserService.updatePassword(pwd, newPwd, cfgPwd);
        return new JsonResult("update ok");
    }

    @RequestMapping("/doLogin")
    public JsonResult doLogin(String username,String password,Boolean isRememberMe){
        //1.获取Subject对象
        Subject subject= SecurityUtils.getSubject();
        //2.通过Subject提交用户信息,交给shiro框架进行认证操作
        //2.1对用户进行封装
        UsernamePasswordToken token= new UsernamePasswordToken(
                        username,//身份信息
                        password);//凭证信息
        if(isRememberMe){
            token.setRememberMe(true);
        }
        //2.2对用户信息进行身份认证
        subject.login(token);
        //分析:
        //1)token会传给shiro的SecurityManager
        //2)SecurityManager将token传递给认证管理器
        //3)认证管理器会将token传递给realm
        return new JsonResult("login ok");
    }
}
