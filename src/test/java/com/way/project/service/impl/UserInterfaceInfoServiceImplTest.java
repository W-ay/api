package com.way.project.service.impl;

import com.way.project.service.UserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserInterfaceInfoServiceImplTest {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    void invokeCount() {
        userInterfaceInfoService.invokeCount(1,1);
        userInterfaceInfoService.getById(1);
    }
}