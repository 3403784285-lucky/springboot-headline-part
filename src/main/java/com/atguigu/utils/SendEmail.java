package com.atguigu.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

public class SendEmail {


    public static void sendEmail(JavaMailSender javaMailSender,String emailAddress)
    {
        //构建邮件
        SimpleMailMessage msg = new SimpleMailMessage();
        //设置邮件主题
        msg.setSubject("这是测试邮件主题");
        //设置邮件内容
        msg.setText("爱丽丝，我喜欢你，可以做我的女朋友吗！！！");
        //邮件是谁发的，要写的和application.properties发邮件的用户名一致，不然发布出去
        msg.setFrom("3458821194@qq.com");
        //设置发邮件的时间
        msg.setSentDate(new Date());
        //接收方的qq邮箱
        msg.setTo(emailAddress);
        javaMailSender.send(msg);
    }
}
