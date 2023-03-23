package com.way.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * ID请求
 * @author Way
 */
@Data
public class IdRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
}
