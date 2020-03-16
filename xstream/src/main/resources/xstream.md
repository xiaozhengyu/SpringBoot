#  XStream使用

## XStream简介

```
Xstream是一种OXMapping 技术，是用来处理XML文件序列化的框架,在将JavaBean序列化，或将XML文件反序列化的时候，不需要其它辅助类和映射文件，使得XML序列化不再繁索。Xstream也可以将JavaBean序列化成Json或反序列化，使用非常方便。
```

## XStream使用案例1

1. 添加依赖

1. 添加依赖

```xml
<!--xml与bean相互转换-->
<dependency>
    <groupId>com.thoughtworks.xstream</groupId>
    <artifactId>xstream</artifactId>
    <version>1.4.10</version>
</dependency>
```

2. 创建没有添加任何XStream注释的实体类

```java
package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xzy
 * @date 2020-03-16 21:14
 * 说明：玩具球
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ball {
    /**
     * 颜色
     */
    private String color;
    /**
     * 价格
     */
    private Double price;
}
```

```java
package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xzy
 * @date 2020-03-16 21:13
 * 说明：猫
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cat {
    /**
     * 名字
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 拥有的玩具球
     */
    private List<Ball> balls;
}
```

3. 创建Java对象，使用XStream将对象转换成XML

```java
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
        xstream.toXML(cat,fileOutputStream);
    }
}
```

转换结果为：

```xml
<com.example.demo.entity.Cat>
  <name>胖子</name>
  <age>1</age>
  <balls>
    <com.example.demo.entity.Ball>
      <color>red</color>
      <price>19.0</price>
    </com.example.demo.entity.Ball>
    <com.example.demo.entity.Ball>
      <color>yellow</color>
      <price>30.0</price>
    </com.example.demo.entity.Ball>
  </balls>
</com.example.demo.entity.Cat>
```

## XStream使用案例2——@XStreamAlias()注解

1. 简单说明

Alias翻译过来就是“化名“、”别名“的意思，确实@XStreamAlias()注解的作用就是为有元素起别名。

2. 为实体类添加@XStreamAlias()注解

```java
package com.example.demo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xzy
 * @date 2020-03-16 21:13
 * 说明：猫
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("cat")
public class Cat {
    /**
     * 名字
     */
    @XStreamAlias("catName")
    private String name;
    /**
     * 年龄
     */
    @XStreamAlias("catAge")
    private Integer age;
    /**
     * 拥有的玩具球
     */
    @XStreamAlias("catBalls")
    private List<Ball> balls;
}
```

```java
package com.example.demo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xzy
 * @date 2020-03-16 21:14
 * 说明：玩具球
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("ball")
public class Ball {
    /**
     * 颜色
     */
    @XStreamAlias("ballColor")
    private String color;
    /**
     * 价格
     */
    @XStreamAlias("ballPrice")
    private Double price;
}
```

3. 调用XStream类的processAnnotations()方法，选择激活哪些类的XStream注解

```java
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
        //xstream.processAnnotations(Cat.class);

        xstream.toXML(cat, fileOutputStream);
    }
}
```

上面的代码只激活了，Ball类中的XStream注解，执行后生成的xml为：

```xml
<com.example.demo.entity.Cat>
  <name>胖子</name>
  <age>1</age>
  <balls>
    <ball>
      <ballColor>red</ballColor>
      <ballPrice>19.0</ballPrice>
    </ball>
    <ball>
      <ballColor>yellow</ballColor>
      <ballPrice>30.0</ballPrice>
    </ball>
  </balls>
</com.example.demo.entity.Cat>
```

可以发现，只有Ball类中的@XStreamAlias()注解起到了作用。现在将Cat类的XStream注解也激活，执行代码，生成的xml为：

```xml
<cat>
  <catName>胖子</catName>
  <catAge>1</catAge>
  <catBalls>
    <ball>
      <ballColor>red</ballColor>
      <ballPrice>19.0</ballPrice>
    </ball>
    <ball>
      <ballColor>yellow</ballColor>
      <ballPrice>30.0</ballPrice>
    </ball>
  </catBalls>
</cat>
```

## XStream使用案例3——@XStreamAsAttribute()注解

1. 简单说明

@XStreamAsAttribute用于将类中某个成员作为父节点的属性输出

2. 在Cat类中添加注解

```java
package com.example.demo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xzy
 * @date 2020-03-16 21:13
 * 说明：猫
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("cat")
public class Cat {
    /**
     * 名字
     */
    @XStreamAsAttribute//将name变为父节点的属性
    @XStreamAlias("catName")
    private String name;
    /**
     * 年龄
     */
    @XStreamAlias("catAge")
    private Integer age;
    /**
     * 拥有的玩具球
     */
    @XStreamAlias("catBalls")
    private List<Ball> balls;
}
```

3. 生成xml

```xml
<cat catName="胖子">
  <catAge>1</catAge>
  <catBalls>
    <ball>
      <ballColor>red</ballColor>
      <ballPrice>19.0</ballPrice>
    </ball>
    <ball>
      <ballColor>yellow</ballColor>
      <ballPrice>30.0</ballPrice>
    </ball>
  </catBalls>
</cat>
```

## XStream使用案例4——@XStreamImplicit()注解

1. 简单说明

@XStreamImplict()注解常用于集合类型元素，前面的例子中，Cat类中的ball属性就是一个集合，最终生成的xml中catName、catAge以及catBalls是同级标签，如果我们想去除catBalls标签，将catBalls下的子标签变成与catName和catAge同级，我们就可以使用@XStreamImplict()注解。

2. 在Cat类中添加注解

```java
package com.example.demo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xzy
 * @date 2020-03-16 21:13
 * 说明：猫
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("cat")
public class Cat {
    /**
     * 名字
     */
    @XStreamAlias("catName")
    private String name;
    /**
     * 年龄
     */
    @XStreamAlias("catAge")
    private Integer age;
    /**
     * 拥有的玩具球
     */
    @XStreamImplicit//去除catBalls标签
    @XStreamAlias("catBalls")
    private List<Ball> balls;
}
```

3. 生成的xml

```xml
<cat>
  <catName>胖子</catName>
  <catAge>1</catAge>
  <ball>
    <ballColor>red</ballColor>
    <ballPrice>19.0</ballPrice>
  </ball>
  <ball>
    <ballColor>yellow</ballColor>
    <ballPrice>30.0</ballPrice>
  </ball>
</cat>
```