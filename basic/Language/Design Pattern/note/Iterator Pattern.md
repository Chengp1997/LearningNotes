# Iterator Pattern

-  **Single Responsability：只能有一个变化的原因**
- Intent: Provide a way to access the elements of an aggregate objects sequentially without exposing its underlying representation. 不需要暴露其内如结构就能够访问聚集元素

- Applicability: Use Iterator when: – to access an aggregate object's contents without exposing its internal representation. – to support multiple traversals of aggregate objects. – to provide a uniform interface for traversing different aggregate structures (that is, to support polymorphic iteration).
- ![1545586250327](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545586250327.png)
-  Consequences: The iterator pattern:
  -  It supports variations in the traversal of an aggregate比如动物类的列表能装牛/兔等其子类
  -  Iterators simplify the Aggregate interface.简化
  -  More than one traversal can be pending on an aggregate.

 A **robust iterator** is the one that works intact even if the associated collection is modified.只要使用iterator的方法来删除就没问题

主要的集中代码

```java
interface	Iterator	{						
    public	void	first();	//将游标指向第一个元素						
    public	void	next();	//将游标指向下一个元素						
    public	boolean	hasNext();	//判断是否存在下一个元素						
    public	Object	currentItem();	//获取游标指向的当前元素	
}

```

