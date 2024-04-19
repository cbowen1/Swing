package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrimaryFrame extends JFrame implements ActionListener {
    JFrame frame;
    JSplitPane splitPane;
    JPanel topPanel, leftPanel, rightPanel;
    JButton btn1, btn2, btn3;

    CustomerUI customerPanel;

    public PrimaryFrame() {
        customerPanel = new CustomerUI();
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
        btn1 = new JButton("Customer Management");
        btn2 = new JButton("Order Management");
        btn3 = new JButton("Inventory Management");

        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);

        Dimension half = new Dimension((int)(screenSize.getWidth()/1.5),(int) (screenSize.getHeight()/1.5));
        topPanel.setPreferredSize(new Dimension(half.width, (int) half.getHeight()/8));

        leftPanel.setLayout(new GridLayout(3,1));
        leftPanel.add(btn1);
        leftPanel.add(btn2);
        leftPanel.add(btn3);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(150);
        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.pack();
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
            //splitPane.setRightComponent(gui.getPanel());
            //revalidate();
        } else if (e.getSource() == btn3) {
            //inventory invPanel = new inventory();
            //splitPane.setRightComponent(invPanel.getPanel());
        }
        revalidate();
    }
}
