package com.atguigu.service.impl;

import com.atguigu.mapper.GoodsMapper;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Comment;
import com.atguigu.service.CommentService;
import com.atguigu.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zplaz
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2024-01-23 06:53:05
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Override
    public Result initComment(int skuId) {
        QueryWrapper<Comment>queryWrapper=new QueryWrapper<>();
        List<Comment>list=commentMapper.selectComment(skuId);
        return Result.ok(list);
    }

    @Override
    public Result declareComment(Comment comment) {
        commentMapper.insert(comment);
        System.out.println(comment);
        return Result.ok(comment);
    }

    @Override
    public Result replyComment(Comment comment) {
       commentMapper.insert(comment);
        System.out.println(comment);

        return Result.ok(comment);
    }
}




