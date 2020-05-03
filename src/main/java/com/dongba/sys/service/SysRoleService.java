package com.dongba.sys.service;

import com.dongba.common.vo.CheckBox;
import com.dongba.common.vo.PageObject;
import com.dongba.sys.entity.SysRole;
import com.dongba.sys.vo.SysRoleMenuVo;

import java.util.List;

public interface SysRoleService {

    public PageObject<SysRole> findPageObjects(String name,Integer pageCurrent);

    public int deleteObject(Integer id);

    public int saveObject(SysRole entity,Integer[]menuIds);

    public SysRoleMenuVo findObjectById(Integer id);

    public int updateObject(SysRole entity,Integer[] menuIds);

    public List<CheckBox> findRoles();
}
