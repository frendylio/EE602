// ================================
// = Created By: Frendy Lio
// = Date: November 7th 2020
// ================================
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import java.util.TreeSet;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class KdTree {

    // Lets create a Data Type Node
    // check x (if < go left; if > go right)
    // then you check y , then x and so on
    private static class Node{
        // Each note has a data type point
        private Point2D p;
        // Aslo ssotrage RectHV to help us later
        private RectHV rect;
        
        // Node Pointer
        private KdTree.Node left;
        private KdTree.Node right;

        // Initialize constructor
        public Node(Point2D p, RectHV rect){
            this.p = p;
            this.rect = rect;
            this.left = null;
            this.right = null;
        }
    }

    // Variables for KdTree
    private Node root;
    private int size;
    private RectHV rect = new RectHV(0,0,1,1);

    // construct an empty KdTree
    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    // is the KdTree empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points/Nodes in the KdTree
    public int size() {
        return this.size;
    }

    // add the point to the KdTree (if it is not already in the KdTree)
    public void insert(Point2D p) {
        try{
            if (p == null) 
                throw new IllegalArgumentException("NULL");
        }
        catch(IllegalArgumentException exception){
            StdOut.println("Error Insert:" + exception);
            return;
        }        

        // call helper function
        this.root = insert(this.root, p, true, this.rect.xmin(), this.rect.ymin(), this.rect.xmax(), this.rect.ymax());
        
        return;
    }

    //==============
    // Helper function to insert a new point in the tree
    // ==============
    private Node insert(Node node, Point2D p, Boolean isXChecker, double xmin, double ymin, double xmax, double ymax){

        // Base case if Node is null
        if (node == null){
            this.size = this.size + 1;
            return new Node(p, new RectHV(xmin,ymin,xmax,ymax));
        }

        // Check where to go left or right
        Boolean isItLeft = this.isItLeft(p, node.p, isXChecker);

        // If it is the same, isItLeft is null
        if (isItLeft == null) return null;

        // Go left
        else if (isItLeft == true){
            if (isXChecker == true) xmax = node.p.x();
            else ymax = node.p.y();

            // Go left node
            node.left = this.insert(node.left, p, !isXChecker, xmin, ymin, xmax, ymax);
        }

        // Go right
        else {
            if (isXChecker == true) xmin = node.p.x();
            else ymin = node.p.y();

            // Go right node
            node.right = this.insert(node.right, p, !isXChecker, xmin, ymin, xmax, ymax);
        }     

        return node;
    }

    // ============
    // Helper function to decide if we go left or right
    // =============
    private Boolean isItLeft(Point2D currentPoint, Point2D rootPoint, Boolean isXChecker){
        
        // If you are inserting the same point return null;
        if ( currentPoint.equals(rootPoint)) return null;

        // Check X
        else if (isXChecker == true){
            // go left 
            if (currentPoint.x() < rootPoint.x()) return true;
            // go right
            return false;
        }

        // Check right
        else {
            if (currentPoint.y() < rootPoint.y()) return true;
            else return false;
        }

    }

    // does the KdTree contain point p?
    public boolean contains(Point2D p) {
        try{
            if (p == null) 
                throw new IllegalArgumentException("NULL");
        }
        catch(IllegalArgumentException exception){
            StdOut.println("Error contains:" + exception);
            return false;
        }     

        return this.contains(this.root, p, true);
    }

    //==============
    // Helper function to search if the tree has the point
    // ==============
    private boolean contains(Node node, Point2D p, Boolean isXChecker){

        // Base case 
        if (node == null) return false;

        // Check where to go left or right
        Boolean isItLeft = this.isItLeft(p, node.p, isXChecker);        

        // If null
        if (isItLeft == null) return true;
        // Go Left
        else if (isItLeft){
            return this.contains(node.left, p, !isXChecker);
        }
        // Go right
        else{
            return this.contains(node.right, p, !isXChecker);
        }

    }

    // Show all points to standard Show 
    public void draw() {
        // Initialize drawing scale and parameters
        StdDraw.setScale();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        rect.draw();
        this.draw(this.root, true);
    }

    // ===================
    // Call helper function to draw
    // ===================
    private void draw(Node node, Boolean isXChecker){

        // Base case
        if (node == null) return;

        // Draw Points
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();

        // If xChecker (Vertical line so red)
        if (isXChecker){
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        // If horizontal line
        else{
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());            
        }

        // go left
        this.draw(node.left, !isXChecker);
        // go right
        this.draw(node.right, !isXChecker);

        return;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        try{
            if (rect == null || this.size == 0) 
                throw new IllegalArgumentException("NULL");
        }
        catch(IllegalArgumentException exception){
            StdOut.println("Error rect:" + exception);
            return null;
        }      

        // Do BFS
        ArrayList <Point2D> OutputList = new ArrayList<Point2D>();
        Queue <Node> VisitQueue = new Queue<Node>();

        // Base case
        if (this.root == null){
            return null;
        }

        VisitQueue.enqueue(this.root);

        // Visit till not empty
        while(!VisitQueue.isEmpty()){
            // Dequeue current one
            Node currentNode = VisitQueue.dequeue();

            // If it in in the range
            if (rect.contains(currentNode.p)){
                // Add it to our OutputList
                OutputList.add(currentNode.p);
            }

            // If it is not in range go left or right
            // From notes/assignment, if it does not intersect do not check at all
            if(currentNode.left != null && rect.intersects(currentNode.left.rect)){
                VisitQueue.enqueue(currentNode.left);
            }
            
            if(currentNode.right != null && rect.intersects(currentNode.right.rect)){
                VisitQueue.enqueue(currentNode.right);
            }            
        }

        return OutputList;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        try{
            if (p == null) 
                throw new IllegalArgumentException("NULL");
        }
        catch(IllegalArgumentException exception){
            StdOut.println("Error nearest:" + exception);
            return null;
        }

        // DO DFS
        return nearest(this.root, p, null);
    }

    // ===============
    // Helper DFS (Recursive)
    // ===============
    private Point2D nearest(Node node, Point2D currentPoint, Point2D nearestPoint){
        
        // Base case
        if(node == null) return nearestPoint;

        // At the start nearestPoint is unknown, start at the root.
        if(nearestPoint == null) nearestPoint = node.p;

        // Check if nearesPoint is the nearest
        if (nearestPoint.distanceSquaredTo(currentPoint) >= node.rect.distanceSquaredTo(currentPoint)){

            // If we are here means that our currentPoint is farther than the point to the root.
            // Check if we should move the nearestPoint to root
            if (node.p.distanceSquaredTo(currentPoint) < nearestPoint.distanceSquaredTo(currentPoint)){
                nearestPoint = node.p;
            }

            // Now do recursion
            // Go left and then right, if point not inside, no need to check
            if (node.left != null && node.left.rect.contains(currentPoint)){
                nearestPoint = this.nearest(node.left, currentPoint, nearestPoint);
                nearestPoint = this.nearest(node.right, currentPoint, nearestPoint);
            }

            // Go right and then left, if point not inside, no need to check  
            else if(node.right != null && node.right.rect.contains(currentPoint)){
                nearestPoint = this.nearest(node.right, currentPoint, nearestPoint);  
                nearestPoint = this.nearest(node.left, currentPoint, nearestPoint);             
            } 
            
        }
        // If you here, you are the end of the tree.
        return nearestPoint;        
    }


    // unit testing of the methods (optional)  
    public static void main(String[] args) {

        // This is to ask which test to do   
        ArrayList<Integer> fileToRead = new ArrayList <Integer> ();


        

        // This is to ask which test to do
        ArrayList <String> question = new ArrayList <String>();

        question.add("Which Test to test?: \n");
        question.add("A = (Test 2.1a) \n");
        question.add("B = (Test 2.1b -> 2.1d) \n");
        question.add("C = (Test 2.2a) \n");
        question.add("D = (Test 2.2b -> 2.2d) \n");
        question.add("E = (Test 2.3a) \n");
        question.add("F = (Test 2.3b -> 2.3d) \n");
        question.add("G = (Test 1.7) \n");

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

        for (String checker: equalsInput){
            if(input.equals(checker)) validInput = true;
        }
        
        if (validInput == false) return;

        // Create data for arrays
            if (input.equals("A") || input.equals("C") || input.equals("E")){
            // Used KdTreeGenerator.Java to create the files
                fileToRead.add(0);
                fileToRead.add(1);
                fileToRead.add(5);
                fileToRead.add(10);              
        
            // read in the board specified in the filename
            for (Integer n : fileToRead) {
                StdOut.println("==============resources/input" + n + ".txt===============");     
                In in = new In("resources/input" + n + ".txt");
                KdTree tempKdTree = new KdTree();
                Random rand = new Random();
                int m = 10;

                for (int i = 0; i < n; i++) {
                    double x = in.readDouble();
                    double y = in.readDouble();
                    Point2D tempPoint = new Point2D(x,y);

                    tempKdTree.insert(tempPoint);

                    //==============
                    // Test 2.1 a
                    // ==============
                    if (input.equals("A")){
                        StdOut.println("Size: " + tempKdTree.size());
                        StdOut.println("isEmpty: " + tempKdTree.isEmpty()); 
                    }
                                      
                }  
                
                //==============
                // Test 2.2 a
                // ==============  
                if (input.equals("C")){
                    // Loop 5 times
                    for(int k = 0; k < 5; k++){
                        // Get a random number from 0 and m
                        double tempX = (double)rand.nextInt(m+ 1)/m;
                        double tempY = (double)rand.nextInt(m+ 1)/m;

                        // Create your point
                        Point2D tempPoint = new Point2D(tempX, tempY);  
                        StdOut.println("Contains:" + tempKdTree.contains(tempPoint));
                    }
                }
                
                // ============
                // test 2.4a
                // ==========
                if (input.equals("E")){
                    // Loop 5 times
                    for(int k = 0; k < 5; k++){                    
                        // Get a random number from 0 and m
                        float tempXMax = (float)rand.nextInt(m+ 1)/m;
                        float tempYMax = (float)rand.nextInt(m+ 1)/m;
                        float tempX = (float)rand.nextInt(m+ 1)/m;
                        float tempY = (float)rand.nextInt(m+ 1)/m;                        
                        float tempLocation;
                        
                        if (tempXMax < tempX){
                            tempLocation = tempXMax;
                            tempXMax = tempX;
                            tempX = tempLocation;
                        }

                        if (tempYMax < tempY){
                            tempLocation = tempYMax;
                            tempYMax = tempY;
                            tempY = tempLocation;                       
                        }

                        // Create your point
                        RectHV tempHV = new RectHV(tempX, tempY, tempXMax, tempYMax);

                        Iterable<Point2D> listRange = tempKdTree.range(tempHV);
                        System.out.println("List of Point in Range:" + tempHV.toString());
                        if (listRange != null)
                        for (Point2D p : listRange){
                            System.out.println(" \n" + p.toString());
                        }
                    }
                }
            }  
        } 
        
        //==========================
        // Test 2.1b, 2.1c 2,1d
        //==========================
        else if (input.equals("B") || input.equals("D") || input.equals("F")){
            ArrayList<String> TempfileToRead = new ArrayList <String> ();
            // #points_GrdidSize_Type
            // At the start of each file, I tell them the number of point and the sizeof the matrix
            // 5 8
            // poin1
            // poin2 etc

            if (!input.equals("F")){
                // 2,1b
                TempfileToRead.add("1_1_nonDegenerate.txt");
                TempfileToRead.add("5_8_nonDegenerate.txt");
                TempfileToRead.add("10_16_nonDegenerate.txt");

                if(input.equals("B")) TempfileToRead.add("50_128_nonDegenerate.txt");
                if(input.equals("D")) TempfileToRead.add("20_32_nonDegenerate.txt");
                
                TempfileToRead.add("500_1024_nonDegenerate.txt");
                // 2.c
                TempfileToRead.add("1_1_distinct.txt");

                if(input.equals("B")){
                    TempfileToRead.add("10_8_distinct.txt");
                    TempfileToRead.add("20_16_distinct.txt");
                }

                if(input.equals("D")){
                    TempfileToRead.add("10_4_distinct.txt");
                    TempfileToRead.add("20_8_distinct.txt");  
                    TempfileToRead.add("10000_128_distinct.txt");                
                }

                // 2.1.d
                if(input.equals("B")){
                    TempfileToRead.add("5_1_general.txt");
                    TempfileToRead.add("10_4_general.txt");
                    TempfileToRead.add("50_8_general.txt");
                }

                if(input.equals("D")){
                    TempfileToRead.add("10000_1_general.txt");
                    TempfileToRead.add("10000_16_general.txt");  
                    TempfileToRead.add("10000_128_general.txt");                
                }      
            }      

            else{         
                TempfileToRead.add("3_4_nonDegenerate.txt");
                TempfileToRead.add("6_8_nonDegenerate.txt");
                TempfileToRead.add("10_16_nonDegenerate.txt");
            }

            // read in the board specified in the filename
            for (String file : TempfileToRead) {
                StdOut.println("==============resources/" + file + "===============");     
                In in = new In("resources/" + file);
                
                // First row is the number of record to loop
                int n = in.readInt();
                int m = in.readInt(); 
                Random rand = new Random();
                KdTree tempKdTree = new KdTree();
                for (int i = 0; i < n; i++) {
                    double x = in.readDouble();
                    double y = in.readDouble();
                    Point2D tempPoint = new Point2D(x,y);

                    tempKdTree.insert(tempPoint);
                    if (input.equals("B")){
                        StdOut.println("Size: " + tempKdTree.size());
                        StdOut.println("isEmpty: " + tempKdTree.isEmpty());                
                    }
                }  
                
                //==============
                // Test 2.2 b -> d
                // ==============  
                if (input.equals("D")){
                    // Loop 5 times
                    for(int k = 0; k < 5; k++){
                        // Get a random number from 0 and m
                        double tempX = (double)rand.nextInt(m+ 1)/m;
                        double tempY = (double)rand.nextInt(m+ 1)/m;

                        // Create your point
                        Point2D tempPoint = new Point2D(tempX, tempY);  
                        StdOut.println("Contains:" + tempKdTree.contains(tempPoint));
                    }
                }                 
            }             
        }

    }
}