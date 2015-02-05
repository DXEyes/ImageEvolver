/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageevolver;

import java.awt.Color;
import java.awt.Polygon;

/**
 *
 * @author pcowal15
 */
public class ColorGon extends Polygon{
    Color color;
    private int w, h;
    public ColorGon(int vertices, int w, int h){
        super();
        this.w=w;
        this.h=h;
        changeColor();
        for(int i=0; i<vertices; i++){
            this.addPoint((int)(Math.random()*w), (int)(Math.random()*h));
        }
    }
    public void mutate(){
        if(Math.random()>0.5){
            changeRandomVertex();
        }
        else{
            changeColor();
        }
    }
    private void changeRandomVertex(){
        int v=(int)(Math.random()*this.npoints);
        this.xpoints[v]=(int)(Math.random()*w);
        this.ypoints[v]=(int)(Math.random()*h);
    }
    private void changeColor(){
        color=new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*150));
    }
    public ColorGon duplicate(){
        ColorGon c=new ColorGon(this.npoints,w,h);
        c.color=new Color(this.color.getRGB());
        for(int i=0; i<npoints; i++){
            c.xpoints[i]=xpoints[i];
            c.ypoints[i]=ypoints[i];
        }
        return c;
    }
    public ColorGon duplicateHalf(){
        ColorGon c=new ColorGon(this.npoints,w,h);
        c.color=new Color(this.color.getRGB());
        for(int i=0; i<npoints; i++){
            c.xpoints[i]=xpoints[i]/2;
            c.ypoints[i]=ypoints[i]/2;
        }
        return c;
    }
    @Override
    public String toString(){
        char q='"';
        String s="<polygon style="+q+"fill:rgba("+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+(double)color.getAlpha()/255+")"+q+" points="+q;
        for(int i=0; i<npoints; i++){
            s+=(xpoints[i]*5)+","+(ypoints[i]*5);
            if(i<npoints-1)s+=" ";
        }
        s+=q+" />";
        return s;
    }
}
