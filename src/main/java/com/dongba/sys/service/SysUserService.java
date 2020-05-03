package com.dongba.sys.service;

import com.dongba.common.vo.PageObject;
import com.dongba.sys.entity.SysUser;
import com.dongba.sys.vo.SysUserDeptVo;

import java.util.Map;

public interface SysUserService {

    public PageObject<SysUserDeptVo> findPageObjects(String username,Integer pageCurrent);

    public int validById(Integer id,Integer valid,String modifiedUser);

    public int saveObject(SysUser entity, Integer[]roleIds);

    Map<String,Object> findObjectById(Integer userId);

    int updateObject(SysUser entity,Integer[] roleIds);

    public int findUserByUserName(String username);
}
