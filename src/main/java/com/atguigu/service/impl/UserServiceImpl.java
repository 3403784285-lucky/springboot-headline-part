package com.atguigu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.atguigu.mapper.OrderSkuMapper;
import com.atguigu.pojo.OrderSku;
import com.atguigu.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.mapper.UserMapper;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.URIParameter;
import java.util.*;

/**
* @author zplaz
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-12-05 19:04:27
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired(required = false)
    private  UserMapper userMapper;
    @Autowired(required = false)
    private OrderSkuMapper orderSkuMapper;



    @Autowired
    private JwtHelper jwtHelper;
    /**
     * 登录业务
     *
     *    1.根据账号 ，查询用户对象 ---loginUser
     *    2.如果用户对象为null， 查询失败 ， 账号错误 ！ 501
     *    3.对比，密码，失败 返回503错误
     *    4.根据用户id生成一个token， token -> result 返回
     *
     */
    @Override
    public Result login(Long id,String password) {

        // 根据账号查询数据
        System.out.println(id+"-->"+password);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,id);
        lambdaQueryWrapper.ne(User::getStatus,"-1");
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);
        System.out.println(loginUser);

        if(loginUser == null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        // 对比密码
        if(!StringUtils.isEmpty(password)  && MD5Util.encrypt(password).equals(loginUser.getPassword())){
            //登录成功
            // 根据用户id生成 token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getId()));
            System.out.println("token = " + token);
            Map data = new HashMap();
            data.put("token",token);
            data.put("userId",id);
            data.put("email",loginUser.getEmail());
            data.put("nickname",loginUser.getNickname());
            data.put("userPic",loginUser.getUserPic());

            System.out.println("哈哈哈");
            return Result.ok(data);
        }


        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }


    /**
     * 根据token 获取用户数据
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {

        //是否过期
        boolean expiration = jwtHelper.isExpiration(token);
        if(expiration){
            //失败 ，未登录
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        int userID = jwtHelper.getUserId(token).intValue();
        User user = userMapper.selectById(userID);

        user.setPassword("");
        System.out.println("user = " + user);
        Map data = new HashMap();
        data.put("loginUser",user);

        return Result.ok(data);
    }

    /**
     * 检查账号是否可用
     *  1.根据账号进行count查询
     *  2.count == 0 可用
     *  3.count > 0  不可用
     *
     * @param username
     * @return
     */
    @Override
    public Result checkUserName(String username) {

        LambdaQueryWrapper<User> queryWrapper
                = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,username);
        Long count = userMapper.selectCount(queryWrapper);

        if (count==0) {
            return Result.ok(null);
        }

        return Result.build(null,ResultCodeEnum.USERNAME_USED);

    }

    /**
     * 注册业务
     *  1.检查账号是否已经被注册
     *  2.密码加密处理
     *  3.账号数据保存
     *  4.返回结果
     *
     */

    @Override
    public Result regist(String email,String password,String rePassword,String confirm) {

        User user=new User();
        System.out.println(confirm);

        if (UtilStorage.confirm==null||!UtilStorage.confirm.equals(confirm))
            return Result.build(null,ResultCodeEnum.CONFIRM_ERROR);;

        user.setPassword(MD5Util.encrypt(password));
        user.setEmail(email);
        user.setStatus("0");



        userMapper.insert(user);

        return Result.ok(null);
    }

    @Override
    public Result getConfirm(JavaMailSender javaMailSender, String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        //设置邮件主题
        msg.setSubject("验证码");

        String confirm=""+(new Random().nextInt(9000)+1000);
        UtilStorage.confirm=confirm;
        UtilStorage.resetConfirm();
        //设置邮件内容
        msg.setText(confirm);

        //邮件是谁发的，要写的和application.properties发邮件的用户名一致，不然发布出去
        msg.setFrom("3458821194@qq.com");
        //设置发邮件的时间
        msg.setSentDate(new Date());
        //接收方的qq邮箱
        msg.setTo(email);
        javaMailSender.send(msg);
        return Result.ok(null);

    }

    @Override
    public Result userReturn(OrderSku orderSku) {
       orderSku.setOrderStatus("-2");
       System.out.println(orderSkuMapper.updateById(orderSku));
       return Result.ok(null);
    }



    @Override
    public Result userReturnApplied(OrderSku orderSku) {
        User user=((User)(userMapper.selectById(orderSku.getUserId())));
        BigDecimal balance=user.getBalance().add(orderSku.getTotalPrice());
        user.setBalance(balance);
        userMapper.updateById(user);
        OrderSku orderSku1=new OrderSku();
        orderSku1.setOrderSkuId(orderSku.getOrderSkuId());
        orderSku1.setOrderStatus("-3");
        orderSkuMapper.updateById(orderSku1);
        return Result.ok(null);

    }

    @Override
    public Result userDelete(int orderSkuId) {
        System.out.println(orderSkuId);
        System.out.println(orderSkuMapper.deleteById(orderSkuId));
        return  Result.ok(null);
    }

    @Override
    public Result userUpdate(User user) {
        int updatedUser = userMapper.updateById(user);
        return Result.ok(updatedUser);
    }
    @Override
    public Result getAllowance(int userId) {
        User user=userMapper.selectById(userId);
        return  Result.ok(user.getBalance());

    }

    @Override
    public Result pay(int userId,String password,BigDecimal totalPrice,int orderSkuId) {
        User user=userMapper.selectById(userId);
        OrderSku orderSku1=orderSkuMapper.selectById(orderSkuId);
        System.out.println(orderSkuId);
        if(orderSku1.getOrderStatus()!="-1"){
            System.out.println(password+"--->"+MD5Util.encrypt(password));
            if (MD5Util.encrypt(password).equals(user.getPassword())){
                if(user.getBalance().compareTo(totalPrice)==0||user.getBalance().compareTo(totalPrice)==1){
                    UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
                    BigDecimal h=user.getBalance().subtract(totalPrice);
                    User user1=new User();
                    user1.setBalance(h);
                    updateWrapper.eq("id",userId);
                    userMapper.update(user1, updateWrapper);
                    UpdateWrapper<OrderSku> updateWrapperOrder = new UpdateWrapper<>();
                    updateWrapperOrder.set("order_status", 1) // 设置要更新的字段及值
                            .eq("order_sku_id", orderSkuId); // 设置更新条件，这里假设 id 是主键
                    // 执行更新操作
                    orderSkuMapper.update(null, updateWrapperOrder);


                 return  Result.build(null,ResultCodeEnum.SUCCESS);
                }else{
                    return  Result.build(null,ResultCodeEnum.BALANCE_LESS);
                }
            }
            else{
                return  Result.build(null,ResultCodeEnum.PASSWORD_ERROR);

            }
        }else{
            return  Result.build(null,ResultCodeEnum.ORDER_EXP);
        }
    }

    @Override
    public Result manageUser(Page<User>page) {
        IPage<User>userIPage =userMapper.selectPage(page,null);
        return Result.ok(userIPage);
    }

    @Override
    public Result freeze(int userId) {
        User user=new User();
        user.setId(userId);
        user.setStatus("-1");
        userMapper.updateById(user);
        return Result.ok(null);


    }

    @Override
    public Result unfreeze(int userId) {
        User user=new User();
        user.setId(userId);
        user.setStatus("0");
        userMapper.updateById(user);
        return Result.ok(null);
    }

    @Override
    public Result getStatus(int userId) {
        User user=userMapper.selectById(userId);
        return Result.ok(user.getStatus());
    }

}




