package tests;

import mechanics.GeomFunctions;

import static org.junit.Assert.*;

public class GeomFunctionsTest
{

    @org.junit.Test
    public void distance()
    {
        assertEquals(5 ,(int)GeomFunctions.distance(0,0,5,0));
        assertEquals(2, (int)GeomFunctions.distance(0,0,0,2));
        assertEquals(7,(int)GeomFunctions.distance(0,0,-7,0));
        assertEquals(300, (int)GeomFunctions.distance(0,0,0,-300));
        assertEquals(5, (int)GeomFunctions.distance(0,0,3,4));
    }
}