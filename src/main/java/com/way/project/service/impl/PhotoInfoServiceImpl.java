package com.way.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.way.dubbointerface.model.entity.PhotoInfo;
import com.way.project.service.PhotoInfoService;
import com.way.project.mapper.PhotoInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Way
* @description 针对表【photo_info(图片信息)】的数据库操作Service实现
* @createDate 2023-08-29 17:52:09
*/
@Service
public class PhotoInfoServiceImpl extends ServiceImpl<PhotoInfoMapper, PhotoInfo>
    implements PhotoInfoService {

}




