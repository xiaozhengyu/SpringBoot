package com.example.webservice.demo.service;

import com.example.webservice.demo.entity.UserEntity;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * @author xzy
 * @date 2020-03-15 18:26
 * 说明：对外提供的用户信息服务
 */
@WebService(targetNamespace = "http://service.demo.webservice.example.com")
public interface UserService {
    /**
     * 获取所有用户信息
     *
     * @return - 所有用户信息
     */
    @WebMethod
    List<UserEntity> getAllUser();

    /**
     * 根据用户名获取用户信息
     *
     * @param username - 用户名
     * @return - 用户信息
     */
    @WebMethod
    UserEntity getUserByName(@WebParam(name = "username") String username);
}
