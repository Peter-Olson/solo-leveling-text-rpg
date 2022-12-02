/**
   GraphicsBaseFrame.java
   
   Simplest possible frame and drawing tools / methods to use Graphics to draw and manipulate images
   (Well, that was the intent. More could be scrapped away for simplicity)
   
   IN ACTUALITY, this is purely designed to take an image, create a copy and filter that copy, and then
   print the image next to the original to see how well the filter worked. In essence, this is a big test
   file for filters written, and is not at all designed well, but it works
   
   @author Peter Olson
   @version 6/2/19
   @see ImageSetter.java
   @see Pixel.java
   @see filter( ImageSetter image ) -- @@NOTE: Adjust this method for controlling what filters are used
*/

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.MediaTracker;
import java.awt.Graphics2D;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.awt.Color;
import java.awt.AlphaComposite;
import java.awt.Rectangle;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.Point;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.Point;
import javax.swing.BorderFactory;
import java.awt.Insets;
import java.awt.FlowLayout;

public class GraphicsBaseFrame extends JPanel {

   //protected JFrame win;
   private ImagePanel ip;
   
   /*
      Available files:
         flags.jpg, fry_meme.jpg, heavy_breathing.jpg, player_right.png, boromir_meme.jpg, balloons.jpg
   */
   public String fileName = "rose.jpeg"; //boromir_meme.jpg
   public String secondFileName = "heavy_breathing.jpg"; //heavy_breathing.jpg
   public String fileNameFiltered = "";      //fileName of filtered image --> set in saveImage( BufferedImage image )
   
   public boolean filtered = false; //Used to tell if a filter is used on the image, so that the name can of the save file can be adjusted
   public boolean latticeImage = false; //Used to tell if an image has multiple saves in a method or not -- used for naming conventions
   
   public int imageWidth;
   public int imageHeight;
   public final int BUFFER = 20;
   
   public int index;
   public boolean save = true;
   
   /*
      //@@NOTE: Add this back for independent use
   
   public static void main( String[] args ) {
      GraphicsBaseFrame base = new GraphicsBaseFrame();
   }
   */
   
   /**
      Create a JFrame and ImagePanel to display images.
      
      Draw the original images, apply filters, and save the filtered image
      
      @see ImagePanel private class
      @see drawImage( String fileName )
      @see filterNewImage( String fileName )
      @see saveImage( BufferedImage image )
   */
   public GraphicsBaseFrame() {
      /* @@NOTE: DUAL-SETTINGS FOR THIS FILE -- THIS FILE CAN BE RUN INDEPENDENTLY, OR THROUGH A PROGRAM.
      
                 If run independently, uncomment the code for the JFrame below and for the
                 main method, as well as the win. calls, the code below, and the global instance of the JFrame window.
                 
                 If running through a program, use this file as a JPanel and add it to your container. Additionally, this file should extend JPanel and all of the win. should be removed
      */
      /*
         @@NOTE: Add this back for independent use; also, the main method, and all the win.repaint() calls
      
      win = new JFrame( "Graphics Drawing Space" );

      win.setPreferredSize( new Dimension( 1500, 1000 ) );

      ip = new ImagePanel( this );
      win.add( ip, BorderLayout.CENTER );

      win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      win.pack();
      win.setVisible( true );
      */ip = new ImagePanel( this ); // this is needed from the commented section above
      add( ip );
      setLocation( new Point(0,0) );
      setSize( new Dimension( GameWindow.getWindowWidth(), GameWindow.getWindowHeight() ) );
      //setPreferredSize( new Dimension( WIDTH, HEIGHT ) );
      //setFocusable( true );
      //requestFocus();
      
      fileName = getTextImage( "Testing bruh", "Grobold", Font.PLAIN, 30, "Testing bruh image" );
      
      drawImage( fileName );                           //draws image to panel
      //drawNewImage( fileName );                       //functions draw on top of image instead of changing pixels
      //filterNewImage( fileName, secondFileName );   //functions change pixels of actual image
      
      /*
         @NOTE if using drawNewImage and filterNewImage:
         
         If drawing over an image and then filtering, keep in mind that the image saved is saved to the secondFileName
         file, so if drawing over an image and then filtering, the filename file should be the name of the drawn over
         file, which would in this case be 'secondFileName', so you would want to pass in secondFileName as the first
         parameter, not fileName
      */
   }
   
   public GraphicsBaseFrame( int width, int height ) {
      ip = new ImagePanel( this ); // this is needed from the commented section above
      ip.setLayout( new FlowLayout( FlowLayout.CENTER ) );
      add( ip, FlowLayout.CENTER );
      setLocation( new Point(0,0) );
      setSize( new Dimension( width, height ) );
      setLayout( new FlowLayout( FlowLayout.CENTER ) );
      setOpaque( true );
      setBackground( Color.BLACK );
      //setBorder( BorderFactory.createLineBorder( Color.black, 5 ) );
      
      fileName = getTextImage( "The sound of your sister giggling alerts you to the fact\nthat there’s a world outside the dishes you’re"
                             + " currently washing.\n\nJin-Ah looks up at you, a wide smile on her face.\nYou always wonder where she gets"
                             + " the energy to bounce\naround after a long day of school.\nOr the joyfulness. You’re glad she’s here.", "Arial", Font.PLAIN, 45, "Testing bruh image" );
      
      //@@DEBUG
      /*
      File tmpDir = new File( fileName );
      boolean exists = tmpDir.exists();
      SOPln( "Working Directory = " + System.getProperty( "user.dir" ) );
      */
      
      BufferedImage img = null;
      try {
         img = ImageIO.read( new File( fileName ) );
      } catch( IOException e ) {
         System.out.println( "Error: " + e );
      }
      //SOPln( "img width: " + img.getWidth() + "height: " + img.getHeight() );
      ip.setSize( new Dimension( img.getWidth(), img.getHeight() ) );
      ip.setPreferredSize( new Dimension( img.getWidth(), img.getHeight() ) );
      ip.setBackground( Color.BLACK );
      
      drawImage( fileName );                           //draws image to panel
      //drawNewImage( fileName );                       //functions draw on top of image instead of changing pixels
      //filterNewImage( fileName, secondFileName );   //functions change pixels of actual image
      
      /*
         @NOTE if using drawNewImage and filterNewImage:
         
         If drawing over an image and then filtering, keep in mind that the image saved is saved to the secondFileName
         file, so if drawing over an image and then filtering, the filename file should be the name of the drawn over
         file, which would in this case be 'secondFileName', so you would want to pass in secondFileName as the first
         parameter, not fileName
      */
   }
   
   public GraphicsBaseFrame( int x, int y, int width, int height, String text, String fontName, int fontType, int fontSize, String imageName, int index ) {
      this.index = index;
      this.save = save;
   
      ip = new ImagePanel( this ); // this is needed from the commented section above
      ip.setLayout( null );
      add( ip );
    
      Insets insets = getInsets();
      Dimension size = new Dimension( width, height );
      ip.setBounds( 0, 0, size.width, size.height );
      
      //@@DEBUG
      //ip.setBorder( BorderFactory.createLineBorder( Color.YELLOW ) );
      
      repaint();

      fileName = getTextImage( text, fontName, fontType, fontSize, imageName );
      
      BufferedImage img = null;
      try {
         img = ImageIO.read( new File( fileName ) );
      } catch( IOException e ) {
         System.out.println( "Error: " + e );
      }
      
      //@@DEBUG
      //SOPln( "img width: " + img.getWidth() + "height: " + img.getHeight() );
      
      imageWidth = img.getWidth();
      imageHeight = img.getHeight();
      
      Insets insets2 = getInsets();
      Dimension size2 = new Dimension( imageWidth, imageHeight );
      setBounds( x + insets2.left, y + insets2.top, size2.width, size2.height );
      
      setLayout( null );
      setOpaque( true );
      ip.setOpaque( true );
      setBackground( Color.BLACK );
      ip.setBackground( Color.BLACK );

      
      //@@DEBUG
      //setBorder( BorderFactory.createLineBorder( Color.YELLOW ) );

      repaint();
      
      drawImage( fileName );                            //draws image to panel
      //drawNewImage( fileName );                       //functions draw on top of image instead of changing pixels
      //filterNewImage( fileName, secondFileName );     //functions change pixels of actual image

      /*
         @NOTE if using drawNewImage and filterNewImage:
         
         If drawing over an image and then filtering, keep in mind that the image saved is saved to the secondFileName
         file, so if drawing over an image and then filtering, the filename file should be the name of the drawn over
         file, which would in this case be 'secondFileName', so you would want to pass in secondFileName as the first
         parameter, not fileName
      */
   }
   
   public int getImageWidth() {
      return imageWidth;
   }
   
   public int getImageHeight() {
      return imageHeight;
   }
   
   /**
      Controls what filters are applied to the image. Comment out or uncomment filter calls to use
      
      @param image The ImageSetter object to filter (essentially, an image whose pixels are accessible for direct modification)
      @param image2 The ImageSetter object that is used in conjunction with another image to create a filter
      @see additionFilter( ImageSetter image )
      @see bitwiseANDFilter( ImageSetter image )
      @see flipHorizontalFilter( ImageSetter image )
      @see flipVerticalFilter( ImageSetter image )
      @see gaussianFilter( ImageSetter image )
      @see grayFilter( ImageSetter image )
      @see inverseFilter( ImageSetter image )
      @see laplacianFilter( ImageSetter image )
      @see rotateCounterClockwiseFilter( ImageSetter image )
      @see rotateClockwiseFilter( ImageSetter image )
      @see sharpenFilter( ImageSetter image );
   */
   private void filter( ImageSetter image, ImageSetter image2 ) {
      /* @@@@@@ UNCOMMENT TO USE @@@@@@@@ */
      /* @@@@@@ NOTE: filtered boolean var used so that original file is not overwritten @@@@@ */
      
      //additionFilter( image ); filtered = true;
      //bitwiseANDFilter( image, image2 ); filtered = true;
      //flipHorizontalFilter( image ); filtered = true;
      //flipVerticalFilter( image ); filtered = true;
      //gaussianFilter( image ); filtered = true;
      //grayFilter( image ); filtered = true;
      //inverseFilter( image ); filtered = true;
      //laplacianFilter( image ); filtered = true;
      //rotateCounterClockwiseFilter( image ); filtered = true; // @@NOTE: ONLY CAN BE USED ON SQUARE IMAGES (for now)
      //rotateClockwiseFilter( image ); filtered = true;   // @@NOTE: ONLY CAN BE USED ON SQUARE IMAGES (for now)
      //sharpenFilter( image ); filtered = true;
      //layerImage( image, image2, 100, 0 ); filtered = true;
      /*@NOTE: second parameter is blurriness 'strength'. 9 = gaussian blur, 400 = strongly blurred
               Also, strength should be a perfect square eg. 9, 25, 36, 49, 64, 100, 225, 400
      */
      blurFilter( image, 100.0f ); filtered = true;
      
      /*win.*/repaint();
   }
   
   /**
      Method that controls what drawing methods are called and which aren't.
      For any methods that you do not want to use, comment them out, and for whatever methods that you want to use,
      uncomment them.
      
      @see rotate( double theta )
      @see outlineBoundingBox( Color c )
   */
   private void drawOver( ) {
      /* @@@@@@ UNCOMMENT TO USE @@@@@@@@ */
      //rotate( 45.0 ); filtered = true;              /* @@@@@@ NOTE: filtered boolean var used so that original file is not overwritten @@@@@ */
      //cropImage( new Rectangle( 100, 100, 150, 150 ) ); filtered = true;
      //outlineBoundingBox( Color.YELLOW ); filtered = true;
      //setTransparency( 0.5f ); filtered = true; //value must be between 0.0f and 1.0f
      cropLatticeImage( new Rectangle( 0, 0, 192, 192 ) ); filtered = true; latticeImage = false;
   }
   
   /**
      Draws the original image to the screen (or whatever image is set to ip.bi)
      
      @param fileName The file to draw
   */
   public void drawImage( String fileName ) {
      updateImages( fileName );
      /*win.*/repaint();
   }
   
   /**
      Helper method used to avoid deep-copy issues, and sets image of original file
      
      @param fileName The nameo of the image to set
      @see drawImage( String fileName )
   */
   private void updateImages( String fileName ) {
      BufferedImage temp = getImage( fileName );
      fileName = makeCopy( temp ); //avoids deep copy issues by setting original image to a saved copy of the first image
      
      ip.bi = getImage( fileName );
      ip.bi = convertRGBAtoRGB( ip.bi, fileName );
   }
   
   /**
      Converts a RGB BufferedImage to a RGBA BufferedImage. Note that in place of transparent pixels, black pixels are drawn
      
      @param rgba The RGBA image to convert
      @param fileName The new file to create as an RGB image
      @return BufferedImage The new image
      @see BufferedImage.getWidth()
      @see BufferedImage.getHeight()
      @see BufferedImage.createGraphics()
      @see Graphics.drawImage( BufferedImage image, int x, int y, int width, int height, ImageObserver observer )
      @see ImageIO.write( BufferedImage image, String extension, File file )
   */
   public BufferedImage convertRGBAtoRGB( BufferedImage rgba, String fileName ) {
      BufferedImage newRGB = new BufferedImage( rgba.getWidth(), rgba.getHeight(), BufferedImage.TYPE_INT_RGB );
      newRGB.createGraphics().drawImage( rgba, 0, 0, rgba.getWidth(), rgba.getHeight(), null );
      try {
         ImageIO.write( newRGB , "PNG", new File( fileName ) );
      } catch ( IOException e ) {}
      
      return newRGB;
   }
   
   /**
      Adds a filter to an image by changing the actual pixels of the image and saves the image
      Note that this method takes in two file names for filters that use the combination of two images, so if a filter is being used
      that just includes one image, any file name can be used for the second image and nothing will be affected unless you are using 
      a method that requires two images to add a filter (such as bitwiseADDFilter(..))
      
      @param fileName The name of the original file to filter
      @param fileName2 The name of the second file to use in case the filter method uses two images
      @see ImagePanel.filterImage() -- private class method that draws and sets image
      @see saveImage( BufferedImage image )
   */
   public void filterNewImage( String fileName, String fileName2 ) {
      String name = filtered ? fileNameFiltered : fileName; //Pull right file name --> don't want original if file has been drawn on
      ip.bi2 = getFilterImage( name );
      ip.bi3 = getFilterImageSecond( fileName2 );
      ip.filterImage();
      /*win.*/repaint();
      saveImage( ip.bi2 );
   }
   
   /**
      Draw on top of the image instead of changing its pixels and then save that image
      
      @param fileName The name of the file to draw onto
      @see ImagePanel.paintImage() - private class method that calls the drawing methods and sets the image
      @see saveImage( BufferedImage )
   */
   public void drawNewImage( String imageName ) {
      BufferedImage temp = getImage( imageName );
      imageName = makeCopy( temp );
   
      ip.bi2 = getFilterImage( imageName );
      ip.paintImage();
      /*win.*/repaint();
      saveImage( ip.bi2 ); //save once drawing is complete
   }
   
   /**
      Adds a filter to an image and overwrites the image instead of creating a new one; saves the result over the old image file too
      
      @see ImagePanel.applyFilter() -- private class method
      @see filterNewImage( String fileName, String fileName2 ) -- method that adds a filter but creates a new image
      @see saveImage( BufferedImage image )
   */
   public void drawCurrentFilter() {
      ip.applyFilter();
      /*win.*/repaint();
      saveImage( ip.bi );
   }
   
   /**
      Makes a copy of the image and saves it, appending "_copy" to the name (and before the extension).
      Used to avoid deep copy issues, particularly in using BufferedImage.subimage(..)
      
      @param image The image to copy and save
      @see drawImage( String fileName )
      @see ImageIO.write(..)
   */
   public String makeCopy( BufferedImage image ) {
      String[] fileSplit = fileName.split( "\\." );
      String extension = "." + fileSplit[ fileSplit.length - 1 ];
      
      //Automatically name file based on filters applied
      String name = "";
      //in case fileName has multiple periods... eg. flags.market.street.jpg [is that even a valid file name?]
      for( int i = 0; i < fileSplit.length - 1; i++ )
         name += fileSplit[i];
      
      //add to name to not overwrite original file
      if( !name.contains("_copy") )
         name += "_copy";

      name += extension; //add extension to fileName
      fileName = name;
   
      extension = extension.replaceAll( "\\.", "" ); //extension needs to not have period for ImageIO.write call...
      
      try {
         ImageIO.write( image, extension, new File( name ) );
      } catch ( IOException e ) {
         System.out.println( e.getMessage() );
      }
      
      return fileName;
   }
   
   /**
      Saves the current state of the image to a the original file or a new file (depends on whether the image has been filtered
      or not, which is saved in the global boolean variabe 'filtered'
      
      @param image The image to save
      @see ImageIO.write( BufferedImage image , String imageType, File file )
      @see filtered -- global variable that determines whether original file is overwritten or not
   */
   public void saveImage( BufferedImage image ) {
   
      String[] fileSplit = fileName.split( "\\." );
      String extension = "." + fileSplit[ fileSplit.length - 1 ];
      
      //Automatically name file based on filters applied
      String name = "";
      //in case fileName has multiple periods... eg. flags.market.street.jpg [is that even a valid file name?]
      for( int i = 0; i < fileSplit.length - 1; i++ )
         name += fileSplit[i];
      
      //add to name to not overwrite original file
      if( filtered )
         name += "_Filtered";
      
      //add to name for lattice images (multiple images saved per function call)
      if( latticeImage ) {
         name += "_" + latticeRow + "_" + latticeCol;
         name = name.replace("copy_", "");
      }

      name += extension; //add extension to fileName
      fileNameFiltered = name;
   
      extension = extension.replaceAll( "\\.", "" ); //extension needs to not have period for ImageIO.write call...
      
      try {
         ImageIO.write( image, extension, new File( name ) );
      } catch ( IOException e ) {
         System.out.println( e.getMessage() );
      }
   }
   
   /**
      Given a String text and the text's properties, create and save an image file of that text
      
      @param text The text to create an image out of
      @param fontName The name of the font
      @param FONT_TYPE The type of font; Eg: Font.PLAIN, Font.BOLD, Font.ITALIC
      @param fontSize The size of the font, in pixels
      @param fileName The name of the file to be saved
      @return String The name of the new file saved
      @see getTextImage(...) <see below>
      @see BufferedImage.createGraphics()
      @see Graphics2D.setFont( Font font )
      @see Graphics2D.getFontMetrics()
      @see FontMetrics.stringWidth( String text )
      @see FontMetrics.getHeight()
      @see Graphics2D.dispose()
      @see Graphics2D.setRenderingHint(...)
      @see Graphics2D.setColor( Color c )
      @see Graphics2D.drawString( String text, int x, int y )
      @see FontMetrics.getAscent()
      @see ImageIO.write( BufferedImage img, String extension, File file )
      @see IOException.printStackTrace()
   */
   public String getTextImage( String text, String fontName, int FONT_TYPE, int fontSize, String fileName ) {
      final int TEXT_BOX_BUFFER = 20;
      final String TAB_SPACES = "     ";
   
      text = text.replaceAll( "\\\\t", TAB_SPACES );
   
      // Because font metrics is based on a graphics context, create a small, temp image to ascertain the width and height of the final image
      BufferedImage img = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );
      Graphics2D g2d = img.createGraphics();
      Font font = new Font( fontName, FONT_TYPE, fontSize );
      g2d.setFont( font );
      FontMetrics fm = g2d.getFontMetrics();
      int width = getTextWidth( fm, text ) + TEXT_BOX_BUFFER;
      int height = fm.getHeight()*( text.split("\\\\n").length ) + TEXT_BOX_BUFFER;
      g2d.dispose();
      
      img = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
      g2d = img.createGraphics();
      g2d.setRenderingHint( RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY );
      g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
      g2d.setRenderingHint( RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY );
      g2d.setRenderingHint( RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE );
      g2d.setRenderingHint( RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON );
      g2d.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
      g2d.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
      g2d.setRenderingHint( RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE );
      g2d.setFont( font );
      fm = g2d.getFontMetrics();
      g2d.setColor( Color.WHITE );
      drawString( g2d, text, 0, 0 );
      g2d.dispose();
      try {
         ImageIO.write( img, DEFAULT_EXTENSION, new File( fileName + "." + DEFAULT_EXTENSION ) );
      } catch ( IOException ex ) {
         ex.printStackTrace();
      }
      
      return fileName + "." + DEFAULT_EXTENSION;
   }
      final String DEFAULT_EXTENSION = "png";
      final String DEFAULT_FONT = "Arial";
      final int DEFAULT_FONT_TYPE = Font.PLAIN;
      final int DEFAULT_FONT_SIZE = 12;
   public String getTextImage( String text, String fontName, int FONT_TYPE, int fontSize ) { return getTextImage( text, fontName, FONT_TYPE, fontSize, text + "" ); }
   public String getTextImage( String text, String fontName, int fontSize, String fileName ) { return getTextImage( text, fontName, DEFAULT_FONT_TYPE, fontSize, fileName ); }
   public String getTextImage( String text, int FONT_TYPE, int fontSize, String fileName ) { return getTextImage( text, DEFAULT_FONT, FONT_TYPE, fontSize, fileName ); }
   public String getTextImage( String text, String fileName ) { return getTextImage( text, DEFAULT_FONT, DEFAULT_FONT_TYPE, DEFAULT_FONT_SIZE, fileName ); }
   
   /**
      Since Graphics2D.drawString doesn't handle new line characters, create individually drawn lines occuring at each line break character
      
      @param g The graphics object
      @param text The text to draw
      @param x The starting x value location of the text
      @param y The starting y value location of the text
      @see Graphics2D.drawString( String text, int x, int y )
      @see String.split( String delimiter )
      @see getTextImage( String text, String fontName, int FONT_TYPE, int fontSize, String fileName )
   */
   private void drawString( Graphics g, String text, int x, int y ) {
      for( String line : text.split("\\\\n") )
         g.drawString( line, x, y += g.getFontMetrics().getHeight() );
   }
   
   /**
      Get the widest line in the paragraph of text and return the width of that line
      
      @param fm The FontMetrics of the text
      @param text The text to get the width of
      @return int The width of the longest line, in pixels
      @see FontMetrics.stringWidth( String text )
      @see String.split( String delimiter )
      @see getTextImage( String text, String fontName, int FONT_TYPE, int fontSize, String fileName )
   */
   private int getTextWidth( FontMetrics fm, String text ) {
      int widestValue = 0;
      //SOPln( "Text is: " + text );
      String[] splitLines = text.split( "\\\\n" );
      for( int i = 0; i < splitLines.length; i++ )
         widestValue = fm.stringWidth( splitLines[i] ) > widestValue ? fm.stringWidth( splitLines[i] ) : widestValue;
         
      return widestValue;
   }
   
   /**
      Helper function that sets the image
      
      @param theta The angle to rotate the image by
      @see rotateImage( double theta )
   */
   public void rotate( double theta ) {
      ip.bi2 = rotateImage( theta );
      saveImage( ip.bi2 );
   }
   
   /**
      Rotates an image by a given angle
      
      @param theta The angle to rotate the image by
      @return BufferedImage The rotated image
      @see rotate( double theta )
   */
   private BufferedImage rotateImage( double theta ) {
      //  Determine the size of the rotated image
      double cos = Math.abs( Math.cos( theta ) );
      double sin = Math.abs( Math.sin( theta ) );
      double width, height;
      // Make sure to get data from right image
      if( filtered ) {
         width = ip.bi2.getWidth();
         height = ip.bi2.getHeight();
      } else {
         width  = ip.bi.getWidth();
         height = ip.bi.getHeight();
      }
      
      int w = (int)( width * cos + height * sin );
      int h = (int)( width * sin + height * cos );

      //  Rotate and paint the original image onto a BufferedImage
      BufferedImage out = new BufferedImage( w, h, ip.bi2.getType() );
      Graphics2D g2 = out.createGraphics();
      
      g2.setPaint( UIManager.getColor( "Panel.background" ) );
      g2.fillRect( 0, 0, w, h );
      
      double x = w / 2;
      double y = h / 2;
      AffineTransform at = AffineTransform.getRotateInstance( theta, x, y );
      x = ( w - width ) / 2;
      y = ( h - height ) / 2;
      at.translate( x, y );
      
      g2.drawRenderedImage( ip.bi2, at );
      
      g2.dispose();
      
      return out;
   }
   
   /**
      Helper function that sets the image
      
      @param c The color of the border
      @see outlineBB( Color c )
   */
   public void outlineBoundingBox( Color c ) {
      ip.bi2 = outlineBB( c );
      saveImage( ip.bi2 );
   }
   
   /**
      Paint a thin border around the edge of an image
      
      @param c The color of the border to paint
      @return BufferedImage The resulting image with the border around it
      @see outlineBoundingBox( Color c )
      @see BORDER_WIDTH_PERCENT Determines width of border
   */
   private BufferedImage outlineBB( Color c ) {
      final int BORDER_WIDTH_PERCENT = 20; //determines the width of the border equal to the average of the width and height divided by this value
      int width, height;
      //Make sure to get data from right image
      if( filtered ) {
         width = ip.bi2.getWidth();
         height = ip.bi2.getHeight();
      } else {
         width = ip.bi.getWidth();
         height = ip.bi.getHeight();
      }
      
      BufferedImage out = new BufferedImage( width, height, ip.bi2.getType() );
      Graphics2D g2 = out.createGraphics();
      
      g2.setPaint( UIManager.getColor( "Panel.background" ) ); //set paint to image
      g2.fillRect( 0, 0, width, height );
      g2.drawRenderedImage( ip.bi2, new AffineTransform() ); //paint image
      
      g2.setColor( c );
      int borderWidth = ( (width + height) / 2 ) / BORDER_WIDTH_PERCENT;
      g2.fillRect( 0, 0, width, borderWidth );
      g2.fillRect( width - borderWidth, 0, borderWidth, height );
      g2.fillRect( 0, height - borderWidth, width, height );
      g2.fillRect( 0, 0, borderWidth, height );
      
      g2.dispose();
      
      return out;
   }
   
   /**
      Helper functions that draws the image and sets it
      
      @param percent The percent transparent to set the image to. Range is from (invisible) 0.0f to 1.0f (opaque)
      @see transparency( float percent )
   */
   public void setTransparency( float percent ) {
      ip.bi2 = transparency( percent );
      saveImage( ip.bi2 );
   }
   
   /**
      Set the transparency of an image
      
      @param percent The percent transparent to set the image to. Range is from (invisible) 0.0f to 1.0f (opaque)
      @return BufferedImage The transparent image
      @see Graphics2D.setComposite( Composite comp )
   */
   private BufferedImage transparency( float percent ) {
      int width, height;
      //Make sure to get data from right image
      if( filtered ) {
         width = ip.bi2.getWidth();
         height = ip.bi2.getHeight();
      } else {
         width = ip.bi.getWidth();
         height = ip.bi.getHeight();
      }
      
      BufferedImage out = new BufferedImage( width, height, ip.bi2.getType() );
      Graphics2D g2 = out.createGraphics();
      g2.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, percent ) );
      g2.drawImage( ip.bi2, 0, 0, width, height, null );
      
      g2.dispose();
      
      return out;
   }
   
   /**
      Method that crops the image and saves the result
      
      @param src The ImageSetter object for this image
      @param rect The crop boundaries held in a Rectangle object. A rectangle is defined by ( x, y, w, h ), which corresponds to
                  top left x coordinate, top left y coordinate, width of rectangle, height of rectangle
      @see crop( BufferedImage image, Rectangle rect )
      @see saveImage( BufferedImage image )
   */
   public void cropImage( Rectangle rect ) {
      ip.bi2 = crop( new ImageSetter( ip.bi2 ), rect ); //first param: get current ImageSetter for image
      saveImage( ip.bi2 );
   }

   /**
      Globals for cropLatticeImage function. These are implemented because this is the only function that parses images into
      multiple save files right now. Perhaps these could be worked into locals, but it didn't fit so well, and I was trying to
      do this function in >2 hours (which I did :)
      
      Anyways, latticeRow and latticeCol keep track of coordinate naming system for cropped images produced.
      The boolean variable latticeImage is set when cropLatticeImage is called.
   
   */
   public int latticeRow = 0;
   public int latticeCol = 0;

   /**
      Crops an image into multiple cropped images, like a lattice. Eg. Can crop a 400 x 400 px image into 400 total 20x20 px images.
      This uses two globals which should be above, latticeRow and latticeCol, to set the names of each new saved image.
      Uses cropImage( Rectangle rect ), and simply goes through the entire picture based on the crop size (a rectangle), which is set
      when the function is called.
      
      @version 01/25/19 Only works if rectangles evenly tile image (width and height of crop rectangle need to evenly divide pic width
                        and pic height). cropImage(..) does allow crops that go past the image boundary, but this hasn't been written
                        into this function yet when iterating the window of the crop rectangle.
      
      @param rect The boundary of the cropped images. Its width and height need to divide pic width and height evenly
      @see crop( Rectangle rect )
      @see cropLatticeHelper( Rectangle rect ) This is a special image-saving helper method since the changed image needs to not overwrite
                                               the copy of the original (ip.bi2) each time the image is cropped. Ie. ip.bi2 is cropped, but
                                               new crop data saved to ip.bi3, leaving ip.bi2 the same size. This is needed due to the ever-
                                               aggravating nature of working with deep copies using BufferedImage (which sucks)
      
   */
   public void cropLatticeImage( Rectangle rect ) {
      latticeImage = true;
      
      int imageWidth = new ImageSetter( ip.bi2 ).getWidth();
      int imageHeight = new ImageSetter( ip.bi2 ).getHeight();
      
      int origX = rect.x;
      int origY = rect.y;
      
      for( ; rect.x < imageWidth; rect.x += rect.width ) {
         for( ; rect.y < imageHeight; rect.y += rect.height ) {
            cropLatticeHelper( rect );
            latticeCol++;
         }
         latticeCol = 0;
         latticeRow++;
         rect.y = origY;
      }
   }
   
   /**
      Similar to cropImage( Rectangle rect ), this function is needed since the copy of the original needs to not be
      overwritten when cropping for the second and subsequent images. Ie. the data in ip.bi2 is used, but is altered and
      saved to ip.bi3, which is then written and saved
      
      @param rect The boundary of the crop
      @see crop( ImageSetter setter, Rectangle rect )
      
   */
   private void cropLatticeHelper( Rectangle rect ) {
      ip.bi3 = crop( new ImageSetter( ip.bi2 ), rect ); //first param: get current ImageSetter for image
      saveImage( ip.bi3 );
   }
   
   /**
      Crop an image based on a given Rectangle's coordinates
      
      @param src The image to crop
      @param boundary The coordinates specifying what part of the image to keep (rect should be smaller than image)
      @return BufferedImage The cropped image
      @see BufferedImage.getSubimage( int x, int y, int w, int h )
   */
   private BufferedImage crop( ImageSetter src, Rectangle boundary ) {
      //one-liner works, but doesn't create deep-copy
      //BufferedImage result = src.getSubimage( boundary.x, boundary.y, boundary.width, boundary.height );      
       
      Pixel[][] srcData = src.getData();
      
      int newWidth = ( boundary.width > srcData.length ) ? srcData.length : boundary.width;
      int newHeight = ( boundary.height > srcData[0].length ) ? srcData[0].length : boundary.height;
      
      Pixel[][] cropData = new Pixel[ newWidth ][ newHeight ];
      
      for( int row = 0, srcRow = boundary.x; row < boundary.height; row++, srcRow++ ) {
         for( int col = 0, srcCol = boundary.y; col < boundary.width; col++, srcCol++ ) {
            cropData[ row ][ col ] = srcData[ srcRow ][ srcCol ];
         }
      }
      
      src.setSize( new Rectangle( boundary.x, boundary.y, newWidth, newHeight ) );
      src.setData( cropData );
      
      return src.getImage(); 
   }
   
   /**
      Creates a deep copy of a BufferedImage. For some methods, such as BufferedImage.getSubimage(..), changes to the original image can
      get relayed back to the original image and get it as well. Thus, this method can produce a BufferedImage that creates a truly
      deep copy of a BufferedImage
      
      @param image The image to copy
      @return BufferedImage The copy of the image, independent of the original if changes are made to it
      @see BufferedImage.copyData( WriteableRaster raster )
   */
   public static BufferedImage deepCopy( BufferedImage image ) {
      ColorModel colorModel = image.getColorModel();
      boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
      WritableRaster raster = image.copyData( null );
      return new BufferedImage( colorModel, raster, isAlphaPremultiplied, null );
   }
   
   /**
      @NOTUSED: This method copies a BufferedImage, paints the image, and returns the copy, which should be a deep copy
      
      @param source The image to copy and paste and return
      @return BufferedImage The deep-copy image
   */
   public static BufferedImage copyImage( BufferedImage source ){
      BufferedImage image = new BufferedImage( source.getWidth(), source.getHeight(), source.getType() );
      Graphics g = image.getGraphics();
      g.drawImage( source, 0, 0, null );
      g.dispose();
      return image;
   }
   
   /**
      Given an image and another image to layer on this image, as well as the local coordinate for the layer image,
      paste in the layered portion onto the original image directly into the pixels of the original image.
      
      Note that this function can layer on an image even if the layer is not completely contained in the image
      ( see helper function getOverlappingRectAndLayerPoint(..) )
      
      @param image The original image to layer upon
      @param layer The image to layer on this image
      @param x The x coordinate of where to place the layer image on this image. The layer can be placed such that the layered
               image's contents go outside the original image's content, such that the part not overlapping is not included in the
               new image. This means that the x and y coordinate can be negative and still work accordingly. eg. -100, -100 would
               place the layered image up to the left and above the original image, so that the layered image's right corner is the
               only part that is layered and saved onto the new image
      @param y The y coordinate of where to place the layer image on this image.
      @return void Returns the original image if the layer does not overlap this image
      @see getOverlappingRectAndLayerPoint( Rectangle dataRect, Rectangle layerRect, int x, int y )
   */
   public void layerImage( ImageSetter image, ImageSetter layer, int x, int y ) {
      // get the data from the images
      Pixel[][] data = image.getData();
      Pixel[][] layerData = layer.getData();
      
      Rectangle dataRect = new Rectangle( data[0].length, data.length );
      Rectangle layerRect = new Rectangle( layerData[0].length, layerData.length );
         
      if( !dataRect.intersects( layerRect ) )
         return; // No overlap means no layering
      
      Object[] newRectAndPoint = getOverlappingRectAndLayerPoint( dataRect, layerRect, x, y );
      
      Rectangle newLayer = ( Rectangle )newRectAndPoint[0];
      Point start = ( Point )newRectAndPoint[1];
      int newLayerX = -1;
      int newLayerY = -1;
      int layerRowCount = 0; // Tells when we have completed layering based on height of overlap Rectangle
      
      Pixel[][] newData = new Pixel[ data.length ][ data[0].length ];
      boolean layering = false;
      
      int dataW = data[0].length;
      int dataH = data.length;
      
      for( int row = 0; row < dataH; row++ ) {
         for( int col = 0; col < dataW; col++ ) {
         
            //Start using the layering pixels instead
            if( row == newLayer.y && col == newLayer.x ) {
               layering = true;
               newLayerX = start.x;
               newLayerY = start.y;
            }
            
            //If started layering, and if we're back at the x coordinate of the layered Rectangle, start layering again
            if( newLayerX >= 0 && col == newLayer.x ) {
               layering = true;
            }
            
            //Choose the right pixel, either from the original image or the layer based on whether layering or not
            if( layering ) {
               Pixel debugPix = layerData[ newLayerY ][ newLayerX++ ];
               newData[ row ][ col ] = debugPix;
            } else {
               newData[ row ][ col ] = data[ row ][ col ];
            }
            
            //Determine if no longer layering anymore and reset layering x coordinate
            if( layerRowCount < newLayer.height && (newLayerX >= newLayer.width + start.x || newLayerY >= newLayer.height + start.y) ) {
               layering = false;
               newLayerX = start.x;
               layerRowCount++;
               newLayerY++;

            }
            //If reach end of layered image and there is still more pixels of the original, make sure layering stops
            else if ( layerRowCount >= newLayer.height ) { 
               newLayerX = -1;
            }
         }
      }
      
      //Set new data to image
      image.setData( newData );
   }
   
   /**
      Given two Rectangle objects, returns the overlapping Rectangle based on the original rectangles coordinates, as well
      as the Point of intersection based on the layered Rectangle's coordinates. The layered Rectangle is placed
      according to the x and y integer parameters, which should be the coordinates based on the original image's coordinate
      system. This function returns the original rectangle if the Rectangles do not overlap. This function also handles
      all possibilities of overlap
      
      @param dataRect The original Rectangle
      @param layerRect The rectangle placed on the original Rectangle
      @param x The x coordinate of where the layered Rectangle is placed, based on the coordinate system of the original Rectangle
      @param y The y coordinate of where the layerd Rectangle is placed, based on the coordinate system of the original Rectangle
      @return Object[] A two element list, containing the resulting overlapping Rectangle, and secondly, the Point corresponding
                       to the upper left hand corner of the overlapping Rectangle based in the layered Rectangle coordinate system
      @see layerImage( ImageSetter image, ImageSetter layer, int x, int y )
   */
   private Object[] getOverlappingRectAndLayerPoint( Rectangle dataRect, Rectangle layerRect, int x, int y ) {
      
      if( !dataRect.intersects( layerRect ) )
         return new Object[]{ dataRect, new Point( 0, 0 ) }; // No overlap means no layering
      
      Rectangle newLayer; //Rectangle that holds the offset values for the area to add the layer pixels to
      Point start;        //Point in the layerRect to start copying pixels
      
      /* SET NEW LAYER RECTANGLE AND STARTING POINT */
      
      //layerRect is completely inside dataRect
      if( x >= 0 && y >= 0 && x + layerRect.width < dataRect.width && y + layerRect.height < dataRect.height ) { //do nothing -- layer rectangle is set
         newLayer = new Rectangle( x, y, layerRect.width, layerRect.height );
         start = new Point( 0, 0 );
      }
      
      //dataRect is completely inside layerRect
      else if( x < 0 && y < 0 && x + layerRect.width > dataRect.width && y + layerRect.height > dataRect.height ) {
         newLayer = dataRect;
         start = new Point( Math.abs(x), Math.abs(y) );
      }
      
      //layerRect's wider than image and top corners are not overlapping, or bottom corners, or all four corners
      else if ( x < 0 && x + layerRect.width > dataRect.width ) { 
         if( y < 0 ) { //top
            newLayer = new Rectangle( 0, 0, dataRect.width, layerRect.height + y );
            start = new Point( Math.abs(x), Math.abs(y) );
         } else if ( y + layerRect.height > dataRect.height ) { //bottom
            newLayer = new Rectangle( 0, y, dataRect.width, dataRect.height );
            start = new Point( Math.abs(x), 0 );
         } else { //mid
            newLayer = new Rectangle( 0, y, dataRect.width, layerRect.height + y );
            start = new Point( Math.abs(x), 0 );
         }
      }
      
      //layerRect's taller than the image and the left corners are not overlapping, or the right corners, or all four corners
      else if ( y < 0 && y + layerRect.height > dataRect.height ) {
         if( x < 0 ) { //left
            newLayer = new Rectangle( 0, 0, layerRect.width + x, dataRect.height );
            start = new Point( Math.abs(x), Math.abs(y) );
         } else if ( x + layerRect.width > dataRect.width ) { //right
            newLayer = new Rectangle( x, 0, dataRect.width, dataRect.height );
            start = new Point( 0, Math.abs(y) );
         } else { //mid
            newLayer = new Rectangle( x, 0, x + layerRect.width, dataRect.height );
            start = new Point( 0, Math.abs(y) );
         }
      }
      
      //layerRect overlaps dataRect, but is neither wider than the image nor longer
      else if ( x + layerRect.width > dataRect.width ) { //layerRect's right side is beyond right side of image
         if( y < 0 ) {
            newLayer = new Rectangle( x, 0, dataRect.width - x, layerRect.height + y );
            start = new Point( 0, Math.abs(y) );
         } else if( y + layerRect.height > dataRect.height ) {
            newLayer = new Rectangle( x, y, dataRect.width - x, dataRect.height - y);
            start = new Point( 0, 0 );
         } else {
            newLayer = new Rectangle( x, y, dataRect.width - x, layerRect.height );
            start = new Point( 0, 0 );
         }
      } else if ( x >= 0 ) { //layerRect's bottom left and right corners are inside dataRect, but not top, or vice versa
         if( y < 0 ) { //top
            newLayer = new Rectangle( x, 0, layerRect.width, layerRect.height + y );
            start = new Point( 0, Math.abs(y) );
         } else { //bottom
            newLayer = new Rectangle( x, y, layerRect.width, dataRect.height - y );
            start = new Point( 0, 0 );
         }
      } else if( x < 0 ){ //layerRect's left side is beyond left side of image
         if( y < 0 ) { //top
            newLayer = new Rectangle( 0, 0, x + layerRect.width, y + layerRect.height );
            start = new Point( Math.abs(x), Math.abs(y) );
         } else if ( y + layerRect.height > dataRect.height ) { //bottom
            newLayer = new Rectangle( 0, y, layerRect.width + x, dataRect.height - y );
            start = new Point( Math.abs(x), 0 );
         } else { //middle
            newLayer = new Rectangle( 0, y, layerRect.width + x, layerRect.height );
            start = new Point( Math.abs(x), 0 );
         }
      }
      
      //There should be no other options, so if we get here, print an error
      else {
         SOPln("Error: In layerImage(..), reached impossible new Rectangle option.");
         newLayer = null;
         start = null;
      }
      
      return new Object[]{ newLayer, start };
   }
   
   /**
      Blurs the image based on the given strength.
      
      Strength should be float values that are perfect squares eg. 4, 9, 16, 25, 36, 49, 64, 81, 100, 121, 144, 225, 400, 900, 1600, etc
      
      Strength 9 = similar to gaussian blur
               100 = strong blur
               400 = super blur
               10000 = what is this
      
      @param theImage The image to blur
      @param strength The strength of the blur
      @see ConvolveOp.filter( BufferedImage src, BufferedImage dest )
   */
   public void blurFilter( ImageSetter theImage, float strength ) {
   
      int width = (int)Math.sqrt( (double)strength );
      int height = width;
      float[] matrix = new float[ (int)strength ];
      for( int i = 0; i < strength; i++ ) {
         matrix[i] = 1.0f / strength;
      }
      ConvolveOp newImage = new ConvolveOp( new Kernel( width, height, matrix ), ConvolveOp.EDGE_NO_OP, null );
      theImage.setImage( newImage.filter( theImage.getImage(), null ) );
   }
   
   /**
      Apply a Gaussian filter to the image
   
      @param theImage The image to modify
   */
   public void gaussianFilter( ImageSetter theImage ) {

      //mild blur
      int[][] kernel = {{1,2,1},{2,4,2},{1,2,1}};
      final int KSIZE = kernel.length;
      final int KREDUCTION = 16;
        
      // get the data from the image
      Pixel[][] data = theImage.getData();
      Pixel[][] result = new Pixel[ data.length ][ data[0].length ];
        
      final int WIDTH = theImage.getWidth();
      final int HEIGHT = theImage.getHeight();
        
      //for each row, swap its contents from left to right
      for ( int row = 0; row < HEIGHT; row++ ) {
         for ( int col = 0; col < WIDTH; col++ ) {
            int totalRed = 0, totalGreen = 0, totalBlue = 0;
            for( int kRow = 0; kRow < KSIZE; kRow++ ) {
               for( int kCol = 0; kCol < KSIZE; kCol++ ) {
                  int rowLimit = row + kRow - 1;
                  int colLimit = col + kCol - 1;
                  if( rowLimit >= 0 && colLimit >= 0 && rowLimit < HEIGHT && colLimit < WIDTH ) {            
                     int red = data[ rowLimit ][ colLimit ].rgb[ data[ rowLimit ][ colLimit ].RED ];
                     int green = data[ rowLimit ][ colLimit ].rgb[ data[ rowLimit ][ colLimit ].GREEN ];
                     int blue = data[ rowLimit ][ colLimit ].rgb[ data[ rowLimit ][ colLimit ].BLUE ];
                     totalRed += kernel[ kRow ][ kCol ] * red;
                     totalGreen += kernel[ kRow ][ kCol ] * green;
                     totalBlue += kernel[ kRow ][ kCol ] * blue;
                  }
               }
            }
            totalRed /= KREDUCTION;
            totalGreen /= KREDUCTION;
            totalBlue /= KREDUCTION;
                
            result[ row ][ col ] = new Pixel( totalRed, totalGreen, totalBlue );
         }
      }
        
      // update the image with the moved pixels
      theImage.setData( result );
        
   }
   
   /**
      Apply a Laplacian filter to the image
   
      @param theImage The image to modify
   */
   public void laplacianFilter( ImageSetter theImage ) {
      
      final boolean USE_DARK = false;
      
       /*
         Kernels: {1,1,1},{1,-8,1},{1,1,1}
                  {-1,6,-1},{6,-20,6},{-1,6,-1}
                  {0,-1,0},{-1,4,-1},{0,-1,0} //convolved negative
                  {0,-1,0},{1,4,1},{0,-1,0} //convolved positive
       */
      
      final int MIDDLE = -8;  
      int[][] kernel = {{1,1,1},{1,-8,1},{1,1,1}};
      final int KSIZE = kernel.length;
      final int KREDUCTION = 16;
        
      // get the data from the image
      Pixel[][] data = theImage.getData();
      Pixel[][] result = new Pixel[ data.length ][ data[0].length ];
        
      final int WIDTH = theImage.getWidth();
      final int HEIGHT = theImage.getHeight();
        
      //for each row, swap its contents from left to right
      for ( int row = 0; row < HEIGHT; row++ ) {
         for ( int col = 0; col < WIDTH; col++ ) {
            int totalRed = 0, totalGreen = 0, totalBlue = 0;
            for( int kRow = 0; kRow < KSIZE; kRow++ ) {
               for( int kCol = 0; kCol < KSIZE; kCol++ ) {
                  int rowLimit = row + kRow - 1;
                  int colLimit = col + kCol - 1;
                  if( rowLimit >= 0 && colLimit >= 0 && rowLimit < HEIGHT && colLimit < WIDTH ) {            
                     int red = data[ rowLimit ][ colLimit ].rgb[ data[ rowLimit ][ colLimit ].RED ];
                     int green = data[ rowLimit ][ colLimit ].rgb[ data[ rowLimit ][ colLimit ].GREEN ];
                     int blue = data[ rowLimit ][ colLimit ].rgb[ data[ rowLimit ][ colLimit ].BLUE ];
                     totalRed += kernel[ kRow ][ kCol ] * red;
                     totalGreen += kernel[ kRow ][ kCol ] * green;
                     totalBlue += kernel[ kRow ][ kCol ] * blue;
                  }
               }
            }
            //totalRed = data[row][col].rgb[ 0 ] - (int)(0.2*totalRed);
            //totalGreen = data[row][col].rgb[ 1 ] - (int)(0.2*totalGreen);
            //totalBlue = data[row][col].rgb[ 2 ] - (int)(0.2*totalBlue);
            
            if( USE_DARK ) {
               totalRed /= KREDUCTION;
               totalGreen /= KREDUCTION;
               totalBlue /= KREDUCTION;
            } else {
               totalRed = ( totalRed + Math.abs(MIDDLE)*255 ) / KREDUCTION;
               totalGreen = ( totalGreen + Math.abs(MIDDLE)*255 ) / KREDUCTION;
               totalBlue = ( totalBlue + Math.abs(MIDDLE)*255 ) / KREDUCTION;
            }
            
            if( totalRed > 255 ) totalRed = 255;
            if( totalGreen > 255 ) totalGreen = 255;
            if( totalBlue > 255 ) totalBlue = 255;
            if( totalRed < 0 ) totalRed = 0;
            if( totalGreen < 0 ) totalGreen = 0;
            if( totalBlue < 0 ) totalBlue = 0;
                
            result[ row ][ col ] = new Pixel( totalRed, totalGreen, totalBlue );
         }
      }
        
      // update the image with the moved pixels
      theImage.setData( result );
        
   }
   
   /**
      Given two pictures, perform a bitwise AND operation across all pixels
      (supposedly works well with two same size, grey images, that have plain backgrounds but different foreground objects)
      
      @param theImage The first image to use
      @param secondImage The second image to use
      @see ImageSetter.setData( Pixel[][] data )
   */
   public void bitwiseANDFilter( ImageSetter theImage, ImageSetter secondImage ) {
      // get the data from the image
      Pixel[][] data = theImage.getData();
      Pixel[][] data2 = secondImage.getData();
      Pixel[][] result = new Pixel[ data.length ][ data[0].length ];
        
      final int WIDTH = theImage.getWidth();
      final int HEIGHT = theImage.getHeight();
      final int WIDTH2 = secondImage.getWidth();
      final int HEIGHT2 = secondImage.getHeight();
      
      if( WIDTH != WIDTH2 || HEIGHT != HEIGHT2 ) {
         System.out.println("Images not same size");
         return;
      }
        
      //for each row, swap its contents from left to right
      for ( int row = 0; row < HEIGHT; row++ ) {
         for ( int col = 0; col < WIDTH; col++ ) {
            int totalRed = 0, totalGreen = 0, totalBlue = 0;
            int totalRed2 = 0, totalGreen2 = 0, totalBlue2 = 0;

            totalRed = data[row][col].rgb[ 0 ];
            totalGreen = data[row][col].rgb[ 1 ];
            totalBlue = data[row][col].rgb[ 2 ];
            totalRed2 = data2[row][col].rgb[ 0 ];
            totalGreen2 = data2[row][col].rgb[ 1 ];
            totalBlue2 = data2[row][col].rgb[ 2 ];
            
            if( totalRed > 255 ) totalRed = 255;
            if( totalGreen > 255 ) totalGreen = 255;
            if( totalBlue > 255 ) totalBlue = 255;
            if( totalRed < 0 ) totalRed = 0;
            if( totalGreen < 0 ) totalGreen = 0;
            if( totalBlue < 0 ) totalBlue = 0;
            if( totalRed2 > 255 ) totalRed2 = 255;
            if( totalGreen2 > 255 ) totalGreen2 = 255;
            if( totalBlue2 > 255 ) totalBlue2 = 255;
            if( totalRed2 < 0 ) totalRed2 = 0;
            if( totalGreen2 < 0 ) totalGreen2 = 0;
            if( totalBlue2 < 0 ) totalBlue2 = 0;
            
            totalRed = totalRed & totalRed2;
            totalGreen = totalGreen & totalGreen2;
            totalBlue = totalBlue & totalBlue2;
                
            result[ row ][ col ] = new Pixel( totalRed, totalGreen, totalBlue );
         }
      }
        
      // update the image with the moved pixels
      theImage.setData( result );
   }
   
   /**
      Add a fixed amount to each rgb pixel, overflowing amounts over 255 by using modulus
      
      @param theImage The image to adjust
   */
   public void additionFilter( ImageSetter theImage ) {
      final int AMOUNT_ADDED = 100;
   
      // get the data from the image
      Pixel[][] data = theImage.getData();
      Pixel[][] result = new Pixel[ data.length ][ data[0].length ];
        
      final int WIDTH = theImage.getWidth();
      final int HEIGHT = theImage.getHeight();
        
      //for each row, swap its contents from left to right
      for ( int row = 0; row < HEIGHT; row++ ) {
         for ( int col = 0; col < WIDTH; col++ ) {
            int totalRed = 0, totalGreen = 0, totalBlue = 0;

            totalRed = data[row][col].rgb[ 0 ] + AMOUNT_ADDED;
            totalGreen = data[row][col].rgb[ 1 ] + AMOUNT_ADDED;
            totalBlue = data[row][col].rgb[ 2 ] + AMOUNT_ADDED;
            
            if( totalRed > 255 ) totalRed %= 255;
            if( totalGreen > 255 ) totalGreen %= 255;
            if( totalBlue > 255 ) totalBlue %= 255;
            if( totalRed < 0 ) totalRed = 255 - totalRed;
            if( totalGreen < 0 ) totalGreen = 255 - totalGreen;
            if( totalBlue < 0 ) totalBlue = 255 - totalBlue;
                
            result[ row ][ col ] = new Pixel( totalRed, totalGreen, totalBlue );
         }
      }
        
      // update the image with the moved pixels
      theImage.setData( result );
   }
   
   /**
      Inverse image
   
      @param theImage The image to modify
   */
   public void inverseFilter( ImageSetter theImage ) {
        
      // get the data from the image
      Pixel[][] data = theImage.getData();
      Pixel[][] result = new Pixel[ data.length ][ data[0].length ];
        
      final int WIDTH = theImage.getWidth();
      final int HEIGHT = theImage.getHeight();
        
      //for each row, swap its contents from left to right
      for ( int row = 0; row < HEIGHT; row++ ) {
         for ( int col = 0; col < WIDTH; col++ ) {
            int totalRed = 0, totalGreen = 0, totalBlue = 0;

            totalRed = data[row][col].rgb[ 0 ];
            totalGreen = data[row][col].rgb[ 1 ];
            totalBlue = data[row][col].rgb[ 2 ];
            
            if( totalRed > 255 ) totalRed = 255;
            if( totalGreen > 255 ) totalGreen = 255;
            if( totalBlue > 255 ) totalBlue = 255;
            if( totalRed < 0 ) totalRed = 0;
            if( totalGreen < 0 ) totalGreen = 0;
            if( totalBlue < 0 ) totalBlue = 0;
                
            result[ row ][ col ] = new Pixel( 255 - totalRed, 255 - totalGreen, 255 - totalBlue );
         }
      }
        
      // update the image with the moved pixels
      theImage.setData( result );
        
   }
   
   /**
      Sharpen image
   
      @param theImage The image to modify
   */
   public void sharpenFilter( ImageSetter theImage ) {
       /*
         Kernels: {-1.0,0.0,-1.0},{-2.0,0.2,-2.0},{-2.0,0.2,-2.0}
                  
       */
       
      double[][] kernel = {{-1.0,0.0,-1.0},{-2.0,0.2,-2.0},{-2.0,0.2,-2.0}};
      final int KSIZE = kernel.length;
      final int KREDUCTION = 2;
        
      // get the data from the image
      Pixel[][] data = theImage.getData();
      Pixel[][] result = new Pixel[ data.length ][ data[0].length ];
        
      final int WIDTH = theImage.getWidth();
      final int HEIGHT = theImage.getHeight();
        
      //for each row, swap its contents from left to right
      for ( int row = 0; row < HEIGHT; row++ ) {
         for ( int col = 0; col < WIDTH; col++ ) {
            int totalRed = 0, totalGreen = 0, totalBlue = 0;
            for( int kRow = 0; kRow < KSIZE; kRow++ ) {
               for( int kCol = 0; kCol < KSIZE; kCol++ ) {
                  int rowLimit = row + kRow - 1;
                  int colLimit = col + kCol - 1;
                  if( rowLimit >= 0 && colLimit >= 0 && rowLimit < HEIGHT && colLimit < WIDTH ) {            
                     int red = data[ rowLimit ][ colLimit ].rgb[ data[ rowLimit ][ colLimit ].RED ];
                     int green = data[ rowLimit ][ colLimit ].rgb[ data[ rowLimit ][ colLimit ].GREEN ];
                     int blue = data[ rowLimit ][ colLimit ].rgb[ data[ rowLimit ][ colLimit ].BLUE ];
                     totalRed += (int)(kernel[ kRow ][ kCol ] * red);
                     totalGreen += (int)(kernel[ kRow ][ kCol ] * green);
                     totalBlue += (int)(kernel[ kRow ][ kCol ] * blue);
                  }
               }
            }
            totalRed = data[row][col].rgb[ 0 ] - (int)(0.2*totalRed);
            totalGreen = data[row][col].rgb[ 1 ] - (int)(0.2*totalGreen);
            totalBlue = data[row][col].rgb[ 2 ] - (int)(0.2*totalBlue);
            
            totalRed /= KREDUCTION;
            totalGreen /= KREDUCTION;
            totalBlue /= KREDUCTION;
            
            if( totalRed > 255 ) totalRed = 255;
            if( totalGreen > 255 ) totalGreen = 255;
            if( totalBlue > 255 ) totalBlue = 255;
            if( totalRed < 0 ) totalRed = 0;
            if( totalGreen < 0 ) totalGreen = 0;
            if( totalBlue < 0 ) totalBlue = 0;
                
            result[ row ][ col ] = new Pixel( totalRed, totalGreen, totalBlue );
         }
      }
        
      // update the image with the moved pixels
      theImage.setData( result );
        
   }
   
   /**
     * Flip the image horizontally ( over the vertical axis)
     * 
     * @param theImage The image to modify
     */
    public void flipHorizontalFilter( ImageSetter theImage ) {
        
        // get the data from the image
        Pixel[][] data = theImage.getData();
        
        //for each row, swap its contents from left to right
        for ( int row = 0; row < theImage.getHeight(); row++ ) {
            for ( int col = 0; col < theImage.getWidth() / 2; col++ ) {
                // given a column: i, its pair is column: width() - i - 1
                // e.g. with a width of 10
                // column 0 is paired with column 9
                // column 1 is paired with column 8 etc.
                Pixel temp = data[row][col];
                data[ row ][ col ] = data[ row ][ theImage.getWidth() - col - 1 ];
                data[ row ][ theImage.getWidth() - col - 1 ] = temp;
            }
        }
        
        // update the image with the moved pixels
        theImage.setData( data );
        
    }
   
   /**
     * Flip the image vertically ( over the vertical axis)
     * 
     * @param theImage The image to modify
     */
    public void flipVerticalFilter( ImageSetter theImage ) {
        
        // get the data from the image
        Pixel[][] data = theImage.getData();
        
        //for each row, swap its contents from left to right
        for ( int col = 0; col < theImage.getWidth(); col++ ) {
            for ( int row = 0; row < theImage.getHeight() / 2; row++ ) {
                // given a column: i, its pair is column: width() - i - 1
                // e.g. with a width of 10
                // column 0 is paired with column 9
                // column 1 is paired with column 8 etc.
                Pixel temp = data[ row ][ col ];
                data[ row ][ col ] = data[ theImage.getHeight() - row - 1 ][ col ];
                data[ theImage.getHeight() - row - 1 ][ col ] = temp;
            }
        }
        
        // update the image with the moved pixels
        theImage.setData( data );
        
    }
   
   /**
     * Flip the image vertically ( over the vertical axis)
     * 
     * @param theImage The image to modify
     */
    public void grayFilter( ImageSetter theImage ) {
        
        final double RED_SCALE = 0.30;
        final double GREEN_SCALE = 0.59;
        final double BLUE_SCALE = 0.11;
        
        // get the data from the image
        Pixel[][] data = theImage.getData();
        
        //for each row, swap its contents from left to right
        for ( int row = 0; row < theImage.getHeight(); row++ ) {
            for ( int col = 0; col < theImage.getWidth(); col++ ) {
                int gray = (int)( data[row][col].rgb[ data[row][col].RED ] * RED_SCALE +
                                 data[row][col].rgb[ data[row][col].GREEN ] * GREEN_SCALE +
                                 data[row][col].rgb[ data[row][col].BLUE ] * BLUE_SCALE);
                
                data[ row ][ col ] = new Pixel( gray, gray, gray );
            }
        }
        
        // update the image with the moved pixels
        theImage.setData( data );
        
    }
   
   /**
     * Rotate image counter-clockwise.
     * @@NOTE: ONLY WORKS FOR SQUARE IMAGES
     * 
     * @param theImage The image to modify
     */
    public void rotateCounterClockwiseFilter( ImageSetter theImage ) {
        
        // get the data from the image
        Pixel[][] data = theImage.getData();
        Pixel[][] result = new Pixel[ data.length ][ data.length ];
        
        int width = theImage.getWidth();
        int height = theImage.getHeight();
        
        //for each row, swap its contents from left to right
        for( int row = 0; row < width; row++ ) {
            for( int col = 0; col < height; col++ ) {
                result[ ( height - 1 ) - col ][ row ] = data[ row ][ col ];
            }
        }
        
        // update the image with the moved pixels
        theImage.setData( result );
        
    }
   
   /**
     * Flip the image vertically ( over the vertical axis)
     * @@NOTE: ONLY WORKS FOR SQUARE-IMAGES
     * 
     * @param theImage The image to modify
     */
    public void rotateClockwiseFilter( ImageSetter theImage ) {
                
        // get the data from the image
        Pixel[][] data = theImage.getData();
        Pixel[][] result = new Pixel[ data.length ][ data.length ];
        
        int width = theImage.getWidth();
        int height = theImage.getHeight();
        
        //for each row, swap its contents from left to right
        for ( int row = 0; row < width; row++ ) {
            for ( int col = 0; col < height; col++ ) {
                result[ col ][ ( width - 1 ) - row ] = data[ row ][ col ];
            }
        }
        
        // update the image with the moved pixels
        theImage.setData( result );
        
    }
   
   /**
      Get and set the image given to the ImagePanel
      
      @param imageName The path / name of the image file
      @return BufferedImage The resulting BufferedImage
   */
   private BufferedImage getImage( String imageName ) {
      try {
         ip.bi = ImageIO.read( getClass().getResourceAsStream( imageName ) );
      } catch ( IOException e ) {
         e.printStackTrace();
      }

      return ip.bi;
   }
   
   //Get a copy of the original image for the filter to change (so the original is left unchanged)
   private BufferedImage getFilterImage( String imageName ) {
      try {
         ip.bi2 = ImageIO.read( getClass().getResourceAsStream( imageName ) );
      } catch ( IOException e ) {
         e.printStackTrace();
      }

      return ip.bi2;
   }
   
   //Get a copy of the original image for the filter to change (so the original is left unchanged)
   private BufferedImage getFilterImageSecond( String imageName ) {
      try {
         ip.bi3 = ImageIO.read( getClass().getResourceAsStream( imageName ) );
      } catch ( IOException e ) {
         e.printStackTrace();
      }
      return ip.bi3;
   }
   
   /**
      Gets the ImagePanel
   
      @returns the ImagePanel
   */
   protected ImagePanel getImagePanel() {
      return ip;
   }

   private void SOPln() {
      System.out.println();
   }

   private void SOPln( String message ) {
      System.out.println( message );
   }
   
   private void SOP( String message ) {
      System.out.print( message );
   }

   /** @@PRIVATE_CLASS
   
      JPanel for JFrame private class. Paints image and adds filters. This is a crappy implementation to just check if filters are working
      
      --- Methods ---
      paintComponent( Graphics g ) -- paints image
      applyFilter() -- call filter method, whichever one is being tested
   */
   private class ImagePanel extends JPanel {
      private BufferedImage bi, bi2, bi3;
      private GraphicsBaseFrame base;
        
      public ImagePanel( GraphicsBaseFrame base ) {
         bi = null;
         this.base = base;
      }
      
      public ImagePanel( GraphicsBaseFrame base, int width, int height ) {
         bi = null;
         this.base = base;
         setSize( width, height );
         setPreferredSize( new Dimension( width, height ) );
      }
      
      //Paint images  
      public void paintComponent( Graphics g ) {
         super.paintComponent(g);
         if ( bi != null ) {
            g.drawImage( bi, 0, 0, this );
            //SOPln( "ip width: " + this.getWidth() + ", height: " + ip.getHeight() );
         }
         if( bi2 != null )
            g.drawImage( bi2, 0/*bi.getWidth()*/, 0, this ); //Currently drawing over the original image (second parameter) --> original GraphicsFrame program drew image to the right of the original
      }

      //Apply filter to original image
      public void applyFilter( ) {
         if ( bi == null )
            return;
         
         ImageSetter newImage, newImage2;
         //If original already edited, should use copy image which contains the edits since the original is always left unchanged
         if( filtered ) {       
            newImage = new ImageSetter( bi2 );
            newImage2 = new ImageSetter( bi3 ); //Used for bitwise filter
         } else {
            newImage = new ImageSetter( bi2 );
            newImage2 = new ImageSetter( bi2 ); //Used for bitwise filter
         }
                     
         try {
            filter( newImage, newImage2 );
         } catch( IllegalStateException e ) {
            JOptionPane.showMessageDialog( this/*win*/, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
         } catch ( Exception e ) {
            e.printStackTrace( System.err );
         }
            
         bi = newImage.getImage();
         repaint();
      }
      
      //Apply filter to copy of image, and display to right of original
      public void filterImage() {
         if ( bi2 == null || bi3 == null)
            return;
                
         ImageSetter newImage, newImage2;
               
         newImage = new ImageSetter( bi2 );
         newImage2 = new ImageSetter( bi3 ); //Used for bitwise filter, layer filter, etc
           
         try {
            filter( newImage, newImage2 );
         } catch( IllegalStateException e ) {
            JOptionPane.showMessageDialog( this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
         } catch ( Exception e ) {
            e.printStackTrace( System.err );
         }
            
         bi2 = newImage.getImage();

         repaint();
      }
      
      //Use if painting directly onto the image, as in the case of rotate
      public void paintImage() {
         if ( bi2 == null )
            return;
                
         ImageSetter newImage = new ImageSetter( bi2 );
            
         try {
            drawOver( );
         } catch( IllegalStateException e ) {
            JOptionPane.showMessageDialog( this/*win*/, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
         } catch ( Exception e ) {
            e.printStackTrace( System.err );
         }

         repaint();
      }

      /**
         Set the background color of the ImagePanel
         
         @param color The color to change to
         @see JPanel.setOpaque( boolean isOpaque )
         @see JPanel.setBackground( Color color )
      */
      public void setBackground( Color color ) {
         setOpaque( true );
         super.setBackground( color ); 
      }

    } //end private class

}