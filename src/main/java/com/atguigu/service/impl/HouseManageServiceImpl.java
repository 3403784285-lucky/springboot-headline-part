package com.atguigu.service.impl;

import com.atguigu.mapper.OtherMapper;
import com.atguigu.pojo.HouseOrder;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.HouseManage;
import com.atguigu.service.HouseManageService;
import com.atguigu.mapper.HouseManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author zplaz
* @description 针对表【house_manage】的数据库操作Service实现
* @createDate 2024-01-25 16:20:39
*/
@Service
public class HouseManageServiceImpl extends ServiceImpl<HouseManageMapper, HouseManage>
    implements HouseManageService{

    @Autowired(required = false)
    private HouseManageMapper houseManageMapper;

    @Override
    public Result editSmallTime(Map map) {

        int skuId=(Integer) map.get("houseId");
        List<String>array=new ArrayList<>();
        array.add((String) map.get("timeSmallOne"));
        array.add((String) map.get("timeSmallTwo"));
        array.add((String) map.get("timeSmallThree"));
        String idCopy=(String)map.get("idCopy");
        List<HouseManage>houseManages=new ArrayList<>();



        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("house_id",skuId);
        if (idCopy.equals("")){
            for (String a:array){
                HouseManage H=new HouseManage();
                H.setHouseId(skuId);
                H.setHouseTimeType(a);
                houseManages.add(H);

            }
            for (HouseManage houseManage:houseManages)
            {
                houseManageMapper.insert(houseManage);
            }

        }else{
            String[] stringArray = idCopy.substring(1).split(",");

            // 将字符串数组中的每个元素转换为数字
            for (int i = 0; i < stringArray.length; i++) {
                houseManages.add(new HouseManage(skuId,array.get(i),Integer.parseInt(stringArray[i])));
            }
            for (int i=0;i<stringArray.length;i++)
            {
                houseManageMapper.updateById(houseManages.get(i));
            }
        }

        return Result.ok(houseManages);
    }

    @Override
    public Result initSmallTime() {
        List<HouseManage>houseManages=houseManageMapper.selectList(null);
        return Result.ok(houseManages);
    }

    @Override
    public Result manageTime(int skuId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("house_id",skuId);
        List<HouseManage>houseManages=houseManageMapper.selectList(queryWrapper);
        return Result.ok(houseManages);
    }
}




