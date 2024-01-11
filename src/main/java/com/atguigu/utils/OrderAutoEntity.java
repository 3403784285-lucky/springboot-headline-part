package com.atguigu.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class OrderAutoEntity implements Delayed {

    //订单编号
    private String orderNo;
    //订单具体的过期时间
    private long expire;
    //订单过期时间间隔定义（毫秒），这里设置15分钟
    public static final long expireTime = TimeUnit.MINUTES.toMillis(15);

    public OrderAutoEntity(String orderNo, LocalDateTime orderTime) {
        this.orderNo = orderNo;
        //转成毫秒
        long creatTime = orderTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        this.expire = expireTime + creatTime;
    }

    /**
     * 获取剩余时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS));
    }

}

