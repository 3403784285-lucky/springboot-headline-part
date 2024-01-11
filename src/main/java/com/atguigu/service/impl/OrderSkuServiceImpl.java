package com.atguigu.service.impl;

import com.atguigu.mapper.GoodsMapper;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.OrderSku;
import com.atguigu.service.OrderSkuService;
import com.atguigu.mapper.OrderSkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author zplaz
* @description 针对表【order_sku】的数据库操作Service实现
* @createDate 2023-12-24 02:44:22
*/
@Service
public class OrderSkuServiceImpl extends ServiceImpl<OrderSkuMapper, OrderSku>
    implements OrderSkuService{

    @Autowired(required = false)
    private OrderSkuMapper orderSkuMapper;
    public Result insertPreview(OrderSku order)
    {
        orderSkuMapper.insert(order);
        return Result.ok(order);

    }

    public Result judgeOrderPreview(OrderSku order)
    {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.gt("order_end_time", LocalDateTime.now());
        queryWrapper.ne("order_status","-1");
        List<OrderSku> orderSkus=orderSkuMapper.selectList(queryWrapper);
        return Result.ok(orderSkus);

    }

    @Override
    public Result cancelOrder(String orderNo) {
        UpdateWrapper<OrderSku> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_sku_id", orderNo); // 假设订单ID字段为order_id

        // 构建更新字段
        OrderSku updateOrder = new OrderSku();
        updateOrder.setOrderStatus("-1"); // 将订单状态改为0

        // 执行更新操作
        orderSkuMapper.update(updateOrder, updateWrapper);
        return Result.ok(null);

    }


    public Result readOrder(int id)
    {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",id);
        List<OrderSku> orders=orderSkuMapper.selectList(queryWrapper);
        return Result.ok(orders);
    }

}




