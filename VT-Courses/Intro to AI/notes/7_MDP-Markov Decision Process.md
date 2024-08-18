# MDP-Markov Decision Process

**Terms**

Q-state : action states: 在state下，实行action的动作获得的状态。可以用 (s,a)表示

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220503190149763.png" alt="image-20220503190149763" style="zoom:33%;" />

## MDP Definition

**Definition**: sequential decision problem for a fully observable, **stochastic environment** with a **Markovian transition model** and **additive rewards** 专门用来解决 non deteministic problem

**Approach**: 动态规划

**Define：**可以如下这么定义一个状态转移的形式。

- Set of state S 当前状态
- Set of actions a 采取的动作
- A start state 初始状态
- terminal states possibly >=1 结束状态，可能不止一个
- A transition model T(s,a,s') : 从状态s到s'需要动作a （或者P(s'|s,a）) 
- A reward function R(s,a,s'): 从状态s到s'采取动作a可能获得的reward。(Rewards may be positive or negative depending on whether or not they benefit the agent in question) Agent的目的就是最终能够获得最大的reward
- Possibliy  a discount factor $0<=\gamma<=1$ 

### discount factor意义

**Definiton**: a number between 0 and 1.

**Reason**: agent有可能因为reward不需要risk就可以一直获得而一直持续在几个状态之间切换，而不去探索心的未知state。为了克服这个问题，就可以设置finite horizons /discount factors. 

有以下两种方式来解决这个问题。第一种方式导致了policy和时间有关，并非最优；后者导致policy只和当前状态有关，是很好的方式。

- finite horizons-限定时间
  - **Nonstationary: a policy that depends on the time**
- Discount factors -- reward会衰退。（decay in the value of rewards over time.）
  - **Stationary: policy that optimal action depends only on the current state.**

在t时刻，reward不再是$R(s,a,s')$, 而应该是 $\gamma R(s,a,s')$

**value**

- Close to 0: 此时的reward已经没那么重要了
- close to1: 此时，reward依然很大，agent更愿意为了长远的reward而作出改变。
- =1: 是additive rewards

通过这个的使用，agent的目标就从追求additive utility变成了追求 **discounted utility**，也就是：

$U([s_0,a_0,s_1,a_1,s_2,...])=R(s_0,a_0,s_1)+\gamma R(s_1,a_1,s_2)\\+\gamma ^2 R(s_2,a_2,s_3)+...$

## Markovianess

$State_{past}->State_{present}->State_{future}$

**Markov Property: memoryless**

- given present, past 和 future是相互独立的，二者是**conditional independent**
- 作出什么action只和当前的state有关系

因此可以得出，只要知道present

根据上述的定义，可以得出以下公式：如何转移取决于当前状态s和当前行为a==$T(s,a,s')=P(s'|s,a)$==

### maximum expected Utility

EU(a): 实行动作a后，可能获得的utility

P(result(a)=s'): action a后到达state s'的概率

$EU(a) = \sum_s' P(Result(a)=s')U(s')$

一个rationale的agent追求能够获得最大的EU的policy

# Solution

### policy

MDP的目的就是要找到**optimal policy** $\pi ^*: S->A$

policy：在给定state s， 实现policy$\pi$的agent会在时间题选择 $a=\pi(s)$作为下一步的action.

**optimal** policy: 能够找到最终获得maximum expected total reward

- optimal solution目的是找到optimal policy，optimal policy目的是找到maximum expected total reward

### Example

given the following MDP, discount factor $\gamma=0.1$

![image-20220503155418559](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503155418559.png)

问，一下哪个policy更好？

![image-20220503155522305](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503155522305.png)

计算reward有：

a: 10; 

b: 0.1*10 VS 0.1^3^ * 1=**1** VS 0.001 向左

c: 0.1^2^ * 10 VS 0.1^2^*1 = **0.1** VS 0.01. 向左

d: 0.1^3^*10 VS 0.1 * 1 = 0.01VS**0.1** 想右

e:1

所以，policy2更好，因为这个policy下，每个状态都可以获得它的最大reward

## Bellman Equation

### Optimal Quantities

- optimal value of a state s, U^*^(s): 在s状态起始时，能够获得的optimal value（expected utility starting in s and acting optimally）
- optimal value of a q-state(s,a), Q^*^(s,a): s状态下，实行动作a，能够获得的optimal value（expected utility starting out having taken action *a* from state *s* and (thereafter) acting optimally）
  - ==$Q^*(s,a)=\sum_{s'}P(s'|s,a)[R(s,a,s')+\gamma U^*(s')]$==

根据这个定义可以得出Bellman Equation(下面详细描述)

### Definition

**Bellman Equation**: 

因为：$EU(a) = \sum_s' P(Result(a)=s')U(s')$所以有

下一个状态的utiliy=当前获得的R+gamma*下一个状态可能获得的utility

==$U^*(S)=max_aQ^*(s,a)\\=max_a\sum_{s'}P(s'|s,a)[R(s,a,s')+\gamma U^*(s')]\\=max_aQ^*(s,a)$==

简而言之，就是s在实行a动作后，到达s'的optimal value 是Q-state(s,a)的最大值。

期中：P-s实行动作a转移到s’的概率；R-s实行动作a到s‘能够获得的reward；

#### Example

如图，给定$\gamma=1，r=-0.04,P(up)=0.8,P(other)=0.1$

![image-20220503204508883](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503204508883.png)

#### Policy Extraction

根据计算的值可以获得对应的

optimal：选择每次最大的q-value

## Solving MDPs 

- Offline algorithm
  - **value iteration**
  - **policy iteration**
  - Linear programming
- Online algorithm
  - Monte Carlo planning

### Value Iteration

A **dynamic programming** algorithm that uses an iteratively **longer time limit** to compute time-limited values until convergence 不停迭代，直到收敛

![image-20220503210108098](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503210108098.png)

#### Method

- Initialization: U0(s)=0 
- Do Bellman Equation: 通过这个式子得出的不停更新每个state的值utility![image-20220503210343174](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503210343174.png)
- 直到值收敛

Complexity： O(S^2^A)

#### Summary

value iteration最终会收敛到一个特定的值。可以通过policy extraction，获取最终的policy

- repeats bellman update
- 一般，**每一个state的max值不会改变**
- 似乎做的比需要的多：**Policy通常比values收敛得快多了。**
- 即使utility function的估算出错了，也很有可能获得optimal policy
- 如果一个action获得的明显比其他的都好，我们不需要这个state的精确计算

#### Example

2种action：fast/slow $\gamma = 0.5$

![image-20220503212815792](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503212815792.png)

1. initialization： U0(s)=0

   ![image-20220503212857860](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503212857860.png)

   我们可以根据状态转换计算得出U1

   ![image-20220503212928925](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503212928925.png)

   同理，可以计算U2

   ![image-20220503214649089](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503214649089.png)

一直计算，直到值收敛，即为最终答案。

最终，根据每个state的最大q值获得最终的optimal policy



### policy Iteration

value iteration的缺点：速度太慢，每次迭代都需要更新所有值。

为了解决这个问题，可以使用policy iteration. 既能够保证value iteration 的最佳，又能够显著提高效率。

#### Method

- initialize policy 定义一个policy。可以是随机的，但是**如果和最终的optimal越趋近，收敛的会更快。**
- Repeat until convergence
  - policy evaluation：计算当前policy下的utility![image-20220503233157784](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503233157784.png)
  - policy improvement：根据对应的utility值，更新policy ![image-20220503234144363](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503234144363.png)
- 如果policy不变，就收敛了

#### Example

仍然是赛车例子，定义policy如下

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220503234411046.png" alt="image-20220503234411046" style="zoom:50%;" />

1. 根据当前的policy，计算当前的utility

   ![image-20220503234449650](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503234449650.png)

2. 更新policy

   ![image-20220503234500943](/Users/chengeping/Library/Application Support/typora-user-images/image-20220503234500943.png)

   <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220503234510022.png" alt="image-20220503234510022" style="zoom:50%;" />





