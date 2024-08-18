[TOC]

# Ch.1

## 软件开发的本质

* 高达70%软件项目失败

![1540475285288](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540475285288.png)

* 复杂性、一致性、可变性、不可见性，没有silver bullets

### 利益相关者

关注项目和系统结果：

* 投资人
* 用户

关注产品：

* QA & 过程改进团队

### 软件工程过程模型

#### plan-driven

##### 瀑布模型

![1540475783464](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540475783464.png)

* 特点

阶段间具有顺序性和依赖性。其中包含两重含义：

① 必须等前一阶段的工作完成之后，才能开始后一阶段的工作；

② 前一阶段的输出文档就是后一阶段的输入文档。

推迟实现的观点：

① 瀑布模型在编码之前设置了系统分析和系统设计的各个阶段，分析与设计阶段的基本任务规定，在这两个阶段主要考虑目标系统的逻辑模型，不涉及软件的物理实现。

② 清楚地区分逻辑设计与物理设计，尽可能推迟程序的物理实现，是按照瀑布模型开发软件的一条重要的指导思想。 

质量保证的观点

① 每个阶段都必须完成规定的文档，没有交出合格的文档就是没有完成该阶段的任务。

② 每个阶段结束前都要对所完成的文档进行评审，以便尽早发现问题，改正错误。

* 优点

可强迫开发人员采用规范化的方法。

严格地规定了每个阶段必须提交的文档。

要求每个阶段交出的所有产品都必须是经过验证的。

* 缺点

由于瀑布模型几乎完全依赖于书面的规格说明，很可能导致最终开发出的软件产品不能真正满足用户的需要。

如果需求规格说明与用户需求之间有差异，就会发生这种情况。

瀑布模型只适用于项目开始时需求已确定的情况。

##### V 模型

![1540476022807](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540476022807.png)

##### 螺旋模型

![1540476042495](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540476042495.png)

特点：注重过程控制

#### 增量和迭代模型

![1540476097576](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540476097576.png)

##### 统一过程RUP

https://www.cnblogs.com/vettel/p/3503359.html

包含四个过程：初始、精化、构建和产品化

![1540476327211](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540476327211.png)

##### OPF: Open Process Framework

##### MDA模型驱动体系结构

![1540476674310](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540476674310.png)

##### 敏捷

原则：**embrace change, deliver early and deliver often**

###### Srum和XP

https://www.cnblogs.com/byvar/p/7235650.html

![img](https://images2015.cnblogs.com/blog/613907/201707/613907-20170725180048357-60510522.jpg)



###### 精益开发lean

哲学：**think big, act small, fail fast**

原则：

![1540476861194](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540476861194.png)

* 看板：https://www.cnblogs.com/byvar/p/7235650.html

#### Formal Model形式化模型

**数学形式**

包括：Cleanroom, VDM, B方法，Z语言

#### 过程改进模型

##### CMM能力成熟度模型

https://blog.csdn.net/wdeng2011/article/details/79843607

是一种measurement，用于进行过程评估和改进

![1540477017810](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540477017810.png)

##### QA，QC，QM的区别

QC是方法论

QA是保证质量所进行的手段，例如code review，testing（质量保证工作的对象是产品和开发过程中的行为）

QM是管理层的协调工作

##### ISO 9000标准

process standard
质量管理，适用于多个产业

##### ITIL框架

process standard
##### COBIT框架

product standard

#### 利用UML建模开发


![1542277088081](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1542277088081.png)

## 系统规划

方法：SWOT, VCM, BPR

共同点：关注效果而不是效率

### SWOT

Strengths, Weaknesses, Opportunities, Threats

Objectives长远目标指导goal具体目标

![1540478168748](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540478168748.png)



### VCM value chain model

组织职能分为主要primary和支持support，主要活动为产品增加或创造价值，例如市场营销等；支持活动不增加价值，不丰富产品，例如行政管理、研发等。



### BPR bussiness process reengineering

与传统垂直结构组织的区别在于有没有business process owner



## 三级管理系统

![1540486730347](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540486730347.png)



### 事物处理系统online transaction processing systems

### 分析处理系统online analytical processing systems

### 知识处理系统

## 第一章练习题

51页

# Ch.2 需求确定

## From business processes to solution envisioning

* IT解决方案与业务过程的关系

#### BPMN业务过程建模表示法

* 作用：对业务过程（Bussiness Process）进行建模

* 与UML是竞争关系
* 可以转换为BPEL

##### 过程层次建模

* 过程可能包含其他子过程，也可能是原子的（称为任务）
* 过程至少有一个输入流一个输出流

##### BPMN建模元素对象 

书P60，PPT P9

* 核心元素：流对象，包括三种，事件event、活动activity、路由gateway

* 连接对象

* 泳池

* 人工制品

#### 解决方案构想Solution Envisioning

* 业务价值驱动bussines value-driven方法，提供解决当前业务问题的IT方法
* 3E: 效果，效率，edge
* 解决方案构想过程的三个阶段：
  * 业务能力探索Business capability exploration 
  * 解决方案能力构想Solution capability envisioning 
  * 软件能力设计Software capability design，得到能力体系结构

* 实现策略和能力体系结构
  * 常规开发
  * 基于包的开发
  * 基于构件的开发

## 需求工程Requirements Engineering

#### RE products

*  requirements specification 
*  system and software acceptance test criteria

#### why is RE important

* 早发现错误，省钱
* ……

#### Functional and Non-Functional Requirements

* 定义和来源
* 必须是精确的需求



## Requirements Elicitation需求获取
### activities 步骤：
*  Analysing the Problem
* Identifying Requirements Sources
* Eliciting Requirements
### techniques 
* Interviews 
* Workshops 
* Focus Groups 
* Observations 
* Questionnaires
* Independent Elicitation Techniques
### Product of the Elicitation Phase
 Requirements documentation including a requirements definition 


 ## Requirements negotiation and validation（with all stakeholders）
 ### 原因
 * 需求可能是重复或冲突的
 * 需求可能不现实、不明确
 * 需求可能不完全
 * 需求可能超出要求
 * 利用需求矩阵

### 需求风险和优先性
#### 风险分类
* Technical 
* Performance
* Security 
* Database integrity 
* Development process 
* Political 
* Legal 
* Volatility
#### 优先
主要用于处理延迟情况

### Requirements Management
#### 需求识别和分类
* 自然语言陈述
* 识别和分类策略
	* 唯一标识符（一般是一个顺序号）
	* 文档层次内的顺序编号
	* 需求分类中的顺序编号
#### 需求层次
* 应有父子层次
* eg. 1. …… 1.1 …… 1.2 ……

### 变更管理
* 需求的变更：更改、删除、增加
* 开发阶段越靠后，变更需求的成本越高
* 变更应被记录（software configuration management tool）和评估

### 需求可跟踪性
* 变更管理的一部分

##  Requirements business model: 
* high-level, visual
* 用例图
* 类图



# Ch.3
## abstraction and modelling
### systems, model and views

* model is abstraction of systems
* views dipicts aspects of a model
* notations is set of rules to describle views and models

### UML model
= functional model + 
### other models 
* task model: PERT chart, organization chart, schedule
* issues model: Jura is a common tool

### decomposition
#### functional decomposition
* not good
* high coupling->hard to understand
#### object-oriented decomposition
system->classes->smaller classes

### deal with complexity
抽象，分解（OO分解 is good ），分等级

### UML models
* 结构模型：静态
* 行为模型：描述系统行为，用例图、时序图等
* 状态改变模型：动态视图，状态机图

## use case view

### 用例建模
* 对功能性需求进行建模
* 组成：参与者actor，用例use case，关系association

#### actor
* 和系统进行交互的人或系统： 使用系统完成一个任务，或者提供服务供系统使用（secondary actor）
* 注意与user进行区分
* actor在系统外部，**使用**系统。而不是系统的模型。 actors are people or other systems that would actually use the system, not things that would be modelled within the system 
* 系统不能使用自己
* 数据库不是参与者，是技术选项
* Primary and Secondary Actors

#### use case
* Describes what a system does but not how it does it 

* 从primary actor的角度进行命名

* 八步进行用例建模
  ![1540650781022](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1540650781022.png)



  * Task 1: Identify Actors

  * Task 2: Identify Initial Use Cases

  * Task 3: Draw Use Case Diagram 

    注意，Associations in use case diagrams connect actors to use cases, never actors to actors, or use cases to use cases. 用例图不是流程图。

  * Task 4: Identify Packages

     a group of use cases work towards a common goal

  * Task 5: Develop Initial Use Cases

    Identify Typical and Alternative Scenarios

  * Task 6: Refine the Typical and Alternative Scenarios 

    **focuses on details**

  * Task 7: Restructure Use Cases

    Use cases can be restructured in three ways 

    * **«include» relationship**

      Factor out common behaviour in use cases 

      虚线，被include（被调用）指向include（调用者）

    * **«extend» relationship** 

      Factors out optional behaviour in use cases 

      去掉扩展部分，用例还是完整的

      虚线，扩展部分指向原始用例

    * **generalisation **

      实现，三角空心箭头，指向父亲

      actor之间也有泛化关系

* User Stories，Event – Response Tables等是用例的代替品



## activity view

* Models  dynamic behavior
* Flow-chart like representation of a use case 
* activities
	* simple action
	* invoke activity
	* time event
* nodes
	* initial node
	* final node: all flows stop when reached, can be 0 or more than 1
	* flow final node
	* decision node
	* merge node
	* Fork Node 
	* Join Node 


### structure view
#### Class modeling
* Captures the static view of the system 
* Class modeling elements : classes, operations and their relationships

##### class
* persistent and transient
* attributes


## interaction view
### Interaction modeling
* Captures interactions between objects
* behavior of use cases
*  **Sequence diagram and Communication diagram **

### Sequence Diagram 
### communication diagram
体现类之间的耦合程度

## state machine view
### State machine diagram
