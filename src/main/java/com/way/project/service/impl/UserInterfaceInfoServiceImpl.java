package com.way.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.way.dubbointerface.common.BusinessException;
import com.way.dubbointerface.common.ErrorCode;
import com.way.dubbointerface.model.entity.UserInterfaceInfo;
import com.way.project.mapper.UserInterfaceInfoMapper;
import com.way.project.model.dto.userinterfaceinfo.UserInterfaceAddRequest;
import com.way.project.model.vo.UserInterfaceInfoVO;
import com.way.project.service.UserInterfaceInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<UserInterfaceInfoVO> listInterfaceInfoByUserId(long userId, long current, long pageSize) {
        List<UserInterfaceInfoVO> userInterfaceInfoVOS = userInterfaceInfoMapper.listInterfaceInfoByUserId(userId, pageSize * (current - 1), pageSize);
        return userInterfaceInfoVOS;
    }

    @Override
    public boolean addCount(UserInterfaceAddRequest request) {
        Long id = request.getId();
        Integer count = request.getCount();
        if (ObjectUtils.anyNull(id, count)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        LambdaUpdateWrapper<UserInterfaceInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserInterfaceInfo::getId, id);
        wrapper.setSql("leftNum = leftNum + " + count);
        boolean update = this.update(wrapper);
        return update;
    }
}
