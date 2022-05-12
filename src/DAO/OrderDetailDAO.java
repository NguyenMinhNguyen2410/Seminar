package DAO;

import DTO.OrderDetailDTO;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAO {
    ConnectDB connection = new ConnectDB();

    public OrderDetailDAO() {

    }

    public ArrayList docDB() throws SQLException, Exception { //cần ghi lại khi qua class khác

        ArrayList<OrderDetailDTO> ds = new ArrayList<>();
        try {
            String qry = "SELECT * FROM order_detail";
            ResultSet result = connection.excuteQuery(qry);
            if (result != null) {
                while (result.next()) {
                    OrderDetailDTO OrderDetail = new OrderDetailDTO();
                    OrderDetail.setOrder_detail_id(result.getString("order_detail_id"));
                    OrderDetail.setOrder_id(result.getString("order_id"));
                    OrderDetail.setProduct_id(result.getString("product_id"));
                    OrderDetail.setProduct_quantity(result.getInt("product_quantity"));
                    ds.add(OrderDetail);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không đọc được dữ liệu bảng order_detail");
        }
        return ds;
    }

    public void them(OrderDetailDTO OrderDetail) { //cần ghi lại khi qua class khác
        try {
            String qry = "INSERT INTO order_detail values (";
            qry = qry + "'" + OrderDetail.getOrder_detail_id() + "'";
            qry = qry + "," + "'" + OrderDetail.getOrder_id() + "'";
            qry = qry + "," + "'" + OrderDetail.getProduct_id() + "'";
            qry = qry + "," + "'" + OrderDetail.getProduct_quantity() + "'";
            qry = qry + ")";
            connection.getStatement();
            connection.ExecuteUpdate(qry);
            System.out.println(qry);
            connection.closeConnect();
        } catch (Exception ex) {
        }
    }



}
