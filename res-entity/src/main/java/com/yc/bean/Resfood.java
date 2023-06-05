package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resfood implements Serializable {

    @TableId(type = IdType.AUTO) //当前键为mysql表的主键,自增
    private Integer fid;

    private String fname;
    private Double normprice;
    private Double realprice;
    private String detail;
    private String fphoto;

    //增加一个属性,点赞数    这个属性的值是redis提供的,不是数据库
    @TableField(select = false)
    private Long praise;

}
