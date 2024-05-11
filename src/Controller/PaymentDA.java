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
                pay.setStatus(rs.getString("payment_status"));
                payList.add(pay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPaymentStatus(int id) {
        String status = null;
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT Payment_Status FROM payment_method where payment_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                status = rs.getString("payment_status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean updatePaymentStatus(String status, int paymentID) {
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("UPDATE payment_method set payment_status = ? where payment_id = ?");

            ps.setString(1, status);
            ps.setInt(2, paymentID);
            ps.executeUpdate();

            //TODO: This should update order from NEW to PAID. That should allow SHIPPING TO BE tracked after the fact
            /*
            ps = DatabaseTools.GetConnection().prepareStatement("UPDATE order set Status = ? where order_id = ?");
            ps.setString(1, "SHIPPED");
            ps.setInt(2, orderID);
            ps.executeUpdate();
             */
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
