/**
   ImageSetter.java

   Adjustable image that can be set to new images (useful for filters, drawing)

*/

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.Rectangle;

public class ImageSetter {

   private BufferedImage myImage;
   private int width;
   private int height;

   /**
      Construct a PixelImage from a Java BufferedImage
      
      @param bi The image
   */
   public ImageSetter( BufferedImage bi ) {
      this.myImage = bi;
      this.width = bi.getWidth();
      this.height = bi.getHeight();
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   /**
      Return the image's pixel data as an array of Pixels.  
      
      @return The array of pixels
   */
   public Pixel[][] getData() {

      /* The Raster has origin 0,0 , so the size of the 
         array is [width][height], where width and height are the 
         dimensions of the Raster
      */
      Raster raster = this.myImage.getRaster();
      Pixel[][] data = new Pixel[ raster.getHeight() ][ raster.getWidth() ];
      int[] samples = new int[4];

      // Translates from java image data to Pixel data
      for ( int row = 0; row < raster.getHeight(); row++ ) {
         for ( int col = 0; col < raster.getWidth(); col++ ) {
            raster.getPixel( col, row, samples );
            
            Pixel ourPixel;
            if( isTransparent( row, col ) )
               ourPixel = new Pixel( samples[0], samples[1], samples[2], samples[3] );
            else
               ourPixel = new Pixel( samples[0], samples[1], samples[2] );
               
            data[ row ][ col ] = ourPixel;
         }
      }
      
      return data;
   }

   /**
      Determines whether a pixel at a given location is an RGBA value (has an alpha channel (has transparency) ) or is an RGB value
      
      @param x The x location of the pixel
      @param y The y location of the pixel
      @return boolean True if the pixel is RGBA, false otherwise
      @see BufferedImage.getRGB( int x, int y )
      @see getData()
   */
   public boolean isTransparent( int x, int y ) {
      int pixel = myImage.getRGB( x, y );
      if( ( pixel>>24 ) == 0x00 ) {
         return true;
      }
      return false;
   }

   /**
      Set the image's pixel data from an array.  This array must match
      that returned by getData() in terms of dimensions. 
      
      @param data The array to pull from
      @throws IllegalArgumentException if the passed in array does not match
      the dimensions of the PixelImage or has pixels with invalid values
   */
   public void setData( Pixel[][] data ) throws IllegalArgumentException {
      WritableRaster wr = this.myImage.getRaster();

      if ( data.length != wr.getHeight() ) {
         throw new IllegalArgumentException( "Array size does not match" );
      } else if ( data[0].length != wr.getWidth() ) {
         throw new IllegalArgumentException( "Array size does not match" );
      }

      // Translates from an array of Pixel data to java image data
      for ( int row = 0; row < wr.getHeight(); row++ ) {
         for ( int col = 0; col < wr.getWidth(); col++ ) {
            if( isTransparent( row, col ) )
               wr.setPixel( col, row, data[ row ][ col ].rgba );
            else
               wr.setPixel( col, row, data[ row ][ col ].rgb );
         }
      }
      
   }
   
   /**
      Sets the image to a new size based on the dimensions of the rectangle
      
      @param box The Rectangle object containing the x and y coordinate of the upper left corner, and the width and height of the rectangle
      @see GraphicsBaseFrame.crop(..)
   */
   public void setSize( Rectangle box ) {
      myImage = new BufferedImage( box.width, box.height, myImage.getType() );
   }

   /**
      Get the BufferedImage of this image
      
      @return BufferedImage The BufferedImage of this ImageSetter image
   */
   public BufferedImage getImage() {
      return this.myImage;
   }
   
   public void setImage( BufferedImage newImage ) {
      this.myImage = newImage;
   }
}

