#CXX = g++
CXX = mpicxx
CXXFLAGS = -Wall -W -s -lpthread
.PHONY: default run
default: run

run:
	${CXX} ${CXXFLAGS} *.cpp -o program 
	
clean:
	rm -f *.o program
