package UI;
## you guys are awesome
import Oracle.OraGuest;
import Object.GuestInfo;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;


public class InfoEditGuest extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel buttomBox;
    private JPanel buttonBox;
    private JPanel mainField;
    private JLabel cardtxt;
    private JTextField credit;
    private JLabel nametxt;
    private JLabel bday;
    private JTextField phone;
    private JComboBox month;
    private JComboBox year;
    private JComboBox date;
    private JTextField name;
    GuestInfo guest;
    OraGuest gm = new OraGuest();
    private static InfoEditGuest dialog;

    public InfoEditGuest(int id) {
        guest = gm.getGuestById(id);
        name.setText(guest.getName());
        credit.setText(((Long)guest.getCredit_card_num()).toString());
        phone.setText(((Long)guest.getPhoneNum()).toString());
        int yr = guest.getBirthday().getYear();
        int mo = guest.getBirthday().getMonth();
        int da = guest.getBirthday().getDate();
        year.setSelectedIndex(yr);
        month.setSelectedIndex(mo);
        date.setSelectedIndex(da-1);

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
        try{
            int y = year.getSelectedIndex();
            int m = month.getSelectedIndex();
            int d = 1+date.getSelectedIndex();
            long ph = Long.parseLong(phone.getText());
            long cr = Long.parseLong(credit.getText());



            if(ph != guest.getPhoneNum()){
                if(ph<1000000000L||ph>9999999999L||gm.isValidPhoneNumber(ph)){
                    JOptionPane.showMessageDialog(dialog, "INVALID Phone Number!");
                    return;
                }
            }
            if(cr<1000000000000000L||cr>9999999999999999L){
                JOptionPane.showMessageDialog(dialog, "INVALID Credit Card Number!");
                return;
            }
            JOptionPane.showMessageDialog(dialog, "guest info check birthday: "+(1900+y)+"-"+(m+1)+"-"+d+" Name: "+name.getText()+" Phone: "+ph+" Card: "+cr);
            gm.updateGuestInfo(guest.getID(), name.getText(), new Date(y,m,d), ph, cr);
           // gm.updatePhone(ph,guest.getID());
            dialog.dispose();
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(dialog, "INVALID Phone Number or Credit Card Number!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(dialog, "ERROR");
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void run(int id) {
        dialog = new InfoEditGuest(id);
        dialog.pack();
        dialog.setVisible(true);
    }
}
