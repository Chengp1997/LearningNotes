all: program dotprod

program: program.o
	gcc -D_REENTRANT -o program program.o -lpthread -lm

program.o: program.c
	gcc -c program.c

dotprod: dotprod.o
	gcc -D_REENTRANT -o dotprod dotprod.o -lpthread -lm

dotprod.o: dotprod.c
	gcc -c dotprod.c

clean:
	rm -f *.o program dotprod
