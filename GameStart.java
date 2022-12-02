
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.Color;

public class GameStart {

   public static void main( String[] args ) {
      JFrame window = new JFrame( "TXTRPG" );
      window.setContentPane( new GameWindow() );
      window.setBackground( Color.BLACK );
      window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      window.setResizable( false );
      window.setLayout( null );
      window.pack();
      window.setLocationRelativeTo( null );
      window.setVisible( true );
   }

}