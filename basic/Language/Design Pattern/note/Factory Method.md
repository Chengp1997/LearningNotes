# Factory Method

> 工厂方法（Factory Method）模式是类的创建模式，其用意是定义一个创建产品对象的工厂接口，将实际创建工作推迟到子类中。
>
> 工厂方法模式是简单工厂模式的进一步抽象和推广。由于使用了多态性，工厂方法模式保持了简单工厂模式的优点，而且克服了它的缺点。 
>
> 在工厂方法模式中，核心的工厂类不再负责所有产品的创建，**而是将具体创建工作交给子类去做。**这个核心类仅仅负责给出具体工厂必须实现的接口，而不接触哪一个产品类被实例化这种细节。这使得工厂方法模式可以允许系统在不修改工厂角色的情况下引进新产品。
>
>  
>
> 在Factory Method模式中，工厂类与产品类往往具有平行的等级结构，它们之间一一对应。



- pattern name: Factory Method，Define an interface for creating an object, but let subclasses decide which class to instantiate…定义了一个创建对象的接口，具体创建什么对象由其子类来决定
- problem:
- solution:
  - ![1545542641734](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545542641734.png)
  - ![img](https://images.cnblogs.com/cnblogs_com/zhenyulu/Pic41.gif)
  - product：要生产的产品的接口
  - concreteProduct：具体生产的产品，某种类型的具体产品由具体工厂创建
  - factory：抽象的工厂（核心）
  - concreteFactory：具体的工厂
  - ![1545542551724](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545542551724.png)
  - ![1545542576555](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545542576555.png)
-  Consequences:
  - Creation requester class is independent of the class of concrete product objects actually created.缠上请求的类是抽象的
  - The set of product classes that can be instantiated can change dynamically.：实例的对象能轻易变换，只要是产品就行！
  - **Provides hooks for subclasses**. Creating objects inside a class with a factory method is always more flexible than creating an object directly. Factory Method gives subclasses a hook for providing an extended version of an object. 版本能够变换！！
  - **Connects parallel class hierarchies**. In the examples we've considered so far, the factory method is only called by Creators. But this doesn't have to be the case; clients can find factory methods useful, especially in the case of parallel class hierarchies. Parallel class hierarchies result when a class delegates some of its responsibilities to a separate class. Consider graphical figures that can be manipulated interactively; that is, they can be stretched, moved, or rotated using the mouse.  Implementing such interactions isn't always easy. It often requires storing and updating information that records the state of the manipulation at a given time. This state is needed only during manipulation; therefore it needn't be kept in the figure object. Moreover, different figures behave differently when the user manipulates them. For example, stretching a line figure might have the effect of moving an endpoint, whereas stretching a text figure may change its line spacing.
-  Implementation: – Two major varieties: 
  - Creator is abstract class/interface without a default implementation for factory method. 
  - A default implementation is provided. 
  - Parameterized factory methods…

![1545542928241](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545542928241.png)**Depend upon abstractions. Do not depend upon concrete classes**