# Command Pattern

- Pattern name: Command pattern
- Problem:
- Intent： Encapsulate a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations. 将请求封装成对象，以便使用不同的请求。
- motivation：有时候只需要执行

> 在软件开发中，我们经常需要向某些对象发送请求（调用其中的某个或某些方法），但是并 不知道请求的接收者是谁，也不知道被请求的操作是哪个，此时，我们特别希望能够以一种 松耦合的方式来设计软件，使得请求发送者与请求接收者能够消除彼此之间的耦合，让对象 之间的调用关系更加灵活，可以灵活地指定请求接收者以及被请求的操作

- solution:
  - ![1545578832652](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545578832652.png)
  - ![img](https://images.cnblogs.com/cnblogs_com/zhenyulu/PicX00103.gif)
  - 客户（Client）角色：创建了一个具体命令(ConcreteCommand)对象并确定其接收者。 
  - 命令（Command）角色：声明了一个给所有具体命令类的抽象接口。这是一个抽象角色。 
  - 具体命令（ConcreteCommand）角色：定义一个接受者和行为之间的弱耦合；实现Execute()方法，负责调用接收考的相应操作。Execute()方法通常叫做执方法。 
  - 请求者（Invoker）角色：负责调用命令对象执行请求，相关的方法叫做行动方法。 
  - 接收者（Receiver）角色：负责具体实施和执行一个请求。任何一个类都可以成为接收者，实施和执行请求的方法叫做行动方法。 
- advantage
  - 可以容易地实现对请求的Undo和Redo
  - 你可以把命令对象聚合在一起，合成为合成命令。比如宏命令便是合成命令的例子。合成命令是合成模式的应用。
  - 当命令优势需要以特定顺序执行时，可使用此模式



> 首先命令应当"重"一些还是"轻"一些。在不同的情况下，可以做不同的选择。
>
> 如果把命令设计得"轻"，那么它**只是提供了一个请求者和接收者之间的耦合而己**，命令代表请求者实现请求。 
>
> 相反，如果把命令设计的"重"，那么**它就应当实现所有的细节，包括请求所代表的操作，而不再需要接收者了。**当一个系统没有接收者时，就可以采用这种做法。









### example

![1545145481688](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545145481688.png)

```java
abstract	class	Command	{	
    public	abstract	void	execute();		
}

class	Invoker	{				
    private	Command	command;		
				//构造注入				
    public	Invoker(Command	command)	{
        this.command	=	command;			
    }		
				//设值注入	
    public	void	setCommand(Command	command)	{										this.command	=	command;						
     }		
				//业务方法，用于调用命令类的execute()方法						
    public	void	call()	{
        command.execute();		
    }
}

class	ConcreteCommand	extends	Command	{			
    private	Receiver	receiver;	//维持一个对请求接收者对象的引用		
    public	void	execute()	{									
        receiver.action();	//调用请求接收者的业务处理方法action()						
    }
}
class	Receiver	{				
    public	void	action()	{	
        //具体操作	
    }
}


```

