# Template Method Pattern

-  **Hollywood Principle: Don’t call us, we’ll call you!**
- Intent: Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm’s structure. 定义一个算法，将其中某些步骤独立出来
- ![1545585353196](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545585353196.png)
- ![1545585366268](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545585366268.png)
  - AbstractClass:算法原步骤
  - ConcreteClass：执行特定步骤的类
- 应用：
  - To implement the invariant parts of an algorithm once and let subclasses implement the behavior that can vary.将相同的方法封装起来，然后将变化的部分变成其子类
  - When common behavior in subclasses should be factored out into a super-class…
  - To control subclass extensions…
- Consequences: The template method pattern: –
  -  Is good for code reuse… 它提取了类库中的公共行 为，将公共行为放在父类中，而通过其子类来实现不同的行为，它鼓励我们恰当使用继承来 实现代码复用。
  - Supports Inversion of Control principle (also called the Hollywood principle) … – Template methods call the following kinds of operation:可实现一种反向控制结构，通过子类覆盖父类的钩子方法来决定某一特定步骤是否需要执 行。
  - AbstractClassmethods… – Primitive abstract operations… – Factory methods… – Hook operations…在模板方法模式中可以通过子类来覆盖父类的基本方法，不同的子类可以提供基本方法的 不同实现，更换和增加新的子类很方便，符合单一职责原则和开闭原则。



![1545585831024](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545585831024.png)

如图，封装方式：相同的为private，这些DrinkMaker调用即可

需要修改的，有不同地方的为#，

最终方法为public