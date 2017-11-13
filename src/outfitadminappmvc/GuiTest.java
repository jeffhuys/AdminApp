/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outfitadminappmvc;

import javax.swing.JFrame;
//import sun.applet.Main;

/**
 *
 * @author Razzy
 */
public class GuiTest {
       private JFrame frame;                 // Frame maken en onthouden
       private static GuiTest m = new GuiTest();   // Statisch object onthouden van Main
    
      public static void main(String[] args) {
            m.start();
    }
      
    public static GuiTest getMain() {
		// Zichzelf returnen (daarom is dit een static)
		return m;
	}
      
    public void start() {        
		//frame = new -zet hier eigen gui neer-
		
                frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);         // en maakt deze zichtbaar
	}
}
