package Function;

import static java.lang.Math.*;
import static MFEA.TSP.main.*;

public class Weierstrass {
    public static double Weierstrass(double[] X, double[][] M, double[] opt){
        double vars[] = new double[X.length];
        for (int i = 0; i < X.length; i++) {
            vars[i] = X[i] - opt[i];
        }
        double v[] = new double[X.length];
        for (int row = 0; row < X.length; row++) {
            v[row] =0.0;
            for (int col = 0; col < X.length; col++) {
                    v[row]+= M[row][col]*vars[col];
            }
        }
        double a = 0.5;
        double b = 3;
        int kmax = 20;
        double obj = 0;
        int D = X.length;
        for(int i=1; i<= D; i++){
            for(int k=0; k<=kmax; k++){
                obj = obj + Math.pow(a,k)*Math.cos(2*Math.PI*Math.pow(b,k)*(v[i-1]+0.5));
            }
        }
        for(int k=0;k<=kmax; k++){
            obj = obj - D*Math.pow(a,k)*Math.cos(2*Math.PI*Math.pow(b,k)*0.5);
        }
        return obj;
    }
}
