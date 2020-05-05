package com.dongba.sys.service.impl;

import com.dongba.common.annotation.RequiredLog;
import com.dongba.common.exception.ServiceException;
import com.dongba.common.util.ShiroUtil;
import com.dongba.common.vo.Node;
import com.dongba.sys.dao.SysMenuDao;
import com.dongba.sys.dao.SysRoleMenuDao;
import com.dongba.sys.dao.SysUserRoleDao;
import com.dongba.sys.entity.SysMenu;
import com.dongba.sys.service.SysMenuService;
import com.dongba.sys.vo.SysUserMenuVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @RequiresPermissions("sys:menu:view")
    @Cacheable(value = "menuCache")
    @Override
    public List<SysMenu> findObjects() {
        List<SysMenu> list= sysMenuDao.findObjects();

        if(list==null||list.size()==0)
            throw new ServiceException("没有对应的菜单信息");

        return list;
    }

    @RequiresPermissions("sys:menu:delete")
    @CacheEvict(value = "menuCache",allEntries = true)
    @RequiredLog(operation = "删除菜单")
    @Transactional
    @Override
    public int deleteObject(Integer id) {
        //1.验证数据的合法性
        if(id==null||id<=0)
            throw new IllegalArgumentException("请先选择");
        //2.基于id进行子元素查询
        int count=sysMenuDao.getChildCount(id);
        if(count>0)
            throw new ServiceException("请先删除子菜单");
        //3.删除角色,菜单关系数据
        sysRoleMenuDao.deleteObjectsByMenuId(id);
        //4.删除菜单元素
        int rows=sysMenuDao.deleteObject(id);
        if(rows==0)
            throw new ServiceException("此菜单可能已经不存在");
        //5.返回结果
        return rows;
    }

    @Cacheable(value = "menuCache")
    @Override
    public List<Node> findZtreeMenuNodes() {
        return sysMenuDao.findZtreeMenuNodes();
    }

    @RequiresPermissions("sys:menu:add")
    @CacheEvict(value = "menuCache",allEntries = true)
    @RequiredLog(operation = "新增菜单")
    @Override
    public int saveObject(SysMenu entity) {
        //1.合法验证
        if(entity==null)
            throw new ServiceException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("菜单名不能为空");
        int rows;
        //2.保存数据
        try{
            entity.setCreatedUser(ShiroUtil.getUsername()).setModifiedUser(ShiroUtil.getUsername());
            rows=sysMenuDao.insertObject(entity);
        }catch(Exception e){
            e.printStackTrace();
            throw new ServiceException("保存失败");
        }
        //3.返回数据
        return rows;
    }

    @RequiresPermissions("sys:menu:update")
    @CacheEvict(value = "menuCache",allEntries = true)
    @RequiredLog(operation = "更新菜单")
    @Override
    public int updateObject(SysMenu entity) {
        //1.合法验证
        if(entity==null)
            throw new ServiceException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("菜单名不能为空");

        entity.setModifiedUser(ShiroUtil.getUsername());
        //2.更新数据
        int rows=sysMenuDao.updateObject(entity);
        if(rows==0)
            throw new ServiceException("记录可能已经不存在");
        //3.返回数据
        return rows;
    }

    @Override
    public List<SysUserMenuVo> findUserMenus(Integer userId) {
        // 1.获取用户对应的角色id并校验
        List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
        // 2.基于角色id获取对应菜单id并校验
        List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(new Integer[] {}));
        // 3.基于菜单id获取菜单信息
        List<SysUserMenuVo> list = sysMenuDao.findUserMenus(menuIds.toArray(new Integer[] {}));
        return list;
    }


}
