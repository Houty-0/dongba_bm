package com.dongba.sys.dao;

import com.dongba.sys.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleDao {

    int getRowCount(@Param("name")String name);

    List<SysRole> findPageObjects(@Param("name")String name,
                                  @Param("startIndex")Integer startIndex,
                                  @Param("pageSize")Integer pageSize);

}
