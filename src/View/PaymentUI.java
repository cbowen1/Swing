package View;

import Controller.PaymentDA;
import Model.PaymentMethod;
import Model.ShippingData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

public class PaymentUI {
    private JPanel payPanel;
    private JPanel viewPanel;
    private JPanel editPanel;
    private JScrollPane payScrollPane;
    private JTable payTable;
    PaymentDA pda;

    public PaymentUI() {
        init();
        pda = new PaymentDA();
        table_update();
    }

    public void table_update() {
        ArrayList<PaymentMethod> plList = pda.getPayList();

        DefaultTableModel tm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tm.addColumn("ID");
        tm.addColumn("Payment Method");
        tm.addColumn("Payment Amount ");
        tm.addColumn("Status");
        payTable.setModel(tm);
        for(PaymentMethod pm: plList) {
            Vector<Object> rowObj = new Vector<>(4);
            rowObj.add(0, pm.getId());
            rowObj.add(1, pm.getType());
            rowObj.add(2, pm.getAmount());
            rowObj.add(3, pm.getStatus());
            tm.addRow(rowObj);
        }
    }

    private void init() {
        payPanel = new JPanel();
        payPanel.setLayout(new GridBagLayout());
        viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout(0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        payPanel.add(viewPanel, gbc);
        payTable = new JTable();
        payTable.setRowHeight(30);

        payTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                //String tracking = (String) target.getValueAt(row,4);
                /*shippingID = (int) target.getValueAt(row, 0);
                orderId = (int) target.getValueAt(row, 1);
                if(tracking == null) {
                    addTracking.setEnabled(true);
                    orderDelivered.setEnabled(false);
                } else {
                    addTracking.setEnabled(false);
                    if((String) target.getValueAt(row,5) == "false") {
                        orderDelivered.setEnabled(true);
                    }

                }
                //moreInfo((int) target.getValueAt(row,0));
                 */
            }
        });

        payScrollPane = new JScrollPane(payTable);
        viewPanel.add(payScrollPane);

        initEditPanel();
    }

    private void success(String message) {
        JOptionPane.showMessageDialog(null, message);
        table_update();
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public JComponent getRootComponent() {
        return payPanel;
    }

    private void initEditPanel() {
        GridBagConstraints gbc;
        editPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = .5;
        gbc.fill = GridBagConstraints.BOTH;
        payPanel.add(editPanel, gbc);

        JButton newProdLine = new JButton("Update Shipping Info");
        newProdLine.setSize(25,25);
/*
        addTracking = new JButton("Add Tracking");
        addTracking.setEnabled(false);

        orderDelivered = new JButton("Delivered");
        orderDelivered.setEnabled(false);

        addTracking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var name = javax.swing.JOptionPane.showInputDialog("Tracking Number:");
                if(!sda.shipIt(shippingID, name, orderId)) {
                    error("ERROR! Tracking information not updated");
                } else {
                    success("Success! Tracking number added to order");
                }
            }
        });

        orderDelivered.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //var name = javax.swing.JOptionPane.showInputDialog("Tracking Number:");
                if(!sda.delivered(shippingID, orderId)) {
                    error("ERROR! Delivery Status Updated");
                } else {
                    success("Success! Delivery Status Updated");
                }
            }
        });

        editPanel.add(addTracking);
        editPanel.add(orderDelivered);

 */
    }

}
