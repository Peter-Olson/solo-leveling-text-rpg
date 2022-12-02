/**
   Screen.java
   
   Holds the information of a screen, the location, and the options for that screen. Each Screen has one or more options that are used to direct
   to a new screen, see digraph map of screens. Some Screen objects use data from the State to alter the text, location, or options.
   
   @author Peter Olson
   @version 06/11/19
*/

import javax.swing.JPanel;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Robot;
import java.awt.AWTException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Screen extends JPanel {
   
   private int width;
   private int height;
   private String textFileName;
   private String text;
   private Coord location;
   private ArrayList<String> options;
   
   private GraphicsBaseFrame gbfText;
   private GraphicsBaseFrame[] gbfOptions;
   private GraphicsBaseFrame gbfLocation;
   
   private final double X_SCALE_MID = 2.0;
   private final double X_SCALE_SOFT_LEFT = 3.0;
   private final double X_SCALE_MID_LEFT = 4.0;
   private final double X_SCALE_LEFT_MID = 6.5;
   private final double X_SCALE_LEFT = 20.0;
   private final double X_SCALE_HARD_LEFT = 40.0;
   private final double X_SCALE_FULL_LEFT = GameWindow.getWindowWidth();
   private final double X_SCALE_MID_RIGHT = 1.5;
   private final double X_SCALE_RIGHT_MID = 1.35;
   private final double X_SCALE_RIGHT = 1.2;
   private final double X_SCALE_HARD_RIGHT = 1.1;
   private final double X_SCALE_FULL_RIGHT = 1.0;  //Be careful using this one, most likely will cause component to be drawn off screen
   
   private final double Y_SCALE_MID = 2.0;
   private final double Y_SCALE_SOFT_UP = 3.0;
   private final double Y_SCALE_MID_UP = 4.0;
   private final double Y_SCALE_UP = 20.0;
   private final double Y_SCALE_HARD_UP = 40.0;
   private final double Y_SCALE_FULL_UP = GameWindow.getWindowHeight();
   private final double Y_SCALE_MID_DOWN = 1.5;
   private final double Y_SCALE_DOWN_MID = 1.35;
   private final double Y_SCALE_DOWN = 1.2;
   private final double Y_SCALE_HARD_DOWN = 1.1;
   private final double Y_SCALE_FULL_DOWN = 1.0;   //Be careful using this one, most likely will cause component to be drawn off screen
   
   private final String REG_FONT = "Constantia";
   private final String RETRO_FONT = "A Goblin Appears!";
   private final int PLAIN = Font.PLAIN;
   private final int REG_FONT_SIZE = 32;
   private final int TITLE_FONT_SIZE = 64;
   private String imageName = "";
   
   private final String TEXT_BOX_PROMPT = "What would you like to do?";
   private JTextField textBox;
   private boolean hasTextBox;
   private boolean textBoxInitialized = false;
   
   private boolean repeated;
   
   public Screen() {
      //dummy values
      width = 0; height = 0; text = ""; location = null; options = null;
      add( new GraphicsBaseFrame( ) );
   }
   
   public Screen( int width, int height ) {
      this.width = width;
      this.height = height;
      add( new GraphicsBaseFrame( width, height ) );
      setBackground( Color.BLACK );
   }
   
   /**
      Create a screen with GraphicsBaseFrame images. Screens should be as wide and high as the painting space, and are initiated from a text file
      with information on the text, location, options, and text box options, if applicable.
      
      @param width The width of the Screen
      @param height The height of the Screen
      @param textFileName The name of the text file that has the information on the text, location, options, and text box options (if applicable)
      @param text The text should use \n line breaks for determining the height of the text
      @param location A coordinate with an x and y value, or row and column. These correspond to the location of the player in the map
      @param options A list of Strings, which are descriptions of the options
      @param hasTextBox True if this Screen has a text box, false otherwise
      @param repeated True if this Screen is repeated elsewhere in the Screen plotline, false otherwise
      @see JPanel.setLayout( LayoutManager mgr )
      @see JPanel.add( Component comp )
      @see JPanel.getInsets()
      @see JPanel.getPreferredSize()
      @see JPanel.setBounds( int x, int y, int width, int height )
      @see addLocationText( double xScale, double yScale )
      @see addOptionText( double xScale, double yScale, int index )
      @see addOptionText( double xScale, double yScale )
      @see JPanel.setBackground( Color c )
   */
   public Screen( int width, int height, String textFileName, String text, Coord location, ArrayList<String> options, boolean hasTextBox, boolean repeated ) {
      this.width = width;
      this.height = height;
      this.textFileName = textFileName;
      this.text = text;
      this.location = location;
      this.options = options;
      this.hasTextBox = hasTextBox;
      this.repeated = repeated;
      
      //Add main text
      setLayout( null );
      
      if( /*text.contains( "@@CODE" ) ||*/ text.contains( "@" ) )
         text = setVariableText();
      
      GraphicsBaseFrame gbf;
      if( !textFileName.equals( "title" ) ) gbf = new GraphicsBaseFrame( 0, 0, width, height, text, REG_FONT, PLAIN, REG_FONT_SIZE, textFileName, 0 );
      else                                gbf = new GraphicsBaseFrame( 0, 0, width, height, text, RETRO_FONT, PLAIN, TITLE_FONT_SIZE, textFileName, 0 );
      add( gbf );
      
      Insets insets = getInsets();
      Dimension size = gbf.getPreferredSize();
      if( !textFileName.equals( "title" ) ) gbf.setBounds( 20 + insets.left, 15 + insets.top, size.width, size.height );    
      else                                gbf.setBounds( insets.left, insets.top, size.width, size.height ); 
      gbfText = gbf;
      
      //Add location
      if( !textFileName.equals( "title" ) )
         addLocationText( X_SCALE_HARD_RIGHT, Y_SCALE_HARD_UP );
      
      //Add options
      if( options.size() == 1 && textFileName.equals( "title" ) )
         addOptionText( X_SCALE_LEFT_MID, Y_SCALE_MID, 0 );
      else if( options.size() == 1 && !hasTextBox )
         addOptionText( X_SCALE_LEFT, Y_SCALE_DOWN, 0 );
      else if( options.size() == 4 && !hasTextBox ) {
         addOptionText( X_SCALE_LEFT, Y_SCALE_DOWN_MID, 0 );
         addOptionText( X_SCALE_MID_LEFT, Y_SCALE_DOWN_MID, 1 );
         addOptionText( X_SCALE_LEFT, Y_SCALE_DOWN, 2 );
         addOptionText( X_SCALE_MID_LEFT, Y_SCALE_DOWN, 3 );
      } else if( hasTextBox ) {
         addOptionText( X_SCALE_LEFT, Y_SCALE_DOWN );
      }
      
      setBackground( Color.BLACK );
      repaint();
      
      //@@DEBUG
      //setBorder( BorderFactory.createLineBorder( Color.BLUE ) );
      
   }
      
   /**
      Set up the text for the location
      
      @param scaleX The factor for positioning this component on the screen in the x direction
      @param scaleY The factor for positioning this component on the screen in the y direction
      @see String.valueOf( int value )
      @see Coord.getX()
      @see Coord.getY()
      @see GameWindow.getWindowWidth()
      @see GameWindow.getWindowHeight()
      @see JPanel.add( Component c )
      @see JPanel.getInsets()
      @see JPanel.getPreferredSize()
      @see JPanel.repaint()
   */
   private void addLocationText( double scaleX, double scaleY ) {
      String locationText = "(" + String.valueOf( location.getX() ) + ", " + String.valueOf( location.getY() ) + ")";
      GraphicsBaseFrame gbf = new GraphicsBaseFrame( (int)(GameWindow.getWindowWidth()/scaleX), (int)(GameWindow.getWindowHeight()/scaleY), GameWindow.getWindowWidth(), GameWindow.getWindowHeight(), locationText, REG_FONT, PLAIN, REG_FONT_SIZE, textFileName, 0 );
      add( gbf );
      
      Insets insets = getInsets();
      Dimension size = gbf.getPreferredSize();
      gbf.setBounds( (int)(GameWindow.getWindowWidth()/scaleX) + insets.left, (int)(GameWindow.getWindowHeight()/scaleY) + insets.top, size.width, size.height );
      gbfLocation = gbf;
      
      repaint();
   }
   
   /**
      Set up the text format for listing the options
      
      @param scaleX The scaling for the x coordinate position of this text
      @param scaleY The scaling for the y coordinate position of this text
      @param index The enumeration of this text
      @see ArrayList.get( int index )
      @see String.valueOf( char ch )
   */
   private void addOptionText( double scaleX, double scaleY, int index ) {
      final int LOWER_CASE_A = 97;
      gbfOptions = new GraphicsBaseFrame[ options.size() ];

      String optionText = "";
      if( options.size() > 1 )
         optionText += String.valueOf( index + 1 ) + ") " + options.get( index );
      else
         optionText += options.get( index );

      //If the if statement for this option is false, do not include this option
      if( optionText.contains( "@@CODE" ) ) {
         String codeText = optionText.substring( optionText.indexOf( "@@CODE" ) + "@@CODE".length(), optionText.lastIndexOf( "@@CODE" ) );
         codeText = codeText.replaceAll( "\\|\\|", "" );
         if( !analyzeIfCode( codeText ) )
            return;
         optionText = optionText.substring( optionText.lastIndexOf( "@@CODE" ) + "@@CODE".length() ); //Get rid of code stuff
      }

      GraphicsBaseFrame gbf = new GraphicsBaseFrame( (int)(GameWindow.getWindowWidth()/scaleX), (int)(GameWindow.getWindowHeight()/scaleY), GameWindow.getWindowWidth(), GameWindow.getWindowHeight(), optionText, REG_FONT, PLAIN, REG_FONT_SIZE, textFileName, index );
      add( gbf );
      
      Insets insets = getInsets();
      Dimension size = gbf.getPreferredSize();
      gbf.setBounds( (int)(GameWindow.getWindowWidth()/scaleX) + insets.left, (int)(GameWindow.getWindowHeight()/scaleY) + insets.top, size.width, size.height );
      gbfOptions[ index ] = gbf;
      
      repaint();
   }
   
   /**
      Set up the JTextField and paint it to the screen. Note that this method does not start the JPanel focused
      
      @param scaleX The scaling of where to place this component in the x direction
      @param scaleY The scaling of where to place this component in the y direction
      @see JPanel.add( Component comp )
      @see JPanel.getInsets()
      @see JPanel.setBounds( int x, int y, int width, int height )
      @see GameWindow.getWindowWidth()
      @see GameWindow.getWindowHeight()
      @see initTextBox()
   */
   public void addOptionText( double scaleX, double scaleY ) {
      JTextField textBox = new JTextField( TEXT_BOX_PROMPT );
      add( textBox );
      
      Insets insets = getInsets();
      Dimension size = new Dimension( 200, 40 );
      textBox.setBounds( (int)(GameWindow.getWindowWidth()/scaleX) + insets.left, (int)(GameWindow.getWindowHeight()/scaleY) + insets.top, size.width, size.height );
      
      this.textBox = textBox;
      
      repaint();
   }
   
   /**
      For text that has code in it, set the final text resolving the code based on variables, if statements, and different blocks of text
      as designated by the if statement code. See examples below:
      
      Jin-Woo swings his @currentWeapon@ at the monster. @@CODE|@STR@>10&&@PCN@>20|The @currentWeapon@ strikes, but you notice that you
      only graze the surface of the monster's tough skin |@STR@>10&&@PCN@<=20|The @currentweapon@ hits. ||You swing the @currentWeapon@,
      but you aren't able to do any significant damage. @@CODE
      
      //If the main character's stats are STR = 15, PCN = 30, and uses a battleaxe, the following would be written to the screen:
      Jin-Woo swings his battleaxe at the monster. The battleaxe strikes, but you notice that you only graze the surface of the monster's tough skin.
      
      Note that text can also be placed after the last @@CODE or the text can contain multiple code segments. Note that if statements can be nested,
      and that if statement conditions can use ands and ors, &&s and ||s that is, as well as multiple conditions such as >, <, >=, <=, ==, &&, ||,
      and = (corresponds to .equals) for variables of the basic different data types
      
      Additionally, consider the following example, which contains code that simply changes the screen destinations:
      
      You walk into the crystal cavern.@@CODE|@PCN@>50&&@quest:Sovereign's Lair@=Sovereign's Lair|<ch8c1.txt>||<ch7b2dg3.txt>@@CODE
      
      This codes adds the text from the given text file. Additional text can be added if placed after the end of the @@CODE spot.
      Variables stored in lists can be accessed as Strings, but are not currently accessible as variables objects @@FIX
      Note that certain variables have the abilities to specify additional information by using colons (:). For instance,
      
      @stats:str@, @equips:weapon@, @quest:dg4@, @location:r7c9@, etc
      
      Note the available variables that can be accessed below. Also note that these variables must be spelled correctly, and have correct capitalizations.
      
      @@ADD variable list here
      Level, Class, Title, HP, MP, Tiredness, Stats, Points, Reduction, Skills, Equipped, Exp, Inventory, TempInventory,
      Quest, Location
      
      @return String The compiled text after taking into account coding edits and substitutions
      @see analyzeTextCode( String codeText )
      @see analyzeVariableText( String variableText )
      @see String.split( String delimiter )
   */
   private String setVariableText() {
      String finalText = "";
      String[] finalTextSplits = text.split( "@@CODE" );
      for( int i = 0; i < finalTextSplits.length; i++ ) {
         if( i % 2 == 1 )
            finalText += analyzeTextCode( finalTextSplits[i] );
         else
            finalText += analyzeVariableText( finalTextSplits[i] );
      }
      
      return finalText;
   }
   
   /**
      Analyzes and resolves the text through substitution using if statement code and variable code
      
      Note that |s indicate sections of if statement logic (or else if, or else)
      
      @param codeText The text to be analyzed
      @return String The resultant text after substituting based on if statement logic and variables
      @see analyzeVariableText( String codeText )
      @see analyzeIfCode( String code )
      @see String.split( String delimiter )
      @see String.contains( String regex )
   */
   private String analyzeTextCode( String codeText ) {
      String finalText = "";
      
      if( !codeText.contains( "|" ) )
         finalText += analyzeVariableText( codeText );
      else {
         String[] ifSplits = codeText.split( "\\|" );
         finalText += ifSplits[0];
         for( int i = 1; i < ifSplits.length; i += 2 ) {
            if( analyzeIfCode( ifSplits[i] ) ) {
               finalText += analyzeVariableText( ifSplits[ i + 1 ] );
               break;
            }
         }
      }
      
      return finalText;
   }
   
   /**
      Analyze text with variables in it and substitutes variable values in for variables within the text
      
      @param codeText The text that may have variables in it that need to be resolved to values
      @return String The text with variables replaced with values
      @see getVariable( String variableName )
      @see String.contains( String regex )
      @see String.length()
      @see String.indexOf( String str, int index )
      @see String.substring( int startIndex, int endIndex )
   */
   private String analyzeVariableText( String codeText ) {
   
      //If text is a reference to a new text file @@NOTE: Must have no code and nothing else besides the text file name; eg: <p6.txt>
      if( codeText.contains("<") || codeText.contains(">") ) {
         codeText = codeText.replaceAll(">","");
         codeText = codeText.replaceAll("<","");
         
         String fileName = codeText;
         if( !fileName.contains(".txt") )
            codeText += ".txt";
         
         String resultText = "\\n";
         Scanner scanner = null;
         try {
            scanner = new Scanner( new File( fileName ) );
            boolean stopReading = false;
            while( scanner.hasNextLine() && !stopReading ) {
               String newLine = scanner.nextLine();
               if( newLine.equals( "@@END" ) ) {
                  resultText += "\\n";
                  stopReading = true;
               } else {
                  resultText += newLine;
               }
            }
         } catch( FileNotFoundException e ) {
            e.printStackTrace();
         }
         
         return resultText;
      }
      
      //No code
      if( !codeText.contains( "@" ) )
         return codeText;
      
      //Analyze code
      String resultText = "";
      
      String tempText = codeText;
      while( tempText.length() > 0 ) {
         
         int indexOfFirstAnd = 0;
         int indexOfSecondAnd = 0;
         if( tempText.contains("@") ) {
            indexOfFirstAnd = codeText.indexOf( "@" );
            indexOfSecondAnd = codeText.indexOf( "@", indexOfFirstAnd + 1 );
            
            String variableName = codeText.substring( indexOfFirstAnd + 1, indexOfSecondAnd );
            
            //Determine if this is a set call or a get. If set, set the variable to the new value. If a get, get the variable and convert it to a String
            if( !variableName.contains("SET") ) {
               Object obj = getVariable( variableName );
               resultText += tempText.substring( 0, indexOfFirstAnd ) + obj.toString();
               
               //Format list
               resultText = resultText.replaceAll( "\\[|\\]", "" );
               int lastCommaIndex = resultText.lastIndexOf( "," );
               if( lastCommaIndex >= 0 ) {
                  String firstHalf = resultText.substring( 0, lastCommaIndex + 2 );
                  String secondHalf = resultText.substring( lastCommaIndex + 2 );
                  resultText = firstHalf + "and " + secondHalf;
               }
               
               //If the list is empty
               if( obj.toString().equals( "[]" ) )
                  resultText += "nothing";
               
            } else {
               String[] setPieces = variableName.split( ":" );
               String newValue = setPieces[2];
               
               //Set class
               Object newObject;
               if( newValue.equals( "true" ) || newValue.equals( "false" ) )
                  newObject = Boolean.valueOf( newValue );
               else if( newValue.matches( ".*\\d.*" ) && newValue.contains( "." ) )
                  newObject = Double.valueOf( newValue );
               else if( newValue.matches( ".*\\d.*" ) )
                  newObject = Integer.valueOf( newValue );
               else if( newValue.contains( "'" ) )
                  newObject = newValue.charAt(0);
               else
                  newObject = String.valueOf( newValue );
               
               State.variables.replace( setPieces[1], newObject );
            }
            
         } else {
            resultText += tempText;
            break;
         }
         
         tempText = tempText.substring( indexOfSecondAnd + 1 );
      }
      
      return resultText;
   }
   
   /**
      Analyze if statement code by determining a resultant boolean
      
      Note that if statement code uses the following operators: >,<,>=,<=,=,==,%%,## and can be multi-conditonal,
      as determined by the operators $$ and &&. The ! operator and unary operators are not supported (yet)
      
      @param code The if statement code to analyze
      @return boolean True if the if statement code evaluates to true, false otherwise
      @see getStringBoolTruth( String boolExpression )
      @see String.trim()
      @see String.split( String delimiter )
      @see String.equals( Object obj )
   */
   public static boolean analyzeIfCode( String code ) {
      code = code.trim();
      
      //Else statement
      if( code.equals("") )
         return true;
      
      boolean isTrue = true;
      
      //Get an ordered list of the evaluated boolean String expressions
      String[] multBools = code.split( "&&|[\\$\\$]" ); //note that $$ is being used as a boolean or sign ||
      boolean[] bools = new boolean[ multBools.length ];
      for( int i = 0; i < multBools.length; i++ )
         bools[i] = getStringBoolTruth( multBools[i] );
      
      //Get an ordered list of the logic operators
      String[] listOperators = code.split( "((?<=&&)|(?=&&))|((?<=[\\$\\$])|(?=[\\$\\$]))" ); //note that $$ is being used as a boolean or sign ||
      
      //The number of operators is equal to the floor of half of the number of conditions. If there are no operators, need to still make the size of the array at least one
      int numOperators = 0;
      if( listOperators.length > 0 )
         numOperators = (int)( listOperators.length / 2 );
      else
         numOperators = 1;
         
      boolean[] isAnd = new boolean[ numOperators ];
      for( int i = 0; i < listOperators.length; i++ ) {
         if( i % 2 == 1 ) {
            if( listOperators[i].equals( "&&" ) )
               isAnd[ (int)(i/2) ] = true;
            else
               isAnd[ (int)(i/2) ] = false;  
         }
      }
      
      //Alternate between the boolean values and the logic operators to see what the resulting boolean value is
      boolean temp = isTrue = bools[0];
      for( int i = 1, j = 0, k = 0; i < bools.length || j < isAnd.length; k++ ) {
         // Starting off on the second boolean term, since temp is initialized to the first
         if( k % 2 == 0 ) {
            temp = bools[i++];
         } else {
            if( isAnd[j++] )
               isTrue &= temp;
            else
               isTrue |= temp;
         }
      }
      
      return isTrue;
   }
   
   /**
      Given a conditional String expression, get the resulting boolean value
      
      Note that if statement code uses the following operators: >,<,>=,<=,=,==,%%,## and can be multi-conditonal,
      as determined by the operators || and &&. The ! operator and unary operators are not supported
      
      @param boolExpression The String conditonal of the if statement. This will be translated into actual code, that is,
                            a variable compared to another variable producing a boolean value
      @return boolean True if the expression is true, false otherwise
      @see getVariable( String variableName )
      @see String.lastIndexOf( String str )
      @see String.indexOf( String str )
      @see String.valueOf( char ch )
      @see String.charAt( int index )
      @see String.substring( int index )
      @see String.equals( Object obj )
      @see String.matches( String regex )
      @see String.contains( String regex )
      @see Boolean.valueOf( String str )
      @see Double.valueOf( String str )
      @see Integer.valueOf( String str )
      @see String.valueOf( String str )
      @see Object.getClass()
   */
   private static boolean getStringBoolTruth( String boolExpression ) {
      int indexOfComparator = boolExpression.lastIndexOf( "@" );
      int indexOfFirstAnd = boolExpression.indexOf( "@" );
      
      Object obj = getVariable( boolExpression.substring( indexOfFirstAnd + 1, indexOfComparator ) );
      String operator = String.valueOf( boolExpression.charAt( indexOfComparator + 1 ) );
      
      //Extend operators that are longer than one character
      if( boolExpression.charAt( indexOfComparator + 2 ) == '=' ) {
         operator += "=";
         indexOfComparator++;
      } else if( boolExpression.charAt( indexOfComparator + 2 ) == '#' ) {
         operator += "#";
         indexOfComparator++;
      } else if( boolExpression.charAt( indexOfComparator + 2 ) == '%' ) {
         operator += "%";
         indexOfComparator++;
      }
      String comparison = boolExpression.substring( indexOfComparator + 2 );
      
      //Determine class of comparison
      Object obj2;
      if( comparison.equals( "true" ) || comparison.equals( "false" ) )
         obj2 = Boolean.valueOf( comparison );
      else if( comparison.matches( ".*\\d.*" ) && comparison.contains( "." ) )
         obj2 = Double.valueOf( comparison );
      else if( comparison.matches( ".*\\d.*" ) )
         obj2 = Integer.valueOf( comparison );
      else if( comparison.length() == 1 )
         obj2 = comparison.charAt(0);
      else
         obj2 = String.valueOf( comparison );
      
      //Show error message if classes are not equal
      if( obj.getClass() != obj2.getClass() )
         SOPln( "ERROR: Code in text uses illegal comparison" );
      
      //Determine comparisons
      if( obj instanceof Boolean ) {
         if( operator.equals("##") )
            return (boolean)obj && (boolean)obj2;
         else
            return (boolean)obj || (boolean)obj2;
      } else if( obj instanceof Double || obj instanceof Integer || obj instanceof Character ) {
         if( operator.equals(">") ) {
            if( obj instanceof Double )
               return (double)obj > (double)obj2;
            if( obj instanceof Integer )
               return (int)obj > (int)obj2;
            if( obj instanceof Character )
               return (char)obj > (char)obj2;
         } else if( operator.equals("<") ) {
            if( obj instanceof Double )
               return (double)obj < (double)obj2;
            if( obj instanceof Integer )
               return (int)obj < (int)obj2;
            if( obj instanceof Character )
               return (char)obj < (char)obj2;
         } else if( operator.equals(">=") ) {
            if( obj instanceof Double )
               return (double)obj >= (double)obj2;
            if( obj instanceof Integer )
               return (int)obj >= (int)obj2;
            if( obj instanceof Character )
               return (char)obj >= (char)obj2;
         } else if( operator.equals("<=") ) {
            if( obj instanceof Double )
               return (double)obj <= (double)obj2;
            if( obj instanceof Integer )
               return (int)obj <= (int)obj2;
            if( obj instanceof Character )
               return (char)obj <= (char)obj2;
         } else if( operator.equals( "==" ) ) {
            if( obj instanceof Double )
               return (double)obj == (double)obj2;
            if( obj instanceof Integer )
               return (int)obj == (int)obj2;
            if( obj instanceof Character )
               return (char)obj == (char)obj2;
         } else /*if( operator.equals( "!=" ) )*/ {
            if( obj instanceof Double )
               return (double)obj != (double)obj2;
            if( obj instanceof Integer )
               return (int)obj != (int)obj2;
            if( obj instanceof Character )
               return (char)obj != (char)obj2;
         }
      } else {
         if( operator.contains( "!" ) )
            return !obj.equals( obj2 );
         else
            return obj.equals( obj2 );
      }
      
      SOPln( "Error: should not be reached" );
      return false;
   }
   
   /**
      Get the variable value of a global variable from this class given a String name of that variable
      
      @param variableName The name of the variable whose value is to be accessed
      @return Object The value of the variable
      @see Class.forName( String name )
      @see Class.newInstance()
      @see Class.getDeclaredMethods()
      @see Method.getName()
      @see Method.setAccessible( boolean isAccessible )
      @see Method.invoke( Object t )
      @see InvocationTargetException.printStackTrace()
      @see String.substring( int startIndex, int endIndex )
      @see String.toUpperCase()
      @see String.equals( Object obj )
   */
   private static Object getVariable( String variableName ) {
   
      //If variable points to another variable, first get the object of the first variable, handle the rest later
      boolean isLinked = false;
      String variableLink = "";
      if( variableName.contains(":") ) {
         variableLink = variableName.substring( variableName.indexOf(":") + 1, variableName.length() );
         variableName = variableName.substring( 0, variableName.indexOf(":") );
         isLinked = true;
      }
      
      //Make variable's first letter uppercase
      variableName = variableName.substring(0, 1).toUpperCase() + variableName.substring(1); 

      //Pull the variable from the map of variables that are accessed in the State via lambda functions. If the variable is a list, pull the item from the list
      if( !isLinked )
         return State.variables.get( variableName );
      else {
         ArrayList<String> list = (ArrayList<String>)( State.variables.get( variableName ) );
         int index = list.indexOf( variableLink );
         if( index >= 0 )
            return list.get( index );
         else
            return "";
      }
   }
   
   /**
      Initializes the text box by setting its focus, reseting the text in the box, and giving it a listener once the user
      presses enter, which then gives the focus back to the GameWindow
      
      @see JTextField.requestFocus()
      @see JTextField.addFocusListener( FocusListener listener )
      @see JTextField.setText( String text )
      @see FocusListener.focusGained( FocusEvent e )
      @see FocusListener.focusLost( FocusEvent e )
      @see JTextField.addKeyListener( KeyListener listener )
      @see KeyListener.keyPressed( KeyEvent event )
      @see KeyListener.keyReleased( KeyEvent event )
      @see KeyListener.keyTyped( KeyEvent event )
      @see KeyEvent.getKeyCode()
      @see JTextField.transferFocusBackward()
      @see Robot.keyPress( KeyEvent event )
      @see Robot.keyRelease( KeyEvent event )
      @see AWTException.printStackTrace()
      @see JTextField.setEditable( boolean isEditable )
      @see addOptionText( double scaleX, double scaleY )
   */
   public void initTextBox() {
      textBox.requestFocus();
      
      textBox.addFocusListener( new FocusListener() {
         @Override
         public void focusGained( FocusEvent e ) {
            textBox.setText("");
         }
         
         @Override
         public void focusLost( FocusEvent e ) {
         
         }
      });
      
      textBox.addKeyListener( new KeyListener() {
         @Override
         public void keyPressed( KeyEvent event ) {
         
         }
         
         @Override
         public void keyReleased( KeyEvent event ) {
            if( event.getKeyCode() == KeyEvent.VK_ENTER ) {
               textBox.transferFocusBackward(); //shift focus back to game window
            
               //Simulate 'enter' being pressed from the game window -- the shift in focus is required in order to correctly set text and type and then return input
               try {
                  Robot robot = new Robot();
                  robot.keyPress( KeyEvent.VK_ENTER );
                  robot.keyRelease( KeyEvent.VK_ENTER );
               } catch( AWTException e ) {
                  e.printStackTrace();
               }
            }
         }
         
         @Override
         public void keyTyped( KeyEvent event ) {
            
         }
      });
      
      textBox.setEditable( true );
      
      textBoxInitialized = true;
   }
   
   public int getWidth() {
      return width;
   }
   
   public int getHeight() {
      return height;
   }
   
   public String getTextFileName() {
      return textFileName;
   }
   
   public String getText() {
      return text;
   }
   
   public Coord getCoord() {
      return location;
   }
   
   public ArrayList<String> getOptions() {
      return options;
   }
   
   public GraphicsBaseFrame getGBFText() {
      return gbfText;
   }
   
   public GraphicsBaseFrame[] getGBFOptions() {
      return gbfOptions;
   }
   
   public GraphicsBaseFrame getGBFLocation() {
      return gbfLocation;
   }
   
   public JTextField getTextBox() {
      return textBox;
   }
   
   public boolean hasTextBox() {
      return hasTextBox;
   }
   
   public boolean isRepeated() {
      return repeated;
   }
   
   public void setTextBoxFocus() {
      textBox.requestFocus();
   }
   
   public boolean isTextBoxInitialized() {
      return textBoxInitialized;
   }
   
   public static void SOPln( String message ) {
      System.out.println( message );
   }
   
}