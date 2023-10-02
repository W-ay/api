package com.way.dubbointerface.service;

import com.way.dubbointerface.model.entity.PhotoInfo;

import java.util.List;

public interface InnerPhotoInfoService {
    /**
     * 根据图片类型获取图片信息
     * @param photoType 图片类型
     * @return 图片信息集合
     */
    List<PhotoInfo> getPhotoInfosByPhotoType(String photoType);
}
