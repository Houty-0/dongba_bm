package com.dongba.sys.service.impl;

import com.dongba.common.config.PaginationProperties;
import com.dongba.common.util.Assert;
import com.dongba.common.vo.PageObject;
import com.dongba.sys.dao.SysRoleDao;
import com.dongba.sys.entity.SysRole;
import com.dongba.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

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
}
