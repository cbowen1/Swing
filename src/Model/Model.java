package Model;

import Constants.*;

import javax.swing.table.DefaultTableModel;

public class Model extends DefaultTableModel {
    public Model() {
        super(Constants.DATA, Constants.TABLE_HEADER);
    }

}
