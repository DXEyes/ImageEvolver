/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageevolver;

import javax.swing.JFrame;

/**
 *
 * @author pcowal15
 */
public class ImageEvolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PolygonFrame p=new PolygonFrame();
        p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.setVisible(true);
        
        while(true){
            p.repaint();
        }
    }
}
