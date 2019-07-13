package Object;

public class Parking_SpraceInfo {
    
    String plate_num;
    int stall_num;
    
    public Parking_SpraceInfo(String plate_num, int stall_num){
        
        this.plate_num = plate_num;
        this.stall_num = stall_num;
    }
    
    public String getPlate_num(){return plate_num;}
    
    public int getStall_num(){return stall_num;}
}
