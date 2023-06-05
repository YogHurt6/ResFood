package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resorder implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer roid;
    private Integer userid;
    private String address;
    private String tel;

    private String ordertime; //在业务层确定数据(Date->格式化->字符串)
    private String deliverytime;

    private String ps;  //附言
    private Integer status; //状态


}
