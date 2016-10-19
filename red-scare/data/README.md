# Red Scare! data format


Every input file is of the following form:

```
	n m r
	s t
	<vertices>
	<edges>
```

The integer `n` is the number of vertices.
The integer `m` is the number of edges.
The integer `r` is the cardinality of *R*.

`<vertices>` is a list of vertex names, one per line. Each vertex name is a string from `[_a-z0-9]+`.
The names of vertices in R are followed by “` *`”.

`<edges>` is a list of edges of the form

````
u -- v
````

for the undirected edge between *u* and *v*, or

````
u -> v
````

for the directed arc from *u* to *v*.
