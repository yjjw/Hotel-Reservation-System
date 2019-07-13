package UI;

import Oracle.OraGuest;
import Oracle.OraVIP;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;

public class RegisterGuest extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel mainField;
    private JPanel buttonBox;
    private JPanel buttomBox;
    private JTextField credit;
    private JTextField phone;
    private JComboBox month;
    private JComboBox date;
    private JComboBox year;
    private JCheckBox registerAsVIPCheckBox;
    private JLabel bday;
    private JLabel cardtxt;
    private JTextField name;
    private JLabel nametxt;
    private static JFrame frame = new JFrame("RegisterGuest");
    OraGuest gm = new OraGuest();
    OraVIP vm = new OraVIP();

    public static void run() {

        frame.setContentPane(new RegisterGuest().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public RegisterGuest() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void onOK() {
        // add your code here
        try{
            int y = year.getSelectedIndex();
            int m = month.getSelectedIndex();
            int d = 1+date.getSelectedIndex();
            long ph = Long.parseLong(phone.getText());
            long cr = Long.parseLong(credit.getText());
            if(ph<1000000000L||ph>9999999999L||gm.isValidPhoneNumber(ph)){
                JOptionPane.showMessageDialog(frame, "INVALID Phone Number!");
                return;
            }
            if(cr<1000000000000000L||cr>9999999999999999L){
                JOptionPane.showMessageDialog(frame, "INVALID Credit Card Number!");
                return;
            }
            int id = gm.InsertGuest(name.getText(),new Date(y,m,d),ph,cr);
            JOptionPane.showMessageDialog(frame, "guest info check - birthday: "+(1900+y)+"-"+(m+1)+"-"+d+" id: "+ id +" Name: "+name.getText()+" Phone: "+ph+" Card: "+cr);
            if(registerAsVIPCheckBox.isSelected()){
                vm.BeAVip(gm.getGuestByPhoneNumber(ph).getID());
            }
            frame.dispose();
        }catch(Exception e){
            JOptionPane.showMessageDialog(frame, "INVALID Phone Number or Credit Card Number!");
        }

    }

    private void onCancel() {
        // add your code here if necessary
        frame.dispose();
    }
}
