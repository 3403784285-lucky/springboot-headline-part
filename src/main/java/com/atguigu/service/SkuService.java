package com.atguigu.service;

import com.atguigu.pojo.Sku;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zplaz
* @description 针对表【sku】的数据库操作Service
* @createDate 2023-12-22 00:51:30
*/
public interface SkuService extends IService<Sku> {
    public Result getHouseDetail();



}
