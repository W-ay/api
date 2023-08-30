package com.way.project.service.dubboimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.way.dubbointerface.common.BusinessException;
import com.way.dubbointerface.common.ErrorCode;
import com.way.dubbointerface.model.entity.PhotoInfo;
import com.way.dubbointerface.service.InnerPhotoInfoService;
import com.way.project.service.PhotoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

@DubboService
@Slf4j
public class InnerPhotoInfoServiceImpl implements InnerPhotoInfoService {
    @Resource
    private PhotoInfoService photoInfoService;

    @Override
    public List<PhotoInfo> getPhotoInfosByPhotoType(String photoType) {
        if (StringUtils.isBlank(photoType)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        LambdaQueryWrapper<PhotoInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PhotoInfo::getPhotoType, photoType);
        wrapper.eq(PhotoInfo::getStatus, 0);
        List<PhotoInfo> list = photoInfoService.list(wrapper);
        return list;
    }
}
