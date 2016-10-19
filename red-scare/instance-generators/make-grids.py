import sys
import random
import networkx as nx
from write_nx_graph import write_graph

def rep((tuple)): return '{0}_{1}'.format(tuple[0], tuple[1])

def grid_graph(X,Y):
    G = nx.Graph()
    for x in range(X):
        for y in range(Y):
            G.add_node(rep((x,y)))
            if x > 0: G.add_edge(rep((x,y)),rep((x-1,y)))
            if y > 0: G.add_edge(rep((x,y)),rep((x,y-1)))
            if x > 0 and y > 0: G.add_edge(rep((x,y)),rep((x-1,y-1)))
    return G

def long(X,Y):
    G = grid_graph(X,Y)
    R = set()
    for x in range(1, X-1, 4):
        for y in range(Y-1):
            R.add(rep((x,y)))
    for x in range(3,X-1,4):
        for y in range(1,Y):
            R.add(rep((x,y)))
    name = 'grid-{0}-0'.format(X)
    write_graph(G, name, rep((0,0)), rep((X-1,Y-1)), R)

    random.seed(0)

    R_subset = R.difference(random.sample(R, X/2)) # remove X/2 random elements
    name = 'grid-{0}-1'.format(X)
    write_graph(G, name, rep((0,0)), rep((X-1,Y-1)), R_subset)

    R_superset = R.union(random.sample ( set(G.nodes()).difference(R), X/2)) # add X/2 random elements
    name = 'grid-{0}-2'.format(X)
    write_graph(G, name, rep((0,0)), rep((X-1,Y-1)), R_superset)

for N in [5,10,25,50]:
    long(N,N)
