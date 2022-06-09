# Project 0

Question Link: https://courses.cs.vt.edu/cs5804/Spring22/projects/project0.html#question-1-addition 

some basic problems of python in order to get familliar with the language

### Question1

Addition.py

```python
def add(a, b):
    "Return the sum of a and b"
    "*** YOUR CODE HERE ***"
    print("Passed a = %s and b = %s, returning a + b = %s" % (a, b, a + b))
    return a + b
    return 0
```

### Question2

buyLotsOfFruit.py

```python

fruitPrices = {'apples': 2.00, 'oranges': 1.50, 'pears': 1.75,
               'limes': 0.75, 'strawberries': 1.00}


def buyLotsOfFruit(orderList):
    """
        orderList: List of (fruit, numPounds) tuples

    Returns cost of order
    """
    totalCost = 0.0
    "*** YOUR CODE HERE ***"
    for fruit, numPounds in orderList:
        if fruit not in fruitPrices:
            print("Sorry we don't have %s" % (fruit))
        else:
            totalCost += fruitPrices[fruit] * numPounds
    return totalCost
```

### Question 3

shopSmart.py

```python
import shop


def shopSmart(orderList, fruitShops):
    """
        orderList: List of (fruit, numPound) tuples
        fruitShops: List of FruitShops
    """
    bestShop=fruitShops[0]
    minPrice=float('inf')
    for shops in fruitShops:
        totalCost=shops.getPriceOfOrder(orderList)
        if totalCost<minPrice:
            bestShop=shops
            minPrice=totalCost
    return bestShop
```

