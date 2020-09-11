points = parseInput()

def quadraticSolution():
    minDist = float('+Inf')
    for i in range(0, len(points)):
        for i2 in range(i + 1, len(points)):
            dist = euclidean_distance(points[i], points[i2])
            if (dist < minDist):
                minDist = dist
    return minDist
