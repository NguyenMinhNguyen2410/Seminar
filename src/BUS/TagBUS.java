package BUS;

import DAO.TagDAO;
import DTO.TagDTO;

import javax.swing.*;
import java.util.ArrayList;

public class TagBUS {
    public static ArrayList<TagDTO> ds;
    public static TagDAO data = new TagDAO();
    public TagBUS() {

    }

    public static void docDB() throws Exception //cần ghi lại khi qua class khác
    {
        try{
            if (ds == null) {
                ds = new ArrayList<>();
            }
            ds = data.docDB(); // đọc dữ liệu từ database
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không đọc được dữ liệu bảng tag ở BUS "+e);
        }
    }
    public void them(TagDTO DTO){
        data.them(DTO);//ghi vào database
        if (ds != null)
            ds.add(DTO);//cập nhật arraylist
    }
    public void updateTagDate(TagDTO DTO){
        data.updateTagDate(DTO);
    }
}
