package com.dongba.sys.service;

import com.dongba.common.vo.PageObject;
import com.dongba.sys.entity.SysLog;

public interface SysLogService {

    PageObject<SysLog> findPageObjects(String username, Integer pageCurrent);

    int deleteObjects(Integer... ids);

    void saveObject(SysLog entity);
}
