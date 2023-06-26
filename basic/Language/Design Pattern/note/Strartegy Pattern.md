# Strategy Pattern

简单来说，将策略封装为接口，其他新的策略继承此接口，使用方可选择对应的策略。

> 策略模式的用意是针对一组算法，将每一个算法封装到具有共同接口的独立的类中，从而使得它们可以相互替换。策略模式使得算法可以在不影响到客户端的情况下发生变化。
>
>
>
> 优点：
>
> 使用策略模式可以把行为和环境分割开来。环境类负责维持和查询行为类，各种算法则在具体策略类（ConcreteStrategy）中提供。由于算法和环境独立开来，算法的增减、修改都不会影响环境和客户端。当出现新的促销折扣或现有的折扣政策出现变化时，只需要实现新的策略类，并在客户端登记即可。策略模式相当于"可插入式（Pluggable）的算法"。

实质：

![img](https://images.cnblogs.com/cnblogs_com/zhenyulu/PicX00115.gif)

- Pattern name: Strategy pattern（policy）
- Problem: 
  - 定义了许多解决方法，将每个算法封装，并使他们能够内部互相切换
  - 策略模式使这些方法能够根据client的需求灵活改变
- Solution:
  - ![img](https://images.cnblogs.com/cnblogs_com/zhenyulu/PicX00115.gif)
- Consequences:
  -  Families of related algorithms. ：创造了多种算法family，将有相似特点的算法进行继承，成为一个family。
  - An alternative to sub-classing.：对每个新算法都更加灵活，能够简易地为Context 类建立新的子类，并且都具备这些strategy，你能够容易切换这些strategy。并且也可新增其他strategy family而互不影响
  - Strategies eliminate conditional statements. ：减少这些判断循环，能够直接选择对应的算法









![1544969387414](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1544969387414.png)

```java
interface CompressionAlgorithm {  //此处使用接口，定义一些类似的功能
  public CompressedFile compress(File f);
  public File decompress(CompressedFile f);
}

class AudioCompression implements CompressionAlgorithm {
  public CompressedFile compress(File f) {}
  public File decompress(CompressedFile {}
}//接口的实现类

public class Sender {//使用环境
  private CompressionAlgorithm ca;
  public setCompressionAlgorithm(CompressionAlgorithm ca) {//设置对应的算法
    this.ca = ca;
  }
    
  public void sendFile(File f) {
    CompressedFile cf = ca.compress(f);
    send(cf);
  }
  protected send(CompressedFile cf) {
   //...
  }
}

main:
Sender s = new Sender();//如此，避免了多个条件判断，能够简单确定算法
s.setCompressionAlgorithm(new AudioCompression());
s.sendFile(audioFile);
```

> 使用特点：应当由客户端自己决定在什么情况下使用什么具体策略角色。策略模式仅仅封装算法，提供新算法插入到已有系统中，以及老算法从系统中"退休"的方便，策略模式并不决定在何时使用何种算法。
>
>
>
> 使用情况：
>
> 1. 如果在一个系统里面有许多类，它们之间的区别仅在于它们的行为，那么使用策略模式可以动态地让一个对象在许多行为中选择一种行为。 
>
> 1. 一个系统需要动态地在几种算法中选择一种。那么这些算法可以包装到一个个的具体算法类里面，而这些具体算法类都是一个抽象算法类的子类。换言之，这些具体算法类均有统一的接口，由于多态性原则，客户端可以选择使用任何一个具体算法类，并只持有一个数据类型是抽象算法类的对象。 
>
> 1. 一个系统的算法使用的数据不可以让客户端知道。策略模式可以避免让客户端涉及到不必要接触到的复杂的和只与算法有关的数据。
>
> 1. 如果一个对象有很多的行为，如果不用恰当的模式，这些行为就只好使用多重的条件选择语句来实现。此时，使用策略模式，把这些行为转移到相应的具体策略类里面，就可以避免使用难以维护的多重条件选择语句，并体现面向对象设计的概念。
>
>

参考：

https://www.cnblogs.com/zhenyulu/articles/82017.html