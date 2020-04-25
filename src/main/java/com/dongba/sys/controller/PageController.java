package com.dongba.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping("doIndexUI")
    public String doIndexUI(){

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
}
