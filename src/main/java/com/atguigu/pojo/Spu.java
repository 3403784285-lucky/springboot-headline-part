package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName spu
 */
@TableName(value ="spu")
@Data
public class Spu implements Serializable {
    private Integer spuId;

    private String houseName;

    private static final long serialVersionUID = 1L;
}