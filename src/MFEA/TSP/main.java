package MFEA.TSP;

import Function.BENCHMARK;

import java.io.*;
import java.util.Random;

import static MFEA.TSP.KhoiTao.*;
import static MFEA.TSP.DanhGiaQuanThe.*;
import static MFEA.TSP.LaiGhep.*;
import static java.lang.Math.pow;

public class main {
    static int theHe = 0; //biến đếm thế hệ
        //id Thế hệ hiện tại = theHe % 2
        //id Thế hệ trước đó = 1 - Thế hệ hiện tại
    static int maxCaThe = 100; //Số cá thể ở mỗi tác vụ (Nghĩa là tổng số cá thể của quần thể = Số tác vụ * maxCaThe)
    public static int soTacVu = 50; //Số lượng tác vụ
    static int globalGen = 50; //Số lượng Gen của cá thể trong không gian chung
    static double xsDotBien = 1;
    static double xacSuatLaiCheoToiThieu = 0.1;

    static double[][] M = new double[C(2,soTacVu)][5]; // Bộ nhớ lịch sử thành công , 10 tác vụ => có 50C2 = 1225 bộ nhớ
    static int[] p = new int[C(2,soTacVu)]; //Vị trí update M tiếp theo
    static double σ = 0.1; //Giá trị độ lệch chuẩn tối ưu nhất (=0.1)

    public static int Nmin = 30; //Số cá thể tối thiểu ở mỗi tác vụ
    public static int[] Ni = new int[soTacVu]; // số lượng cá thể ban đầu ở mỗi tác vụ
    public static int[] Eval = new int[soTacVu]; //Max số lần đánh giá ở mỗi tác vụ là 100000
    public static int totalEval = 0; //tổng số đánh giá
    static int maxEval = 100000-100; //Số lượng đánh giá tối đa

    public static boolean print_theHe_0 = false; //Có hay không in ra các cá thể trong thế hệ đầu tiên
    public static boolean print_ketQua_theHe = true; //Có hay không in kết quả tốt nhất từng task của từng thế hệ
    public static int print_theHe = 0; //In ra các cá thể ở thế hệ print_theHe (Đặt = 0 để tắt chức năng này)

    static double[] oldBest_fcost = new double[soTacVu]; //Lưu kết quả tốt nhất của thế hệ trước đó
    static double[] caiThien_fcost = new double[soTacVu]; // Lưu giá trị cả thiện của mỗi tác vụ qua từng thế hệ (Có thể biểu trưng cho độ khó của tác vụ)

//    static int dim_laiCheo_ChuDong = 4; //Luôn phải nhỏ hơn số tác vụ - 1
//    static int[][] laiCheo_ChuDong = new int[soTacVu][dim_laiCheo_ChuDong]; //Chủ động lai chéo để loại bỏ các tác vụ không tương đồng
    static int theHe_laiCheo1 = 0;
    static int theHe_laiCheo2 = theHe_laiCheo1 + 100;
    static int[] laiCheo = new int[C(2,soTacVu)]; //Lưu số lần lai chéo gặp nhau giữa 2 tác vụ khác nhau
    static int[] laiCheo_caiThien = new int[C(2,soTacVu)]; //Số lần lai chéo mà tác vụ con được cải thiện

    static double[] good = new double[soTacVu]; //Ghi lại kết quả tốt nhất của từng tác vụ qua các thế hệ

    //----------Dựa trên cải tiến của Lê Kiên, Tấn Minh-------------
    static double[][] Z = new double[C(2,soTacVu)][globalGen]; //Xác suất lai ghép trên từng chiều (Xác suất chỉ áp dụng khi lai ghép 2 tác vụ riêng biệt)

    //-----------------------------------------------------

    //Lưu dữ liệu đọc từ file
    public static double[][][] matrix = new double[soTacVu][50][50];
    public static double[][] shift = new double[soTacVu][50];

    static NST[][] dsNST = new NST[2][soTacVu*maxCaThe]; //[thế hệ][tác vụ][cá thể] đưa ra cá thế ở thế hệ tương ứng, ở đây chỉ lưu lại 2 thế hệ là thế hệ hiện tại và thế hệ trước đó

    public static void main(String[] args) throws IOException {
        for(int ID = 1;ID<=1;ID++){
            for(int lan=1;lan<=1;lan++)
            {
                //reset giá trị các biến
                theHe = 0;
                totalEval = 0;

                File file = new File("C:\\Users\\monleak-GV72-7RE\\Desktop\\Ketqua2\\benchmark_"+ID+"_"+lan);
                OutputStream outputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

                System.out.println("Chương trình đã chạy!");
                //Đọc file dữ liệu
                if(soTacVu==50){
                    String file_dir = "G:\\IdeaProjects\\LSA-MFEA_50task\\Data\\Tasks\\benchmark_" + ID;
                    for(int task_id=1;task_id<=soTacVu;task_id++){
                        String shift_file = file_dir + "\\bias_" + task_id;
                        String rotation_file = file_dir + "\\matrix_"+ task_id;
                        matrix[task_id-1] = BENCHMARK.read_matrix(rotation_file, soTacVu);
                        shift[task_id-1] = BENCHMARK.read_bias(shift_file,soTacVu);
                    }
                }

                for (int i=0 ; i<C(2,soTacVu) ; i++){
                    //Đặt giá trị khởi tạo ban đầu cho các biến
                    for(int j=0;j<5;j++){
                        M[i][j] = 0.5;
                    }
                    p[i] = 0;
                    laiCheo[i] =0;
                    laiCheo_caiThien[i]=0;
                    for (int j=0;j<globalGen;j++){
                        Z[i][j] = 0.5;
                    }
                }
                for(int i=0;i<2;i++) {
                    for (int j = 0; j < soTacVu * maxCaThe; j++) {
                        dsNST[i][j] = new NST();
                        dsNST[i][j].khoiTaoDoiTuong();
                    }
                }
                for (int k=0;k<soTacVu;k++){
                    //Tổng số đánh giá của các tác vụ ban đầu = 0
                    Eval[k] = 0;
                    //Số lượng cá thể của mỗi tác vụ ban đầu bằng maxCaThe
                    Ni[k] = maxCaThe;
                    oldBest_fcost[k] =0;
                    caiThien_fcost[k] =0;
                }

                khoiTao();
                danhGiaCaThe(dsNST[theHe%2],ID);

                if(print_theHe_0){
                    for (int k=0;k<soTacVu;k++){
                        for (int i=k*maxCaThe;i<(k+1)*maxCaThe;i++){
                            System.out.print(k+1+" "+i+" ");
                            outputStreamWriter.write(k+1+" "+i+" ");
                            for(int gen =0;gen<globalGen;gen++){
                                System.out.print(dsNST[theHe%2][i].Gen[gen]+" ");
                                outputStreamWriter.write(dsNST[theHe%2][i].Gen[gen]+" ");
                            }
                            System.out.print(" Fcost:"+dsNST[theHe%2][i].f_cost+" Skillfactor: "+dsNST[theHe%2][i].skill_factor+" Rank: "+dsNST[theHe%2][i].rank+"\n");
                            outputStreamWriter.write(" Fcost:"+dsNST[theHe%2][i].f_cost+" Skillfactor: "+dsNST[theHe%2][i].skill_factor+" Rank: "+dsNST[theHe%2][i].rank+"\n");
                        }
                    }
                }
                for (int i=0;i<dsNST[theHe%2].length;i++){
                    if(dsNST[theHe%2][i].rank == 1){
                        good[dsNST[theHe%2][i].skill_factor] = dsNST[theHe%2][i].f_cost;
                    }
                }
//                oldBest_fcost = good;
                if(print_ketQua_theHe){
                    System.out.print(theHe+": ");
                    outputStreamWriter.write(theHe+": ");
                    for (double v : good) {
                        System.out.print(v + "\t");
                        outputStreamWriter.write(v + "\t");
                    }
                    System.out.print("\n");
                    outputStreamWriter.write("\n");
                }

                while (totalEval<maxEval*soTacVu){
                    //Chạy chương trình đến khi thỏa mãn điều kiện dừng
                    for (int i=0;i<good.length;i++){
                        oldBest_fcost[i] = good[i];
                    }
                    theHe++; //Tăng biến đếm thế hệ

                    if(theHe % theHe_laiCheo2 == 0){
                        for (int i=0;i<C(2,soTacVu);i++){
                            if(laiCheo_caiThien[i] < laiCheo[i]*1.0/3){
                                for (int j=0;j<5;j++){
                                    M[i][j] = xacSuatLaiCheoToiThieu;
                                }
                            }
                            if(laiCheo_caiThien[i] > laiCheo[i]*1.0/2){
                                for (int j=0;j<5;j++){
                                    M[i][j] = 1;
                                }
                            }
                            laiCheo[i] = 0;
                            laiCheo_caiThien[i] = 0;
                        }
                    }

                    //50C2 = 1225 (Số lượng cặp được chọn ra từ 50 tác vụ)
                    double[][] S = new double[C(2,soTacVu)][soTacVu*maxCaThe]; //Lưu trữ các giá trị rmp tốt
                    double[][] δ = new double[C(2,soTacVu)][soTacVu*maxCaThe]; //Lưu giữ giá trị Delta
                    int[] demS = theHeTiepTheo(S,δ,ID);
                    danhGiaCaThe(dsNST[theHe%2],ID);
                    updateM(S,δ,demS);
                    LPSR();
                    if(print_theHe == theHe){
                        for (int k=0;k<soTacVu;k++){
                            for (int i=k*maxCaThe;i<(k+1)*maxCaThe;i++){
                                System.out.print(k+1+" "+i+" ");
                                outputStreamWriter.write(k+1+" "+i+" ");
                                for(int gen =0;gen<globalGen;gen++){
                                    System.out.print(dsNST[theHe%2][i].Gen[gen]+" ");
                                    outputStreamWriter.write(dsNST[theHe%2][i].Gen[gen]+" ");
                                }
                                System.out.print(" Fcost:"+dsNST[theHe%2][i].f_cost+" Skillfactor: "+dsNST[theHe%2][i].skill_factor+" Rank: "+dsNST[theHe%2][i].rank+"\n");
                                outputStreamWriter.write(" Fcost:"+dsNST[theHe%2][i].f_cost+" Skillfactor: "+dsNST[theHe%2][i].skill_factor+" Rank: "+dsNST[theHe%2][i].rank+"\n");
                            }
                        }
                    }
                    for (int i=0;i<soTacVu*maxCaThe;i++){
                        if(dsNST[theHe%2][i].rank == 1){
                            good[dsNST[theHe%2][i].skill_factor] = dsNST[theHe%2][i].f_cost;
                        }
                    }

                    for (int i=0;i<soTacVu;i++){
                        caiThien_fcost[i] += (oldBest_fcost[i] - good[i])/oldBest_fcost[i];
                    }
                    if(print_ketQua_theHe){
                        System.out.print(theHe+" "+totalEval+": ");
                        outputStreamWriter.write(totalEval+": ");
                        for (double v : good) {
                            System.out.print(v + "\t");
                            outputStreamWriter.write(v + "\t");
                        }
                        System.out.print("\n");
                        outputStreamWriter.write("\n");
                    }
                }
                System.out.print("\nKẾT QUẢ CUỐI CÙNG:\n");
                outputStreamWriter.write("\nKẾT QUẢ CUỐI CÙNG:\n");
                for (int i=0;i<soTacVu;i++){
                    System.out.print("Task:"+(i+1)+" Stt:"+i*maxCaThe+" Gen:");
                    outputStreamWriter.write("Task:"+(i+1)+" Stt:"+i*maxCaThe+" Gen:");
                    for(int j=0;j<globalGen;j++){
                        System.out.print(dsNST[theHe%2][i*maxCaThe].Gen[j]+" ");
                        outputStreamWriter.write(dsNST[theHe%2][i*maxCaThe].Gen[j]+" ");
                    }
                    System.out.print("\tFcost:"+dsNST[theHe%2][i*maxCaThe].f_cost+"\n");
                    outputStreamWriter.write("\tFcost:"+dsNST[theHe%2][i*maxCaThe].f_cost+"\n");
                }
                System.out.print("\n");
                for(int i=0;i<soTacVu;i++){
                    System.out.print(Eval[i]+" ");
                }
                outputStreamWriter.flush();
            }
        }
    }
}
