package space_obj;

import mechanics.*;
//import space_obj.*;
import display.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Planet
{
    private double Xpos, Ypos;
    private double Xvel, Yvel;
    private double mass, r;
    private Ellipse2D.Double Elipse;
    private ArrayList<Dimension>  LinePoints;

    static final int LinePointsLimit=600;
    Color myColor;

    public Planet(View aView, double Xpos, double Ypos, double Xvel, double Yvel, double mass)
    {
        myColor = new Color(0, 76, 153);
        this.Xpos = Xpos;
        this.Ypos = Ypos;
        this.Xvel = Xvel;
        this.Yvel = Yvel;
        if(mass<0) mass*=-1;
        if(mass == 0) mass = 1000;
        this.mass = mass;
        double dim = Math.pow(this.mass,(double)1/3);
        r = dim/2;
        LinePoints = new ArrayList<Dimension>();
        Elipse = new Ellipse2D.Double(Xpos-r+aView.getWidth()/2, aView.getHeight()/2 -Ypos - r, dim, dim);
        if(aView.getSelectedLogItem()>1)
            aView.LogEvent("Utworzono objekt! X=" +(int)this.Xpos+" Y="+(int)this.Ypos+" Vx="+this.Xvel+" Vy="+this.Yvel+" M="+this.mass+"\n");
    }
    public Planet(View aView, double Xpos, double Ypos, double Xvel, double Yvel, double mass, Color myColor)
    {
        this.myColor = myColor;
        this.Xpos = Xpos;
        this.Ypos = Ypos;
        this.Xvel = Xvel;
        this.Yvel = Yvel;
        if(mass<0) mass*=-1;
        if(mass == 0) mass = 1000;
        this.mass = mass;
        double dim = Math.pow(this.mass,(double)1/3);
        r = dim/2;
        LinePoints = new ArrayList<Dimension>();
        Elipse = new Ellipse2D.Double(Xpos-r+aView.getWidth()/2, aView.getHeight()/2 -(Ypos+r), dim, dim);
        if(aView.getSelectedLogItem()>1)
            aView.LogEvent("Utworzono objekt! X=" +(int)this.Xpos+" Y="+(int)this.Ypos+" Vx="+this.Xvel+" Vy="+this.Yvel+" M="+this.mass+"\n");
    }

    public Ellipse2D.Double getElipse() {   return Elipse;  }
    public double getXpos() { return Xpos; }
    public double getYpos() { return Ypos; }
    public double getXvel() { return Xvel; }
    public double getYvel() { return Yvel; }
    public double getMass() { return mass; }
    public double getRadious() { return r; }
    public Color getColor() { return myColor;}

    public void Xcorrection(double Cor) {this.Xpos += Cor; }
    public void Ycorrection(double Cor) {this.Ypos += Cor; }
    public void PosTracking()
    {
        if(LinePoints.size()>Planet.LinePointsLimit)
           LinePoints.remove(0);

        Dimension dim = new Dimension();
        dim.setSize(Xpos, Ypos);
        LinePoints.add(dim);
    }

    public void vXcorrection(double Cor) {this.Xvel = this.Xvel + Cor;}
    public void vYcorrection(double Cor) {this.Yvel = this.Yvel + Cor;}

    public void redraw(View aView) { Elipse = new Ellipse2D.Double(Xpos-r+aView.getWidth()/2, aView.getHeight()/2-(Ypos+r), 2*r, 2*r);}

    public void impact(Planet planetka) //planetka uderza w obiekt
    {
        this.Xpos = (this.Xpos*this.mass + planetka.getXpos()*planetka.getMass())/ (this.mass+planetka.getMass());
        this.Ypos = (this.Ypos*this.mass + planetka.getYpos()*planetka.getMass())/ (this.mass+planetka.getMass());
        this.Xvel = (this.Xvel*this.mass + planetka.getXvel()*planetka.getMass())/ (this.mass+planetka.getMass());
        this.Yvel = (this.Yvel*this.mass + planetka.getYvel()*planetka.getMass())/ (this.mass+planetka.getMass());
        this.mass += planetka.getMass();
        this.r = Math.pow(this.mass,(double)1/3)/2;
    }

    public ArrayList<Dimension> GetPath() {return LinePoints;}
}
