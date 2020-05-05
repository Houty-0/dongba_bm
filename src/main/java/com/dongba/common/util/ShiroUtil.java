package com.dongba.common.util;

import com.dongba.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    /**
     * 获取登录用户的Id
     * @return
     */
    public static Integer getUserId() {
        return getLoginUser().getId();
    }

    /**
     * 获取登录用户的用户名
     * @return
     */
    public static String getUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 获取登录用户对象
     * @return
     */
    public static SysUser getLoginUser() {

        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }
}
