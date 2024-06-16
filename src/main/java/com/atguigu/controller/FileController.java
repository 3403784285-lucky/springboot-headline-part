package com.atguigu.controller;

import com.atguigu.pojo.Sku;
import com.atguigu.service.SkuService;
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

@RestController
@CrossOrigin
@RequestMapping("file")
public class FileController {

    @Autowired
    private SkuService skuService;

    // 上传文件保存的路径
    private static final String UPLOAD_DIR = "img/";


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
        // 处理文件上传逻辑
        if (file.isEmpty()) {
            return Result.build(null, ResultCodeEnum.FILE_NULL);
        }

        try {
            // 获取上传的文件名
            String fileName = file.getOriginalFilename();
            String fileP="E:\\test-vue-big\\public\\"+UPLOAD_DIR;
            String filePCopy="http://127.0.0.1:5173/"+UPLOAD_DIR;


            // 构建上传路径
            Path uploadPath =Paths.get(fileP);


            // 如果目录不存在，则创建目录
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 构建文件存储路径
            Path filePath = uploadPath.resolve(fileName);

            if((new File(filePath.toString())).exists()){

            }else{
                Files.copy(file.getInputStream(), filePath);
            }
            System.out.println(filePath.toUri());
            // 将文件保存到本地

            Sku sku=new Sku(Integer.parseInt(skuId),Integer.parseInt(spuId),houseName,Double.parseDouble(houseSize),houseType,new BigDecimal(housePrice),housePosition,houseStatus,houseOnto,filePCopy+fileName,houseCity,unitType,Integer.parseInt(houseOwner));
            skuService.updateById(sku);

            // 返回上传成功的消息
            return Result.ok(sku);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.build(null,500,"error");

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

