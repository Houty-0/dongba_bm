package com.dongba.sys.controller;

import com.dongba.common.vo.JsonResult;
import com.dongba.sys.entity.SysUser;
import com.dongba.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    @ResponseBody
    public JsonResult doValidById(Integer id,Integer valid){
        sysUserService.validById(id,valid, "admin");//"admin"用户将来是登陆用户
        return new JsonResult("update ok");
    }

    @RequestMapping("/doSaveObject")
    @ResponseBody
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

    // TODO 修改密码
}
