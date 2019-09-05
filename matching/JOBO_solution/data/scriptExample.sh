#!/bin/sh
for FILE in *-in.txt

do
	echo $FILE
	base=${FILE%-in.txt}
    java -cp '../src/' Main $FILE > $base.groupi.out.txt # replace with your command!
    diff $base.groupi.out.txt $base-out.txt
done
