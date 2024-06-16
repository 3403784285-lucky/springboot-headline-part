package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName appoint
 */
@TableName(value ="appoint")
@Data
public class Appoint implements Serializable {
    private Integer workerId;

    private Integer orderId;

    private Integer workStatus;

    private static final long serialVersionUID = 1L;
}