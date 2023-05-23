package com.way.dubbointerface.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口状态枚举
 *
 * @author way
 */
public enum InterfaceInfoEnum {
    /**
     * 接口下线
     */
    OFFLINE("下线", 0),
    /**
     * 接口上线
     */
    ONLINE("上限", 1);

    private final String text;

    private final int value;

    InterfaceInfoEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
