from inputParser import parseInput
import math
import matplotlib.pyplot as plt

plt.show()
points = parseInput()
points.sort(key=lambda point: point[0])

splitPoints = []

def recursiveSplit(array):
    if (len(array) > 3):
        splitPoint = int(len(array) / 2)
        splitPoints.append(splitPoint)
        recursiveSplit(array[:splitPoint])
        recursiveSplit(array[splitPoint:])

recursiveSplit(points)

print(splitPoints)

X = []
Y = []

for (x, y, id) in points:
    X.append(x)
    Y.append(y)

plt.scatter(X, Y)
plt.xlabel('x')
plt.ylabel('y')
plt.show()
