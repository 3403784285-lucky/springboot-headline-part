package com.atguigu.service;

import com.atguigu.pojo.Focuslist;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zplaz
* @description 针对表【focuslist】的数据库操作Service
* @createDate 2023-12-29 13:13:33
*/
public interface FocuslistService extends IService<Focuslist> {

    Result focus(Focuslist focuslist);

    Result searchFocus(Focuslist focuslist);

    Result focused(Focuslist focuslist);
}
