package com.dongba.sys.service;

import com.dongba.common.vo.Node;
import com.dongba.sys.entity.SysMenu;

import java.util.List;

public interface SysMenuService {

    List<SysMenu> findObjects();

    int deleteObject(Integer id);

    List<Node> findZtreeMenuNodes();

    int saveObject(SysMenu entity);

    int updateObject(SysMenu entity);

}
