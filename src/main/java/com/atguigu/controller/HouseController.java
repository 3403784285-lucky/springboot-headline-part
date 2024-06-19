package com.atguigu.controller;
import com.atguigu.pojo.Comment;
import com.atguigu.pojo.OrderSku;
import com.atguigu.pojo.Sku;
import com.atguigu.service.*;
import com.atguigu.utils.OrderAutoEntity;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
@RestController
@RequestMapping("house")
@CrossOrigin
public class HouseController {
    @Autowired
    private SkuService skuService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @Autowired
    private HouseManageService houseManageService;

    @Autowired
    private HouseOrderService houseOrderService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private OrderCaneService orderCaneService;

    @Autowired
    private OrderSkuService orderSkuService;

    @PostMapping("detail")
    public Result getHouseDetail(){
        Result result = skuService.getHouseDetail();
        return result;
    }
    @PostMapping("search1")
    public Result getPreviewDetail(@RequestBody Map map){
        Result result = goodsService.getPreviewDetail((Integer) map.get("skuId"));
        return result;
    }
    @PostMapping("read")
    public Result readOrder(@RequestBody Map map){
        Result result = orderSkuService.readOrder((Integer) map.get("id"));
        return result;
    }
    @PostMapping("preview1")
    public Result insertPreviewDetail(@RequestBody OrderSku order){
        Result result = orderSkuService.insertPreview(order);
        LocalDateTime localDateTime = order.getOrderCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(order);
        OrderAutoEntity orderAutoEntity = new OrderAutoEntity(order.getOrderSkuId()+"", localDateTime);
        orderCaneService.add(orderAutoEntity);
        return result;
    }

    @PostMapping("preview2")
    public Result judgeOrderPreview(@RequestBody Map map){
        Result result = orderSkuService.judgeOrderPreview((int)map.get("skuId"));
        return result;
    }


    @PostMapping("certain")
    public Result houseCertain(@RequestBody Map map){
        int skuId=(Integer)map.get("skuId");
        Result result = skuService.houseCertain(skuId);
        return result;
    }

    @PostMapping("declare")
    public Result declare(@RequestBody Map map){
        System.out.println(map+"嘿嘿");
        Result result = skuService.declare(map);
        return result;
    }

    @PostMapping("manage")
    public Result manageHouse(@RequestBody Map map){
        Result result = skuService.manageHouse(new Page<Sku>((int)map.get("page"),5));
        return result;
    }

    @PostMapping("previewManage")
    public Result managePreview(@RequestBody Map map){
        Result result = orderSkuService.managePreview(new Page<OrderSku>((int)map.get("page"),4));
        return result;
    }

    //指派人员搜索
    @PostMapping("appointSearch")
    public Result appointSearch(){
        Result result = orderSkuService.appointSearch();
        return result;
    }
    //确定指派
    @PostMapping("appoint")
    public Result appoint(@RequestBody Map map){
        Result result = orderSkuService.appoint((int)map.get("orderId"),(int)map.get("workerId"));
        return result;
    }

    //上架房屋
    @PostMapping("uploadHouse")
    public Result uploadHouse(@RequestBody Map map){
        Result result = orderSkuService.uploadHouse((int)map.get("houseId"));
        return result;
    }
    //下架房屋
    @PostMapping("takeOffHouse")
    public Result takeOffHouse(@RequestBody Map map){
        Result result = orderSkuService.takeOffHouse((int)map.get("houseId"));
        return result;
    }


    //更改编辑信息
    @PostMapping("initComment")
    public Result initComment(@RequestBody Map map){
        Result result = commentService.initComment((int)map.get("houseId"));
        return result;
    }

    @PostMapping("declareComment")
    public Result initComment(@RequestBody Comment comment){
        Result result = commentService.declareComment(comment);
        return result;
    }

    @PostMapping("replyComment")
    public Result replyComment(@RequestBody Comment comment){
        Result result = commentService.replyComment(comment);
        return result;
    }

    @PostMapping("editHouse")
    public Result editHouse(@RequestBody Sku sku){
        Result result = orderSkuService.editHouse(sku);
        return result;
    }
    @PostMapping("return")
    public Result returnMoney(@RequestBody OrderSku orderSku){
        Result result = userService. userReturnApplied(orderSku);
        return result;
    }


    //初始化房屋时间小
    @PostMapping("initSmallTime")
    public Result initSmallTime(){
        Result result = houseManageService. initSmallTime();
        return result;
    }
    //初始化房屋时间大
    @PostMapping("initBigTime")
    public Result initBigTime(){
        Result result = houseOrderService. initBigTime();
        return result;
    }
    //保存房屋时间小
    @PostMapping("editSmallTime")
    public Result editSmallTime(@RequestBody Map map){
        Result result = houseManageService.editSmallTime(map);
        return result;
    }
    //保存房屋时间大
    @PostMapping("editBigTime")
    public Result editBigTime(@RequestBody Map map){
        System.out.println("map = " + map);
        Result result = houseOrderService.editBigTime(map);
        return result;
    }

    //保存房屋价格
    @PostMapping("editMoney")
    public Result editMoney(@RequestBody Map map){
        Result result = goodsService.editMoney(map);
        return result;
    }

    //初始化房屋时间大
    @PostMapping("initMoney")
    public Result initMoney(){
        Result result = goodsService. initMoney();
        return result;
    }



    //搜索特定房屋预约时间大
    @PostMapping("orderTime")
    public Result orderTime(@RequestBody Map map){
        System.out.println("map = " + map);
        int skuId=(Integer) map.get("skuId");
        Result result = houseOrderService.orderTime(skuId);
        return result;
    }

    //搜索特定房屋预约时间小
    @PostMapping("manageTime")
    public Result manageTime(@RequestBody Map map){
        System.out.println("map = " + map);
        int skuId=(Integer) map.get("skuId");
        Result result = houseManageService.manageTime(skuId);
        return result;
    }

//搜索用户关注的所有房屋
@PostMapping("searchUserFocus")
public Result searchUserFocus(@RequestBody Map map){
    int userId=(Integer) map.get("userId");
    Result result = skuService.searchUserFocus(userId);
    return result;
}

//搜索用户发布的所有房屋
@PostMapping("searchUserDeclare")
public Result searchUserDeclare(@RequestBody Map map){
    int userId=(Integer) map.get("userId");
    Result result = skuService.searchUserDeclare(userId);
    return result;
}


//搜索用户发布的所有房屋
@PostMapping("searchByKey")
public Result searchByKey(@RequestBody Map map){
    Result result = skuService.searchByKey(map);
    return result;
}

//筛选搜索
@PostMapping("searchOption")
public Result searchOption(@RequestBody Map map){
    Result result = skuService.searchOption(map);
    return result;
}


}
