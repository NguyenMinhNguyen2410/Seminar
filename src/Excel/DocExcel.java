/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;

/**
 *
 * @author Piger Streaming
 */
import BUS.ProductBUS;
import DTO.ProductDTO;
import EXT.MyTable;
import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DocExcel {

    FileDialog fd = new FileDialog(new JFrame(), "Đọc excel", FileDialog.LOAD);

    public DocExcel() {

    }

    public FileDialog getFd() {
        return fd;
    }
    
    private String getFile() {
        fd.setFile("*.xls");
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if (url.equals("nullnull")) {
            return null;
        }
        return url;
    }
    
    //Đọc file excel Món ăn
    public void docFileExcelProduct() {
        fd.setTitle("Nhập dữ liệu product từ excel"); //set Tiêu đề
        String url = getFile(); //tạo file
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream); //Tạo workbook excel mới
            HSSFSheet sheet = workbook.getSheetAt(0); // Tạo sheet excel mới
            Iterator<Row> rowIterator = sheet.iterator(); //Set row trong sheet
            Row row1 = rowIterator.next(); //Tạo biến row
//Tạo biến khi trùng, đếm số lần thêm, ghi đè, bỏ qua
            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
//Tạo cell (ô) - giá trị, 
                    String id = cellIterator.next().getStringCellValue(); 
                    String name = cellIterator.next().getStringCellValue();
                    System.err.println(name);
                    int quantity = (int)cellIterator.next().getNumericCellValue();
                    String detail=cellIterator.next().getStringCellValue();
                    ProductBUS BUS = new ProductBUS();
                    ProductDTO old = null;
                    for(ProductDTO DTO:ProductBUS.ds)
                        if(DTO.getProduct_id().equals(id))
                        {
                            old=DTO;
                            break;
                        }
                    if (old != null) {
                        if (!hanhDongKhiTrung.contains("tất cả")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "product_id", "product_name", "product_quantity", "product_detail"});
                            mtb.addRow(new String[]{
                                "Cũ:", old.getProduct_id(),
                                old.getProduct_name(),
                                String.valueOf(old.getProduct_quantity()),
                                old.getProduct_detail()
                            });

                            mtb.addRow(new String[]{
                                "Mới:", id, name,String.valueOf(quantity),detail                            
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi đè")) {
                            ProductDTO DTO=new ProductDTO();
                            DTO.setProduct_id(id);
                            DTO.setProduct_name(name);
                            DTO.setProduct_quantity(quantity);
                            DTO.setProduct_detail(detail);
                            BUS.sua(DTO);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }                      
                        
                    //Khi database trống    
                     
                    } else {          
                        ProductDTO DTO=new ProductDTO();
                            DTO.setProduct_id(id);
                            DTO.setProduct_name(name);
                            DTO.setProduct_quantity(quantity);
                            DTO.setProduct_detail(detail);
                            BUS.them(DTO);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, "
                    + "Thêm " + countThem
                    + "; Ghi đè " + countGhiDe
                    + "; Bỏ qua " + countBoQua
                    + ". Vui lòng làm mới để thấy kết quả");
        } catch (Exception ex) {
            ex.printStackTrace();
           
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu từ file: " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng inputstream: " + ex.getMessage());
            }
        }
    }
    
}




