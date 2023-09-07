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
    

}
