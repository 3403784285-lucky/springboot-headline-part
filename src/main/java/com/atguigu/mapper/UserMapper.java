package com.atguigu.mapper;

import com.atguigu.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
* @author zplaz
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-12-05 19:04:27
* @Entity com.atguigu.pojo.User
*/
public interface UserMapper extends BaseMapper<User> {

    @Update("UPDATE user SET nickname = #{nickname}, user_pic = #{user_pic}, email = #{email} WHERE id = #{userId}")
    void updateUserProfile(@Param("userId") Long userId, @Param("nickname") String nickname, @Param("user_pic") String user_pic, @Param("email") String email);

}




