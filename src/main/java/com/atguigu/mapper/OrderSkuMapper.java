package com.atguigu.mapper;

import com.atguigu.pojo.OrderSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author zplaz
* @description 针对表【order_sku】的数据库操作Mapper
* @createDate 2023-12-24 02:44:22
* @Entity com.atguigu.pojo.OrderSku
*/
public interface OrderSkuMapper extends BaseMapper<OrderSku> {

    @Select("select * from order_sku,sku where order_sku.sku_id=sku.sku_id ")
    IPage<OrderSku> selectPreviews(Page<OrderSku>page);

    @Select("select * from order_sku where order_sku.user_id=#{userId} and order_sku.sku_id=#{skuId}")
    List<OrderSku> selectOrderStatus(int userId, int skuId);
}




