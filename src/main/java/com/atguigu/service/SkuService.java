package com.atguigu.service;

import com.atguigu.pojo.Sku;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author zplaz
* @description 针对表【sku】的数据库操作Service
* @createDate 2023-12-22 00:51:30
*/
public interface SkuService extends IService<Sku> {
    public Result getHouseDetail();


    Result houseCertain(int skuId);

    Result declare(Map map);

    Result manageHouse(Page<Sku>page);


    Result searchUserFocus(int userId);

    Result searchUserDeclare(int userId);

    Result searchByKey(Map map);

    Result searchOption(Map map);
}
