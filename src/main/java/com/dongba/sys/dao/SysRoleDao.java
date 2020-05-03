package com.dongba.sys.dao;

import com.dongba.common.vo.CheckBox;
import com.dongba.sys.entity.SysRole;
import com.dongba.sys.vo.SysRoleMenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleDao {

    public int getRowCount(@Param("name")String name);

    public List<SysRole> findPageObjects(@Param("name")String name,
                                  @Param("startIndex")Integer startIndex,
                                  @Param("pageSize")Integer pageSize);

    public int deleteObject(Integer id);

    public int insertObject(SysRole entity);

    public SysRoleMenuVo findObjectById(Integer id);

    public int updateObject(SysRole entity);

    public List<CheckBox> findObjects();

}
