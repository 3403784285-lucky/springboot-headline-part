package com.atguigu.service.impl;

import com.atguigu.mapper.OtherMapper;
import com.atguigu.pojo.HouseManage;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.HouseOrder;
import com.atguigu.service.HouseOrderService;
import com.atguigu.mapper.HouseOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author zplaz
* @description 针对表【house_order】的数据库操作Service实现
* @createDate 2024-01-25 16:20:42
*/
@Service
public class HouseOrderServiceImpl extends ServiceImpl<HouseOrderMapper, HouseOrder>
    implements HouseOrderService{
    @Autowired(required = false)
    private HouseOrderMapper houseOrderMapper;

    @Override
    public Result editBigTime(Map map) {
        System.out.println("map = " + map);
        int skuId=(Integer) map.get("houseId");
        List<String>array=new ArrayList<>();
        array.add((String) map.get("timeBigOne"));
        array.add((String) map.get("timeBigTwo"));
        array.add((String) map.get("timeBigThree"));
        array.add((String) map.get("timeBigFour"));
        String idCopy=(String)map.get("idCopy");
        List<HouseOrder>houseOrders=new ArrayList<>();



        QueryWrapper queryWrapper=new QueryWrapper();
                queryWrapper.eq("house_id",skuId);

        if (idCopy.equals("")){ for (String a:array){
            HouseOrder H=new HouseOrder();
            H.setHouseId(skuId);
            H.setHouseOrderTime(a);
            houseOrders.add(H);

        }
            for (HouseOrder houseOrder:houseOrders)
            {

                houseOrderMapper.insert(houseOrder);
            }

        }else{
            String[] stringArray = idCopy.substring(1).split(",");

            // 将字符串数组中的每个元素转换为数字
            for (int i = 0; i < stringArray.length; i++) {
                houseOrders.add(new HouseOrder( Integer.parseInt(stringArray[i]),skuId,array.get(i)));
            }
            for (int i=0;i<stringArray.length;i++)
            {
                houseOrderMapper.updateById(houseOrders.get(i));
            }
        }

        return Result.ok(houseOrders);
    }

    @Override
    public Result initBigTime() {
        List<HouseOrder>houseOrders=houseOrderMapper.selectList(null);
        return Result.ok(houseOrders);
    }

    @Override
    public Result orderTime(int skuId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("house_id",skuId);
        List<HouseOrder>houseOrders=houseOrderMapper.selectList(queryWrapper);
        return Result.ok(houseOrders);
    }
}




