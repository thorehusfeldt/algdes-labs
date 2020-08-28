#!/bin/sh
for FILE in *-in.txt

do
	echo $FILE
	base=${FILE%-in.txt}
    python3 ../solution.py < $FILE > $base.our_solution.out.txt
    python3 unordered-diff.py $base.our_solution.out.txt $base-out.txt
    rm $base.our_solution.out.txt
done
