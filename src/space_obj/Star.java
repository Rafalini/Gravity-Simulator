package space_obj;

import display.*;
import java.awt.*;

public class Star extends Planet
{
    double leftLifeTime;
    double lifeTime;
    int colorTime;

    public Star(View aView, double Xpos, double Ypos, double Xvel, double Yvel, double mass, double lifeTime)
    {
        super(aView, Xpos, Ypos, Xvel, Yvel, mass);
        this.leftLifeTime = lifeTime*500;
        this.lifeTime = lifeTime*500;
        colorTime = 249;
        this.myColor = new Color(227, colorTime, 0);
    }
    public double getLifeTime () {return leftLifeTime;}
    public void decreaseLifeTime ()
    {
        this.leftLifeTime--;
        double tmp;
        if(lifeTime!=0)
            tmp = 249 * leftLifeTime/lifeTime;
        else
            tmp = 0;
        colorTime = (int) Math.abs(tmp);
        myColor = new Color(227, colorTime, 0);
    }
}
