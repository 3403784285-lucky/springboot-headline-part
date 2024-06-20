package com.atguigu.service.impl;

import com.atguigu.mapper.GoodsMapper;
import com.atguigu.mapper.OrderSkuMapper;
import com.atguigu.pojo.OrderSku;
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

    @Autowired(required = false)
    private OrderSkuMapper orderSkuMapper;

    @Override
    public Result initComment(int skuId) {
        QueryWrapper<Comment>queryWrapper=new QueryWrapper<>();
        List<Comment>list=commentMapper.selectComment(skuId);
        return Result.ok(list);
    }

    @Override
    public Result declareComment(Comment comment) {
        //判断用户是否进行购买
        int userId = comment.getUserId();
        int houseId = comment.getHouseId();
        List<OrderSku> list = orderSkuMapper.selectOrderStatus(userId,houseId);
        boolean hasStatus2 = list.stream()
                .anyMatch(orderSku -> "2".equals(orderSku.getOrderStatus()));
        if(hasStatus2){
            commentMapper.insert(comment);
            System.out.println(comment);
            return Result.build(comment,200,"OK");
        }else {
            return Result.build(null,200,"FAIL");
        }
    }

    @Override
    public Result replyComment(Comment comment) {
       commentMapper.insert(comment);
        System.out.println(comment);
        return Result.ok(comment);
    }
}




