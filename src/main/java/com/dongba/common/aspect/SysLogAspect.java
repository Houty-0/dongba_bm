package com.dongba.common.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.dongba.common.annotation.RequiredLog;
import com.dongba.common.util.ShiroUtil;
import com.dongba.sys.entity.SysLog;
import com.dongba.sys.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class SysLogAspect {

    @Pointcut("@annotation(com.dongba.common.annotation.RequiredLog)")
    public void logPointCut(){}

    @Around("logPointCut()")
    public Object arounds(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        log.info("start{}",start);
        try {
            Object targetMethodResult = joinPoint.proceed(); // 调用下一个切面或目标方法
            long end = System.currentTimeMillis();
            log.info("end{}",end);
            log.info("方法执行时长:" + (end-start) + "mm");
            // 记录用户正常行为日志
            saveLog(joinPoint,end-start);
            return targetMethodResult;
        }catch (Throwable t){
            log.error("error{}",t.getMessage());
            throw t;
        }
    }

    @Autowired
    private SysLogService sysLogService;

    private void saveLog(ProceedingJoinPoint joinPoint, long time) throws Exception {
        // 1.获取行为日志(借助连接点对象)
        // 1.1 获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 1.2获取目标方法名
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        String targetClsMethod = targetClass.getName() +"." + ms.getName();
        // 1.3获取目标方法实际参数
        String params = Arrays.toString(joinPoint.getArgs());

        // 1.4获取操作名称(由此注解RequiredLog指定)
        // 1.4.1获取目标方法对象(Method)-->反射技术(应用的起点为Class对象)
        Method targetMethod = targetClass.getMethod(ms.getName(),ms.getParameterTypes());
        System.out.println("targetMethod=" + targetMethod);

        RequiredLog requiredLog = targetMethod.getAnnotation(RequiredLog.class);
        String operation = requiredLog.operation();

        // 1.5获取IP
        //String ip = IPUtils.getIpAddr();
        // 1.5获取客户端的IP地址
        // 1.5.1)获取到请求对象
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        // 1.5.2)从请求对象中获取IP
        String ip = ServletUtil.getClientIP(request);

        // 2.封装行为日志
        SysLog sysLog = new SysLog();

        sysLog.setUsername(ShiroUtil.getUsername());
        sysLog.setIp(ip);
        sysLog.setOperation(operation);
        sysLog.setMethod(targetClsMethod);
        sysLog.setParams(params);
        sysLog.setTime(time);
        sysLog.setCreatedTime(new Date());

        sysLogService.saveObject(sysLog);

    }

}
