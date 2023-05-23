package com.way.project.model.vo;

import com.way.dubbointerface.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图
 *
 * @author way
 * @TableName product
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    private int totalNum;

    private static final long serialVersionUID = 1L;
}