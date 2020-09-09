from inputParser import parseInput
import math

points = parseInput()

# Sort points by x
points.sort(key=lambda point: point[0])

def euclideanDistance(p1, p2):
    xDiff = p1[0] - p2[0]
    yDiff = p1[1] - p2[1]
    return math.sqrt(xDiff ** 2 + yDiff ** 2)

def xBeltPredicate(p1, p2, dist):
    return p1[0] - p2[0] < dist

def yBeltPredicate(p1, p2, dist):
    return p1[1] - p2[1] < dist

def getMinDistInPointArray(points):
    if (len(points) <= 1):
        return float('+Inf')

    distances = []
    for i in range(0, len(points)):
        for i2 in range(i + 1, len(points)):
            distances.append(euclideanDistance(points[i], points[i2]))

    return min(distances)

def recursiveSplit(array, isWithinBelt):
    if (len(array) > 3):
        splitPoint = int(len(array) / 2)

        leftSplit = recursiveSplit(array[:splitPoint], isWithinBelt)
        rightSplit = recursiveSplit(array[splitPoint:], isWithinBelt)

        beltPoints = []
        minDist = min(leftSplit, rightSplit)

        lowerBound = splitPoint
        while (lowerBound >= 0 and isWithinBelt(array[splitPoint], array[lowerBound], minDist)):
            beltPoints.append(array[lowerBound])
            lowerBound -= 1


        upperBound = splitPoint + 1
        while (upperBound < len(array) and isWithinBelt(array[upperBound], array[splitPoint], minDist)):
            beltPoints.append(array[upperBound])
            upperBound += 1

        # Avoid stack overflow by stopping here
        if (len(beltPoints) <= 8):
            beltMin = getMinDistInPointArray(beltPoints)
        else:
            beltPoints.sort(key=lambda point: point[1])
            beltMin = recursiveSplit(beltPoints, yBeltPredicate)

        return min(minDist, beltMin)
    else:
        minDist = getMinDistInPointArray(array)
        return minDist

if (len(points)):
    min = recursiveSplit(points, xBeltPredicate)
    print(len(points), min)
