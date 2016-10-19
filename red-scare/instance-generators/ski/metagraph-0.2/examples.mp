input metagraph

beginfig(0)
	prologues := 2;
	noderadius := 3;
	node.x; node.y;
	y.c = x.c + (2cm,0);
	drawboxed(x, y);
	drawarrow arc(x)(--)(y);
endfig;

beginfig(1)
	prologues := 2;
	noderadius := 7;
	node.x;
	drawboxed(x);
	drawdblarrow loop(x, up) dashed evenly;
	drawdblarrow loop(x, down);
endfig;

beginfig(2)
	prologues := 2;
	noderadius := 7; node.x0("0"); noderadius := 10; node.x1("1");
	x1c = x0c + (2cm,0);
	drawboxed(x0, x1);
	drawarrow larc(x0)(--)(x1)(btex $a$ etex);
	drawarrow larc.bot(x0)({down}..)(x1)(btex $b$ etex);
	drawarrow lloop(x0, left, btex $\delta$ etex);
	drawarrow lloop(x1, right, btex $\sqrt2$ etex) dashed evenly;
	drawarrow lloop.llft(x1, 2right, btex $\gamma$ etex) dashed evenly;
endfig;

beginfig(3)
	prologues := 2;
	noderadius := 8;
	node.x; node.y;
	y.c = x.c + (2cm,0);
	drawboxed(x, y);
	drawarrow x.n{up}..y.c cutarc(x,y);
	drawarrow x.s{down}..y.c cutarc(x,y);
	drawarrow x.e{right}..y.c cutarc(x,y);
endfig;

beginfig(4)
	prologues := 2;
	noderadius := 7;
	node.x; node.y;
	y.c = x.c + (2cm,0);
	drawboxed(x, y);
	drawarrow arc(x)({up}..)(y);
	drawarrow arc(x)({down}..)(y);
	drawarrow arc(x)({right}..)(y);
endfig;

beginfig(5)
	% Petersen's graph
	prologues := 2;
	noderadius := 2;
	
	for i = 0 upto 4: 
		node.x[i]; node.y[i];
		x[i].c = up scaled 60 rotated (360i/5);
		y[i].c = up scaled 30 rotated (360i/5);
	endfor;	
	
	for i = 0 upto 4: drawboxed(x[i],y[i]); endfor;
	
	for i = 0 upto 4: 
		draw arc(x[i])(--)(x[(i+1)mod 5]); 
		draw arc(y[i])(--)(y[(i+2)mod 5]); 
		draw arc(x[i])(--)(y[i]); 
	endfor;

endfig;

beginfig(6)
	% A six-point star
	prologues := 2;
	noderadius := 4;

	n := 6;
	for i = 0 upto n - 1: 
		node.x[i]; 
		x[i].c = right scaled 60 rotated ((360i)/n);
	endfor;
	
	node.o;
	o.c = origin;
	
	for i = 0 upto n - 1: drawboxed(x[i]); endfor;
	drawboxed(o);
	
	for i = 0 upto n - 1: 
		drawdblarrow arc (o)(..)(x[i]) dashed evenly;
		drawarrow arc (x[i])({dir (90+(360i)/n)}..)(x[(i+1)mod n]); 
		drawarrow arc (x[(i+1)mod n])({dir (-90+(360i)/n)}..)(x[i]); 
	endfor;


endfig;

loopcurl := .3;

beginfig(7)
	prologues := 2;
	noderadius := 7;
	node.x;
	drawboxed(x);
	drawdblarrow loop(x, up) dashed evenly;
	drawdblarrow loop(x, down);
endfig;

loopcurl := 1.5;


bye