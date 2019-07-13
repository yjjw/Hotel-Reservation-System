package UI;
##cool cat four bro
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Boot{

    private JButton guestButton;
    private JButton employeeButton;
    private JPanel mainPanel;
    private JPanel button;
    private JLabel titlebox;
    private JPanel title;
    public static JFrame bFrame ;

    public Boot() {
        guestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGuest.run();
                bFrame.dispose();

            }
        });
        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LoginEmployee.run();
                bFrame.dispose();
            }
        });
    }
    public static void main(String[] args){
        bFrame = new JFrame("xxx hotel");
        bFrame.setContentPane(new Boot().mainPanel);
        bFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bFrame.pack();
        bFrame.setVisible(true);

    }
}
