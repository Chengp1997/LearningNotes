# CSP (constraint satisfaction problem)



![image-20220215080810343](/Users/chengeping/Library/Application Support/typora-user-images/image-20220215080810343.png)

![image-20220215080918734](/Users/chengeping/Library/Application Support/typora-user-images/image-20220215080918734.png)

![image-20220215081416179](/Users/chengeping/Library/Application Support/typora-user-images/image-20220215081416179.png)

A<=2

B=1

D>=3

D<C

A B C D E 不想等

Variables- A B C D

Domains 1 2 3 4 

Constraints 见上

以上可得： A=2 B=1 C=4 D=3 E =



## Intro

Planning vs Identification

- Planning: sequences of actions
  - The path to the goal is the important thing
  - Paths **have various costs, depths** 
  - **Heuristics** give problem-specific guidance

- Identification：assignments to variables
  - The **goal** itself is important, not the path
  - All paths **at the same depth** (for some formulations)
  - **CSPs are specialized for identification problems**

### Search problems

> **CSP is a special subset of search problems**

一般常见的**search problem**拥有以下的一些特点：

- state is a "black box"： arbitrary data structure
- Goal test: any function over states 与state有关的任何方法。
- successor function can also be anything

**CSP problem：**

- State: State is defined by ==**variables X~i~**== with values from a ==**domain D**== (sometimes D depends on i)
- Goal test: ==**A set of constraints**== specifying allowable combinations of values for subsets of variables

常见的现实生活中的问题：

- Assignment problems: e.g., who teaches what class 
- Timetabling problems: e.g., which class is offered when and where? 
- Hardware configuration 
- Transportation scheduling 
- Factory scheduling 
- Circuit layout 
- Fault diagnosis
- ……



## Terms

### **Components**

- X: variables
- D: domains.值域，X可能取的值
  - 如果是有限的离散变量，常见问题例如map coloring, scheduling 问题，8 queen 问题
  - 如果是离散的无限值域，可以考虑linear constraints
  - 连续值域，polynomial time/linear programming
- C: constraints（unary/binary/higher-order/global）



### **Assginments**

- **Consistent(legal) assginment**: 完成赋值后，仍然满足constraints
- **Partial assignment**: 仍然有一些变量还是unassigned
- **Complete** assignment：所有的variable都被赋值了



### **Solution**

> A **solution** to CSP is a **consistent, complete** assignment(注意这里complete, 不论csp是否有解无解，前提是所有变量都已经赋值了)

- **partial** solution: **partial** assignment that is **consistent**. 

CSP is an **NP-complete** problem, which means that exists no known algorithm for finding solutions in polynomial time



### **Constraint Graph**

- Node: 变量X
- Edge: 只要两个node之间有constraints，就会相连
- constraint：如图，两个节点之间是binary constraint，单个节点T是unary

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220223183218148.png" alt="image-20220223183218148" style="zoom: 25%;" />

**Binary CSP**:  each constraint relates(**at most**)two variables 每个变量最多都有两个关联



### **Constraint Propagation**

一般的一元的状态搜索算法：通过扩展node来访问successor

1. **CSP**：

- 通过选择一个新的变量并对其进行赋值来生成successor
- **Constraint propagation**: a specific type of inference 可以理解为一种推断方式？

2. **Constraint propagation:**

- 使用constraints来减少变量可选择的值域
- 每次做完选择后，下一次选择会拥有更少的选项
- may be！可以在preproces的步骤做完，甚至能在search开始前
- local consistency！



**Node consistency**

==unary constraints:== 一元限制。限制变量本身。

例如:$SA!=green-->SA:red/blue$

**Arc consistency**

==Binary constraints==: 二元限制。关注每两个节点之间的关系。

例如: $SA!=NT$

arc consistency中，每一对节点之间的关系中，任意节点之间的关系是相互的

**Path consistency**

加深了binary constraints的关系（通过path来了解三个变量之间的关系）

对于{X~i~,X~j~}, 存在第三个节点X~m~, 如果为X~i~,X~j~赋值后，仍然存在一个值，使得X~m~赋值后，{X~i~,X~m~},{X~m~,X~j~}仍然符合constraints，则符合path consistency

**K consistency**

和上面集中关系 相同，当对第k个赋值后，能够保证剩下的k-1个变量都仍然符合关系并且能够进行赋值。

- 1-Consistency (**Node** Consistency): Each single node’s domain has a value which meets that node’s unary constraints
- 2-Consistency (**Arc** Consistency): For each pair of nodes, any consistent assignment to one can be extended to the other
- K-Consistency: For each k nodes, any consistent assignment to k-1 can be extended to the kth node.
- Strong k-consistency: also k-1, k-2, … 1 consistent
  - Claim: strong n-consistency means we can solve without backtracking!

**Global consistency**

在现实中更加常见。例如数独



## Standard search Formulation CSP

states defined by the vlues assigned so far

- **Initial state:** **empty** assignment{}
- Successor function: **为unassigned的变量进行赋值**
- Goal test：检测当前的赋值是否是**complete** 并且**satisfies all constraints**

简单来说，就是每一步都为下一个unassigned的变量进行赋值，并且检测当前是否符合constraints，是否已经完成了所有的赋值。



那么该如何解决这类问题呢？很明显，用之前的BFS，DFS是无法解决这类问题了，这类问题，我们应该使用的方法为：**Backtracking**



# Solving CSP

## Backtracking

> Backtracking search is the **basic uninformed algorithm** for solving CSPs

- **One variable at a time**
  - 变量之间的**赋值顺序是可以互相交换的**。（WA=red & NT=blue和NT=blue&WA=red是相同的）**整体的赋值的顺序非常重要**，这会影响到算法的性能。Variable assignments are commutative, so fix ordering -> better branching factor! 
  - 一次操作中的动作顺序并不重要
  - 每次赋值值需要考虑一个变量即可。
- **check constraints as you go**
  - 每次选择的值都不会和之前的assignment发生冲突
  - 如果没有符合的值存在，**backtrack**, 返回上一个赋值操作并更改。

==**Backtracking = DFS+variable-ordering+fail-on-violation**==

从下面伪代码可以注意到，assign的顺序很重要，会影响到backtrack的生长。顺序的选择可能会影响到failure的早晚。从而影响算法性能。

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220223190452948.png" alt="image-20220223190452948" style="zoom:50%;" />



那么是否可能提前寻找到failure呢，我们需要改进back tracking

## Improving Backtracking

主要有三种方法进行改进：**filtering/ordering/structure**方面的改进

- Backtracking = DFS + variable-ordering + fail-on-violation
- **Select-unassigned-variable and order-domain-values** implement the general-purpose **heuristics**
- **Inference**  imposes forward checking/arc/path/k consistency

可见，相比于原先的回溯算法， 多了inference的步骤，能够提前发现是否有failure。

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220223232237106.png" alt="image-20220223232237106" style="zoom:50%;" />

#### Inference

**infer new domain reductions** on the **neighboring variables** every time **when making a choice of a value for a variable.**

其实就是一个更新的操作，每次都通知neibor新的状况，进行更新。

inference包括例如forward checking，arc-consistency等

> forward checking 是最简单的一种inference的形式
>
> - When a variable X is assigned, for each unassigned variable Y that is connected to X by a constraint, delete any value in Y’s domain that is inconsistent with the value chosen for X



## Filtering

### Forward checking

**Filtering**: Keep track of domains **for unassigned variables** and **cross off bad options**

**Forward checking**: **Cross off values** that **violate** a constraint when added to the existing assignment

简单来说，每次assign value后，都关注一下unassaigned variable，根据constraints, 然后剔除不合适的值。

Forward checking propagates information **from assigned to unassigned variables**, but **doesn't provide early detection for all failures:**

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220223193300020.png" alt="image-20220223193300020" style="zoom:50%;" />

然而！可以发现，虽然filtering后，backtrarcking的效率提高了，不需要查找所有情况，但是仍然没法提前发现failure，仍然需要走到最后知道failure出现。

### Arc-Consistency

For **single arc consistency**:

对于一条边，有：An arc X -> Y is **consistent** **iff** for==every== x in the tail there is ==some== y in the head which could be assigned without violating a constraint。

也就是说，对于这样一条边的consistency，这样一条关系下，一定会有y对应的值

Forward checking也就是保证了 **assigned -> unassigned** 的arc consistency, 是**单边的**！因此效率并不高

> Arc consistency detects failure **earlier** than forward checking

#### AC-3

AC-3可以理解为forward checking的进阶版，forward checking是从assigned到unassigned，而AC-3则是**双边**的。

**procedure**：

- **Stores** all arcs in the CSP in queue Q
  - 每个binary constraints 都会变成双边关系，每个方向都有.
  - 例如：Q=[SA->V, V->SA]
- **Remove** arc(X~i~,X~j~) from Q and make X~i~ arc-consistent with X~j~ 删除对应的Arc，并且接下来进行判断。
- **Continue remove** values from the domains of variables until queue is empty.删除不符合的所有values
- **Output**: an arc-consistent CSP that have smaller domains/ no solution exist 要么输出相符的CSP值域，也就是符合的值，要么就输出，没有解决方案！
- **Time Complexity**: O(cd^3^)

**Important Nodes**

- 删除node的时候，每次都**删除的是tail**！(如果有X->Y的关系，删除的是Y的值域而不是X的)
- 如果有值被删除了，这个关系还**需要再次被check一遍**。例如：在Q中，如果SA->V的关系中，V中有value被删除了，那么SA->V的关系需要再次塞入Q中，等待二次确认。如果二次确认没有值再被删除，就可以移出Q。
- ==Arc consistency detects failure earlier than forward checking==
- ==**Can be run as preprocessor or after each assignment**==

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220223223230809.png" alt="image-20220223223230809" style="zoom:50%;" />

#### Limitation

即使使用这个方法后，仍然有：1/multiple/no solutions left 意味着，选择还很多，我们仍然还需要继续进行搜索！

虽然提高了效率，可以提前发现failure，但是仍然有改进的地方。

> 总体来说，相比较前向检测而言，弧相容是一种**更全面的域剪枝技术**，能**减少导致的回溯**，但是为了强制执行，**需要运行更多的计算**。综上，在决定使用哪一个过滤技术来解决的的CSP问题时，有必要权衡一下。

**Arc consistency still runs inside a backtracking search**

## Ordering

从前面看到，在解决csp问题的时候，涉及到对变量和值进行排序。那我我们应该如何排序才能更好地提高效率呢？

which variable/value should be tried first?

### Minimum Remaining Values(MRV)

**Heuristic**: Choose the **variable** with the ==**fewest legal left values**== in its domain 选择**剩余值最少的变量**赋值(即限制最多的变量)。**受到约束最多的变量也最容易耗尽所有可能的值**，并且没有赋值的话最终会回溯，这样可以尽快提高算法效率

- Most constrained variable heuristic
- **Fail-first** heuristic

### Least Constraining Value(LCV)

**Heuristic:** Choose the **value** that ==**rules out the fewest choices**== for the neighboring variables in the constraint graph 选择从unassigned value 中淘汰掉值域最少的那一个(即限制最少的变量the one that rules out the fewest values in the remaining variables)。虽然这个方法可能会要求更多的计算，但是取决于用途，仍然能获得更快的速度。

- fail-last heuristic

### Degree 

**Heuristic**：选择拥有拥有限制最多的变量

#### Filtering+ordering？

Forward checking+MRV：我们可以通过Forward checking来减少值域，MRV来进行值域的选择，然而这样的方式仍然有缺点，当接下来的选择相等的时候，我们应该进行怎么样的选择呢？

**Maintaing Arc Consistency(MAC)**

- X~i~ 赋值
- inference: AC-3
- Only the arcs (Xj, Xi) for all Xj that are unassigned variables that are **neighbors** of Xi are stored in the queue（也就是，每次只存入赋值的边）
- more strictly than forward checking

## Structure

> 结论：树形结构最高效！时间复杂度从O(d^n^)变成O(nd^2^)

CSP问题，极端情况是，所有都是independent subproblems

一般的CSP问题，最差的时间复杂度是O(d^n^)

如果一个有n个节点的graph能够分解为有c个节点的子问题，worst case-O((n/c)d^c^)

### Tree structured CSP

没有循环的constraint graph

general approach

- 首先，在CSP的约束图中**任选一个节点**来作为树的根节点（具体选哪一个并不重要，因为基础图论告诉我们一棵树的任一节点都可以作为根节点）。
- 将树中的**所有无向边转换为指向根节点反方向的有向边**。然后将得到的有向无环图**线性化（linearize）**（或**拓扑排序（topologically sort）**）。简单来说，这也就意味着将图的节点排序，让所有边都指向右侧。注意我们选择节点A作为根节点并让所有边都指向A的反方向，这一过程的结果是如下的CSP转换：
- <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220224011252365.png" alt="image-20220224011252365" style="zoom:50%;" />

**Tree-CSP algorithm**

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220224011349698.png" alt="image-20220224011349698" style="zoom:50%;" />

图解过程如下：

简单来说，首先进行排序，然后从后往前，从前往后保证一致性

![image-20220224011617285](/Users/chengeping/Library/Application Support/typora-user-images/image-20220224011617285.png)

### Improving structure

Idea: 转换成树形结构

**Remove** nodes: **cutset conditioning**

**Collapse** nodes together: Tree decomposition

#### Cutset conditioning

以上我们 可以看出，树形结构可以极大地提高效率，那么我们可以考虑将这个算法扩展到树形结构相近的csp中。

通过cutset conditioning来进行树形结构的转换

**approcah**

- 选择变量中的一个子集S
- 移走这个子集后，constraints graph会变成一棵树（cycle cutset）
- 一般是最小子集

找到S后

- 将所有变量分配进去，并删除附近所有相连节点的对应的inconsistent的域
- 最后返回solution（记得加上S）(也就是下面的部分)

- O(d^c^(n-c)d^2^)

#### Tree Decomposition

![image-20220224012723395](/Users/chengeping/Library/Application Support/typora-user-images/image-20220224012723395.png)

## Local Search

Backtracking 并不是唯一解决CSP问题的算法。另一个广泛应用的方法是local search。

- find a good state without worrying about the path to get there
- 通过iterative来提升算法
- 一般**much faster** and more **memory efficient**(**but** incomplete and suboptimal) 有可能找不到solution

### Min-conflicts heuristic

- **Start with some random assignment to values**从一些随机变量的赋值开始。
- Iteratively select a random **conflicted variable** and **reassign** its value to the one that **violates the fewest constraints** until no more constraint violations exist反复选择违反约束最多的变量并将其充值位违反约束最少的值

有个问题就是，附近的搜索成本高

### Hill climbing search

Most basic local search technique

general idea:

- start wherever 任意位置开始
- Repeat: move to the best neighboring state 选择相邻最好的值
- if no neighbors better than current , but 如果没有了，停止

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220224003958324.png" alt="image-20220224003958324" style="zoom:50%;" />

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220224004058658.png" alt="image-20220224004058658" style="zoom:50%;" />

- Global maximum: The highest peak. 全局最优
- Local maximum: Higher than its neighboring state but lower than the global maximum 当前状况下，附近最有
- Plateaus: A **flat area** of the state- space landscape.
  - a flat local maximum: No uphill exit exists
  - shoulder: It is a plateau that has an uphill edge
- Current state : The region of state space diagram where we are currently present during the search

很明显，这个算法会产生一个问题，就是**只会上山**，或者停留在平台上！很有可能最后找到的是local maximum而不是global maximum。于是我们有：

### Simulated annealing

**Idea**:**escape local maximum**b y allowing downhil move 其实就是允许下山找global maximum

> - If temperature T decreased slowly enough, then a property of the Boltzmann distribution,  								, is concentrated on the global maxima

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220224004922480.png" alt="image-20220224004922480" style="zoom:50%;" />

这个算法其实就是找是否有比当前高的。只要有global maximum，那么这个值一定会大于0，最终移动到global maximum的位置。

缺点：The more downhill steps you need to escape a local optimum, the less likely you are to ever make them all in a row简而言之，步骤太多了！

### Mini-conflicts local search CSP

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220224005826311.png" alt="image-20220224005826311" style="zoom:50%;" />

