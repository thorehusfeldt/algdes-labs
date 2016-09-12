Data Files for Closest Pair in the Plane
========================================

Inputs
------
The data directory contains a bunch of files with extension “-tsp.txt” taken from the TSPLIB [1], a library of instances from various sources originally intended for Traveling Salesman algorithms.
The input format is somewhat heterogenous, but here are the first few lines of two of the input files, and a middle part of a third one:

    NAME : a280
    COMMENT : drilling problem (Ludwig)
    TYPE : TSP
    DIMENSION: 280
    EDGE_WEIGHT_TYPE : EUC_2D
    NODE_COORD_SECTION
      1 288 149
      2 288 129
      3 270 133
      4 256 141
    ...

    NAME: gr96
    TYPE: TSP
    COMMENT: Africa-Subproblem of 666-city TSP (Groetschel)
    DIMENSION: 96
    EDGE_WEIGHT_TYPE: GEO
    DISPLAY_DATA_TYPE: COORD_DISPLAY
    NODE_COORD_SECTION
     1 14.55 -23.31
     2 28.06 -15.24
     3 32.38 -16.54
     4 31.38 -8.00
    ...

    ...
    8 7.21100e+03 1.19020e+04
    9 1.53280e+04 7.87600e+03
    10 1.05760e+04 5.21400e+03
    11 1.25600e+04 2.42000e+03
    12 1.63680e+04 4.31200e+03
    ...

As you can see, the interesting stuff appears afer NODE_COORD_SECTION.
Coordinates are integers or reals; sometimes given in scientific notation.

Some of the files are huge.

I also uploaded six files called “closest-pair-N-in.txt” for N=1,...,6 that contain some really simple instances that you may find useful for testing in the beginning.
In each of them, the closest pair is “romeo” and “juliet”, and their distance is 1.
Finally, the files of the form “wc-instance-N.txt" for various instances sizes N contain some hand-crafted instances whose only purpose it too annoy a divide and conquer-algorithm, hopefully (if everthing is written correctly) leading to a lot of recursive calls.

You may want to rename the city names to indices for parsing purposes.

Output
------
The file “closest-pairs.out” contains the output of my algorithm for all the .tsp files.
It shows, in each line, the name of the input file, the number of points in that file, and the closest distance between those points.

[1] TSPLIB, a library of sample instances for TSP and related problems.
http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/
