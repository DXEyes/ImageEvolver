/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageevolver;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author pcowal15
 */
public class PolygonImage {
    BufferedImage target;
    BufferedImage current;
    BufferedImage best;
    ColorGon bestPoly;
    int bestPolyIndex;
    int bestQuality;
    int polyIndex;
    ColorGon[] polygons;
    public PolygonImage(File targetImage, int polyCount) throws IOException{
        BufferedImage temp=ImageIO.read(targetImage);
        target=new BufferedImage(temp.getWidth(),temp.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d=(Graphics2D)target.getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, target.getWidth(), target.getHeight());
        g2d.drawImage(temp, null, 0, 0);
        
        
        polygons=new ColorGon[polyCount];
        bestPoly=null;
        bestPolyIndex=-1;
        best=ImageIO.read(targetImage);
        bestQuality=Integer.MIN_VALUE;
        for(int i=0; i<polygons.length; i++){
            polygons[i]=new ColorGon(6,target.getWidth(),target.getHeight());
        }
        
    }
    public void iterate(int tries){
        bestPoly=null;
        for(int i=0; i<tries; i++){
            mutate();
        }
        if(bestPoly!=null)
        polygons[bestPolyIndex]=bestPoly;
        //best=new BufferedImage(target.getWidth(),target.getHeight(),BufferedImage.TYPE_INT_RGB);
        /*
        ColorGon temp=polygons[0];
        for(int i=1; i<polygons.length; i++){
            polygons[i-1]=polygons[i];
        }
        polygons[polygons.length-1]=temp;
        */
        Graphics2D g2d=(Graphics2D)best.getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, best.getWidth(), best.getHeight());
        for(int i=0; i<polygons.length; i++){
            g2d.setColor(polygons[i].color);
            g2d.fill(polygons[i]);
        }
        
        bestQuality=0;
        for(int i=0; i<best.getWidth(); i+=2){
            for(int j=0; j<best.getHeight(); j+=2){
                Color n=new Color(best.getRGB(i, j));
                Color t=new Color(target.getRGB(i, j));
                int r=Math.abs(n.getRed()-t.getRed());
                int g=Math.abs(n.getGreen()-t.getGreen());
                int b=Math.abs(n.getBlue()-t.getBlue());
                bestQuality-=r+g+b;
            }
        }
    }
    void mutate(){
        //int polyIndex=(int)Math.random()*polygons.length;
        //polyIndex=polygons.length-1;
        polyIndex=(polyIndex+1)%polygons.length;
        ColorGon poly=polygons[polyIndex].duplicate();
        poly.mutate();
        current=new BufferedImage(target.getWidth()/2,target.getHeight()/2,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d=(Graphics2D)current.getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, current.getWidth(), current.getHeight());
        for(int i=0; i<polygons.length; i++){
            if(i==polyIndex){
                g2d.setColor(poly.color);
                g2d.fill(poly.duplicateHalf());
            }
            else{
                g2d.setColor(polygons[i].color);
                g2d.fill(polygons[i].duplicateHalf());
            }
        }
        int quality=0;
        for(int i=0; i<current.getWidth(); i++){
            for(int j=0; j<current.getHeight(); j++){
                Color n=new Color(current.getRGB(i, j));
                Color t=new Color(target.getRGB(i*2, j*2));
                int r=Math.abs(n.getRed()-t.getRed());
                int g=Math.abs(n.getGreen()-t.getGreen());
                int b=Math.abs(n.getBlue()-t.getBlue());
                quality-=r+g+b;
            }
        }
        
        if(quality>bestQuality){
            bestQuality=quality;
            bestPoly=poly;
            bestPolyIndex=polyIndex;
            System.out.println(quality);
        }
    }
    public void writeToSVG(String filename){
        ArrayList<String> lines=new ArrayList<String>();
        lines.add("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">");
        for(ColorGon c:polygons){
            lines.add(c.toString());
        }
        lines.add("</svg");
        Path file=Paths.get(filename);
        try {
            Files.write(file,lines,StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(PolygonImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
