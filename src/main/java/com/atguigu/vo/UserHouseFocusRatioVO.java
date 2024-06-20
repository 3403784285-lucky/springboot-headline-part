package com.atguigu.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserHouseFocusRatioVO implements Serializable {
    // 房屋名称
    private String houseName;

    // 房屋关注百分比
    private Integer focusRatio;
}
