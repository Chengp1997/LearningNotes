# Memento

> 为了使软件的使用更加人性化，对于误操作，我们需要提供一种类似“后悔药”的机制，让软件系统可以回到误操作前的状态，因此需要保存用户每一次操作时系统的状态，一旦出现误操作，可以把存储的历史状态取出即可回到之前的状态。
> 现在大多数软件都有撤销(Undo)的功能，快捷键一般都是Ctrl+Z，目的就是为了解决这个后悔的问题

- Pattern name: memento pattern
- problem:
- intent：Without violating encapsulation, capture and externalize an object's internal state so that the object can be restored to this state later. 在不破坏封装的原则下，能够捕捉对象的状态并保存
- 应用：a snapshot of (some portion of) an object's state must be saved so that it can be restored to that state later, and
  – a direct interface to obtaining the state would expose implementation details and break the object's encapsulation.
- solution:
  - ![1545580847002](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545580847002.png)
  - Originator：要保存的信息源
  - memento：保存的信息，可以有originator的一部分或全部
  - caretaker：保存信息的
  - ![1545149347903](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545149347903.png)
    - 针对此图：为了保证封装性，不希望其他类能够改变，因此set方法通过bankAccount的内部类来实现
    - 在实际开发中，原发器与备忘录之间的关系是非常特殊的，它们要分享信息而不让其他类知 道，实现的方法因编程语言的不同而有所差异，在C++中可以使用friend关键字，让原发器类 和备忘录类成为友元类，互相之间可以访问对象的一些私有的属性；在Java语言中可以将原发 器类和备忘录类放在一个包中，让它们之间满足默认的包内可见性，也可以将备忘录类作为 原发器类的内部类，使得**只有原发器才可以访问备忘录中的数据，其他对象都无法使用备忘 录中的数据。**
-  Consequences: The memento pattern leads to: – 
  - Preserving encapsulation boundaries 保证了信息的封装
  - 能够恢复状态
  - It simplifies the Originator使信息源不需要做很多
  - Using mementos might be expensive 使用备忘录可能会耗费巨大
  - Defining narrow and wide interfaces could be difficult in a language – 
  - Hidden costs in caring for mementos

```java
package	dp.memento;		
public class Originator{
    private	String	state;	
    public	Originator(){}		
　　//	创建一个备忘录对象					
    public	Memento	createMemento()	{		　　
        return	new	Memento(this);			
    }		
　　//	根据备忘录对象恢复原发器状态				
    public	void	restoreMemento(Memento	m)	{
        state	=	m.state;		
    }		
	public	void	setState(String	state){
        this.state=state;				
    }		
    public	String	getState()	{
        return	this.state;			
    }
}
package	dp.memento;		//备忘录类，默认可见性，包内可见	
class	Memento	{					
    private	String	state;
    public	Memento(Originator	o)	{
        state	=	o.getState();			
    }		
    public	void	setState(String	state)	{
        this.state=state;
    }
    public	String	getState()	{										
        return	this.state;	
    }	
}
package	dp.memento;	
public	class	Caretaker	{	
    private	Memento	memento;
    public	Memento	getMemento()	{	
        return	memento;				
    }		
    public	void	setMemento(Memento	memento)	{		
        this.memento=memento;				
    }	
}


```

