package com.atguigu.service;

import com.atguigu.pojo.Comment;
import com.atguigu.pojo.OrderSku;
import com.atguigu.pojo.Sku;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    Result judgeOrderPreview(int skuId);

    Result allPreview();


    Result cancelOrder(String orderNo);

    Result managePreview(Page<OrderSku>page);

    Result appointSearch();

    Result appoint(int houseId,int workerId);



    Result uploadHouse(int houseId);

    Result takeOffHouse(int houseId);



    Result editHouse(Sku sku);


    Result askOrder(int userId);


}
