package com.atguigu.service;

import com.atguigu.pojo.OrderSku;
import com.atguigu.pojo.User;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import org.springframework.mail.javamail.JavaMailSender;

import java.math.BigDecimal;


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

    Result userReturn(OrderSku orderSku);

    Result userDelete(int orderSkuId);

    Result userUpdate(User user);

    Result getAllowance(int userId);

    Result pay(int userId, String password, BigDecimal totalPrice,int orderSkuId);

    Result manageUser(Page<User>page);

    Result freeze(int userId);

    Result unfreeze(int userId);

    Result userReturnApplied(OrderSku orderSku);

    Result getStatus(int userId);


}
