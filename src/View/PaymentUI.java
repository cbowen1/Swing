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
    JButton markPaid;
    Component parent;
    int paymentID;

    public PaymentUI(Component parent) {
        this.parent = parent;
        markPaid = new JButton("Mark Paid");
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
            @Override
            public Class<?> getColumnClass(int col) {
                Class retVal = Object.class;
                if(getRowCount() > 0) {
                    retVal = getValueAt(0, col).getClass();
                }
                return  retVal;
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

        payTable.setAutoCreateRowSorter(true);
        payTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                String status = (String) target.getValueAt(row, 3);
                paymentID = (int) target.getValueAt(row, 0);
                if(status.equals("NEW")) {
                    markPaid.setEnabled(true);
                } else {
                    markPaid.setEnabled(false);
                }
            }
        });

        payScrollPane = new JScrollPane(payTable);
        viewPanel.add(payScrollPane);

        initEditPanel();
    }

    private void success(String message) {
        JOptionPane.showMessageDialog(this.getRootComponent(), message);
        table_update();
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(this.getRootComponent(), message);
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

        markPaid.setSize(25,25);
        editPanel.add(markPaid);

        markPaid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(parent, "Do you want to mark this order as PAID", "Order Update",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION) {
                    if(!pda.updatePaymentStatus("PAID", paymentID)) {
                        error("ERROR! Payment Information Not Updated");
                    } else {
                        success("Success! Order Marked as PAID");
                    }
                }
            }
        });

        /*
        addTracking = new JButton("Add Tracking");
        addTracking.setEnabled(false);

        orderDelivered = new JButton("Delivered");
        orderDelivered.setEnabled(false);



        orderDelivered.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //var name = javax.swing.JOptionPane.showInputDialog("Tracking Number:");

            }
        });

        editPanel.add(addTracking);
        editPanel.add(orderDelivered);

 */
    }

}
