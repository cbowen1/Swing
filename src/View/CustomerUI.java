package View;

import Controller.CustomerDA;
import Model.Customer;

import javax.swing.*;

public class CustomerUI {

    private JFrame frame;
    private JTextField custID_tf;
    private JLabel custID_lbl;
    private JTextField custName_tf;
    private JLabel custName_lbl;

    private JButton next_btn;

    private CustomerDA customerDA;
    public CustomerUI() {
        customerDA = new CustomerDA();
        frame = new JFrame();
        frame.setBounds(100,100,570,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        custID_lbl = new JLabel("Customer ID:");
        custID_lbl.setBounds(58,10,76,16);
        frame.getContentPane().add(custID_lbl);

        custID_tf = new JTextField();
        custID_tf.setBounds(141,7,376,22);
        frame.getContentPane().add(custID_tf);
        custID_tf.setColumns(10);
        custID_tf.setEditable(false);

        next_btn = new JButton("Next Cust.");
        next_btn.setBounds(150,100, 100,35);
        frame.getContentPane().add(next_btn);

        frame.setVisible(true);
    }
}
