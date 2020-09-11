import sys
import re

numberRegex = '[-+]?\d*\.?\d+(?:[eE][-+]?\d+)?'
regex = "^\s*(\w+)\s*(" + numberRegex + ")\s*(" + numberRegex + ")$"

def parseInput():
    points = []
    for l in sys.stdin:
        m = re.search(regex, l)
        if (m != None):
            id = m.group(1)
            x = float(m.group(2))
            y = float(m.group(3))
            points.append((x, y, id))
    return points
