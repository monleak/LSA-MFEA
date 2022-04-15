package MFEA.TSP;

import java.io.*;
import java.util.Random;

import static MFEA.TSP.main.*;
import static java.lang.Math.*;

public class KhoiTao {

    //Các hàm sinh ngẫu nhiên
    public static int genRandom(int gioiHan){
        //gioiHan là giá trị giới hạn, các số được sinh ra nằm trong [1;gioiHan]
        //VD: Nếu muốn sinh ra 1 hoặc 2, hãy đặt gioiHan = 2
        Random output = new Random();
        int number = output.nextInt(gioiHan);
        return number+1;
    }
    public static double genRandomDouble(){
        //Sinh kiểu double, các số được random trong khoảng [0;1)
        Random output = new Random();
        double number = output.nextDouble();
        return number;
    }
    public static int C(int k, int n) {
        if (k == 0 || k == n) return 1;
        if (k == 1) return n;
        return C(k - 1, n - 1) + C(k, n - 1);
    }
    public static void khoiTao() {
        //Khởi tạo quần thể ban đầu với maxCaThe*soTacVu cá thể
        //Ở không gian chung các cá thể có gen Xi được khởi tạo trong khoảng -1 đến 1
        for (int k=0;k<soTacVu;k++){
            for (int i=k*maxCaThe;i<(k+1)*maxCaThe;i++){
                for (int j=0;j<globalGen;j++){
                    dsNST[theHe%2][i].Gen[j] = genRandomDouble();
                }
                dsNST[theHe%2][i].skill_factor=k;
            }
        }
    }
}
