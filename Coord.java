/**
   Coord.java
   
   A coordinate object holding an x and y value, also usuable as a row and
   column value.
   
   @author Peter Olson
   @version 06/11/19
*/

public class Coord {

   private int x, y;
   
   public Coord( int x, int y ) {
      this.x = x;
      this.y = y;
   }

   public int getX() {
      return x;
   }
   
   public int getY() {
      return y;
   }
   
   public void setX( int x ) {
      this.x = x;
   }
   
   public void setY( int y ) {
      this.y = y;
   }
   
}