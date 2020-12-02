// ================================
// = Created By: Frendy Lio
// = Date: October 18th
// ================================

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class Board { 

    // Variables
    private int[][] board;
    // Since we use the length alot..
    private int boardLength;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)    
    public Board(int[][] tiles){
        
        // Create a temp board with size n
        this.boardLength = tiles.length;
        int n = this.boardLength;

        int [][] tempBoard = new int[n][n];

        // Populate tempBoard so we can copy to our 
        // Actual board 
        for (int row = 0; row < n; row++){
            for (int col = 0; col < n; col++){
                tempBoard[row][col] = tiles[row][col];
            }
        }

        // Copy to actual board
        this.board = tempBoard;

        return;
    } 
    
    // string representation of this board    
    public String toString(){

        // Create our tempString using StringBuilder
        // StringBuilder = String that can be modified
        // Think like in c++ vectors.
        StringBuilder tempString = new StringBuilder();

        // First line is the size n
        // To go next line add \n
        int n = this.boardLength;
        tempString.append(n + "\n");

        // now construct our puzzle
        for(int row = 0; row < n; row++){
            for(int col = 0; col < n; col++){
                // append each col
                // Change string format to do the spacing
                // for some reason, "  " doesnt work
                tempString.append(String.format("%4d",this.board[row][col]));
            }
            // go next row
            tempString.append("\n");
        }
        
        // So now we return a string not a StringBuilder
        String output = tempString.toString();

        return output; 

    } 
    
    // board dimension n    
    public int dimension(){
        return this.boardLength;
    }
    
    // number of tiles out of place    
    public int hamming(){

        int counter = 1;
        int n = this.boardLength;
        int outOfPlace = 0;

        // Loop through each title
        for(int row = 0; row < n; row++){
            for(int col = 0; col < n; col++){
                // append each col
                if (counter != this.board[row][col] && this.board[row][col] != 0){
                    outOfPlace++;
                }
                counter++;
            }
        }

        return outOfPlace;
    }
    
    // sum of Manhattan distances between tiles and goal    
    public int manhattan(){

        int sum = 0;
        int currentNumber;
        int counter = 1;
        int colMoves = 0;
        int rowMoves = 0;
        int n = this.boardLength;

        // Loop through each title
        for(int row = 0; row < n; row++){
            for(int col = 0; col < n; col++){
                
                // Get currentumber
                currentNumber = this.board[row][col];

                // if title in wrong position, calculate where to go
                if(counter != currentNumber && currentNumber != 0){

                    // get column
                    colMoves = Math.abs(col - (currentNumber-1)%n);

                    // get rows
                    rowMoves = Math.abs(row - (currentNumber-1)/n);
                }
                sum = sum + colMoves + rowMoves;
                // System.out.println("Sum " + counter + " :" + sum);
                // increase counter;
                counter++;

                // Reset parameters
                colMoves = 0;
                rowMoves = 0;
            }
        }

        return sum;
    } 
    
    // is this board the goal board?    
    public boolean isGoal(){
        if(this.hamming() == 0) return true;
        
        return false;
    } 
    
    // does this board equal y?    
    public boolean equals(Object y) {
        Board that = (Board) y;
        if( y == this) return true;
        else if(Arrays.deepEquals(this.board, that.board) == true) return true;
        
        return false;
    }
    
    // all neighboring boards    
    public Iterable<Board> neighbors(){
        
        // Create a list to storage all our boards
        ArrayList<Board> ListBoard = new ArrayList<Board>();
        int row = -1;
        int col = -1;
        int n = this.boardLength;
        boolean checkBreak = false;
        // First find where our 0 is

            for(row = 0; row < n && checkBreak == false; row++){
                for(col = 0; col < n && checkBreak == false; col++){
                    if(this.board[row][col] == 0){ 
                        checkBreak = true;                        
                    }
                }
            }

        // Now we should know our empty space/0
            
            // move up
            Board tempBoard;
            // needed because for loop increases +1 after finding 0
            row--;
            col--;
            tempBoard = helperBoard(row, col, row - 1, col);
            if (tempBoard != null) ListBoard.add(tempBoard);  

            // east
            tempBoard = helperBoard(row, col, row, col + 1);
            if (tempBoard != null) ListBoard.add(tempBoard);            

            // south
            tempBoard = helperBoard(row, col, row + 1, col);
            if (tempBoard != null) ListBoard.add(tempBoard);            

            // west
            tempBoard = helperBoard(row, col, row, col - 1);
            if (tempBoard != null) ListBoard.add(tempBoard);            


        return ListBoard;
    } 

    // Helper neighbors
    public Board helperBoard(int currentRow, int currentCol, int moveRow, int moveCol) {
        Board tempBoard = new Board(this.board);

        try{
            tempBoard.board[currentRow][currentCol] = this.board[moveRow][moveCol];
            tempBoard.board[moveRow][moveCol] = 0;
        }
        catch(ArrayIndexOutOfBoundsException exception){
            return null;
        }

        return tempBoard;
    }
    
    // a board that is obtained by exchanging any pair of tiles    
    public Board twin() {
        Board tempBoard = new Board(this.board);

        // Do Random
        Random rand = new Random();
        int n = this.boardLength;
        int colA = rand.nextInt(n);
        int rowA = rand.nextInt(n);
        int colB = rand.nextInt(n);
        int rowB = rand.nextInt(n);

        tempBoard.board[colA][rowA] = this.board[colB][rowB];
        tempBoard.board[colB][rowB] = this.board[colA][rowA];

        return tempBoard;
    }
    
    // unit testing (not graded)    
    public static void main(String[] args) {

        // This is to ask which test to do
        ArrayList <String> question = new ArrayList <String>();

        question.add("Which Test to test?: \n");
        question.add("A = (Test 1 -> 4) \n");
        question.add("B = (Test 5) \n");
        question.add("C = (Test 7) \n");
        question.add("D = (Test 8) \n");
        question.add("E = (Test 10) \n");
        question.add("F = (Test 11) \n");

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

        for (String checker: equalsInput){
            if(input.equals(checker)) validInput = true;
        }

        if (validInput == false) return;

        // Create list of testing arguments 
        ArrayList<String> fileList = new ArrayList<String>(); 
        
            // Test A
            if (input.equals("A")){
                fileList.add("puzzle04.txt");
                fileList.add("puzzle00.txt");
                fileList.add("puzzle07.txt");
                fileList.add("puzzle17.txt");
                fileList.add("puzzle27.txt");
                fileList.add("puzzle2x2-unsolvable1.txt");
            }

            // Test B
            else if(input.equals("B") || input.equals("C")){
                fileList.add("puzzle04.txt");
                fileList.add("puzzle00.txt");
                if(input.equals("B")) fileList.add("puzzle06.txt");
                fileList.add("puzzle09.txt");
                fileList.add("puzzle23.txt");
                fileList.add("puzzle2x2-unsolvable1.txt");
            }

            // Test D
            else if(input.equals("D") ){
                fileList.add("puzzle00.txt");
                fileList.add("puzzle04.txt");
                fileList.add("puzzle16.txt");
                fileList.add("puzzle09.txt");
                fileList.add("puzzle23.txt");
                fileList.add("puzzle2x2-unsolvable1.txt");
                fileList.add("puzzle3x3-00.txt");
                fileList.add("puzzle4x4-00.txt");
            }

        // for each command-line argument
        for (String filename : fileList) {

            System.out.println("===============================");
            System.out.println("File:" + filename);

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

                // For Test A
                if(input.equals("A")){
                    System.out.println("Hamming: " + initial.hamming());
                    System.out.println("Manhattan: " + initial.manhattan());
                    System.out.println("Dimension: " + initial.dimension());
                    System.out.println("ToString: \n" + initial.toString());
                }

                // For Test B
                else if (input.equals("B")){

                    // Print each neightbour
                    Iterable<Board> listNeightbour = initial.neighbors();
                    System.out.println("Neightbours:");
                    for (Board tempBoard : listNeightbour){
                        System.out.println(" \n" + tempBoard.toString());
                    }
                }

                // Test C
                else if (input.equals("C")){
                    Board TestC = initial.twin();
                    System.out.println("Twin:");
                    System.out.println(" \n" + TestC.toString());
                }

                // Test D
                else if (input.equals("D")){
                    System.out.println("isGoal: " + initial.isGoal());
                }             
        }

        if (input.equals("E")){
            int [][] mainTiles = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } }; 
            
            Board Board1 = new Board(mainTiles);
            Board Board2 = new Board(mainTiles);
            Board Board3 = new Board(mainTiles);
            
            System.out.println("Reflexive Board1 = Board1: " + Board1.equals(Board1));
            
            System.out.println("Symmetric Board2 = Board1: " + Board1.equals(Board2));
            System.out.println("Symmetric Board1 = Board2: " + Board2.equals(Board1));
            
            System.out.println("Transitive Board1 = Board2: " + Board1.equals(Board2));
            System.out.println("Transitive Board2 = Board3: " + Board2.equals(Board3));
            System.out.println("Transitive Board1 = Board3: " + Board1.equals(Board3));


        }   
        
        else if (input.equals("F")){
            int [][] mainTiles = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } }; 
            
            Board Board1 = new Board(mainTiles);
            
            System.out.println("Before changing argument array: " + Board1.toString());
            
            mainTiles[0][0] = 3; 
            mainTiles[0][2] = 1; 

            System.out.println("After changing argument array: " + Board1.toString());
        } 
    }


}