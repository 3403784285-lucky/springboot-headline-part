package com.atguigu.service;

import com.atguigu.pojo.Comment;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zplaz
* @description 针对表【comment】的数据库操作Service
* @createDate 2024-01-23 06:53:05
*/
public interface CommentService extends IService<Comment> {
    Result initComment(int skuId);

    Result declareComment(Comment comment);

    Result replyComment(Comment comment);

}
