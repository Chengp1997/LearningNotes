# JWT, Session, Cookie, Token

[区别](https://juejin.cn/post/6844904034181070861)

用来记录登录状态的

方案1: session+cookie，sessionID是连接桥梁。

方案2 ：使用session -- 但是分布式系统中，可能会[存在问题](https://server.51cto.com/article/662998.html)

方案3: 使用token



### 注意！！

Nginx可能会导致session ID不一致的问题

[原因](https://juejin.cn/post/6850418112253149197)



