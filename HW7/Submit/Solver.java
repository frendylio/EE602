// ================================
// = Created By: Frendy Lio
// = Date: October 18th
// ================================


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Scanner;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.ArrayList;
import java.io.File;


public class Solver { 
    private boolean isSolvable;
    private final Stack<Board> solutionBoards;
    private int boardCounter = 0;
    private int equalCounter = 0;
    private int manhattanCounter = 0;

    // find a solution to the initial board (using the A* algorithm)    
    public Solver(Board initial) {
        // Throw anIllegalArgumentExceptionin the constructor if the argument isnull
        try{
            if (initial == null) throw new NullPointerException();
        }
        catch(NullPointerException NullPointerException){
            System.out.println("The Board provided is null");
            this.solutionBoards = null;
            return;
        }        

        // Create my variables
        this.isSolvable = false;
        this.solutionBoards = new Stack<>();
        MinPQ<SearchNode> searchNodes = new MinPQ <> ();

        // insert initial searchNode
        searchNodes.insert(new SearchNode(initial, null));
        searchNodes.insert(new SearchNode(initial.twin(), null));

        this.manhattanCounter = this.manhattanCounter + 2;
        this.boardCounter = this.boardCounter + 2;

        // Repeat till we reach goal
        // insert searchnode -> delelte node with the minimun priority -> insert priority
        while (!searchNodes.min().board.isGoal()) {
            SearchNode searchNode = searchNodes.delMin();
            for (Board board : searchNode.board.neighbors())
                if (searchNode.prevNode == null || searchNode.prevNode != null && !searchNode.prevNode.board.equals(board))
                    searchNodes.insert(new SearchNode(board, searchNode));
                this.equalCounter++;
                this.manhattanCounter++;
                this.boardCounter++;
        }


        SearchNode current = searchNodes.min();
        while (current.prevNode != null) {
            this.solutionBoards.push(current.board);
            current = current.prevNode;
        }
        this.solutionBoards.push(current.board);

        if (current.board.equals(initial)) this.isSolvable = true;
        this.equalCounter++;        
    }
    
    // is the initial board solvable? (see below)    
    public boolean isSolvable(){
        return this.isSolvable;
    } 
    
    // min number of moves to solve initial board; -1 if unsolvable    
    public int moves(){
        if(this.isSolvable == false) return -1;
        return solutionBoards.size() - 1; // initial should be 0 andnot 1
    } 
    
    // sequence of boards in a shortest solution; null if unsolvable    
    public Iterable<Board> solution(){
        if(this.isSolvable == true) return this.solutionBoards;
        return null;
    }

    // ====================================================
    // Create the search node for the A* search algorithm
    // ====================================================
    private class SearchNode implements Comparable<SearchNode> {
        
        //  Board
        private final Board board;
        // Moves made
        private int moves;
        // Previous search node
        private final SearchNode prevNode;


        private int manhattan;

        // Insert the initial search node into a priority queue
        public SearchNode(Board board, SearchNode prevNode) {
            this.board = board;
            this.prevNode = prevNode;
            this.manhattan = board.manhattan();


            // If initial board , 0 moves and null previous search node
            if (prevNode != null) moves = prevNode.moves + 1;
            else moves = 0;
        }

        @Override
        public int compareTo(SearchNode that) {

            // Find the minority priority
            int priorityDiff = (this.manhattan + this.moves) - (that.manhattan + that.moves);

            if (priorityDiff == 0) return this.manhattan - that.manhattan;
            return priorityDiff;
        }
    }

    // test client (see below)    
    public static void main(String[] args) {   
        int counter = 0;
        // This is to ask which test to do
        ArrayList <String> question = new ArrayList <String>();

        question.add("Which Test to test?: \n");
        question.add("========Q2========== \n");
        question.add("A = (Test 1a) \n");
        question.add("B = (Test 1b) \n");
        question.add("C = (Test 5a) \n");
        question.add("D = (Test 10) \n");
        question.add("E = (Test 11a) \n");
        question.add("F = (Test 11b) \n");
        question.add("G = (Test 12b) \n");
        question.add("========Q3========== \n");
        question.add("H = (Test 1) \n");
        question.add("I = (Test 2) \n");

        // Ask which test case to test
        System.out.println(question);

        String input;
 
        Scanner scanIn = new Scanner(System.in);
        input = scanIn.nextLine();
 
        scanIn.close();          

        // Check if input is correct
        ArrayList <String> equalsInput = new ArrayList<String>();
        boolean validInput = false;
        equalsInput.add("A");
        equalsInput.add("B");
        equalsInput.add("C");
        equalsInput.add("D");
        equalsInput.add("E");
        equalsInput.add("F");
        equalsInput.add("G");
        equalsInput.add("H");
        equalsInput.add("I");

        for (String checker: equalsInput){
            if(input.equals(checker)) validInput = true;
        }

        if (validInput == false) return;

        // Create list of testing arguments 
        ArrayList<String> fileList = new ArrayList<String>(); 
        
            // Test A
            if (input.equals("A") || input.equals("B")){
                fileList.add("puzzle00.txt");
                fileList.add("puzzle01.txt");
                fileList.add("puzzle02.txt");
                fileList.add("puzzle03.txt");
                fileList.add("puzzle04.txt");
                fileList.add("puzzle05.txt");
                fileList.add("puzzle06.txt");
                fileList.add("puzzle07.txt");
                fileList.add("puzzle08.txt");
                fileList.add("puzzle09.txt");            
                fileList.add("puzzle10.txt");
                fileList.add("puzzle11.txt");
                fileList.add("puzzle12.txt");
                fileList.add("puzzle13.txt"); 
            }

            // Test C
            else if(input.equals("C") ){
                fileList.add("puzzle01.txt");
                fileList.add("puzzle03.txt");
                fileList.add("puzzle04.txt");
                fileList.add("puzzle17.txt");
                fileList.add("puzzle3x3-unsolvable1.txt");
                fileList.add("puzzle3x3-unsolvable2.txt");
                fileList.add("puzzle4x4-unsolvable.txt");
            }

            // Test E
            else if(input.equals("E") || input.equals("F") ){
                fileList.add("puzzle2x2-00.txt");
                fileList.add("puzzle2x2-01.txt");
                fileList.add("puzzle2x2-02.txt");
                fileList.add("puzzle2x2-03.txt");
                fileList.add("puzzle2x2-04.txt");
                fileList.add("puzzle2x2-05.txt");
                fileList.add("puzzle2x2-06.txt");
            }

            // Test G
            else if(input.equals("G")){
                fileList.add("puzzle3x3-02.txt");
                fileList.add("puzzle3x3-03.txt");
                fileList.add("puzzle3x3-04.txt");
                fileList.add("puzzle3x3-05.txt");
                fileList.add("puzzle3x3-06.txt");
                fileList.add("puzzle3x3-07.txt");
                fileList.add("puzzle3x3-08.txt");
                fileList.add("puzzle3x3-09.txt");
                fileList.add("puzzle3x3-10.txt");
            } 

            else if(input.equals("H") || input.equals("I")){
                // Storage/open all files 
                File folder = new File("resources/");
                File[] tempFileList= folder.listFiles();
                
                for (File file : tempFileList) {
                    // I run out of memory, so I splitted my tseting for this test.
                    if (file.getName() == "Skip") continue;
                    if (file.isFile()) {
                        fileList.add(file.getName());
                    }
                }
            } 

        if(input.equals("H")){
            System.out.println("       filename        moves  n seconds");
            System.out.println("----------------------------------------------------------");
        }

        else if(input.equals("I")){
            System.out.println("       filename        Board()  Equals() manhatthan()");
            System.out.println("----------------------------------------------------------");
        }        

        // for each command-line argument
        for (String filename : fileList ) {

            if(!input.equals("H")){
                if(!input.equals("I")){
                    System.out.println("===============================");
                    System.out.println("File:" + filename);
                }
            }
            // read in the board specified in the filename
            In in = new In("resources/" + filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);

            // solve the puzzle     
            Solver solver = new Solver(initial);            
            
                // For Test A
                if(input.equals("A")){
                    System.out.println("Minimum number of moves: " + solver.moves());
                }

                // For Test B
                else if (input.equals("B")){
                    System.out.println(solver.solution());
                }

                // Test C
                else if (input.equals("C")){
                    System.out.println("Is this solvable: " + solver.isSolvable());
                }
                
                // Test E
                else if (input.equals("E")){
                    System.out.println("Minimum number of moves: " + solver.moves());
                }
                
                // For Test F
                else if (input.equals("F")){
                    System.out.println(solver.solution());
                }

                // For Test G
                else if (input.equals("G")){
                    System.out.println(solver.solution());
                }   
                
                // For Test H
                else if (input.equals("H")){
                    Stopwatch Stopwatch = new Stopwatch();
                    Solver solverH = new Solver(initial);     
                    double timePass = Stopwatch.elapsedTime();
                    System.out.printf("%20s %5d %5d %5f\n",filename, solver.moves(), initial.dimension(), timePass);                    
                
                    if (timePass > 5) counter ++;
                }    
                
                else if (input.equals("I")){
                    System.out.printf("%20s %5d %5d %5d\n",filename, solver.boardCounter, solver.equalCounter, solver.manhattanCounter);                    
                }
        }

        if (input.equals("D")){
            
            Board Board1 = null;

            // solve the puzzle     
            Solver solver1 = new Solver(Board1); 
        }   

        else if (input.equals("H")){
            System.out.println("Counter:" + counter);
        }
    }


}