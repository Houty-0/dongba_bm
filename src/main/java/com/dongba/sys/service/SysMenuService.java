package com.dongba.sys.service;

import com.dongba.common.vo.Node;
import com.dongba.sys.entity.SysMenu;
import com.dongba.sys.vo.SysUserMenuVo;

import java.util.List;

public interface SysMenuService {

    List<SysMenu> findObjects();

    int deleteObject(Integer id);

    List<Node> findZtreeMenuNodes();

    int saveObject(SysMenu entity);

    int updateObject(SysMenu entity);

    /**
     * 6.查找用户界面菜单
     * @param userId
     * @return
     */
    List<SysUserMenuVo> findUserMenus(Integer userId);

}
