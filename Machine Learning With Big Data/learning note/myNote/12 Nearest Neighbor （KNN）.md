# Nearest Neighbor （KNN）

also called -- **instance based learning**

also referred to as a **non-parametric method.**(无参数)

## 概念

k近邻算法是一种**基本分类和回归方法**。

即是给定一个训练数据集，对新的输入实例，在训练数据集中找到与该实例**最邻近**的K个实例，**这K个实例的多数属于某个类**，就把该输入实例分类到这个类中。（**这就类似于现实生活中少数服从多数的思想**）

## Example

<img src="../note picture/KNN.png" alt="image-20211013140338648" style="zoom:50%;" />

如图为给定的数据集，白色方块为新的输入实例。目标：判断白色方块是什么类别的。

- K=1时，找离白色方块最近的1格方块，于是有如下：是红色

  <img src="../note picture/k=1.png" alt="image-20211013140446078" style="zoom:25%;" />

- k=4时，找离白色方块最近的4歌方块，于是有如下：是蓝色

  <img src="../note picture/KNN4.png" alt="image-20211013140519747" style="zoom:25%;" />



## 过程

一般来说，过程如下

1. 计算待分类点与已知类别的点之间的距离
2. 按照距离递增次序排序
3. 选取与待分类点距离最小的K个点
4. 确定前K个点所在类别的**出现次数**
5. 返回前K个点**出现次数最高的类别**作为待分类点的预测分类

## 要点：K的选取

KNN是通过 **多数表决**的方式来进行预测的。

K不能过大也不能过小。

**K太小**可能会忽略周边信息而导致**过拟合**，如果周边信息刚好是噪声，会导致出错严重

**K过大**模型过于简单（相当于没有怎么训练模型），容易发生**欠拟合**，因为不管输入实例是什么，都选的是多的一类。

选K的方法：**使用cross-validation**来选最优的k值

- 将数据分为training set（80%）和validation set（10%）和test set（10%）
- 训练数据（training set），预测validation set
- 选择使分类准确度最高的K

## 优缺点

### 优点

- Can learn very complex target functions with arbitrary boundaries
- Fast training time 没有明显的训练过程，而是在程序开始运行时，把数据集加载到内存后，不需要进行训练，直接进行预测，所以训练时间复杂度为0。
- 由于KNN方法主要靠周围有限的邻近的样本，而不是靠判别类域的方法来确定所属的类别，因此对于类域的交叉或重叠较多的待分类样本集来说，KNN方法较其他方法更为适合。
- 算法简单，理论成熟，既可以用来做分类也可以用来做回归。
- 该算法比较适用于样本容量比较大的类域的自动分类，而那些样本容量比较小的类域采用这种算法比较容易产生误分类情况。

### 缺点

- Can get easily bogged down by noise (e.g., irrelevant attributes) 对稀有类别的预测准确度低。
- Slow at classification time 是lazy learning方法，基本上不学习，导致预测时速度比起逻辑回归之类的算法慢
- 需要算每个测试点与训练集的距离，当训练集较大时，计算量相当大，时间复杂度高，特别是特征数量比较大的时候
- 需要大量的内存，空间复杂度高。

## 加速KNN算法（kd-tree）

原因

实现该算法的时候，主要考虑的问题是如何对训练数据**进行快速k近邻的搜索**。尤其是，数据量过大，纬度过大的时候。

快速k近邻搜索最简单的实现方法就是线性扫描：**每次**计算输入实例和每一个实例的距离。当**数据量过大的时候，慢**！！！

因此，可以考虑使用 **特殊的结构存储训练数据**，减少计算次数。

以下， 可以使用kd-tree的方法。

### KD-Tree

K：维度

原理： https://zhuanlan.zhihu.com/p/127022333  如何建立KD树和如何在kd树上进行临近查找

我们可以把一个多维的数据进行划分。

<img src="../note picture/kdTree1.png" alt="image-20211018171944821" style="zoom:33%;" />

算法首先找到自己这个字节点中距离最近的几个节点。

以最近的节点为中心，搜寻附近几个节点中距离近的节点。

<img src="../note picture/kdTree2.png" alt="image-20211018173348896" style="zoom:33%;" />

当这个节点搜寻完毕后，可以向父节点向上找寻，找到更近的节点

<img src="../note picture/kdTree3.png" alt="image-20211018173513555" style="zoom:33%;" />

此时，搜寻后，可以发现，有的节点是不需要存在的，我们可以进行剪枝，以达更快的搜索。

<img src="../note picture/kdTree4.png" alt="image-20211018173617630" style="zoom:33%;" />

### 优缺点：

当维数较大时，直接利用k-d树**快速检索的性能急剧下降**。假设数据集的维数为D，一般来说要求数据的规模N满足条件：**N远大于2的D次方**，才能达到高效的搜索。

## 优化KNN

- **feature selecting**： 当计算结果只有2个attribute和target相关的时候，这时候，KNN很容易被高维的数据误导。我们可以对feature进行一个选择，删除一些不必要的feature。
- **choosing neighbors within some distance**：不选择top k，选择有一定距离的
- **weighted k** nearest neighbors: 加权重。

## 使用

KNN分类

```python
from sklearn import neighbors

knearest_clf = neighbors.KNeighborsClassifier(n_neighbors=5, weights='uniform') # Keep varying k by changing n_neighbors
knearest_clf.fit(X_matrix, Y)

print("predicted:", knearest_clf.predict(X_matrix[-2:,:]))
print("truth", Y[-2:])

```

KNN回归

```python
knn_regression = neighbors.KNeighborsRegressor(n_neighbors=4, weights='uniform')
knn_regression.fit(diabetes_X_train, diabetes_y_train)

print("Mean Squared Error: %.2f"
      % np.mean((knn_regression.predict(diabetes_X_test) - diabetes_y_test) ** 2))
print('R^2: %.2f' % knn_regression.score(diabetes_X_test, diabetes_y_test))
```

