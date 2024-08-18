# Decision Networks

Bayesian Networks + expectimax

### Terms

Nodes types

- Chance Node <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505034612858.png" alt="image-20220505034612858" style="zoom:50%;" />
- (Action)Decision node<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505034621795.png" alt="image-20220505034621795" style="zoom:50%;" /> decision maker 做选择的地方
- utility node<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505034628402.png" alt="image-20220505034628402" style="zoom:50%;" />utility function是deterministic的

### Example

![image-20220505035729066](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505035729066.png)

这个网络可以被简化

![image-20220505035744760](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505035744760.png)

- chance nodes are omitted
- utility node 和 current-state node/decision node 相连
- utlility node 代表了和它相关的action的expected utility，每个都和一个action-utility function 有关

### DN Evaluation

1. Set the evidence variables **for the current state**
2. For each possible value of the action node:
   1. **Set** the action node to that value
   2. Calculate the **posterior probabilities** for the parent nodes of the utility node, using a standard probabilistic inference algorithm
   3.  Calculate the resulting utility for the action
3.  Return the action **with the highest utility**

也就是，选择一个action，然后计算选择这个action的所有可能。最后选出最大的action

### Calculate maximum expected utility

![image-20220505040931122](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505040931122.png)

![image-20220505043045096](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505043045096.png)

## Value of information

通过上面可以知道，选择最重要的信息很重要。

Theory

- Enables an agent to **choose** what information to acquire agent可以通过information的价值来进行选择
- Involves a simplified form (belief state) of sequential decision making 
- Agent’s action will be **affected** by the value of observation
- Observing new evidence almost always has some cost 会有多的cost

### Value of Perfect information (VPI)

也就是最具有价值的information！

- Depends on the **current state** of information 当前state下的最有价值的
- It can change as more information is acquired 当更多的信息被获取，可能会改变
- Mathematically quantifies the amount an agent’s  maximum expected utility is expected to increase if it observes some new evidence如果有新的evidence，increase
- Is it worth to get more evidence to help decide action to make? 是否需要获取更多的evidence来决定action是不一定的。可以通过计算获得，具体看下面例子。
  - Cost of get new evidence  取决于获取成本
  - New MEU after get this new evidence 获得新的evidence后的新值

![image-20220505044127394](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505044127394.png)

#### Example

- with no evidence
  - 当没有evidence的时候，可以看到，utility是由action weather决定，于是，当下的MEU计算取最大值为70
- evidence : forecast = bad
  - 此时观察到了一个evidence，forecast=bad，根据当前的evidence，来计算新的MEU
  - 根据新的probability，计算得出，当下最大的MEU是53
- evidence： forecast=good
  - 根据这个计算，获得当下的最大的MEU是95
- 因为不知道具体的new evidence可能是哪一个，所以要计算各种情况。对于这个例子，就是evidence=bad/good的两种情况
  - 计算的，总的MEU是77.78
- 最后，计算VPI可以得到，最终MEU上升了7.78。

![image-20220505044328545](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505044328545.png)

#### Properties

- Nonnegative <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505045024223.png" alt="image-20220505045024223" style="zoom:50%;" />
  - 获取新的evidence，一定会让你做出更informed的决定。
- Nonadditive:<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505045040814.png" alt="image-20220505045040814" style="zoom:50%;" />
  - 几个新的evidence获得的VPI是不能简单相加的，因为会相互影响。
- Order-independent <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505045053184.png" alt="image-20220505045053184" style="zoom:50%;" />
  - 不论先观察到什么evidence，最终都不会改变



## Bandit Problem

现实问题：有时候，我们无法得知propability。

需要尝试，探索来获得新的information

- Unknown probability distribution of winnings
- Gambler must exploit best action to obtain rewards
- Gambler must explore previous unknown states and actions to gain information

### Partially Observable MDPs(POMDPs)

Fully observable: agent 永远知道自己现在处于什么state

Partially observable：

- ageng 不需要知道自己在哪个state
- 无法执行policy推荐的action

那么！该如何计算utility？该如何选择optimal action？

#### Definition

前面都和正常MDP相同，重点是最后，引入了sensor model P

**P(e|s) 在state s可以获得evidence的概率**

optimal action only 和agent当前的belief state 有关系

![image-20220505051723933](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505051723933.png)

注意POMDP中加入的observation。observation可以理解为定义的观察者。function也就是sensor model。P(o|s)也就是在状态s，是否能够获得新的evidence。

这个模型可以理解为。在state b，做出尝试action a。此时在b，a的状态下，需要判断是否能获得新的evidence。

![image-20220505051853151](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505051853151.png)

#### **Example**

考虑吃豆人游戏

游戏中，belief state是根据evidence来决定的。probabilistic 需要用来根据已知的past预测未来的的新的evidence。

解决方案

方案1: 使用truncated expectimax来计算。

但是问题在于：此时如果发生了bust情况，无法解决。

于是需要VPI-based agent来处理这个问题。

![image-20220505052145593](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505052145593.png)

#### **VPI based agent**

![image-20220505053112710](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505053112710.png)

事务不是fixed的，随着时间改变可能会不停的变换。当前的时间可能会影响之后的事情而产生新的事实从而影响之后的选择。也就是上图表达的意思。



