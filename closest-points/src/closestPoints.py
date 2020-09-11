from inputParser import parse_input
import math

points = parse_input()

# Sort points by x
points.sort(key=lambda point: point[0])

def euclidean_distance(p1, p2):
    return math.sqrt((p1[0] - p2[0]) ** 2 + (p1[1] - p2[1]) ** 2)

def x_belt_predicate(p1, p2, dist):
    return p1[0] - p2[0] < dist

def y_belt_predicate(p1, p2, dist):
    return p1[1] - p2[1] < dist

def get_min_dist_in_points(points):
    if (len(points) <= 1):
        return float('inf')

    minimum = float('inf')
    for i in range(0, len(points)):
        for i2 in range(i + 1, len(points)):
            distance = euclidean_distance(points[i], points[i2])
            if distance < minimum: 
                minimum = distance

    return minimum

def recursive_split(array, isWithinBelt):
    if (len(array) > 3):
        splitPoint = int(len(array) / 2)

        left_split_list = array[:splitPoint]
        right_split_list = array[splitPoint:]

        left_split_closest = recursive_split(left_split_list, isWithinBelt)
        right_split_closest = recursive_split(right_split_list, isWithinBelt)

        belt_points = []
        min_dist = min(left_split_closest, right_split_closest)

        lowerBound = splitPoint
        list_split_point = array[splitPoint]
        while (lowerBound >= 0 and isWithinBelt(array[splitPoint], array[lowerBound], min_dist)):
            belt_points.append(array[lowerBound])
            lowerBound -= 1

        upper_bound = splitPoint + 1
        while (upper_bound < len(array) and isWithinBelt(array[upper_bound], array[splitPoint], min_dist)):
            belt_points.append(array[upper_bound])
            upper_bound += 1

        if (len(belt_points) <= 15):
            belt_min = get_min_dist_in_points(belt_points)
        else:
            belt_points.sort(key=lambda point: point[1])
            belt_min = recursive_split(belt_points, y_belt_predicate)

        return min(min_dist, belt_min)
    else:
        min_dist = get_min_dist_in_points(array)
        return min_dist

if (len(points)):
    min = recursive_split(points, x_belt_predicate)
    print(f"{len(points)}\t {min}")
