package com.dongba.sys.controller;

import com.dongba.common.vo.JsonResult;
import com.dongba.common.vo.PageObject;
import com.dongba.sys.entity.SysLog;
import com.dongba.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("/doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(String username, Integer pageCurrent){
        PageObject<SysLog> pageObject=
                sysLogService.findPageObjects(username,pageCurrent);
        return new JsonResult(pageObject);
    }

    @PostMapping("doDeleteObjects")
    @ResponseBody
    public JsonResult doDeleteObjects(Integer... ids){
        sysLogService.deleteObjects(ids);
        return new JsonResult("delete ok");
    }
}
