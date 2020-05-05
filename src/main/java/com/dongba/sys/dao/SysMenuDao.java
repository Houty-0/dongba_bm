package com.dongba.sys.dao;

import com.dongba.common.vo.Node;
import com.dongba.sys.entity.SysMenu;
import com.dongba.sys.vo.SysUserMenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuDao {

    /**
     * 获取菜单列表
     * @return
     */
    List<SysMenu> findObjects();

    /**
     * 获取菜单的子菜单数
     * @param id
     * @return
     */
    int getChildCount(Integer id);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    int deleteObject(Integer id);

    /**
     * 查询菜单列表树
     * @return
     */
    List<Node> findZtreeMenuNodes();

    /**
     * 新增菜单
     * @param entity
     * @return
     */
    int insertObject(SysMenu entity);

    /**
     * 修改菜单
     * @param entity
     * @return
     */
    int updateObject(SysMenu entity);

    List<String> findPermissions(@Param("menuIds") Integer[] menuIds);

    List<SysUserMenuVo> findUserMenus(Integer[] menuIds);

}
