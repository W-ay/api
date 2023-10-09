package com.way.project.controller;

import com.way.dubbointerface.common.BaseResponse;
import com.way.project.common.ResultUtils;
import com.way.project.common.StrResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/assist")
public class AssistController {
    @GetMapping("/manual-md")
    public BaseResponse<String> getManual() throws IOException {
        List<String> list = Files.readAllLines(Paths.get("assets/项目介绍.md"));
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s+"\n");
        }
//        return new StrResponse(0,sb.toString(),"success");
        return ResultUtils.success(sb.toString());
    }
}
