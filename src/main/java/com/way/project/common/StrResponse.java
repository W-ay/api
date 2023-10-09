package com.way.project.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.way.dubbointerface.common.BaseResponse;
import com.way.dubbointerface.common.ErrorCode;
import lombok.Data;

@Data
public class StrResponse extends BaseResponse<String> {
    private int code;

    @JsonSerialize(using = StringSerializer.class)
    private String data;

    private String message;
    public StrResponse(int code, String data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public StrResponse(int code, String data) {
        this(code, data,"");
    }

    public StrResponse(ErrorCode errorCode) {
        super(errorCode);
    }
}
