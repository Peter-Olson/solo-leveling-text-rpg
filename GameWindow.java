/**
   GameWindow.java
   
   This is the main JPanel that is in the JFrame game. It contains additional JPanels, which are each GraphicsBaseFrame, that hold text and other information.
   The layout of the JFrame is a FlowLayout.LEFT, as is this, the game window.
   This class handles the painting and the timer of the game.
   
   @author Peter Olson
   @version 06/10/19
*/

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.Timer;
import java.awt.Point;
import java.awt.Point;
import java.awt.Color;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.List;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import java.awt.Robot;
import java.awt.AWTException;

public class GameWindow extends JPanel implements Runnable {

   private static final long serialVersionUID = 1L;
   
   private Thread thread;
   private boolean running;
   private int FPS = 60;
   private long targetTime = 1000/FPS;
   private Timer timer;
   private Digraph<String> screenInputTextFileDigraph;   //holds the names of the text files as pairs of vertices, creating directed paths. The first listed text file points to the second in the same line
   private final String BRANCHES_FILE = "branches.txt";
   private final String TEXT_BOX = "<textbox>";
   private final double TEXT_BOX_LOC_X = 20.0;
   private final double TEXT_BOX_LOC_Y = 1.2;
   
   private final boolean HACKER_MODE = false;
   private final String HACKER_MODE_FIRST_SCREEN = "ch0b.txt";
   
   private final String REPEATED_SCREEN_TAG = "@repeated"; //tag for screen text files to tell whether this screen is used multiple times in different places within the screen plotline
   
   private static final int WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(); //1904 -- reg size
   private static final int HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(); //1008 -- reg size
   private final int X_PAD = 10;
   private final int Y_PAD = 10;

   /**
      Creates the game window jpanel in the JFrame
   */
   public GameWindow(){
      super();
      setPreferredSize( new Dimension( WIDTH, HEIGHT ) );
      setLayout( null );
      setBounds( 0, 0, WIDTH, HEIGHT );
      setOpaque( true );
      setBackground( Color.BLACK );
      
      //@@DEBUG
      //setBorder( BorderFactory.createLineBorder( Color.RED ) );
      
      setFocusable( true );
      requestFocus();
      init();
   }
   
   /**
      Invoked when GameStart is added to the screen. This method in turn invokes the run() method
      That is, thread.start() calls the run() method
   
      @see JComponent.addNotify();
      @see Thread.start();
   */
   public void addNotify() {
      super.addNotify();
      if ( thread == null ) {
         thread = new Thread( this );
         thread.start();
      }
      running = true;
   }
   
   /**
      Paints the components within the JPanel
      
      @param g The graphics object used to paint
      @see JComponent.paintComponent( Graphics g )
      @see repaint()
   */
   public void paintComponent ( Graphics g ) {
      super.paintComponent( g );
      
      repaint();
   }
   
   /**
      The initial setup of the game before the timer starts
      
      @see setScreenBranches();
      @see Container.add( Component comp, int index )
      @see Timer.start()
   */
   private void init() {
      new State();   // Reset State variables
      screenInputTextFileDigraph = new Digraph<String>();
      setScreenBranches();
   
      //Adds the first screen of the game
      Map<String, List<String>> map = screenInputTextFileDigraph.getNeighbors();
      String firstScreenTitle;
      
      //Hacker mode is used for starting at a specific screen in the game
      if( HACKER_MODE ) {
         firstScreenTitle = HACKER_MODE_FIRST_SCREEN;
      } else {
         Map.Entry<String, List<String>> entry = map.entrySet().iterator().next();
         firstScreenTitle = entry.getKey();
      }
      
      Screen firstScreen = setupScreen( firstScreenTitle );
      State.currentScreen = firstScreen;
      State.location = firstScreen.getCoord();

      add( firstScreen );

      Dimension size = new Dimension( WIDTH, HEIGHT );
      firstScreen.setBounds( (int)(WIDTH/3.0), (int)(HEIGHT/4.0), size.width, size.height ); //mid x/2.5, y/4
   
      addKeyListener( new MyKeyListener() );
   
      timer = new javax.swing.Timer( FPS, new MoveListener() );
      timer.start();
   }
   
   /**
      This method is called at regular intervals according to the repaint() frame rate
   */
   public void update() {
      updateScreens();
   }
   
   /**
      Remove the current screen and draw a new screen when an option is chosen
      
      @see update()
      @see Screen.getOptions()
      @see ArrayList.get( int index )
      @see setupScreen( String textFileName )
      @see Screen.getCoord()
   */
   private void updateScreens() {
      if( !State.changeScreens )
         return;
      
      //@@DEBUG
      /*
      int debugOptNum = State.optionNumber;
      Screen debugScreen = State.currentScreen;
      Map<String, List<String>> debugMap = screenInputTextFileDigraph.getNeighbors();
      String debugTextFileName = debugScreen.getTextFileName();
      List<String> debugConnectedScreenTextFileNamesList = debugMap.get( debugTextFileName );
      String debugNextScreenTextFileName = debugConnectedScreenTextFileNamesList.get( debugOptNum );
      */
      
      remove( State.currentScreen );
      
      String nextScreenTextFileName = screenInputTextFileDigraph.getNeighbors().get( State.currentScreen.getTextFileName() ).get( State.optionNumber );
      
      Screen screen = setupScreen( nextScreenTextFileName );
      State.currentScreen = screen;
      State.location = screen.getCoord();
      
      //removeAll();                     //remove all JPanels within this JPanel
      
      add( screen );  //add new Screen
      
      Insets insets = getInsets();
      Dimension size = screen.getPreferredSize();
      screen.setBounds( X_PAD + insets.left, Y_PAD + insets.top, size.width, size.height );
      
      repaint();
      
      //Set focus to JTextField
      if( State.currentScreen.hasTextBox() ) {
         try {
            Robot robot = new Robot();
            robot.keyPress( KeyEvent.VK_ENTER );
            robot.keyRelease( KeyEvent.VK_ENTER );
         } catch( AWTException e ) {
            e.printStackTrace();
         }
      }
      
      State.changeScreens = false;
   }
   
   /**
      This is the game loop
      1. Updates the world, 2. Repaints the screen
   
      @see System.nanoTime()
      @see update()
      @see Thread.sleep( long wait )
      @see JPanel.repaint()
   */
   @Override
   public void run() {
      long start, elapsed, wait;
      
      while( running ) {
         start = System.nanoTime();
         
         update();
         
         elapsed = System.nanoTime() - start;
         wait = targetTime - elapsed / 1000000;
         if( wait < 0 ) 
            wait = 5;
            
         try {
            Thread.sleep( wait );
         } catch ( Exception e ) {
            e.printStackTrace();
         }
         repaint();
      }
   }
   
   /**
      Gets the map of screens (a digraph) -- this is the map of what screen connects to what screen --
      also, recall that this is directed. Additionally, note that the order of screens added matters, as the
      neighbors of a given vertex (screen) are stored in an ArrayList, which can then be processed based on order
      
      @return Digraph<String> The map of screens
   */
   public Digraph<String> getScreenMap() {
      return screenInputTextFileDigraph;
   }
   
   /**
      Sets the map of text files into a digraph
      
      @see init()
      @see Scanner.nextLine()
      @see String.split( String delimiter )
      @see Digraph.add( V from, V to )
      @see Scanner.close();
   */
   private void setScreenBranches() {
      Scanner scanner = null;
      
      //@@DEBUG
      //SOPln( "Working Directory = " + System.getProperty( "user.dir" ) );
      
      try {
         scanner = new Scanner( new File( BRANCHES_FILE ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }

      while( scanner.hasNextLine() ) {
         String screenPair = scanner.nextLine();
         String[] screens = screenPair.split(",");
         
         if( screens.length != 2 ) {
            SOPln( "Error: invalid pair of screens" );
            return;
         }
         
         screenInputTextFileDigraph.add( screens[0], screens[1] );
      }
      
      //@@DEBUG
      //SOPln( screenInputTextFileDigraph.toString() );
      
      scanner.close();
   }
   
   /**
      Create a new Screen object by pulling info from the given text file
      
      @param textFileName The name of the text file containing the information of the screen
      @return Screen The screen object with the fields set
      @see init()
      @see updateScreens()
      @see String.trim()
      @see String.contains( String regex )
      @see Scanner.hasNextLine()
      @see Scanner.nextLine()
      @see String.equals( Object obj )
   */
   private Screen setupScreen( String textFileName ) {
      textFileName = textFileName.trim();
      if( !textFileName.contains(".txt") )
         textFileName += ".txt";
      
      //Since some screens are repeated and gone back to throughout the game, for instance s1.txt, set this variable to know to look for a the last non-repeating screen within the history of screens list
      boolean repeated = false;
      if( textFileName.contains( REPEATED_SCREEN_TAG ) )
         repeated = true;
      
      Scanner scanner = null;
      try {
         scanner = new Scanner( new File( textFileName ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      String text = "";
      Coord location = null;
      ArrayList<String> options = new ArrayList<String>();
      boolean hasTextBox = false;
      while( scanner.hasNextLine() ) {
         //Grab text
         String line = "";
         while( !line.equals( "@@END" ) ) {
            line = scanner.nextLine();
            if( !line.equals( "@@END" ) )
               text += line;
         }
         
         //Grab location
         String locationLine = scanner.nextLine();
         if( !locationLine.matches( "\\d,\\d" ) && !locationLine.equals( "unknown" ) ) {
            SOPln( "Invalid location: " + textFileName );
            return null;
         } else if( locationLine.equals( "unknown" ) ) {
            location = (Coord)State.variables.get( "Location" );
         } else {
            String[] coordParts = locationLine.split(",");
            location = new Coord( Integer.valueOf( coordParts[0] ), Integer.valueOf( coordParts[1] ) );
            State.variables.replace( "Location", location );
         }
         
         //Grab options
         do {
            line = scanner.nextLine();
            if( line.equals( TEXT_BOX ) )
               hasTextBox = true;
            else {
               //Check if option has if code
               if( line.contains( "@@CODE" ) ) {
                  String codeText = line.substring( line.indexOf( "@@CODE" ) + "@@CODE".length(), line.lastIndexOf( "@@CODE" ) );
                  codeText = codeText.replaceAll( "\\|", "" );
                  if( !Screen.analyzeIfCode( codeText ) ) {}
                  else {
                     line = line.substring( line.lastIndexOf( "@@CODE" ) + "@@CODE".length() ); //Get rid of code stuff
                     options.add( line );
                  }
               } else if( line.contains( "s1" ) ) { // Unique code set for repeated screens such as s1.txt
                  options = setS1Options( options );
               } else {
                  options.add( line );
               }
            }
         } while( scanner.hasNextLine() );
      }
      
      scanner.close();
      
      Screen newScreen = new Screen( WIDTH, HEIGHT, textFileName.replaceAll( ".txt", "" ), text, location, options, hasTextBox, repeated );
      State.screenHistory.add( newScreen );  //keep track of screen progression for looping and regularly used screens, such as s1.txt, among others
      
      return newScreen;
   }
   
   /**
      If reading the s1.txt file, manually set options
      
      @param options The list of options to process
      @return ArrayList<String> The finished list of options for s1.txt
      @see setupScreen( String textFileName )
   */
   private ArrayList<String> setS1Options( ArrayList<String> options ) {
      options.add( "choose,select,pick,|1,inventory" );
      options.add( "choose,select,pick,|2,stats" );
      options.add( "choose,select,pick,|3,skills" );
      options.add( "choose,select,pick,|4,quests" );
      options.add( "choose,select,pick,|5,equipment" );
      
      if( !State.className.equals("Hunter") ) {
         options.add( "choose,select,pick,|6,shadows" );
      }
      
      return options;
   }
   
   /**
      Based on the input into the JTextField, evaluate the next screen. If the input is not valid (does not lead to a new screen), do nothing
      
      @param text The text entered into the JTextField
      @param options The possible options recognized in the JTextField -- see text file
      @return boolean True if the evaluation was a success and points to a new Screen, false if the text does not lead to a new screen
      @see MyKeyListener.keyReleased( KeyEvent event )
      @see ArrayList.get( int index )
      @see String.contains( String regex )
      @see String.split( String delimiter )
      @see ArrayList.add( E e )
   */
   private boolean evalTextBoxInput( String text, ArrayList<String> options ) {
      boolean hasRightAt = false;
      boolean isSuccess = false;
      boolean hasVerb = false;
      boolean hasNoun = false;

      for( int i = 0; i < options.size(); i++ ) {
         String[] verbsNouns = options.get(i).split( "\\|" );
         
         //@@NOTE: options with multi-options (@s) should come before single option entries
         if( verbsNouns[1].contains( "@" ) ) {
            hasRightAt = true;
            verbsNouns[1] = verbsNouns[1].replaceAll( "@", "," );
         }
         
         String[] verbs = verbsNouns[0].split( ",", -1 ); //Allows for empty strings. ie. Allows for valid entries that don't require both a noun and a vowel
         String[] nouns = verbsNouns[1].split( ",", -1 ); //eg. take,grab,|jacket,sweater ---> an input of 'jacket' will return true, since the empty String for the verbs will be counted
         
         hasVerb = false;
         hasNoun = false;
         String lastVerb = ""; // Don't allow a blank verb and a blank noun, or allow one word to cover both a noun and a verb
         for( int j = 0; j < verbs.length; j++ ) {
            if( text.contains( verbs[j] ) ) {
               lastVerb = verbs[j];
               hasVerb = true;
               break;
            }
         }
         for( int j = 0; j < nouns.length; j++ ) {
            if( text.contains( nouns[j] ) && !lastVerb.equals( nouns[j] ) ) {
               hasNoun = true;
               if( !hasRightAt )
                  break;
               else if( !State.tempInventory.contains( nouns[j] ) && ( lastVerb.equals( "take" ) || lastVerb.equals( "grab" ) || lastVerb.equals( "get" ) || lastVerb.equals( "pick" ) ) )
                  State.tempInventory.add( nouns[j] );
            }
         }
         
         if( hasVerb && hasNoun ) {
            State.optionNumber = i;
            isSuccess = true;
            return isSuccess;
         }
      }
      
      return isSuccess;
   }
   
   /**
      Gets the option number of the last non-repeated screen within the player's screen history. The last repeated screen is defined as the last screen that is not a 'repeated' screen,
      that is, a screen that is visited multiple times in places throughout the plotline of screen. A good example of a repeated screen is the 'Self' screens, an option that is often
      available to the player from which they can observe their items, stats, and other elements relating to the System.
      
      This optionNumber or index depends on the added order of screens within the digraph of screen branches. Since repeated screens are always loops (acyclic), all repeated screens
      have a non-repeated root, which should be returned to. The right optionNumber will allow the player to return to the right spot in the story that points to this common loop.
      
      @return int The optionNumber of this current repeated screen. The optionNumber will correspond to the correct screen text file that the player had just come from, a non-repeated screen
      @see MyKeyListener.keyReleased( KeyEvent event )
   */
   public int getLastNonRepeatedScreenOptionNumber() {
      //Get last non-repeated screen
      Screen lastNonRepeatedScreen = null;
      int index = State.screenHistory.size() - 1;
      for( int i = index - 1; i >= 0; i-- ) {
         if( !State.screenHistory.get(i).isRepeated() )
            lastNonRepeatedScreen = State.screenHistory.get(i);
      }
      
      if( lastNonRepeatedScreen == null )
         SOPln( "Error: see getLastNonRepeatedScreenOptionNumber() method" );
      
      //Get option number of last non-repeated screen
      int actualNumberOfOptions = screenInputTextFileDigraph.outDegree().get( State.currentScreen.getTextFileName().replace( ".txt", "" ) ); //This gets how many screens this repeated screen points to
      for( int i = 0; i < actualNumberOfOptions; i++ ) {
         if( screenInputTextFileDigraph.getNeighbors().get( State.currentScreen.getTextFileName() ).get(i).equals( lastNonRepeatedScreen.getTextFileName() ) )
            return i;
      }
      
      SOPln( "Error: last non-repeated screen not found -- see getLastNonRepeatedScreenOptionNumber() method" );
      
      return -1;
   }
   
   /**
      Gets the width of the screen
      
      @return int The width in pixels of the screen
   */
   public static int getWindowWidth() {
      return WIDTH;
   }
   
   /**
      Gets the height of the screen
      
      @return int The height in pixels of the screen
   */
   public static int getWindowHeight() {
      return HEIGHT;
   }
   
   /**
      Faster way to print to console
      
      @param message The message to print
      @see System.out.println( String str )
   */
   public void SOPln( String message ) {
      System.out.println( message );
   }

   /**
      KeyListener for the GameWindow. This listener is added to the GameWindow
      and is never removed.
   */
   private class MyKeyListener implements KeyListener {
   
      @Override
      public void keyPressed( KeyEvent event ) {
         /*
         if( State.currentScreen.getOptions().size() == 1 && event.getKeyCode() == KeyEvent.VK_ENTER ) {
            GraphicsBaseFrame option = State.currentScreen.getGBFOptions()[0];
            option.setBackground( Color.YELLOW );
            option.setOpaque( true );
            repaint();
         }
         */
      }
       
      @Override
      public void keyReleased( KeyEvent event ) {
         if( State.currentScreen.getOptions().size() == 1 && event.getKeyCode() == KeyEvent.VK_ENTER && !State.currentScreen.hasTextBox() ) {
            //If the current screen is a repeated screen, need to find what screen to turn to
            if( State.currentScreen.isRepeated() ) {
               State.optionNumber = getLastNonRepeatedScreenOptionNumber();
               State.changeScreens = true;
            } else {
               State.optionNumber = 0;
               State.changeScreens = true;
            }
         } else if( State.currentScreen.getOptions().size() == 4 && !State.currentScreen.hasTextBox() ) {
            if( event.getKeyCode() == KeyEvent.VK_1 || event.getKeyCode() == KeyEvent.VK_LEFT )
               State.optionNumber = 0;
            else if( event.getKeyCode() == KeyEvent.VK_2 || event.getKeyCode() == KeyEvent.VK_UP )
               State.optionNumber = 1;
            else if( event.getKeyCode() == KeyEvent.VK_3 || event.getKeyCode() == KeyEvent.VK_RIGHT )
               State.optionNumber = 2;
            else if( event.getKeyCode() == KeyEvent.VK_4 || event.getKeyCode() == KeyEvent.VK_DOWN )
               State.optionNumber = 3;
               
            State.changeScreens = true;           
         } else if( State.currentScreen.hasTextBox() && event.getKeyCode() == KeyEvent.VK_ENTER ) {
            if( !State.currentScreen.isTextBoxInitialized() )
               State.currentScreen.initTextBox();
            //Only change screens if text entered is valid (leads to a new screen)
            else if( !State.currentScreen.getTextBox().getText().equals("") ) {
               if( evalTextBoxInput( State.currentScreen.getTextBox().getText(), State.currentScreen.getOptions() ) )
                  State.changeScreens = true;
               else {
                  State.currentScreen.remove( State.currentScreen.getTextBox() );
                  State.currentScreen.addOptionText( TEXT_BOX_LOC_X, TEXT_BOX_LOC_Y );
                  State.currentScreen.revalidate();
                  State.currentScreen.initTextBox();
               }
            } else {
               State.currentScreen.setTextBoxFocus();
            }
         }
      }
       
      @Override
      public void keyTyped( KeyEvent event ) {
         
      }
   }
   
   private class MoveListener implements ActionListener {
   
      @Override
      public void actionPerformed( ActionEvent e ) {
      
      }
      
   }

}