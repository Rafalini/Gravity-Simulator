import java.awt.*;
import java.util.ArrayList;

public class Model
{
    public Model() {}

    public void recalculate(ArrayList<Planet> Planetki, double timebase, MapPanel aMapPanel, View aView)
    {
        int i_timebase = (int)timebase;
        timebase = timebase * 0.001;
        double sqare_range;
        int colisionCounter=0, tabSize = Planetki.size();
        int delA, delB;
            for (int i = 0; i < tabSize; i++)
                for (int j = i + 1; j < tabSize; j++)
                {
                    sqare_range = Math.pow(Planetki.get(j).getXpos() - Planetki.get(i).getXpos(), 2) +
                            Math.pow(Planetki.get(j).getYpos() - Planetki.get(i).getYpos(), 2);

                    if (Math.sqrt(sqare_range) < Planetki.get(i).getRadious() + Planetki.get(j).getRadious() / 2) //zderzenie
                    {
                        if (aView.getSelectedLogItem() > 1)
                            aView.LogEvent("Zderzenie!  id: " + i + ", " + j + " Masy: " + (int) Planetki.get(i).getMass() + ", " + (int) Planetki.get(j).getMass());

                        if (Planetki.get(i) instanceof Star)
                        {
                            Planetki.get(i).impact(Planetki.get(j));
                            Planetki.remove(j);
                        }
                        else if (Planetki.get(j) instanceof Star)
                        {
                            Planetki.get(j).impact(Planetki.get(i));
                            Planetki.remove(i);
                        }
                        else
                        {
                            Planetki.get(i).impact(Planetki.get(j));
                            Planetki.remove(j);
                        }
                        i = 0;
                        j = i + 1;
                        tabSize--;
                    }
                }

            double[] dvX = new double[tabSize];
            double[] dvY = new double[tabSize];
            for (int i = 0; i < tabSize; i++)   //zderzenia poza petlą!!!
            {
                dvX[i] = 0;
                dvY[i] = 0;
                for (int j = 0; j < tabSize; j++)  // v = at = F/m *t = G*m*M/r^2 *t /m = G*M*t/r^2  <- r^2 = r * abs(r) dla zachowania znaku działania siły
                {
                    if (i != j && Planetki.get(i).getMass() * Planetki.get(j).getMass() != 0)
                    {
                        sqare_range = Math.pow(Planetki.get(j).getXpos() - Planetki.get(i).getXpos(), 2) +
                                Math.pow(Planetki.get(j).getYpos() - Planetki.get(i).getYpos(), 2);

                        dvX[i] += timebase * Planetki.get(j).getMass() / sqare_range
                                * (Planetki.get(j).getXpos() - Planetki.get(i).getXpos()) / Math.sqrt(sqare_range);

                        dvY[i] += timebase * Planetki.get(j).getMass() / sqare_range
                                * (Planetki.get(j).getYpos() - Planetki.get(i).getYpos()) / Math.sqrt(sqare_range);
                    }
                }
            }
            for (int i = 0; i < tabSize; i++)
            {
                Planetki.get(i).Xcorrection(Planetki.get(i).getXvel() * timebase);
                Planetki.get(i).Ycorrection(Planetki.get(i).getYvel() * timebase);
                Planetki.get(i).PosTracking();

                Planetki.get(i).vXcorrection(dvX[i] / 2);
                Planetki.get(i).vYcorrection(dvY[i] / 2);

                Planetki.get(i).redraw(aView);
            }
        for(int i=0; i<tabSize; i++)
        {
            if(Planetki.get(i) instanceof Star)
            {
                if (((Star) Planetki.get(i)).getLifeTime() < 0)
                {
                    double Xpos = Planetki.get(i).getXpos();
                    double Ypos = Planetki.get(i).getYpos();
                    double baseVelX = Planetki.get(i).getXvel();
                    double baseVelY = Planetki.get(i).getYvel();
                    double radius = Planetki.get(i).getRadious();
                    double Seeds = Math.sqrt(Planetki.get(i).getMass())+Math.random()*50;

                    double SeedRadius = radius/Math.sqrt(Seeds);

                    double circles = radius / (1.9*SeedRadius);
                    double curentRadius = 2*SeedRadius;
                    int planetsOnRing;
                    double angle, SeedXpos, SeedYpos;
                    for (int j = 0; j < (int)circles+7; j++)
                    {
                        planetsOnRing  = (int)Math.round(2*Math.PI*curentRadius/(2*SeedRadius));
                        angle = 2*Math.PI/planetsOnRing;
                        if(j%2==1)
                            angle *= 1.5;
                        for(int k=0; k<planetsOnRing; k++)
                        {
                            SeedXpos = curentRadius * Math.cos(angle*k)+Xpos;
                            SeedYpos = curentRadius * Math.sin(angle*k)+Ypos;
                            Planetki.add(new Planet(aView, SeedXpos, SeedYpos, radius * Math.cos(angle*k)*Math.pow(j+1,(double)1/3)+baseVelX,
                                    radius*Math.sin(angle*k)*Math.pow(j+1,(double)1/3)+baseVelY, 8*Math.pow(SeedRadius,3), new Color(176, 0, 0)));
                        }
                        curentRadius+=2*SeedRadius;
                    }
                    //new Color(176, 0, 0)
                    Planetki.remove(i);
                    tabSize--;
                    i = 0;
                }
                else
                    for(int j=0; j<i_timebase; j++)
                    {
                        if(((Star) Planetki.get(i)).getLifeTime() == 5000)
                            aView.LogEvent("Gwiada kończy swoje życie, nidługo wybuchnie");
                        ((Star) Planetki.get(i)).decreaseLifeTime();
                    }
            }
        }
    }
}
