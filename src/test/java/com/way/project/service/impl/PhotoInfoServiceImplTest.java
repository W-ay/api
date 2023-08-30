package com.way.project.service.impl;
import java.util.Date;
import java.util.List;

import com.way.dubbointerface.model.entity.PhotoInfo;
import com.way.project.service.PhotoInfoService;
import com.way.project.utils.CommonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static com.way.project.utils.CommonUtils.getPhotoInfoInFile;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PhotoInfoServiceImplTest {
    @Resource
    private PhotoInfoService photoInfoService;

    @Test
    public void crudTest(){
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setUrl("https://img1.imgtp.com/2023/08/29/NpFFZacZ.png");
        photoInfo.setPhotoType("girlAvatar");
        photoInfo.setStatus(0);

        boolean save = photoInfoService.save(photoInfo);
        Assertions.assertTrue(save);

        photoInfo.setStatus(1);
        boolean b = photoInfoService.updateById(photoInfo);
        Assertions.assertTrue(b);

        PhotoInfo queryPhotoInfo = photoInfoService.getById(photoInfo.getId());
        Assertions.assertTrue(queryPhotoInfo.getUrl().equals(photoInfo.getUrl()));

        boolean b1 = photoInfoService.removeById(queryPhotoInfo.getId());
        Assertions.assertTrue(b1);

    }

    @Test
    public void saveBatchTest(){
        List<PhotoInfo> photoInfoInFile = getPhotoInfoInFile("assets/avatar-urls.txt","girlAvatar");
        boolean b = photoInfoService.saveBatch(photoInfoInFile);
        Assertions.assertTrue(b);
    }
    @Test
    public void saveBatch2Test(){
        List<PhotoInfo> photoInfoInFile = getPhotoInfoInFile("assets/boy-avatar-urls.txt","boyAvatar");
        boolean b = photoInfoService.saveBatch(photoInfoInFile);
        Assertions.assertTrue(b);
    }

}