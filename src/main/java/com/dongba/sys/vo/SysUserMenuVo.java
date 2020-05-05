package com.dongba.sys.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysUserMenuVo implements Serializable {

    private static final long serialVersionUID = 8365568381841385235L;
    private Integer id;
    private String name;
    private String url;
    private List<SysUserMenuVo> childs;
}
