package com.example.webservice.demo.config;

import com.example.webservice.demo.service.StudentService;
import com.example.webservice.demo.service.UserService;
import org.apache.cxf.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.cxf.jaxws.EndpointImpl;

import javax.xml.ws.Endpoint;

/**
 * @author xzy
 * @date 2020-03-15 17:49
 * 说明：服务端发布相关接口
 */
@Configuration
public class WebConfig {
    private Bus bus;
    private StudentService studentService;
    private UserService userService;

    @Autowired
    private void dependenceInject(Bus bus, StudentService studentService, UserService userService) {
        this.bus = bus;
        this.studentService = studentService;
        this.userService = userService;
    }

    @Bean
    public Endpoint endpointStudentService() {
        EndpointImpl endpoint = new EndpointImpl(bus, studentService);
        //接口发布在 /StudentService下
        endpoint.publish("/StudentService");
        return endpoint;
    }

    @Bean
    public Endpoint endpointUserService() {
        EndpointImpl endpoint = new EndpointImpl(bus, userService);
        endpoint.publish("/UserService");
        return endpoint;
    }
}
