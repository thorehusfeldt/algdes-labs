#!/bin/sh
for FILE_NAME in data/*; do
	echo $FILE_NAME
	echo -n $FILE_NAME >> output.txt
	echo -n " " >> output.txt
	python3 src/closestPoints.py < $FILE_NAME >> output.txt
    # python3 ../solution.py < $FILE > $base.our_solution.out.txt
    # python3 unordered-diff.py $base.our_solution.out.txt $base-out.txt
    # rm $base.our_solution.out.txt
done
