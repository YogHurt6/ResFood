package com.yc.resfood.model;

import com.yc.bean.Resfood;

import java.io.Serializable;

/**
 * @program: yc119res
 * @description:
 * @author: zy
 * @create: 2022-10-09 19:37
 */
public class CartItem implements Serializable {
    private Resfood food;
    private Integer num;
    private Double smallCount;


    public Double getSmallCount() {
        if(   food!=null) {
            smallCount = this.food.getRealprice() * this.num;
        }
        return smallCount;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "food=" + food +
                ", num=" + num +
                ", smallCount=" + smallCount +
                '}';
    }

    public void setFood(Resfood food) {
        this.food = food;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setSmallCount(Double smallCount) {
        this.smallCount = smallCount;
    }

    public Resfood getFood() {
        return food;
    }

    public Integer getNum() {
        return num;
    }

}
