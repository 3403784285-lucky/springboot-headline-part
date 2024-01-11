package com.atguigu.service;

import com.atguigu.pojo.OrderSku;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author zplaz
* @description 针对表【order_sku】的数据库操作Service
* @createDate 2023-12-24 02:44:22
*/

public interface OrderSkuService extends IService<OrderSku> {
    public Result insertPreview(OrderSku order);

    public Result readOrder(int id);

    Result judgeOrderPreview(OrderSku order);

    Result cancelOrder(String orderNo);
}
