package Function;

public class SPHERE {
    public static double Sphere(double[] X, double[][] M, double[] opt){
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
        double sum = 0;
        for(double d : v){
            sum += d*d;
        }
        return sum;
    }
}
