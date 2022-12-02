/**
   Pixel.java
   
   Stores information for pixel (rgb)
   
   @author Peter Olson
   @version 4/26/18
   @see ImageSetter.java
*/

public class Pixel {
       
   public static final int RED = 0, GREEN = 1, BLUE = 2, ALPHA = 3;
    
   // RGB color values for this pixel (0-255)
   public int[] rgb;
   public int[] rgba;
    
   /**
      Constructor for objects of class Pixel
      Initializes the pixel values
      
      @param red value for red portion of pixel
      @param green value for green portion of pixel
      @param blue value for blue portion of pixel
      @throws IllegalArgumentException if any of the parameters are not within the bounds of 0 - 255
   */
   public Pixel( int red, int green, int blue ) {
      if ( ( red < 0 || red > 255 ) || ( green < 0 || green > 255 ) || ( blue < 0 || blue > 255 ) )
         throw new IllegalArgumentException( "Bad RGB value for Pixel" );
        
      rgb = new int[3];
      rgb[ RED ] = red;
      rgb[ GREEN ] = green;
      rgb[ BLUE ] = blue;
   }
   
   /**
      Constructor for objects of class Pixel
      Initializes the pixel values
      
      @param red value for red portion of pixel
      @param green value for green portion of pixel
      @param blue value for blue portion of pixel
      @param alpha value for alpha portion of pixel
      @throws IllegalArgumentException if any of the parameters are not within the bounds of 0 - 255
   */
   public Pixel( int red, int green, int blue, int alpha ) {
      if ( ( red < 0 || red > 255 ) || ( green < 0 || green > 255 ) || ( blue < 0 || blue > 255 ) || ( alpha < 0 || alpha > 255 ) )
         throw new IllegalArgumentException( "Bad RGBA value for Pixel" );
        
      rgba = new int[4];
      rgba[ RED ] = red;
      rgba[ GREEN ] = green;
      rgba[ BLUE ] = blue;
      rgba[ ALPHA ] = alpha;
   }
}

