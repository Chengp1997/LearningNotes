# Machine Learning Overview

## Types

- Supervised Learning: labeled data-learns func from input to output
  - Regression
    - target：continuous
    - example: linear
  - Classification
    - Target: categorical
    - Example: naive bayes/ decision tree
- Unsupervised Learning: unlabeled data-learns func from input
  - No target variable
  - Example: k-means, association
- Semi-supervised Learning
- Reinforcement Learning: rewards and punishment(根据不同的奖惩来学习)



## Supervised Learning

### Definition

- Data: 带标签的数据。根据input，output来进行模型的训练。
- Goal：寻找趋近真实情况的模型M

### DataSet Types

一份数据，会分成几份用于不同的功能

- Training set：用于模型训练
- validation set
  - development data/**hold-out set**
  - 为了对模型进行评估。可能会切分成好几份，并训练测试几次，每一个validation set都是用来进行模型评估的。-- 也就是 cross-validation 方法
- test set：用于测试数据

### two problems

我们训练的目的是让模型在test data上变现良好，更趋近真实情况

#### Overfitting

和模型贴合很好，但是泛化性很差，不适用于其他数据集，只适合这一个。

**High variance**: Training error will be much lower than test error

//由于贴合得太好，没有什么错误产生。

![image-20220504010045317](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504010045317.png)

解决方案：使用更大的数据集/feature选择少一些

#### Underfitting

和模型贴合差。

**High bias**：Training error will also be high

//由于不贴合，训练误差仍然很大

![image-20220504010112593](/Users/chengeping/Library/Application Support/typora-user-images/image-20220504010112593.png)

解决方案：使用更多features

### Model selection/optimazation

选择validation小的模型

### experimentation Process

- learn parameters on training set
- **Tune hyperparameters on validation set** 注意这个，通过验证来进行调参的步骤。
- compute accuracy of test set

## Model Based

### Decision Tree

entropy informationgain来分树

用处：垃圾邮件分类。

### Naive Bayes

通过计算概率来分类。

Example

![image-20220505182319317](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505182319317.png)

### parameter Estimation

如何估算任意变量的概率-通过training data

采样计算，估算比例

![image-20220505182907646](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505182907646.png)

这是用来估算data的likelihood

那么如何寻找最大的likelihood？需要用到下面的MLE

#### maximum likelihood estimation（MLE）

所有样本概率分布相同；相互独立---独立同分布

**Goal：** 找到能容Probability of D尽可能大的theta

Process

![Process](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505183735998.png)

![image-20220505183203694](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505183203694.png)

![image-20220505183648214](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505183648214.png)

### Linear classifier



### Perceptron

看成线性回归，注意更新的时候要

![image-20220505184200803](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505184200803.png)

**Properties**

- separability
- convergence
- mistake bound 容错

**problems**

- noise 容易受影响
- mediocre generalization 有时候很不好分
- Overtraining overfitting

### Summary

![image-20220505184420210](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505184420210.png)