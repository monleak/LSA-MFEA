package MFEA.TSP;

import static MFEA.TSP.main.*;

public class NST {
    public double[] Gen; //[i] đưa ra giá trị của gen ở vị trí i
    public int dim; // Số chiều của tác vụ cá thể
    public double f_cost; //Chỉ đánh giá cá thể dựa trên tác vụ mà nó giải quyết
    public int rank; //[tác vụ] đưa ra xếp hạng của cá thể trong tác vụ (Xếp theo factorial-cost)
    public int skill_factor; // = tác vụ khởi tạo (0,1,2,3,4,...)
    public double scalar_fitness; // = 1/(min(rank[]))

    public void khoiTaoDoiTuong(){
        Gen = new double[globalGen];
    }
}
