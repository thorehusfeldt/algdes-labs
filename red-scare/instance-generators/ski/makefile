SHELL = bash


graphs:
	mkdir figures
	mkdir graphs
	for j in 1 2 3; do \
		for level in 3 5 10 20; do \
			fsharpi ski.fsx $$level ski-level$${level}-$$j;\
			if [ "$$level" -lt "20" ];then \
				cd figures; mpost ski-level$${level}-$$j.mp; cd ..;\
			fi\
		done \
	done

clean:
	$(RM) figures -r
	$(RM) graphs -r
