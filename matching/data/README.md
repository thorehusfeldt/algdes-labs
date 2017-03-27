Data File for Stable Matching
=============================

Input File Format
-----------------

A typical input file looks like this:

    # Stable marriage instance
    # based on NBC's show "Friends"
    #
    n=3
    1 Ross
    2 Monica
    3 Chandler
    4 Phoebe
    5 Joey
    6 Rachel
    
    1: 6 4 2
    2: 3 5 1
    3: 2 6 4
    4: 5 1 3
    5: 6 4 2
    6: 1 5 3

The first zero or more lines start with “#” and are ignored.
Then comes a line of the form “n=int” (no whitespace around the equality sign) defining n.
The following 2n lines describe m1, w1, ..., mn, wn, in that order.
Every line starts with an identifying integer id, starting with 1, followed by a single space, followed by a nonempty string name of non-whitespace characters (such as letters, digits, punctuation).
Odd-numbered lines are men, even-numbered lines are women.
Then follows a blank line.
Finally, there is a line for every id (not necessarily in order) that describes a preference list.
The line starts with id, followed by a colon and a single space.
Then follows a permutation of the ids of the opposite gender, separated by a single space.

Output File Format
------------------

The output file corresponding to the above example looks like this:

    Ross -- Rachel
    Chandler -- Monica
    Joey -- Phoebe

It contains one line for every matching.
Every line contains the name of the man, followed by space, two hyphens, space, and the name of the matched woman.


About the Files
---------------

    Name        |          n | Description
    ------------+------------+-------------
    sm-friends  |          3 | Based on the popular US TV show Friends
    sm-bbt      |          4 | Based on the popular US TV show Big Bang Theory	
    sm-illiad   |         62 | Based on Homer’s Illiad
    sm-kt-p-*   |          2 | Examples in [KT] on page 4 and 5
    sm-random-* | 5, 50, 500 | Random instances of various sizes
    sm-worst-*  | 5, 50, 500 | j Worst case instances of various sizes
Testing
--------
You can run all tests one by one by editing the scriptExample.sh with your execution commands.