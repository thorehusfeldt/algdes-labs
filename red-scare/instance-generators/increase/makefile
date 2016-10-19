SHELL = bash


graphs:
	mkdir figures
	mkdir graphs
	for j in 1 2 3; do \
		for i in 8 10 20 50 100 500; do \
			fsharpi increase.fsx $$i increase-n$${i}-$$j;\
			if [ "$$i" -lt "20" ];then \
				cd figures; mpost increase-n$${i}-$$j.mp; cd ..;\
			fi\
		done \
	done

pdf: graphs
	pdflatex increase.tex

clean:
	$(RM) figures -r
	$(RM) graphs -r
