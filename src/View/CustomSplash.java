package View;

import Database.DatabaseTools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class CustomSplash {
    final BufferedImage logo = ImageIO.read(getClass().getResource("/images/logo_cropped.png"));
    JFrame frame;
    public CustomSplash() throws IOException {
        DatabaseTools db = new DatabaseTools();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width/2;
        int height = screenSize.height/2;


        JButton contButton = new JButton("OK");
        contButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrimaryFrame(frame.getGlassPane());
                hide();

            }
        });

        JLabel picLabel = new JLabel(new ImageIcon(logo.getScaledInstance(logo.getWidth() /2, logo.getHeight()/2, Image.SCALE_DEFAULT)));
        contButton.setSize(25,25);
        frame = new JFrame("Eclipse Breaks LLC.");
        //Set screen size to be 50% of the screen size

        frame.setPreferredSize(new Dimension(width, height));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(contButton, BorderLayout.CENTER);
        frame.add(picLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().setBackground(Color.WHITE);

        frame.setMinimumSize(new Dimension(1000,500));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void hide() {
        this.frame.setVisible(false);
    }
}
