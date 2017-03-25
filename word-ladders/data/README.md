# Data files for word-ladders

The file

	words-5757.txt

contains 5757 English words of 5 letters, one per line.
For debugging, maybe you want to run on smaller graphs first, these can be found in 

    words-10.txt
    words-250.txt
    words-50.txt	
    words-500.txt

## Testing

For size n, the file

	words-<n>-in.txt

contains a number of test cases that you can try, one per line.
For instance, here is words-10-in.txt:

	other there
	other their
	could would
	would could
	there other
	about there

The corresponding out-file contains, for each line, the number of edges no the shortest path from the first word to the last.
Otherwise, it contains “-1”.
For instance, words-10-out.txt:

	1
	1
	1
	1
	-1
	-1 

tells you that there is a shortest path of lenght 1 from “other” to “their”.
There is no path at all from “there” to “other”.

You can run all your tests at once by editing the scriptExample.sh. It lists and sorts all files ending with -in.txt by size, then iterates through the files and runs your execution commands, in the example file it is a java command with the files as arguments and output piped to another file.This is followed by a diff command that compares your output with the expected output. 