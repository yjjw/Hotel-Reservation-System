package Object;

public class ProvidesInfo {
    
    int reserve_num;
    int stall_num;
    
    public ProvidesInfo(int reserve_num, int stall_num){
        
        this.reserve_num = reserve_num;
        this.stall_num = reserve_num;
    }
    
    public int getReserve_num(){return reserve_num;}
    
    public int getStall_num(){return stall_num;}
}
