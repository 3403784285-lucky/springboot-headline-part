package com.atguigu.mapper;

import com.atguigu.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author zplaz
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2024-01-23 06:53:05
* @Entity com.atguigu.pojo.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select * from user,comment where user.id=comment.user_id and house_id=#{skuId}")
    List<Comment> selectComment(int skuId);
}




