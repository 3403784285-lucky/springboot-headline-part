package com.atguigu.service.impl;

import com.atguigu.mapper.*;
import com.atguigu.pojo.Appoint;
import com.atguigu.pojo.Sku;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.OrderSku;
import com.atguigu.service.OrderSkuService;
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

    @Autowired(required = false)
    private SkuMapper skuMapper;


    @Autowired(required = false)
    private AppointMapper appointMapper;


    public Result insertPreview(OrderSku order)
    {
        orderSkuMapper.insert(order);
        return Result.ok(order);

    }

    public Result judgeOrderPreview(int skuId) {
        QueryWrapper<OrderSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("order_end_time", LocalDateTime.now());
        queryWrapper.eq("sku_id",skuId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status", "1").or().eq("order_status", "0"));
        List<OrderSku> orderSkus = orderSkuMapper.selectList(queryWrapper);
        return Result.ok(orderSkus);
    }


    @Override
    public Result allPreview() {
        QueryWrapper<OrderSku>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("order_status","1").lt("order_end_time",LocalDateTime.now());
        return Result.ok(orderSkuMapper.selectList(null));
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

    @Override
    public Result managePreview(Page<OrderSku>page) {
        IPage<OrderSku>pageOrderSku =orderSkuMapper.selectPreviews(page);
        return Result.ok(pageOrderSku);
    }

    @Override
    public Result appointSearch() {
        QueryWrapper<Appoint>queryWrapper=new QueryWrapper<>();
        queryWrapper.ne("work_status",1);
        appointMapper.selectList(queryWrapper);
        return null;
    }

    @Override
    public Result appoint(int houseId,int workerId) {
        Appoint appoint=new Appoint();
        appoint.setWorkerId(workerId);
        appoint.setWorkStatus(0);
        appointMapper.updateById(appoint);
        return Result.ok(null);

    }


    @Override
    public Result uploadHouse(int houseId) {
        Sku sku=new Sku();
        sku.setHouseStatus("1");
        sku.setSkuId(houseId);
        skuMapper.updateById(sku);
        return Result.ok(null);
    }

    @Override
    public Result takeOffHouse(int houseId) {
        Sku sku=new Sku();
        sku.setHouseStatus("0");
        sku.setSkuId(houseId);
        skuMapper.updateById(sku);
        return Result.ok(null);
    }



    @Override
    public Result editHouse(Sku sku) {
        skuMapper.updateById(sku);
        return Result.ok(null);
    }

    @Override
    public Result askOrder(int userId) {
        QueryWrapper<OrderSku> queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status", "1").or().eq("order_status", "0"));
        List<OrderSku> orders=orderSkuMapper.selectList(queryWrapper);
        return Result.ok(orders);
    }



    public Result readOrder(int id)
    {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",id);
        List<OrderSku> orders=orderSkuMapper.selectList(queryWrapper);
        return Result.ok(orders);
    }




}




