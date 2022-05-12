package DAO;

import DTO.TagDTO;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TagDAO {
    ConnectDB connection = new ConnectDB();

    public TagDAO() {

    }

    public ArrayList docDB() throws SQLException, Exception { //cần ghi lại khi qua class khác

        ArrayList<TagDTO> ds = new ArrayList<>();
        try {
            String qry = "SELECT * FROM tag";
            ResultSet result = connection.excuteQuery(qry);
            if (result != null) {
                while (result.next()) {
                    TagDTO Tag = new TagDTO();
                    Tag.setTag_id(result.getString("tag_id"));
                    Tag.setProduct_id(result.getString("product_id"));
                    Tag.setTag_gate_in(result.getString("tag_gate_in"));
                    Tag.setTag_gate_out(result.getString("tag_gate_out"));
                    if(result.getDate("tag_date_in")==null)
                        Tag.setTag_date_in(null);
                    else
                        Tag.setTag_date_in(result.getDate("tag_date_in").toLocalDate());
                    if(result.getDate("tag_date_out")==null)
                        Tag.setTag_date_out(null);
                    else
                    Tag.setTag_date_out(result.getDate("tag_date_out").toLocalDate());
                    ds.add(Tag);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không đọc được dữ liệu bảng Tag ");
        }
        return ds;
    }
    public void them(TagDTO DTO) { //cần ghi lại khi qua class khác
        try {
            String qry = "INSERT INTO tag values (";
            qry = qry + "'" + DTO.getTag_id() + "'";
            qry = qry + "," + "'" + DTO.getProduct_id() + "'";
            qry = qry + "," + "null";
            qry = qry + "," + "null";
            qry = qry + "," + "null";
            qry = qry + "," + "null";
            qry = qry + ")";
            connection.getStatement();
            connection.ExecuteUpdate(qry);
            System.out.println(qry);
            connection.closeConnect();
        } catch (Exception ex) {
        }
    }
    public void updateTagDate(TagDTO DTO){
        try {
            String qry = "Update tag Set ";
            qry = qry + "tag_date_out=" + "'" + DTO.getTag_date_out() + "'";
            qry = qry + ",tag_gate_out=" + "'" + DTO.getTag_gate_out() + "'";
            qry = qry + " " + "where tag_id='" + DTO.getTag_id() + "'";
            connection.getStatement();
            connection.ExecuteUpdate(qry);
            System.out.println(qry);
            connection.closeConnect();

        } catch (Exception ex) {
        }
    }
}
