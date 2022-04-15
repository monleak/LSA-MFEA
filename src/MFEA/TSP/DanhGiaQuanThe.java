package MFEA.TSP;

import Function.*;

import java.io.IOException;

import static MFEA.TSP.main.*;

public class DanhGiaQuanThe {
    public static double[] maHoa_10(double[] Gen,int Task, int dim){
        //Mã hóa các cá thể từ không gian chung ra không gian riêng để tính toán
        double[] temp = new double[dim];
        if(Task==1){
            for (int i=0;i<dim;i++){
                temp[i] = (Gen[i]-0.5)*200; //-100 100
            }
        }
        if(Task==2){
            for (int i=0;i<dim;i++){
                temp[i] = (Gen[i]-0.5)*200; //-100 100
            }
        }
        if(Task==3){
            for (int i=0;i<dim;i++){
                temp[i] = (Gen[i]-0.5)*200; //-100 100
            }
        }
        if(Task==4){
            //Riêng task 4 chỉ có 25 phần tử
            for (int i=0;i<dim;i++){
                temp[i] = Gen[i]-0.5; //-0.5 0.5
            }
        }
        if(Task==5){
            for (int i=0;i<dim;i++){
                temp[i] = (Gen[i]-0.5)*100; //-50 50
            }
        }
        if(Task==6){
            for (int i=0;i<dim;i++){
                temp[i] = (Gen[i]-0.5)*100; //-50 50
            }
        }
        if(Task==7){
            for (int i=0;i<dim;i++){
                temp[i] = Gen[i]-0.5; //-0.5 0.5
            }
        }
        if(Task==8){
            for (int i=0;i<dim;i++){
                temp[i] = (Gen[i]-0.5)*1000; //-500 500
            }
        }
        if(Task==9){
            for (int i=0;i<dim;i++){
                temp[i] = (Gen[i]-0.5)*200; //-100 100
            }
        }
        if(Task==10){
            for (int i=0;i<dim;i++){
                temp[i] = (Gen[i]-0.5)*100; //-50 50
            }
        }
        return temp;
    }
    public static double[] maHoa(double[] Gen,int Task){
        //Mã hóa các cá thể từ không gian chung ra không gian riêng để tính toán
        double[] temp = new double[Gen.length];
        if(Task==1){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*200; //-100 100
            }
        }
        if(Task==2){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*100; //-50 50
            }
        }
        if(Task==3){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*100; //-50 50
            }
        }
        if(Task==4){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*100; //-50 50
            }
        }
        if(Task==5){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*200; //-100 100
            }
        }
        if(Task==6){
            for (int i=0;i<Gen.length;i++){
                temp[i] = Gen[i]-0.5; //-0.5 0.5
            }
        }
        if(Task==7){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*1000; //-500 500
            }
        }
        return temp;
    }
    public static void danhGiaCaThe(NST[] dsNST, int ID) throws IOException {
        for (int i=0;i<dsNST.length;i++){
            dsNST[i].rank =0;
        }
        for (int i=0;i<dsNST.length;i++){
            dsNST[i].f_cost=BENCHMARK.benchmark(ID,dsNST[i].skill_factor + 1,dsNST[i]);
        }
        //Xếp rank cho các cá thể
        double[] good = new double[soTacVu]; //Lưu các giá trị tốt thứ rank của các tác vụ
        int[] idGood = new int[soTacVu]; //Lưu cá thể tốt thứ rank của các tác vụ
        for (int rank=dsNST.length/soTacVu;rank>=1;rank--){
            for (int i=0;i<good.length;i++){
                good[i]=0;
            }
            //Các cá thể có fcost càng nhỏ thì cá thể đó càng tốt (Nghĩa là rank của cá thể đó sẽ cao hơn)
            for (int i=0;i<dsNST.length;i++){
                if(good[dsNST[i].skill_factor] <= dsNST[i].f_cost && dsNST[i].rank == 0){
                    good[dsNST[i].skill_factor] = dsNST[i].f_cost;
                    idGood[dsNST[i].skill_factor] = i;
                }
            }
            for (int k=0;k<soTacVu;k++){
                dsNST[idGood[k]].rank = rank;
            }
        }
    }
}
