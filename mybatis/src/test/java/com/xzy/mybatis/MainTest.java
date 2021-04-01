package com.xzy.mybatis;

import com.xzy.mybatis.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xzy
 * @date 2021-03-31 15:17
 * 说明：
 */
@SpringBootTest
public class MainTest {

    /**
     * 纯xml文件方式
     *
     * @throws IOException -
     */
    @Test
    public void test1() throws IOException {
        // 1.根据XML配置文件（全局配置文件）创建SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 2.获取SqlSession实例：能够直接执行已经映射的SQL语句
        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            Employee employee = openSession.selectOne("com.xzy.mybatis.repository.EmployeeRepository.findById", 1);
            System.out.println(employee);
        }
    }

    /**
     *
     */
    @Test
    public void test2() {
        
    }
}
