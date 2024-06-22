package com.atguigu.controller;

import com.atguigu.pojo.Sku;
import com.atguigu.service.SkuService;
import com.atguigu.utils.AliOssUtil;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("file")
public class FileController {

    @Autowired
    private SkuService skuService;

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("save")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("skuId") String skuId,
                                   @RequestParam("spuId") String spuId,
                                   @RequestParam("houseOwner") String houseOwner,
                                   @RequestParam("houseSize") String houseSize,
                                   @RequestParam("houseName") String houseName,
                                   @RequestParam("houseType") String houseType,
                                   @RequestParam("houseStatus") String houseStatus,
                                   @RequestParam("housePic") String housePic,
                                   @RequestParam("housePosition") String housePosition,
                                   @RequestParam("housePrice") String housePrice,
                                   @RequestParam("houseCity") String houseCity,
                                   @RequestParam("houseOnto") String houseOnto,
                                   @RequestParam("unitType") String unitType) {
        if (file.isEmpty()) {
            return Result.build(null, ResultCodeEnum.FILE_NULL);
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + extension;

            String fileUrl = aliOssUtil.upload(file.getBytes(), objectName);

            Sku sku = new Sku(
                    Integer.parseInt(skuId),
                    Integer.parseInt(spuId),
                    houseName,
                    Double.parseDouble(houseSize),
                    houseType,
                    new BigDecimal(housePrice),
                    housePosition,
                    houseStatus,
                    houseOnto,
                    fileUrl,
                    houseCity,
                    unitType,
                    Integer.parseInt(houseOwner)
            );
            skuService.updateById(sku);

            return Result.ok(sku);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.build(null, 500, "error");
        }
    }
    @PostMapping("saveNoFile")
    public Result handleFileUpload(
                                   @RequestParam("skuId") String skuId,
                                   @RequestParam("spuId") String spuId,
                                   @RequestParam("houseOwner") String houseOwner,
                                   @RequestParam("houseSize") String houseSize,
                                   @RequestParam("houseName") String houseName,
                                   @RequestParam("houseType") String houseType,
                                   @RequestParam("houseStatus") String houseStatus,
                                   @RequestParam("housePic") String housePic,
                                   @RequestParam("housePosition") String housePosition,
                                   @RequestParam("housePrice") String housePrice,
                                   @RequestParam("houseCity") String houseCity,
                                   @RequestParam("houseOnto") String houseOnto,
                                   @RequestParam("unitType") String unitType) {


            Sku sku=new Sku(Integer.parseInt(skuId),Integer.parseInt(spuId),houseName,Double.parseDouble(houseSize),houseType,new BigDecimal(housePrice),housePosition,houseStatus,houseOnto,housePic,houseCity,unitType,Integer.parseInt(houseOwner));
            skuService.updateById(sku);

            // 返回上传成功的消息
            return Result.ok(sku);


    }
}

