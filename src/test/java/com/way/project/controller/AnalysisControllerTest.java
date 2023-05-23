package com.way.project.controller;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
