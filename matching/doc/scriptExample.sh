#!/bin/sh
for FILE in data/*-in.txt

do
	echo $FILE
	base=${FILE%-in.txt}
    java GS $FILE > $base.yourname.out.txt # replace with your command!
    diff $base.yourname.out.txt $base-out.txt
done
