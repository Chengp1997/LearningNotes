# Abstract Factory Pattern

- Pattern name: abstract factory pattern
- intent:Provide an interface for **creating families of related or dependent objects without specifying their concrete classes** 
- solution:
  - ![img](https://images.cnblogs.com/cnblogs_com/zhenyulu/Pic46.gif)
- Consequences:
  -  **It isolates concrete classes.** The Abstract Factory pattern helps you control the classes of objects that an application creates. Because a factory encapsulates the responsibility and the process of creating product objects, it isolates clients from implementation classes. Clients manipulate instances through their abstract interfaces. Product class names are isolated in the implementation of the concrete factory; they do not appear in client code. 
  - **It makes exchanging product families easy**. The class of a concrete factory appears only once in an application—that is, where it's instantiated. This makes it easy to change the concrete factory an application uses. It can use different product configurations simply by changing the concrete factory. Because an abstract factory creates a complete family of products, the whole product family changes at once.
  -  **It promotes consistency among products.** When product objects in a family are designed to work together, it's important that an application use objects from only one family at a time. Abstract Factory makes this easy to enforce.
  - ***Supporting new kinds of products is difficult* Extending abstract factories to produce new kinds of Products isn't easy.** That's because the Abstract Factory interface fixes the set of products that can be created. Supporting new kinds of products requires extending the factory interface, which involves changing the Abstract Factory class and all of its subclasses产品方法是定义好的，要给接口！！！
  - ![1545565808096](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545565808096.png)
-  Applicability: Use when:
  - Application should be independent of how its products are created 
  -  Application should be configured with one of multiple families of products 
  - A family of related product objects is designed to be used together and you wish to enforce this
  - You want to provide a class library of products and you want to reveal just their interfaces, not their implementations
-  Implementation:(issues) 
  - Factories as Singletons 
  - Use of static for Abstract Factory’s `getFactorymethod` 
  - Mechanisms used in get Factory for knowing which ConcreteFactoryto return: 
    - Static variable with appropriate factory instance 
    - Parameter provided by client and hard-coded logic
    - External configinformation allows mapping to class name from which we create an instance

**与factory method的区别在于：产品更多样，于是factory可选择进行产品的组合**





![1545560728800](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545560728800.png)![1545563324238](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545563324238.png)

