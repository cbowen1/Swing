package View;

import javax.swing.*;
import java.awt.*;

public class PrimaryFrame extends JFrame {

    JFrame frame;
    public PrimaryFrame() {
        init();
        CustomerUI customerUI = new CustomerUI();
        frame.add(customerUI.getRootComponent());
    }

    private void init() {
        frame = new JFrame("Eclipse Collectiables LLC");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width/2;
        int height = screenSize.height/2;
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
