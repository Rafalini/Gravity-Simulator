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
    ArrayList<Planet> Planetki;
    boolean reset = false;
    long begintime;
    int calculations_counter=0;

    public Controller(View aView, Model aModel)
    {
        this.theView = aView;
        this.theModel = aModel;
        this.theMapPanel = theView.getMapPanel();
        Planetki = new ArrayList<Planet>();
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
            Planetki.clear();
        }

        if (calculations_counter < 1)
        {
            theModel.next_simulation_setp(Planetki, theView.getTimeTextValue(), theMapPanel, theView);
            theMapPanel.update(Planetki);
            calculations_counter++;
         }
        long calculationtime = System.currentTimeMillis()-begintime;
        if(calculationtime>15)
        {
            if(theView.getSelectedLogItem()>1)
                theView.LogEvent("Czas generowania ramki: "+calculationtime+" [ms]");
            calculations_counter=0;
            begintime = System.currentTimeMillis();
            //try{TimeUnit.MILLISECONDS.sleep(10- (int)calculationtime);}
            //catch(InterruptedException e) {}
            theMapPanel.repaint();
            theView.display_stats(calculationtime, Planetki.size(), theView.getTimeTextValue());
        }
    }

    class MapPanelListener implements MouseListener         //Myszka na mapce
    {
        public void mouseClicked(MouseEvent e)
        {
            double Xvel, Yvel, Mass;
            int StarTime;
            Xvel = theView.getXvelFromText();
            Yvel = theView.getYvelFromText();
            Mass = theView.getMassFromText();
            StarTime = theView.getStarTime();
            if(theView.getSelectedObj() == 0)
                Planetki.add(new Planet(theView,e.getX()-theView.getWidth()/2, theView.getHeight()/2-e.getY(), Xvel, Yvel, Mass));
            else
                Planetki.add(new Star(theView,e.getX()-theView.getWidth()/2, theView.getHeight()/2-e.getY(), Xvel, Yvel, Mass, StarTime));
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
            int planets = theView.getPresetTextValue();
            double Xpos, Ypos, Mass, Xvel, Yvel;
            double Xcenter = theView.getWidth()/2;
            double Ycenter = theView.getHeight()/2;
            Dimension VelVector;
            switch(theView.getSelectedPresetItem())
            {
                case 0: //siatka
                    theView.LogEvent("Tworzenie siatki...");
                    Planetki.add(new Star(theView, 0, 0, 0, 0, 15*theView.getMassFromText(), theView.getStarTime()));
                    int side = planets;
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
                            Xpos = -0.5*theView.getWidth() +j*baseXdistance;
                            Ypos = -0.5*theView.getHeight() +i*baseYdistance;
                            if(theView.getSelectedMassPreset()==1)
                                Mass = (Math.random()*theView.getMassFromText()+125)/planets;
                            else
                                Mass = (theView.getMassFromText())/planets;
                            switch (theView.getSelectedVelPresetItem())
                            {
                                case 0:
                                    VelVector = GeomFunctions.VelVectorforSpiral(Xpos, Ypos, theView, Math.pow(theView.getPresetTextValue(), 0.75)*theView.getMassFromText());
                                    Planetki.add(new Planet(theView, Xpos, Ypos, VelVector.getWidth(), VelVector.getHeight(), Mass));
                                    break;
                                case 1:
                                    Xvel = Math.random()*theView.getXvelFromText()-theView.getXvelFromText()/2;
                                    Yvel = Math.random()*theView.getYvelFromText()-theView.getYvelFromText()/2;
                                    Planetki.add(new Planet(theView, Xpos, Ypos, Xvel, Yvel, Mass));
                                    break;
                                case 2: Planetki.add(new Planet(theView,Xpos,Ypos,0,0,Mass)); break;
                            }
                        }
                break;  //okręgi
                case 1:
                    theView.LogEvent("Tworzenie okręgów...");
                    Planetki.add(new Star(theView, 0, 0, 0, 0, 15*theView.getMassFromText(), theView.getStarTime()));
                    double radius = 0.4 * theView.getWidth();
                    double angle=0;
                    Mass = theView.getMassFromText();
                    int planetsOnRing = (int)(Math.PI * radius / (5*Math.pow(Mass, (double)1/3)));
                    while(planets>0)
                    {
                        if (planets - planetsOnRing > 0)
                            planets -= planetsOnRing;
                        else
                        {
                            planetsOnRing = planets;
                            planets = 0;
                        }
                        angle = 2*Math.PI/planetsOnRing;
                        for (int i = 0; i < planetsOnRing; i++)
                        {
                            Xpos = radius*Math.cos(angle*i);
                            Ypos = radius*Math.sin(angle*i);
                            if (theView.getSelectedMassPreset() == 1)
                                Mass = Math.random() * theView.getMassFromText() + 125;
                            else
                                Mass = theView.getMassFromText();
                            switch (theView.getSelectedVelPresetItem())
                            {
                                case 0:
                                    VelVector = GeomFunctions.VelVectorforSpiral(Xpos, Ypos, theView, 15 * theView.getMassFromText());
                                    Planetki.add(new Planet(theView, Xpos, Ypos, VelVector.getWidth(), VelVector.getHeight(), Mass));
                                    break;
                                case 1:
                                    Xvel = Math.random() * theView.getXvelFromText() - theView.getXvelFromText() / 2;
                                    Yvel = Math.random() * theView.getYvelFromText() - theView.getYvelFromText() / 2;
                                    Planetki.add(new Planet(theView, Xpos, Ypos, Xvel, Yvel, Mass));
                                    break;
                                case 2:
                                    Planetki.add(new Planet(theView, Xpos, Ypos, 0, 0, Mass));
                                    break;
                            }
                        }
                        radius*=(double)2/3;
                    }
                break;
                case 2: ///spirale
                    theView.LogEvent("Tworzenie spiral...");
                    Planetki.add(new Star(theView, 0, 0, 0, 0, 15*theView.getMassFromText(), theView.getStarTime()));
                    int bow = (int)planets/2;
                    double basedistance = 0.6*theView.getWidth()/(bow+1);
                    for(int i=0; i<bow; i++)
                    {
                        if(theView.getSelectedMassPreset()==1)
                            Mass = Math.random()*theView.getMassFromText()+125;
                        else
                            Mass = theView.getMassFromText();
                        Ypos = (-0.3*theView.getHeight() +i*basedistance);
                        Ypos = (-0.3*theView.getHeight() +i*basedistance);
                        switch (theView.getSelectedVelPresetItem())
                        {
                            case 0:
                                VelVector = GeomFunctions.VelVectorforSpiral(0, Ypos, theView, 15*theView.getMassFromText());
                                Planetki.add(new Planet(theView, 0, Ypos, VelVector.getWidth(), VelVector.getHeight(), Mass));
                                Planetki.add(new Planet(theView, 0, -Ypos, -VelVector.getWidth(), -VelVector.getHeight(), Mass));
                                break;
                            case 1:
                                Xvel = Math.random()*theView.getXvelFromText()-theView.getXvelFromText()/2;
                                Yvel = Math.random()*theView.getYvelFromText()-theView.getYvelFromText()/2;
                                Planetki.add(new Planet(theView, 0, Ypos, Xvel, Yvel, Mass));
                                Planetki.add(new Planet(theView, 0, -Ypos, Xvel, Yvel, Mass));
                                break;
                            case 2:
                                Planetki.add(new Planet(theView, 0,Ypos,0,0,Mass));
                                Planetki.add(new Planet(theView, 0,-Ypos,0,0,Mass));
                                break;
                        }
                    }
                break;
                case 3:
                    side = 3;
                    baseXdistance = theView.getWidth()/(side+1);
                    baseYdistance = theView.getHeight()/(side+1);
                    for(int i=0; i<side; i++)
                        for(int j=0; j<side; j++)
                        {
                            Xpos = -0.4*theView.getWidth() +j*baseXdistance;
                            Ypos = -0.4*theView.getHeight() +i*baseYdistance;
                            if(theView.getSelectedMassPreset()==1)
                                Mass = (Math.random()*theView.getMassFromText()+125)/planets;
                            else
                                Mass = (theView.getMassFromText())/planets;

                            Xvel = 7+ (Math.random()-0.5)*5 +j*i;
                            Yvel = 7+ (Math.random()-0.5)*5 +j*i;
                            Planetki.add(new Star(theView, Xpos, Ypos, Xvel, Yvel, 20000, 20+(j+1)*(i+1)*3));
                        }
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
}
