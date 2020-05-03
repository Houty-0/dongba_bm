package com.dongba.sys.service.impl;

import com.dongba.common.config.PaginationProperties;
import com.dongba.common.util.Assert;
import com.dongba.common.vo.CheckBox;
import com.dongba.common.vo.PageObject;
import com.dongba.sys.dao.SysRoleDao;
import com.dongba.sys.dao.SysRoleMenuDao;
import com.dongba.sys.dao.SysUserRoleDao;
import com.dongba.sys.entity.SysRole;
import com.dongba.sys.service.SysRoleService;
import com.dongba.sys.vo.SysRoleMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Autowired
    private PaginationProperties paginationProperties;

    @Override
    public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
        //1.参数校验
        Assert.isArgumentValid(pageCurrent==null||pageCurrent<1, "当前页码值不正确");
        //2.查询总记录数并校验
        int rowCount=sysRoleDao.getRowCount(name);
        Assert.isServiceValid(rowCount==0, "没有对应的记录");
        //3.查询当前页角色信息记录
        Integer pageSize=paginationProperties.getPageSize();
        Integer startIndex=paginationProperties.getStartIndex(pageCurrent);
        List<SysRole> records= sysRoleDao.findPageObjects(name, startIndex, pageSize);
        //4.封装查询结果
        return new PageObject<>(pageCurrent,pageSize,rowCount,records);
}

    @Transactional
    @Override
    public int deleteObject(Integer id) {
        //1.参数校验
        Assert.isArgumentValid(id==null||id<1, "id值无效");
        //2.删除菜单角色关系数据
        int i = sysRoleMenuDao.deleteObjectsByRoleId(id);
        //3.删除角色用户关系数据
        sysUserRoleDao.deleteObjectsByRoleId(id);
        //4.删除角色自身信息
        int rows=sysRoleDao.deleteObject(id);
        //5.返回结果
        return rows;
    }

    @Transactional
    @Override
    public int saveObject(SysRole entity, Integer[] menuIds) {
        //1.参数校验
        Assert.isArgumentValid(entity==null, "保存对象不能为空");
        Assert.isEmpty(entity.getName(), "角色名不能为空");
        //2.保存角色自身信息
        int rows=sysRoleDao.insertObject(entity);
        //3.保存角色菜单关系数据
        sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
        //4.返回结果
        return rows;
    }

    @Override
    public SysRoleMenuVo findObjectById(Integer id) {
        //1.参数校验
        Assert.isArgumentValid(id==null||id<1, "id值无效");
        //2.查询角色自身信息
        SysRoleMenuVo rm=sysRoleDao.findObjectById(id);
        Assert.isNull(rm,"此记录可能已经不存在");
        return rm;
    }

    @Override
    public int updateObject(SysRole entity, Integer[] menuIds) {
        //1.参数校验
        Assert.isArgumentValid(entity==null, "保存对象不能为空");
        Assert.isEmpty(entity.getName(), "角色名不能为空");
        //2.保存角色自身信息
        int rows=sysRoleDao.updateObject(entity);
        //3.保存角色菜单关系数据
        //3.1先删除原有关系数据
        sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
        //3.2添加新的关系数据
        sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
        //4.返回结果
        return rows;
    }

    @Override
    public List<CheckBox> findRoles() {
        return sysRoleDao.findObjects();
    }
}
