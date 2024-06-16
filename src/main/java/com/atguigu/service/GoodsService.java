package com.atguigu.service;

import com.atguigu.pojo.Goods;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author zplaz
* @description 针对表【goods】的数据库操作Service
* @createDate 2024-01-25 16:20:35
*/
public interface GoodsService extends IService<Goods> {

    Result editMoney(Map map);

    Result getPreviewDetail(Integer skuId);

    Result initMoney();




}
