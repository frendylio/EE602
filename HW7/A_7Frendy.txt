================================
= Created By: Frendy Lio
= Date: October 18th
================================

******************************************************************************
Q1 Testing correctness of Board
******************************************************************************

===================
Q1.1 How many cases passed?
===================
Output for test cases are at the end of this report.


Test 1: all passed
Test 2: all passed
Test 3: all passed
Test 4: all passed
Test 5a: all passed
Test 7: all passed
Test 8: all passed
Test 10: all passed
Test 11: passed

===================
Q1.2 Which cases are not passed? Please copy the output in your report.
===================
All test passed

******************************************************************************
Q2 Testing correctness of Solver
******************************************************************************

===================
Q2.1 How many cases passed?
===================
PS: Some of the testing work sometimes because I am testing multiple inputs at once.
Thus, I believe they might be sharing the same memory when they shouldn't causing the erros.
When tested individually, they work fine.

Test 1a: all passed
Test 1b: all passed
Test 5a: all passed (Sometimes it runs out of memory, sometimes it works...)
Test 10: passed
Test 11a: all passed (Sometimes it outputs -1 ...) 
Test 11b: all passed
Test 12b: all passed


===================
Q1.2 Which cases are not passed? Please copy the output in your report.
===================
This errors might be explained because I am running multiple test cases at once.
Thus, they might be sharing the same memory when they shouldn't.
If I test them individually, they work fine.

Test 5a: Soemtimes I run of memory when testing puzzle3x3-unsolvable2.txt. But usually it works.

Output:
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at Board.<init>(Board.java:28)
        at Board.helperBoard(Board.java:205)
        at Board.neighbors(Board.java:192)
        at Solver.<init>(Solver.java:38)
        at Solver.main(Solver.java:210)
makefile:32: recipe for target 'run_Solver' failed
make: *** [run_Solver] Error 1

Test 11a: Sometimes it outputs -1
File:puzzle2x2-03.txt
Minimum number of moves: -1

******************************************************************************
Q3 TIMING  of Solver
******************************************************************************
===================
Q3.1 How many of your cases run over 5 seconds? Present your times.
===================

12

Output in a file call 3_1_Output.txt

===================
Q3.2 Q3.2 What are the average ratios of your Board operations over mine, for three methods Board(), equals(), and manhattan()? Are there special cases?
===================
My counter is different from the assignment. 

This means the way I count the Board Operators is wrong.


       filename        Board()  Equals() manhatthan()
----------------------------------------------------------
        puzzle38.txt 465761 465760 465761
        puzzle39.txt 56094 56093 56094
        puzzle34.txt 70082 70081 70082
        puzzle32.txt 118599 118598 118599
        puzzle36.txt 637341 637340 637341
        puzzle42.txt 746561 746560 746561
        puzzle37.txt 18456 18455 18456
        puzzle33.txt 139420 139419 139420
        puzzle46.txt 222828 222827 222828
        puzzle40.txt 156019 156018 156019
        puzzle35.txt 76477 76476 76477
        puzzle43.txt 255686 255685 255686
        puzzle41.txt  6572  6571  6572
        puzzle44.txt 76262 76261 76262
        puzzle45.txt 266016 266015 266016