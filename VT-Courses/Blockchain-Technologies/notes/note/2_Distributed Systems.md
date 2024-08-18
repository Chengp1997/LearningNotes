# Distributed Systems

A group of independent entities communicated with one another in a coordinated manner.



### Key characteristics

- Transparency(最重要的)
  - Illusion of a single system(所有信息都是透明的，让使用的人看起来仿佛是一个server)
    - hide all internal organization, communication details 
    - uniform system
- Openness
  - offer services according to standard rules
  - 可以和其他平台进行交流
- Heterogeneity: variety and differences in hardware and sotware components
- resource sharing： 每个node之间可以互享信息
- concurrency
- 



### Design goals

- reliability
  - Failure detection, self stabilization(能够容忍错误)
  - preserve correctness and integrity in the presence of faulty/malicious nodes
- scalability
- high performance
  - low latency, high throughput
- consistency
  - synchronization between concurent tasks
  - update,replication, cache, failure, 都要同步
- Security



### CAP Theorem

分布式系统是不可能同时做到的，但是多为二者结合

C: Consistency 同步的。所有节点之间都是相同的，同步的。

A: availability 一定能够有回应。every request to the system receives response

P: partition tolerance 高容忍度的，即使挂了几个节点，整个系统仍然能够运作



#### A+P(availability over consisitency)

系统能够运作更重要，可能不会很同步

#### C+P(Consistency over availability)

系统可能会返回错误信息，如果这个信息有可能不是同步的时候

### Architectural Models

#### Client-Server (C-S) Architecture

- basic model: 一个服务器，多个client
  - server: resource-powerful
  - Client: resource-limited
- Asymmetric, partially distributed：不是服务分布等
- advantages: 容易维护，服务范围也可以很广，设计实现简单
- disadvantage：容易单点故障（服务器挂了就挂了）,不容易扩展，所有trust都集中在单点上。

#### Peer-to-Peer(P2P) Architecture

- 节点之间互享分享信息

- symmetric：每个节点都是相同的， 既是服务提供者又是消费者，没有server
- fully distributed： 完全分布式。
- advantages：分布trust，平衡资源，容易扩容，容量极大，容错率高
- disadvantage：难以备份所有数据，难以管理，难以保证一致性，不稳定（可能网速问题等导致）
- Example： blockchains，vehicular network, file sharing

#### 分布式VS去中心化

P2P 是分布式的，但是提供了一定程度的去中心化。一些P2P系统仍然需要中心来做决策。

**Decentralized is NOT all-or-nothing**



#### unstructured P2P Network

loose restriction on overlay structure, data location and resource distribution

简单来说，就是一个比较随意的P2P系统，nodes and resources are loosely coupled,

#### structured P2P network

nodes and resources are tightly-coupled， everyone has their own task

- 每个节点在network中都有特定的角色
- Distributed hash table 用来记录node-task
- simplifying content location
- Low resiliency against churn
- 难以构建

#### hybrid p2p network

CS+P2P

- central authorities to help nodes navigate each other 有中心化的角色用来定位各个节点
- tend to improve overall performance trade-off b/w centralization vs node equality
-  inherit the best of both worlds

### Consensus Mechanisms

**motivation** : **Reliability** and **fault-tolerance** in distributed system

- correct operation in the presence of corrupted nodes/faulty processes
- reach an agreement among correct nodes/processes for a single data value

简单来说，如何让各个节点互相说服对方。

Important！

#### Consensus Protocol

**definition**

propose and decide

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220207181140771.png" alt="image-20220207181140771" style="zoom:50%;" />

**properties**

- Validity: 最后决定的值一定是某个节点提出的。
- uniform agreement**: no two correct nod**es can decide on different values
- integrity: 每个节点最多只能决定一次数值
- termination：最后所有节点都会同一个一个答案

**Types of failure**

- network :网络问题
- crash：shut down
- byzantine: nodes act arbitrarily 可能呗攻击了

#### Synchronous/Asynchronous Distributed systems

**Synchronous**

- Strong assumptions about **time** and **order of events**

- **easy** to reach consensus
- Not really practical

**Asynchronous**

- 对时间和事件顺序没有要求这么严格，
  - clock可以不是那么准确
  - 信息也可能延迟
- 难以达到consensus
- more practical

#### FLP impossibility result

**FLP impossibility** for asynchronous deterministic consensus

对于一个**full asynchronous system**：

**Safe,liveness,fault tolerant**: 2选3 重点是fault tolerant，因此前面两个二选三

这三个点是不可能同时达到的。

- **Safety**： something bad will never happen 两个节点得出的结论不会是不同的
  - violation of safety can be shown in a finite execution
- **Liveness**: something good will eventually happen （不会无限循环，一定会有最后的state）
  - violation of liveness cannot be shown in a fine execution
- FLP impossibility 并不意味值，不可能同时拥有

**Applying  to blockchain**

- Nakamoto used by Bitcoin
  - **liveness over safety**
  - ![image-20220207183241386](/Users/chengeping/Library/Application Support/typora-user-images/image-20220207183241386.png)
- BFT style consensus protocols
  - For 传统分布式系统
  - **safety over liveness**:
  - ![image-20220207183253024](/Users/chengeping/Library/Application Support/typora-user-images/image-20220207183253024.png)
  - Can prove liveness in the partial synchronous network model
  - ![image-20220207183400387](/Users/chengeping/Library/Application Support/typora-user-images/image-20220207183400387.png)

**Consensus protocols**

- Paxos
  - Majority-wins protocols少数服从多数
  - asynchronous settings
  - Consistency, fault tolerance, but may get stuck
  - 不能适应byzantine-fault
- Raft：Raxos的变种
  - leader-follower模式 多一个做决策的
  - 不能适应Byzantine-fault
- pBFT“（practical Byzantine fault tolerance)
  - 能够适应Byzantine - fault

**permissioned & permissionless consensus**

**传统的**共识协议-**permissioned**& work in a **closed** environment

- 参与的节点是一定的
- 节点互相知道对方，并且可以互相授权

但是！如果对应到open environment：

- 对Sybil attack，非常容易坏掉
  - Sybil attack：会创造很多虚拟节点，导致无法容忍错误

**Permissionless**

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220207184012640.png" alt="image-20220207184012640" style="zoom:50%;" />



**Consensus in public blockchain**

- 所有节点都有



<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220207184551182.png" alt="image-20220207184551182" style="zoom:50%;" />