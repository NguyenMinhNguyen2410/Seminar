package BUS;

import DAO.OrderProductDAO;
import DTO.OrderProductDTO;

import javax.swing.*;
import java.util.ArrayList;

public class OrderProductBUS {
    public static ArrayList<OrderProductDTO> ds;
    public static OrderProductDAO data = new OrderProductDAO();

    public OrderProductBUS() {

    }

    public static void docDB() throws Exception //cần ghi lại khi qua class khác
    {
        try {
            if (ds == null) {
                ds = new ArrayList<>();
            }
            ds = data.docDB(); // đọc dữ liệu từ database
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không đọc được dữ liệu bảng order_product ở BUS");
        }
    }

    public void them(OrderProductDTO OrderProduct) //cần ghi lại khi qua class khác
    {
        data.them(OrderProduct);//ghi vào database
        if (ds != null)
            ds.add(OrderProduct);//cập nhật arraylist
    }
}
