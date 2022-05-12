package BUS;

import DAO.OrderDetailDAO;
import DTO.OrderDetailDTO;
import DTO.ProductDTO;

import javax.swing.*;
import java.util.ArrayList;

public class OrderDetailBUS {
    public static ArrayList<OrderDetailDTO> ds;
    public static OrderDetailDAO data = new OrderDetailDAO();
    public OrderDetailBUS() {

    }

    public static void docDB() throws Exception //cần ghi lại khi qua class khác
    {
        try{
            if (ds == null) {
                ds = new ArrayList<>();
            }
            ds = data.docDB(); // đọc dữ liệu từ database
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không đọc được dữ liệu bảng order_detail ở BUS");
        }
    }

    public void them(OrderDetailDTO OrderDetail) //cần ghi lại khi qua class khác
    {
        data.them(OrderDetail);//ghi vào database
        if(ds!=null)
            ds.add(OrderDetail);//cập nhật arraylist
    }
    public void reduceQuantityProduct(String ID,int quantity){
            for(ProductDTO DTO:ProductBUS.ds)
            {
                if(DTO.getProduct_id().equals(ID))
                {
                    DTO.setProduct_quantity(DTO.getProduct_quantity()-quantity);
                    ProductBUS BUS=new ProductBUS();
                    BUS.sua(DTO);
                }
            }
    }
}
