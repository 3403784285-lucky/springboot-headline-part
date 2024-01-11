package com.atguigu.service.impl;

import com.atguigu.mapper.UserMapper;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Sku;
import com.atguigu.service.SkuService;
import com.atguigu.mapper.SkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zplaz
* @description 针对表【sku】的数据库操作Service实现
* @createDate 2023-12-22 00:51:30
*/
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku>
    implements SkuService{
    @Autowired(required = false)
    private SkuMapper skuMapper;

    public Result getHouseDetail()
    {

        return Result.ok(skuMapper.selectList(null));

    }


}




