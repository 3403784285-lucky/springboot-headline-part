package com.atguigu.utils;

/**
 * 统一返回结果状态信息类
 *
 */
public enum ResultCodeEnum {

    SUCCESS(200,"success"),
    USERNAME_ERROR(501,"usernameError"),
    PASSWORD_ERROR(503,"error"),
    NOTLOGIN(504,"notLogin"),
    USERNAME_USED(505,"userNameUsed"),
    CONFIRM_ERROR(506,"confirmError"),
    BALANCE_LESS(507,"fail"),
    ORDER_EXP(508,"time");



    private Integer code;
    private String message;
    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
