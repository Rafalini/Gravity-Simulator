import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

class MapPanel extends JComponent
{
    ArrayList<Planet> Planetki;

    public MapPanel()
    {
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
            }
        }
        catch (NullPointerException e) {}

    }

    public void addActionListener(MouseListener ac) { this.addMouseListener(ac); }
}