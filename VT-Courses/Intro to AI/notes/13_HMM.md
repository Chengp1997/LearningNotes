# HMM

# Markov Models

![image-20220505053658787](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505053658787.png)

### Terms

- Xt: t 时间的state

- Transition model P(Xt|Xt-1): 改变的契机
- 状态
  - stationarity：transition probabilites不会改变，stays the same

### **Example of Markov chains**

初始分布：sun 1.0. 

注意状态转移方程。前一个状态到后一个状态的转变概率。

![image-20220505054100981](data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1386 706"></svg>)

前进一个状态。后一个状态由前一个状态决定。因为不知sun的情况，所以应累加。

最后得出P(X2=sun)=0.9 P(X2=rain)=0.1

![image-20220505140134029](data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1418 694"></svg>)

下一个状态也同理，根据当前的状态来进行计算。

![image-20220505160224831](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505160224831.png)

### Stationary distribution

随着markov chain 状态的转移，最开始initial distribution的影响会越来越小---distribution end up 趋近于一个值

![image-20220505160912067](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505160912067.png)

#### Calculation

如何计算stationary的状态？

![image-20220505161528665](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505161528665.png)

# HMM

隐马尔可夫链

在一般的马尔可夫链中，evidence是可以直接被观察到的。而对于隐马尔可夫链，状态并不是直接可见的，但是受状态影响的某些变量是可变的。

![image-20220505162821973](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505162821973.png)

如图。evidence不是直接可知的，但是在每个状态x上，都可以观察到当前的情况。可以理解为，每个状态都有对应的sensor来观察。

### Example

![image-20220505162949090](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505162949090.png)

### Probability Model

对于一般的马尔可夫链有：<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505164549760.png" alt="image-20220505164549760" style="zoom:50%;" />

对于HMM有：<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505164607680.png" alt="image-20220505164607680" style="zoom:50%;" />

注意，式子的区别。HMM的两个状态转移为

- **Markov hidden process**: future depends on past given the present
- **Current observation** independent of all else given current state

### Inference in Temporal Models

#### Filtering(P(Xt|e1:t))- state estimation

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505165232197.png" alt="image-20220505165232197" style="zoom:50%;" />

鉴于到当前状态的所有evidence，估算当前的belief state。

- Filtering is the task of **computing the belief state** P(Xt|e1:t)

  <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505170833593.png" alt="image-20220505170833593" style="zoom:50%;" />

##### Example

对于HMM，等于有两个模型，一个是X1-X2的转换模型；一个是X1-E1的observation 模型，于是对于两个模型有

![image-20220505214139258](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505214139258.png)

对于 Xt-Xt+1有

![image-20220505215420496](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505215420496.png)

对于X1-E1有：

![image-20220505215513375](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505215513375.png)

所以，根据前面两个式子结合有，要求下一个状态P(Xt+1|e1:t+1)如下

![image-20220505215742056](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505215742056.png)



#### Prediction (P(Xt+k|e1:t) for k > 0)

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505165242069.png" alt="image-20220505165242069" style="zoom:50%;" />

根据past的所有evidence，预测之后的概率。

#### Smoothing (P(Xk|e1:t) for 0 ≤ k < t)

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505165253793.png" alt="image-20220505165253793" style="zoom:50%;" />

能够很好用来苹果past states

#### Explanation (arg maxx1:t P(x1:t | e1:t))

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220505165302913.png" alt="image-20220505165302913" style="zoom:50%;" />

speech recognition/decoding with a noisy channel等。

寻找其中最大的概率。





## Example

通过上面的分析，可以看到，更新HMM主要分两步，predict，然后update，最后一步进行normalize即可

![image-20220505221129258](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505221129258.png)

![image-20220505221143202](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505221143202.png)

## Dynamic Bayes Nets

那么，针对HMM的再进行繁华，就是DBNs

可以处理更多的state了

## Particle filtering

HMM问题：成本太高，计算量太大。每次都需要inference！

可以趋近模拟。Simulatethemotionofasetofparticlesthrough a state graph to approximate the probability (belief) distribution of the random variable in question

于是又了particle filtering

### Definition

- Store a list of n **particles** instead of storing a full probability table for all states

- 存储particle

- 每个particle中可能会有不同的state

### Process

第一步先预测下一个状态。

第二部观察，weight sample，看比重

第三步 重新采样

![image-20220505230332154](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505230332154.png)

### Example

模拟belief的改变

原先的数据

![image-20220505232805286](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505232805286.png)

transition model

![image-20220505232817564](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505232817564.png)

**Step 1 time elapse update**

Random sample

![image-20220505232841960](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505232841960.png)

根据概率取样有，并且概率如下

![image-20220505232855844](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505232855844.png)

**Step 2 observation update**

sensor model![image-20220505233240855](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505233240855.png)

此时，if giving evidence 可以计算各个particle的权重，以及被选中概率。

![image-20220505233539535](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505233539535.png)

**Step 3 resample**

最后，重新取样

![image-20220505234142872](/Users/chengeping/Library/Application Support/typora-user-images/image-20220505234142872.png)

