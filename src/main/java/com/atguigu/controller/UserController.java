package com.atguigu.controller;


import com.atguigu.mapper.UserMapper;
import com.atguigu.pojo.Focuslist;
import com.atguigu.pojo.OrderSku;
import com.atguigu.pojo.User;
import com.atguigu.service.FocuslistService;
import com.atguigu.service.OrderSkuService;
import com.atguigu.service.OtherService;
import com.atguigu.service.UserService;
import com.atguigu.utils.AliOssUtil;
import com.atguigu.utils.Result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;


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

    @Autowired(required = false)
    private OrderSkuService orderSkuService;

    @Autowired
    private FocuslistService focuslistService;

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired(required = false)
    private UserMapper userMapper;


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

                @PostMapping("doing")
                public Result searchDoing(@RequestBody Map map){
                    Integer userId=(Integer) map.get("userId");
                    Result result = otherService.searchDoing(userId);
                    return result;
                }

                @PostMapping("finish")
                public Result searchFinish(@RequestBody Map map){
                    Integer userId=(Integer) map.get("userId");
                    Result result = otherService.searchFinish(userId);
                    return result;
                }

                @PostMapping("return")
                public Result userReturn(@RequestBody OrderSku orderSku){
                    System.out.println(orderSku);
                    Result result = userService.userReturn(orderSku);
                    return result;
                }

                @PostMapping("delete")
                public Result userDelete(@RequestBody OrderSku orderSku){
                    System.out.println(orderSku);
                    int orderSkuId=orderSku.getOrderSkuId();
                    Result result = userService.userDelete(orderSkuId);
                    return result;
                }

    @PostMapping("cancelOrder")
    public Result cancelOrder(@RequestBody Map map){
        Integer orderSkuId=(Integer) map.get("orderSkuId");
        Result result = orderSkuService.cancelOrder(orderSkuId+"");
        return result;
    }
                //获取用户余额
                @PostMapping("allowance")
                public Result getAllowance(@RequestBody Map map){
                    int userId=(int)map.get("userId");
                    Result result = userService.getAllowance(userId);
                    return result;
                }


                @PostMapping("pay")
                public Result toPay(@RequestBody Map map){
                    System.out.println("dfdsf");
                    System.out.println("map = " + map.get("orderSkuId"));
                    Result result = userService.pay((int)map.get("userId"),(String) map.get("password"),new BigDecimal((int)map.get("totalPrice")),(int)map.get("orderSkuId"));
                    return result;
                }



                @PostMapping("manage")
                public Result manageUser(@RequestBody Map map){
                    Result result = userService.manageUser(new Page<User>((int)map.get("page"),4));
                    return result;
                }

    /**
     * 冻结用户
     */
    @PostMapping("freeze")
    public Result freeze(@RequestBody Map map){
        Result result = userService.freeze((int)map.get("userId"));
        return result;
    }

    @PostMapping("unfreeze")
    public Result unfreeze(@RequestBody Map map){
        Result result = userService.unfreeze((int)map.get("userId"));
        return result;
    }

    //退款函数
    @PostMapping("returnMoney")
    public Result returnMoney(@RequestBody OrderSku orderSku){
        Result result = userService. userReturnApplied(orderSku);
        return result;
    }

    //查询用户的所有订单
    @PostMapping("askOrder")
    public Result askOrder(@RequestBody Map map){
        Result result = orderSkuService. askOrder((int)map.get("userId"));
        return result;
    }

    //搜索已取消或者已经退款的订单
    @PostMapping("cancel")
    public Result getCancel(@RequestBody Map map){
        Result result = otherService.getCancel((int)map.get("userId"));
        return result;
    }
    @PutMapping("updateInfo")
    public Result updateById(@RequestBody User user) {
        userService.updateById(user);
        return Result.ok(null);
    }

    /**
     * 修改个人信息
     * @param f
     * @param userId
     * @param nickname
     * @param email
     * @param fileUrl
     * @return
     */
    @PutMapping("/upload/{userId}")
    public Result<String> upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable Long userId,
            @RequestParam("nickname") String nickname,
            @RequestParam("email") String email,
            @RequestParam(value = "fileUrl", required = false) String fileUrl) {

        try {
            String filePath;

            if (file != null && !file.isEmpty()) {

                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String objectName = UUID.randomUUID().toString() + extension;

                filePath = aliOssUtil.upload(file.getBytes(), objectName);
            } else if (fileUrl != null && !fileUrl.isEmpty()) {

                filePath = fileUrl;
            } else {
                return Result.build("未提供文件或URL");
            }

            userMapper.updateUserProfile(userId, nickname, filePath, email);
            System.out.println("filePath = " + filePath);
            return Result.ok(filePath);
        } catch (IOException e) {
            return Result.build("上传失败");
        }
    }






}
