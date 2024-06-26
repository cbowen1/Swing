package View;

import Controller.OrderDA;
import Controller.PaymentDA;
import Controller.ShipDA;
import Model.Order;
import Model.Product_Line;
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

public class ShippingUI {
    private JPanel shipPanel;
    private JPanel viewPanel;
    private JPanel editPanel;
    private JScrollPane shipScrollPane;
    private JTable shipTable;
    ShipDA sda;
    Component parent;
    int shippingID, orderId;
    Order ord;
    OrderDA oda;
    JButton addTracking, orderDelivered;
    int paymentID;

    public ShippingUI(Component parent) {
        this.parent = parent;
        ord = new Order();
        oda = new OrderDA();
        init();
        sda = new ShipDA();
        table_update();
    }

    protected void table_update() {
        ArrayList<ShippingData> plList = sda.getShipList();

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
        tm.addColumn("Order Number");
        tm.addColumn("Shipping Date");
        tm.addColumn("Expected Date");
        tm.addColumn("Tracking");
        tm.addColumn("Weight");
        tm.addColumn("Delivered");
        tm.addColumn("Cancelled");

        shipTable.setModel(tm);
        for(ShippingData pl: plList) {
            Vector<Object> rowObj = new Vector<>(7);
            rowObj.add(0, pl.getId());
            rowObj.add(1, pl.getOderId());
            rowObj.add(2, pl.getShipDate());
            rowObj.add(3, pl.getEstDate());
            rowObj.add(4, pl.getTracking());
            rowObj.add(5, pl.getWeight());
            rowObj.add(6, pl.getDelivered().toString());
            rowObj.add(7, pl.getCancelled().toString());
            tm.addRow(rowObj);
        }
    }

    private void init() {
        shipPanel = new JPanel();
        shipPanel.setLayout(new GridBagLayout());
        viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout(0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        shipPanel.add(viewPanel, gbc);
        shipTable = new JTable();
        shipTable.setRowHeight(30);
        shipTable.setAutoCreateRowSorter(true);
        shipTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                String tracking = (String) target.getValueAt(row,4);
                shippingID = (int) target.getValueAt(row, 0);
                orderId = (int) target.getValueAt(row, 1);
                ord = oda.getOrder(orderId);
                paymentID = ord.getPaymentID();
                if(tracking == null) {
                    addTracking.setEnabled(true);
                    orderDelivered.setEnabled(false);
                } else {
                    addTracking.setEnabled(false);
                    if((String) target.getValueAt(row,6) == "false") {
                        orderDelivered.setEnabled(true);
                    }

                }
                //moreInfo((int) target.getValueAt(row,0));
            }
        });

        shipScrollPane = new JScrollPane(shipTable);
        viewPanel.add(shipScrollPane);

        initEditPanel();
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
        shipPanel.add(editPanel, gbc);

        JButton newProdLine = new JButton("Update Shipping Info");
        newProdLine.setSize(25,25);
        //newProdLine.addActionListener(e -> moreInfo(null));

        addTracking = new JButton("Add Tracking");
        addTracking.setEnabled(false);

        orderDelivered = new JButton("Delivered");
        orderDelivered.setEnabled(false);

        addTracking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentDA pda = new PaymentDA();
                String status = pda.getPaymentStatus(paymentID);
                String tracking;
                if(!status.equals("PAID")) {
                    JOptionPane.showMessageDialog(parent, "ERROR! Order has not been paid yet.\nCannot add shipping until payment is complete");
                    return;
                }
                JTextField tf = new JTextField();
                int okCxl = JOptionPane.showConfirmDialog(parent, tf, "Tracking Number:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (okCxl == JOptionPane.OK_OPTION) {
                    tracking = new String(tf.getText());
                } else {
                    return;
                }
                if(!sda.shipIt(shippingID, tracking, orderId)) {
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
    }
    private void success(String message) {
        JOptionPane.showMessageDialog(this.getRootComponent(), message);
        table_update();
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(this.getRootComponent(), message);
    }


    public JComponent getRootComponent() {
        return shipPanel;
    }
}
