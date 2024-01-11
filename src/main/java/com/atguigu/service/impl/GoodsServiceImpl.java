package com.atguigu.service.impl;

import com.atguigu.mapper.SkuMapper;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Goods;
import com.atguigu.service.GoodsService;
import com.atguigu.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zplaz
* @description 针对表【goods】的数据库操作Service实现
* @createDate 2023-12-23 22:06:32
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService{

    @Autowired(required = false)
    private GoodsMapper goodsMapper;

    public Result getPreviewDetail(int skuId){
        QueryWrapper<Goods>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("sku_id",skuId);
        List<Goods> goods=goodsMapper.selectList(queryWrapper);
        return Result.ok(goods);

    }

}




