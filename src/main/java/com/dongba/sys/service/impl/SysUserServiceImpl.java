package com.dongba.sys.service.impl;

import com.dongba.common.annotation.RequiredLog;
import com.dongba.common.config.PaginationProperties;
import com.dongba.common.util.Assert;
import com.dongba.common.vo.PageObject;
import com.dongba.sys.dao.SysUserDao;
import com.dongba.sys.dao.SysUserRoleDao;
import com.dongba.sys.entity.SysUser;
import com.dongba.sys.service.SysUserService;
import com.dongba.sys.vo.SysUserDeptVo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private PaginationProperties paginationProperties;

    @Override
    public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
        //1.参数校验
        Assert.isArgumentValid(pageCurrent==null||pageCurrent<1, "当前页码值不正确");
        //2.查询总记录数并校验
        int rowCount=sysUserDao.getRowCount(username);//sqlSession.commit(true)
        Assert.isServiceValid(rowCount==0, "没有对应的记录");
        //3.查询当前页用户记录信息
        Integer pageSize=paginationProperties.getPageSize();
        Integer startIndex=paginationProperties.getStartIndex(pageCurrent);
        List<SysUserDeptVo> records= sysUserDao.findPageObjects(username, startIndex, pageSize);
        //4.封装结果并返回
        return new PageObject<>(pageCurrent,pageSize,rowCount,records);
    }

    @RequiredLog(operation = "禁用启用")
    @Override
    public int validById(Integer id, Integer valid, String modifiedUser) {
        //1.参数校验
        Assert.isArgumentValid(id==null||id<1, "id值不正确");
        Assert.isArgumentValid(valid!=1&&valid!=0,"状态值不正确");
        //2.执行更新并校验
        int rows=sysUserDao.validById(id, valid, modifiedUser);
        Assert.isServiceValid(rows==0, "记录可能已经不存在");
        //3.返回结果
        return rows;
    }

    @Override
    public int saveObject(SysUser entity, Integer[] roleIds) {
        //1.参数校验
        Assert.isNull(entity, "保存对象不能为空");
        Assert.isEmpty(entity.getUsername(), "用户名不能为空");
        Assert.isEmpty(entity.getPassword(), "密码不能为空");
        Assert.isArgumentValid(roleIds==null||roleIds.length==0, "必须为用户分配权限");

        int row = sysUserDao.findUserByUserName(entity.getUsername());
        Assert.isServiceValid(row>0,"用户名已存在");

        //2.对密码进行加密
        String password=entity.getPassword();
        String salt= UUID.randomUUID().toString();
        //String hashPassword=DigestUtils.md5DigestAsHex((password+salt).getBytes());//spring
        SimpleHash sh=new SimpleHash(
                "MD5",//algorithmName 加密算法
                password,//source 需要加密的密码
                salt, //salt 加密盐
                1);//hashIterations 加密次数
        entity.setPassword(sh.toHex());//将密码加密结果转换为16进制并存储到entity对象
        entity.setSalt(salt);
        //3.保存用户自身信息
        int rows=sysUserDao.insertObject(entity);
        //4.保存用户与角色的关系数据
        sysUserRoleDao.insertObjects(entity.getId(), roleIds);
        //5.返回业务结果
        return rows;
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> findObjectById(Integer userId) {
        Assert.isArgumentValid(userId==null||userId<1,"id值不正确" );
        //2.查询用户以及用户对应的部门信息
        SysUserDeptVo user=sysUserDao.findObjectById(userId);
        Assert.isNull(user, "记录可能已经不存在");
        //3.查询用户对应的角色id
        List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(userId);
        //4.对查询结果进行封装
        Map<String,Object> map=new HashMap<>();
        map.put("user", user);
        map.put("roleIds", roleIds);
        return map;
    }

    @CacheEvict(value = "userCache",key = "#entity.id")
    @Override
    public int updateObject(SysUser entity, Integer[] roleIds) {
        //1.参数校验
        Assert.isNull(entity, "保存对象不能为空");
        Assert.isEmpty(entity.getUsername(), "用户名不能为空");
        Assert.isArgumentValid(roleIds==null||roleIds.length==0, "必须为用户分配权限");

        //3.更新用户自身信息
        int rows=sysUserDao.updateObject(entity);//commit
        //4.更新用户与角色的关系数据
        sysUserRoleDao.deleteObjectsByUserId(entity.getId());
        sysUserRoleDao.insertObjects(entity.getId(), roleIds);
        //5.返回业务结果
        return rows;
    }

    @Override
    public int findUserByUserName(String username) {
        int row = sysUserDao.findUserByUserName(username);
        return row;
    }


}
