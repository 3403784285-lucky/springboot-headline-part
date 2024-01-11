package com.atguigu.controller;
import com.atguigu.pojo.OrderSku;
import com.atguigu.service.*;
import com.atguigu.utils.OrderAutoEntity;
import com.atguigu.utils.Result;
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
    public Result judgeOrderPreview(@RequestBody OrderSku order){
        Result result = orderSkuService.judgeOrderPreview(order);
        return result;
    }


    @PostMapping("cancelOrder")
    public Result cancelOrder(@RequestBody Map map){
        Integer orderSkuId=(Integer) map.get("orderSkuId");
        Result result = orderSkuService.cancelOrder(orderSkuId+"");
        return result;
    }

}
