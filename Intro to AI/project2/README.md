# Project 2 - Game

`multiAgent.py`

## Question 1 Reflex Agent

Use the reciprocal to calculate the new score of the game. 

Eating food is encouraged because it increases the score of the game while getting too close to the ghost is not encrouged because it will lead to the death  of the pacman. 

Therefore set the weight of pacman to food to 10, and set the weight of pacman to ghost to 1. For the score, increase the value for food and decrease the value for ghost. 

```python
    def evaluationFunction(self, currentGameState, action):
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"
        score = successorGameState.getScore()

        # use the reciprocal to calculate the new score
        # foodDistance
        foodList = newFood.asList()
        pacmanToFood = [manhattanDistance(foodPosition, newPos) for foodPosition in foodList]
        if len(pacmanToFood) > 0:
            score += 10 / min(pacmanToFood)

        # ghostDistance
        for ghostState in newGhostStates:
            pacmanToGhost = manhattanDistance(newPos, ghostState.getPosition())
            if pacmanToGhost > 0:
                score -= 1 / pacmanToGhost
            else:
                return 0
        return score
```

## Question 2 Minimax 

terminal state for the game : when game is win, lose, if it reach the depth of the tree or when the agent has no leagal actions to move

```python
    def isTerminal(self, gameState, depth, agentIndex):
        return depth == self.depth or gameState.isLose() or \
               gameState.isWin() or gameState.getLegalActions(agentIndex) == 0
```

Get the value of the game.

If  it reach the terminal state, then calculate the utility to get the score. 

If the agentIndex is 0, which means the agent is pacman currently, then get the maxValue iteratively.

If the agentIndex is not 0, then it's ghosts' turn, get the minValue of the game.

```python
    def getValue(self, gameState, depth, agentIndex):
        if self.isTerminal(gameState, depth, agentIndex):
            return self.evaluationFunction(gameState)
        elif agentIndex == 0:
            return self.getMaxValue(gameState, depth, 0)
        else:
            return self.getMinValue(gameState, depth, agentIndex)
```

Get max value: for all the legal state of current agent(which indeed is the pacman), get the maxValue of each action. if the depth is equal to 0, which means it is the last node, then return the bestAction.

```python
# pacman get max value
    def getMaxValue(self, gameState, depth, agentIndex):
        maxValue = float('-inf')
        bestAction = None
        for action in gameState.getLegalActions(agentIndex):  # only pacman
            # calculate ghost value ,find the max value
            nextState = gameState.generateSuccessor(agentIndex, action)
            tempMax = self.getValue(nextState, depth, agentIndex + 1)
            if tempMax > maxValue:
                maxValue = tempMax
                bestAction = action
        if depth > 0:  # if it is not the first node, then should go over to find the max Value
            return maxValue
        return bestAction
```

Get min value. For Every ghost, find the minValue of this level.

```python
    def getMinValue(self, gameState, depth, agentIndex):
        minValue = float('inf')
        for action in gameState.getLegalActions(agentIndex):
            nextState = gameState.generateSuccessor(agentIndex, action)
            if agentIndex == gameState.getNumAgents() - 1:  # last ghost, next one is pacman
                tempValue = self.getValue(nextState, depth + 1, 0)  # should go deeper
            else:  # calculate all the ghost
                tempValue = self.getValue(nextState, depth, agentIndex + 1)
            if tempValue < minValue:
                minValue = tempValue
        return minValue
```

## Question 3 Alpha-Beta Pruning

Most of the part are the same as the minimax. The only difference is the prunning condition of the two function

GetMaxValue. set the alpha as the max value. if the maxValue is bigger than beta, it can exit.

```python
# pacman get max value
    def getMaxValue(self, gameState, depth, agentIndex, alpha, beta):
        maxValue = float('-inf')
        bestAction = None
        for action in gameState.getLegalActions(agentIndex):  # only pacman
            # calculate ghost value ,find the max value
            nextState = gameState.generateSuccessor(agentIndex, action)
            tempMax = self.getValue(nextState, depth, agentIndex + 1, alpha, beta)
            if tempMax > maxValue:
                maxValue = tempMax
                bestAction = action
            if maxValue > beta:
                return maxValue
            alpha = max(alpha, maxValue)
        if depth > 0:  # if it is not the first node, then should go over to find the max Value
            return maxValue
        return bestAction
```

getMinValue. same as get max Value function. 

```python
    def getMinValue(self, gameState, depth, agentIndex, alpha, beta):
        minValue = float('inf')
        for action in gameState.getLegalActions(agentIndex):
            nextState = gameState.generateSuccessor(agentIndex, action)
            if agentIndex == gameState.getNumAgents() - 1:  # last ghost, next one is pacman
                tempValue = self.getValue(nextState, depth + 1, 0, alpha, beta)  # should go deeper
            else:  # calculate all the ghost
                tempValue = self.getValue(nextState, depth, agentIndex + 1, alpha, beta)
            minValue = min(minValue, tempValue)
            if minValue < alpha:
                return minValue
            beta = min(beta, minValue)
        return minValue
```

## Question 4 Expectimax

most are the same as minimax algorithm. The only difference is, the mini agent does not return the minimum of the value, but return the expect value of all ghost. 

```python
    def getExpectValue(self, gameState, depth, agentIndex):
        totalValue = 0.0  # use this to calculate the expect value
        for action in gameState.getLegalActions(agentIndex):
            nextState = gameState.generateSuccessor(agentIndex, action)
            if agentIndex == gameState.getNumAgents() - 1:  # last ghost, next one is pacman
                tempValue = self.getValue(nextState, depth + 1, 0)  # should go deeper
            else:  # calculate all the ghost
                tempValue = self.getValue(nextState, depth, agentIndex + 1)
            totalValue += tempValue
        return totalValue / len(gameState.getLegalActions(agentIndex))
```

## Question 5 Evaluation Function

Like the evaluation in relax agent, use the reciprocal to calculate the new score.

Eating food and eating the ghost while they are scared is encouraged,
and ghost hunting is more encouraged since it will get higher score.
Therefore I set the weight of eating food to 10, and eating ghost to 100.

Oppositely, getting too close to the ghost is not encouraged,
therefore for every ghost,the score would minus the feature of the distance to ghost
I tried for some number, and found that set the weight of the feature to 1 would get
a better score.

```python
def betterEvaluationFunction(currentGameState):
    # things I need to consider:
    # food left, pacman state(distance to food), ghost sate(distance to ghost), ghost getScared and eat ghost
    currentFood = currentGameState.getFood()
    pacmanPosition = currentGameState.getPacmanPosition()
    ghostState = currentGameState.getGhostStates()

    # evaluate
    score = currentGameState.getScore()
    # use the reciprocal to calculate the new score
    # pacman to food
    foodList = currentFood.asList()
    pacmanToFood = [manhattanDistance(foodPosition, pacmanPosition) for foodPosition in foodList]
    if len(pacmanToFood) > 0:
        score += 10 / min(pacmanToFood)
    else:
        score = score

    # pacman to ghost
    for ghost in ghostState:
        pacmanToGhost = manhattanDistance(pacmanPosition, ghost.getPosition())
        scaredTimes = ghost.scaredTimer  # consider the scared time
        if pacmanToGhost > 0:
            if scaredTimes > 0:  # if it is still scared, eating ghost is encouraged
                score += 100 / pacmanToGhost
            else:
                score -= 1 / pacmanToGhost
        else:
            return 0
    return score
```