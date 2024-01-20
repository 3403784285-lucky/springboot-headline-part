package com.atguigu.service;

import com.atguigu.utils.Result;
import org.springframework.stereotype.Service;


public interface OtherService {

    Result searchUnPay(int userId);

    Result searchDoing(int userId);

    Result searchFinish(int userId);
}
