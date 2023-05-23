package com.way.project.service.dubboimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.way.dubbointerface.model.entity.User;
import com.way.dubbointerface.service.InnerUserService;
import com.way.project.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author Way
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserService userService;

    @Override
    public User getInvokeUser(String accessKey) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("access_key",accessKey);
        return userService.getOne(queryWrapper);
    }
}
