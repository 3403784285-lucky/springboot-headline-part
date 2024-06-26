package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.SpringVersion;

/**
 * @TableName sku
 */
@TableName(value ="sku")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sku implements Serializable {
    @TableId(value = "sku_id", type = IdType.AUTO)
    private Integer skuId;

    private Integer spuId;

    @TableField(exist = false)
    private String houseName;

    private Double houseSize;

    private String houseType;

    private BigDecimal housePrice;

    private String housePosition;

    private String houseStatus;

    private String houseOnto;

    private String housePic;

    private String houseCity;

    private String unitType;

    private int houseOwner;



    private static final long serialVersionUID = 1L;
}