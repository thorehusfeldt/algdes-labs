# Flow behind enemy lines

## rail.txt

The input file “rail.txt” describes the network of capacitated railway connections.
* on the first row, the number of nodes *n*,
* *n* rows containing descriptive names of nodes *0*,...,*n*–1.
The names can be a word like “ORIGINS” or a number like “2”.
These names are not unique, so you can’t use them as keys.
* one row containing the number of arcs *m*
* *m* rows on the form
      u v c
   where *u* and *v* are node indices (between 0 and *n*–1), and *c* is a capacity. Capacity –1 means that the capacity on that edge is considered infinite (probably either because that part of the railway has really, really high capacity, or our spying attempts in that area were foiled by a beautiful Russian superspy of the opposite sex).
   Arcs are undirected.

The file is created from Figure 7 in T.E. Harris, F.S. Ross, “Fundamentals of a Method for Evaluating Rail Net Capacities”, US Air Force Project RAND research memorandum RM–1573, October 24, 1955, declassified on 13 May 1999.
The node DESTINATIONS was added with connections according to the figure legend, i.e., from Divisions 3, 6, 9 (Poland); B (Czechoslovovaka [sic]); and 2, 3 (Austria.)
The two ORIGINS nodes in the figure were merged into one.


## results.txt

The file “result.txt” describes a maximum flow of value 163 in the graph described by “rail.txt”
from ORIGINS to DESTINATIONS.
The flow is given in terms of a minumum cut.
The format of each line “result.txt” is

    u v c

where (*u*,*v*) is an arc on the cut with capacity *c*.
