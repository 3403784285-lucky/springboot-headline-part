package com.atguigu.service.impl;

import com.atguigu.mapper.SpuMapper;
import com.atguigu.pojo.Spu;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.atguigu.vo.UserHouseFocusRatioVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Focuslist;
import com.atguigu.service.FocuslistService;
import com.atguigu.mapper.FocuslistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author zplaz
* @description 针对表【focuslist】的数据库操作Service实现
* @createDate 2023-12-29 13:13:33
*/
@Service
public class FocuslistServiceImpl extends ServiceImpl<FocuslistMapper, Focuslist>
    implements FocuslistService{

    @Autowired(required = false)
    FocuslistMapper focuslistMapper;

    @Autowired(required = false)
    private SpuMapper spuMapper;


    @Override
    public Result focus(Focuslist focuslist) {
        focuslistMapper.insert(focuslist);
        return Result.ok(null);
    }

    @Override
    public Result focused(Focuslist focuslist) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("focus_id",focuslist.getFocusId());
        queryWrapper.eq("house_id",focuslist.getHouseId());
        focuslistMapper.delete(queryWrapper);
        return Result.ok(null);
    }


    @Override
    public Result searchFocus(Focuslist focuslist) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("focus_id",focuslist.getFocusId());
        queryWrapper.eq("house_id",focuslist.getHouseId());
        if(focuslistMapper.selectOne(queryWrapper)==null){
            return Result.build(null, ResultCodeEnum.CONFIRM_ERROR);

        }else {
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }

    }


    @Override
    public Result<List<UserHouseFocusRatioVO>> search() {
        List<Focuslist> focusLists = focuslistMapper.selectList(null);

        Map<Integer, Long> focusCountMap = focusLists.stream()
                .collect(Collectors.groupingBy(Focuslist::getHouseId, Collectors.counting()));
        long totalFocusCount = focusLists.size();
        Map<Integer, BigDecimal> focusRatioMap = focusCountMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> BigDecimal.valueOf(entry.getValue() * 100.0  /totalFocusCount)
                                .setScale(2, RoundingMode.HALF_UP)
                ));

        List<Spu> spus = spuMapper.selectList(null);
        Map<Integer, String> houseNameMap = spus.stream()
                .collect(Collectors.toMap(Spu::getSpuId, Spu::getHouseName));
        List<UserHouseFocusRatioVO> userHouseFocusRatioVOList = focusRatioMap.entrySet().stream()
                .map(entry -> {
                    UserHouseFocusRatioVO vo = new UserHouseFocusRatioVO();
                    vo.setHouseName(houseNameMap.get(entry.getKey()));
                    vo.setFocusRatio(entry.getValue().intValue());
                    return vo;
                })
                .collect(Collectors.toList());

        return Result.ok(userHouseFocusRatioVOList);
    }

}




