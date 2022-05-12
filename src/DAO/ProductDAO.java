package DAO;

import DTO.ProductDTO;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {
    ConnectDB connection = new ConnectDB();

    public ProductDAO() {

    }

    public ArrayList docDB() throws SQLException, Exception { //cần ghi lại khi qua class khác

        ArrayList<ProductDTO> ds = new ArrayList<>();
        try {
            String qry = "SELECT * FROM product";
            ResultSet result = connection.excuteQuery(qry);
            if (result != null) {
                while (result.next()) {
                    ProductDTO Product = new ProductDTO();
                    Product.setProduct_id(result.getString("product_id"));
                    Product.setProduct_name(result.getString("product_name"));
                    Product.setProduct_detail(result.getString("product_detail"));
                    Product.setProduct_quantity(result.getInt("product_quantity"));
                    ds.add(Product);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không đọc được dữ liệu bảng product");
        }
        return ds;
    }
    public void them(ProductDTO DTO){
        try {
            String qry = "INSERT INTO product values (";
            qry = qry + "'" + DTO.getProduct_id()+ "'";
            qry = qry + "," + "'" + DTO.getProduct_name()+ "'";
            qry = qry + "," + "'" + DTO.getProduct_quantity()+ "'";
            qry = qry + "," + "'" + DTO.getProduct_detail()+ "'";
            qry = qry + ")";
            connection.getStatement();
            connection.ExecuteUpdate(qry);
            System.out.println(qry);
            connection.closeConnect();
        } catch (Exception ex) {
        }
    }
    public void sua(ProductDTO DTO){
        try {
            String qry = "Update product Set ";
            qry = qry + "product_name=" + "'" + DTO.getProduct_name() + "'";
            qry = qry + ",product_quantity=" + "'" + DTO.getProduct_quantity() + "'";
            qry = qry + ",product_detail=" + "'" + DTO.getProduct_detail() + "'";
            qry = qry + " " + "where product_id='" + DTO.getProduct_id() + "'";
            connection.getStatement();
            connection.ExecuteUpdate(qry);
            System.out.println(qry);
            connection.closeConnect();

        } catch (Exception ex) {
        }
    }
}
