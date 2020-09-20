package javacourse.sandbox;

public class PointDistance {

  public static void main(String[] args) {

    Point p1 = new Point(2, 5);
    Point p2 = new Point(0, -3.5);

    //calling function distance
    System.out.println("Distance between two points is " + distance(p1, p2));
  }

  // function for calculating distance between two points

  public static double distance(Point p1, Point p2) {
    return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
  }
}
