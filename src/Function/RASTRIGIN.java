package Function;

import static MFEA.TSP.main.*;
import static java.lang.Math.cos;

public class RASTRIGIN {
    public static double Rastrigin(double[] X, double[][] M, double[] opt){
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
        int dim = X.length;
        double obj = dim*10;
        for(int i=0;i<dim; i++){
            obj=obj+(v[i]*v[i] - 10*(Math.cos(2*Math.PI*v[i])));
        }
        return obj;
    }
}
