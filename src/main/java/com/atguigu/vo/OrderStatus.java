package com.atguigu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderStatus implements Serializable {
    // 房子头像
    private String housePic;

    //房子名称
    private String houseName;

    // 用户id
    private Integer userId;

    //用户昵称
    private String nickname;

    // 订单描述
    private String description;

    // 订单号
    private Integer orderSkuId;

    private String status;
    //创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderCreateTime;

    // 价格
    private BigDecimal totalPrice;

    // sku_id
    private Integer skuId;

    // 用户头像
    private String userPic;
}
