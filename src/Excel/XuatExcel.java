/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;
import BUS.*;
import DTO.*;
import GUI.GUIOrderProduct;
import java.awt.FileDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author Admin
 */
public class XuatExcel {

    FileDialog fd = new FileDialog(new JFrame(), "Xuất excel", FileDialog.SAVE);

    public FileDialog getFd() {
        return fd;
    }
    
    private String getFile() {
        fd.setFile("untitled.xls");
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if (url.equals("nullnull")) {
            return null;
        }
        return url;
    }
        // Xuất file Excel Món Ăn   
    public void xuatDatetime(ArrayList<OrderProductDTO> array) {
        fd.setTitle("Xuất báo cáo ra excel"); //Set tên
        String url = getFile(); //Kiểm tra getfile()
        if (url == null) {
            return;
        }

        FileOutputStream outFile = null;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();//Đọc và ghi file định dạng Microsoft Excel (XLS – định dạng hỗ trợ của Excel 2003) - Workbook: file
            HSSFSheet sheet = workbook.createSheet("Report");//Tạo bảng tính Món Ăn

            int rownum = 0; //cột thứ 0
            Row row = sheet.createRow(rownum); //tạo biến row (hàng) trong sheet
//createCell(int cột, CellType."kiểu dữ liệu") row.createCell (hàng row, tạo cột) 
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            row.createCell(0, CellType.STRING).setCellValue("BÁO CÁO RFID");
            rownum++;
            row = sheet.createRow(rownum);
            
//Tạo vòng lập for chạy hết giá trị của list
            for (OrderProductDTO DTO : array) {
                for(int i=0;i<GUI.GUIOrderProduct.header.length;i++)
                row.createCell(i, CellType.STRING).setCellValue(GUIOrderProduct.header[i]); //Hàng 0. cột 0- kiểu String, giá trị ID
                rownum++; //rownum (tăng lên giá trị, lúc nãy là 0 giờ là 1 - hàng thứ 1)
                row = sheet.createRow(rownum);
                row.createCell(0, CellType.STRING).setCellValue(DTO.getOrder_id()); 
                row.createCell(1, CellType.STRING).setCellValue(DTO.getOrder_date().toString());
                row.createCell(2, CellType.STRING).setCellValue(DTO.getStatus());
                 rownum++; //rownum (tăng lên giá trị, lúc nãy là 0 giờ là 1 - hàng thứ 1)
                    row = sheet.createRow(rownum);
                    row.createCell(1, CellType.STRING).setCellValue("order_detail_id"); //Hàng 0. cột 0- kiểu String, giá trị ID
                    row.createCell(2, CellType.STRING).setCellValue("product_id");
                    row.createCell(3, CellType.STRING).setCellValue("order_quantity");
                for(OrderDetailDTO DTO2:OrderDetailBUS.ds)
                {
                    if(DTO2.getOrder_id().equals(DTO.getOrder_id())){
                        rownum++; //rownum (tăng lên giá trị, lúc nãy là 0 giờ là 1 - hàng thứ 1)
                        row = sheet.createRow(rownum);
                        row.createCell(1, CellType.STRING).setCellValue(DTO2.getOrder_detail_id()); //Hàng 0. cột 0- kiểu String, giá trị ID
                        row.createCell(2, CellType.STRING).setCellValue(DTO2.getProduct_id());
                        row.createCell(3, CellType.NUMERIC).setCellValue(DTO2.getProduct_quantity());
                    }
                }
            }
//Tạo vòng lập từ 0 tới rownum để set lại kích thước cột cho gọn
            for (int i = 0; i < rownum; i++) {
                sheet.autoSizeColumn(i);
            }
//Tiến hành tạo file và ghi file
            File file = new File(url);
            file.getParentFile().mkdirs();
            outFile = new FileOutputStream(file);
            workbook.write(outFile);

            JOptionPane.showMessageDialog(null, "Ghi file thành công: " + file.getAbsolutePath());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(XuatExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XuatExcel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (outFile != null) {
                    outFile.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(XuatExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void xuatProductOrder(OrderProductDTO DTO) {
        fd.setTitle("Xuất báo cáo ra excel"); //Set tên
        String url = getFile(); //Kiểm tra getfile()
        if (url == null) {
            return;
        }

        FileOutputStream outFile = null;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();//Đọc và ghi file định dạng Microsoft Excel (XLS – định dạng hỗ trợ của Excel 2003) - Workbook: file
            HSSFSheet sheet = workbook.createSheet("Report");//Tạo bảng tính Món Ăn

            int rownum = 0; //cột thứ 0
            Row row = sheet.createRow(rownum); //tạo biến row (hàng) trong sheet
//createCell(int cột, CellType."kiểu dữ liệu") row.createCell (hàng row, tạo cột) 
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            row.createCell(0, CellType.STRING).setCellValue("BÁO CÁO RFID THEO PRODUCT_ID");
            rownum++;
            row = sheet.createRow(rownum);
            
//Tạo vòng lập for chạy hết giá trị của list
            
                for(int i=0;i<GUI.GUIOrderProduct.header.length;i++)
                row.createCell(i, CellType.STRING).setCellValue(GUIOrderProduct.header[i]); //Hàng 0. cột 0- kiểu String, giá trị ID
                rownum++; //rownum (tăng lên giá trị, lúc nãy là 0 giờ là 1 - hàng thứ 1)
                row = sheet.createRow(rownum);
                row.createCell(0, CellType.STRING).setCellValue(DTO.getOrder_id()); 
                row.createCell(1, CellType.STRING).setCellValue(DTO.getOrder_date().toString());
                row.createCell(2, CellType.STRING).setCellValue(DTO.getStatus());
                 rownum++; //rownum (tăng lên giá trị, lúc nãy là 0 giờ là 1 - hàng thứ 1)
                    row = sheet.createRow(rownum);
                    row.createCell(1, CellType.STRING).setCellValue("order_detail_id"); //Hàng 0. cột 0- kiểu String, giá trị ID
                    row.createCell(2, CellType.STRING).setCellValue("product_id");
                    row.createCell(3, CellType.STRING).setCellValue("order_quantity");
                for(OrderDetailDTO DTO2:OrderDetailBUS.ds)
                {
                    if(DTO2.getOrder_id().equals(DTO.getOrder_id())){
                        rownum++; //rownum (tăng lên giá trị, lúc nãy là 0 giờ là 1 - hàng thứ 1)
                        row = sheet.createRow(rownum);
                        row.createCell(1, CellType.STRING).setCellValue(DTO2.getOrder_detail_id()); //Hàng 0. cột 0- kiểu String, giá trị ID
                        row.createCell(2, CellType.STRING).setCellValue(DTO2.getProduct_id());
                        row.createCell(3, CellType.NUMERIC).setCellValue(DTO2.getProduct_quantity());
                    }
                }
            
//Tạo vòng lập từ 0 tới rownum để set lại kích thước cột cho gọn
            for (int i = 0; i < rownum; i++) {
                sheet.autoSizeColumn(i);
            }
//Tiến hành tạo file và ghi file
            File file = new File(url);
            file.getParentFile().mkdirs();
            outFile = new FileOutputStream(file);
            workbook.write(outFile);

            JOptionPane.showMessageDialog(null, "Ghi file thành công: " + file.getAbsolutePath());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(XuatExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XuatExcel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (outFile != null) {
                    outFile.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(XuatExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    private String getTime() {
        return new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
    }
}
    


