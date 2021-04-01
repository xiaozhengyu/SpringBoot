package com.xzy.mybatis.entity;

import lombok.Data;

/**
 * @author xzy
 * @date 2021-03-31 15:08
 * 说明：
 */
@Data
public class Employee {
    private Integer id;
    private String lastName;
    private String gender;
    private String email;
}
