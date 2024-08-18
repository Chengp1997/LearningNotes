# Java Basic

## 指针和引用

[Java的引用和c++的指针](https://segmentfault.com/a/1190000041657797)

## Java 和 C++



## Override Overload

override 是 子类继承父类时发生的



overload是同一个类中相同名的方法



## 接口和抽象类



## String StringBuilder StringBuffer



## == equals



## Hashcode equals



## Java 执行顺序

[代码的执行顺序](https://juejin.cn/post/6844903986475040781)

[static执行顺序](https://www.cnblogs.com/hongchengshise/p/10375400.html)

1. static -> parent 静态代码块，静态变量 
2. static - > children 静态代码块，静态变量
3. 成员变量 - > parent 成员变量 -> parent 构造函数
4. 成员变量 -> children成员变量 -> children构造函数
5. static方法只有在调用的时候才会执行。

**static：静态代码块**

类加载的时候就会执行了，最先被初始化。

> 1、Java静态代码块中的代码会在类加载JVM时运行，且只被执行一次
>
>  2、静态块常用来执行类属性的初始化 
>
> 3、静态块优先于各种代码块以及构造函数，如果一个类中有多个静态代码块，会按照书写顺序依次执行
>
>  4、静态代码块可以定义在类的任何地方中除了方法体中【这里的方法体是任何方法体】  -- 一般方法是先类加载，后再new 出实例才可以执行，因此不可以再这里。
>
> 5、静态代码块不能访问普通变量

