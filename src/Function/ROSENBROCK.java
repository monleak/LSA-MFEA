package Function;

import static java.lang.Math.pow;

public class ROSENBROCK {
    public static double Rosenbrock(double[] X, double[][] M, double[] opt){
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
        double sum =0;
        int dim = X.length;
        for(int i=0;i< dim -1; i++){
            double xi = v[i];
            double xnext = v[i+1];
            double _new = 100*(xnext-xi*xi)*(xnext-xi*xi)  + (xi-1)*(xi-1);
            sum = sum + _new;
        }
        return sum;
    }
}
