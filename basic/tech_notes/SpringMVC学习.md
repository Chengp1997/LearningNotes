# SpringMVC学习

## 配置

建立一个SpringMVC项目。

- 正常建立一个java项目
- 引入web模块。（add framework support）
- 引入SpringMVC模块。

![image-20210121150719804](/Users/chengeping/Library/Application Support/typora-user-images/image-20210121150719804.png)

web模块有几个文件。

- dispatcher-servlet.xml是Spring的配置文件。重要！
- web.xml可以配置servlet，编码格式等。servlet可以配置多个，根据多个不同路径。

**配置时遇到的问题：**

tomcat启动失败时：https://blog.csdn.net/qq_45771692/article/details/107684758。将tomcat中 artifact装入对应路径。

配置完后没出现对应的数据：![image-20210119154346531](/Users/chengeping/Library/Application Support/typora-user-images/image-20210119154346531.png)

原因在于。其实我们使用springmvc时，访问的路径是我们自己配置的。

## 原理

![点击查看原始大小图片](http://dl.iteye.com/upload/attachment/0068/8752/620f63e1-ee68-30c9-a53d-13107e634364.png)

servlet可以看作中转站。通过servlet来调动model view controller之间的交互。

使用时，有两种方法。方法1:通过注解。方法2:通过配置文件。

- 请求发送给服务器。
- 服务器先查找，该路径下，是否有对应的注解。
- 如果有对应注解。则交给对应注解所控制的controller来执行函数
- 如果没有注解。则寻找配置文件中配置的对应controller来执行。
- 如果配置文件中也未配置。则使用默认。

注意！springmvc中，要访问的路径是自己配置的这种！

## 配置文件方法

### **web.xml**

```xml
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/applicationContext.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
<!-- 这里配置拦截器   配置/ 说明不管什么路径都拦截下来进行处理 -->
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

### dispatcher-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="simpleUrlHandlerMapping"
          class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
                <!-- /hello 路径的请求交给 id 为 helloController 的控制器处理-->
              <!-- 这个意思是，当访问localhost:8080/home时，会交给helloController这个处理类进行处理 -->
                <prop key="/hello">helloController</prop>
            </props>
        </property>
    </bean>
    <bean id="helloController" class="controller.HelloController"></bean>
</beans>

```

### HelloController.java

```java
package controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloController implements Controller {

  //当通过配置文件，读取到此，会定位到这里来进行处理
  //SpringMVC通过ModelView此类来进行传递
  //最后将model和view连同请求一起发送回去。保证了model和view直接的解耦
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mav = new ModelAndView("index.jsp");//通过此路径，定位到这个页面
        mav.addObject("message", "Hello Spring MVC");//对应的model 传递到对应view中
        return mav;
    }
}
```

### index.jsp

```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 
<h1>${message}</h1>

```

最后，根据试图解析器来分配到页面

## 注解法

### **@controller**

注解的类为控制器

```java
package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController{

    @RequestMapping("/hello")
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mav = new ModelAndView("index.jsp");
        mav.addObject("message", "Hello Spring MVC");
        return mav;
    }
}
```

### **@RequestMapping**

简而言之，这个可以看作是路径。

例如上面的例子。当路径为  localhost:8080/hello时，就会使用该控制器。

当注解在类上时，就相当于在之前再加一个路径。

### **dispatcher-servet.xml**

对应的，可以取消配置文件法的内容，使用context来配置即可。

```xml
<context:component-scan base-package="controller"/>
```

### **视图解析器**

当有页面不想被他人直接看到（或者说，直接使用x x x x/index.jsp访问到），可以将页面内容放置到WEB-INFO中。该目录是对外人隐藏的。此时只需要配置视图解析器就可以进行访问。

![image-20210121170545055](/Users/chengeping/Library/Application Support/typora-user-images/image-20210121170545055.png)

在dispatcher-servet.xml中配置此即可

```xml
<bean id="viewResolver"
      class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/page/" />
    <property name="suffix" value=".jsp" />
</bean>
```

此时，对应的controller文件中，对应路径就可以修改为

![img](https://upload-images.jianshu.io/upload_images/7896890-2ce49e171bd6d547.png?imageMogr2/auto-orient/strip|imageView2/2/w/765)

### 接收数据

例如，对于表格，主要有几种方法

```xml
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Spring MVC 传参方式</title>
</head>
<body>
<form action="param" role="form">
    用户名：<input type="text" name="userName"><br/>
    密码：<input type="text" name="password"><br/>
    <input type="submit" value="提  交">
</form>
</body>
</html>
```

#### 原生API（request.getParameter）

```java
@RequestMapping("/param")
public ModelAndView getParam(HttpServletRequest request,
                         HttpServletResponse response) {
  //此处，需要对应到表格中，需要获取的数据的name
    String userName = request.getParameter("userName");
    String password = request.getParameter("password");

    System.out.println(userName);
    System.out.println(password);
    return null;
}
```

#### 同名规则（@RequestParam）

如下，对应的是对应param表格中，username和password。但是为了防止过耦合，这里，建议：

```java
/**
@RequestMapping("/param")
public ModelAndView getParam(String userName,
                             String password) {
    System.out.println(userName);
    System.out.println(password);
    return null;
}
**/

    @RequestMapping("/testParam")
    public ModelAndView getParam(@RequestParam("user_userName")String userName,
                                 @RequestParam("user_password")String password) {

        System.out.println(userName);
        System.out.println(password);
        return null;
    }

```

#### 使用模型传参

```java
package pojo;

public class User {
    
    String userName;
    String password;

    /* getter and setter */
}

//controller.java
@RequestMapping("/param")
public ModelAndView getParam(User user) {
    System.out.println(user.userName);
    System.out.println(user.password);
    return null;
}
```

### 传送数据

#### 原生API (request.setAttribute)

```java
@RequestMapping("/value")
public ModelAndView handleRequest(HttpServletRequest request,
                                  HttpServletResponse response) {
    request.setAttribute("message","成功！");
    return new ModelAndView("test1");//返回到test1页面
}
```

#### ModelAndView

```java
public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    ModelAndView mav = new ModelAndView("index");//通过此路径，定位到这个页面
    mav.addObject("message", "Hello Spring MVC");//对应的model 传递到对应view中
    return mav;
}
```
#### Model

```
@RequestMapping("/value")
public ModelAndView handleRequest(Model model) {
    model.addAttribute("message","成功！");
    return "test1";//返回到test1页面
}
```

#### @ModelAttribute

```java
@ModelAttribute
public void model(Model model) {
    model.addAttribute("message", "注解成功");
}

@RequestMapping("/value")
public String handleRequest() {
    return "test1";
}
```

> 这样写就会在访问控制器方法 handleRequest() 时，会首先调用 model() 方法将 `message` 添加进页面参数中去，在视图中可以直接调用，但是这样写会导致该控制器所有的方法都会首先调用 model() 方法，但同样的也很方便，因为可以加入各种各样的数据。

### 页面跳转

```java
@RequestMapping("/jump")
public String jump() {
    return "redirect: ./hello";
}
```

当输入localhost:8080/jump，会跳转到/hello页面

```java
@RequestMapping("/jump")
public ModelAndView jump() {
    ModelAndView mav = new ModelAndView("redirect:/hello");
    return mav;
}
```

甚至可以这样

```java
@RequestMapping("/testPage")
public String toPage(){
    System.out.println("去页面");
    return "test";
}
```

### 防止乱码

web.xml

```xml
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <!-- 设置编码格式 -->
        <param-value>utf-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

### 文件上传

https://www.jianshu.com/p/91a2d0a1e45a见此，最下。



这里有几个例子：

https://www.cnblogs.com/youngchaolin/p/11354307.html



https://www.cnblogs.com/baiduligang/p/4247164.html细节过多，但是讲的很清楚

### 什么是ioc（控制反转），什么是DI（依赖注入）

https://blog.csdn.net/qq_42709262/article/details/81951402



### Springboot和Springmvc什么区别

https://blog.csdn.net/u014590757/article/details/79602309