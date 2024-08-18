# Assignment 2   Report

## Question1 Report

### Intro 

**Input**: Votes of US congressmen/congresswomen

**Output**: predict whether they are a Republican or a Democrat

**Method**: Decision tree and Naive Bayes Classifier

**Data Pre-processing Method**:  3 ways to clean the data. 

​														**a)** discard the "missing" value

​														**b)** treat the "missing" value as a new value(three values) 

​														**c)** replace the "missing" value with the most common value for the feature.

**Train and Evaluation**: 5-fold cross validation and report precision,recall and F1-scores to evaluate models(6 scenarios of the 3 ways of cleaning data).

### Description of dataset

The data coumes from UCI machine learning repository.

- Datasets
  - "house-votes-84.names": describes what the different columns mean.
  - "house-votes-84.data": where data stores. 

- Data:
  - **Size**: 435
  - **Attributes**: 17 attributes including Class Name:  (democrat, republican), handicapped-infants: 2 (y,n), water-project-cost-sharing: 2 (y,n), adoption-of-the-budget-resolution: 2 (y,n), physician-fee-freeze: 2 (y,n), el-salvador-aid: 2 (y,n), religious-groups-in-schools: 2 (y,n), anti-satellite-test-ban: 2 (y,n), aid-to-nicaraguan-contras: 2 (y,n), mx-missile: 2 (y,n), immigration: 2 (y,n), synfuels-corporation-cutback: 2 (y,n), education-spending: 2 (y,n), superfund-right-to-sue: 2 (y,n), crime: 2 (y,n), duty-free-exports: 2 (y,n), export-administration-act-south-africa: 2 (y,n)
  - **Class name**: The first attribute. republican, democrat
  - **Feature**: the next 16 attributes.
  - **Values**: y, n, ?(missing value)

### Method

My method to solve this problems including:

1. Import the packages that are needed
2. Load data
3. Data preprocessing
4. Using the Models
5. Train and evaluate the models

The models using in this assignment are Decision Tree and Naive Bayes. 

Decision Tree : model available in the package sklearn.tree. Used the entropy as the criterion. The infomation gain is the key to choose the splitting attribute.

Naive Bayes: model available in the package sklearn.naive_bayes. And I chose the BernoulibNB as my Naive Bayes classifier. 

K-Fold: Use 5-fold cross validation to train and evaluate the data. 5-fold cross validation makes the model much more precise, and can improve the performance of the model.

### Processing

#### 1. Import Packages

```python
import pandas as pd  # DataFrame
import numpy as np # manipulate the data

import sklearn.tree # Decision tree
from sklearn.naive_bayes import BernoulliNB # Naive Bayes Classifer
from sklearn import model_selection # K-Fold cross validation
```

#### 2. Load Data

Using `read_csv` method in pandas to read the data. 

```python
votes_data=pd.read_csv("house-votes-84.data",header=None)
data_columns="class,handicapped-infants,water-project-cost-sharing,adoption-of-the-budget-resolution,physician-fee-freeze,el-salvador-aid,religious-groups-in-schools,anti-satellite-test-ban,aid-to-nicaraguan-contras,mx-missile,immigration,synfuels-corporation-cutback,education-spending,superfund-right-to-sue,crime,duty-free-exports,export-administration-act-south-africa"
votes_data.columns=data_columns.split(",")
```

#### 3. Data Pre-processing

##### a. Discard missing values

Transfer the value "y" into number 1; value "n" into number 0.

For the missing data, transfer all the "?"  into `NaN` and used the `dropna`method to drop all the rows that have missing value.

```python
discard_missing_row=votes_data.copy()

discard_missing_row[discard_missing_row=="y"]=1
discard_missing_row[discard_missing_row=="n"]=0
discard_missing_row[discard_missing_row=="?"]=np.nan

discard_missing_row.dropna(inplace=True)
```

##### b.  Treat "missing" value as a value

Transfer the missing value into number 2 as a new value. 

```python
three_value=votes_data.copy()

three_value[three_value=="y"]=1
three_value[three_value=="n"]=0
three_value[three_value=="?"]=2
```

##### c. replace "missing" value with the most common value

First, for each feature, we need to find the most common value of the column. So I use the `value_counts()` method of  to find the times each value occurs. Then, use `idxmax()`method to find the most common value.

```python
change=replace_missing[x].value_counts()
change=change.idxmax()
```

Second, for each feature, after comparing the length of value "y" and "n", replace the missing value with the most common value using `loc[]`.

```python
for i in range(1,17):
    ...
    replace_missing.loc[replace_missing[x]=='?',x]=change
    ...
```

Last, transfer all data into numbers

```python
replace_missing[replace_missing=='y']=1
replace_missing[replace_missing=='n']=0
```

#### 4. Using the model

initialize the two classifier.

```python
infoGain_clf = sklearn.tree.DecisionTreeClassifier(criterion='entropy')
bayes_clf = BernoulliNB()
```

#### 5. Train and Evalute using KFold

Using the K-Fold cross validation to improve the performance of the model. Split into 5 folds. And calculate the precision, recall, and f1-score of each fold. Calculate the mean of the scores.

```python
def format_ouput(clf, x, y):
    kf = model_selection.KFold(n_splits=5, shuffle=True)
    scores = model_selection.cross_validate(clf, x, y, cv=kf, 
                                        scoring=["precision_weighted", "recall_weighted", "f1_weighted"])
    print("precision: %f" % np.mean(scores["test_precision_weighted"]))
    print("recall: %f" % np.mean(scores["test_recall_weighted"]))
    print("F1-Score: %f" % np.mean(scores["test_f1_weighted"]))
```

### Results

<img src="notePic/assignment 2.png" alt="image-20211006214832644" style="zoom: 33%;" />

### Conclusion

We can see from the results that, the score for the Decision Tree is higher than the naive bayes classifier. We can conclude that, the decision tree works better in this  problem. 







## Question 2 Which one to choose

### Decision Tree

I think decision treee is really easy to use, and really easy to understand. 

First of all, as we can see from the tutorial, the tree is really readable. So if I want to classify something like this assignment or classify medical patients by disease etc, I would choose to use the decision tree to make the process much more understandble, besides, the tree can even tell us which attribute is has the max information gain, or in other words, the key to classify. 

However, if the dataset has too much attributes, I would not consider using the decision tree, so maybe considering of Bayes or other algorithms. The decision tree is made up of the attributes, which means if there are too much attributes, the tree may be so deep/large that make it too complicated, and may cause over fitting. Plus, if we want to avoid the over fitting problem, prunning may occur. But when this happen, some attributes may be lost during the prunning process, and may decrease the precision of the decision tree. 

### Naive Bayes

Naive Bayes can also used in classification, but not that readable as the decison tree does.

1. If there no much training data, I will consider using the Naive Bayes algorithm. Since Bayes algorithm is based on the degree of belief, not on the frequentist, so I think a small amount of data can well construct the model. 

2. If the dataset has many continuous - valued feature, I will consider Naive Bayes not desicion tree. Decision tree, is so simple that it may lose some important attributes of the continuous- valued features. However, we can choose the Gaussian Naive Bayes classifer, since each feature is distributed Gaussian, with a mean and variance specific to (feature, class) combination. 

3. When we want to do the text classification, spam filtering, recommender systems, Naive Bayes algorithm is the prime choice. all these tasks need so many classes.



Answers:

> - Decision trees create models which are very interpretable in form of If-then rules.
>
> - Decision trees are prone to overfitting. So a large dataset is needed to get a goodmodel.
>   - Naive bayes works well with smaller datasets.
>
> -  Naive bayes assumes that features are independant , which may not be true. 
>   - Decision trees handle feature correlation better.
>
> - Decision trees computation may be expensive if the number of features are large.
> - With class imbalance, decision trees perform better.
> - Naive Bayes Classifiers are immune to features not having relevance. It takes a global approach rather than the local approach taken by DT in the greedy/heuristic decisions at each node.

