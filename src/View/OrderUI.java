package View;

import Controller.CustomerDA;
import Controller.OrderDA;
import Controller.ProductDA;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class OrderUI {
    private JPanel orderPanel;
    private JPanel viewPanel;
    private JPanel editPanel;
    private JScrollPane orderScrollPane;
    private JTable orderTable;
    JFrame infoFrame;
    OrderDA orderDA;
    CustomerDA custDA;
    ProductDA prodDA;

    JPanel existCustPanel;
    JPanel dropDownPanel;
    JPanel newCustPanel;

    private Boolean showDropdown = null;

    boolean newCustomer = false;

    public OrderUI() {
        init();
        orderDA = new OrderDA();
        custDA = new CustomerDA();
        prodDA = new ProductDA();
        table_update();
    }

    private void table_update() {
        ArrayList<Order> orderList = orderDA.getOrderList();

        DefaultTableModel tm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tm.addColumn("Order ID");
        tm.addColumn("Customer ID");
        tm.addColumn("Payment ID");
        tm.addColumn("Current Status");
        tm.addColumn("Order Date");

        orderTable.setModel(tm);
        for(Order o: orderList) {
            Vector<Object> rowObj = new Vector<>(3);
            rowObj.add(0, o.getOrderID());
            rowObj.add(1, o.getCustomerID());
            rowObj.add(2, o.getPaymentID());
            rowObj.add(3, o.getStatus());
            rowObj.add(4, o.getOrder_date());
            tm.addRow(rowObj);
        }
    }

    private void init() {
        orderPanel = new JPanel();
        orderPanel.setLayout(new GridBagLayout());
        viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout(0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        orderPanel.add(viewPanel, gbc);
        orderTable = new JTable();
        orderTable.setRowHeight(30);

        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    moreInfo((int) target.getValueAt(row,0));
                }
            }
        });

        orderScrollPane = new JScrollPane(orderTable);
        viewPanel.add(orderScrollPane);

        initEditPanel();
    }

    private void moreInfo(Integer orderID) {
        if(infoFrame != null) {
            infoFrame.dispose();
        }

        infoFrame = new JFrame("Order Details");
        infoFrame.setLayout(new GridBagLayout());

        JPanel dataPanel = new JPanel(new GridLayout(2,3));
        JPanel togglePanel = new JPanel(new GridLayout(1,1));
        existCustPanel = new JPanel(new GridLayout(2,3));
        newCustPanel = new JPanel(new GridLayout(4,4));
        dropDownPanel = new JPanel(new GridLayout(1,1));
        JPanel tablePanel = new JPanel(new GridBagLayout());
        JPanel btnPanel = new JPanel(new GridLayout(1,3));

        JLabel orderLabel = new JLabel("Order ID");
        dataPanel.add(orderLabel);
        dataPanel.add(new JLabel("Order Status"));
        dataPanel.add(new JLabel("Order Date"));


        JTextField custFirst = new JTextField();
        JTextField custLast = new JTextField();
        JTextField custAddress = new JTextField();

        JTextField newcustFirst = new JTextField();
        JTextField newcustLast = new JTextField();
        JTextField newcustAddress = new JTextField();
        JTextField newcustEmail = new JTextField();

        JComboBox<Customer> custCombobox;

        JTextField street = new JTextField();
        JTextField city = new JTextField();
        JTextField state = new JTextField();
        JTextField zip = new JTextField();
        newcustFirst.setColumns(10);
        newcustLast.setColumns(10);
        newcustEmail.setColumns(25);

        //Create the existing customer panel
        existCustPanel.add(new JLabel("First: "));
        existCustPanel.add(new JLabel("Last: "));
        existCustPanel.add(new JLabel("Address: "));
        existCustPanel.add(custFirst);
        existCustPanel.add(custLast);
        existCustPanel.add(custAddress);

        //Create the new customer panel
        newCustPanel.add(new JLabel("First: "));
        newCustPanel.add(new JLabel("Last: "));
        newCustPanel.add(new JLabel("E-Mail: "));
        newCustPanel.add(new JLabel());
        newCustPanel.add(newcustFirst);
        newCustPanel.add(newcustLast);
        newCustPanel.add(newcustEmail);
        newCustPanel.add(new JLabel());
        newCustPanel.add(new JLabel("Street: "));
        newCustPanel.add(new JLabel("City: "));
        newCustPanel.add(new JLabel("State: "));
        newCustPanel.add(new JLabel("Zip: "));
        newCustPanel.add(street);
        newCustPanel.add(city);
        newCustPanel.add(state);
        //TODO: Make this only allow 2 characters
        newCustPanel.add(zip);

        JTextField txtOrderId = new JTextField();
        JTextField txtOrderStatus = new JTextField();
        JTextField txtOrderDate = new JTextField();
        JTable detailTable = new JTable();
        JScrollPane detailPane = new JScrollPane(detailTable);

        dataPanel.add(txtOrderId);
        dataPanel.add(txtOrderStatus);
        dataPanel.add(txtOrderDate);
        GridBagConstraints tableGBC = new GridBagConstraints();
        tableGBC.gridx = 0;
        tableGBC.gridy = 0;
        tablePanel.add(detailPane, tableGBC);

        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dtm.addColumn("Item Name");
        dtm.addColumn("Qty");
        dtm.addColumn("Price");

        //TODO: Make this swap between new/existing customer on toggle/button
        if(orderID == null) {
            toggleDropdown();
            if(showDropdown) {
                CustomerDA customerDA = new CustomerDA();
                ArrayList<Customer> custList = customerDA.getCustomerList();
                custCombobox = new JComboBox<>(custList.toArray(new Customer[0]));
                existCustPanel.add(custCombobox);
            }
            //JButton toggleUserCreation = new JButton("Toggle User");
            //togglePanel.add(toggleUserCreation);
            //toggleUserCreation.addActionListener(e -> toggleDropdown());
            //This is a new order
            txtOrderId.setEnabled(false);
            txtOrderStatus.setEnabled(false);
            txtOrderDate.setEnabled(false);
            txtOrderId.setText("NEW");
            txtOrderStatus.setText("NEW");
            LocalDate today = LocalDate.now();
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            txtOrderDate.setText(today.format(f));
            JButton addItem = new JButton("ADD ITEM");
            JButton deleteItem = new JButton("REMOVE ITEM");
            addItem.addActionListener(e -> showInventory());
            tableGBC.gridy++;
            tablePanel.add(addItem, tableGBC);
            tableGBC.gridy++;
            tablePanel.add(deleteItem, tableGBC);
        } else {
            //Existing order, grab information
            Order ord = orderDA.getOrder(orderID);
            ord.setOrderDetails(orderDA.getOrderDetails(orderID));
            //Grab better customer and product information from corresponding tables
            ord.setCustomer(custDA.getCustomer(ord.getCustomerID()));

            //Add order details to table
            detailTable.setModel(dtm);
            for(OrderDetails o: ord.getOrderDetails()) {
                Vector<Object> row = new Vector<>(3);
                row.add(0,prodDA.getProductName(o.getProductID()));
                row.add(1,o.getQty());
                row.add(2,(o.getQty() * prodDA.getProductPrice(o.getProductID())));
                dtm.addRow(row);
            }

            //Set values in UI
            txtOrderId.setText(String.valueOf(ord.getOrderID()));
            txtOrderStatus.setText(ord.getStatus());
            txtOrderDate.setText(ord.getOrder_date().toString());

            custFirst.setText(ord.getCustomer().getCustomerName_first());
            custLast.setText(ord.getCustomer().getCustomerName_last());
            custAddress.setText(ord.getCustomer().getFullAddress());
        }

        JPanel fillerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        JButton save = new JButton("Save");
        JButton delete = new JButton("Delete");

        save.addActionListener(e -> updateOrder(txtOrderId.getText()));
        delete.addActionListener(e -> deleteOrder(txtOrderId.getText()));

        buttonPanel.add(save);
        buttonPanel.add(delete);

        btnPanel.add(buttonPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        infoFrame.add(dataPanel,gbc);
        gbc.gridy++;
        infoFrame.add(togglePanel,gbc);
        gbc.gridy++;
        infoFrame.add(existCustPanel,gbc);

        gbc.gridy++;
        infoFrame.add(new JSeparator(JSeparator.HORIZONTAL),gbc);
        gbc.gridy++;
        infoFrame.add(tablePanel,gbc);
        gbc.gridy++;
        infoFrame.add(btnPanel,gbc);

        infoFrame.pack();
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }

    private void showInventory() {
        ProductDA productDA = new ProductDA();
        ArrayList<Product> prodList = productDA.getOrderList();

        JFrame itemList = new JFrame("Current Inventory");
        JScrollPane scrollPane;
        JTable itemListTable = new JTable();
        itemListTable.setRowHeight(30);

        DefaultTableModel tm2 = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tm2.addColumn("Product ID");
        tm2.addColumn("Line ID");
        tm2.addColumn("Product Name");
        tm2.addColumn("Unit Price");
        tm2.addColumn("Qty");

        itemListTable.setModel(tm2);
        for(Product o: prodList) {
            Vector<Object> rowObj = new Vector<>(5);
            rowObj.add(0, o.getId());
            rowObj.add(1, o.getProductLineId());
            rowObj.add(2, o.getName());
            rowObj.add(3, o.getUnitPrice());
            rowObj.add(4, o.getQty());
            tm2.addRow(rowObj);
        }


        itemListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    addItemToOrder((int)target.getValueAt(row,0));
                }
            }
        });
        scrollPane = new JScrollPane(itemListTable);
        itemList.add(scrollPane);

        itemList.pack();
        infoFrame.setLocationRelativeTo(null);
        itemList.setVisible(true);
    }

    private void addItemToOrder(int id) {
        //TODO: Add item to order details table
    }

    private void toggleDropdown() {
        if(showDropdown == null) {
            existCustPanel.removeAll();
            //Disable customer fields and show dropdown
            showDropdown = true;
        } else if(showDropdown == true) {
            existCustPanel.removeAll();
            //Hide dropdown and show new customer
            existCustPanel.add(newCustPanel);
            return;
        } else {
            //Show the dropdown
            System.out.println("hide new cust, show dropdown");
        }
    }

    private void success(String message) {
        JOptionPane.showMessageDialog(null, message);
        infoFrame.dispose();
        table_update();
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void deleteOrder(String id) {
        System.out.println("Delete order not implemented");
    }

    private void updateOrder(String id) {
        System.out.println("Update order not implemented");
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
        orderPanel.add(editPanel, gbc);

        JButton newSupplier  = new JButton("New Order");
        newSupplier.setSize(25,25);
        newSupplier.addActionListener(e -> moreInfo(null));
        editPanel.add(newSupplier);
    }

    public JComponent getRootComponent() {
        return orderPanel;
    }

}
