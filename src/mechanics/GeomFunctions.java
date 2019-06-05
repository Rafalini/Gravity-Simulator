package mechanics;

import display.*;
import java.awt.Dimension;

public class GeomFunctions
{
    public static double distance (double X1, double Y1, double X2, double Y2)
    {
        return Math.sqrt(
                Math.pow(X1-X2,2)+
                Math.pow(Y1-Y2,2));
    }

    public static Dimension velVectorforCircle (double X1, double Y1, double BaseMass)
    {
        // |v| = sqrt( x^2+y^2 ) = sqrt( x^2+(ay)^2 )   a = d/dx( y(x) )
        // x^2 + y^2 = r^2    y(x) = sqrt( r^2 - x^2 )  y'(x) = -x/sqrt( r^2 - x^2 )
        // m|v|^2/r = GMm/r^2    |v| = sqrt ( GM/r ) -> |v| = sqrt( x^2 + y^2 ) = sqrt( x^2+(ay)^2 )

        double radious = distance(X1, Y1, 0, 0);
        //if(Y1!=0)
        ///dir_fact = -X1/ Y1;
        //else Vx=0;
        //baseVx = Math.sqrt( BaseMass/( radious*(dir_fact+1) ) );
        //Vx = baseVx * (Math.abs(Y1)/-Y1); // wsp +-1 względem położenia -> |y|/y
        //Vy = baseVx * dir_fact * (Math.abs(X1)/X1);
        double velx = -(-Y1/radious) * Math.sqrt(BaseMass/(2*radious));
        double vely = -(X1/radious) * Math.sqrt(BaseMass/(2*radious));
        Dimension VelVector = new Dimension();
        VelVector.setSize(velx,vely);
        return VelVector;
    }
}
