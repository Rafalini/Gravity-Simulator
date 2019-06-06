package mechanics;

import space_obj.*;
import display.*;
import java.awt.Dimension;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.event.*;


public class Controller
{
    View theView;
    Model theModel;
    MapPanel theMapPanel;
    ArrayList<Planet> planets;
    boolean reset = false;
    long begintime;
    int calculations_counter=0;

    public Controller(View aView, Model aModel)
    {
        this.theView = aView;
        this.theModel = aModel;
        this.theMapPanel = theView.getMapPanel();
        planets = new ArrayList<Planet>();
        //Gwiazdki = new ArrayList<Star>();

        theMapPanel.addActionListener(new MapPanelListener());
        theView.addTimeSliderListener(new TimeSliderListener());
        theView.addMassSliderListener(new MassSliderListener());
        theView.addTimePlusButtonListener(new TimePlusButtonListener());
        theView.addTimeMinusButtonListener(new TimeMinusButtonListener());
        theView.addPresetButtonListener(new PresetButtonListener());
        theView.addPresetSliderListener(new PresetSliderListener());
        theView.addResetButtonListener(new ResetButtonListener());
        theView.addStarTimeSliderListener(new StarTimeSliderListener());
        theView.addObjSelectionListener(new ObjSelectionListener());

        begintime = System.currentTimeMillis();
        for(;;)
        {recalculate_and_repaint();}
    }

    public void recalculate_and_repaint()
    {
        if (reset)
        {
            reset = false;
            planets.clear();
        }

        if (calculations_counter < 1)
        {
            theModel.next_simulation_setp(planets, theView.getTimeTextValue(), theView);
            theMapPanel.update(planets);
            calculations_counter++;
         }
        long calculationtime = System.currentTimeMillis()-begintime;
        if(calculationtime>15)
        {
            if(theView.getSelectedLogItem()==0)
                theView.LogEvent("Czas generowania ramki: "+calculationtime+" [ms]");
            calculations_counter=0;
            begintime = System.currentTimeMillis();
            //try{TimeUnit.MILLISECONDS.sleep(10- (int)calculationtime);}
            //catch(InterruptedException e) {}
            theMapPanel.repaint();
            theView.display_stats(calculationtime, planets.size(), theView.getTimeTextValue());
        }
    }

    class MapPanelListener implements MouseListener         //Myszka na mapce
    {
        public void mouseClicked(MouseEvent e)
        {
            double xVel, yVel, mass;
            int StarTime;
            xVel = theView.getXvelFromText();
            yVel = theView.getYvelFromText();
            mass = theView.getMassFromText();
            StarTime = theView.getStarTime();
            if(theView.getSelectedObj() == 0)
                planets.add(new Planet(theView,e.getX()-theView.getWidth()/2, theView.getHeight()/2-e.getY(), xVel, yVel, mass));
            else
                planets.add(new Star(theView,e.getX()-theView.getWidth()/2, theView.getHeight()/2-e.getY(), xVel, yVel, mass, StarTime));
        }
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }
    class TimeSliderListener implements ChangeListener      //Suwak czasu
    {
        public void stateChanged(ChangeEvent e)     { theView.setTimeValueText(); }
    }
    class MassSliderListener implements ChangeListener      //Suwak masy
    {
        public void stateChanged(ChangeEvent e)     { theView.setMassValueText(); }
    }
    class TimePlusButtonListener implements  ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            theView.setTimeValueSlider(theView.getTimeSliderValue()+1);
            if(theView.getTimeSliderValue()==100)
                theView.setTimeValueText(theView.getTimeTextValue()+1);
        }
    }
    class TimeMinusButtonListener implements  ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(theView.getTimeTextValue()>0)
            {
                if(theView.getTimeTextValue()<101)
                    theView.setTimeValueSlider(theView.getTimeSliderValue()-1);
                else
                    theView.setTimeValueText(theView.getTimeTextValue()-1);
            }
        }
    }
    class PresetButtonListener implements  ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            switch(theView.getSelectedPresetItem())
            {
                case 0: //siatka
                   gridLayout();
                break;  //okręgi
                case 1:
                    circleLayout();
                break;
                case 2: ///spirale
                    spiralLayout();
                break;
                case 3:
                    fireworks();
                break;
            }
        }
    }
    class ResetButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            reset = true;
        }
    }
    class PresetSliderListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            theView.setPresetTextValue();
        }
    }
    class StarTimeSliderListener implements  ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            theView.setStarTimeText();
        }
    }
    class ObjSelectionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            theView.setCorrectOptions();
        }
    }
    private void gridLayout()
    {
        int planetsAmount = theView.getPresetTextValue();
        double xPos, yPos, mass, xVel, yVel;
        Dimension VelVector;
        theView.LogEvent("Tworzenie siatki...");
        planets.add(new Star(theView, 0, 0, 0, 0, 15*theView.getMassFromText(), theView.getStarTime()));
        int side = planetsAmount;
        double baseXdistance;
        double baseYdistance;
        if(side>1)
        {
            baseXdistance = theView.getWidth()/(side-1);
            baseYdistance = theView.getHeight()/(side-1);
        }
        else
        {
            baseXdistance = theView.getWidth();
            baseYdistance = theView.getHeight();
        }
        for(int i=0; i<side; i++)
            for(int j=0; j<side; j++)
            {
                xPos = -0.5*theView.getWidth() +j*baseXdistance;
                yPos = -0.5*theView.getHeight() +i*baseYdistance;
                if(theView.getSelectedMassPreset()==1)
                    mass = (Math.random()*theView.getMassFromText()+125)/planetsAmount;
                else
                    mass = (theView.getMassFromText())/planetsAmount;
                switch (theView.getSelectedVelPresetItem())
                {
                    case 0:
                        VelVector = GeomFunctions.velVectorforCircle(xPos, yPos, Math.pow(theView.getPresetTextValue(), 0.75)*theView.getMassFromText());
                        planets.add(new Planet(theView, xPos, yPos, VelVector.getWidth(), VelVector.getHeight(), mass));
                        break;
                    case 1:
                        xVel = Math.random()*theView.getXvelFromText()-theView.getXvelFromText()/2;
                        yVel = Math.random()*theView.getYvelFromText()-theView.getYvelFromText()/2;
                        planets.add(new Planet(theView, xPos, yPos, xVel, yVel, mass));
                        break;
                    case 2: planets.add(new Planet(theView,xPos,yPos,0,0,mass)); break;
                }
            }
    }
    private void circleLayout()
    {
        int planetsAmount = theView.getPresetTextValue();
        double xPos, yPos, mass, xVel, yVel;
        Dimension VelVector;
        theView.LogEvent("Tworzenie okręgów...");
        planets.add(new Star(theView, 0, 0, 0, 0, 15*theView.getMassFromText(), theView.getStarTime()));
        double radius = 0.4 * theView.getWidth();
        double angle=0;
        mass = theView.getMassFromText();
        int planetsOnRing = (int)(Math.PI * radius / (5*Math.pow(mass, (double)1/3)));
        while(planetsAmount>0)
        {
            if (planetsAmount - planetsOnRing > 0)
                planetsAmount -= planetsOnRing;
            else
            {
                planetsOnRing = planetsAmount;
                planetsAmount = 0;
            }
            angle = 2*Math.PI/planetsOnRing;
            for (int i = 0; i < planetsOnRing; i++)
            {
                xPos = radius*Math.cos(angle*i);
                yPos = radius*Math.sin(angle*i);
                if (theView.getSelectedMassPreset() == 1)
                    mass = Math.random() * theView.getMassFromText() + 125;
                else
                    mass = theView.getMassFromText();
                switch (theView.getSelectedVelPresetItem())
                {
                    case 0:
                        VelVector = GeomFunctions.velVectorforCircle(xPos, yPos, 15 * theView.getMassFromText());
                        planets.add(new Planet(theView, xPos, yPos, VelVector.getWidth(), VelVector.getHeight(), mass));
                        break;
                    case 1:
                        xVel = Math.random() * theView.getXvelFromText() - theView.getXvelFromText() / 2;
                        yVel = Math.random() * theView.getYvelFromText() - theView.getYvelFromText() / 2;
                        planets.add(new Planet(theView, xPos, yPos, xVel, yVel, mass));
                        break;
                    case 2:
                        planets.add(new Planet(theView, xPos, yPos, 0, 0, mass));
                        break;
                }
            }
            radius*=(double)2/3;
        }
    }
    private void spiralLayout()
    {
        int planetsAmount = theView.getPresetTextValue();
        double xPos, yPos, mass, xVel, yVel;
        Dimension VelVector;
        theView.LogEvent("Tworzenie spiral...");
        planets.add(new Star(theView, 0, 0, 0, 0, 15*theView.getMassFromText(), theView.getStarTime()));
        int bow = (int)planetsAmount/2;
        double basedistance = 0.6*theView.getWidth()/(bow+1);
        for(int i=0; i<bow; i++)
        {
            if(theView.getSelectedMassPreset()==1)
                mass = Math.random()*theView.getMassFromText()+125;
            else
                mass = theView.getMassFromText();
            xPos = (-0.3*theView.getHeight() +i*basedistance);
            yPos = (-0.3*theView.getHeight() +i*basedistance);
            switch (theView.getSelectedVelPresetItem())
            {
                case 0:
                    VelVector = GeomFunctions.velVectorforCircle(0, yPos, 15*theView.getMassFromText());
                    planets.add(new Planet(theView, 0, yPos, VelVector.getWidth(), VelVector.getHeight(), mass));
                    planets.add(new Planet(theView, 0, -yPos, -VelVector.getWidth(), -VelVector.getHeight(), mass));
                    break;
                case 1:
                    xVel = Math.random()*theView.getXvelFromText()-theView.getXvelFromText()/2;
                    yVel = Math.random()*theView.getYvelFromText()-theView.getYvelFromText()/2;
                    planets.add(new Planet(theView, 0, yPos, xVel, yVel, mass));
                    planets.add(new Planet(theView, 0, -yPos, xVel, yVel, mass));
                    break;
                case 2:
                    planets.add(new Planet(theView, 0,yPos,0,0,mass));
                    planets.add(new Planet(theView, 0,-yPos,0,0,mass));
                    break;
            }
        }
    }
        private void fireworks()
        {
            int side = 3;
            double xPos, yPos, xVel, yVel;
            double baseXdistance = theView.getWidth()/(side+1);
            double baseYdistance = theView.getHeight()/(side+1);
            for(int i=0; i<side; i++)
                for(int j=0; j<side; j++)
                {
                    xPos = -0.4*theView.getWidth() +j*baseXdistance;
                    yPos = -0.4*theView.getHeight() +i*baseYdistance;
                    xVel = 7+ (Math.random()-0.5)*5 +j*i;
                    yVel = 7+ (Math.random()-0.5)*5 +j*i;
                    planets.add(new Star(theView, xPos, yPos, xVel, yVel, 20000, 20+(j+1)*(i+1)*3));
                }
        }
}
