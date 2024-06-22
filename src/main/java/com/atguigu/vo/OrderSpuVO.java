package com.atguigu.vo;

import com.atguigu.pojo.Sku;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderSpuVO implements Serializable {
    // 种类
    private String category;

    //订单
    private List<Sku> orderSkus;

    // 数量
    private Integer orderCount;

}
