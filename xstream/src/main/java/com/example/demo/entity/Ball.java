package com.example.demo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xzy
 * @date 2020-03-16 21:14
 * 说明：玩具球
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("ball")
public class Ball {
    /**
     * 颜色
     */
    @XStreamAlias("ballColor")
    private String color;
    /**
     * 价格
     */
    @XStreamAlias("ballPrice")
    private Double price;
}
