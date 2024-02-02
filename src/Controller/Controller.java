package Controller;

import Resources.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Controller implements ActionListener {
    private JTextField searchTermTextField = new JTextField(26);
    private DefaultTableModel model;

    private Connection conn;

    public Controller(JTextField searchTermTextField, DefaultTableModel model) {
        super();
        this.searchTermTextField = searchTermTextField;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if(action.equals("Reset")) {
            model.setDataVector(Constants.DATA, Constants.TABLE_HEADER);
            searchTermTextField.setText("");
        } else {
            String searchTerm = searchTermTextField.getText();
            if(searchTerm != null && !"".equals(searchTerm)) {
                Object[][] newData = new Object[Constants.DATA.length][];
                int idx = 0;
                for(Object[] o: Constants.DATA) {
                    if("*".equals(searchTerm.trim())){
                        newData[idx++] = o;
                    } else {
                        if(String.valueOf(o[0]).toUpperCase().startsWith(searchTerm.toUpperCase().trim()))
                            newData[idx++] = o;
                    }
                }
                model.setDataVector(newData, Constants.TABLE_HEADER);
            } else {
                JOptionPane.showMessageDialog(null, "Search term is empty", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static Connection createConnection(String databaseName) {
        String dbURL = "jdbc:mysql://localhost:1527/" + databaseName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
