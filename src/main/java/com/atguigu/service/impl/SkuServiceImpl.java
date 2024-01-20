package com.atguigu.service.impl;

import com.atguigu.mapper.HouseRelationMapper;
import com.atguigu.mapper.UserMapper;
import com.atguigu.pojo.HouseRelation;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.atguigu.utils.UtilStorage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Sku;
import com.atguigu.service.SkuService;
import com.atguigu.mapper.SkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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


    @Autowired(required = false)
    private HouseRelationMapper houseRelationMapper;

    public Result getHouseDetail()
    {

        return Result.ok(skuMapper.getDetail());

    }

    @Override
    public Result houseCertain(int skuId) {
        System.out.println(skuId);
        Sku house=skuMapper.selectDetailById(skuId);
        return Result.ok(house);
    }

    @Override
    public Result declare(Map map) {
        //验证码：对，返回正确信息，并插入数据，错返回错误信息
        System.out.println("检测验证码"+UtilStorage.confirm);
        if(UtilStorage.confirm.equals(map.get("confirmPassword"))){
            Sku sku=new Sku();
            sku.setHousePosition((String)map.get("position"));
            sku.setHouseCity((String)map.get("city"));
            sku.setHousePrice(new BigDecimal((String) map.get("expPrice")));
            skuMapper.insert(sku);
            HouseRelation houseRelation=new HouseRelation();
            houseRelation.setHouseId(sku.getSkuId());
            houseRelation.setUserId((int)map.get("userId"));
            houseRelationMapper.insert((houseRelation));
            return Result.build(null,ResultCodeEnum.SUCCESS);

        }else {
            return Result.build(null, ResultCodeEnum.CONFIRM_ERROR);
        }



    }


}




