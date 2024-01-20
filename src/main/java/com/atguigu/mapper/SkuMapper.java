package com.atguigu.mapper;

import com.atguigu.pojo.Sku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author zplaz
* @description 针对表【sku】的数据库操作Mapper
* @createDate 2023-12-22 00:51:30
* @Entity com.atguigu.pojo.Sku
*/
public interface SkuMapper extends BaseMapper<Sku> {

        @Select("select * from sku,spu where sku.spu_id=spu.spu_id")
        List<Sku> getDetail();

        @Select("select * from sku,spu where sku.spu_id=spu.spu_id and sku.sku_id=#{skuId}")
        Sku selectDetailById(int skuId);
}




