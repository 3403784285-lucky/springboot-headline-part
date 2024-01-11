package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.springframework.core.SpringVersion;

/**
 * @TableName sku
 */
@TableName(value ="sku")
@Data
public class Sku implements Serializable {
    private Integer skuId;

    private Integer spuId;

    private Double houseSize;

    private String houseType;

    private BigDecimal housePrice;

    private String housePosition;

    private String houseStatus;

    private String houseOnto;

    private String housePic;

    private String unitType;



    private static final long serialVersionUID = 1L;
}