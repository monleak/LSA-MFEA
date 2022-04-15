package MFEA.TSP;

import Function.BENCHMARK;

import java.io.IOException;
import java.util.Random;

import static MFEA.TSP.main.p;
import static java.lang.Double.NaN;
import static java.lang.Math.pow;

import static MFEA.TSP.main.*;
import static MFEA.TSP.DanhGiaQuanThe.*;
import static MFEA.TSP.KhoiTao.*;

public class LaiGhep {

    public static int LuaChonChaMe(){
        int temp;
        do{
            //Lựa chọn cá thể trở thành cha mẹ trong thế hệ theHe
            temp = genRandom(maxCaThe*soTacVu)-1; //random cá thể
            //Kiểm tra xem cá thể vừa chọn có hợp lệ không (Do kích thước quần thể giảm tuyến tính qua các thế hệ)
            // tác vụ = temp/maxCaThe
            // Id cá thể trong tác vụ = temp%maxCaThe
        } while (temp%maxCaThe >= Ni[dsNST[1-theHe%2][temp].skill_factor] || Eval[dsNST[1-theHe%2][temp].skill_factor] >= maxEval);
        return temp;
    }
    public static NST mutation(NST parent) throws IOException {
        NST ind = parent;
        for (int i = 1; i <= globalGen; i++) {
            if (genRandomDouble() < 1.0/globalGen) {
                double u = genRandomDouble();
                double v = 0;
                if (u <= 0.5) {
                    double del = Math.pow((2 * u), 1.0 / (1 + 5)) - 1;
                    v = ind.Gen[i - 1] * (del + 1);
                } else {
                    double del = 1 - Math.pow(2 * (1 - u), 1.0 / (1 + 5));
                    v = ind.Gen[i - 1] + del * (1 - ind.Gen[i - 1]);
                }
                if (v > 1) {
                    ind.Gen[i - 1] = ind.Gen[i - 1] + genRandomDouble() * (1 - ind.Gen[i - 1]);
                } else if (v < 0) {
                    ind.Gen[i - 1] = ind.Gen[i - 1] * genRandomDouble();
                }
            }
        }
        return ind;
    }

    public static void variable_swap(NST p1, NST p2){
    //Hàm swap của anh đạo
        for(int i=0; i<globalGen; i++){
            if(genRandomDouble() > 0.5){
                double temp1 = p1.Gen[i];
                double temp2 = p2.Gen[i];
                p1.Gen[i] = temp2;
                p2.Gen[i] = temp1;
            }
        }
    }

    public static NST[] laiGhep_cungSkillfactor(NST cha,NST me, int ID, int dim) throws IOException {
        //Lai ghép 2 cha mẹ có cùng skillfactor
        NST[] child = new NST[2];
        for(int i=0;i<2;i++){
            child[i] = new NST();
            child[i].khoiTaoDoiTuong();
            child[i].skill_factor = cha.skill_factor;
            for (int j=0;j<globalGen;j++){
                child[i].Gen[j] = cha.Gen[j];
            }
        }
        //Ở đây ta sử dụng phương pháp Lai ghép chéo hóa nhị phân - SBX
        //Đầu tiên random 1 số thực u trong khoảng [0,1]
        //Tính hệ số Beta
        //Chọn 1 Nc là hệ số phân bố thường nằm trong khoảng 2 đến 10, Nc càng cao thì cá thể con sinh ra càng gần cha mẹ
        int Nc=2;
        double cf[] = new double[dim];
        for(int i=0; i<dim; i++){
            cf[i] = 1;
            double u = genRandomDouble();
            if(u<=0.5){
                cf[i]= Math.pow((2*u), 1.0/(Nc +1));
            }else{
                cf[i]= Math.pow(2*(1-u), -1.0/(Nc +1));
            }
        }
        for(int i=0;i<dim; i++){
            double v = 0.5*((1+cf[i])*cha.Gen[i] + (1-cf[i])*me.Gen[i]);
            if(v>1) v=1;
            else if(v<0) v= 0;
            child[0].Gen[i]= v;

            double v2 = 0.5*((1-cf[i])*cha.Gen[i] + (1+cf[i])*me.Gen[i]);
            if(v2>1) v2= 1;
            else if(v2<0) v2= 0;
            child[1].Gen[i]= v2;
        }
        //Đột biến 2 con
        if(genRandomDouble()<xsDotBien){
            child[0] = mutation(child[0]);
        }
        if(genRandomDouble()<xsDotBien){
            child[1] = mutation(child[1]);
        }
        variable_swap(child[0],child[1]);
        return child;
    }
    public static NST[] laiGhep_khacSkillfactor(NST cha, NST me, boolean temp, int ID) throws IOException {
        NST[] child = new NST[2];
        for(int i=0;i<2;i++){
            child[i] = new NST();
            child[i].khoiTaoDoiTuong();
        }
        if(temp){
            //Ở đây ta sử dụng phương pháp Lai ghép chéo hóa nhị phân - SBX
            //Đầu tiên random 1 số thực u trong khoảng [0,1]
            //Tính hệ số Beta
            //Chọn 1 Nc là hệ số phân bố thường nằm trong khoảng 2 đến 10, Nc càng cao thì cá thể con sinh ra càng gần cha mẹ
            int Nc=2;
            double cf[] = new double[globalGen];
            for(int i=0; i<globalGen; i++){
                cf[i] = 1;
                double u = genRandomDouble();
                if(u<=0.5){
                    cf[i]= Math.pow((2*u), 1.0/(Nc +1));
                }else{
                    cf[i]= Math.pow(2*(1-u), -1.0/(Nc +1));
                }
            }
            for(int i=0;i<globalGen; i++){
                double v = 0.5*((1+cf[i])*cha.Gen[i] + (1-cf[i])*me.Gen[i]);
                if(v>1) v=1;
                else if(v<0) v= 0;
                child[0].Gen[i]= v;

                double v2 = 0.5*((1-cf[i])*cha.Gen[i] + (1+cf[i])*me.Gen[i]);
                if(v2>1) v2= 1;
                else if(v2<0) v2= 0;
                child[1].Gen[i]= v2;
            }
            for(int i=0;i<child.length;i++){
                child[i].f_cost=BENCHMARK.benchmark(ID,child[i].skill_factor + 1,child[i]);
            }
            //Đột biến 2 con và đặt skill factor của 2 con ngẫu nhiên bằng của cha hoặc mẹ
            if(genRandomDouble()<xsDotBien){
                child[0] = mutation(child[0]);
            }
            if(genRandomDouble()<xsDotBien){
                child[1] = mutation(child[1]);
            }
            if(genRandomDouble()<0.5){
                child[0].skill_factor = cha.skill_factor;
                child[1].skill_factor = me.skill_factor;
            }else{
                child[1].skill_factor = cha.skill_factor;
                child[0].skill_factor = me.skill_factor;
            }
        }else {
            //Chọn 2 cá thể có cùng skillfactor với cha mẹ (skill factor của cha mẹ khác nhau nên 2 cá thể cũng có skill factor khác nhau)
            int me1 = (genRandom(Ni[cha.skill_factor])-1)+cha.skill_factor*maxCaThe; //Cá thể me1 cùng skill factor với cha sẽ sử dụng để lai  cùng với cha
            int cha1 = (genRandom(Ni[me.skill_factor])-1)+me.skill_factor*maxCaThe; //Cá thể cha1 cùng skill factor với cha sẽ sử dụng để lai  cùng với me
            NST[] child1 = laiGhep_cungSkillfactor(dsNST[1-(theHe%2)][me1],cha,ID,cha.dim);
            NST[] child2 = laiGhep_cungSkillfactor(dsNST[1-(theHe%2)][cha1],me,ID,me.dim);
            child[0] = child1[0];
            child[1] = child2[0];
            for(int i=0;i<child.length;i++){
                child[i].f_cost=BENCHMARK.benchmark(ID,child[i].skill_factor + 1,child[i]);
            }
        }
        return child;
    }
    public static double randomGauss(double µ, double σ){
        //Hàm random Gauss của anh thắng
        Random output = new Random();
        return µ + σ*Math.sqrt(-2.0 * Math.log(output.nextDouble())) * Math.sin(2.0 * Math.PI * output.nextDouble());
    }
    public static double tinhDelta(NST pA, NST pB, NST oA, NST oB){
        //Hàm tính giá trị Tỷ lệ phần trăm cải thiện của cả 2 tác vụ
        //pA, pB là 2 cá thể cha mẹ
        //oA, oB là 2 cá thể con
        double Delta = 0.0;
        double tempA = 0.0,tempB = 0.0;
        if(oA.skill_factor==pA.skill_factor){
            tempA = (pA.f_cost-oA.f_cost)/pA.f_cost;
        }else {
            tempB = (pB.f_cost-oA.f_cost)/pB.f_cost;
        }
        if(oB.skill_factor==pA.skill_factor){
            tempA = (pA.f_cost-oB.f_cost)/pA.f_cost;
        }else {
            tempB = (pB.f_cost-oB.f_cost)/pB.f_cost;
        }
        if(caiThien_fcost[pA.skill_factor] == caiThien_fcost[pB.skill_factor]){
            Delta = tempA + tempB;
        }else {
            double sum = caiThien_fcost[pA.skill_factor] + caiThien_fcost[pB.skill_factor];
            Delta = (1-(caiThien_fcost[pA.skill_factor]/sum))*tempA + (1-(caiThien_fcost[pB.skill_factor]/sum))*tempB;
        }
        return Delta;
    }
    public static void updateM(double[][] S,double[][] δ,int[] dem1){
        //Cập nhật bộ nhớ lịch sử thành công
        for(int i=0;i<soTacVu;i++){
            for(int j=i+1;j<soTacVu;j++){
                if(dem1[tinhIdM(i+1,j+1)]>0){
                    double tuSo = 0.0,mauSo = 0.0;
                    for (int i1=0;i1<dem1[tinhIdM(i+1,j+1)];i1++){
                        tuSo += δ[tinhIdM(i+1,j+1)][i1]*Math.pow(S[tinhIdM(i+1,j+1)][i1],2);
                    }
                    for (int i1=0;i1<dem1[tinhIdM(i+1,j+1)];i1++){
                        mauSo += δ[tinhIdM(i+1,j+1)][i1]*S[tinhIdM(i+1,j+1)][i1];
                    }
                    if(mauSo!=0){
                        M[tinhIdM(i+1,j+1)][p[tinhIdM(i+1,j+1)]] = tuSo/mauSo;
                    }else M[tinhIdM(i+1,j+1)][p[tinhIdM(i+1,j+1)]] = 0;
                    p[tinhIdM(i+1,j+1)] = p[tinhIdM(i+1,j+1)]+1;
                    if(p[tinhIdM(i+1,j+1)] == M[tinhIdM(i+1,j+1)].length) p[tinhIdM(i+1,j+1)] = 0;
                }
                else if(laiCheo[tinhIdM(i+1,j+1)]*1.0/3 >= laiCheo_caiThien[tinhIdM(i+1,j+1)]){
                    M[tinhIdM(i+1,j+1)][p[tinhIdM(i+1,j+1)]] = 0;
                    p[tinhIdM(i+1,j+1)] = p[tinhIdM(i+1,j+1)]+1;
                    if(p[tinhIdM(i+1,j+1)] == M[tinhIdM(i+1,j+1)].length) p[tinhIdM(i+1,j+1)] = 0;
                }
            }
        }
    }
    public static int tinhIdM(int sf1, int sf2){
        //Hàm nhận đầu vào là skill factor của 2 cá thể (sf1 != sf2)
        //Đầu ra là id của bộ nhớ M tương ứng
        int p1,p2,id;
        if(sf1<sf2){
            p1 = sf1;
            p2 = sf2;
        }else {
            p1 = sf2;
            p2 = sf1;
        }
        if(p1==1){
            id = p2 - p1 -1;
        }else {
            id=p2-p1;
            id = id + tinhIdM(p1-1,soTacVu);
        }
        return id;
    }
    public static void LPSR(){
        //Chiến lược giảm quy mô dân số tuyến tính
        //Điểm khác biệt giữa LSA MFEA và SA MFEA
        for(int i=0;i<soTacVu;i++){
            //Duyệt qua tất cả các tác vụ
            double n = (Nmin-maxCaThe)*1.0/(maxEval) * Eval[i] + maxCaThe;
            if(n < Ni[i]) Ni[i] = (int)n;
        }
    }
    public static int[] theHeTiepTheo(double[][] S, double[][] δ, int ID) throws IOException {
        NST[] O = new NST[maxCaThe*soTacVu]; //Mảng lưu trữ các con được sinh ra
        int dem=0; //Đếm số lượng con đã sinh ra
        int dem1=0; //Đếm số lượng cá thể hiện có trong quần thể
        for(int i=0;i<soTacVu;i++){
            dem1 += Ni[i];
        }
        int[] demS = new int[C(2,soTacVu)]; //Đếm số lượng phần tử được thêm vào S và δ
        for(int i=0;i<C(2,soTacVu);i++){
            demS[i]=0;
        }
        while(dem < dem1 && totalEval < maxEval*soTacVu){
            int cha = LuaChonChaMe();
            int me;
            if(theHe>=theHe_laiCheo1 && theHe < theHe_laiCheo2){
                //100 thế hệ tiếp theo các tác vụ lai chéo (rmp=1)
                me = LuaChonChaMe();
                while(me == cha || dsNST[1-(theHe%2)][cha].skill_factor == dsNST[1-(theHe%2)][me].skill_factor) me = LuaChonChaMe();
//                me = laiCheo_ChuDong[dsNST[1-(theHe%2)][cha].skill_factor][genRandom(dim_laiCheo_ChuDong)-1];
//                me = genRandom(maxCaThe)-1+me*maxCaThe;
            }else{
                me = LuaChonChaMe();
                while (me==cha) me = LuaChonChaMe();
            }

            if(dsNST[1-(theHe%2)][cha].skill_factor == dsNST[1-(theHe%2)][me].skill_factor){
                NST[] child = laiGhep_cungSkillfactor(dsNST[1-(theHe%2)][cha],dsNST[1-(theHe%2)][me],ID,dsNST[1-(theHe%2)][cha].dim);
                for (int i=0;i<child.length;i++){
                    O[dem] = child[i];
                    totalEval++;
                    Eval[child[i].skill_factor]++; //Tăng số lần đánh giá của tác vụ này lên 1
                    dem++;
                }
            }else {
                int id = tinhIdM(dsNST[1-(theHe%2)][cha].skill_factor+1,dsNST[1-(theHe%2)][me].skill_factor+1);
                int idµ = genRandom(5)-1;
                double µ = M[id][idµ];
                double rmp = randomGauss(µ,σ);
                if(rmp > 1) rmp = 1;
                else if(rmp<0) rmp = 0;

                if(theHe<theHe_laiCheo1) rmp = xacSuatLaiCheoToiThieu;
                else if(theHe<theHe_laiCheo2) rmp=1;

                boolean temp = (genRandomDouble()<rmp);
                NST[] child = laiGhep_khacSkillfactor(dsNST[1-(theHe%2)][cha],dsNST[1-(theHe%2)][me],temp,ID);
                if(temp){
                    laiCheo[tinhIdM(dsNST[1-(theHe%2)][cha].skill_factor+1,dsNST[1-(theHe%2)][me].skill_factor+1)]++;
                }
                for (int i=0;i<child.length;i++){
                    O[dem] = child[i];
                    totalEval++;
                    Eval[child[i].skill_factor]++; //Tăng số lần đánh giá của tác vụ này lên 1
                    dem++;
                }
                double Delta = tinhDelta(dsNST[1-(theHe%2)][cha],dsNST[1-(theHe%2)][me],child[0],child[1]);
                if(Delta>0){
                    S[id][demS[id]] = rmp;
                    δ[id][demS[id]] = Delta;
                    demS[id]++;
                    if(temp){
                        laiCheo_caiThien[tinhIdM(dsNST[1-(theHe%2)][cha].skill_factor+1,dsNST[1-(theHe%2)][me].skill_factor+1)]++;
                    }
                }
            }
        }
        //Xác nhập O và P
        NST[] total = new NST[dem+maxCaThe*soTacVu];
        for(int i=0;i<maxCaThe*soTacVu;i++){
            total[i] = dsNST[1-(theHe%2)][i];
        }
        for(int i=maxCaThe*soTacVu;i<dem+maxCaThe*soTacVu;i++){
            total[i] = O[i-maxCaThe*soTacVu];
        }
        danhGiaCaThe(total,ID);
        //Loại bỏ các cá thể tồi ở mỗi tác vụ, giữ sô lượng cá thể mỗi tác vụ ở mức maxCaThe
        double[] min = new double[soTacVu]; //Biến lưu giá trị
        double[] oldmin = new double[soTacVu]; //Biến lưu giá trị max trước đó
        for (int i=0;i<oldmin.length;i++){
            //Đặt giá trị oldmin =0
            oldmin[i]=0;
        }
        int[] id = new int[soTacVu];
        for (int k=0;k<maxCaThe;k++){
            for (int i=0;i<min.length;i++){
                //Đặt giá trị min ban đầu là lớn nhất
                min[i]=1.7976931348623157 * pow(10,308);
            }
            for(int i=0;i<dem+maxCaThe*soTacVu;i++){
                //Tìm cá thể tốt nhất ở mỗi tác vụ
                if(min[total[i].skill_factor] > total[i].f_cost && total[i].f_cost > oldmin[total[i].skill_factor]){
                    min[total[i].skill_factor] = total[i].f_cost;
                    id[total[i].skill_factor] = i;
                }
            }
            for (int i=0;i<id.length;i++){
                oldmin[i] = min[i];
                dsNST[theHe%2][i*maxCaThe+k] = total[id[i]];
            }
        }
        return demS;
    }
}
