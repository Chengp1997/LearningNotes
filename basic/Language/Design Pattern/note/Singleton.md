# Singleton

- **intent**：保证一个类有且仅有一个实例，并且所有操作都基于此

- **Motivation**：为了节约系统资源，有时需要确保系统中某 个类只有唯一一个实例，当这个唯一实例创建成功之后，我们无法再创建一个同类型的其他 对象，所有的操作都只能基于这个唯一实例。

  为了确保对象的唯一性，我们可以通过单例模 式来实现，这就是单例模式的动机所在。

- **structure**： 

![1545185539658](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545185539658.png)

- Consequences: – Benefits:
  - Controlled access to sole instance…
  - Reduced name space… 全局的，就没有这个问题，节约资源
  - Sub-classing is possible with some refinement…
  - Permits a variable number of instances…允许可变数目的实例。
-  Implementation: Need to ensure a unique instance. What are the issues?
  - Need a method that obtains that instance for Client object
  -  Need to restrict access to creating instances; e.g. make the constructor private, package private or protected
  -  Might need to make access to instance thread safe
  - 首先需要有一个能获得这个对象的方法，有特定的访问方式，需要线程安全

单例模式有三个要点：一是某个类只能有一个实例；二是它必须自行创建这个实例；三是它 必须自行向整个系统提供这个实例。

```java
class	TaskManager		{	
    /**。因此，我们 可以在TaskManager中创建并保存这个唯一实例。为了让外界可以访问这个唯一实例，需要在 TaskManager中定义一个静态的TaskManager类型的私有成员变量**/
    private	static	TaskManager	tm	=	null;		
    
    /**	由于每次使用new关键字来实例化TaskManager类时都将产生一个新对象，为了确保 TaskManager实例的唯一性，我们需要禁止类的外部直接使用new来创建对象，因此需要将 TaskManager的构造函数的可见性改为private**/
    private	TaskManager()	{……}	//初始化窗口							
    public	void		displayProcesses()	{……}	//显示进程
    public	void		displayServices()	{……}	//显示服务		
    
    /**为了保证成员变量的封装性，我们将TaskManager类型的tm对象的可见性设置为private，但 外界该如何使用该成员变量并何时实例化该成员变量呢？答案是增加一个公有的静态方法， 如下代码所示：
**/
    public	static	TaskManager	getInstance(){	
        if	(tm	==	null){	
            tm	=	new	TaskManager();
        }										
        return	tm;						
    }					
    ……		
}

```

**为何需要为static：.instance需要在调用getInstance时候被初始化，只有static的成员才能在没有创建对象时进行初始化。且类的静态成员在类第一次被使用时初始化后就不会再被初始化，保证了单例。static类型的instance存在静态存储区，每次调用时，都指向的同一个对象。**

```java
public class MethodStats{ 
    private static volatile MethodStatsinstance; 
    private java.util.HashMap<String, Statistic> stats = new HashMap<String, Statistic>();
   
    /**
    由于每次使用new关键字来实例化TaskManager类时都将产生一个新对象，为了确保 TaskManager实例的唯一性，我们需要禁止类的外部直接使用new来创建对象，因此需要将 TaskManager的构造函数的可见性改为private
    **/
	private MethodStats(){}
	public static MethodStatsgetInstance(){ 
        if (null == instance) { 
            synchronized(MethodStats.class) { 
                if (null == instance) { 
                    instance = new MethodStats(); 
                } 
            } 
        } 
        return instance; 
    }
}
```



```java

```

两种对比：

> 饿汉式单例类在类被加载时就将自己实例化，它的优点在于无须考虑多线程访问问题，可以 确保实例的唯一性；从调用速度和反应时间角度来讲，由于单例对象一开始就得以创建，因 此要优于懒汉式单例。但是无论系统在运行时是否需要使用该单例对象，由于在类加载时该 对象就需要创建，因此从资源利用效率角度来讲，饿汉式单例不及懒汉式单例，而且在系统 加载时由于需要创建饿汉式单例对象，加载时间可能会比较长。
> 懒汉式单例类在第一次使用时创建，无须一直占用系统资源，实现了延迟加载，但是必须处 理好多个线程同时访问的问题，特别是当单例类作为资源控制器，在实例化时必然涉及资源 初始化，而资源初始化很有可能耗费大量时间，这意味着出现多线程同时首次引用此类的机 率变得较大，需要通过双重检查锁定等机制进行控制，这将导致系统性能受到一定影响。