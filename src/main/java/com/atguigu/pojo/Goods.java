package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    private Integer orderGoodsId;

    private Integer orderSpuId;

    private BigDecimal reservePrice;

    private Integer skuId;

    private static final long serialVersionUID = 1L;
}