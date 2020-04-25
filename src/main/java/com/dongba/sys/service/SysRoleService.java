package com.dongba.sys.service;

import com.dongba.common.vo.PageObject;
import com.dongba.sys.entity.SysRole;

public interface SysRoleService {

    PageObject<SysRole> findPageObjects(String name,Integer pageCurrent);
}
