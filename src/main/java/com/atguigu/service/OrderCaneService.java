package com.atguigu.service;

import com.atguigu.mapper.OrderSkuMapper;
import com.atguigu.pojo.OrderSku;
import com.atguigu.service.OrderSkuService;
import com.atguigu.utils.OrderAutoEntity;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class OrderCaneService  {

    //订单增删改查业务逻辑
    @Autowired
    private OrderSkuService orderSkuService;






    //用于存放需要未支付计时订单
    private final static DelayQueue<OrderAutoEntity> delayQueue = new DelayQueue<>();


//    订单取消，数据库改变订单状态，DelayQueue容器移除该订单记录
    public void cancelOrder(String orderNo) {
        //数据库改变订单状态
        orderSkuService.cancelOrder(orderNo);
        //orderSkuMapper.
        //容器遍历找到对应的订单记录，并从容器中移除
        Iterator val = delayQueue.iterator();
        while (val.hasNext()) {
            OrderAutoEntity orderAutoEntity = (OrderAutoEntity) val.next();
            if(orderAutoEntity.getOrderNo().equals(orderNo)){
                delayQueue.remove(orderAutoEntity);
            }
        }
    }


    //往队列中新增订单记录
    public void add(OrderAutoEntity orderAutoEntity) {
        delayQueue.put(orderAutoEntity);
    }

    /**
     * 服务器启动时，自动加载待支付订单
     */
    @PostConstruct
    public void initOrderStatus() {
        log.info(">>>>>>>>>>> 系统启动时，加载所有待支付订单到延时队列 >>>>>>>>>>>>.");
        //未支付订单查询
        QueryWrapper<OrderSku> wrapper = new QueryWrapper();
        wrapper.select("order_sku_id", "order_create_time").eq("order_status", "0");
        //获取所有未支付订单，这里用了mybatisplus操作数据库
        List<Map<String, Object>> orders = null;
        try{
            orders = orderSkuService.listMaps(wrapper);
        }catch (Exception e){
            e.getStackTrace();
        }


        //逐个构造Delay的实现类，添加进容器
        for (Map<String, Object> order : orders) {
            LocalDateTime createTime = (LocalDateTime) order.get("order_create_time");
            OrderAutoEntity orderAutoEntity = new OrderAutoEntity((Integer) order.get("order_sku_id")+"", createTime);
            delayQueue.add(orderAutoEntity);
        }

        //启动一个线程持续健康订单超时
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                while (true) {
                    if (delayQueue.size() > 0) {
                        //容器中超时的订单记录会被取出
                        OrderAutoEntity order = delayQueue.take();
                        //修改数据库，容器中移除数据
                        cancelOrder(order.getOrderNo());
                    }
                }
            } catch (InterruptedException e) {
                log.error("InterruptedException error:", e);
            }
        });
    }
}

