# Deep learning intro

## Gradient Ascent

- An optimization algorithm used to find the values of parameters of a function that minimizing loss

![image-20220505184958066](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505184958066.png)

![image-20220505185355700](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505185355700.png)

**Process**

- start somewhere
- Repeat: take a step, update

## Loss function

![image-20220505185423626](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505185423626.png)

## Summary

![image-20220505185552218](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505185552218.png)

Bach:  成本很高，一次就计算所有的误差。但是保证最佳。

SGD： 成本较低，可能不是最佳，但是速度快。

### Optimization

对于神经网络，调整参数的方式是：

Backprobagaition 

一次计算后，通过loss function计算误差

然后使用gradient descent 来计算梯度。

通过梯度的调整来调整weight。

![image-20220505194936900](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505194936900.png)

## Pros and cons

![image-20220505195124088](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505195124088.png)