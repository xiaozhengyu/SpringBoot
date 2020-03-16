package com.example.demo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xzy
 * @date 2020-03-16 21:13
 * 说明：猫
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("cat")
public class Cat {
    /**
     * 名字
     */
    @XStreamAlias("catName")
    private String name;
    /**
     * 年龄
     */
    @XStreamAlias("catAge")
    private Integer age;
    /**
     * 拥有的玩具球
     */
    @XStreamImplicit
    @XStreamAlias("catBalls")
    private List<Ball> balls;
}
