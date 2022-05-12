package GUI;

import BUS.ProductBUS;
import BUS.Tool;
import DTO.ProductDTO;
import EXT.FormContent;
import EXT.MyTable;
import Excel.DocExcel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.plaf.FontUIResource;

public class GUIProduct extends FormContent {
    //Tạo mảng tiêu đề
    public static String[] header = {"product_id", "product_name", "product_quantity", "product_detail"};
    //Các textfield trong thanh tìm kiếm
    public JTextField searchtext, Tu_SoLuong, Den_SoLuong;
    //Combobox để chọn thuộc tính muốn tìm
    private JComboBox cbSearch;
    //Tạo đối tượng cho BUS
    private final ProductBUS BUS = new ProductBUS();
    //Phần nhãn bên trong Dialog thêm sửa 
    private JLabel label_Product[] = new JLabel[header.length];
    //Phần textfield của thêm
    private JTextField txt_Product_Them[] = new JTextField[header.length];
    //Phần textfield của sửa
    private JTextField txt_Product_Sua[] = new JTextField[header.length];
    public GUIProduct() {
        super();
    }
    @Override
    protected void initcomponent(){
        setLayout(new BorderLayout());
        //Tạo thanh công cục ở phía trên
        CongCu=CongCu();
        CongCu.setPreferredSize(new Dimension(0,70));
        add(CongCu,BorderLayout.NORTH);
        //Tạo thanh tìm kiếm
        TimKiem=TimKiem();
        add(TimKiem,BorderLayout.CENTER);
        //Tạo bảng dữ liệu
        JPanel panel=new JPanel(new BorderLayout());
        Table=Table();
        Table.setPreferredSize(new Dimension(0,300));
        panel.add(Table,BorderLayout.NORTH);

        panel.setPreferredSize(new Dimension(0,600));
        add(panel,BorderLayout.SOUTH);
        setVisible(true);
        setSize(GUIMenu.width_content, 870);
    }
    //Viết đè hàm tìm kiếm
    @Override
    protected JPanel TimKiem() {
        JPanel TimKiem=new JPanel(null);

        JLabel lbTen=new JLabel("");
        lbTen.setBorder(new TitledBorder("Search"));
        int x=200;
        cbSearch = new JComboBox(new String[]{"product_id", "product_name", "product_detail"});
        cbSearch.setBounds(5, 20, 100, 40);
        lbTen.add(cbSearch);

        searchtext =new JTextField();
        searchtext.setBorder(new TitledBorder("product_id"));
        searchtext.setBounds(105, 20, 150, 40);
        lbTen.add(searchtext);
        addDocumentListener(searchtext);

        cbSearch.addActionListener((ActionEvent e) -> {
            searchtext.setBorder(BorderFactory.createTitledBorder(cbSearch.getSelectedItem().toString()));
            searchtext.requestFocus();

        });
        lbTen.setBounds(x, 0, 265, 70);
        TimKiem.add(lbTen);

        //Tìm kiếm theo số lượng
        JLabel SoLuong = new JLabel("");
        SoLuong.setBorder(new TitledBorder("Số lượng"));

        Tu_SoLuong = new JTextField();
        Tu_SoLuong.setBorder(new TitledBorder("Từ"));
        Tu_SoLuong.setBounds(5, 20, 100, 40);
        SoLuong.add(Tu_SoLuong);
        //Gọi sự kiện khi tìm kiếm với Tu_SoLuong
        addDocumentListener(Tu_SoLuong);

        Den_SoLuong = new JTextField();
        Den_SoLuong.setBorder(new TitledBorder("Đến"));
        Den_SoLuong.setBounds(105, 20, 100, 40);
        SoLuong.add(Den_SoLuong);
        //Gọi sự kiện khi tìm kiếm với Den_SoLuong
        addDocumentListener(Den_SoLuong);

        SoLuong.setBounds(580, 0, 215, 70);
        TimKiem.add(SoLuong);

        return TimKiem;
    }

    @Override
    protected void docDB() {
            try {
                BUS.docDB();
            } catch (Exception ex) {
                Logger.getLogger(GUIProduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        table.setHeaders(header);
        //Chỉ hiện những món ăn ở trạng thái hiện , trạng thái ẩn là khi xóa
        for (ProductDTO DTO : ProductBUS.ds) {
            table.addRow(DTO);

        }
    }


    // để cho hàm tìm kiếm
    private void addDocumentListener(JTextField tx) {
        // https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
        tx.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                txtSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                txtSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                txtSearchOnChange();
            }
        });
    }

    //Hàm tìm kiếm mỗi khi thao tác trên field
    public void txtSearchOnChange() {
        int soLuong1 = -1, soLuong2 = -1;
        //Ràng buộc
        try {
            soLuong1 = Integer.parseInt(Tu_SoLuong.getText());
            Tu_SoLuong.setForeground(Color.black);
        } catch (NumberFormatException e) {
            Tu_SoLuong.setForeground(Color.red);
        }

        try {
            soLuong2 = Integer.parseInt(Den_SoLuong.getText());
            Den_SoLuong.setForeground(Color.black);
        } catch (NumberFormatException e) {
            Den_SoLuong.setForeground(Color.red);
        }
        //Đẩy dữ liệu đi và nhận lại danh sách đúng với field tìm kiếm
        setDataToTable(Tool.searchProduct(searchtext.getText(),cbSearch.getSelectedItem().toString(), soLuong1, soLuong2), table); //chưa sửa xong hỏi Nguyên cái Textfield
    }

    //Set dữ liệu lên lại table
    private void setDataToTable(ArrayList<ProductDTO> ProductDTO, MyTable myTable) {
        myTable.clear();
        for (ProductDTO Product : ProductDTO) {
            table.addRow(Product);
        }
    }
    //Hàm tạo Dialog thêm món ăn
    @Override
    protected void Them_click() {
        super.Them_click();
        //Tạo tiêu đề và set hình thức
        JLabel Title = new JLabel("Thêm Product");
        Title.setFont(new Font("Time New Roman", Font.BOLD, 21));
        Title.setForeground(Color.decode("#FF4081"));
        Title.setBounds(150, 0, 200, 40);
        Them_Frame.add(Title);
        int y = 50;
        //Tạo tự động các label và textfield
        for (int i = 0; i < header.length; i++) {
            label_Product[i] = new JLabel(header[i]);
            label_Product[i].setBounds(100, y, 100, 30);
            Them_Frame.add(label_Product[i]);
            txt_Product_Them[i] = new JTextField();
            txt_Product_Them[i].setBounds(200, y, 150, 30);
            //Tạo nút để lấy tên ảnh 
            y += 40;
            Them_Frame.add(txt_Product_Them[i]);
        }
        String maProduct =BUS.getNewID(); //lấy mã tự động
        txt_Product_Them[0].setEditable(false);
        txt_Product_Them[0].setText(maProduct); //set mã lên textfield
    }
    @Override
    protected void luuThem_Frame(){
        cohieu=1;
                int a = op.showConfirmDialog(Them_Frame, "Bạn chắc chứ ?", "", JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION) {
                    //Kiểm tra luồng dữ liệu 
                    if (checkTextThem(txt_Product_Them[1].getText(),
                            txt_Product_Them[2].getText(),
                            txt_Product_Them[3].getText())) {
                        //Tạo đối tượng và truyền dữ liệu trực tiếp vào 
                        ProductDTO DTO = new ProductDTO();
                        DTO.setProduct_id(txt_Product_Them[0].getText());
                        DTO.setProduct_name(txt_Product_Them[1].getText());
                        DTO.setProduct_quantity(Integer.valueOf(txt_Product_Them[2].getText()));
                        DTO.setProduct_detail(txt_Product_Them[3].getText());
                        //Gọi hàm thêm ở bus và truyền đối tượng DTO vào
                        BUS.them(DTO);
                        //Thêm vào table
                        table.addRow(DTO);
                        //clear textfield trong Them_frame
                        clearThem_Frame();       
                        LamMoi_click();
                        //Lệnh này để đóng dialog
                        Them_Frame.dispose();
                    }
                }
    }
    public boolean checkTextThem(String TenProduct,String SoLuong, String ChiTiet) {
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Segoe UI", 0, 20)));
        if (TenProduct.equals("")
                || ChiTiet.equals("")
                || SoLuong.equals("")) {
            op.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
        } else if (!Tool.isSpecialChar(Tool.removeAccent(TenProduct))) {
            op.showMessageDialog(null, "Tên Product không được chứa ký tự đặc biệt");
            txt_Product_Them[1].requestFocus();
        } else if (!Tool.isLength50(TenProduct)) {
            op.showMessageDialog(null, "Tên Product không được quá 50 ký tự");
            txt_Product_Them[1].requestFocus();
        }  else if (!Tool.isNumber(SoLuong)) {
            op.showMessageDialog(null, "Số lượng phải là số nguyên dương");
            txt_Product_Them[2].requestFocus();
        } else if (!Tool.isSpecialChar(SoLuong)) {
            op.showMessageDialog(null, "Số lượng không được chứa ký tự đặc biệt");
            txt_Product_Them[2].requestFocus();
        }  else {
            return true;

        }
        return false;
    }
    
    @Override
    protected void clearThem_Frame(){
        for (int i = 0; i < GUIProduct.header.length; i++) {
            txt_Product_Them[i].setText("");
        }
    }
    //Hàm tạo Dialog sửa món ăn
    @Override
    protected void Sua_click() {
        super.Sua_click();
        int row = table.tb.getSelectedRow();
        if (row == -1) {
            op.showMessageDialog(null, "Vui lòng chọn 1 hàng để sửa");
        } 
        else 
        {
            //Tạo tiêu đề
            JLabel Title = new JLabel("Sửa Product");
            Title.setFont(new Font("Time New Roman", Font.BOLD, 21));
            Title.setForeground(Color.decode("#FF4081"));
            Title.setBounds(150, 0, 200, 40);
            Sua_Frame.add(Title);
            int y = 50;
            //Tạo tự động các lable và textfield
            for (int i = 0; i < header.length; i++) {
                label_Product[i] = new JLabel(header[i]);
                label_Product[i].setBounds(100, y, 100, 30);
                Sua_Frame.add(label_Product[i]);
                txt_Product_Sua[i] = new JTextField();
                txt_Product_Sua[i].setBounds(200, y, 150, 30);
                y += 40;
                txt_Product_Sua[i].setText(table.tbModel.getValueAt(row, i).toString());
                Sua_Frame.add(txt_Product_Sua[i]);
            }    
            txt_Product_Sua[0].setEditable(false);
            //Set tự động giá trị các field
            
            Sua_Frame.setVisible(true);
         }
    }
    @Override
    protected void luuSua_Frame(){
        //Tắt cờ hiệu đi 
                cohieu = 1;
                int a = op.showConfirmDialog(Sua_Frame, "Bạn chắc chứ ?", "", JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION) {
                    //Chạy hàm checkText để ràng buộc dữ liệu 
                    if (checkTextSua(txt_Product_Sua[1].getText(),
                            txt_Product_Sua[2].getText(),
                            txt_Product_Sua[3].getText())) {
                        //Chạy hàm để lưu lại việc sửa dữ liệu
                            //Sửa dữ liệu trong database và arraylist trên bus
                            //Tạo đối tượng monAnDTO và truyền dữ liệu trực tiếp thông qua constructor
                            ProductDTO DTO = new ProductDTO();
                            DTO.setProduct_id(txt_Product_Sua[0].getText());
                            DTO.setProduct_name(txt_Product_Sua[1].getText());
                            DTO.setProduct_quantity(Integer.valueOf(txt_Product_Sua[2].getText()));
                            DTO.setProduct_detail(txt_Product_Sua[3].getText());
                            //Truyền dữ liệu và vị trí vào bus
                            BUS.sua(DTO);
                            LamMoi_click();
                        //Lệnh này để tắt dialog
                        Sua_Frame.dispose();
                    }
                }
                else
                    cohieu=0;
    }
    public boolean checkTextSua(String TenProduct,String SoLuong, String ChiTiet) {
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Segoe UI", 0, 20)));
        if (TenProduct.equals("")
                || ChiTiet.equals("")
                || SoLuong.equals("")) {
            op.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
        } else if (!Tool.isSpecialChar(Tool.removeAccent(TenProduct))) {
            op.showMessageDialog(null, "Tên Product không được chứa ký tự đặc biệt");
            txt_Product_Sua[1].requestFocus();
        } else if (!Tool.isLength50(TenProduct)) {
            op.showMessageDialog(null, "Tên Product không được quá 50 ký tự");
            txt_Product_Sua[1].requestFocus();
        }  else if (!Tool.isNumber(SoLuong)) {
            op.showMessageDialog(null, "Số lượng phải là số nguyên dương");
            txt_Product_Sua[2].requestFocus();
        } else if (!Tool.isSpecialChar(SoLuong)) {
            op.showMessageDialog(null, "Số lượng không được chứa ký tự đặc biệt");
            txt_Product_Sua[2].requestFocus();
        }  else {
            return true;

        }
        return false;
    }
    //Tạo hàm này dùng để clear các textfield trong Sua_Frame
    @Override
    protected void clearSua_Frame(){
        for (int i = 0; i < GUIProduct.header.length; i++) {
            txt_Product_Sua[i].setText("");
        }
    }
    

    @Override
    protected void ChiTiet(){

    }
    @Override
    protected void LamMoi_click(){
        super.LamMoi_click();
        searchtext.setText("");
        Tu_SoLuong.setText("");
        Den_SoLuong.setText("");
    }
    @Override
    protected void NhapExcel_click(){
        DocExcel read=new DocExcel();
        read.docFileExcelProduct();
        LamMoi_click();
    }
    
}