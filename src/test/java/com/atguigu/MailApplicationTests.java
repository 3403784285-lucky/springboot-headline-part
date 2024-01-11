package com.atguigu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

@SpringBootTest
class MailApplicationTests {

    @Autowired(required = false)
    JavaMailSender javaMailSender; //当你引入了javaMailSender并且在pom文件里面引入了mail依赖就自动准备好了
    @Test
    void contextLoads() {
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
        msg.setTo("3403784285@qq.com");
        javaMailSender.send(msg);
    }

}