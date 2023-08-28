package com.way.project.model.dto.userinterfaceinfo;

import lombok.Data;

@Data
public class UserInterfaceAddRequest {
    private Long id;
    private Long interfaceInfoId;
    private Long userId;
    private Integer count;
}
