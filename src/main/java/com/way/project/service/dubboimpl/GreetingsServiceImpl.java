package com.way.project.service.dubboimpl;

import com.way.dubbointerface.api.GreetingsService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Way
 */
@DubboService
public class GreetingsServiceImpl implements GreetingsService {
    @Override
    public String sayHi(String name) {
        return "provider ++++>" + name;
    }
}
