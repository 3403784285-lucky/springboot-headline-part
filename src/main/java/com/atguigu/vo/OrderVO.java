package com.atguigu.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderVO implements Serializable {

    //订单
    private List<AllOrderVO>  allOrderVOS;

    // 订单种类
    private String caters;

    // 种类的总销售金额
    private Double totalPrice;
}
