package com.atguigu.mapper;

import com.atguigu.pojo.OrderSku;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OtherMapper {


    @Select("select * from order_sku,sku where order_sku.sku_id=sku.sku_id and order_sku.user_id=#{userId} and order_sku.order_status='0'")
    public List<OrderSku> searchUnPay(int userId);

}
