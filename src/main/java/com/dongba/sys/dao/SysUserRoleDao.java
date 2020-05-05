package com.dongba.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleDao {

    int deleteObjectsByRoleId(Integer roleId);

    int insertObjects(@Param("userId")Integer userId, @Param("roleIds")Integer[] roleIds);

    int deleteObjectsByUserId(Integer userId);

    List<Integer> findRoleIdsByUserId(Integer userId);
}
