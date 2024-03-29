package com.atguigu.controller;


import com.atguigu.pojo.Focuslist;
import com.atguigu.pojo.OrderSku;
import com.atguigu.pojo.User;
import com.atguigu.service.FocuslistService;
import com.atguigu.service.OrderSkuService;
import com.atguigu.service.OtherService;
import com.atguigu.service.UserService;
import com.atguigu.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;


@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired(required = false)
    JavaMailSender javaMailSender; //当你引入了javaMailSender并且在pom文件里面引入了mail依赖就自动准备好了

    @Autowired
    private UserService userService;

    @Autowired(required = false)
    private OtherService otherService;

    @Autowired
    private FocuslistService focuslistService;

    @PostMapping("login")
    public Result login(@RequestBody Map map){
        Result result = userService.login(Long.parseLong((String) map.get("id")),(String) map.get("password"));
        return result;
    }
//    @GetMapping("getUserInfo")
//    public Result getUserInfo(@RequestBody Map map){
//        Result result = userService.getUserInfo(token);
//        return result;
//    }

    @PostMapping("getConfirm")
    public  Result getConfirm(@RequestBody Map map){
        Result result=userService.getConfirm(javaMailSender,(String) map.get("email"));
        return result;

    }

//    @PostMapping("checkUserName")
//    public Result checkUserName(String username){
//        Result result = userService.checkUserName(username);
//        return result;
//    }
    @PostMapping("regist")
    public Result regist(@RequestBody Map map){
        System.out.println(map.get("password"));
        Result result = userService.regist((String) map.get("email"),(String)map.get("password"),(String)map.get("rePassword"),(String)map.get("confirm"));
        return result;
    }

    @PostMapping("focus")
        public Result focus(@RequestBody Focuslist focuslist){
            System.out.println(focuslist);
            Result result = focuslistService.focus(focuslist);
            return result;
        }

        @PostMapping("focused")
        public Result focused(@RequestBody Focuslist focuslist){
            System.out.println(focuslist);
            Result result = focuslistService.focused(focuslist);
            return result;
        }

        @PostMapping("searchFocus")
        public Result searchFocus(@RequestBody Focuslist focuslist){
            System.out.println(focuslist);
            Result result = focuslistService.searchFocus(focuslist);
            return result;
        }

        @PostMapping("searchUnPay")
        public Result searchUnPay(@RequestBody Map map){
            Integer userId=(Integer) map.get("userId");
            Result result = otherService.searchUnPay(userId);
            return result;
        }






}
