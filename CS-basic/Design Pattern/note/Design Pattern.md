# Design Pattern

Definition: a solution to a commonly occurring problem in a context…对常见的问题的解决模式



用处：当你需要重复解决类似的问题时，可以使用

### 特点

- 一般：使用前需要修改
- 不是用的越多越好！

### Elements of patterns

- pattern name：描述这个模式
- problem：描述了模式的使用环境，用来解决什么问题的
- solution：描述element之间如何交互成这样的设计的，包括其关系，职责。
- consequences：使用后的变化



### 设计目标

- Low Coupling& high cohesion: 方法间无甚关系，方法内部高内聚
- Information hiding & encapsulation：将信息封装在一个object中
- abstraction
- low redundancy：减少重复
- modularity：方法不要太长
- simplicity：算法不要太复杂

### 设计原则

- Encapsulate what varies：寻找共同点和不同点
- Program to interfaces, not implementations：尽量使用接口！灵活
- Favor composition over inheritance：尽量使用多组合，能将多接口组合成一个大功能，尽量少使用继承

