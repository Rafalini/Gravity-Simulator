package display;

import space_obj.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class MapPanel extends JComponent
{
    ArrayList<Planet> Planetki;
    ArrayList<Dimension> tmp;
    Shape line;
    View aView;

    public MapPanel(View aView)
    {
        this.aView = aView;
        Planetki = new ArrayList<Planet>();
    }
    public void update(ArrayList<Planet> Planetki)
    {
        this.Planetki = Planetki;
    }
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,getWidth(),getHeight());
        try
        {
            for(int i=0; i<Planetki.size(); i++)
            {
                g2.setColor(Planetki.get(i).getColor());
                g2.fill(Planetki.get(i).getElipse());

                if(aView.TracksSet())
                {
                    tmp = Planetki.get(i).GetPath();
                    for(int j=0; j<tmp.size()-1; j++)
                    {
                        line = new Line2D.Double(tmp.get(j).getWidth()+aView.getWidth()/2, aView.getHeight()/2 -tmp.get(j).getHeight(),
                                              tmp.get(j+1).getWidth()+aView.getWidth()/2, aView.getHeight()/2 -tmp.get(j+1).getHeight());
                        g2.draw(line);
                    }
                }
            }
            if(aView.MoreLinesSet())
              for(int i=0; i<Planetki.size(); i++)
                for(int j=i+1; j<Planetki.size(); j++)
                {
                  g2.setColor(Color.WHITE);
                  line = new Line2D.Double(Planetki.get(i).getXpos()+aView.getWidth()/2, aView.getHeight()/2-Planetki.get(i).getYpos(),
                                           Planetki.get(j).getXpos()+aView.getWidth()/2, aView.getHeight()/2-Planetki.get(j).getYpos());
                  g2.draw(line);
                }
        }
        catch (NullPointerException e) {}
        catch (IndexOutOfBoundsException e) {}

    }

    public void addActionListener(MouseListener ac) { this.addMouseListener(ac); }
}
