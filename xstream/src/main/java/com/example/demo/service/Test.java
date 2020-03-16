package com.example.demo.service;

import com.example.demo.entity.Ball;
import com.example.demo.entity.Cat;
import com.thoughtworks.xstream.XStream;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xzy
 * @date 2020-03-16 21:17
 * 说明：
 */
public class Test {
    public static void main(String[] args) throws Exception {
        List<Ball> balls = new ArrayList<>(2);
        balls.add(new Ball("red", 19.0));
        balls.add(new Ball("yellow", 30.0));
        Cat cat = new Cat("胖子", 1, balls);

        FileOutputStream fileOutputStream = new FileOutputStream("E:\\Program Code\\Java\\xstream\\src\\main\\resources\\cat.xml");
        XStream xstream = new XStream();
        /**
         * 需要明确指出，哪个类的XStream注解需要被激活
         */
        xstream.processAnnotations(Ball.class);
        xstream.processAnnotations(Cat.class);

        xstream.toXML(cat, fileOutputStream);
    }
}
