package Object;

public class RoomInfo {

    int room_num;
    String type;
    double price;

    public RoomInfo(int room_num, String type, double price){
        this.room_num = room_num;
        this.type = type;
        this.price = price;
    }

    public int getRoom_num(){return room_num;}

    public String getType(){return type;}

    public double getPrice(){return price;}
}
