# Chain of responsibility

-  Intent: Avoid coupling sender of request to its receiver by giving more than one object chance to handle request.  Chain receiving objects and pass request along until an object handles it. ![1545587131668](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545587131668.png)
- ![1545587205888](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545587205888.png)
- 多个对象提出请求，并且请求之间没有高低关系/使用这个！！
-  Consequences: The Chain of Responsibility pattern leads to: 
  - **Reduced coupling.** The pattern frees an object from knowing which other object handles a request. An object only has to know that a request will be handled "appropriately." Both the receiver and the sender have no explicit knowledge of each other, and an object in the chain doesn't have to know about the chain's structure. 	职责链模式使得一个对象无须知道是其他哪一个对象处理其请求，对象仅需知道该请求会 被处理即可，接收者和发送者都没有对方的明确信息，且链中的对象不需要知道链的结构， 由客户端负责链的创建，降低了系统的耦合度。
  -  **Added flexibility in assigning responsibilities to objects.** Chain of Responsibility gives you added flexibility in distributing responsibilities among objects. You can add or change responsibilities for handling a request by adding to or otherwise changing the chain at run-time.请求处理对象仅需维持一个指向其后继者的引用，而不需要维持它对所有的候选处理者的 引用，可简化对象的相互连接。
  - **Receipt isn't guaranteed.** Since a request has no explicit receiver, there's no guaranteeit'll be handled—the request can fall off the end of the chain without ever being handled. A request can also go unhandled when the chain is not configured properly在给对象分派职责时，职责链可以给我们更多的灵活性，可以通过在运行时对该链进行动 态的增加或修改来增加或改变处理一个请求的职责。
  - 一个请求也可能因职责链没有被正确配置而得不到处理。
  - 对于比较长的职责链，请求的处理可能涉及到多个处理对象，系统性能将受到一定影响， 而且在进行代码调试时不太方便。如果建链不当，可能会造成循环调用，将导致系统陷入死循环。
-  Implementation: Someissues implementinga Chain of Responsibility: 
  - Implementing the successor chain. There are two possible ways to implement the successor chain:
    - Define new links (usually in the Handler)
    - Use existing links 
  - Connecting successors. If there are no preexisting references for defining a chain, then you'll have to introduce them yourself. 
  - Representing requests – Object operations (methods) – Simple parameters to an operation – Request objects