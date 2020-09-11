import sys
import re

# numberRegex = '[-+]?\d*\.?\d+(?:[eE][-+]?\d+)?'
# regex = "^\s*(\w+)\s*(" + numberRegex + ")\s*(" + numberRegex + ")$"
regex = re.compile('^\s*(?P<name>\w+)\s*(?P<x>([-+]?\d+(\.\d+)?([eE]?\+\d+)?))\s*(?P<y>([-+]?\d+(\.\d+)?([eE]?\+\d+)?))$')

def parse_input():
    points = []
    for l in sys.stdin:
        l = l.strip()
        m = regex.search(l)
        if (m != None):
            name = m.group('name')
            x = float(m.group('x'))
            y = float(m.group('y'))
            points.append((x, y, name))
    return points
