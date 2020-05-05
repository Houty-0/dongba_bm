package com.dongba.sys.controller;

import com.dongba.sys.entity.SysUser;
import com.dongba.sys.service.SysMenuService;
import com.dongba.sys.vo.SysUserMenuVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
public class PageController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("doIndexUI")
    public String doIndexUI(ModelMap modelMap){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();

        List<SysUserMenuVo> userMenus = sysMenuService.findUserMenus(user.getId());
        log.info(userMenus.toString());
        modelMap.addAttribute("userMenus", userMenus);
        modelMap.addAttribute("username", user.getUsername());
        return "starter";
    }

    @GetMapping("{module}/{moduleUI}")
    public String doModulUI(@PathVariable String moduleUI){
        return "sys/" + moduleUI;
    }

    @RequestMapping("doPageUI")
    public String doPageUI() {
        return "common/page";
    }

    @RequestMapping("doLoginUI")
    public String doLoginUI(){
        return "login";
    }
}
