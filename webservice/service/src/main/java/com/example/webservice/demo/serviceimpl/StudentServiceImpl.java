package com.example.webservice.demo.serviceimpl;

import com.example.webservice.demo.entity.StudentEntity;
import com.example.webservice.demo.service.StudentService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xzy
 * @date 2020-03-15 20:01
 * 说明：对外提供的学生信息服务
 */
@WebService(serviceName = "StudentService",
        targetNamespace = "http://service.demo.webservice.example.com",
        endpointInterface = "com.example.webservice.demo.service.StudentService")
@Service
public class StudentServiceImpl implements StudentService {
    /**
     * 获取所有学生信息
     *
     * @return - 所有学生信息
     */
    @Override
    public List<StudentEntity> getAll() {
        System.out.println("getAll():监听到请求");
        List<StudentEntity> students = new ArrayList<>();
        StudentEntity student1 = new StudentEntity("zhangsan", 20);
        StudentEntity student2 = new StudentEntity("lisi", 21);
        students.add(student1);
        students.add(student2);
        return students;
    }
}
