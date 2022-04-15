package Function;

import static java.lang.Math.*;

public class ACKLEY {
    public static double Ackley(double[] X, double[][] M, double[] opt){
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
        double sum1 = 0.0;
        double sum2 = 0.0;
        for (int i = 0; i < X.length; i++) {
            sum1 += v[i]*v[i];
            sum2 += Math.cos(2*Math.PI*v[i]);
        }
        double avgsum1 = sum1/X.length;
        double avgsum2 = sum2/X.length;
        return -20*Math.exp(-0.2*Math.sqrt(avgsum1)) - Math.exp(avgsum2) + 20 + Math.exp(1);
    }
}
