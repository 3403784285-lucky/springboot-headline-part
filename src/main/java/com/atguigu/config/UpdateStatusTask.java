package com.atguigu.config;
import com.atguigu.mapper.AppointMapper;
import com.atguigu.mapper.SkuMapper;
import com.atguigu.pojo.Appoint;
import com.atguigu.pojo.OrderSku;
import com.atguigu.service.AppointService;
import com.atguigu.service.OrderSkuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.injector.methods.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
public class UpdateStatusTask {

    @Autowired
    private OrderSkuService orderSkuService;

    @Autowired(required = false)
    private AppointMapper appointMapper;

    @PostConstruct
    public void init() {
        // 在项目启动时执行一次，相当于手动调用 updateStatus 方法
        updateStatus();
    }

    // 定时任务，每分钟执行一次
    @Scheduled(fixedRate = 60000*10)
    public void updateStatus() {
        List<OrderSku> entitiesToUpdateStatus = (List<OrderSku>) orderSkuService.allPreview().getData();
        for (OrderSku entity : entitiesToUpdateStatus) {
            UpdateWrapper<OrderSku>updateWrapperCopy=new UpdateWrapper<>();
            updateWrapperCopy.set("order_status","2").eq("order_status",1);
            UpdateWrapper<Appoint> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("order_id",entity.getOrderSkuId()).set("work_status",0);
            appointMapper.update(null,updateWrapper);
            orderSkuService.update(updateWrapperCopy);
        }
    }
}
