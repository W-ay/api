package com.way.project.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInterfaceInfoVO implements Serializable {
    /**
     * 接口信息id
     */
    private Long id;
    /**
     * 接口名称
     */
    private String name;
    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    private static final long serialVersionUID = 1L;
}
