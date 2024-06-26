package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @TableName order_sku
 */
@TableName(value ="order_sku")
@Data
public class OrderSku implements Serializable {
    @TableId(value = "order_sku_id", type = IdType.AUTO)
    private Integer orderSkuId;

    private Integer orderSpuId;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm:ss")
    private Date orderBadTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderCreateTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm:ss")
    private Date orderEndTime;

    private Integer skuId;

    private BigDecimal totalPrice;

    private  Integer userId;

    // 订单状态
    private  String orderStatus;

    @TableField(exist = false)
    private  String housePic;

    private String description;

    @TableField(exist = false)
    private String password;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm:ss")
    private Date orderOverTime;

    private static final long serialVersionUID = 1L;
}
