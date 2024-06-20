package com.atguigu.mapper;

import com.atguigu.pojo.OrderSku;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OtherMapper {


    @Select("select * from order_sku,sku where order_sku.sku_id=sku.sku_id and order_sku.user_id=#{userId} and order_sku.order_status='0'")
    public List<OrderSku> searchUnPay(int userId);
    @Select("select * from order_sku,sku where order_sku.sku_id=sku.sku_id and order_sku.user_id=#{userId} and(order_sku.order_status='1' or order_sku.order_status='-2') ")
    List<OrderSku> searchDoing(int userId);
    @Select("select * from order_sku,sku where order_sku.sku_id=sku.sku_id and order_sku.user_id=#{userId} and( order_sku.order_status='2' or  order_sku.order_status='3')")
    List<OrderSku> searchFinish(int userId);
    @Select("select * from order_sku,sku where order_sku.sku_id=sku.sku_id and order_sku.user_id=#{userId} and( order_sku.order_status='-3' or  order_sku.order_status='-1')")
    List<OrderSku> searchCancel(int userId);
}
