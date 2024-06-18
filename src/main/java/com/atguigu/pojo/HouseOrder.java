package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TableName house_order
 */
@TableName(value ="house_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseOrder implements Serializable {
    @TableId(value = "house_order_id", type = IdType.AUTO)
    private Integer houseOrderId;
    private Integer houseId;
    private String houseOrderTime;
    private static final long serialVersionUID = 1L;
}