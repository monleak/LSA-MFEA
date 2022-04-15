package Function;
import MFEA.TSP.DanhGiaQuanThe;
import MFEA.TSP.NST;
import MFEA.TSP.main;

import java.io.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static MFEA.TSP.DanhGiaQuanThe.maHoa;
import static MFEA.TSP.main.*;

public class BENCHMARK {
    public static double[][] read_matrix(String file_name, int dim) {
        double[][] matrix = new double[dim][dim];
        BufferedReader br = null;

        String sCurrentLine = null;
        try {
            br = new BufferedReader(new FileReader(file_name));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BENCHMARK.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sCurrentLine = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(BENCHMARK.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] str = null;
        int i = 0;
        while (sCurrentLine != null) {
            int j = 0;
            str = sCurrentLine.split("\\s+");
            for (String s : str) {
                if(s.isEmpty()){
                    continue;
                }
                matrix[i][j] = Double.parseDouble(s);
                j++;
            }
            try {
                sCurrentLine = br.readLine();
                i++;
            } catch (IOException ex) {
                Logger.getLogger(BENCHMARK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(BENCHMARK.class.getName()).log(Level.SEVERE, null, ex);
        }

        return matrix;
    }

    public static double[] read_bias(String file_name, int dim) {
        double[] bias = new double[dim];

        BufferedReader br = null;

        String sCurrentLine = null;
        try {
            br = new BufferedReader(new FileReader(file_name));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BENCHMARK.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sCurrentLine = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(BENCHMARK.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] str = null;
        str = sCurrentLine.split("\\s+");
        int i = 0;
        for (String s : str) {
            if(s.isEmpty()){
                continue;
            }
            bias[i] = Double.parseDouble(s);
            i++;
        }

        try {
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(BENCHMARK.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bias;
    }

    public static double benchmark(int ID, int task_id, NST X) throws IOException {
        double fcost = 0;
        if(main.soTacVu == 50){
            //task_id chạy từ 1 đến 50
            X.dim = 50;
            int[] choice_functions = null;
            switch (ID) {
                case 1:
                    choice_functions = new int[]{1};
                    break;
                case 2:
                    choice_functions = new int[]{2};
                    break;
                case 3:
                    choice_functions = new int[]{4};
                    break;
                case 4:
                    choice_functions = new int[]{1,2,3};
                    break;
                case 5:
                    choice_functions = new int[]{4,5,6};
                    break;
                case 6:
                    choice_functions = new int[]{2,5,7};
                    break;
                case 7:
                    choice_functions = new int[]{3,4,6};
                    break;
                case 8:
                    choice_functions = new int[]{2,3,4,5,6};
                    break;
                case 9:
                    choice_functions = new int[]{2,3,4,5,6,7};
                    break;
                case 10:
                    choice_functions = new int[]{3,4,5,6,7};
                    break;
                default:
                    System.out.println("Invalid input: ID should be in [1,10]");
                    break;
            }
            int func_id = choice_functions[(task_id - 1) % choice_functions.length];
            switch (func_id){
                case 1:
                    fcost = SPHERE.Sphere(maHoa(X.Gen,1),matrix[task_id-1],shift[task_id-1]);
                    break;
                case 2:
                    fcost = ROSENBROCK.Rosenbrock(maHoa(X.Gen,2),matrix[task_id-1],shift[task_id-1]);
                    break;
                case 3:
                    fcost = ACKLEY.Ackley(maHoa(X.Gen,3),matrix[task_id-1],shift[task_id-1]);
                    break;
                case 4:
                    fcost = RASTRIGIN.Rastrigin(maHoa(X.Gen,4),matrix[task_id-1],shift[task_id-1]);
                    break;
                case 5:
                    fcost = GRIEWANK.Griewank(maHoa(X.Gen,5),matrix[task_id-1],shift[task_id-1]);
                    break;
                case 6:
                    fcost = Weierstrass.Weierstrass(maHoa(X.Gen,6),matrix[task_id-1],shift[task_id-1]);
                    break;
                case 7:
                    fcost = SCHWEFEL.Schwefel(maHoa(X.Gen,7),matrix[task_id-1],shift[task_id-1]);
                    break;
            }
        }
        else if(main.soTacVu == 10){
            double[][] matrix_10 = new double[50][50];
            double[] shift_10 = new double[50];
            if(task_id==1){
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j <= i; j++) {
                        if (i == j) {
                            matrix_10[i][j] = 1;
                        } else {
                            matrix_10[i][j] = matrix_10[j][i] = 0;
                        }
                    }
                }
                for(int i=0;i<shift_10.length;i++){
                    shift_10[i] = 0;
                }
                fcost = SPHERE.Sphere(DanhGiaQuanThe.maHoa_10(X.Gen, 1,50), matrix_10, shift_10);
                X.dim = 50;
            }
            if(task_id==2){
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j <= i; j++) {
                        if (i == j) {
                            matrix_10[i][j] = 1;
                        } else {
                            matrix_10[i][j] = matrix_10[j][i] = 0;
                        }
                    }
                }
                for (int i = 0; i < shift_10.length; i++) {
                    shift_10[i] = 80;
                }
                fcost = SPHERE.Sphere(DanhGiaQuanThe.maHoa_10(X.Gen, 2,50), matrix_10, shift_10);
                X.dim = 50;
            }
            if(task_id==3){
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j <= i; j++) {
                        if (i == j) {
                            matrix_10[i][j] = 1;
                        } else {
                            matrix_10[i][j] = matrix_10[j][i] = 0;
                        }
                    }
                }
                for (int i = 0; i < shift_10.length; i++) {
                    shift_10[i] = -80;
                }
                fcost = SPHERE.Sphere(DanhGiaQuanThe.maHoa_10(X.Gen, 3,50), matrix_10, shift_10);
                X.dim = 50;
            }
            if(task_id==4){
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j <= i; j++) {
                        if (i == j) {
                            matrix_10[i][j] = 1;
                        } else {
                            matrix_10[i][j] = matrix_10[j][i] = 0;
                        }
                    }
                }
                for (int i = 0; i < shift_10.length; i++) {
                    shift_10[i] = -0.4;
                }
                fcost = Weierstrass.Weierstrass(DanhGiaQuanThe.maHoa_10(X.Gen, 4,25), matrix_10, shift_10);
                X.dim = 25;
            }
            if(task_id==5){
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j <= i; j++) {
                        if (i == j) {
                            matrix_10[i][j] = 1;
                        } else {
                            matrix_10[i][j] = matrix_10[j][i] = 0;
                        }
                    }
                }
                for (int i = 0; i < shift_10.length; i++) {
                    shift_10[i] = -1;
                }
                fcost = ROSENBROCK.Rosenbrock(DanhGiaQuanThe.maHoa_10(X.Gen, 5,50), matrix_10, shift_10);
                X.dim = 50;
            }
            if(task_id==6){
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j <= i; j++) {
                        if (i == j) {
                            matrix_10[i][j] = 1;
                        } else {
                            matrix_10[i][j] = matrix_10[j][i] = 0;
                        }
                    }
                }
                for (int i = 0; i < shift_10.length; i++) {
                    shift_10[i] = 40;
                }
                fcost = ACKLEY.Ackley(DanhGiaQuanThe.maHoa_10(X.Gen, 6,50), matrix_10, shift_10);
                X.dim = 50;
            }
            if(task_id==7){
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j <= i; j++) {
                        if (i == j) {
                            matrix_10[i][j] = 1;
                        } else {
                            matrix_10[i][j] = matrix_10[j][i] = 0;
                        }
                    }
                }
                for (int i = 0; i < shift_10.length; i++) {
                    shift_10[i] = -0.4;
                }
                fcost = Weierstrass.Weierstrass(DanhGiaQuanThe.maHoa_10(X.Gen, 7,50), matrix_10, shift_10);
                X.dim = 50;
            }
            if(task_id==8){
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j <= i; j++) {
                        if (i == j) {
                            matrix_10[i][j] = 1;
                        } else {
                            matrix_10[i][j] = matrix_10[j][i] = 0;
                        }
                    }
                }
                fcost = SCHWEFEL.Schwefel(DanhGiaQuanThe.maHoa_10(X.Gen, 8,50), matrix_10, shift_10);
                X.dim = 50;
            }
            if(task_id==9){
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j <= i; j++) {
                        if (i == j) {
                            matrix_10[i][j] = 1;
                        } else {
                            matrix_10[i][j] = matrix_10[j][i] = 0;
                        }
                    }
                }
                for (int i = 0; i < shift_10.length / 2; i++) {
                    shift_10[i] = -80;
                }
                for (int i = shift_10.length / 2; i < shift_10.length; i++) {
                    shift_10[i] = 80;
                }
                fcost = GRIEWANK.Griewank(DanhGiaQuanThe.maHoa_10(X.Gen, 9,50), matrix_10, shift_10);
                X.dim = 50;
            }
            if(task_id==10){
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j <= i; j++) {
                        if (i == j) {
                            matrix_10[i][j] = 1;
                        } else {
                            matrix_10[i][j] = matrix_10[j][i] = 0;
                        }
                    }
                }
                for (int i = 0; i < shift_10.length / 2; i++) {
                    shift_10[i] = 40;
                }
                for (int i = shift_10.length / 2; i < shift_10.length; i++) {
                    shift_10[i] = -40;
                }
                fcost = RASTRIGIN.Rastrigin(DanhGiaQuanThe.maHoa_10(X.Gen, 10,50), matrix_10, shift_10);
                X.dim = 50;
            }
        }else if(soTacVu==2){

        }
        return fcost;
    }
}
