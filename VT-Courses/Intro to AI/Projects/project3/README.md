# Project 3

`analysis.py, qlearningAgents.py, valueIterationAgents.py`

## Question 1 Value Iteration

![image-20220330132243351](/Users/chengeping/Library/Application Support/typora-user-images/image-20220330132243351.png)

- Compute **Q-value** based on the (state, action) and the nextState value from the getValue function

  ```python
  def computeQValueFromValues(self, state, action):
      """
        Compute the Q-value of action in state from the
        value function stored in self.values.
      """
      qValue = 0
      for nextState, probability in self.mdp.getTransitionStatesAndProbs(state, action):
          reward = self.mdp.getReward(state, action, nextState)
          qValue += probability * (reward + self.discount * self.getValue(nextState))
      return qValue
  ```

- Compute **Action** from values(maxQvalue)

  ```python
  def computeActionFromValues(self, state):
      nextAction = util.Counter();
      for action in self.mdp.getPossibleActions(state):
          nextAction[action] = self.computeQValueFromValues(state, action)
      return nextAction.argMax()
  ```

- Run Value iteration

  - Helper function: compute the maxQvalue

    ```python
    def getMaxQvalue(self, state):
        qValue = []
        for action in self.mdp.getPossibleActions(state):
            qValue.append(self.computeQValueFromValues(state, action))
        return max(qValue)
    ```

  - Run: start iteration, compute value and update the grid

    ```python
    def runValueIteration(self):
        # Write value iteration code here
        for i in range(self.iterations):
            newValue = self.values.copy()
            for currentState in self.mdp.getStates():  # for every state from beginning
                if self.mdp.isTerminal(currentState):
                    continue
                newValue[currentState] = self.getMaxQvalue(currentState)
            self.values = newValue
    ```

## Question 2 Bridge Crossing Analysis

only one way, can not fall, therefore, answerNoise should be 0

```python
def question2():
    answerDiscount = 0.9
    answerNoise = 0
    return answerDiscount, answerNoise
```

## Question 3 Policies

1. Prefer the close exit (+1), risking the cliff (-10)

   ```python
       answerDiscount = 0.2
       answerNoise = 0         # take a risk, only one way 0
       answerLivingReward = 0
   ```

2. Prefer the close exit (+1), but avoiding the cliff (-10)

   ```python
   answerDiscount = 0.2
   answerNoise = 0.1       # more ways to go, can not be 0
   answerLivingReward = 0
   ```

3. Prefer the distant exit (+10), risking the cliff (-10)

   ```python
   answerDiscount = 0.5    # nextState is more important
   answerNoise = 0         # only one way
   answerLivingReward = 2  # need to live longer
   ```

4. Prefer the distant exit (+10), avoiding the cliff (-10)

   ```python
   answerDiscount = 0.5
   answerNoise = 0.1       # more ways to go, can not be 0
   answerLivingReward = 2  # live longer
   ```

5. Avoid both exits and the cliff (so an episode should never terminate)

   ```python
   answerDiscount = 0
   answerNoise = 0
   answerLivingReward = 100  # Living!
   ```

## Question 4 Asynchronous Value Iteration

Everytime, only update one state. 

```python
def runValueIteration(self):
    states = self.mdp.getStates()
    numState = len(states)
    for i in range(self.iterations):
        # one state each time
        currentState = states[i % numState]
        if self.mdp.isTerminal(currentState):
            continue
        self.values[currentState] = self.getMaxQvalue(currentState)
```

## Question 5 

compute predecessor of one state:

```python
# compute predecessor
predecessors = util.Counter()
for state in self.mdp.getStates():
    if self.mdp.isTerminal(state):
        continue
    for action in self.mdp.getPossibleActions(state):
        for nextState, probability in self.mdp.getTransitionStatesAndProbs(state, action):
            if probability == 0:
                break
            if nextState not in predecessors:
                predecessors[nextState] = set()
            predecessors[nextState].add(state)
```

push value in priorityQueue

```python
pq=util.PriorityQueue()
# push value in pq
for state in self.mdp.getStates():
    if self.mdp.isTerminal(state):
        continue
    maxQ = self.getMaxQvalue(state)
    diff = abs(self.getValue(state)-maxQ)
    pq.update(state,-diff)
```

Update 

```python
for i in range(self.iterations):
    if pq.isEmpty():
        break
    state = pq.pop()

    if self.mdp.isTerminal(state):
        continue
    self.values[state] = self.getMaxQvalue(state)
    for predecessor in predecessors[state]:
        maxQ = self.getMaxQvalue(predecessor)
        diff = abs(self.getValue(predecessor) - maxQ)
        if diff > self.theta:
            pq.update(predecessor, -diff)
```

## Question 6 Q-learing

initialize qvalues in __init__

```python
def __init__(self, **args):
    "You can initialize Q-values here..."
    ReinforcementAgent.__init__(self, **args)
    self.qValues = util.Counter()
```

- getQvalue(): Returns Q(state,action) stored in the qValues dictionary

  ```python
  def getQValue(self, state, action):
      return self.qValues[(state, action)]
  ```

- computeValueFromQValues: if there is no leagal actions, the value will be 0.0; else will return the max qValue

  ```python
  def computeValueFromQValues(self, state):
      qValue = []
      for action in self.getLegalActions(state):
          qValue.append(self.getQValue(state, action))
      if len(qValue) == 0:
          return 0.0
      else:
          return max(qValue)
  ```

- computeActionFromQValues: same as the above function, if no legalActions, return None; else return the action

  ```python
  def computeActionFromQValues(self, state):
      nextAction = util.Counter();
      for action in self.getLegalActions(state):
          nextAction[action] = self.getQValue(state, action)
      if len(nextAction) == 0:
          return None
      else:
          return nextAction.argMax()
  ```

- Update: update qValues of  (state, action)

  ```python
  def update(self, state, action, nextState, reward):
      # Q(state, action) = (1-alpha)Q(curState,curAction) + alpha * sample
      sample = reward + self.discount * self.getValue(nextState)
      estimate = (1 - self.alpha) * self.getQValue(state,action) + self.alpha * sample
      self.qValues[(state, action)] = estimate
  ```

## Question 7 Epsilon Greedy

Epsilon is the probability of exploration. flip the coin, if it is smaller than epsilon, then choose random Action using `random.choice()` else, using exploitation, choosing the best action.

```python
def getAction(self, state):
    # Pick Action
    legalActions = self.getLegalActions(state)
    randomAction = random.choice(legalActions)
    bestAction = self.computeActionFromQValues(state)
    action = None
    # if < p random choose, else policy
    if util.flipCoin(self.epsilon):
        return randomAction
    else:
        return bestAction

    return action
```

## Question 8 Bridge Crossing Revisited

the answer should be 'NOT POSSIBLE'.

When epsilon = 1, the result is:

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220330154123916.png" alt="image-20220330154123916" style="zoom: 33%;" />

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220330154204762.png" alt="image-20220330154204762" style="zoom:33%;" />

when epsilon = 0, the result is:

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220330154304265.png" alt="image-20220330154304265" style="zoom:33%;" />

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220330154320131.png" alt="image-20220330154320131" style="zoom:33%;" />

since epsilon is between 0 and 1, therefore, is impossible to find the optimal only for 50 episodes.

```python
answerEpsilon = None
answerLearningRate = None
return 'NOT POSSIBLE'
```

## Question 9 Q-learing and Pacman

![image-20220330155521645](/Users/chengeping/Library/Application Support/typora-user-images/image-20220330155521645.png)

## Question 10 Approximate Q-Learning

getQvalue(): should return weights * feature

```python
def getQValue(self, state, action):
    weights = self.getWeights()
    featureVector = self.featExtractor.getFeatures(state,action)
    return weights * featureVector
```

Update 

```python
def update(self, state, action, nextState, reward):
    """
       Should update your weights based on transition
    """
    sample = reward + self.discount * self.getValue(nextState)
    diff = sample - self.getQValue(state, action)
    featureVector = self.featExtractor.getFeatures(state,action)

    # update weights weight = weight + alpha * difference * feature
    for feature in featureVector:
        self.weights[feature] += self.alpha * diff * featureVector[feature]
```