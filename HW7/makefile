#
# define compiler and compiler flag variables
#

JFLAGS = -g
JC = javac
JR = java

Libraries = -classpath $*./../shared/algs4.jar:$*./../shared/stdlib.jar
Output_Libraries = -classpath $*./../shared/algs4.jar:$*./../shared/stdlib.jar:$*./../shared/checkstyle-lift.jar:$*./output

######
# Compile Java Class
######

#Board
class_Board:
	$(JC) $(JFLAGS) $(Libraries) $*./src/Board.java -d $*./output

run_Board:
	$(JR) $(Output_Libraries) Board

Board: clean class_Board run_Board

#Solver
class_Solver:
	$(JC) $(JFLAGS) $(Libraries) \
		$*./src/Board.java \
		$*./src/Solver.java -d $*./output

run_Solver:
	$(JR) $(Output_Libraries) Solver

Solver: clean class_Solver run_Solver

#FastCollinearPoints
class_FastCollinearPoints:
	$(JC) $(JFLAGS) $(Libraries) \
		$*./src/Point.java \
		$*./src/LineSegment.java \
		$*./src/FastCollinearPoints.java -d $*./output

run_FastCollinearPoints:
	@echo "Enter N and T: "; \
    read N; \
	$(JR) $(Output_Libraries) FastCollinearPoints $*./resources/$$N

FastCollinearPoints: clean class_FastCollinearPoints run_FastCollinearPoints

clean:
	$(RM) $*./output/*.class