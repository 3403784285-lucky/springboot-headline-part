package com.atguigu.service;

import com.atguigu.pojo.HouseManage;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author zplaz
* @description 针对表【house_manage】的数据库操作Service
* @createDate 2024-01-25 16:20:39
*/
public interface HouseManageService extends IService<HouseManage> {

    Result editSmallTime(Map map);

    Result initSmallTime();

    Result manageTime(int skuId);
}
