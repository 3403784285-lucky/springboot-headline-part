package com.atguigu.controller;


import com.atguigu.mapper.OrderSkuMapper;
import com.atguigu.mapper.SkuMapper;
import com.atguigu.mapper.SpuMapper;
import com.atguigu.mapper.UserMapper;
import com.atguigu.pojo.OrderSku;
import com.atguigu.pojo.Sku;
import com.atguigu.pojo.Spu;
import com.atguigu.pojo.User;
import com.atguigu.service.OrderSkuService;
import com.atguigu.service.SpuService;
import com.atguigu.utils.Result;
import com.atguigu.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Autowired(required = false)
    private SpuMapper spuMapper;

    @Autowired(required = false)
    private SkuMapper skuMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

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
    public Result<Page<OrderSkuVO>> getOrderByStatus(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String status) {

        QueryWrapper<OrderSku> orderSkuQueryWrapper = new QueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            orderSkuQueryWrapper.eq("order_status", status);
        }

        Page<OrderSku> orderSkuPage = new Page<>(page, size);
        Page<OrderSku> orderSkusPageResult = orderSkuMapper.selectPage(orderSkuPage, orderSkuQueryWrapper);
        List<OrderSku> orderSkus = orderSkusPageResult.getRecords();

        List<OrderSkuVO> categorizedOrders = new ArrayList<>();
        Map<String, List<OrderSku>> statusMap = new HashMap<>();
        statusMap.put(status, orderSkus);

        statusMap.forEach((statusKey, orders) -> {
            OrderSkuVO orderSkuVO = new OrderSkuVO();
            orderSkuVO.setCategory(getStatusDescription(statusKey));
            orderSkuVO.setOrderSkus(orders);
            orderSkuVO.setOrderCount(orders.size());
            categorizedOrders.add(orderSkuVO);
        });

        Page<OrderSkuVO> resultPage = new Page<>(page, size);
        resultPage.setRecords(categorizedOrders);
        resultPage.setTotal(orderSkusPageResult.getTotal());

        return Result.ok(resultPage);
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

    /**
     * 通过 spu 对 sku 分类
     */
    @GetMapping("/getOrderBySpu")
    public Result<List<OrderSpuVO>> getOrderBySpu() {
        // 查询所有的 Spu
        List<Spu> spuList = spuMapper.selectList(null);

        QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper.isNotNull("spu_id");
        // 查询所有的 Sku
        List<Sku> skuList = skuMapper.selectList(skuQueryWrapper);

        // 根据 Spu 的 houseName 进行分类
        Map<String, List<Sku>> spuMap = new HashMap<>();
        for (Spu spu : spuList) {
            spuMap.put(spu.getHouseName(), new ArrayList<>());
        }

        // 将 Sku 按照对应的 Spu 进行分类
        for (Sku sku : skuList) {
            for (Spu spu : spuList) {
                if (sku.getSpuId().equals(spu.getSpuId())) {
                    sku.setHouseName(spu.getHouseName());
                    spuMap.get(spu.getHouseName()).add(sku);
                }
            }
        }

        // 构建返回对象
        List<OrderSpuVO> categorizedOrders = new ArrayList<>();
        spuMap.forEach((houseName, skus) -> {
            OrderSpuVO orderSpuVO = new OrderSpuVO();
            orderSpuVO.setCategory(houseName);
            orderSpuVO.setOrderSkus(skus);
            orderSpuVO.setOrderCount(skus.size());
            categorizedOrders.add(orderSpuVO);
        });
        return Result.ok(categorizedOrders);
    }


    /**
     * 获取指定状态的房屋订单信息
     */
    @GetMapping("/getByStatus")
    public Result<IPage<OrderStatus>> getByStatus(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam("status") String status) {

        QueryWrapper<OrderSku> orderSkuQueryWrapper = new QueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            orderSkuQueryWrapper.eq("order_status", status);
        }


        Page<OrderSku> orderSkuPage = new Page<>(page, size);
        Page<OrderSku> orderSkusPageResult = orderSkuMapper.selectPage(orderSkuPage, orderSkuQueryWrapper);
        List<OrderSku> orderSkus = orderSkusPageResult.getRecords();

        Map<String, List<OrderSku>> statusMap = new HashMap<>();
        statusMap.put(status, orderSkus);
        List<OrderStatus> orderStatusList = new ArrayList<>();

        for (OrderSku orderSku : orderSkusPageResult.getRecords()) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setOrderSkuId(orderSku.getOrderSkuId());
            orderStatus.setUserId(orderSku.getUserId());
            orderStatus.setDescription(orderSku.getDescription());
            orderStatus.setOrderCreateTime(orderSku.getOrderCreateTime());
            orderStatus.setStatus(getStatusDescription(orderSku.getOrderStatus()));
            Sku sku = skuMapper.selectById(orderSku.getSkuId());
            if (sku != null) {
                orderStatus.setHousePic(sku.getHousePic());
            }


            Spu spu = spuMapper.selectById(sku.getSpuId());
            if (spu != null) {
                orderStatus.setHouseName(spu.getHouseName());
            }


            User user = userMapper.selectById(orderSku.getUserId());
            if (user != null) {
                orderStatus.setNickname(user.getNickname());
            }

            orderStatusList.add(orderStatus);
        }


        Page<OrderStatus> orderStatusPage = new Page<>(page, size);
        orderStatusPage.setRecords(orderStatusList);
        orderStatusPage.setCurrent(orderSkusPageResult.getCurrent());
        orderStatusPage.setSize(orderSkusPageResult.getSize());
        orderStatusPage.setTotal(orderSkusPageResult.getTotal());

        return Result.ok(orderStatusPage);
    }

}

