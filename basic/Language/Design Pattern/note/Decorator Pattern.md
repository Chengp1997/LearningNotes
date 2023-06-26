# Decorator Pattern

- **Design Principle: Open closed principle(Classes should be open for extension, but closed for modification.)**
- **intent**:能够动态地为对象增加职能。
- Problem: Decorator pattern
- problem:  
- Collaborations:  Decorator forwards requests for its component object… 
- solution:
  - ![1545532154499](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545532154499.png)
- Consequences:装饰者可以在所委托被装饰者的行为之前与/或之后，加上自己的行为，以达到特定的目的。 
  - More flexibility than static inheritance… 
  - Avoids feature-laden classes high up in the hierarchy… 
  - A decorator and its component aren’t identical… 都是相同类型
  - Lots of little objects…装饰者会导致设计中出现许多 小对象，如果过度使用，会让 程序变得很复杂。
- Implementation: Issues to consider 
  -  Interface conformance…
  -  Omitting the abstract Decorator class… 
  -  Keeping Component classes lightweight…
  -  Change only the skin of the object, not the gut (else use Strategy)
- Example：JAVA I/O
  - ![1545540184445](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545540184445.png)
  - ![1545540221547](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545540221547.png)









```java
package com.company.DecoratorPattern.example;

/**
 * 该例子为，超市收钱
 * 超市又需要打折又需要减折扣
 */
public interface Cash {
    public double getCash(String product);
}

```

```java
package com.company.DecoratorPattern.example.Cash;

/**
 * 实际要被装饰的为此类，将此类传入decorator来进行装饰
 */
public class CashImplement implements Cash {
    @Override
    public double getCash(String product) {
        return 20.0;
    }
}

```

```java
package com.company.DecoratorPattern.example.Cash;

public class CashDecorator implements  Cash {
    private Cash cash;

    /**
     * 将具体实现类传入进行装饰！
     * @param cash  具体实现的cash类
     */
    public CashDecorator(Cash cash){
        this.cash=cash;
    }
    @Override
    public double getCash(String product) {
        return cash.getCash(product);
    }
}

```

```java
package com.company.DecoratorPattern.example.Cash;

public class BacktrackCashDecorator extends CashDecorator {
    /**
     * 将具体实现类传入进行装饰！
     *
     * @param cash 具体实现的cash类
     */
    public BacktrackCashDecorator(Cash cash) {
        super(cash);
    }

    public double getCash(String product){
        return super.getCash(product)-0.1;
    }
}

```

```java
package com.company.DecoratorPattern.example.Cash;

public class RebateCashDecorator extends CashDecorator {
    /**
     * 将具体实现类传入进行装饰！
     *
     * @param cash 具体实现的cash类
     */
    public RebateCashDecorator(Cash cash) {
        super(cash);
    }

    /**
     * 将父类的getCash override
     * @param product
     * @return  返回具体的cash值
     */
    public double getCash(String product){
        return super.getCash(product)*0.1;
    }
}

```

```java
package com.company.DecoratorPattern.example.Cash;

public class Client {
    public static void main(String[] args){
        Cash cash=new CashImplement();
        //调用减折扣
        CashDecorator decoratedCash=new RebateCashDecorator(cash);
        System.out.println("价格为："+ decoratedCash.getCash("lala"));
        //又打折又满减
        CashDecorator newDecoratedCah=new RebateCashDecorator(new BacktrackCashDecorator(cash));
        System.out.println("价格为："+newDecoratedCah.getCash("lalala"));
    }
}

```

