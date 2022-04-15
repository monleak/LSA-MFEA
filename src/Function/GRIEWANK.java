package Function;

import static java.lang.Math.*;

public class GRIEWANK {
    public static double Griewank(double[] X, double[][] M, double[] opt){
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
        double sum1 =0;
        double sum2 = 1;
        for (int i = 0; i < X.length; i++) {
            sum1+= v[i]*v[i];
            sum2 = sum2 * Math.cos(v[i]/(Math.sqrt(i+1)));
        }
        return 1+1.0/4000*sum1-sum2;
    }
}
