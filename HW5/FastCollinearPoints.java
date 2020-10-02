/* ===============================
Made By Frendy Lio


Running time n^2logn because of the one for loop (n) and the 2 while loops nlogn.

Space = n + line segment because of the sortedPoints which is an array of size n.
==================================*/
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    // ===========================================
    // finds all line segments containing 4 points
    // ===========================================   
    public FastCollinearPoints(Point[] points){

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

        //Fast algorithm
        // •Think of p as the origin.
        // •For each other point q, determine the slope it makes withp. 
        // •Sort the points according to the slopes they makes with p. 
        // •Check if any 3 (or more) adjacent points in the sorted order 
            // have equal slopes with respect top. 
            // If so, these points, together with p, are collinear.
        final int n = points.length;
        final List<LineSegment> list = new LinkedList<>();


        for (int i = 0; i < n; i++){
            Point P = sortedPoints[i];
            // Sort by slope
            Point[] slopePointArray = sortedPoints.clone();
            Arrays.sort(slopePointArray, P.slopeOrder());

            // •Check if any 3 (or more) adjacent points in the sorted order 
            // have equal slopes with respect top. 
            // If so, these points, together with p, are collinear.

            // We don't need to recheck the first element since the origin, thus -> start at 1
            int Q = 1;
            while (Q < n) {

                LinkedList<Point> PossiblePoints = new LinkedList<>();
                final double SlopePQ = P.slopeTo(slopePointArray[Q]);
                
                // Check if they have the same slope as PQ
                // If yes, add it to PossiblePoints List
                do {
                    PossiblePoints.add(slopePointArray[Q++]);
                } while (Q < n && P.slopeTo(slopePointArray[Q]) == SlopePQ);

                // If we have 3 PossiblePoints or more, then is collinear
                // Fine how many points are Coolinear 
                // Peek to see first element of list without remove
                // If organize, then P must be smaller than the others points
                if (PossiblePoints.size() >= 3 && P.compareTo(PossiblePoints.peek()) == P.LOWER) {
                    Point min = P;
                    // Get the last element from Possible Points
                    Point max = PossiblePoints.removeLast();
                    list.add(new LineSegment(min, max));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points); 
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
        FastCollinearPoints test = new FastCollinearPoints(null);
        Point[] Test10points = points.clone();
        
        if(Test10points.length > 1){ 
            Test10points[1] = null;
            FastCollinearPoints test2 = new FastCollinearPoints(Test10points);
        }
        else{
            System.out.print("Cannot test NULL error because file has 1 point");
        }

        // Test 11
        System.out.print("\n=========Test 11==========\n");
        Point[] Test11points = points.clone();
        if(Test11points.length > 1){ 
           Test11points[0] = Test11points[1];    
            FastCollinearPoints test3 = new FastCollinearPoints(Test11points);
        }
        else{
            System.out.print("Cannot test Duplicate because file has 1 point");
        }    
    }    
}

