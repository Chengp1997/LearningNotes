# Project 5

`models.py`

## Question 1 DotProduct

Run: using the DotProduct to calculate 

```python
def run(self, x):
		return nn.DotProduct(self.get_weights(), x)
```

getPreidiction: if the prediction is bigger than 0, return 1; otherwise return -1;

```python
def get_prediction(self, x):
    prediction = nn.as_scalar(self.run(x))
    if prediction >= 0.0:
        return 1
    else:
        return -1
```

train: train for every dataset, since we want it to be 100% accuracy, if the answer is not right, the loop will continue.

```python
def train(self, dataset):
    while True:
        success = True
        for x, y in dataset.iterate_once(1):
            result = self.get_prediction(x)
            if nn.as_scalar(y) != result:
                success = False
                self.w.update(x, nn.as_scalar(y))
        if success:
            break
```

## Quesiton 2 LinearRegression

init

we have only 1 input and 1 output. And for the hidden layer, I randomly choose 50 and it works fine.

```python
def __init__(self):
    self.w1 = nn.Parameter(1, 50)
    self.b1 = nn.Parameter(1, 50)
    self.w2 = nn.Parameter(50, 1)
    self.b2 = nn.Parameter(1, 1)
    self.batchSize = 1
    self.parameters = [self.w1, self.b1, self.w2, self.b2]
    self.learningRate = -0.01
```

run

```python
def run(self, x):
    xw1 = nn.Linear(x, self.w1)
    predicted1 = nn.AddBias(xw1, self.b1)
    relu1 = nn.ReLU(predicted1)
    xw2 = nn.Linear(relu1, self.w2)
    predicted2 = nn.AddBias(xw2, self.b2)
    return predicted2
```

getloss: use the SquareLoss to compute the loss

```python
def get_loss(self, x, y):
    predicted_y = self.run(x)
    return nn.SquareLoss(predicted_y, y)
```

train

```python
def train(self, dataset):
    while True:
        for x, y in dataset.iterate_once(self.batchSize):
            loss = self.get_loss(x, y)
            grad = nn.gradients(loss, self.parameters)
            self.w1.update(grad[0], self.learningRate)
            self.b1.update(grad[1], self.learningRate)
            self.w2.update(grad[2], self.learningRate)
            self.b2.update(grad[3], self.learningRate)
        if nn.as_scalar(self.get_loss(nn.Constant(dataset.x), nn.Constant(dataset.y))) < 0.02:
            return
```

## Question 3 Digit Classification

Init

the input is 28x28 = 784, output is 10. For the hidden layer, I tried 50 initially which is not that accuarate at first. So I change it to 100, this works fine

```python
def __init__(self):
    self.w1 = nn.Parameter(784, 100)
    self.b1 = nn.Parameter(1, 100)
    self.w2 = nn.Parameter(100, 10)
    self.b2 = nn.Parameter(1, 10)
    self.batchSize = 1
    self.parameters = [self.w1, self.b1, self.w2, self.b2]
    self.learningRate = -0.01
```

run is the same as the linear regression since they all use 2 layers

getLoss: use the softmax Loss

```python
def get_loss(self, x, y):
    predicted_y = self.run(x)
    return nn.SoftmaxLoss(predicted_y, y)
```

train

```python
def train(self, dataset):
    while True:
        for x, y in dataset.iterate_once(self.batchSize):
            loss = self.get_loss(x, y)
            grad = nn.gradients(loss, self.parameters)
            self.w1.update(grad[0], self.learningRate)
            self.b1.update(grad[1], self.learningRate)
            self.w2.update(grad[2], self.learningRate)
            self.b2.update(grad[3], self.learningRate)
        if dataset.get_validation_accuracy() >= 0.97:
            return
```



