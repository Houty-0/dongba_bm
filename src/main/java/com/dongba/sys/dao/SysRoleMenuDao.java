package com.dongba.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuDao {

    int deleteObjectsByMenuId(Integer menuId);

    int deleteObjectsByRoleId(Integer roleId);

    int insertObjects(@Param("roleId")Integer roleId, @Param("menuIds")Integer[] menuIds);

    List<Integer> findMenuIdsByRoleId(Integer roleId);

}
