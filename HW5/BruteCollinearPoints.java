/* ===============================
Made By Frendy Lio

Running time n^4 because of the 4 for loops in BruteCollinearPoints

Space = n + line segment because of the sortedPoints which is an array of size n.
==================================*/
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;

    // ===========================================
    // finds all line segments containing 4 points
    // ===========================================   
    public BruteCollinearPoints(Point[] points){

        // Check Null
        try{
            checkNull(points);
        }
        catch(NullPointerException NullPointerException){
            System.out.println(NullPointerException);
            lineSegments = null;
            return;
        }
        // Sort Points from lower to higher to check for duplicates easier
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        // Check for Duplicates
        try{
            checkDuplicate(sortedPoints);
        }
        catch(IllegalArgumentException IllegalArgumentException){
            System.out.println(IllegalArgumentException);
            lineSegments = null;
            return;
        }        

        // BruteForce Algorithm
        // To check whether the 4 pointsp, q, r, and s are collinear,
        //  check whether the three slopes betweenp andq, betweenp andr,
        //  and betweenp ands are all equal.
        final int n = points.length;
        List<LineSegment> list = new LinkedList<>();


        for (int i = 0; i < n - 3; i++){
            Point Q = sortedPoints[i];

            for (int j = i + 1; j < n - 2; j++){
                Point R = sortedPoints[j];
                // First slope
                double slopeQR = Q.slopeTo(R);

                for (int k = j + 1; k < n -1; k++ ){
                    Point S = sortedPoints[k];
                    // Second slope
                    double slopeQS = Q.slopeTo(S);
                    
                    // If the First Slope == Seconds slope, we check for the third one
                    if (slopeQR == slopeQS){

                        for (int l = k + 1; l < n; l++){
                            Point T = sortedPoints[l];
                            // Third slope
                            double slopeQT = Q.slopeTo(T);

                            // If the slope same, add it to our list
                            if (slopeQR == slopeQT){
                                list.add(new LineSegment(Q,T));
                            }
                        }
                    }
                }

            }
        }

        lineSegments = list.toArray(new LineSegment[0]);

        return;

    }   

    // Helper for Algorithms
    public void checkNull(Point[] points){
        // If the whole list is null
        if (points == null){
            throw new NullPointerException("The List Point is empty");
        }

        // Check if there is a null inside the list.
        for (Point thisPoint: points){
            if( thisPoint == null){
                throw new NullPointerException("The List Point has an empty element");
            }
        }

        return;
    }

    // Since the list of point is sorted, we need to loop once and check the next index;
    public void checkDuplicate(Point[] points){
        for (int i = 0; i < points.length - 1; i++){
            if(points[i].compareTo(points[i + 1]) == 0){
                throw new IllegalArgumentException("There is a Duplicate");
            }
        }
        
        return;
    }

    // ===========================================
    // lineSegments
    // ===========================================
    public int numberOfSegments(){
         return this.lineSegments.length;
     }     
    
    // ===========================================
    // the line segments 
    // ===========================================
    public LineSegment[] segments(){
        return this.lineSegments.clone(); //create copy of the line segments
    }               

    // ===========================================
    // Sample Client provided by the assignment
    // ===========================================
    public static void main(String[] args) {  

        // read the n points from a file     
        In in = new In(args[0]);     
        int n = in.readInt();     
        Point[] points = new Point[n];     
        for (int i = 0; i < n; i++) {         
            int x = in.readInt();         
            int y = in.readInt();         
            points[i] = new Point(x, y);     
        }    
        
        // draw the points     
        StdDraw.enableDoubleBuffering();     
        StdDraw.setXscale(0, 32768);     
        StdDraw.setYscale(0, 32768);     
        for (Point p : points) {         
            p.draw();     
        }    
        StdDraw.show();     
        
        // print and draw the line segments     
        BruteCollinearPoints collinear = new BruteCollinearPoints(points); 
        for (LineSegment segment : collinear.segments()) {         
            StdOut.println(segment);         
            segment.draw();     
        }    
        StdDraw.show(); 

        // Test 9
        System.out.print("\n=========Test 9==========\n");
        System.out.print(collinear.numberOfSegments() + " segments\n");

        // Test 10
        System.out.print("\n=========Test 10==========\n");
        BruteCollinearPoints test = new BruteCollinearPoints(null);
        Point[] Test10points = points.clone();
        if(Test10points.length > 1){ 
            Test10points[1] = null;
            BruteCollinearPoints test2 = new BruteCollinearPoints(Test10points);
        }
        else{
            System.out.print("Cannot test NULL error because file has 1 point");
        }

        // Test 11
        System.out.print("\n=========Test 11==========\n");
        Point[] Test11points = points.clone();
        if(Test11points.length > 1){ 
            Test11points[0] = Test11points[1];    
            BruteCollinearPoints test3 = new BruteCollinearPoints(Test11points);
         }
         else{
             System.out.print("Cannot test Duplicate because file has 1 point");
         }    
    }    
}

