# Intro

## Probability

- Product Rule
  - P(X,Y)=P(X|Y)P(Y)
- Bayes’Rule
  - P(Y|X)=P(X|Y)P(Y)/P(X) 
  - P(Y|X)=𝛼P(X|Y)P(Y)
- Independence
  - P(x|y)=P(x)orP(y|x)=P(y) 
  - P(x∧y)=P(x)P(y)
- Conditional Independence
  - **P**(X,Y|Z)=**P**(X|Z)**P**(Y|Z)=**P**(X∧Y|Z)
- Conditional probability
  - P(x | y) = P(x, y) / P(y)
- Chain Rule
  - P(x1, x2, x3) = P(x3 |x2,x1) P(x2 | x1) P(x1) (Chain rule)

于是对相同的P(x1,x2,x3)可以解构为：

- **P**(Toothache , Catch , Cavity)
   = **P**(Toothache, Catch |Cavity) **P**(Cavity) (product rule) = **P**(Toothache | Cavity) **P**(Catch | Cavity) **P**(Cavity)
- **P**(Toothache,Catch,Cavity)
   = **P**(Toothache | Catch, Cavity) **P**(Catch | Cavity) **P**(Cavity)

# Bayesian Networks

**Probabilistic Graphical Models**

把这些概率关系用图来表示。

Allow inference, learning.

## Definition

- It is a **data structure** to represent the dependencies among variables

- It can represent essentially any full **joint probability distribution** concisely

- It is a directed graph

  – Node: random variable, discrete or continuous

  – Links or arrows connect pairs of nodes

  – Nodes and links specifies the **conditional independence** relationships

- Directedacyclicgraph(DAG)

### Properties

- Acyclic
- contain no redundant probability values
- more compact than full joint distribution

### Rules

BNs correct 的前提是each node is **conditionally independent** of its other predecessors in the node ordering

![image-20220412165850548](/Users/chengeping/Library/Application Support/typora-user-images/image-20220412165850548.png)

根据上面的定义，可以得出以下的例子。

1. In the alarm model above, we would store probability tables *P*(*B*),*P*(*E*),*P*(*A* | *B*,*E*),*P*(*J* | *A*) and *P*(*M* | *A*).
2. 所以可以得出P(j,m,a,-b,-e)

![image-20220412165821719](/Users/chengeping/Library/Application Support/typora-user-images/image-20220412165821719.png)

### Pros

N boolean variables 2^N^

N-node net O(N*2^k+1^)

节省空间

### Relations

- A variable is conditionally independent of its other predecessors, given its parents 
- Each variable is conditionally independent of its non-descendants, given its parents
- A variable is conditionally independent of all other nodes in the network, given its parents, children, and children’s parents
   (**Markov blanket**)

## Inference

Inference是概率推理的一种方式。

Definition：Compute the **posterior probability distribution P(X|e)** for a set of **query variables**, given some observed **event**

- Posterior probability: an **event** will happen after **all evidence or background information** has been taken into account
- Prior probability: an **event** will happen before you taken any new **evidence** into account



### Inference by Enumeration

解决这类问题的一个方式是使用enumeration。但是，这要求：the creation of and iteration over an exponentially large table.

根据定义有：<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220412182045461.png" alt="image-20220412182045461" style="zoom: 33%;" />

例子：

**问题**--- Query: P(Burglary | JohnCalls = true, MaryCalls = true)

- Hidden variables Y: Earthquake and Alarm
- 关系：<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220412182335445.png" alt="image-20220412182335445" style="zoom:50%;" />

于是可以求：

![image-20220412183325326](/Users/chengeping/Library/Application Support/typora-user-images/image-20220412183325326.png)

于是求得如下

分别求+a, -a/ +e -e. 这是enumeration的方法

![image-20220412183423458](/Users/chengeping/Library/Application Support/typora-user-images/image-20220412183423458.png)

#### Procedure

**join all factors, sum out all hidden variables**

例子：

![image-20220412235632570](/Users/chengeping/Library/Application Support/typora-user-images/image-20220412235632570.png)

![image-20220412235642409](/Users/chengeping/Library/Application Support/typora-user-images/image-20220412235642409.png)

### Variable Elimination

和上面的方式很像，区别在于，一步一消。join后消除无关，继续join

- Inference by Enumeration: 
  - slow. 需要joint所有分布再sum out hidden variable

- Variable Elimination
  - interleave joining and marginalizing
  - 仍然 NP-hard, but！ 一般更快

![image-20220413002621117](/Users/chengeping/Library/Application Support/typora-user-images/image-20220413002621117.png)

#### Procedure

![image-20220413003007695](/Users/chengeping/Library/Application Support/typora-user-images/image-20220413003007695.png)

- 目标<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220413004241438.png" alt="image-20220413004241438" style="zoom:33%;" />
- 如果有给定的evidence，从这个开始选择。
- 如果还有hidden variables（not Q/evidence）
  - 选择出现hidden variable的所有P
  - join
  - elminate
- join all remaining ,normalize

例子：

第一步，要找B，j, m， hidden variable就是A， E。因此，如下，选择其中的A

![image-20220413004741144](/Users/chengeping/Library/Application Support/typora-user-images/image-20220413004741144.png)

第二步：还有hidden，删除E

![image-20220413005025932](/Users/chengeping/Library/Application Support/typora-user-images/image-20220413005025932.png)

第三步 normalization

![image-20220413005043982](/Users/chengeping/Library/Application Support/typora-user-images/image-20220413005043982.png)

### Summary

- Guaranteed independencies of distributions can be deduced from BN graph structure 可以通过bn来表示independence
- D-separation gives precise conditional independence guarantees from graph alone
- A Bayes net’s joint distribution may have further (conditional) independence that is not detectable until you inspect its specific distribution

## Conditional Independence in BN

我们要了解BN中的相互独立，需要先了解什么样的情况在BN中点和点之间时相互独立的。

### D-seperation

在BN中常见情况是：如果给定Z，X和Y可以相互独立

#### Casual Chain

**Evidence along the chain “blocks” the influence**

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220504144110011.png" alt="image-20220504144110011" style="zoom:50%;" />

**给定Y，X和Z是相互独立的:**

因为有：P(x,y,z) = P(x)P(y|x)P(z|y)

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220504144204719.png" alt="image-20220504144204719" style="zoom:50%;" />

#### Common causes

**Observing the cause blocks influence between effects.**原因可能会造成二者是否独立

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220504144428269.png" alt="image-20220504144428269" style="zoom:50%;" />

给定**Y，X和Z相互独立**：

因为有：P(x,y,z)=P(Y)P(X|Y)P(Z|Y)

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220504144536554.png" alt="image-20220504144536554" style="zoom:50%;" />

#### Common Effect

**Observing an effect activates influence between possible causes.**

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220504151110472.png" alt="image-20220504151110472" style="zoom:50%;" />

X,Y 时相互独立的，不需要给定Z

### How to find conditional independence  pairs in BN

**No active paths = independence!** 如果XY之间所有path都是inactive，那么他们相互独立。

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220504151027230.png" alt="image-20220504151027230" style="zoom:50%;" />

#### Example

![image-20220504151531409](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504151531409.png)

![image-20220504151720596](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504151720596.png)

![image-20220504151822145](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504151822145.png)

![image-20220504152223060](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504152223060.png)



## Sampling

概率推理的另一种方式可以使用计算出现频率的方式。

原先的inference的方法虽然也可行，但是当BN太大，就难以实行，于是有了sampling的方法。其实相当于一种，趋近inference的方法。

**IDEA**

- Basic idea
  - Draw **N samples** from a sampling distribution *S*
  - Compute an **approximate posterior probability**
  - Show this converges to the true probability *P*

### Prior sampling

所有都采样一遍，简而言之，根据采样到的结果，计算最后的值



![image-20220504164248545](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504164248545.png)

![image-20220504164045341](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504164045341.png)

![image-20220504164953160](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504164953160.png)



### rejection sampling

![image-20220504165517336](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504165517336.png)

使用prior sample 抽样，如果结果和evidence 相同，则赋值。重复操作直到取样N次

**Example**

如下图，要求的值不易获取，于是使用rejection sampling。只取样要的sprinkler = false的结果。如图一躬取了100次，只有27次时true的，取这27次的结果来进行计算。

![image-20220504165705121](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504165705121.png)

![image-20220504170537131](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504170537131.png)



### likelihood weighting

**most computationally efficient** 效率最高！

![image-20220504170723903](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504170723903.png)

为何不用rejection sampling： 如果运气不好，**全部拒绝了，就没有样本了**

likelihood weighting： evidence 不变，抽取其余

假设要求 P(shape|blue)

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220504171826131.png" alt="image-20220504171826131" style="zoom:50%;" />

![image-20220504175147177](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504175147177.png)

简而言之，如果是evidence就和weight相乘，否则就sample

![image-20220504175354014](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504175354014.png)

以下是，针对取样，你要怎么判断结果是什么类型。

![image-20220504185416226](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504185416226.png)



### gibbs sampling

likelihood weighting和这个都可以处理大的networks

#### Procedure

1. Start with an **arbitrary state** with the **evidence variables** fixed at their observed values任意的一个state开始，但是evidence variable取对应的值
2. One variable at a time, randomly sampling a value for one of the nonevidence variable Xi for the next state  一次抽样一个，

和likelihood的区别

从上面的例子我们可以看到，likelihood只对children有影响，并且weight可能会很小。gibbs刚好克服这些问题。

![image-20220505024411537](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505024411537.png)



### Example

![image-20220505025931776](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505025931776.png)

![image-20220505030330226](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505030330226.png)

### Summary

![image-20220505033133237](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505033133237.png)

