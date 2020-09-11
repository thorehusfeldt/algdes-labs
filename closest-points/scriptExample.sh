#!/bin/sh
rm output.txt
touch output.txt

for FILE_NAME in data/*-tsp.txt; do
	echo $FILE_NAME
	printf "$FILE_NAME: \t" >> output.txt
	python3 src/closestPoints.py < $FILE_NAME >> output.txt
done
