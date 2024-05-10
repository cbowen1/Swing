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
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.text.ParseException;
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
    Component parent;
    Customer cust;
    JPanel existCustPanel;
    JPanel dropDownPanel;
    JPanel newCustPanel;
    DefaultTableModel dtm;
    JTable detailTable;
    ArrayList<OrderDetails> detailList;
    private Boolean showDropdown = true;
    boolean newCustomer = false;

    public OrderUI(Component parent) {
        this.parent = parent;
        init();

        orderDA = new OrderDA();
        custDA = new CustomerDA();
        prodDA = new ProductDA();
        table_update();
    }

    protected void table_update() {
        ArrayList<Order> orderList = orderDA.getOrderList();

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

        orderTable.setAutoCreateRowSorter(true);

        orderScrollPane = new JScrollPane(orderTable);
        viewPanel.add(orderScrollPane);

        initEditPanel();
    }

    private void moreInfo(Integer orderID) {
        if(infoFrame != null) {
            infoFrame.dispose();
        }

        infoFrame = new JFrame("Order Details");
        infoFrame.setMinimumSize(new Dimension(800,600));
        infoFrame.setResizable(false);
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
        /*
        existCustPanel.add(new JLabel("First: "));
        existCustPanel.add(new JLabel("Last: "));
        existCustPanel.add(new JLabel("Address: "));
        existCustPanel.add(custFirst);
        existCustPanel.add(custLast);
        existCustPanel.add(custAddress);
*/
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
        detailTable = new JTable();
        JScrollPane detailPane = new JScrollPane(detailTable);

        dataPanel.add(txtOrderId);
        dataPanel.add(txtOrderStatus);
        dataPanel.add(txtOrderDate);
        GridBagConstraints tableGBC = new GridBagConstraints();
        tableGBC.gridx = 0;
        tableGBC.gridy = 0;
        tablePanel.add(detailPane, tableGBC);

        dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 1) {
                    return true;
                }
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
        dtm.addColumn("Item Name");
        dtm.addColumn("Qty");
        dtm.addColumn("Price");

        if(orderID == null) {
            /*
            toggleDropdown();
            if(showDropdown) {

            } else {
                custCombobox = null;
            }
*/
            existCustPanel.removeAll();
            CustomerDA customerDA = new CustomerDA();
            ArrayList<Customer> custList = customerDA.getCustomerList();
            custCombobox = new JComboBox<>(custList.toArray(new Customer[0]));
            existCustPanel.add(custCombobox);

            txtOrderId.setEnabled(false);
            txtOrderStatus.setEnabled(false);
            txtOrderDate.setEnabled(false);
            txtOrderId.setText("NEW");
            txtOrderStatus.setText("NEW");
            LocalDate today = LocalDate.now();
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            txtOrderDate.setText(today.format(f));
            JButton addItem = new JButton("ADD Item");
            JButton deleteItem = new JButton("REMOVE Item");
            addItem.addActionListener(e -> showInventory());
            deleteItem.addActionListener(e -> removeOrderDetail());

            deleteItem.setVisible(false);
            tableGBC.gridy++;
            tablePanel.add(addItem, tableGBC);
            tableGBC.gridy++;
            tablePanel.add(deleteItem, tableGBC);
            detailList = new ArrayList<>();
        } else {
            custCombobox = null;
            //Existing order, grab information
            Order ord = orderDA.getOrder(orderID);
            ord.setOrderDetails(orderDA.getOrderDetails(orderID));
            //Grab better customer and product information from corresponding tables
            cust = new Customer();
            cust = custDA.getCustomer(ord.getCustomerID());
            ord.setCustomer(cust);

            drawDetailTable(ord.getOrderDetails());
            //Add order details to table

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
        JButton delete = new JButton("Cancel Order");
        JButton editOrderDetails = new JButton("Edit Order Details");
        editOrderDetails.setVisible(false);

        if(custCombobox == null) {
            save.addActionListener(e -> updateOrder(txtOrderId.getText(),cust,txtOrderDate.getText()));
        } else {
            //save.setVisible(false);
            save.addActionListener(e -> updateOrder(txtOrderId.getText(),(Customer) custCombobox.getSelectedItem(),txtOrderDate.getText()));
        }
        delete.addActionListener(e -> deleteOrder(txtOrderId.getText()));
        editOrderDetails.addActionListener(e -> editOrderItems(txtOrderId.getText()));

        buttonPanel.add(save);
        buttonPanel.add(editOrderDetails);
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
        detailPane.setMinimumSize(new Dimension(450,430));
        infoFrame.pack();
        infoFrame.setLocationRelativeTo(parent);
        infoFrame.setVisible(true);
        infoFrame.addPropertyChangeListener(e -> propChange());
    }

    public void propChange() {
        infoFrame.pack();
        infoFrame.repaint();

    }

    private void removeOrderDetail() {
        System.out.println("Get currently selected ITEM_DETAIL, then delete. NOT IMPLEMENTED");
    }

    private void showInventory() {
        prodDA = new ProductDA();
        ArrayList<Product> prodList = prodDA.getAvailableOrderList();
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
                    tm2.removeRow(row);
                    if(tm2.getRowCount() == 0) {
                        itemList.dispose();
                    }
                }
            }
        });

        itemListTable.setAutoCreateRowSorter(true);
        scrollPane = new JScrollPane(itemListTable);
        itemList.add(scrollPane);

        itemList.pack();
        itemList.setLocationRelativeTo(null);
        itemList.setVisible(true);
    }

    private void addItemToOrder(int id) {
        //TODO: Add item to order details table
        //Product prod = prodDA.getProduct(id);
        OrderDetails o = new OrderDetails();
        o.setProductID(id);
        o.setQty(1);
        detailList.add(o);
        drawDetailTable(detailList);
        //prodList.add(prod);
    }

    private void drawDetailTable(ArrayList<OrderDetails> ord){
        detailTable.setModel(dtm);
        //Clear the dtm and re-draw
        dtm.setRowCount(0);
        for(OrderDetails o: ord) {
            Vector<Object> row = new Vector<>(3);
            row.add(0,prodDA.getProductName(o.getProductID()));
            row.add(1,o.getQty());
            row.add(2,(o.getQty() * prodDA.getProductPrice(o.getProductID())));
            dtm.addRow(row);
        }
    }

    private void toggleDropdown() {
        if(showDropdown == null) {
            existCustPanel.removeAll();
            //Disable customer fields and show dropdown
            showDropdown = true;
        } else if(showDropdown == true) {
            //existCustPanel.removeAll();
            //Hide dropdown and show new customer
            //existCustPanel.add(newCustPanel);

            return;
        } else {
            //Show the dropdown
        }
    }

    private void success(String message) {
        JOptionPane.showMessageDialog(this.getRootComponent(), message);
        infoFrame.dispose();
        table_update();
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(this.getRootComponent(), message);
    }

    private void editOrderItems(String id) {

    }

    private void deleteOrder(String id) {
        if(!orderDA.removeOrder(Integer.valueOf(id))) {
            JOptionPane.showMessageDialog(this.getRootComponent(), "ERROR! Order not cancelled");
        } else {
            success("Success! Order successfully cancelled");
        }
    }

    private void updateOrder(String id, Customer cust, String date) {
        if (detailTable.isEditing())
            detailTable.getCellEditor().stopCellEditing();
        if(id.equals("NEW")) {
            //Save the new order and order details
            Order o = new Order();
            o.setCustomerID(cust.getCustomerID());
            o.setStatus("NEW");
            Date dt = null;
            try {
                dt = new Date(String.valueOf(new SimpleDateFormat("yyyy-MM-dd").parse(date)));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            o.setOrder_date(dt);
            ArrayList<OrderDetails> updatedList = new ArrayList<>();
            DefaultTableModel m = (DefaultTableModel) detailTable.getModel();
            for (int i = 0; i < m.getRowCount(); i++) {
                String qty = "1";
                Object obj = m.getValueAt(i,1);

                try{
                    qty = obj.toString();
                } catch (Exception e) {
                    System.out.println("ERROR....why");
                    //Ignore, default to 1
                }
                updatedList.add(new OrderDetails(id,detailList.get(i).getProductID(),Integer.valueOf(qty)));
            }

            if(!orderDA.addOrder(o,updatedList)) {
                error("ERROR! Order not created");
            } else {
                success("Success! New order created");
            }

        } else {
            System.out.println("Update order not implemented");
        }
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
