package Function;

import static java.lang.Math.*;

public class SCHWEFEL {
    public static double Schwefel(double[] X, double[][] M, double[] opt){
        double vars[] = new double[X.length];
        for (int i = 0; i < X.length; i++) {
            vars[i] = X[i] - opt[i];
        }
        double v[] = new double[X.length];
        for (int row = 0; row < X.length; row++) {
            v[row] =0.0;
            for (int col = 0; col < X.length; col++) {
                    v[row] += M[row][col]*vars[col];
            }
        }
        int dim =X.length;
        double sum =0;
        for(int i=0;i<X.length; i++){
            sum = sum + v[i]*Math.sin(Math.sqrt(Math.abs(v[i])));
        }
        return 418.9829*dim-sum;
    }
}
