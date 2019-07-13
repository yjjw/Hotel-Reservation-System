package Object;

public class EmployeeInfo {
    String name;
    int id;
    long phone_num;

    public EmployeeInfo(String name,int employee_ID,long phone_num){
        this.name = name;
        this.id = employee_ID;
        this.phone_num = phone_num;
    }

    public int getID() {return id;}

    public String getName(){return name;}

    public long getPhoneNum(){return phone_num;}

}


