package com.dongba.sys.dao;

import com.dongba.sys.entity.SysUser;
import com.dongba.sys.vo.SysUserDeptVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserDao {

    public int getRowCount(@Param("username") String username);

    public List<SysUserDeptVo> findPageObjects(
            @Param("username")String  username,
            @Param("startIndex")Integer startIndex,
            @Param("pageSize")Integer pageSize);

    public int validById(
            @Param("id")Integer id,
            @Param("valid")Integer valid,
            @Param("modifiedUser")String modifiedUser);

    public int insertObject(SysUser entity);

    public SysUserDeptVo findObjectById(Integer id);

    public int updateObject(SysUser entity);

    public SysUser findUserByUserName(String username);

    public int getUserCountByDeptId(Integer id);

    public int checkUserNameIsExist(String username);

    public int deleteObjectById(SysUser sysUser);

    public int updatePassword(SysUser sysUser);
}
