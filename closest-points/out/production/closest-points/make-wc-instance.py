# Produces a reasonably difficult instance to the closest pair problem
# usage:
#     python make-wc-instance.py 3
# prints an instance with 2^3-2 points

import math, sys, random


def translate_N(L, dist):
    """ Translate the points in L north, so that they lie at y >= dist """
    if L == []: return L
    miny = min([y for (x,y) in L])
    if miny > 0: miny = 0
    else: return map(lambda (x, y): (x, y - miny + dist), L)

def translate_S(L, dist):
    """ Translate the points in L south, so that they lie at y <= -dist """
    if L == []: return L
    maxy = max([y for (x,y) in L])
    if maxy < 0: maxy = 0
    else: return map(lambda (x,y): (x, y - maxy - dist), L)


def distribute_points(k, w, delta, x_mid):
    """ Return a list of 2^k-2 points.
        Their x-coordinates lie within x_mid +- w
        Their y-coordiantes are positive if x<0, negative if x>0
        All points have distance at least 2delta
        Except for a single pair of distance exactly delta
    """
    assert (k > 0)
    if k == 1: return []

    Ll = distribute_points(k-1, float(w)/2, 2 * delta, x_mid - float(w)/2)
    Ll = translate_N(Ll, 3*delta)

    for (x,y) in Ll: assert x <= x_mid and y > 0

    Lr = distribute_points(k-1, float(w)/2, 2 * delta, x_mid + float(w)/2)
    Lr = translate_S(Lr, 3*delta)

    for (x,y) in Lr: assert x >= x_mid and y < 0

    p1 = (x_mid - float(w)/2, +math.sqrt((float(delta)/2)**2 -(float(w)/2)**2))
    p2 = (x_mid + float(w)/2, -math.sqrt((float(delta)/2)**2 -(float(w)/2)**2))

    return Ll + Lr + [p1,p2]

L = distribute_points(int(sys.argv[1]),1,1,0)
random.shuffle(L)
id = 0
for (x,y) in L:
    print "{0}  {1} {2}".format(id, x , y)
    id += 1
