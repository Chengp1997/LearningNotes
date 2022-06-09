# Project 4

`bustersAgents.py, inference.py`

## Question 0 DiscreteDistribution Class

Normalize 

Normalize the distribution, so that the total value of all keys sums to 1. The ratio of distribution will not change.

```python
def normalize(self):
    totalValues = self.total()
    if totalValues == 0:
        return
    for key in self.keys():
        self[key] /= totalValues
```

Sample

Draw a random sample from the distribution. return the key. 

select a random value, if the key falls into the fraction of a key, return this value;

```python
def sample(self):
    probability = random.random() * self.total()
    cumulate = 0.0
    for key in self.keys():
        cumulate += self[key]
        if probability <= cumulate:
            return key
```

## Question 1 Observation Probability

A general method, to calculate the observation probability based on the noise distance provided by the distance sensor. 

if the ghost is captured(the distance should be None), the ghost must sent to jail with probability 1. Else, calculate the probability . 

```python
def getObservationProb(self, noisyDistance, pacmanPosition, ghostPosition, jailPosition):
    "*** YOUR CODE HERE ***"
    # noiseDistance - noisy reading of the distance to the ghost  (distance sensor)
    if noisyDistance is None:  # capture a ghost
        if ghostPosition == jailPosition:  # must sent to the jail
            return 1
        else:
            return 0
    elif ghostPosition == jailPosition:  # impossible
        return 0
    trueDistance = manhattanDistance(pacmanPosition, ghostPosition)
    probability = busters.getObservationProbability(noisyDistance, trueDistance)
    return probability
```

## Question 2 Exact Inference Observation

Update beliefs based on the distance observation and Pacman's position. 

For all ghost's legal position, calculate the probability and update the beliefs.

```python
def observeUpdate(self, observation, gameState):
    "*** YOUR CODE HERE ***"
    pacmanPosition = gameState.getPacmanPosition()
    jailPosition = self.getJailPosition()
    for ghostPosition in self.allPositions:
        probability = self.getObservationProb(observation, pacmanPosition, ghostPosition, jailPosition)
        self.beliefs[ghostPosition] *= probability  # shrink with probability
    self.beliefs.normalize()
```

## Question 3 Exact Inference with Time Elapse

Predict beliefs in response to a time step passing from the current.

for all the old positions, calculate the posibility distribution of the new position. and update them 

```python
def elapseTime(self, gameState):
    "*** YOUR CODE HERE ***"
    # P(noisyDistance|E=evidence)
    newBelief = DiscreteDistribution()
    for oldPos in self.allPositions:
        # given old position, new Position possibilities
        newPosDist = self.getPositionDistribution(gameState, oldPos)
        for p in newPosDist.keys():  # for all position
            newBelief[p] += self.beliefs[oldPos] * newPosDist[p]  # old evidence * newDistribution  shrink
    self.beliefs = newBelief
    self.beliefs.normalize()
```

## Question 4 Exact Inference Full Test

first, find the closest ghost based on current position.

Then find the action that after this action ,the pacman is the closest to this ghost.

return this action

```python
def chooseAction(self, gameState):
    pacmanPosition = gameState.getPacmanPosition()
    legal = [a for a in gameState.getLegalPacmanActions()]
    livingGhosts = gameState.getLivingGhosts()
    livingGhostPositionDistributions = \
        [beliefs for i, beliefs in enumerate(self.ghostBeliefs)
         if livingGhosts[i + 1]]
    "*** YOUR CODE HERE ***"
    # find the closest ghost
    distance = float("inf")
    closestGhostPosition = None
    for distribution in livingGhostPositionDistributions:
        mostLikelyPosition = distribution.argMax()
        tempDistance = self.distancer.getDistance(pacmanPosition, mostLikelyPosition)
        if tempDistance < distance:
            distance = tempDistance
            closestGhostPosition = mostLikelyPosition

    # find the action that after this action, the pacman is the closest to this ghost
    minDistance = float("inf")
    bestAction = None
    for action in legal:  # for every action to the next state
        successorPosition = Actions.getSuccessor(pacmanPosition, action)
        # calculate the distance between new position to the ghost
        tempDistance = self.distancer.getDistance(successorPosition, closestGhostPosition)
        if tempDistance < minDistance:
            minDistance = tempDistance
            bestAction = action
    return bestAction
```

## Question 5 Approximate Inference Initialization and Beliefs

Initialize a list of particles.

evenly put all the position in each particles

```python
def initializeUniformly(self, gameState):
    self.particles = []
    "*** YOUR CODE HERE ***"
    for i in range(self.numParticles):
        pos = self.legalPositions[i % len(self.legalPositions)]  # evenly put in all particles
        self.particles.append(pos)
    random.shuffle(self.particles)
```

## Question 6 Approximate Inference Observation

Update beliefs based on the distance observation and Pacman's position.

and resample the particles

```python
def observeUpdate(self, observation, gameState):
    "*** YOUR CODE HERE ***"
    pacmanPosition = gameState.getPacmanPosition()
    jailPosition = self.getJailPosition()
    # the beliefDistribution of the particles. dict{particle: probability}
    beliefDistribution = self.getBeliefDistribution()
    for ghostPosition in self.allPositions:  # observe update the belief distribution
        beliefDistribution[ghostPosition] *= self.getObservationProb(observation, pacmanPosition,
                                                                     ghostPosition, jailPosition)  # weight
    if beliefDistribution.total() == 0:  # special case
        self.initializeUniformly(gameState)
        return
    # resample
    beliefDistribution.normalize()
    self.particles = [beliefDistribution.sample() for _ in range(self.numParticles)]
```

## Question 7 Approximate Inference with Time Elapse

new sample of the next state

```python
def elapseTime(self, gameState):
    "*** YOUR CODE HERE ***"
    newParticles = []
    for i, oldPos in enumerate(self.particles):  # for all particles
        newPosDist = self.getPositionDistribution(gameState, oldPos)  # transition model
        newParticles.append(newPosDist.sample())  # sample the particles using transition model
    self.particles = newParticles
```

## Question 8 Joint Particle Filter Observation

Initialize particles to be consistent with a uniform prior.

use the itertools.product to find all the position for each ghost. 

shuffle to distribute the them evenly.

```python
def initializeUniformly(self, gameState):
    self.particles = []
    "*** YOUR CODE HERE ***"
    for p in itertools.product(self.legalPositions, repeat=self.numGhosts):  # repeat!
        self.particles.append(p)
    random.shuffle(self.particles)
```

## Question 9 Joint Particle Filter Observation

Update beliefs based on the distance observation and Pacman's position.

there are more than 1 ghost, need to update the belief for each ghost.

```python
def observeUpdate(self, observation, gameState):
    "*** YOUR CODE HERE ***"
    pacmanPosition = gameState.getPacmanPosition()
    jailPosition = []
    for i in range(self.numGhosts):
        jailPosition.append(self.getJailPosition(i))
    # the beliefDistribution of the particles. dict{particle: probability}
    beliefDistribution = self.getBeliefDistribution()
    for particle in beliefDistribution:  # num of ghost's particle
        for ghost in range(self.numGhosts):
            beliefDistribution[particle] *= self.getObservationProb(observation[ghost], pacmanPosition,
                                                                     particle[ghost], jailPosition[ghost])  # weight
    if beliefDistribution.total() == 0:  # special case
        self.initializeUniformly(gameState)
        return
    # resample
    beliefDistribution.normalize()
    self.particles = [beliefDistribution.sample() for _ in range(self.numParticles)]
```

## Question 10

new sample for the particles

```python
def elapseTime(self, gameState):
    newParticles = []
    for oldParticle in self.particles:
        newParticle = list(oldParticle)  # A list of ghost positions

        # now loop through and update each entry in newParticle...
        "*** YOUR CODE HERE ***"
        for i in range(self.numGhosts):  # for each ghost
            newPosDist = self.getPositionDistribution(gameState, oldParticle, i, self.ghostAgents[i])
            newParticle[i] = newPosDist.sample()
        """*** END YOUR CODE HERE ***"""
        newParticles.append(tuple(newParticle))
    self.particles = newParticles
```