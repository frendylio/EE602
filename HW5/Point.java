/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

/* ===============================
Modified By Frendy Lio
==================================*/
 
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Collections;
public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public static int GREATER = 1;
    public static int LOWER = -1;

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public Double slopeTo(Point that) { 
        try{
            Point dummy = new Point(1,1);
            if(this.x == dummy.x && this.y == dummy.y) ;
        }
        catch(NullPointerException NullPointerException){
            System.out.println("The main Point (This Point) provided is null");
            return null;
        }

        try{
            Point dummy = new Point(1,1);
            if(that.x == dummy.x && that.y == dummy.y) ;
        }
        catch(NullPointerException NullPointerException){
            System.out.println("The Point provided in the argument is null");
            return null;
        }
       
        // Horizontal Error:
        if ( this.y == that.y && this.x != that.x) return +0.0;

        //  Vertical Error
        else if ( this.x == that.x && this.y != that.y) return Double.POSITIVE_INFINITY;

        // Point equal
        else if ( this.x == that.x && this.y == that.y ) return Double.NEGATIVE_INFINITY;

        // Calculate slope
        return (double) (that.y - this.y)/(that.x - this.x);

    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {

        try{
            Point dummy = new Point(1,1);
            if(that.x == dummy.x && that.y == dummy.y) ;
        }
        catch(NullPointerException NullPointerException){
            System.out.println("The Point provided in the argument is null");
            return -5;
        }

        // Y check
        if(this.y > that.y) return this.GREATER;
        else if(this.y < that.y) return this.LOWER;

        // X Check
        else if(this.y == that.y && this.x > that.x) return this.GREATER;
        else if(this.y == that.y && this.x < that.x) return this.LOWER;

        // Error
        else return 0;

    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        final Point This_Point = this;
        return new Comparator<Point>(){

            @Override
            public int compare(Point that_1, Point that_2){

                try{
                    Point dummy = new Point(1,1);
                    if(that_1.x == dummy.x && that_1.y == dummy.y) ;
                    if(that_2.x == dummy.x && that_2.y == dummy.y) ;
                }
                catch(NullPointerException NullPointerException){
                    System.out.println("The Point provided in the argument is null");
                    return -5;
                }

                // 1 > 2
                if (This_Point.slopeTo(that_1) > This_Point.slopeTo(that_2)) return This_Point.GREATER;

                // 1 < 2
                else if (This_Point.slopeTo(that_1) < This_Point.slopeTo(that_2)) return This_Point.LOWER;

                // else
                else return 0;
            }
        };
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // Test slopeTo
        Point point = new Point(1,1);
        Point point_same = new Point(1,1);
        Point point_horizontal = new Point(2,1);
        Point point_vertical = new Point(1,2);
        Point point_that = new Point(4,4);
        Point point_smaller = new Point(0,0);
        Point point_2 = new Point(10,50);

        // =======================
        // MY OWN TESTING
        // =======================

        System.out.print("\n=========slopeTo==========");
        System.out.print("\nPoint Equal:" + point.slopeTo(point_same));
        System.out.print("\nPoint Horizontal:" + point.slopeTo(point_horizontal));
        System.out.print("\nPoint Vertical:" + point.slopeTo(point_vertical));
        System.out.print("\nPoint Slope:" + point.slopeTo(point_that));

        System.out.print("\n=========compareTo==========");
        System.out.print("\nPoint Equal:" + point.compareTo(point_same));
        System.out.print("\nThat Point Bigger:" + point.compareTo(point_horizontal));
        System.out.print("\nThat Point Smaller:" + point.compareTo(point_smaller));

        ArrayList<Point> ar = new ArrayList<Point>(); 
        ar.add(point_same);
        ar.add(point_horizontal);
        ar.add(point_vertical);
        ar.add(point_that);
        ar.add(point_smaller);
        ar.add(point_2);

        System.out.print("\n=========Comparator<Point> slopeOrder==========");

        Collections.sort(ar, point.slopeOrder()); 
  
        System.out.println("\nSorted by Slope"); 
        for (int i=0; i<ar.size(); i++) 
            System.out.println("This is the point:" + ar.get(i) + " == This is the slope:" + point.slopeTo(ar.get(i))); 
    
        // =======================
        // Readme
        // =======================        
        System.out.print("\n=========Test 1==========\n");
        Point P = new Point(1,2);
        Point Q = new Point(1,1);
        Point R = new Point(1,2);
        Point S = new Point(3,2);
        Point T = new Point(-1,-2);
        Point U = new Point(5,7);
        Point V = null;
        System.out.println("1.1: p =" + P + ", q = " + Q + ", slope = " + P.slopeTo(Q));
        System.out.println("1.2: p =" + P + ", q = " + R + ", slope = " + P.slopeTo(R));
        System.out.println("1.3: p =" + P + ", q = " + S + ", slope = " + P.slopeTo(S));
        System.out.println("1.4: p =" + P + ", q = " + T + ", slope = " + P.slopeTo(T));
        System.out.println("1.5: p =" + P + ", q = " + U + ", slope = " + P.slopeTo(U));
        System.out.println("1.6: p =" + P + ", q = " + V + ", slope = " + P.slopeTo(V));
        
        

        System.out.print("\n=========Test 2==========\n");
        P = new Point(1,1);
        Q = new Point(-1,-2);
        R = new Point(1,2);
        S = new Point(2,1);
        T = new Point(-1,-2);
        U = null;        
        System.out.println("2.1: p =" + P + ", q = " + P + ", compareTo = " + P.compareTo(P));
        System.out.println("2.2: p =" + R + ", q = " + S + ", compareTo = " + R.compareTo(S));
        System.out.println("2.4: p =" + R + ", q = " + S + ", r = " + Q + ", compareTo = " + R.compareTo(Q));
        System.out.println("2.5: p =" + P + ", q = " + T + ", compareTo positive (p.compareto(q)) = " + P.compareTo(T)+ ", compareTo negative (q.compareTo(p))= " + T.compareTo(P));
        System.out.println("2.6: p =" + P + ", q = " + U + ", compareTo = null" );  P.compareTo(U);       
        
        System.out.print("\n=========Test 3==========\n");
        P = new Point(1,1);
        Q = new Point(-1,-2);
        R = new Point(1,2);
        S = new Point(2,1);
        T = new Point(-1,-2);
        U = null;       

        System.out.println("3.1: p, r =" + P + ", q = " + P + ", slopeOrder().compare = " + P.slopeOrder().compare(P,P));
        System.out.println("3.2: p, r =" + R + ", q = " + S + ", slopeOrder().compare = " + R.slopeOrder().compare(R,S));
        System.out.println("3.3: p =" + R + ", q = " + S + ", r = " + Q + ", slopeOrder().compare = " + R.slopeOrder().compare(S,Q));
        System.out.println("3.4: p, r =" + P + ", q = " + T + ", compareTo negative (p.slopeOrder().compareto(r,t)) = " + P.slopeOrder().compare(P,T)+ ", compare positive (t.slopeOrder().compareto(p,r))= " + T.slopeOrder().compare(P,T));
        System.out.println("3.5: Argument 1 is empty" );  P.slopeOrder().compare(U,P);       
        System.out.println("3.5: Argument 2 is empty" );  P.slopeOrder().compare(P,U);
        System.out.println("3.5: Both arguments are empty" );  P.slopeOrder().compare(U,U);             
        
        return;        
    }
}
