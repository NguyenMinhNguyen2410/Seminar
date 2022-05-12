package DAO;

import DTO.OrderProductDTO;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderProductDAO {
    ConnectDB connection = new ConnectDB();

    public OrderProductDAO() {

    }

    public ArrayList docDB() throws SQLException, Exception { //cần ghi lại khi qua class khác

        ArrayList<OrderProductDTO> ds = new ArrayList<>();
        try {
            String qry = "SELECT * FROM order_product";
            ResultSet result = connection.excuteQuery(qry);
            if (result != null) {
                while (result.next()) {
                    OrderProductDTO OrderProduct = new OrderProductDTO();
                    OrderProduct.setOrder_id(result.getString("order_id"));
                    OrderProduct.setOrder_date(result.getDate("order_date").toLocalDate());
                    OrderProduct.setStatus(result.getInt("status"));
                    ds.add(OrderProduct);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không đọc được dữ liệu bảng order_product");
        }
        return ds;
    }

    public void them(OrderProductDTO OrderProduct) { //cần ghi lại khi qua class khác
        try {
            String qry = "INSERT INTO order_product values (";
            qry = qry + "'" + OrderProduct.getOrder_id() + "'";
            qry = qry + "," + "'" + OrderProduct.getOrder_date() + "'";
            qry = qry + "," + "'" + OrderProduct.getStatus() + "'";
            qry = qry + ")";
            connection.getStatement();
            connection.ExecuteUpdate(qry);
            System.out.println(qry);
            connection.closeConnect();
        } catch (Exception ex) {
        }
    }

}
