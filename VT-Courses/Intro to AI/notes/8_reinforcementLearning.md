## Intro

### Offline Planning

- MDP
- Agent ==**have full knowledge** of transition function and reward function== 
- **Precompute** optimal actions encoded by the MDP without ever actually taking any actions
- 简而言之，Agent了解这个世界，能够提前预知并且不需要任何尝试地把所有情况提前计算清楚得出最佳的policy

### Online Planning

- MDP
- Agent **may not** know **reward function or transition model**
- **==must try actions==** and states out to learn and get feedbacks
- Agent **use feedback to estimate an optimal policy** through a process known as reinforcement Learning
- 简而言之，Agent有可能对这个世界一无所知。需要一步一步尝试，并且通过在尝试中获得的反馈来作为下一次状态转移的输入，从而获得最佳policy。

# Reinforcement Learning

## Definition

Reinforcement is a kind of online planning.

- Receive **feedback** in the form of **rewards** 以之前reward作为反馈，得到了多少就反馈给agent
- Agent's utility is defined by the reward function--- 也就是说，agent使用feedback来输出utility，来estimate an optimal policy.
- Agent **must** act to **maximize expected rewards**.必须选择能够maximize reward的举动。
- 所有的获取到的信息，都是根据 observed samples of outcomes!

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220319233424976.png" alt="image-20220319233424976" style="zoom:50%;" />

### Model

- Still assume as a MDP
  - A set of states **s $\in$ S**
  - A set of **actions (per state) A**
  - A model **T(s,a,s’)**
  - A reward function **R(s,a,s’)**
- **Goal**: Still looking for policy $\pi(s)$
- New Problem: **不知T & R**
  - 必须try out actions and states 

### Term

sample: (s,a,s',r) tuple

Episode: Collection of samples

Often, an agent continues to take actions and **collect samples** in succession until arriving at a terminal state. 

Agents typically **go through many episodes** during exploration in order to collect sufficient data needed for learning.

## Types

- **Model-based**: Attempts to **estimate the transition and reward functions** with samples(s,a,s') attained during exploration **before using these estimates to solve MDP**(normally with value or policy iteration) 分析transition 和 reward functions
  - Adaptive Dynamic Programming
- **Model-free**: Attempts to **estimate the values or Q-values of states** directly, without ever using any memory to construct a model of the rewards and transitions in MDP 分析计算时得到的values。
  - Direct evaluation
  - Temporal difference learning
  - Q-learning
- **Passive**: **给定policy**，让agent遵循这个policy学习每个状态获得的值
  - Direct evaluation
  - Temporal  difference learning
  - Adaptive Dynamic Programming
- **Activate**：Agent能够使用获得的feedback来**不断更改policy直到获得optimal policy**
  - Action-Utility Learning
    - Q-learning: **learns a Q-function Q(s,a)**, agent can **choose** what to do in state s by finding the action with highest Q-value
  - Policy search
    - Agent learns a policy that maps directly from states to actions

## Model-based RL

### Direct evaluation

**Passive**

IDEA：Lean an **approximate model**(know/unknown),Solve for values as if the learned model were correct. 选择一个相近的模型来进行学习，把这个作为准则来进行值的求解

#### Steps

- Step1 : **Learn empirical MDP model**
  - 观察这个MDP产生的对应的action的对应的outcome(s,a)
  - Normalize，**获得估算的T(s,a,s')**
  - 根据experiece(s,a,s') **获得对应的R(s,a,s')**
- Step2:  **Solve the learned MDP**
  - 例如，使用value iteration的方法来求解MDP

#### **Example**

- 如下例子。Policy $\pi$是approximate model。 根据这个model来收集信息。

- Episodes是Agent运行后收集的samples。一共运行了几次，收集了几组的Episodes。

- Agent 通过收集的数据，进行Normalize，得出Transition Function 和 Reward function.
  - Normalize 方法：\- dividing the count for **each observed tuple (s,a,s')** by the sum over the counts **for all instances where the agent was in Q-state (s,a)**. Count(s,a,s') / Count(s,a)
  - Normalization of counts **scales them such that they sum to one, allowing them to be interpreted as probabilities**

![image-20220320010055244](/Users/chengeping/Library/Application Support/typora-user-images/image-20220320010055244.png)

根据上面的方法进行统计有：

| s    | a     | s'   | Count |
| ---- | ----- | ---- | ----- |
| A    | Exit  | x    | 1     |
| B    | East  | C    | 2     |
| C    | East  | A    | 1     |
| C    | East  | D    | 3     |
| D    | Exit  | x    | 3     |
| E    | North | C    | 2     |

根据MDP的定理，有 $T(s,a,s')=P(s'|a,s)，我们可以根据计数来估算transition function，以及根据获得的值来获得reward。于是上述情况有：

![image-20220320124740411](/Users/chengeping/Library/Application Support/typora-user-images/image-20220320124740411.png)

Summary：

By the law of large numbers, 当我们获取的样本越来越多，transition function和reward function就会**越来越趋近真实情况**。

**Whenever we see fit**, 当我们看到和真实情况相符合的时候，就可以停止agent的训练，并且通过value/policy iteration**生成新的policy。**

#### Pros & Cons

优点：很简单且高效，只需要计数和normalization就可以

缺点：保存计数成本太高。

### Adaptive Dynamic Programming(ADP)

这是**Passive** reinforcement learning方法。

- 这个方法利用了Bellman equation的限制。
- Basically l**earns the transition model T and the reward function R** (model based)
- 根据学习到的T,R,我们可以使用**policy evaluation**来进行计算。
- ==**Pollicy Evaluation**==： calculate utilities for some **fixed policies** until convergence 给定policy，计算utilities，直到收敛。

#### Step

- Given policy，calculate $U_i=U^{\pi i}$
- $U_i(s)=\sum _{s'}P(s'|s,\pi i(s))[R(s,\pi i(s),s')+\gamma U_i(s')]$
- R(s): 当看到new sate，就存储获得的reward值
- T(s,a,s') --- P(s'|s,$\pi (s)$): 关注到达的次数。

![image-20220320201745029](/Users/chengeping/Library/Application Support/typora-user-images/image-20220320201745029.png)

#### Problem

- 需要解决多个方程组
  - 如果状态过多，**很难计算**
  - 如果在iteration时不断改进policy，会轻松些？

## Model-free RL

Agent 对环境一无所知，对transition model一无所知。Agent根据每个状态的直接表象来进行学习自己如何behave。无需通过model就可以获得最佳的policy。

### Types

- Direct Utility Estimation
- Temporal difference learning
- Q-learning



## Passive Reinforcement Learning

给定Policy，让agent遵循policy来take actions，并且学习每个状态获得的值

Simplified task: **Policy. Evaluation**

- Input: **a fixed policy $\pi(s)$**
- Goal: learn the **state values** 目的是为了获得每个状态下的value
- 不知T(s,a,s'),也不知R(s,a,s')，通过循环学习每个状态的值 从而得到。

> 注意：虽然和offline learning很像，但是不是！实际上take actions了！

### Types

- Direct utility estimation
- Adaptive dynamic programming(ADP)
- Temporal-difference(TD) learning

### Direct Utility Estimation(Direct Evaluation)

**Goal: Compute values for each state under $\pi$**、

#### Idea

Idea: Average observed sample values 每次取观察到的样本的平均值

- Agent acts according to policy $\pi$
- Every time the agent visits a state:
  - count the total rewards 总共的rewards
  - count the total number of times each state is visited 总共被访问的次数
- Average samples 取平均值

#### Example

Example1

![image-20220320162820334](/Users/chengeping/Library/Application Support/typora-user-images/image-20220320162820334.png)

根据上图统计可得

![image-20220320162838758](/Users/chengeping/Library/Application Support/typora-user-images/image-20220320162838758.png)

Example 2

![image-20220320163410967](/Users/chengeping/Library/Application Support/typora-user-images/image-20220320163410967.png)

根据上图统计可得：

| State | Total Rewards | Times Visited | U^pi^ (s) |
| ----- | ------------- | ------------- | --------- |
| A     | -10           | 1             | -10       |
| B     | 16            | 2             | 8         |
| C     | 27-11=16      | 4             | 4         |
| D     | 30            | 3             | 10        |
| E     | 8-12=-4       | 2             | -2        |

#### Pros and cons

**Pros**

- 容易理解
- 不需要知道T,R(model free)
- 多次sample计算后，能够偶的正确值。

**Cons**

- 每个state都需要单独计算
- wastes information about state connections
- **需要花费很长时间 才会converge**

#### Why not use it

Bellman equation 有：可见，是跟neighbor的状态是有关系的，这些都需要T和R才能得到，但是我们不知，因此，direct evaluation 无法用。这是Bellman equation 的限制。

![image-20220320192406769](/Users/chengeping/Library/Application Support/typora-user-images/image-20220320192406769.png)

### Temporal-Difference Learning

这个算法既可以克服ADP的重复计算问题，又能更加快速地收敛。

#### Idea

Still fixed policy, still doing evaluation

Learn from **every experiece**

**On-policy learning**: learn the values for a specific policy before deciding whether that policy is suboptimal and needs to be updated.

#### Steps

Update V(s) each time we experience a transition (s, a, s’, r)  一有变化就update

- Move values toward value of whatever successor occurs: **running average** 更正值

  - **$\alpha$是learning rate**取值在0，1之间。可以理解为。在原基础上，每次修正error*learning rate，也就是每次更正的是error！

  <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220321000400580.png" alt="image-20220321000400580" style="zoom:50%;" />

- Uses an exponential moving average with sampled values until convergence to the true values of states under 𝜋 

  - Makes **recent samples more important**

    其实对于每一个都有：<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220321010530242.png" alt="image-20220321010530242" style="zoom:33%;" />

    于是有

    <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220321010551602.png" alt="image-20220321010551602" style="zoom:33%;" />

    可以看出，最近的一次值次啊是最重要的，影响下一次的。

  - Turn α **into a function** that decreases as the # of times a state **has been visited increases**把a变成可变的，而不是一成不变的。

  - Decreasing learning rate (α) can give converging averages 一点一点降低学习速度

  - > It’s **typical to start out with learning rate of α = 1**, accordingly assigning *V* π (*s*) to whatever the first sample happens to be, **and slowly shrinking it towards 0**, at which point all subsequent samples **will be zeroed out and stop affecting our model of *V* π (*s*).**

#### Code

![image-20220321005905445](/Users/chengeping/Library/Application Support/typora-user-images/image-20220321005905445.png)

#### Example

![image-20220321010345722](/Users/chengeping/Library/Application Support/typora-user-images/image-20220321010345722.png)



#### Pros & Cons

**Pros**

- 不需要Transition model 就可以达到update的效果
- 比ADP收敛慢但是**much higher variability**
- update方式简单，并且计算也比ADP少。

- learn at every step, hence using information about state transitions as we get them since we’re using **iteratively updated versions** of *V* π (*s*′ ) in our samples rather than waiting until the end to perform any computation.
- give exponentially **less weight to older**, potentially less accurate samples.
- converge to learning true state values **much faster with fewer episodes than direct evaluation.**



## Active Reinforcement Learning

Agent **decides what actions to take and learns the underline MDP**

The agent attempts to find an optimal policy by **exploring** different actions in the world

Agent能够使用获得的feedback来**不断更改policy直到获得optimal policy**

> 之前的TD虽然已经做到的了很好的趋近的效果，但是存在一个问题：只能模仿已经存在的policy。但是当我们需要寻找新的policy的时候，就无法做到update。

于是有了Active Reinforcement Learning

### Idea

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220321020652853.png" alt="image-20220321020652853" style="zoom: 25%;" /><img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220321020703414.png" alt="image-20220321020703414" style="zoom: 25%;" />

Don't know T & R, choose actions NOW!

**Goal**: learn the optimal policy/values

**Steps**: **Learn Q-Value， 而不是value！**Q-value能够看出选择的方向。Makes action selection model-free too！

区别：Q value寻找的是各个方向上较大的值。

![image-20220321153451317](/Users/chengeping/Library/Application Support/typora-user-images/image-20220321153451317.png)



### Types

- Action-utility learning
  - Q-learning:Agent learns Q-function Q(s,a), agent可以选择highest Q-value的action
- Policy search
  - Agent learns a policy 𝜋 that maps directly from states to actions

### Terms

#### On-policy VS off-policy

- On-policy: 
  - Learns the values for a specific policy **before** deciding **whether that policy is suboptimal and needs to be updated**
  - TD / direct utility estimation

- Off-policy:
  - Learns an optimal policy **even when taking suboptimal actions**
  - Q-learning / approximate Q-learning

#### Exploration VS Exploitation

An agent must make a trade off between **exploitation** of the current best action to maximize its short-term reward and **exploration** of previously **unknown states** to gain information that can lead to a change in policy (greater rewards) Agent必须作出权衡：利用当前的最好的action来获得短期最好效益 或者 探索新的未知state从而获得更好的效益

- exploitation：always chooses the best reward action(选择当前最好)
- Exploration:  always select random moves(选择未知)

几种方法

1. Naive approach：随意选择
2. Greedy approach： 贪心，但overestimate rewards forunexplored states (贪心的是未访问过的地方，权值更高)

Greedy in the limit of infinite exploration(**GLIE**) 有限探索中进行贪心

### GLIE Schemes

**IDEA**

- simplest: Agent chooses a **random action** at time step t with **probability 1/t** and to follow the greedy policy otherwise.
  - Every time step, flip a coin
  - With (small) probability e, act randomly
  - With (large) probability 1-e, act on current policy

但是使用这个简单的贪心方法，有个问题：虽然达到了访问其他地方的目的，但是可能会一只在一个地方打转。解决方案：每次降低probability e/使用exploration function

- Better: give some weight to actions that the agent **has not tried very often**, while **tending to avoid actions that are believed to be of low utility** --- 给未访问过的地方赋予高权值，对肯定会有low utility的地方避免执行此动作。
  - ![image-20220321164540856](/Users/chengeping/Library/Application Support/typora-user-images/image-20220321164540856.png)
  - ==Mix Exploration & Expoitation==

![image-20220321150631694](/Users/chengeping/Library/Application Support/typora-user-images/image-20220321150631694.png)

### Safe Exploration 

那么如何保证安全的探索呢？

- Simplest：Negative rewards 是可以赋值negative的
  - Many actions are irreversible
    - Absorbing state: no actions have any effect and no rewards are received
    - Self-driving car: large negative rewards (Car crashes) 对于自动驾驶，可以赋予非常大的negative值
    - Learn how the traffic lights work and explore it by ignoring red light 通过闯红灯来获取值
- **Better idea**：choose a policy that works reasonably well **for the whole range of models** and thus have a reasonable chance of being the true model 选好的？？？？

### Q-learning

Q-learning: sample-based Q-value iteration

> Q-value 的计算 一般需要transition model和reward function，但是Q-learning克服了这一点，使用迭代的方式，直接获得当下states的值。Q-learning is model-free

#### Steps

Learn Q-values as you go:

![image-20220321161412026](/Users/chengeping/Library/Application Support/typora-user-images/image-20220321161412026.png)

- Receive a sample (s,a,s’,r) 

- Consider your old estimate: Q(s,a)

- Consider your new sample estimate: $sample=R(s,a,s')+\gamma ^{max}_{a'}Q(s',a')$ 每次进行估算，选择value最大的值。

- **Incorporate the new estimate into a running average:**

  $Q(s,a)=Q(s,a)+\alpha[sample-Q(s,a)]$ 同理为

  $Q(s,a)=(1-\alpha)Q(s,\alpha)+(\alpha)sample$ 

  最后更新Q-value

#### Pros & Cons

**Pros**

- Q-learning **converges to optimal policy** -- even if you’re acting suboptimally! -**Off-policy learning**
- **It doesn’t matter how the agent selects actions**
- Off-policy!: 和on-policy不同，即使目前运行的policy不是最佳的，也能获得最好的

**Cons**

- The agent must explore enough
- The agent have to eventually make the learning rate small enough but not decrease it too quickly
- 简而言之：需要遍历次数过多。并且最后learning rate还要足够小，但是每次减小的速度又不能太快，不好控制！

#### code

![image-20220321162043001](/Users/chengeping/Library/Application Support/typora-user-images/image-20220321162043001.png)

#### Probelm

Even if you learn the optimal policy, you still make mistakes along the way!

- Regret is a measure of your total mistake cost: the difference between your (expected) rewards, including youthful suboptimality, and optimal (expected) rewards
- Minimizing regret goes beyond learning to be optimal – it requires optimally learning to be optimal 
- Example: random exploration and exploration functions both end up optimal, but random exploration has higher regret



### Generalization

Q-learning 会保存一张完整的q-value表格，需要大量的q-value，states，这对memory负担很大。而且在真实情况下， 我们也不可能学习所有的state

> This means we can’t visit all states during training and can’t store all Q-values even if we could for lack of memory.

因此，需要generalization！

我们可以根据经验，学习一部分的training states

**Solution**：describe a state using a vector of features (properties)

The key to generalizing learning experiences is the **feature-based representation** of states, which represents each state as a vector known as a feature vector.

- using a weighted linear combination of features f1,...,fn:
- 如下，[f1(s),f2{s}……fn(s)]是feature vector，[f1(s,a), f2(s,a) ……fn(s,a)]是Q-state vectors。[w1,w2,w3……wn]是权重vector
- ![image-20220321174403372](/Users/chengeping/Library/Application Support/typora-user-images/image-20220321174403372.png)

**Advantage**：our experience is summed up in a few powerful numbers

**Disadvantage**：states may share features but actually be very different in value!



### Approximating Q-Learning

- 相比Q-learning， 更加generalized，并且more memory-efficient
- 只需要存储 weight vector即可，可以通过计算直接得出对应的Qvalue
- 它对于一些general的situations进行学习，并且可以引用到其他相似情形上
- 每一个状态都可以用feature vector来表示。

根据linear Q-learning 有：<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220321215946666.png" alt="image-20220321215946666" style="zoom: 25%;" />

defining difference as: （观察到的sample-之前Q-value）

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220321214955377.png" alt="image-20220321214955377" style="zoom:50%;" />

Exact Q: $Q(s,a)=Q(s,a)+\alpha [difference]$

Approximate Q: <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220321215152320.png" alt="image-20220321215152320" style="zoom:50%;" />

由此可以看出，我们并不需要存储qvalue，可以通过计算得出，只需要存储weight即可。

#### Steps

- Comopute Q-value 首先计算各个action的Q-value，为了寻找最高的Value从而作出action
- Make action based on the highest Q-value 根据当前作出action
- Compute Q-values and sample from the reward 根据得到的reward，计算sample
- Compute difference 计算difference
- Update weight vector 更新

#### Example

![image-20220321223622388](/Users/chengeping/Library/Application Support/typora-user-images/image-20220321223622388.png)

### Policy search

Problem：有时候会发现，运行最好的并不是Q value最好的policy

解决方案： learn policies that maximize rewards, not the values that predict them

#### Idea

**fine-tune the policy** as long as its performance improves, then stop

Policy search: start with an ok solution (e.g. Q-learning) then fine-tune by hill climbing on feature weights

- Q-learning: find the value that is close to Q*
- Policy search: find the value that results in good performance

Start adjust the parameters to improve the policy by hill climbing on feature weights

## Comparison

### ADP VS TD

ADP

- 收敛更快
- 和真实值相差不会很多，更准确。
- 会根据所有successors，根据probabilites来调整自己的权重
- Makes as many as it needs to restore consistency between the utility estimates *U* and the transition model *T*

TD

- 不需要构建transitionmodel
- 根据观察到的successor来调整自己的state
- 一次值更改一个state
- update更简单，计算更简单。



### Model free VS Model Based

Model based

- 需要更新T和R，每次都要进行计算
- 使用这些获得的估算来解决MDP问题。

Model free

- 不需要T，R，直接估算value/q-value

### Direct utility estimation/ADP/TD

|                | Direct utility estimation     | ADP                                                 | TD                                                           |
| -------------- | ----------------------------- | --------------------------------------------------- | ------------------------------------------------------------ |
| Types          | Model free                    | model based                                         | Model free                                                   |
| Implementation | Easy                          | Harder                                              | Easy                                                         |
| Converge       | very slow/ long time to learn | Fast                                                | Medium                                                       |
| Pros           |                               | **Fully** exploits Bellman constraints              | **Partially** exploits Bellman constraints (adjustsstateto“agree”with observed successor (Not all possible successors)) |
| Cons           |                               | Each update is a full policy evaluation (Expensive) |                                                              |

