package com.way.project.controller;

import cn.wuaiyang.starter.learn.WayService;
import com.way.apiclient.client.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/try")
@Slf4j
public class TryController {
    @Resource
    private WayService wayService;
    @Resource
    private ApiClient apiClient;
    @GetMapping
    public String string(){
        String s = apiClient.invokeInterface("http://localhost:8090/api2/avatar/girl/", null, "GET");
        wayService.say();
        return s;
    }
}
