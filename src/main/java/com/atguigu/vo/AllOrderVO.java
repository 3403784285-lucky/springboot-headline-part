package com.atguigu.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AllOrderVO implements Serializable {
    // 订单金额
    private BigDecimal Price;
    // 订单状态
    // -1已取消
    //-2退款中
    //-3已退款
    //1 支付成功
    //0未支付
    //2已完成
    private String status;
    // 完成日期
    private Date date;
}
