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

public class PointSET {
    private TreeSet<Point2D> pointSet;

    // construct an empty set of points 
    public PointSET() {
        this.pointSet = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return this.pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        try{
            if (p == null) 
                throw new IllegalArgumentException("NULL");
        }
        catch(IllegalArgumentException exception){
            StdOut.println("Error Insert:" + exception);
            return;
        }        
        this.pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        try{
            if (p == null) 
                throw new IllegalArgumentException("NULL");
        }
        catch(IllegalArgumentException exception){
            StdOut.println("Error contains:" + exception);
            return false;
        }     

        return this.pointSet.contains(p);
    }

    // Show all points to standard Show 
    public void draw() {
        for (Point2D p : this.pointSet)
            StdDraw.point(p.x(), p.y());
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        try{
            if (rect == null) 
                throw new IllegalArgumentException("NULL");
        }
        catch(IllegalArgumentException exception){
            StdOut.println("Error rect:" + exception);
            return null;
        }      

        TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();
        for (Point2D p : this.pointSet) {
            if (rect.contains(p))
                rangeSet.add(p);
        }
        return rangeSet;
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

        Point2D nearestPoint = null;
        double minDistance = Double.MAX_VALUE;
        for (Point2D point : this.pointSet) {
            double distance = point.distanceTo(p);
            if (distance < minDistance) {
                nearestPoint = point;
                minDistance = distance;
            }
        }
        return nearestPoint;

        
    }

    // unit testing of the methods (optional)  
    public static void main(String[] args) {

        // This is to ask which test to do
        ArrayList <ArrayList<Integer>> Data = new ArrayList <ArrayList<Integer> >();    
        ArrayList<Integer> temp = new ArrayList <Integer> ();

        // Variablees
        float tempX;
        float tempY;
        Point2D tempPoint;
        PointSET tempPointSET = new PointSET();
        StdOut.println("isEmpty: " + tempPointSET.isEmpty());
        int m;
        int n;
        Random rand = new Random();
        

        // This is to ask which test to do
        ArrayList <String> question = new ArrayList <String>();

        question.add("Which Test to test?: \n");
        question.add("A = (Test 1.1) \n");
        question.add("B = (Test 1.2) \n");
        question.add("C = (Test 1.3) \n");
        question.add("D = (Test 1.4) \n");
        question.add("E = (Test 1.5) \n");
        question.add("F = (Test 1.6) \n");
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
        if (input.equals("A") || input.equals("B") || input.equals("E") || input.equals("F")){
            temp.add(5);
            temp.add(1);    
            Data.add(temp);

            temp = new ArrayList <Integer> ();
            temp.add(50);
            temp.add(8);    
            Data.add(temp);

            temp = new ArrayList <Integer> ();
            temp.add(100);
            temp.add(16);    
            Data.add(temp);

            temp = new ArrayList <Integer> ();
            temp.add(1000);
            temp.add(128);    
            Data.add(temp);                        
        }

        else if (input.equals("C")){
            temp.add(10);
            temp.add(4);    
            Data.add(temp);

            temp = new ArrayList <Integer> ();
            temp.add(20);
            temp.add(16);    
            Data.add(temp);

            temp = new ArrayList <Integer> ();
            temp.add(100);
            temp.add(32);    
            Data.add(temp);
        }

        else if (input.equals("D")){
            temp.add(2);
            temp.add(2);    
            Data.add(temp);

            temp = new ArrayList <Integer> ();
            temp.add(10);
            temp.add(4);    
            Data.add(temp);

            temp = new ArrayList <Integer> ();
            temp.add(20);
            temp.add(8);    
            Data.add(temp);

            temp = new ArrayList <Integer> ();
            temp.add(100);
            temp.add(16);    
            Data.add(temp);            
        }

        else if (input.equals("G")){
            temp.add(10000);
            temp.add(1);    
            Data.add(temp);
        }

        for (ArrayList<Integer> i : Data){
            m = i.get(1);
            n = i.get(0);

            StdOut.println("Points:" + n + "--- Size: " + m);

            ///////////////////
            // TEst 1.7
            ///////////////////
            if (input.equals("G")){
                // Loop 10000 tiems
                for(int j = 0; j < n; j++){

                    // Create random point
                    tempX = (float)rand.nextInt(m+ 1)/m;
                    tempY = (float)rand.nextInt(m+ 1)/m;
                    tempPoint = new Point2D(tempX, tempY);
                    
                    // CHeck your probability
                    // 0 -> 0.3 = insert()
                    // 0.3 -> 0.4 = isEmpty()
                    // 0.4 -> 0.5 = size()
                    // 0.5 -> 0.6 = contains()
                    // 0.6 -> 0.8 = range()
                    // 0.8 -> 1 = nearest()

                    float randomProbability = rand.nextFloat();
                    
                    if (randomProbability <= 0.3){
                        System.out.println("Inserting:");
                        tempPointSET.insert(tempPoint);
                    }
                    else if (randomProbability > 0.3 && randomProbability <= 0.4){
                        System.out.println("isEmpty:" + tempPointSET.isEmpty());
                    } 

                    else if (randomProbability > 0.4 && randomProbability <= 0.5){
                        System.out.println("isEmpty:" + tempPointSET.size());
                    }

                    else if (randomProbability > 0.5 && randomProbability <= 0.6){
                        System.out.println("isEmpty:" + tempPointSET.contains(tempPoint));
                    }

                    else if (randomProbability > 0.6 && randomProbability <= 0.8){
                        // Get a random number from 0 and m
                        float tempXMax = (float)rand.nextInt(m+ 1)/m;
                        float tempYMax = (float)rand.nextInt(m+ 1)/m;
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

                        Iterable<Point2D> listRange = tempPointSET.range(tempHV);
                        System.out.println("List of Point in Range:" + tempHV.toString());
                        for (Point2D p : listRange){
                            System.out.println(" \n" + p.toString());
                        }                        
                    }
                    
                    else{
                        System.out.println("isEmpty:" + tempPointSET.nearest(tempPoint));
                    }
                }
            }

            if (input.equals("G")){
                continue;
            }
            ///////////////////
            // TEst 1.5, before any insrets
            ///////////////////
            if (input.equals("E")){
                    StdOut.println("Size: " + tempPointSET.size());
                    StdOut.println("isEmpty: " + tempPointSET.isEmpty());
                    
                    // Get a random number from 0 and m
                    tempX = (float)rand.nextInt(m+ 1)/m;
                    tempY = (float)rand.nextInt(m+ 1)/m;

                    // Create your point
                    tempPoint = new Point2D(tempX, tempY);  
                    
                    // Contains
                    StdOut.println("Contains point:" + tempPoint.toString() + "---->" + tempPointSET.contains(tempPoint));
                    
                    // nearest
                    StdOut.println("Nearest point:" + tempPoint.toString() + "---->" + tempPointSET.nearest(tempPoint));
                

                    // Range
                    // Get a random number from 0 and m
                    float tempXMax = (float)rand.nextInt(m+ 1)/m;
                    float tempYMax = (float)rand.nextInt(m+ 1)/m;
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

                    Iterable<Point2D> listRange = tempPointSET.range(tempHV);
                    System.out.println("List of Point in Range:" + tempHV.toString());
                    for (Point2D p : listRange){
                        System.out.println(" \n" + p.toString());
                    }                    

                    break;
            }

            ///////////////////
            // TEst 1.5, before any insrets
            ///////////////////
            if (input.equals("F")){
                tempPointSET.insert(null);
                tempPointSET.contains(null);
                tempPointSET.nearest(null);
                tempPointSET.range(null);

                break;
            }     

            // Loop n numbers
            for(int j = 0; j < n; j++){
                // Get a random number from 0 and m
                tempX = (float)rand.nextInt(m+ 1)/m;
                tempY = (float)rand.nextInt(m+ 1)/m;

                // Create your point
                tempPoint = new Point2D(tempX, tempY);
                
                // Add to our PointSet
                tempPointSET.insert(tempPoint);

                /////////////////
                // Test 1.1
                /////////////////
                if (input.equals("A")){
                    StdOut.println("Size: " + tempPointSET.size());
                    StdOut.println("isEmpty: " + tempPointSET.isEmpty());
                }                
            }
            
            /////////////////
            // Test 1.2 and 1.3 and 1.4 just check 5 points
            /////////////////
            for(int k = 0; k < 5; k++){
                // Get a random number from 0 and m
                tempX = (float)rand.nextInt(m+ 1)/m;
                tempY = (float)rand.nextInt(m+ 1)/m;

                // Create your point
                tempPoint = new Point2D(tempX, tempY);  
                
                // Contains
                if (input.equals("B")){
                    StdOut.println("Contains point:" + tempPoint.toString() + "---->" + tempPointSET.contains(tempPoint));
                }
                
                // nearest
                else if (input.equals("C")){
                    StdOut.println("Nearest point:" + tempPoint.toString() + "---->" + tempPointSET.nearest(tempPoint));
                }

                // Range
                else if (input.equals("D")){
                    // Get a random number from 0 and m
                    float tempXMax = (float)rand.nextInt(m+ 1)/m;
                    float tempYMax = (float)rand.nextInt(m+ 1)/m;
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

                    Iterable<Point2D> listRange = tempPointSET.range(tempHV);
                    System.out.println("List of Point in Range:" + tempHV.toString());
                    for (Point2D p : listRange){
                        System.out.println(" \n" + p.toString());
                    }

                    break;
                }
            }
            // If you want to drwa it, just unconmment it.
            // tempPointSET.draw();
        }
    }
}