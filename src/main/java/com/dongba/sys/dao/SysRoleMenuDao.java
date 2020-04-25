package com.dongba.sys.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleMenuDao {

    int deleteObjectsByMenuId(Integer menuId);

}
