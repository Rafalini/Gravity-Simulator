package mechanics;

//import mechanics.*;
import space_obj.*;
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

    public static Dimension VelVectorforSpiral (double X1, double Y1, View aView, double BaseMass)
    {
        double Xcenter = (double) aView.getWidth();
        double Ycenter = (double) aView.getHeight();
        double baseVel = Math.sqrt(  Math.pow(aView.getXvelFromText(),2) + Math.pow(aView.getYvelFromText(),2)  ) /3;

        double dir_fact, baseVx, Vx, Vy;
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
        Vx = -(-Y1/radious) * Math.sqrt(BaseMass/(2*radious));
        Vy = -(X1/radious) * Math.sqrt(BaseMass/(2*radious));
        Dimension VelVector = new Dimension();
        VelVector.setSize(Vx,Vy);
        return VelVector;
    }
}
