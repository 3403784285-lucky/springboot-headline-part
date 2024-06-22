package com.atguigu.vo;

import com.atguigu.pojo.OrderSku;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderSkuVO  implements Serializable {
    // 种类
    private String category;

    //订单
    private List<OrderSku> orderSkus;

    // 数量
    private Integer orderCount;
}
