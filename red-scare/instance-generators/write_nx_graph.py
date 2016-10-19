import networkx as nx

def write_graph(G, name, s, t, W):
# Write G to the data directory
    filename = "../data/{0}.txt".format(name)
    print "Writing "+ filename
    f = open(filename,'w')
    f.write("{0} {1} {2}\n{3} {4}\n".format(len(G),G.size(),len(W), s,t))
    for v in G:
        f.write(str(v))
        if str(v) in W or v in W: f.write(' *') 
        f.write("\n")
    for e in G.edges():
        f.write ("{0} -{1} {2}\n".format(e[0], ('-','>')[nx.is_directed(G)], e[1]))
    f.close()
