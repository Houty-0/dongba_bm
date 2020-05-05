package com.dongba.common.web;

import com.dongba.common.exception.ServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

public class TimeAccessInterceptor implements HandlerInterceptor {

    /**
     * 此方法会在后端控制方法执行之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 1.定义允许访问的时间范围
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);// 秒
        long start = calendar.getTimeInMillis();// 起始访问时间
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        long end = calendar.getTimeInMillis();
        // 2.获取当前时间进行业务判定
        long currentTime = System.currentTimeMillis();
        if (currentTime < start || currentTime > end)
            throw new ServiceException("请在9~23时间段访问");
        return true;
    }
}
