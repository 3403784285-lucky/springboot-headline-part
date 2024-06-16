package com.atguigu.service;

import com.atguigu.pojo.HouseOrder;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author zplaz
* @description 针对表【house_order】的数据库操作Service
* @createDate 2024-01-25 16:20:42
*/
public interface HouseOrderService extends IService<HouseOrder> {

    Result editBigTime(Map map);

    Result initBigTime();

    Result orderTime(int skuId);
}
