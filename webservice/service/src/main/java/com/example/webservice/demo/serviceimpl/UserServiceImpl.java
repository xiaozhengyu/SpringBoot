package com.example.webservice.demo.serviceimpl;

import com.example.webservice.demo.entity.UserEntity;
import com.example.webservice.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xzy
 * @date 2020-03-15 20:02
 * 说明：
 */
@WebService(serviceName = "UserService",
        targetNamespace = "http://service.demo.webservice.example.com",
        endpointInterface = "com.example.webservice.demo.service.UserService")
@Service
public class UserServiceImpl implements UserService {
    /**
     * 获取所有用户信息
     *
     * @return - 所有用户信息
     */
    @Override
    public List<UserEntity> getAllUser() {
        System.out.println("getAllUser():监听到请求");
        List<UserEntity> users = new ArrayList<>();
        UserEntity user1 = new UserEntity("wangwu", "f");
        UserEntity user2 = new UserEntity("xiaoyu", "m");
        users.add(user1);
        users.add(user2);
        return users;
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username - 用户名
     * @return - 用户信息
     */
    @Override
    public UserEntity getUserByName(String username) {
        System.out.println("getUserByName():监听到请求，参数username = " + username);
        if ("张三".equals(username)) {
            return new UserEntity("张三", "m");
        } else {
            return new UserEntity("helloWorld", "!");
        }
    }
}
