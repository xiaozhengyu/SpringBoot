package com.example.webservice.demo.serviceimpl;

import net.sf.json.JSONArray;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import java.util.Arrays;


/**
 * @author xzy
 * @date 2020-03-15 21:04
 * 说明：测试
 */
public class Test {
    public static void main(String[] args) {
        //在一个方法中连续调用多次WebService接口，每次调用前需要重置上下文。
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        /*
         * --通过JaxWsDynamicClientFactory进行WebService客户端调用的一个好处就是，只需要知道了WSDL地址就行了，
         * 不需要手动生成任何代码，这样，如果需要调用多个WebService服务的话，只需要创建多个Client即可，不用
         * 考虑传统方式（生成代码）冲突问题，这样可以让代码更优雅。
         */
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();

        printStudents(dcf);

        //在调用第二个webservice前，需要重置上下文
        Thread.currentThread().setContextClassLoader(classLoader);

        printUser(dcf);
    }

    /**
     * 调用远端WebService的服务，获取学生信息
     *
     * @param dcf - 用于远程webservice调用。
     */
    private static void printStudents(JaxWsDynamicClientFactory dcf) {
        Client client = dcf.createClient("http://127.0.0.1:1234/services/StudentService?wsdl");

        /*
         *--需要密码的情况需要添加上用户名和密码
         *client.getOutInterceptors().add(new ClientLoginInterceptor(USERNAME,PASSWORD));
         */

        try {
            //invoke("方法名",参数1,参数2,参数3......)
            Object students = client.invoke("getAll");
            JSONArray jsonArray = JSONArray.fromObject(students);
            System.out.println(Arrays.toString(jsonArray.toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 调用远端WebService的服务，获取用户信息
     *
     * @param dcf - 用于远程webservice调用。
     */
    private static void printUser(JaxWsDynamicClientFactory dcf) {
        Client client = dcf.createClient("http://127.0.0.1:1234/services/UserService?wsdl");

        /*
         *--需要密码的情况需要添加上用户名和密码
         *client.getOutInterceptors().add(new ClientLoginInterceptor(USERNAME,PASSWORD));
         */
        try {
            /*
             *--invoke("方法名",参数1,参数2,参数3......)，返回值Object[]
             *--invoke将请求拼接成xml发送给远端的WebService服务，接受对面返回的xml，取出返回值，填到一个Java对象返回。
             */
            Object user = client.invoke("getUserByName", "张三");
            JSONArray jsonArray = JSONArray.fromObject(user);
            System.out.println(Arrays.toString(jsonArray.toArray()));
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }
}

