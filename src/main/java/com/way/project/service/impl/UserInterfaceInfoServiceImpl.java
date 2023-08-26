package com.way.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.way.dubbointerface.model.entity.UserInterfaceInfo;
import com.way.project.mapper.UserInterfaceInfoMapper;
import com.way.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Way
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements UserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {

    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.setSql("leftNum = leftNum -1 , totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }

    @Override
    public boolean verifyCount(long interfaceInfoId, long userid) {
        LambdaQueryWrapper<UserInterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
        wrapper.eq(UserInterfaceInfo::getUserId, userid);
        UserInterfaceInfo one = this.getOne(wrapper);
        if (one == null) {
            return false;
        } else if (one.getLeftNum() > 0) {
            return true;
        }
        return false;
    }
}
