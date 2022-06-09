# Project1 - search

### General search for Q1,Q2,Q3,Q4 (DFS, BFS, UCS, A*)

问题描述：实现DFS，BFS， UCS， A*算法。

general search for **DFS, BFS, UCS, Astar**

```python
def generalSearch(problem, frontier, heuristic=nullHeuristic):
    start_state = (problem.getStartState(), [], 0)  # state, path, costTillNow
    if isinstance(frontier, util.Queue) or isinstance(frontier, util.Stack):
        frontier.push(start_state)
    else:
        frontier.push(start_state, 0)  # node, priority
    visited = set()  # trace whether visited or not

    while not frontier.isEmpty():
        (current, path, cost) = frontier.pop()
        if problem.isGoalState(current):
            return path
        if current not in visited:
            visited.add(current)
            for successor, successor_action, successor_cost in problem.getSuccessors(current):  # node action cost
                new_path = path + [successor_action]
                new_cost = cost + successor_cost
                new_state = (successor, new_path, new_cost)
                if successor not in visited:
                    if isinstance(frontier, util.Stack) or isinstance(frontier, util.Queue):
                        frontier.push(new_state)
                    else:
                        new_cost = new_cost + heuristic(successor, problem)
                        frontier.push(new_state, new_cost)
```

```python
# dfs 使用stack来进行搜索
def depthFirstSearch(problem):
		return generalSearch(problem, util.Stack())

# bfs 使用queue
def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""
    return generalSearch(problem, util.Queue())

# ucs 使用 priority Queue
def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    return generalSearch(problem, util.PriorityQueue())

# A* 使用PriorityQueue，和ucs主要区别在于，使用了不同的heuristic function来进行评价
def aStarSearch(problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    return generalSearch(problem, util.PriorityQueue(), heuristic)
 
def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0
```

### Question 5

Starting state of the corners problem: (node position, whether visited or not)

```python
visited = [False, False, False, False]
self.startingState = (self.startingPosition, tuple(visited))
```

Goal state of the problem： when all the node are visited

```python
return False not in state[1]
```

Get the successor: if next state is one of the corner,  set the position in the visited list to True, append the next state

```python
if not hitsWall:
    nextState = (nextx, nexty)
    visited = list(state[1])
    if nextState in self.corners:
        visited[self.corners.index(nextState)] = True
    successors.append(((nextState, tuple(visited)), action, 1))
```

### Question 6

heuristic function：calculate the distance of all the possibilities from the node to the other unvisited nodes. return the minimum distance

```python
def cornersHeuristic(state, problem):
    corners = problem.corners  # These are the corner coordinates
    walls = problem.walls  # These are the walls of the maze, as a Grid (game.py)

    node = state[0]
    visited = state[1]

    if False not in visited:
        return 0

    unvisitedNode = []
    for i in range(len(corners)):
        if not visited[i]:
            unvisitedNode.append(corners[i])

    return miniDistance(unvisitedNode, node)


def miniDistance(unvisitedNode, position):
    if len(unvisitedNode) == 0:
        return 0

    distance = [];
    for corner in unvisitedNode:
        distance.append(
            util.manhattanDistance(corner, position) + miniDistance([node for node in unvisitedNode if node != corner],
                                                                    corner))
    return min(distance)
```

### Question 7

foodHeuristic: calculate every distance between current position to the food position, return the max distance

```python
def foodHeuristic(state, problem):
    position, foodGrid = state
    foodPosition = foodGrid.asList()
    if len(foodPosition) == 0:
        return 0
    distance = []
    for foodP in foodPosition:
        # distance.append(util.manhattanDistance(position,foodP))
        """calculate every distance between current position to the food position"""
        distance.append(mazeDistance(position, foodP, problem.startingGameState))

    return max(distance)
```

### Question 8

Goal state

```python
    def isGoalState(self, state):
        x, y = state
        return state in self.food.asList()
```

use A* search to find the path to the next goal

```python
return search.aStarSearch(problem)
```