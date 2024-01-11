package com.atguigu.service;

import com.atguigu.pojo.User;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import org.springframework.mail.javamail.JavaMailSender;


/**
* @author zplaz
* @description 针对表【user】的数据库操作Service
* @createDate 2023-12-05 19:04:27
*/
public interface UserService extends IService<User> {


    Result login(Long id, String password);

    Result getUserInfo(String token);

    Result checkUserName(String username);

    Result regist(String email,String password,String rePassword,String confirm);

    Result getConfirm(JavaMailSender javaMailSender, String email);


}
