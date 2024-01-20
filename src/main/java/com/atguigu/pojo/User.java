package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @TableName user
 */

@Data
public class User implements Serializable {
    private Integer id;

    private String password;

    private String nickname;

    private String email;

    private String userPic;

    private BigDecimal possession;


    private BigDecimal balance;

    private static final long serialVersionUID = 1L;
}