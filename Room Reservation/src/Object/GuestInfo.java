package Object;


import java.sql.Date;

public class GuestInfo {
    int id;
    String name;
    Date birthday;
    long phone_num;
    long credit_card_num;

    public GuestInfo(int id, String name, Date birthday, long phone_num, long credit_card_num) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.phone_num = phone_num;
        this.credit_card_num = credit_card_num;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public long getPhoneNum(){
        return phone_num;
    }

    public long getCredit_card_num(){
        return credit_card_num;
    }

}