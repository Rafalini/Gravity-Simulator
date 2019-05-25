import java.awt.*;

public class Star extends Planet
{
    double LeftLifeTime;
    double LifeTime;
    int colorTime;

    public Star(View aView, double Xpos, double Ypos, double Xvel, double Yvel, double mass, double LifeTime)
    {
        super(aView, Xpos, Ypos, Xvel, Yvel, mass);
        this.LeftLifeTime = LifeTime*500;
        this.LifeTime = LifeTime*500;
        colorTime = 249;
        this.myColor = new Color(227, colorTime, 0);
    }
    public double getLifeTime () {return LeftLifeTime;}
    public void decreaseLifeTime ()
    {
        this.LeftLifeTime--;
        double tmp;
        if(LifeTime!=0)
            tmp = 249 * LeftLifeTime/LifeTime;
        else
            tmp = 0;
        colorTime = (int) Math.abs(tmp);
        myColor = new Color(227, colorTime, 0);
    }
}
