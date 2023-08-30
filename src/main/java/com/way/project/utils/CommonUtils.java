package com.way.project.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import com.way.dubbointerface.model.entity.PhotoInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {

    public static List<PhotoInfo> avatars = null;
    public static List<PhotoInfo> getPhotoInfoInFile(String filePath){
        return getPhotoInfoInFile(filePath, null);
    }
        public static List<PhotoInfo> getPhotoInfoInFile(String filePath,String photoType){
        File file = new File(filePath);
        List<String> list = FileUtil.readLines(file, CharsetUtil.UTF_8);
        avatars = list.stream().map((item) -> {
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setUrl(item);
            if (photoType!=null){
                photoInfo.setPhotoType(photoType);
            }
            return photoInfo;
        }).collect(Collectors.toList());
        return avatars;
    }

    public static void main(String[] args) {
        List<PhotoInfo> photoInfoInFile = getPhotoInfoInFile("assets/avatar-urls.txt","girlAvatar");
//        System.out.println(photoInfoInFile);
        photoInfoInFile.forEach(System.out::println);
    }
}
