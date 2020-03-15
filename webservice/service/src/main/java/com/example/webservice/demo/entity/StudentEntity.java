package com.example.webservice.demo.entity;

import lombok.Data;

/**
 * @author xzy
 * @date 2020-03-15 18:24
 * 说明：学生表对应的实体
 */
@Data
public class StudentEntity {
    private String name;
    private Integer age;

    public StudentEntity(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public StudentEntity() {
    }
}
