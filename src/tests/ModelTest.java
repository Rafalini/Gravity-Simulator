package tests;

import display.View;
import mechanics.Model;
import space_obj.Planet;
import space_obj.Star;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ModelTest
{
    ArrayList<Planet> planets;
    View aView;
    Model aModel;

    @org.junit.Before
    public void setupBeforeTest()
    {
        planets = new ArrayList<Planet>();
        aView = new View();
        aModel = new Model();
    }
    @org.junit.Test
    public void twoPlanetsImpact() //2 or more planets impact in single step, +movement
    {
        planets.add(new Planet(aView, 0.,0.,0.,0.,(200.*200.*200.)));  //r = 0.5 * m^(1/3)
        planets.add(new Planet(aView, 150.,0.,-10.,0.,(200.*200.*200.)));
        assertEquals(2, planets.size());
        aModel.next_simulation_setp(planets, 0.0, aView);
        assertEquals(2, planets.size());

        aModel.next_simulation_setp(planets, 10., aView); //time != 0 -> movement will occur
        aModel.next_simulation_setp(planets, 0., aView);

        assertEquals(1, planets.size());
        planets.add(new Planet(aView, 0.,0.,0.,0.,33.));
        planets.add(new Planet(aView, 0.,0.,0.,0.,5436.));
        assertEquals(3, planets.size());
        aModel.next_simulation_setp(planets, 0.0, aView);
        assertEquals(1, planets.size());

    }
    @org.junit.Test
    public void attractionTest()
    {
        //4x planets with [0,0] start velocity
        planets.add(new Planet(aView, 300.,300.,0.,0.,(200.*200.*200.)));
        planets.add(new Planet(aView, -300.,300.,0.,0.,(200.*200.*200.)));
        planets.add(new Planet(aView, -300.,-300.,0.,0.,(200.*200.*200.)));
        planets.add(new Planet(aView, 300.,-300.,0.,0.,(200.*200.*200.)));

        aModel.next_simulation_setp(planets, 10, aView); //simulation step with non-zero time

        for(int i=0; i<planets.size(); i++) //non-zero planet velocities
        {
            assertTrue(planets.get(i).getXvel() != 0);
            assertTrue(planets.get(i).getYvel() != 0);
        }
    }
    @org.junit.Test
    public void starExplosions()
    {
        planets.add(new Star(aView,0.,0.,0.,0., 20000, 3.)); //real life time = 500 * life time [simulation steps * their time base]
        assertEquals(1, planets.size());
        aModel.next_simulation_setp(planets, 3*500, aView);
        assertEquals(1, planets.size());                    //star with 0 life time left
        aModel.next_simulation_setp(planets, 1.0, aView);   //explosion in next simulation
        aModel.next_simulation_setp(planets, 0, aView);     //explosion
        assertTrue(300<planets.size());
    }

}