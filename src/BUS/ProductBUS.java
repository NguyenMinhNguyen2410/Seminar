package BUS;

import DAO.ProductDAO;
import DTO.ProductDTO;

import javax.swing.*;
import java.util.ArrayList;

public class ProductBUS {
    public static ArrayList<ProductDTO> ds;
    public static ProductDAO data = new ProductDAO();
    public ProductBUS() {

    }

    public static void docDB() throws Exception //cần ghi lại khi qua class khác
    {
        try{
            if (ds == null) {
                ds = new ArrayList<>();
            }
            ds = data.docDB(); // đọc dữ liệu từ database
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không đọc được dữ liệu bảng product ở BUS");
        }
    }
    public void them(ProductDTO DTO){
        data.them(DTO);
    }
    public void sua(ProductDTO DTO){
        data.sua(DTO);
    }
    public String getNewID(){
        String id=ds.get(ds.size()-1).getProduct_id();
        id=id.substring(0, 4)+(Integer.parseInt(id.substring(4))+1);
        return id;
    }
}
