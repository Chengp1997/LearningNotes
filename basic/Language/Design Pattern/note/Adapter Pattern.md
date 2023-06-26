# Adapter Pattern

- intent：Convert the interface of a class into another interface clients expect. Adapter lets classes work together that couldn’t otherwise because of incompatible interfaces. 将接口转换为客户需求的接口，adpater使类之间能交互
- Applicability：你想使用他人的类，然而它暴露的接口和你的类不符合
- solution：
  - ![1545583089530](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545583089530.png)
  - ![1545583097929](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545583097929.png)
  - target：客户使用的接口。目标抽象类定义客户所需接口，可以是一个抽象类或接口，也可以 是具体类。
  - client：提供target
  - adaptee：：适配者即被适配的角色，它定义了一个已经存在的接口，这个接口 需要适配，适配者类一般是一个具体类，包含了客户希望使用的业务方法，在某些情况下可 能没有适配者类的源代码。
  - adapter：：适配器可以调用另一个接口，作为一个转换器，对Adaptee和Target进 行适配，适配器类是适配器模式的核心，在对象适配器中，它通过继承Target并关联一个 Adaptee对象使二者产生联系。
- Consequences：使类高重用
-  An object adapter
  - lets a single Adapter work with many Adaptees—that is, the Adapteeitself and all of its subclasses (if any). The Adapter can also add functionality to all Adapteesat once.一个适配器能配多个适配者
  - makes it harder to override Adapteebehavior. It will require subclassingAdaptee and making Adapter refer to the subclass rather than the Adapteeitself. 可以适配适配者的子类。
- A class adapter 
  - adapts Adaptee to Target by committing to a concrete Adapter class. As a consequence, a class adapter won't work when we want to adapt a class and all its subclasses.	对于Java、C#等不支持多重类继承的语言，一次最多只能适配一个适配者类，不能同时适 配多个适配者；
  - lets Adapter override some of Adaptee's behavior, since Adapter is a subclass of Adaptee.适配者类不能为最终类，如在Java中不能为final类，C#中不能为sealed类；
  - introduces only one object, and no additional pointer indirection is needed to get to the Adaptee.

```java
class	Adapter	extends	Target	{//对象适配器		
    private	Adaptee	adaptee;	//维持一个对适配者对象的引用	
    public	Adapter(Adaptee	adaptee)	{	
        this.adaptee=adaptee;						}	
    public	void	request()	{	
        adaptee.specificRequest();	//转发调用				
    }	
}

```

```java
//类适配器
class	Adapter	extends	Adaptee	implements	Target	{	
    public	void	request()	{	
        specificRequest();						
    }		
}

```

​	
