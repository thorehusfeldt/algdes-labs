#!/bin/sh
for FILE in data/*-in.txt # change to your path!

do
	echo $FILE
	base=${FILE%-in.txt}
    java ladders $base.txt $FILE > $base.yourname.out.txt # replace with your command!
    diff $base.yourname.out.txt $base-out.txt
done
