package com.atguigu.service.impl;

import com.atguigu.mapper.OtherMapper;
import com.atguigu.pojo.OrderSku;
import com.atguigu.service.OtherService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class OtherServiceImpl implements OtherService {
    @Autowired(required = false)
    private OtherMapper otherMapper;
    @Override
    public Result searchUnPay(int userId) {
        List<OrderSku> orders=otherMapper.searchUnPay(userId);

        return Result.ok(orders);

    }

    @Override
    public Result searchDoing(int userId) {
        List<OrderSku> orders=otherMapper.searchDoing(userId);
        return Result.ok(orders);
    }

    @Override
    public Result searchFinish(int userId) {
        List<OrderSku> orders=otherMapper.searchFinish(userId);
        return Result.ok(orders);
    }

    @Override
    public Result getCancel(int userId) {
        List<OrderSku> orders=otherMapper.searchCancel(userId);
        return Result.ok(orders);
    }
}
