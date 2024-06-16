package com.atguigu.service.impl;

import com.atguigu.mapper.OrderSkuMapper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.atguigu.utils.UtilStorage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    private OrderSkuMapper orderSkuMapper;



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
            sku.setHouseStatus("0");
            skuMapper.insert(sku);
            sku.setHouseOwner((int)map.get("userId"));
            return Result.build(null,ResultCodeEnum.SUCCESS);

        }else {
            return Result.build(null, ResultCodeEnum.CONFIRM_ERROR);
        }

    }

    @Override
    public Result manageHouse(Page<Sku>page) {
        IPage<Sku>skuPage=skuMapper.selectHouses(page);
        return Result.ok(skuPage);
    }

    @Override
    public Result searchUserFocus(int userId) {
        List<Sku>houses=skuMapper.searchUserFocus(userId);
        return Result.ok(houses);

    }
    @Override
    public Result searchUserDeclare(int userId) {
        List<Sku>houses=skuMapper.searchUserDeclare(userId);
        return Result.ok(houses);

    }

    @Override
    public Result searchByKey( Map map) {
        String keyBig=(String)map.get("searchBig");
        String keySmall=(String)map.get("searchSmall");
        List<Sku>houses=skuMapper.searchByKey(keyBig,keySmall);
        return Result.ok(houses);
    }


     @Override
        public Result searchOption( Map map) {
            Integer search1=(Integer) map.get("search1");
            Integer search2=(Integer) map.get("search2");
            Integer search3=(Integer)map.get("search3");
            List<Sku>houses=skuMapper.searchOption(search1,search2,search3);
            return Result.ok(houses);
        }


}

