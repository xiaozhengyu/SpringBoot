package com.example.webservice.demo.service;

import com.example.webservice.demo.entity.StudentEntity;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * @author xzy
 * @date 2020-03-15 18:26
 * 说明：对外提供的学生信息服务
 * targetNamespace - 命名空间，写一个有意义的http地址就可以，写成包名的倒序是为了易于阅读。
 */
@WebService(targetNamespace = "http://service.demo.webservice.example.com")
public interface StudentService {
    /**
     * 获取所有学生信息
     *
     * @return - 所有学生信息
     */
    @WebMethod
    List<StudentEntity> getAll();
}
