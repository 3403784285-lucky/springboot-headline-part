package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName focuslist
 */
@TableName(value ="focuslist")
@Data
public class Focuslist implements Serializable {
    private Integer id;

    private Integer focusId;

    private Integer houseId;

    private static final long serialVersionUID = 1L;
}