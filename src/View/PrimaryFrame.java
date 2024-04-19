package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrimaryFrame extends JFrame implements ActionListener {
    JFrame frame;
    JSplitPane splitPane;
    JPanel topPanel, leftPanel, rightPanel;
    JButton btn1, btn2, btn3, btn4, btn5, btn6;

    CustomerUI customerPanel;
    OrderUI orderPanel;
    ProductUI productPanel;
    InventoryUI inventoryPanel;
    ProductLineUI plPanel;
    SupplierUI supplierPanel;


    public PrimaryFrame() {
        customerPanel = new CustomerUI();
        orderPanel = new OrderUI();
        productPanel = new ProductUI();
        inventoryPanel = new InventoryUI();
        plPanel = new ProductLineUI();
        supplierPanel = new SupplierUI();

        init();
    }

    private void init() {
        frame = new JFrame("Eclipse Collectibles LLC");
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
        btn4 = new JButton("Inventory");
        btn5 = new JButton("Product Lines");
        btn6 = new JButton("Suppliers");

        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);
        btn6.addActionListener(this);

        Dimension half = new Dimension((int)(screenSize.getWidth()/1.5),(int) (screenSize.getHeight()/1.5));
        topPanel.setPreferredSize(new Dimension(half.width, (int) half.getHeight()/8));

        leftPanel.setLayout(new GridLayout(6,1));
        leftPanel.add(btn1);
        leftPanel.add(btn2);
        leftPanel.add(btn4);
        leftPanel.add(btn3);
        leftPanel.add(btn5);
        leftPanel.add(btn6);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(150);
        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(splitPane.getRightComponent() != null) {
            splitPane.remove(splitPane.getRightComponent());
        }
        if(e.getSource() == btn1) {
            splitPane.setRightComponent(customerPanel.getRootComponent());
        } else if (e.getSource() == btn2) {
            splitPane.setRightComponent(orderPanel.getRootComponent());
        } else if (e.getSource() == btn3) {
            splitPane.setRightComponent(inventoryPanel.getRootComponent());
        } else if (e.getSource() == btn4) {
            splitPane.setRightComponent(productPanel.getRootComponent());
        } else if (e.getSource() == btn5) {
            splitPane.setRightComponent(plPanel.getRootComponent());
        } else if (e.getSource() == btn6) {
            splitPane.setRightComponent(supplierPanel.getRootComponent());
        }
        revalidate();
    }
}
