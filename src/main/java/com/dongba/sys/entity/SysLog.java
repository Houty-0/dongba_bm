package com.dongba.sys.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SysLog implements Serializable {

    private static final long serialVersionUID = -2529643151312029166L;

    private Integer id;

    //用户名
    private String username;

    //用户操作
    private String operation;

    //请求方法
    private String method;

    //请求参数
    private String params;

    //执行时长(毫秒)
    private Long time;

    //IP地址
    private String ip;

    //创建时间
    private Date createdTime;
}
