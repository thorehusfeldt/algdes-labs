# Inputs

The input file

    USA-highway-miles.in

describes the intercity highway distance of 128 cities in the USA, based on data collected for the Stanford Graph Base. 
It starts with listing the names of all cities, one per line.
Names that contain whitespace are enclosed in quote marks.
Then follows a line for every pair of cities with the distance given as an integer in square brackets.

    Duckburg 
    "Gotham City"
     Metropolis 
    Duckburg--"Gotham City" [2324]
     Duckburg--Metropolis [231]
     “Gotham City”--Metropolis [2298] 

## About USA-highway-miles

If you’re curious, the big input file (like many of the files we use in this course) is based on the Stanford GraphBase by Donald Knuth.
In this case, I modified Knuth’s file “miles.dat”, which you can find on the internet. 
It contains the correct intercity highway distances from 1949, based on Rand McNally’s Standard Highway Mileage Guide (1949). 
Knuth’s data includes slight modifications to those numbers (for example, the distances in miles.dat satisfy the triangle inequality) and some more information such as population and location.