package UI;

import Oracle.OraGuest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGuest {

    public LoginGuest(){

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                RegisterGuest.run();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    //log in with phone#
                    if(selectID == false){
                        long a = Long.parseLong(selectLogInTypeTextField.getText());
                        if(gm.isValidPhoneNumber(a)){
                            RoomView.run(a,1);
                            frame.dispose();
                        }
                        else{
                            JOptionPane.showMessageDialog(frame, "Phone Number does not exist!");
                        }
                    }
                        //log in with ID
                    else if(selectID == true){
                        int a = Integer.parseInt(selectLogInTypeTextField.getText());
                        if(gm.isValidID(a)){
                            RoomView.run(a,0);
                            frame.dispose();
                        }
                        else{
                            JOptionPane.showMessageDialog(frame, "User ID does not exist!");
                        }
                    }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(frame, "Please provide a valid number!");
                }
            }
        });
        phoneNumberRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectID = false;
            }
        });
        guestIDRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectID = true;
            }
        });
    }
    private JPanel mainPanel;
    private JRadioButton phoneNumberRadioButton;
    private JButton registerButton;
    private JPanel topTItle;
    private JPanel buttonField;
    private JButton loginButton;
    private JPanel buttonPanel;
    private JPanel inputPanel;
    private JPanel choicePanel;
    private JPanel textPanel;
    private JRadioButton guestIDRadioButton;
    private JTextField selectLogInTypeTextField;
    private JPanel buttom;
    public static JFrame frame;
    private OraGuest gm = new OraGuest();
    private boolean selectID = guestIDRadioButton.isSelected();
    public static void run() {
        frame = new JFrame("LoginGuest");
        frame.setContentPane(new LoginGuest().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

