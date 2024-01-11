package com.atguigu.service;

import com.atguigu.pojo.Goods;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zplaz
* @description 针对表【goods】的数据库操作Service
* @createDate 2023-12-23 22:06:32
*/
public interface GoodsService extends IService<Goods> {
    public Result getPreviewDetail(int skuId);

}
