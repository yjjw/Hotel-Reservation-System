package Object;

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.sql.Date;

public class MakeReservationInfo {

    int reserve_num;
    int number_of_guest;
    Date start_date;
    Date end_date;
    double discount;
    int ID;

    public MakeReservationInfo(int reserve_num, int number_of_guest, Date start_date, Date end_date, double discount, int ID){
        this.reserve_num = reserve_num;
        this.number_of_guest = number_of_guest;
        this.start_date = start_date;
        this.end_date = end_date;
        this.discount = discount;
        this.ID = ID;
    }

    public int getReserve_num(){return reserve_num;}

    public int getNumber_of_guest(){return number_of_guest;}

    public Date getStart_date(){return start_date;}

    public Date getEnd_date(){return end_date;}

    public double getDiscount(){return discount;}

    public int getID(){return ID;}


}
