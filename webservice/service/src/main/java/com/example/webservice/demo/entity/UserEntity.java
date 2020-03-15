package com.example.webservice.demo.entity;

import lombok.Data;

/**
 * @author xzy
 * @date 2020-03-15 18:25
 * 说明：user表对应的实体类
 */
@Data
public class UserEntity {
    private String name;
    private String sex;

    public UserEntity(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public UserEntity() {
    }
}
