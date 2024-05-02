import Controller.CustomerDA;
import View.*;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    FlatLightLaf.setup();
                    UIManager.setLookAndFeel(new FlatDarculaLaf());
                    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    createAndShowGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void createAndShowGUI() throws Exception{
        new CustomSplash();
        //new CustomerUI();
        //CustomerDA da = new CustomerDA();

    }
}