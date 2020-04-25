package com.dongba.sys.dao;

import com.dongba.sys.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysLogDao {

    int getRowCount(@Param("username") String username);

    List<SysLog> findPageObjects(
            @Param("username")String  username,
            @Param("startIndex")Integer startIndex,
            @Param("pageSize")Integer pageSize);

    int deleteObjects(@Param("ids")Integer... ids);

    int insertObject(SysLog entity);
}
