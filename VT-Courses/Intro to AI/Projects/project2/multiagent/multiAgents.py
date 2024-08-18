# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
import random, util

from game import Agent


class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """

    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices)  # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
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


def scoreEvaluationFunction(currentGameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()


class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn='scoreEvaluationFunction', depth='2'):
        self.index = 0  # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

    def isTerminal(self, gameState, depth, agentIndex):
        return depth == self.depth or gameState.isLose() or \
               gameState.isWin() or gameState.getLegalActions(agentIndex) == 0


class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"
        return self.minMaxSearch(gameState)

    def minMaxSearch(self, gameState):
        return self.getValue(gameState, 0, 0)

    def getValue(self, gameState, depth, agentIndex):
        if self.isTerminal(gameState, depth, agentIndex):
            return self.evaluationFunction(gameState)
        elif agentIndex == 0:
            return self.getMaxValue(gameState, depth, 0)
        else:
            return self.getMinValue(gameState, depth, agentIndex)

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


class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        return self.alphaBetaPruning(gameState)

    def alphaBetaPruning(self, gameState):
        return self.getValue(gameState, 0, 0, float('-inf'), float('inf'))

    def getValue(self, gameState, depth, agentIndex, alpha, beta):
        if self.isTerminal(gameState, depth, agentIndex):
            return self.evaluationFunction(gameState)
        elif agentIndex == 0:
            return self.getMaxValue(gameState, depth, 0, alpha, beta)
        else:
            return self.getMinValue(gameState, depth, agentIndex, alpha, beta)

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


class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** YOUR CODE HERE ***"
        return self.expectimax(gameState)

    def expectimax(self, gameState):
        return self.getValue(gameState, 0, 0)

    def getValue(self, gameState, depth, agentIndex):
        if self.isTerminal(gameState, depth, agentIndex):
            return self.evaluationFunction(gameState)
        elif agentIndex == 0:
            return self.getMaxValue(gameState, depth, 0)
        else:
            return self.getExpectValue(gameState, depth, agentIndex)

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


def betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    Like the evaluation in relax agent, use the reciprocal to calculate the new score.

    Eating food and eating the ghost while they are scared is encouraged,
    and ghost hunting is more encouraged since it will get higher score.
    Therefore I set the weight of eating food to 10, and eating ghost to 100.

    Oppositely, getting too close to the ghost is not encouraged,
    therefore for every ghost,the score would minus the feature of the distance to ghost
    I tried for some number, and found that set the weight of the feature to 1 would get
    a better score.
    """
    "*** YOUR CODE HERE ***"
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


# Abbreviation
better = betterEvaluationFunction
