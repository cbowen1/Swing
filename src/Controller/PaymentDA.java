package Controller;

import Database.DatabaseTools;
import Model.PaymentMethod;
import Model.ShippingData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PaymentDA {
    private ArrayList<PaymentMethod> payList;

    public PaymentDA() {}

    public ArrayList<PaymentMethod> getPayList() {
        populatePayList();
        return payList;
    }

    private void populatePayList() {
        payList = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM payment_method");
            ResultSet rs = ps.executeQuery();

            PaymentMethod pay;
            while(rs.next()) {
                pay = new PaymentMethod();
                pay.setId(rs.getInt("Payment_id"));
                pay.setType(rs.getString("Payment_type"));
                pay.setAmount(rs.getDouble("payment_amount"));
                payList.add(pay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
