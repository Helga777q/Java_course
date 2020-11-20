import javacourse.sandbox.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PointDistanceTests {


  @Test
  public void distanceIntegerTest(){
    Point p1 = new Point(4,16);
    Point p2 = new Point (4,14);

    Assert.assertEquals(p1.distance(p2), 2.0);
  }

  @Test
  public void distanceDecimalTest(){
    Point p1 = new Point(3,5);
    Point p2 = new Point (-5,10);

    Assert.assertEquals(p1.distance(p2), 9.433981132056603);

  }


  @Test
  public void distanceLargeTest(){

    Point p1 = new Point(10000,50000);
    Point p2 = new Point (2,1);

    Assert.assertEquals(p1.distance(p2), 50988.82235353156);

  }


  @Test
  public void distanceZeroTest(){

    Point p1 = new Point(0,0);
    Point p2 = new Point (0,0);

    Assert.assertEquals(p1.distance(p2), 0.0);

  }

}
