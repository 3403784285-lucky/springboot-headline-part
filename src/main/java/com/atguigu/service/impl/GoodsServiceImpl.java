package com.atguigu.service.impl;

import com.atguigu.mapper.OtherMapper;
import com.atguigu.pojo.HouseManage;
import com.atguigu.pojo.HouseOrder;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Goods;
import com.atguigu.service.GoodsService;
import com.atguigu.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author zplaz
* @description 针对表【goods】的数据库操作Service实现
* @createDate 2024-01-25 16:20:35
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService{

    @Autowired(required = false)
    private GoodsMapper goodsMapper;

    @Override
    public Result editMoney(Map map) {
        int skuId=(Integer) map.get("houseId");
        List<BigDecimal>array=new ArrayList<>();
        array.add(new BigDecimal((String) map.get("priceOne")));
        array.add(new BigDecimal((String) map.get("priceTwo")));
        array.add(new BigDecimal((String) map.get("priceThree")));
        String idCopy=(String)map.get("idCopy");
        String spu=(String)map.get("spuId");
        List<Goods>goods=new ArrayList<>();


        String[] spuArray = spu.substring(1).split(",");
        System.out.println(spu+"--->"+spuArray+"测测");

        // 将字符串数组中的每个元素转换为数字



        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("sku_id",skuId);
        if (idCopy.equals("")){
            for (BigDecimal a:array){
                Goods H=new Goods();
                H.setSkuId(skuId);
                H.setReservePrice(a);
                goods.add(H);

            }
            for (Goods good:goods)
            {
               goodsMapper.insert(good);
            }

        }else{
            String[] stringArray = idCopy.substring(1).split(",");
            for (int i = 0; i < stringArray.length; i++) {
                goods.add(new Goods( Integer.parseInt(stringArray[i]),Integer.parseInt(spuArray[i]),array.get(i),skuId));
            }
            for (int i=0;i<stringArray.length;i++)
            {
               goodsMapper.updateById(goods.get(i));
            }
        }

        return Result.ok(null);

    }

    @Override
    public Result getPreviewDetail(Integer skuId){
        QueryWrapper<Goods>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("sku_id",skuId);
        List<Goods> goods=goodsMapper.selectList(queryWrapper);
        return Result.ok(goods);

    }


    @Override
    public Result initMoney() {
        List<Goods>goods=goodsMapper.selectList(null);
        return Result.ok(goods);
    }


}




