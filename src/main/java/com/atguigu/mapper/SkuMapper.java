package com.atguigu.mapper;

import com.atguigu.pojo.Sku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author zplaz
* @description 针对表【sku】的数据库操作Mapper
* @createDate 2023-12-22 00:51:30
* @Entity com.atguigu.pojo.Sku
*/
public interface SkuMapper extends BaseMapper<Sku> {

        @Select("select * from sku,spu where sku.spu_id=spu.spu_id and house_status!='0'")
        List<Sku> getDetail();

        @Select("select * from sku,spu where sku.spu_id=spu.spu_id and sku.sku_id=#{skuId}")
        Sku selectDetailById(int skuId);
        @Select("select * from sku left join spu on sku.spu_id=spu.spu_id")
        IPage<Sku> selectHouses(Page<Sku> page);
        @Select("select * from sku,focuslist,spu where sku.sku_id=house_id and sku.spu_id=spu.spu_id and focus_id=#{userId};")
        List<Sku> searchUserFocus(int userId);
        @Select("select * from sku,spu where sku.spu_id=spu.spu_id and house_owner=#{userId};")
        List<Sku> searchUserDeclare(int userId);
        List<Sku> searchByKey(@Param("keyBig") String keyBig, @Param("keySmall") String keySmall);

        List<Sku> searchOption(@Param("search1") Integer search1, @Param("search2") Integer search2,@Param("search3") int search3);
}




