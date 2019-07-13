package Object;

import java.sql.Date;

public class VIPInfo{

    int id;
    double points;

   // public VIPInfo(int id, String name, Date birthday, int phone_num, int credit_card_num, int points){
      //  super(id,name,birthday,phone_num,credit_card_num);
    //    this.points =  points;
  //  }
   public VIPInfo(int id, double points) {
       this.id = id;
       this.points = points;
   }

     public int getID(){return id;}

     public double getPoints(){return points;}

}
