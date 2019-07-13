package UI;

import Oracle.OraEmployee;
import Object.EmployeeInfo;
import javax.swing.*;
import java.awt.event.*;

public class InfoEmployee extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel idLabel;
    private JLabel phoneLabel;
    private JLabel nameLAbel;
    private static InfoEmployee dialog;
    private static OraEmployee em = new OraEmployee();
    private static EmployeeInfo employee;

    public InfoEmployee(int id) {
        employee = em.getEmployeeById(id);
        idLabel.setText(""+employee.getID());
        phoneLabel.setText(employee.getPhoneNum()+"");
        nameLAbel.setText(employee.getName());

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void run(int id) {
        dialog = new InfoEmployee(id);
        dialog.pack();
        dialog.setVisible(true);
    }
}
