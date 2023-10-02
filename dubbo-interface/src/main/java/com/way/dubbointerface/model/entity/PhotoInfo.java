package com.way.dubbointerface.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片信息
 * @TableName photo_info
 */
@TableName(value ="photo_info")
@Data
public class PhotoInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 图片地址
     */
    private String url;
    /**
     * 图片地址
     */
    private String photoType;

    /**
     * 0-正常，1-失效
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}