package View;

import Controller.ShipDA;
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

    int shippingID, orderId;

    JButton addTracking, orderDelivered;

    public ShippingUI() {
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
        };
        tm.addColumn("ID");
        tm.addColumn("Order Number");
        tm.addColumn("Shipping Date");
        tm.addColumn("Expected Date");
        tm.addColumn("Tracking");
        tm.addColumn("Delivered");

        shipTable.setModel(tm);
        for(ShippingData pl: plList) {
            Vector<Object> rowObj = new Vector<>(5);
            rowObj.add(0, pl.getId());
            rowObj.add(1, pl.getOderId());
            rowObj.add(2, pl.getShipDate());
            rowObj.add(3, pl.getEstDate());
            rowObj.add(4, pl.getTracking());
            rowObj.add(5, pl.getDelivered().toString());
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

        shipTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                String tracking = (String) target.getValueAt(row,4);
                shippingID = (int) target.getValueAt(row, 0);
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
    }
    private void success(String message) {
        JOptionPane.showMessageDialog(null, message);
        table_update();
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(null, message);
    }


    public JComponent getRootComponent() {
        return shipPanel;
    }
}
