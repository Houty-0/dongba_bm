package com.dongba.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleDao {

    int deleteObjectsByRoleId(Integer roleId);

    int insertObjects(@Param("userId")Integer userId, @Param("roleIds")Integer[] roleIds);

    List<Integer> findRoleIdsByUserId(Integer id);

    int deleteObjectsByUserId(Integer userId);
}
