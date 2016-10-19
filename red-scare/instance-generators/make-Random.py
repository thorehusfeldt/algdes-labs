# Create some random graphs with random W.
# The parameters are such that roughly half the instances are yes-instances

import networkx as nx
import random
from write_nx_graph import write_graph

def mapping((x,y)):
    return '{0}_{1}'.format(x,y)

def make_small_worlds():
    #for N in [10,20,30,40,50]:
    for N in [3, 10,20,30,40,50]:
        G = nx.navigable_small_world_graph(N, p=1, q=1, r=2, dim=2, seed=None).to_undirected()
        G = nx.relabel_nodes(G, mapping)
        
        R = random.sample(G.nodes(), N)
        name = 'smallworld-{0}-0'.format(N)
        write_graph(G, name, mapping((0,0)), mapping((N-1,N-1)), R)

        R = random.sample(G.nodes(), (N*N)/2)
        name = 'smallworld-{0}-1'.format(N)
        write_graph(G, name, mapping((0,0)), mapping((N-1,N-1)),R)


def make_gnms():
    for n in [10, 1000, 2000, 3000, 4000, 5000]:
        for d in [3,4]:
            m = (d*n)/2
            G = nx.gnm_random_graph(n,m)
            R = random.sample(G.nodes(), n/10)
            name = 'gnm-{0}-{1}-0'.format(n,m)
            write_graph(G, name, 0, 1, R)

            R = random.sample(G.nodes(), n/2)
            name = 'gnm-{0}-{1}-1'.format(n,m)
            write_graph(G, name, 0, 1, R)

random.seed(0)
make_small_worlds()
make_gnms()
