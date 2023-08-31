package com.way.project.controller;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;

@SpringBootTest
class AnalysisControllerTest {
    @Resource
    private AnalysisController analysisController;

    @Test
    void testListTopInterfaceInvoke() throws Exception {
        analysisController.listTopInterfaceInvoke();
    }
    
    @Test
    public void uriComponentsTest(){
        String httpUrl= "http://8.130.76.170:8090/api2/poem/";
        UriComponents components = UriComponentsBuilder.fromHttpUrl(httpUrl).build();
        String scheme = components.getScheme();
        System.out.println("components.getScheme() = " + scheme);
        String host = components.getHost();
        System.out.println("components.getHost() = " + host);
        String path = components.getPath();
        System.out.println("components.getPath() = " + path);
        System.out.println(scheme+"://"+host+path);
//        components.get
    }
}
