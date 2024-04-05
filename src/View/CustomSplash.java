package View;

import Controller.Controller;
import Model.Model;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomSplash {
    public CustomSplash() {

        JButton contButton = new JButton("OK");
        contButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new View();
            }
        });
        contButton.setSize(25,25);
        JFrame frame = new JFrame("Eclipse Breaks LLC.");
        //Set screen size to be 50% of the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width/2;
        int height = screenSize.height/2;
        frame.setPreferredSize(new Dimension(width, height));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(contButton, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
