# Spring

### SpringMVC

https://github.com/brianway/springmvc-mybatis-learning/blob/master

## SpringIOC

SpringIOC（控制反转）：把对象的创建，初始化，销毁等工作交给Spring容器来做。由Spring容器控制对象的生命周期。

https://www.cnblogs.com/leeego-123/p/10824033.html



## Springboot

首先，springboot可以说是Spring配置的一种集成。它本身就是Spring，只是通过Springboot可以将我们之前学习的SpringMVC等内容的配置步骤大幅简化。程序员只需要专注于业务逻辑即可。

### Pom.xml

首先指定父级依赖，将如下配置添加到`project`标签中

```xml
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<!--SpringBoot版本-->
	<version>1.5.9.RELEASE</version>
</parent>
123456
```

然后添加`spring-web`启动器依赖到`dependencies`标签中，没有该标签的在`project`标签下添加一个即可

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>groupId</groupId>
    <artifactId>SpringbootTest</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <!--SpringBoot版本-->
        <version>1.5.9.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>


    </dependencies>
</project>
```

# mybatis

https://www.cnblogs.com/benjieqiang/p/11183580.html。极简版介绍。

![img](https://img2018.cnblogs.com/blog/1415026/201907/1415026-20190714112425241-1715281484.png)

通过此图可以看到

- mybatis首先读取**SqlMapConfig.xml**文件。此文件作为mybatis的全局配置文件，配置了mybatis的运行环境等信息。
- **mapper.xml**文件即sql映射文件，文件中配置了操作数据库的sql语句。此文件需要在SqlMapConfig.xml中加载。
- 通过mybatis环境等配置信息构造**SqlSessionFactory**即会话工厂
- 由会话工厂**创建sqlSession**即会话，操作数据库需要通过sqlSession进行。
- mybatis底层自定义了Executor执行器接口操作数据库，Executor接口有两个实现，一个是基本执行器、一个是缓存执行器。
- Mapped  Statement也是mybatis一个底层封装对象，它包装了mybatis配置信息及sql映射信息等。mapper.xml文件中一个sql对应一个Mapped Statement对象，sql的id即是Mapped statement的id。
-  Mapped  Statement对sql执行输入参数进行定义，包括HashMap、基本类型、pojo，Executor通过Mapped  Statement在执行sql前将输入的java对象映射至sql中，输入参数映射就是jdbc编程中对preparedStatement设置参数。
- Mapped  Statement对sql执行输出结果进行定义，包括HashMap、基本类型、pojo，Executor通过Mapped  Statement在执行sql后将输出结果映射至java对象中，输出结果映射过程相当于jdbc编程中对结果的解析处理过程。



## 配置步骤

### 结构图

![image-20210126220924123](/Users/chengeping/Library/Application Support/typora-user-images/image-20210126220924123.png)



### 导入mybatis

方法一：直接加入mybatis.jar

方法二：使用maven

```xml
<packaging>jar</packaging>
    <dependencies>
      <!-- mybatis依赖-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.6</version>
        </dependency>
      
      <!-- mysql依赖-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.12</version>
        </dependency>
      
      <!-- 日志-->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.12</version>
        </dependency>

      <!-- junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.10</version>
        </dependency>


    </dependencies>
```

### 编写配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!--   配置环境-->
    <environments default="mysql">
        <!--配置mysql的环境-->
        <environment id="mysql">
            <!--配置事务的类型-->
            <transactionManager type="JDBC"></transactionManager>
            <!--配置连接池-->
            <dataSource type="POOLED">
                <!--配置连接数据库的4个基本信息-->
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatisLearningDemo"/>
                <property name="username" value="root"/>
                <property name="password" value="cgp5226926+123"/>
            </dataSource>
        </environment>
    </environments>

    <!--指定映射配置文件的位置，映射配置文件指的是每个dao独立的配置文件-->
    <mappers>
        <mapper resource="sqlmap/User.xml"/>
    </mappers>
</configuration>
```

日志文件

```properties
#配置日志文件

# Global logging configuration
log4j.rootLogger=DEBUG, stdout
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

### 编写实体类

这里使用User来作例子

```java
package pojo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id;
    private String username;// 用户姓名
    private String sex;// 性别
    private Date birthday;// 生日
    private String address;// 地址

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", sex=" + sex
                + ", birthday=" + birthday + ", address=" + address + "]";
    }

}
```

### 编写mapper将实体类和sql语句关联起来

其实就像以前写的dao类，只是写在xml中，如此需要更改的话不需要再编写java代码

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- namespace 命名空间，作用就是对sql进行分类化管理,理解为sql隔离
 注意：使用mapper代理方法开发，namespace有特殊重要的作用
 -->
<mapper namespace="test">
    <!-- 在映射文件中配置很多sql语句 -->
    <!--需求:通过id查询用户表的记录 -->
    <!-- 通过select执行数据库查询
     id:标识映射文件中的sql，称为statement的id
     将sql语句封装到mappedStatement对象中，所以将id称为statement的id
     parameterType:指定输入参数的类型
     #{}标示一个占位符,
     #{value}其中value表示接收输入参数的名称，如果输入参数是简单类型，那么#{}中的值可以任意。

     resultType：指定sql输出结果的映射的java对象类型，select指定resultType表示将单条记录映射成java对象
     -->
    <select id="findUserById" parameterType="int" resultType="pojo.User">
        SELECT * FROM  user  WHERE id=#{value}
    </select>

    <!-- 根据用户名称模糊查询用户信息，可能返回多条
   resultType：指定就是单条记录所映射的java对象类型
   ${}:表示拼接sql串，将接收到参数的内容不加任何修饰拼接在sql中。
   使用${}拼接sql，引起 sql注入
   ${value}：接收输入参数的内容，如果传入类型是简单类型，${}中只能使用value
    -->
    <select id="findUserByName" parameterType="java.lang.String" resultType="pojo.User">
        SELECT * FROM user WHERE username LIKE '%${value}%'
    </select>

  <!-- 添加用户
        parameterType：指定输入 参数类型是pojo（包括 用户信息）
        #{}中指定pojo的属性名，接收到pojo对象的属性值，mybatis通过OGNL获取对象的属性值
        -->
    <insert id="insertUser" parameterType="com.iot.mybatis.po.User">
        <!--
         将插入数据的主键返回，返回到user对象中

         SELECT LAST_INSERT_ID()：得到刚insert进去记录的主键值，只适用与自增主键

         keyProperty：将查询到主键值设置到parameterType指定的对象的哪个属性
         order：SELECT LAST_INSERT_ID()执行顺序，相对于insert语句来说它的执行顺序
         resultType：指定SELECT LAST_INSERT_ID()的结果类型
          -->
        <selectKey keyProperty="id" order="AFTER" resultType="java.lang.Integer">
          SELECT LAST_INSERT_ID()
        </selectKey>
        INSERT INTO user (username,birthday,sex,address)values (#{username},#{birthday},#{sex},#{address})
        <!--
            使用mysql的uuid（）生成主键
            执行过程：
            首先通过uuid()得到主键，将主键设置到user对象的id属性中
            其次在insert执行时，从user对象中取出id属性值
             -->
        <!--  <selectKey keyProperty="id" order="BEFORE" resultType="java.lang.String">
            SELECT uuid()
        </selectKey>
        insert into user(id,username,birthday,sex,address) value(#{id},#{username},#{birthday},#{sex},#{address}) -->

    </insert>

    <!-- 删除 用户
        根据id删除用户，需要输入 id值
         -->
    <delete id="deleteUser" parameterType="java.lang.Integer">
        delete from user where id=#{id}
    </delete>

    <!-- 根据id更新用户
    分析：
    需要传入用户的id
    需要传入用户的更新信息
    parameterType指定user对象，包括 id和更新信息，注意：id必须存在
    #{id}：从输入 user对象中获取id属性值
     -->
    <update id="updateUser" parameterType="pojo.User">
        update user set username=#{username},birthday=#{birthday},sex=#{sex},address=#{address}
        where id=#{id}
    </update>

</mapper>
```

​    

### 编写测试类(这里使用SqlSessionFactoryBuilder来创建SqlSession)

通过`SqlSessionFactoryBuilder`创建会话工厂`SqlSessionFactory`将`SqlSessionFactoryBuilder`当成一个工具类使用即可，不需要使用单例管理`SqlSessionFactoryBuilder`。在需要创建`SqlSessionFactory`时候，只需要new一次`SqlSessionFactoryBuilder`即可。

注意进行时需要的几个步骤

```java
//1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产SqlSession对象
        SqlSession session = sqlSessionFactory.openSession();
        //4.执行Sql语句
        User user = session.selectOne("test.findUserById", 10);
        //5. 打印结果
        System.out.println(user);
        //6.释放资源
        session.close();
        in.close();
```

```java
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pojo.User;

import java.io.IOException;
import java.io.InputStream;

public class myBatisTest {
    //通过Id查询一个用户
    @Test
    public void testSearchById() throws IOException {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产SqlSession对象
        SqlSession session = sqlSessionFactory.openSession();
        //4.执行Sql语句
        User user = session.selectOne("test.findUserById", 1);
        //5. 打印结果
        System.out.println(user);
        //6.释放资源
        session.close();
        in.close();
    }
}
```

### 应用（这里使用SqlSessionFactory来创建SqlSession）

mybatis应用时主要有两种方法。一种是使用原始dao；一种是使用mapper

通过`SqlSessionFactory`创建`SqlSession`，使用单例模式管理`sqlSessionFactory`（工厂一旦创建，使用一个实例）。将来mybatis和spring整合后，使用单例模式管理`sqlSessionFactory`。

#### 使用Dao接口方法

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20210127105757212.png" alt="image-20210127105757212" style="zoom:50%;" />

UserDao接口

```java
package Dao;

import pojo.User;

import java.util.List;

public interface UserDao {
    //根据id查询用户信息
    public User findUserById(int id) throws Exception;

    //根据用户名列查询用户列表
    public List<User> findUserByName(String name) throws Exception;

    //添加用户信息
    public void insertUser(User user) throws Exception;

    //删除用户信息
    public void deleteUser(int id) throws Exception;
}
```

UserDaoImpl接口实现类

```java
package Dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.User;

import java.util.List;

public class UserDaoImpl implements UserDao {
    // 需要向dao实现类中注入SqlSessionFactory
    // 这里通过构造方法注入
    //工厂一旦创建使用一个实例
    private SqlSessionFactory sqlSessionFactory;

    public UserDaoImpl(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
    public User findUserById(int id) throws Exception {
       SqlSession sqlsession= sqlSessionFactory.openSession();
       User user=sqlsession.selectOne("test.findUserById",id);
       sqlsession.close();
       return user;
    }

    public List<User> findUserByName(String name) throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        List<User> list = sqlSession.selectList("test.findUserByName", name);

        // 释放资源
        sqlSession.close();

        return list;
    }

    public void insertUser(User user) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //执行插入操作
        sqlSession.insert("test.insertUser", user);

        // 提交事务
        sqlSession.commit();

        // 释放资源
        sqlSession.close();
    }

    public void deleteUser(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //执行插入操作
        sqlSession.delete("test.deleteUser", id);

        // 提交事务
        sqlSession.commit();

        // 释放资源
        sqlSession.close();
    }
}
```

总结：这是最原始的Dao方法来实现存取数据。有一些无法避免的问题

1.dao接口实现类方法中存在大量模板方法，设想能否将这些代码提取出来，大大减轻程序员的工作量。

2.调用sqlsession方法时将statement的id硬编码了（这个意思是例如sqlSession.delete("test.deleteUser", id);  test。deleteUser）

3.调用sqlsession方法时传入的变量，由于sqlsession方法使用泛型，即使变量类型传入错误，在编译阶段也不报错，不利于程序员开发。

#### 使用mapper代理的方法

使用mapper可以简化大量模版方法，并且可以取消硬编码。

程序员只需要**mapper接口**（相当 于dao接口）

程序员还需要编写**mapper.xml**映射文件

程序员编写mapper接口需要**遵循一些开发规范**，mybatis可以**自动生成mapper接口实现类代理对象**。

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20210127215216721.png" alt="image-20210127215216721" style="zoom:50%;" />

这个方法和Dao的方法类似，但是采用的是mapper代理的方式。

**注意**

请特别注意！  mapper文件中，**namespace必须和上方接口文件的文件位置一致**，换言之，namespace如下。

**UserMapper.xml文件**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- namespace 命名空间，作用就是对sql进行分类化管理,理解为sql隔离
 注意：使用mapper代理方法开发，namespace有特殊重要的作用
 -->
<mapper namespace="mapper.userMapper">
    <!-- 在映射文件中配置很多sql语句 -->
    <!--需求:通过id查询用户表的记录 -->
    <!-- 通过select执行数据库查询
     id:标识映射文件中的sql，称为statement的id
     将sql语句封装到mappedStatement对象中，所以将id称为statement的id
     parameterType:指定输入参数的类型
     #{}标示一个占位符,
     #{value}其中value表示接收输入参数的名称，如果输入参数是简单类型，那么#{}中的值可以任意。

     resultType：指定sql输出结果的映射的java对象类型，select指定resultType表示将单条记录映射成java对象
     -->
    <select id="findUserById" parameterType="int" resultType="pojo.User">
        SELECT * FROM  user  WHERE id=#{value}
    </select>

    <!-- 根据用户名称模糊查询用户信息，可能返回多条
   resultType：指定就是单条记录所映射的java对象类型
   ${}:表示拼接sql串，将接收到参数的内容不加任何修饰拼接在sql中。
   使用${}拼接sql，引起 sql注入
   ${value}：接收输入参数的内容，如果传入类型是简单类型，${}中只能使用value
    -->
    <select id="findUserByName" parameterType="java.lang.String" resultType="pojo.User">
        SELECT * FROM user WHERE username LIKE '%${value}%'
    </select>
    <!-- 添加用户
            parameterType：指定输入 参数类型是pojo（包括 用户信息）
            #{}中指定pojo的属性名，接收到pojo对象的属性值，mybatis通过OGNL获取对象的属性值
            -->
    <insert id="insertUser" parameterType="pojo.User">
        <!--
         将插入数据的主键返回，返回到user对象中

         SELECT LAST_INSERT_ID()：得到刚insert进去记录的主键值，只适用与自增主键

         keyProperty：将查询到主键值设置到parameterType指定的对象的哪个属性
         order：SELECT LAST_INSERT_ID()执行顺序，相对于insert语句来说它的执行顺序
         resultType：指定SELECT LAST_INSERT_ID()的结果类型
          -->
        <selectKey keyProperty="id" order="AFTER" resultType="java.lang.Integer">
            SELECT LAST_INSERT_ID()
        </selectKey>
        INSERT INTO user (username,birthday,sex,address)values (#{username},#{birthday},#{sex},#{address})
        <!--
            使用mysql的uuid（）生成主键
            执行过程：
            首先通过uuid()得到主键，将主键设置到user对象的id属性中
            其次在insert执行时，从user对象中取出id属性值
             -->
        <!--  <selectKey keyProperty="id" order="BEFORE" resultType="java.lang.String">
            SELECT uuid()
        </selectKey>
        insert into user(id,username,birthday,sex,address) value(#{id},#{username},#{birthday},#{sex},#{address}) -->

    </insert>

    <!-- 删除 用户
        根据id删除用户，需要输入 id值
         -->
    <delete id="deleteUser" parameterType="java.lang.Integer">
        delete from user where id=#{id}
    </delete>

    <!-- 根据id更新用户
    分析：
    需要传入用户的id
    需要传入用户的更新信息
    parameterType指定user对象，包括 id和更新信息，注意：id必须存在
    #{id}：从输入 user对象中获取id属性值
     -->
    <update id="updateUser" parameterType="pojo.User">
        update user set username=#{username},birthday=#{birthday},sex=#{sex},address=#{address}
        where id=#{id}
    </update>

</mapper>
```

**userMapper 接口**

编写了需要的各种接口方法，用来编写代码时调用

```java
package mapper;

import pojo.User;

import java.util.List;

public interface UserMapper {
    //根据id查询用户信息
    public User findUserById(int id) throws Exception;

    //根据用户名列查询用户列表
    public List<User> findUserByName(String name) throws Exception;

    //添加用户信息
    public void insertUser(User user) throws Exception;

    //删除用户信息
    public void deleteUser(int id) throws Exception;

    //更新用户
    public void updateUser(User user)throws Exception;
}
```

**SqlMapConfig.xml**

需要在config文件中添加对应的mapper文件用来识别。

这里的路径对应的是mapper文件。

```xml
<mappers>
    <mapper resource="mapper/UserMapper.xml"/>
</mappers>
```

**UserMapperTest**

```java
public class UserMapperTest {
    private SqlSessionFactory sqlSessionFactory;

    //注解Before是在执行本类所有测试方法之前先调用这个方法
    @Before
    public void setup() throws Exception{
        //创建SqlSessionFactory
        String resource="SqlMapConfig.xml";

        //将配置文件加载成流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂，传入mybatis配置文件的信息
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testFindUserById() throws Exception{

        SqlSession sqlSession=sqlSessionFactory.openSession();

        //创建UserMapper代理对象
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);

        //调用userMapper的方法
        User user=userMapper.findUserById(1);

        System.out.println(user.getUsername());
    }
}
```

**注意这一步**

```java
    //创建UserMapper代理对象
    UserMapper userMapper=sqlSession.getMapper(UserMapper.class);

    //调用userMapper的方法
    User user=userMapper.findUserById(1);
```
这里使用了mapper代理的方式。并且通过调用接口，取消了硬编码的问题。

这个方式同意了以下的这些方法，不需要再来编写一遍

```java
User user = sqlSession.selectOne("test.findUserById", id);
sqlSession.insert("test.insertUser", user);
```

### 配置文件进阶

**数据库文件配置**

可以将配置文件sqlMapConfig.xml中的数据库配置文件提取出来便于后续管理

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20210127185332042.png" alt="image-20210127185332042" style="zoom:50%;" />

db.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatisLearningDemo
jdbc.username=root
jdbc.password=cgp5226926+123
```

SqlMapConfig.xml

```xml
<configuration>

    <properties resource="db.properties">
        <!--properties中还可以配置一些属性名和属性值  -->
        <!-- <property name="jdbc.driver" value=""/> -->
    </properties>

    <!-- 和spring整合后 environments配置将废除-->
    <!--   配置环境-->
    <environments default="mysql">
        <!--配置mysql的环境-->
        <environment id="mysql">
            <!--配置事务的类型-->
            <transactionManager type="JDBC"></transactionManager>
            <!--配置连接池-->
            <dataSource type="POOLED">
                <!--配置连接数据库的4个基本信息-->
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>

    <!--指定映射配置文件的位置，映射配置文件指的是每个dao独立的配置文件-->
    <mappers>
        <mapper resource="mapper/UserMapper.xml"/>
    </mappers>
</configuration>
```

建议：

- 不要在`properties`元素体内添加任何属性值，只将属性值定义在properties文件中。
- 在properties文件中定义属性名要有一定的特殊性，如：XXXXX.XXXXX.XXXX

**一些特性的配置**

可见此，例如开启二级缓存等。https://mybatis.org/mybatis-3/configuration.html#settings

**添加别名**

在SqlMapConfig.xml文件中，可以配置一些属性的别名。因为应用时，有时候需要输入全路径，麻烦！

例如：

```xml
<typeAliases>
  <typeAlias alias="Author" type="domain.blog.Author"/>
  <typeAlias alias="Blog" type="domain.blog.Blog"/>
  <typeAlias alias="Comment" type="domain.blog.Comment"/>
  <typeAlias alias="Post" type="domain.blog.Post"/>
  <typeAlias alias="Section" type="domain.blog.Section"/>
  <typeAlias alias="Tag" type="domain.blog.Tag"/>
</typeAliases>
```

**映射位置配置**

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20210127215616533.png" alt="image-20210127215616533" style="zoom:50%;" />

要点注意：

- mybatis自动识别的mapper文件必须放在resources文件夹下面如上图。
- mapper文件中的namespace为接口文件的全路径位置。
- UserMapper.xml文件可以不和接口放在一起，为了maven好打包可以放在一起。

常规加载方法

```xml
<mappers>
        <mapper resource="mapper/UserMapper.xml"/>
    </mappers>
```

当使用mapper代理的方式时

```xml
<mapper class="mapper.UserMapper"/>
```

批量加载方式（推荐）

```xml
<package name="mapper"/>
<!--name为接口文件所在的文件夹 -->
```

关于文件配置，可以见此

https://blog.csdn.net/zxd1435513775/article/details/79710493

### 输入映射和输出映射

**输入映射**

通过parameterType指定输入参数的类型，类型可以是

- 简单类型
- hashmap
- pojo的包装类型

请注意，在使用时需要注意大小写。例如：

```xml
<select id="findUserList" parameterType="com.iot.mybatis.po.UserQueryVo"
            resultType="com.iot.mybatis.po.UserCustom">
        SELECT * FROM user WHERE user.sex=#{userCustom.sex} AND user.username LIKE '%${userCustom.username}%'
    </select>
```

以上：注意不要将`#{userCustom.sex}`中的`userCustom`写成`UserCustom`,前者指属性名(由于使用IDE提示自动补全，所以只是把类型名首字母小写了)，后者指类型名，这里是`UserQueryVo`类中的`userCustom`属性，是**属性名**

**输出映射**

输出映射有两种方式

- `resultType`

  - 使用`resultType`进行输出映射，只有查询出来的列名和pojo中的属性名一致，该列才可以映射成功。

- `resultMap`

  - 如果查询出来的列名和pojo的属性名不一致，通过定义一个resultMap对列名和pojo属性名之间作一个映射关系。简单的来说，就是进行一个转换，查询列名对应的pojo类中的什么属性进行一个映射。

  - ```xml
    //type: pojo类型
    //id. 该resultmap的标识
    <resultMap type="pojo.User" id="userResultMap">
    	//id： 查询结果的标识，可以当作key
      //column：查询结果的列名
      //property：pojo中的属性名
    	 	<id column="id_" property="id"/>
    	//result：普通列
    	 	<result column="username_" property="username"/>
    	 </resultMap>
    
    //之后就可以正常使用
    <select id="findUserByIdResultMap" parameterType="int" resultMap="userResultMap">
            SELECT id id_,username username_ FROM USER WHERE id=#{value}
        </select>
    ```

### 动态sql

**使用if条件**

可以保证有的条件可选。例如

```xml
<select id="findUserList" parameterType="pojo.UserQueryVo"
        resultType="pojo.UserCustom">
    SELECT * FROM user
    <!--  where 可以自动去掉条件中的第一个and -->
    <where>
        <if test="userCustom!=null">
            <if test="userCustom.sex!=null and userCustom.sex != '' ">
               AND user.sex=#{userCustom.sex}
            </if>
            <if test="userCustom.username!=null and userCustom.username != '' ">
               AND user.username LIKE '%${userCustom.username}%'
            </if>
        </if>
    </where>


</select>
```

对于此例子，如果传进来没有sex参数，只会查询username条件。sex条件直接忽略。

**sql片段**

可以将sql片段抽取出来，就可以供其他sql语句使用

```xml
<sql id="query_user_where">
       <if test="userCustom!=null">
           <if test="userCustom.sex!=null and userCustom.sex!=''">
               AND user.sex = #{userCustom.sex}
           </if>
           <if test="userCustom.username!=null and userCustom.username!=''">
               AND user.username LIKE '%${userCustom.username}%'
           </if>
       </if>
   </sql>
   <!-- 用户信息综合查询
   #{userCustom.sex}:取出pojo包装对象中性别值
   ${userCustom.username}：取出pojo包装对象中用户名称
-->
   <select id="findUserList" parameterType="pojo.UserQueryVo"
           resultType="pojo.UserCustom">
       SELECT * FROM user
       <!--  where 可以自动去掉条件中的第一个and -->
       <where>
           <!-- 引用sql片段 的id，如果refid指定的id不在本mapper文件中，需要前边加namespace -->
           <include refid="query_user_where"></include>
           <!-- 在这里还要引用其它的sql片段  -->
       </where>
   </select>
```

**foreach**











配置事务时出现红名问题：

https://blog.csdn.net/qq_40389276/article/details/96478436?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control





### mybatis 逆向工程

自动生成代码

http://mybatis.org/generator/

https://github.com/mybatis/generator

https://github.com/brianway/springmvc-mybatis-learning/blob/master/mybatis/mybatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0(18)-mybatis%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.md