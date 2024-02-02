package View;

import Controller.Controller;
import Model.Model;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class View {
    public View() {
        JTextField searchTermTextField = new JTextField(26);
        JButton filterButton = new JButton("Filter");
        JButton resetButton = new JButton("Reset");
        JTable table = new JTable();

        table.setAutoCreateRowSorter(true);

        Model model = new Model();
        table.setModel(model);

        Controller controller = new Controller(searchTermTextField, model);
        filterButton.addActionListener(controller);

        resetButton.addActionListener(controller);

        JPanel ctrlPane = new JPanel();
        ctrlPane.add(searchTermTextField);
        ctrlPane.add(filterButton);
        ctrlPane.add(resetButton);

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(700,182));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Market Movers",
                TitledBorder.CENTER, TitledBorder.TOP));
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, ctrlPane, tableScrollPane);
        splitPane.setDividerLocation(35);
        splitPane.setEnabled(false);

        JFrame frame = new JFrame("Swing MVC Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(splitPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
