package com.atguigu.controller;


import com.atguigu.mapper.OrderSkuMapper;
import com.atguigu.pojo.OrderSku;
import com.atguigu.service.OrderSkuService;
import com.atguigu.utils.Result;
import com.atguigu.vo.AllOrderVO;
import com.atguigu.vo.OrderSkuVO;
import com.atguigu.vo.OrderVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("order")
@CrossOrigin
public class OrderController {

    @Autowired(required = false)
    private OrderSkuMapper orderSkuMapper;

    @GetMapping("/getAllOrder")
    public Result<List<OrderVO>> getAllOrder() {
        QueryWrapper<OrderSku> orderSkuQueryWrapper = new QueryWrapper<>();

        List<OrderSku> orderSkus = orderSkuMapper.selectList(orderSkuQueryWrapper);
        // 创建一个映射表
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("-1", "已取消");
        statusMap.put("-2", "退款中");
        statusMap.put("-3", "已退款");
        statusMap.put("1", "支付成功");
        statusMap.put("0", "未支付");
        statusMap.put("2", "已完成");


        Map<String, OrderVO> orderMap = new HashMap<>();
        for (OrderSku orderSku : orderSkus) {
            String category = orderSku.getOrderStatus();
            OrderVO orderVO = orderMap.getOrDefault(category, new OrderVO());
            // 从映射表中获取对应的状态字符串
            String status = statusMap.get(category);

            // 设置 allOrderVO 的状态
            if (status != null) {
                orderVO.setCaters(status);
            } else {
                // 处理未知的状态码，这里可以抛出异常或设置为默认值
                orderVO.setCaters("未知状态");
            }

            List<AllOrderVO> allOrderVOS = orderVO.getAllOrderVOS();
            if (allOrderVOS == null) {
                allOrderVOS = new ArrayList<>();
            }

            AllOrderVO allOrderVO = new AllOrderVO();
            allOrderVO.setPrice(orderSku.getTotalPrice());

            // 获取 orderSku 的状态
            String orderStatus = orderSku.getOrderStatus();

            // 从映射表中获取对应的状态字符串
            status = statusMap.get(orderStatus);

            // 设置 allOrderVO 的状态
            if (status != null) {
                allOrderVO.setStatus(status);
            } else {
                // 处理未知的状态码，这里可以抛出异常或设置为默认值
                allOrderVO.setStatus("未知状态");
            }

            allOrderVO.setDate(orderSku.getOrderCreateTime());

            allOrderVOS.add(allOrderVO);
            orderVO.setAllOrderVOS(allOrderVOS);

            Double totalPrice = orderVO.getTotalPrice();
            if (totalPrice == null) {
                totalPrice = 0.0;
            }
            totalPrice += Double.valueOf(String.valueOf(orderSku.getTotalPrice()));
            orderVO.setTotalPrice(totalPrice);

            orderMap.put(category, orderVO);
        }

        List<OrderVO> result = new ArrayList<>(orderMap.values());

        return Result.ok(result);
    }

    /***
     * 获取
     *  statusMap.put("-1", "已取消");
     *         statusMap.put("-2", "退款中");
     *         statusMap.put("-3", "已退款");
     *         statusMap.put("1", "支付成功");
     *         statusMap.put("0", "未支付");
     *         statusMap.put("2", "已完成");
     *
     *  这五种订单数据 归类
     */
    @GetMapping("/getOrderByStatus")
    public Result<List<OrderSkuVO>> getOrderByStatus() {
        List<OrderSku> allOrders = orderSkuMapper.selectList(null);

        Map<String, List<OrderSku>> statusMap = new HashMap<>();
        statusMap.put("-1", new ArrayList<>());
        statusMap.put("-2", new ArrayList<>());
        statusMap.put("-3", new ArrayList<>());
        statusMap.put("1", new ArrayList<>());
        statusMap.put("0", new ArrayList<>());
        statusMap.put("2", new ArrayList<>());
        Integer count = null;
        for (OrderSku order : allOrders) {
            String status = order.getOrderStatus();
            if (statusMap.containsKey(status)) {
                statusMap.get(status).add(order);
            }
        }

        List<OrderSkuVO> categorizedOrders = new ArrayList<>();


        statusMap.forEach((status, orders) -> {
            OrderSkuVO orderSkuVO = new OrderSkuVO();
            orderSkuVO.setCategory(getStatusDescription(status));
            orderSkuVO.setOrderSkus(orders);
            orderSkuVO.setOrderCount(orders.size());
            categorizedOrders.add(orderSkuVO);
        });

        return Result.ok(categorizedOrders);
    }


    private String getStatusDescription(String status) {
        switch (status) {
            case "-1":
                return "已取消";
            case "-2":
                return "退款中";
            case "-3":
                return "已退款";
            case "1":
                return "支付成功";
            case "0":
                return "未支付";
            case "2":
                return "已完成";
            default:
                return "未知状态";
        }
    }
}

