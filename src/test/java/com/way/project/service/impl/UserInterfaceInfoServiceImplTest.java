package com.way.project.service.impl;

import com.way.project.service.UserInterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class UserInterfaceInfoServiceImplTest {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    void invokeCount() {
        userInterfaceInfoService.invokeCount(1, 1);
        userInterfaceInfoService.getById(1);
    }

    @Test
    public void verifyCountTest() {
        log.info("verify :{}" , userInterfaceInfoService.verifyCount(1, 1));
        log.info("verify :{}" , userInterfaceInfoService.verifyCount(4, 2));
        log.info("verify :{}" , userInterfaceInfoService.verifyCount(40, 20));
    }
}