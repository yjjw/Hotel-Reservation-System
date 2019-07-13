package UI;

import Oracle.OraGuest;
import Object.GuestInfo;
import Oracle.OraVIP;

import javax.swing.*;
import java.awt.event.*;

public class InfoGuest extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel infoPanel;
    private JLabel nameLabel;
    private JLabel bdayLabel;
    private JLabel phoneLabel;
    private JLabel cardLabel;
    private JLabel idLabel;
    private JLabel pointLabel;
    private static InfoGuest dialog;
    OraGuest gm = new OraGuest();
    OraVIP vm = new OraVIP();
    private GuestInfo guest;

    public InfoGuest(int a) {
        guest = gm.getGuestById(a);
        nameLabel.setText(guest.getName());
        bdayLabel.setText(guest.getBirthday().toString());
        phoneLabel.setText(guest.getPhoneNum()+"");
        cardLabel.setText(guest.getCredit_card_num()+"");
        idLabel.setText(guest.getID()+"");
        if(vm.getVipWithID(a) == null){
            pointLabel.setText("You are not a Member!");
        }else{
            pointLabel.setText(""+vm.getVipWithID(a).getPoints());
        }
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
        InfoEditGuest.run(guest.getID());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void run(int id) {
        dialog = new InfoGuest(id);
        dialog.pack();
        dialog.setVisible(true);
    }
}
