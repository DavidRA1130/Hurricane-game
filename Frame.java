/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hurricanegame;
import javax.swing.JFrame;
import java.awt.*;

/**
 *
 * @author David
 */
public class Frame extends JFrame {
    public static String title = "Hurricane";
    public static Dimension size = new Dimension(700 , 550);

    /**
     * @param args the command line arguments
     */
     public Frame(){
        setTitle(title);
        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        init();
    

    }
     public void init()  {
        setLayout(new GridLayout (1, 1, 0, 0));
        Screen screen = new Screen(this);
        add(screen);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        Frame frame = new Frame();
    }
    
   
    
}
