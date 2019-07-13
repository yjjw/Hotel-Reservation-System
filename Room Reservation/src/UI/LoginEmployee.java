package UI;

import Oracle.OraEmployee;
import com.sun.deploy.panel.ExceptionListDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginEmployee {
    private JPanel topTItle;
    private JPanel buttonField;
    private JButton loginButton;
    private JTextField employeeIDTextField;
    private JPanel mainPanel;
    private static JFrame frame = new JFrame("LoginEmployee");
    private OraEmployee em = new OraEmployee();
    public LoginEmployee() {

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    Integer id = Integer.parseInt(employeeIDTextField.getText());

                    if(em.isValidEID(id)){
                        ManagementHUB.run(id);
                        frame.dispose();
                    }else{
                        JOptionPane.showMessageDialog(frame, "Employee ID does not exist!");
                    }
                }catch(NumberFormatException exp){JOptionPane.showMessageDialog(frame, "Input is INVALID!");}
                catch (Exception exp){

                    JOptionPane.showMessageDialog(frame, "Error!");
                }
            }
        });
    }

    public static void run() {

        frame.setContentPane(new LoginEmployee().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
