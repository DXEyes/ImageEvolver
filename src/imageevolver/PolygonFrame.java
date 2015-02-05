/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageevolver;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author pcowal15
 */
public class PolygonFrame extends JFrame {

    JPanel panel;
    PolygonImage img;
    int i;

    public PolygonFrame() {
        try {
            this.setBackground(Color.WHITE);
            this.setSize(400,400);
            JFileChooser filechooser = new JFileChooser();
            int returnVal = filechooser.showOpenDialog(this);
            String s=JOptionPane.showInputDialog("Enter the desired number of polygons:");
            int poly=Integer.parseInt(s);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File f = filechooser.getSelectedFile();
                try {
                    img = new PolygonImage(f, poly);
                    
                    
                } catch (FileNotFoundException ex) {
                    img = new PolygonImage(new File("Mona-Lisa-150x150.jpg"), poly);
                } catch (IOException ex) {
                    img = new PolygonImage(new File("Mona-Lisa-150x150.jpg"), poly);
                }

            }

            i = 0;
            //this.add(new JLabel(new ImageIcon(img.best)));
        } catch (IOException ex) {
            Logger.getLogger(PolygonFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void repaint() {

        img.iterate(10);
        this.getGraphics().drawImage(img.target, 100, 100, null);
        this.getGraphics().drawImage(img.best, 110 + img.target.getWidth(), 100, null);
        i++;
        if (i % 1000 == 0) {
            img.writeToSVG("image.svg");
        }
    }
}
