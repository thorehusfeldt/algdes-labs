#!/bin/sh
for FILE in data/*-in.txt

do
	echo $FILE
	base=${FILE%-in.txt}
    python3 solution.py < $FILE > $base.afin.out.txt
    diff $base.afin.out.txt $base-out.txt
done
