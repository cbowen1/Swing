package View;

import Database.DatabaseTools;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PrimaryFrame extends JFrame implements ActionListener {
    BufferedImage icon;
    JFrame frame;
    JSplitPane splitPane;
    JPanel topPanel, leftPanel, rightPanel;
    JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

    CustomerUI customerPanel;
    OrderUI orderPanel;
    ProductUI productPanel;
    InventoryUI inventoryPanel;
    ProductLineUI plPanel;
    SupplierUI supplierPanel;
    ShippingUI shippingPanel;
    PaymentUI payPanel;
    JLabel topLabel;
    Component parent;


    public PrimaryFrame(Component c) {
        try {
            icon = ImageIO.read(getClass().getResource("/images/eclipse_icon.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parent = c;
        frame = new JFrame("Eclipse Collectibles LLC");

        DatabaseTools dt = new DatabaseTools();
        if(dt.GetConnection() == null) {
            javax.swing.JOptionPane.showMessageDialog(frame.getGlassPane(),"ERROR! Could not connect to database");
            System.exit(0);
        }
        customerPanel = new CustomerUI(frame.getGlassPane());
        orderPanel = new OrderUI(frame.getGlassPane());
        productPanel = new ProductUI(frame.getGlassPane());
        inventoryPanel = new InventoryUI(frame.getGlassPane());
        plPanel = new ProductLineUI(frame.getGlassPane());
        supplierPanel = new SupplierUI(frame.getGlassPane());
        shippingPanel = new ShippingUI(frame.getGlassPane());
        payPanel = new PaymentUI(frame.getGlassPane());
        frame.setIconImage(icon);
        init();

    }

    private void init() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width/2;
        int height = screenSize.height/2;
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        topPanel = new JPanel();
        btn1 = new JButton("Customers");
        btn2 = new JButton("Orders");
        btn3 = new JButton("Supplies");
        btn4 = new JButton("Products");
        btn5 = new JButton("Product Lines");
        btn6 = new JButton("Suppliers");
        btn7 = new JButton("Shipping");
        btn8 = new JButton("Payments");

        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);
        btn6.addActionListener(this);
        btn7.addActionListener(this);
        btn8.addActionListener(this);

        Dimension half = new Dimension((int)(screenSize.getWidth()/1.5),(int) (screenSize.getHeight()/1.5));
        topPanel.setPreferredSize(new Dimension(half.width, (int) half.getHeight()/8));

        topLabel = new JLabel("Please select an option on the left");
        topPanel.add(topLabel);

        leftPanel.setLayout(new GridLayout(8,1));
        leftPanel.add(btn2);
        leftPanel.add(btn4);
        leftPanel.add(btn5);
        leftPanel.add(btn3);
        leftPanel.add(btn1);
        leftPanel.add(btn6);
        leftPanel.add(btn7);
        leftPanel.add(btn8);
        splitPane.setLeftComponent(leftPanel);
        //splitPane.setRightComponent(rightPanel);
        splitPane.setRightComponent(orderPanel.getRootComponent());
        topLabel.setText("Order Information");
        //splitPane.setDividerLocation(150);
        splitPane.repaint();
        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(splitPane.getRightComponent() != null) {
            splitPane.remove(splitPane.getRightComponent());
        }
        if(e.getSource() == btn1) {
            customerPanel.table_update();
            splitPane.setRightComponent(customerPanel.getRootComponent());
            topLabel.setText("Customer Information");
        } else if (e.getSource() == btn2) {
            orderPanel.table_update();
            splitPane.setRightComponent(orderPanel.getRootComponent());
            topLabel.setText("Order Information");
        } else if (e.getSource() == btn3) {
            inventoryPanel.table_update();
            splitPane.setRightComponent(inventoryPanel.getRootComponent());
            topLabel.setText("Inventory Information");
        } else if (e.getSource() == btn4) {
            productPanel.table_update();
            splitPane.setRightComponent(productPanel.getRootComponent());
            topLabel.setText("Product Information");
        } else if (e.getSource() == btn5) {
            plPanel.table_update();
            splitPane.setRightComponent(plPanel.getRootComponent());
            topLabel.setText("Product Lines");
        } else if (e.getSource() == btn6) {
            supplierPanel.table_update();
            splitPane.setRightComponent(supplierPanel.getRootComponent());
            topLabel.setText("Supplier Information");
        } else if (e.getSource() == btn7) {
            shippingPanel.table_update();
            splitPane.setRightComponent(shippingPanel.getRootComponent());
            topLabel.setText("Shipping Information");
        } else if (e.getSource() == btn8) {
            payPanel.table_update();
            splitPane.setRightComponent(payPanel.getRootComponent());
            topLabel.setText("Payment Information");
        }
        revalidate();
    }
}
