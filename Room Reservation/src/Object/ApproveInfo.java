package Object;

public class ApproveInfo {

    int reserve_num;
    int employee_ID;

    public ApproveInfo(int reserve_num, int employee_ID){
        this.reserve_num = reserve_num;
        this.employee_ID = employee_ID;
    }

    public int getReserve_num(){return reserve_num;}

    public int getEmployee_ID(){return employee_ID;}

}
