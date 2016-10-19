import sys
import random
import networkx as nx
from write_nx_graph import write_graph

uncommons = set() # Everything except the 3300 common words in SGB

f = open ('data/words.txt','r')
words = []
for line in f:
        if len(line)>0 and line[0] == '*': continue
        word = line.strip()[:5]
        words.append(word)
        if not (len(line.strip())>5 and line[5] == '*'): uncommons.add(word)
f.close()

def starredwords(word, numstars):
        # given 'AWORD' returns ['*WORD', 'A*ORD',...,'AWOR*']
        if numstars == 1: return [word[:i] + '*' +word[i+1:] for i in range(5)]
        else: return [word[:i] + '*' + word[i+1:j] + '*' + word[j+1:] for j in range (1,5) for i in range(j)]

def _numvowels(word):
        # returns the number of vowels in word
        counter = 0
        for c in word: counter += (c in 'aeiou')
        return counter 
def sorted(word):
        letters = list(word)
        letters.sort()
        sorted = "".join(letters)
        return sorted

class Words(nx.Graph):

        def __init__(self,L, numstars):
                nx.Graph.__init__(self)

                self.add_nodes_from(L)
                N = dict()
                for word in L:
                        for starred in starredwords(word,numstars):
                                if not starred in N:
                                        N[starred] = set([word])
                                else: N[starred].add(word)
                        s = sorted(word)
                        if not s in N:
                                N[s] = set([word])
                        else:
                                N[s].add(word)
                S = set(L)
                for word in self.nodes():
                        for starred in starredwords(word,numstars):
                                for neighbour in N[starred]:
                                        if word != neighbour:
                                                self.add_edge(word, neighbour) 
                        for neighbour in N[sorted(word)]:
                                if word != neighbour:
                                        self.add_edge(word, neighbour)

def wordgraph(n, numstars, musthaves):
        L = []
        for word in words:
                if word not in musthaves: L.append(word)
        random.seed(0)
        random.shuffle(L)
        L = L[:n-len(musthaves)]
        L.extend(musthaves)
        return Words(L, numstars)

def write_rusties():
        for n in [2000, 2500, 3000, 3500, 4000, 4500, 5000, 10000]:
            for numstars in [1,2]:
                G = wordgraph(n, numstars, ['begin','ender','rusty'])
                name = "rusty-{0}-{1}".format(numstars, len(G))
                write_graph(G, name, 'begin', 'ender', ['rusty'])

        # write a small graph as well:
        G = Words(words, 1)
        V = set()
        P = nx.all_shortest_paths(G, 'begin', 'rusty')
        for L in P:
               for w in L: V.add(w) 
        P = nx.all_shortest_paths(G, 'ender', 'rusty')
        for L in P:
               for w in L: V.add(w) 
        L = list(V)
        for v in L:
               V.add(v)
        G = Words(V, 1)
        name = "rusty-1-{0}".format(len(G))
        write_graph(G, name, 'begin', 'ender', ['rusty'])

def write_commons():
        for n in [20, 50, 100, 250, 500,1000,1500,2000, 2500, 3000, 3500, 4000, 4500, 5000, 10000]:
            for numstars in [1,2]:
                G = wordgraph(n, numstars, ['start', 'ender'])
                name = "common-{0}-{1}".format(numstars,len(G))
                R = [word for word in G.nodes() if word in uncommons]
                write_graph(G, name, 'start', 'ender', R)

write_rusties()
write_commons()
