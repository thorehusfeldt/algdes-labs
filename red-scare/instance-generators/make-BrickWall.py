# Create BrickWall graphs. 
# These have |W| = 1 and constitute interesting instances to Forced.

import networkx as nx
import sys
from write_nx_graph import write_graph

class BrickWall(nx.Graph):
    # A brick-wall graph of height 2 consisting of
    # b bricks of size 2*s with overhang 0 <= o < s/2
    # The overhang is the number of edges shared between blocks
    #
    # Example for b = 4, s = 4, o = 1
    #
    #   7 6 5 4       16
    #   *-*-*-* *-*-*-*
    #   |     | | 9   |
    #   *-*-*-*-*-*-*-*-*-*
    #   0 1 |     | |     |
    #       *-*-*-* *-*-*-*
    #      13    10
    #

    def _num_nodes(self,i):
        # the number of nodes in blocks 0,..,i-1
        nodes_per_block = 2*self.s - self.o - 1 # in blocks 1,..,i-1
        return 2*self.s + (i - 1) * nodes_per_block

    def _first(self,i):
        # index of first node of block i
        assert(i>0)
        return self._num_nodes(i)

    def _last(self,i):
        # index of last node of block i
        assert(i>0)
        return self._num_nodes(i+1) - 1

    def _first_neighbour(self,i):
        # index of the node of block i-1 that needs an edge to _first
        assert(i>0)
        if i == 1: return self.s-1
        else: return self._first(i) - self.s - 1

    def _last_neighbour(self,i):
        # index of the node of block i-1 that needs an edge to _first
        assert(i>0)
        if i == 1: return self.s - 1 - self.o
        else: return self._first(i) - self.s - 1 - self.o

    def __init__(self, b, o=1, s=4):
        nx.Graph.__init__(self)
        assert(b>0)
        self.b = b
        self.o = o
        self.s = s

        self.add_cycle(range(2*s)) # Brick 0

        for i in range(1,b):
            self.add_path(range(self._first(i), self._last(i) + 1))
            self.add_edge(self._first(i), self._first_neighbour(i))
            self.add_edge(self._last(i), self._last_neighbour(i))


# Generate positive viared-instances using BrickWalls
# of s = 4 with overlap 0 and 1

for n in [1,2,3,4,10,100,1000,10000]:
    for overhang in [0,1]:
        G = BrickWall(n, o = overhang)
        name = "wall-{0}-{1}".format(('z','p')[overhang],n)
        write_graph(G,name,'7','0',[str(len(G)-5)])

# Generate some negative viared-instances: n bricks connected by paths

for n in [1,2,3,4,10,100,1000,10000]:
    G = nx.Graph()

    # 7 *-*-*-* *-*-*-*
    #   |     | |     |
    #   *-*-*-*-*-*-*-* 11
    #   0       8
    #
    # n disjoint 8-bricks:
    for i in range(n): G.add_cycle(range(8*i, 8*(i+1) ))
    # connect brick i to i+1 with an edge
    for i in range(n-1): G.add_edge(8*i + 3, 8*(i+1) ) # this is wrong

    name = "wall-n-{0}".format(n)
    write_graph(G,name,'7','0',[str(len(G)-5)])


