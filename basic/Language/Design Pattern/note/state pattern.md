# state pattern

- **Favor composition over inheritance**

- > 状态模式用于解决系统中复杂对象的状态转换以及不同状态下行为的封装问题。当系统中某 个对象存在多个状态，这些状态之间可以进行转换，而且对象在不同状态下行为不相同时可 以使用状态模式。
  >
  > 状态模式将一个对象的状态从该对象中分离出来，封装到专门的状态类 中，使得对象状态可以灵活变化，对于客户端而言，无须关心对象状态的转换以及对象所处 的当前状态，无论对于何种状态的对象，客户端都可以一致处理。

- Intent: Allow an object to alter its behavior when its internal state changes. The object will appear to change its class. 内部状态改变，对象能改变对应的类？

- ![1545584214133](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545584214133.png)

  - ![1545584523226](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545584523226.png)

- Collaborations: 

  -  Context delegates state-specific requests to current ConcreteStateobject. 
  - A Context may pass itself as an argument to the State object… letting the State object access the Context if necessary… 
  - Context is the primary interface for clients. 
  - Clients can configure context with State objects… 
  - Either Context or the Concrete State subclasses can decide on state transitions and the events that trigger them…

-  Consequences: The state pattern leads to: 

  - localization of state-specific behavior for different states.将所有与某个状态有关的行为放到一个类中，只需要注入一个不同的状态对象即可使环境 对象拥有不同的行为。
  - explicit state transitions explicit… 封装了状态的转换规则，在状态模式中可以将状态的转换代码封装在环境类或者具体状态 类中，可以对状态转换代码进行集中管理，而不是分散在一个个业务方法中。
  - shareable State objects…
  - tightly coupling of concrete sub-classes of State.
  - 状态模式对“开闭原则”**的支持并不太好，**增加新的状态类需要修改那些负责状态转换的源 代码，否则无法转换到新增状态；而且修改某个状态类的行为也需修改对应类的源代码。

允许状态转换逻辑与状态对象合成一体，而不是提供一个巨大的条件语句块，状态模式可 以让我们避免使用庞大的条件语句来将业务方法和状态转换代码交织在一起。