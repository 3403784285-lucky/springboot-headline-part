package com.atguigu.service.impl;

import com.atguigu.mapper.GoodsMapper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Focuslist;
import com.atguigu.service.FocuslistService;
import com.atguigu.mapper.FocuslistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}




