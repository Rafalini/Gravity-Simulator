package mechanics;

import space_obj.*;
import display.*;
import java.awt.*;
import java.util.ArrayList;

public class Model
{
    public Model() {}

    public void next_simulation_setp(ArrayList<Planet> planets, double timebase, View aView)
    {
        int i_timebase = (int)timebase;
        timebase = timebase * 0.001;

        impact_realisation(planets, aView);

        movement_realisation(planets, timebase, aView);

        deageing_stars(planets, i_timebase, aView);
    }

    private void impact_realisation(ArrayList<Planet> planets, View aView)
    {
        double sqare_range = 0;
        int arrSize = planets.size();
        for (int i = 0; i < arrSize; i++)
            for (int j = i + 1; j < arrSize; j++)
            {
                sqare_range = Math.pow(planets.get(j).getXpos() - planets.get(i).getXpos(), 2) +
                        Math.pow(planets.get(j).getYpos() - planets.get(i).getYpos(), 2);

                if (Math.sqrt(sqare_range) < planets.get(i).getRadious() + planets.get(j).getRadious() / 2) //zderzenie
                {
                    if (aView.getSelectedLogItem() > 1)
                        aView.LogEvent("Zderzenie!  id: " + i + ", " + j + " Masy: " + (int) planets.get(i).getMass() + ", " + (int) planets.get(j).getMass());

                    if (planets.get(i) instanceof Star)
                    {
                        planets.get(i).impact(planets.get(j));
                        planets.remove(j);
                    }
                    else if (planets.get(j) instanceof Star)
                    {
                        planets.get(j).impact(planets.get(i));
                        planets.remove(i);
                    }
                    else
                    {
                        planets.get(i).impact(planets.get(j));
                        planets.remove(j);
                    }
                    i = 0;
                    j = 0;
                    arrSize--;
                }
            }
    }

    private void movement_realisation(ArrayList<Planet> planets, double timebase, View aView)
    {
        int arrSize = planets.size();
        double sqare_range = 0;
        double[] dvX = new double[arrSize];
        double[] dvY = new double[arrSize];
        for (int i = 0; i < arrSize; i++)   //zderzenia poza petlą!!!
        {
            dvX[i] = 0;
            dvY[i] = 0;
            for (int j = 0; j < arrSize; j++)  // v = at = F/m *t = G*m*M/r^2 *t /m = G*M*t/r^2  <- r^2 = r * abs(r) dla zachowania znaku działania siły
            {
                if (i != j && planets.get(i).getMass() * planets.get(j).getMass() != 0)
                {
                    sqare_range = Math.pow(planets.get(j).getXpos() - planets.get(i).getXpos(), 2) +
                            Math.pow(planets.get(j).getYpos() - planets.get(i).getYpos(), 2);

                    dvX[i] += timebase * planets.get(j).getMass() / sqare_range
                            * (planets.get(j).getXpos() - planets.get(i).getXpos()) / Math.sqrt(sqare_range);

                    dvY[i] += timebase * planets.get(j).getMass() / sqare_range
                            * (planets.get(j).getYpos() - planets.get(i).getYpos()) / Math.sqrt(sqare_range);
                }
            }
        }
        for (int i = 0; i < arrSize; i++)
        {
            planets.get(i).Xcorrection(planets.get(i).getXvel() * timebase);
            planets.get(i).Ycorrection(planets.get(i).getYvel() * timebase);
            planets.get(i).PosTracking();

            planets.get(i).vXcorrection(dvX[i] / 2);
            planets.get(i).vYcorrection(dvY[i] / 2);

            planets.get(i).redraw(aView);
        }
    }

    private void deageing_stars(ArrayList<Planet> planets, int i_timebase, View aView)
    {
        int arrSize = planets.size();
        for(int i=0; i<arrSize; i++)
        {
            if(planets.get(i) instanceof Star)
            {
                if (((Star) planets.get(i)).getLifeTime() < 0)
                {
                    double Xpos = planets.get(i).getXpos();
                    double Ypos = planets.get(i).getYpos();
                    double baseVelX = planets.get(i).getXvel();
                    double baseVelY = planets.get(i).getYvel();
                    double radius = planets.get(i).getRadious();
                    double Seeds = Math.sqrt(planets.get(i).getMass())+Math.random()*50;
                    double SeedRadius = radius/Math.sqrt(Seeds);

                    double circles = radius / (1.9*SeedRadius);
                    double curentRadius = 2*SeedRadius+1;
                    int planetsOnRing;
                    double angle, SeedXpos, SeedYpos;
                    for (int j = 0; j < (int)circles+4; j++)
                    {
                        planetsOnRing  = (int)Math.round(2*Math.PI*curentRadius/(2*SeedRadius));
                        angle = 2*Math.PI/planetsOnRing;
                        if(j%2==1)
                            angle *= 1.5; //początkowe przesunięcie w co drugim pierścieniu
                        for(int k=0; k<planetsOnRing; k++)
                        {
                            SeedXpos = curentRadius * Math.cos(angle*k)+Xpos;
                            SeedYpos = curentRadius * Math.sin(angle*k)+Ypos;
                            planets.add(new Planet(aView, SeedXpos, SeedYpos, radius * Math.cos(angle*k)*Math.pow(j+1,(double)1/3)+baseVelX,
                                    radius*Math.sin(angle*k)*Math.pow(j+1,(double)1/3)+baseVelY, 8*Math.pow(SeedRadius,3), new Color(176, 0, 0)));
                        }
                        curentRadius+=2*SeedRadius;
                    }
                    //new Color(176, 0, 0)
                    planets.remove(i);
                    arrSize--;
                    i = 0;
                }
                else
                    if(((Star) planets.get(i)).getLifeTime() != 50000)
                        for(int j=0; j<i_timebase; j++)
                        {
                            if(((Star) planets.get(i)).getLifeTime() == 5000)
                                aView.LogEvent("Gwiada kończy swoje życie, nidługo wybuchnie");
                            ((Star) planets.get(i)).decreaseLifeTime();
                        }
            }
        }
    }
}
