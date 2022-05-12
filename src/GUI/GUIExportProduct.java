package GUI;

import BUS.OrderDetailBUS;
import BUS.OrderProductBUS;
import BUS.ProductBUS;
import BUS.TagBUS;
import BUS.Tool;
import DTO.OrderDetailDTO;
import DTO.OrderProductDTO;
import EXT.FormBanNhap;
import EXT.MyTable;
import DTO.ProductDTO;
import DTO.TagDTO;
import RFID.ReadTags;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class GUIExportProduct extends FormBanNhap{
    //Tạo mảng tiêu đề của bảng Product
    private static String array_Product[]={"product_id", "product_name", "product_quantity", "product_detail"};
    //Tạo bảng Product để nhân viên chọn danh sách món và add lên bảng thanh toán
    private MyTable table_product, table_tag,table_export_product,table_order_product;
    //Tạo Panel để show thông tin Product và để chứa thanh tìm kiếm
    private JPanel Show,TimKiem;
    //Tạo các field chứa thông tin Product khi chọn
    private JTextField tx_product_id;
    private JTextField tx_product_name;
    private JTextField tx_product_quantity;
    //Tạo các nút để phục vụ cho việc thuận tiện khi chọn mã khách hàng hay khuyến mãi
    protected JButton Them,Xoa,btnXacNhan,btnBatDau,btnDungLai,btnXoaQuet;
    //Tạo field tìm kiếm Product
    private JTextField search;
    //Tạo ComboBox để chọn tiêu chí tìm kiếm
    private JComboBox cbSearch;
    private ArrayList<ProductDTO> array_export_product=new ArrayList<>();
    public HashMap<String,String> filtertag=new HashMap<>();
    public GUIExportProduct(){
        super();
    }
    @Override
    protected JPanel panelDanhSach(){
        JPanel panel=new JPanel(null);
        //Thanh tìm kiếm Product
        TimKiem=TimKiem();
        TimKiem.setBounds(0,0,GUIMenu.width_content*50/100, 80);
        panel.add(TimKiem);
        //Bảng product
        JPanel Product=Table();
        Product.setBounds(0,85,GUIMenu.width_content*50/100, 300);
        panel.add(Product);
        //Show thông tin Product khi click vào
        Show=Show();
        Show.setBounds(0,390,GUIMenu.width_content*50/100, 120);
        panel.add(Show);

        return panel;
    }
    //Tạo bảng Product
    private JPanel Table(){
        table_product =new MyTable();
        table_product.setHeaders(array_Product);
        for (ProductDTO DTO : ProductBUS.ds) {
                table_product.addRow(DTO);
        }
        table_product.pane.setPreferredSize(new Dimension(GUIMenu.width_content*50/100, 300));
        return table_product;
    }
    //Thanh tìm kiếm Product
    private JPanel TimKiem(){
        JPanel TimKiem=new JPanel(null);

        search=new JTextField();
        search.setBorder(new TitledBorder("product_name"));
        addDocumentListener(search);

        search.setBounds(200, 0, 215, 70);
        TimKiem.add(search);
        return TimKiem;
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
    //xóa ruột của table và đổ lên những kết quả tìm kiếm được
    public void txtSearchOnChange() {
        table_product.clear();
        for(ProductDTO DTO: ProductBUS.ds)
        {
            if(DTO.getProduct_name().toLowerCase().contains(search.getText().toLowerCase()))
                table_product.addRow(DTO);
        }
    }
    //Show thông tin Product
    private JPanel Show(){
        JPanel panel=new JPanel(new FlowLayout());

        tx_product_id =new JTextField();
        tx_product_name =new JTextField();
        tx_product_quantity =new JTextField();

        // border
        tx_product_id.setBorder(BorderFactory.createTitledBorder("product_id"));
        tx_product_name.setBorder(BorderFactory.createTitledBorder("product_name"));
        tx_product_quantity.setBorder(BorderFactory.createTitledBorder("product_quantity"));
        // disable
        tx_product_id.setEditable(false);
        tx_product_name.setEditable(false);
        tx_product_quantity.setEditable(true);
        // font
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        tx_product_id.setFont(f);
        tx_product_name.setFont(f);
        tx_product_quantity.setFont(f);

        tx_product_id.setPreferredSize(new Dimension(150,50));
        tx_product_name.setPreferredSize(new Dimension(150,50));
        tx_product_quantity.setPreferredSize(new Dimension(150,50));
        // Sự kiện khi click vào các row
        table_product.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                String id = String.valueOf(table_product.tbModel.getValueAt(table_product.tb.getSelectedRow(), 0));
                if (id != null) {
                    showInfo(id);
                }
            }
        });

        Them=new JButton("Thêm");
        Them.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/them1-30.png")));
        Them.setFont(new Font("Segoe UI", 0, 14));
        Them.setBackground(Color.decode("#90CAF9"));
        Them.setPreferredSize(new Dimension(150,50));
        //Sự kiện khi bấm nút thêm
        Them.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                Them_click();
            }
        });
        panel.add(tx_product_id);
        panel.add(tx_product_name);
        panel.add(tx_product_quantity);
        panel.add(Them);
        return panel;
    }
    //Hàm hiển thị ảnh và show thông tin
    void showInfo(String id) {
        if (id != null) {
            // show hình
            for (ProductDTO ds : ProductBUS.ds) {
                if (ds.getProduct_id().equals(id)) {
                    // show info
                    tx_product_id.setText(ds.getProduct_id());
                    tx_product_name.setText(ds.getProduct_name());
                    tx_product_quantity.setText("1");
                    return;
                }
            }
        }
    }
    @Override
    //Ghi đè để lấy vị trí và tạo panel thông tin hóa đơn
    protected JPanel panelXuatHang(){
        JPanel panel=new JPanel(new BorderLayout());

        table_export_product =new MyTable();
        table_export_product.setHeaders(new String[]{"product_id", "product_name", "product_quantity", "product_detail"});
        table_export_product.pane.setPreferredSize(new Dimension(GUIMenu.width_content*49/100, 400));
        panel.add(table_export_product,BorderLayout.NORTH);

        //Nút xóa
        Xoa=new JButton("Xóa");
        Xoa.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/delete1-30.png")));
        Xoa.setFont(new Font("Segoe UI", 0, 14));
        Xoa.setBackground(Color.decode("#90CAF9"));

        Xoa.setPreferredSize(new Dimension( GUIMenu.width_content*49/100, 40));
        Xoa.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                Xoa_click();
            }
        });
        panel.add(Xoa,BorderLayout.SOUTH);

        return panel;
    }
    @Override
    //Hàm này tạo bảng chứa các Product order
    protected JPanel panelXacNhan(){
        JPanel panel=new JPanel(new BorderLayout());

        table_tag =new MyTable();
        table_tag.setHeaders(new String[]{"tag_id", "product_id", "tag_gate_in", "tag_date_in", "tag_gate_out", "tag_date_out"});
        table_tag.pane.setPreferredSize(new Dimension(GUIMenu.width_content*49/100, 400));
        panel.add(table_tag,BorderLayout.NORTH);
        
        JPanel button=new JPanel(new GridLayout(1, 2));
        
        //Nút thanh toán
        btnXoaQuet=new JButton("Xóa quét");
        btnXoaQuet.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xoa-30.png")));
        btnXoaQuet.setFont(new Font("Segoe UI", 0, 14));
        btnXoaQuet.setBackground(Color.decode("#90CAF9"));

        btnXoaQuet.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                XoaQuet_click();
            }
        });
        
        //Nút thanh toán
        btnXacNhan=new JButton("Xác nhận");
        btnXacNhan.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/congthuc-30.png")));
        btnXacNhan.setFont(new Font("Segoe UI", 0, 14));
        btnXacNhan.setBackground(Color.decode("#90CAF9"));

        btnXacNhan.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                XacNhan_click();
            }
        });
        button.add(btnXoaQuet);
        button.add(btnXacNhan);
        button.setPreferredSize(new Dimension(0,50));
        
        
        panel.add(button,BorderLayout.SOUTH);

        return panel;
    }
    @Override
    protected JPanel panelOrderProduct(){
        JPanel panel=new JPanel(new BorderLayout());
        JPanel btn=new JPanel(new GridLayout(1, 2));
        table_order_product =new MyTable();
//        table_order_product.setHeaders(new String[]{"order_id", "order_date", "status"});
//        for(OrderProductDTO DTO : OrderProductBUS.ds)
//        {
//                table_order_product.addRow(DTO);
//        }
        table_order_product.pane.setPreferredSize(new Dimension(GUIMenu.width_content*49/100, 400));
        panel.add(table_order_product,BorderLayout.NORTH);

        //Nút thanh toán
        btnBatDau=new JButton("Bắt đầu");
        btnBatDau.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/thanhtoan-30.png")));
        btnBatDau.setFont(new Font("Segoe UI", 0, 14));
        btnBatDau.setBackground(Color.decode("#90CAF9"));

        btnBatDau.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                BatDau_click();
            }
        });
        btnBatDau.setPreferredSize(new Dimension(0,50));
        
        //Nút thanh toán
        btnDungLai=new JButton("Dừng lại");
        btnDungLai.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/thanhtoan-30.png")));
        btnDungLai.setFont(new Font("Segoe UI", 0, 14));
        btnDungLai.setBackground(Color.decode("#90CAF9"));

        btnDungLai.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                DungLai_click();
            }
        });
        btnDungLai.setPreferredSize(new Dimension(0,50));
        
        btn.add(btnBatDau);
        btn.add(btnDungLai);
        panel.add(btn,BorderLayout.SOUTH);
        
        return panel;
    }
    //Hàm này xử lý việc ấn thêm product
    private void Them_click(){
        int i = table_product.tb.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 hàng để thêm");
        }
        else
        {
            int a=0;
            if (Tool.isNumber(tx_product_quantity.getText())&&Integer.parseInt(table_product.tb.getValueAt(i,2).toString())>=Integer.parseInt(tx_product_quantity.getText())) {
                ProductDTO DTO = new ProductDTO();
                DTO.setProduct_id(table_product.tbModel.getValueAt(i, 0).toString());
                DTO.setProduct_name(table_product.tbModel.getValueAt(i, 1).toString());
                DTO.setProduct_quantity(Integer.parseInt(tx_product_quantity.getText()));
                DTO.setProduct_detail(table_product.tbModel.getValueAt(i, 3).toString());
                boolean check_array_export_product=false;
                for(int row=0;row<table_export_product.tb.getRowCount();row++)
                {
                    if(table_export_product.tb.getValueAt(row,0).toString().equals(DTO.getProduct_id())) {
                        int quantity=Integer.parseInt(table_export_product.tb.getValueAt(row,2).toString());
                        if(quantity+DTO.getProduct_quantity()<=Integer.parseInt(table_product.tb.getValueAt(i,2).toString()))
                            table_export_product.tb.setValueAt(quantity+DTO.getProduct_quantity(),row,2);
                        else
                            op.showMessageDialog(null,"Số lượng không đủ, vui lòng điều chỉnh lại");
                        check_array_export_product=true;
                    }
                }
                if(check_array_export_product)
                    return;
                table_export_product.addRow(DTO);
            }
            else
            {
                op.showMessageDialog(null, "Vui lòng nhập lại số lượng");
            }
        }
    }
    private void BatDau_click(){
        try{
            if(ReadTags.reader.isConnected()){
            // Start reading.
                System.out.println("Starting");
                ReadTags.reader.setTagReportListener(new TagReportListener(){
                    @Override
                    public void onTagReported(ImpinjReader reader, TagReport tr) {
                        List<Tag> tags = tr.getTags();
                        for(Tag t:tags)
                        if(!filtertag.containsKey(t.getEpc().toString()))
                        {
                            String key=t.getEpc().toString();
                            boolean isExist=false;
                            for(TagDTO DTO:TagBUS.ds)
                                if(DTO.getTag_id().equals(key))
                                {
                                    table_tag.addRow(DTO);
                                    isExist=true;
                                    break;
                                }
                            if(!isExist){
                                JOptionPane.showMessageDialog(null, "tag "+filtertag.get(key)+" not exist in storage");
                                return;
                            }
                            filtertag.put(t.getEpc().toString(),"1");
                            System.out.println(filtertag);
                            table_tag.clear();
                            for(String key1:filtertag.keySet())
                                table_tag.addRow(new String[]{key1});
                            int quantityTag=0;
                            for(int i=0;i<table_export_product.tbModel.getRowCount();i++)
                                quantityTag+=Integer.parseInt(table_export_product.tbModel.getValueAt(i, 2).toString());
                            table_tag.addRow(new String[]{"","","","","Total Tag ",String.valueOf(quantityTag-table_tag.tbModel.getRowCount())});
                        }
                    }
                });
                ReadTags.reader.start();
            }
        }catch(OctaneSdkException ex){
            System.err.println("Octane SDK exception: " + ex.getMessage());
        }
    }
    private void DungLai_click(){
        try{
        // Stop reading.
        if(ReadTags.reader.isConnected())
        {
            System.out.println("Stopping");
           ReadTags.reader.stop();
        }
        }catch(OctaneSdkException ex){
            System.err.println("Octane SDK exception: " + ex.getMessage());
        }
    }
    //Hàm xử lý khi ấn vào nút xóa nằm ở thanh công cụ
    private void Xoa_click(){
        int i= table_export_product.tb.getSelectedRow();
        if (i == -1) {
            op.showMessageDialog(null, "Vui lòng chọn 1 hàng để xóa");
        }
        else
        {
            int option = op.showConfirmDialog(null, "Bạn chắc chắn xóa?", "", op.YES_NO_OPTION);
            if (option == op.YES_OPTION) {

                table_export_product.tbModel.removeRow(i);
            }
        }
    }
    //Hàm xử lý khi ấn vào nút thanh toán nằm ở thanh công cụ
    private void XacNhan_click(){
        if(Integer.parseInt(table_tag.tbModel.getValueAt(table_tag.tb.getRowCount()-1, 5).toString())==0)
        {
            OrderProductBUS BUS=new OrderProductBUS();
            OrderProductDTO DTO=new OrderProductDTO();
            String Order_id="ORDER"+String.valueOf(Integer.parseInt(BUS.ds.get(BUS.ds.size()-1).getOrder_id().substring(5))+1);
            DTO.setOrder_id(Order_id);
            DTO.setOrder_date(LocalDate.now());
            DTO.setStatus(1);
            BUS.them(DTO);
//            filtertag.remove(table_tag.tbModel.getValueAt(table_tag.tb.getSelectedRow(), 0).toString());
            for(int i=0;i<table_export_product.tbModel.getRowCount();i++)
            {
                OrderDetailBUS BUS2=new OrderDetailBUS();
                OrderDetailDTO DTO2=new OrderDetailDTO();
                DTO2.setOrder_id(Order_id);
                String product_id=table_export_product.tbModel.getValueAt(i, 0).toString();
                DTO2.setProduct_id(product_id);
                int product_quantity=Integer.parseInt(table_export_product.tbModel.getValueAt(i, 2).toString());
                DTO2.setProduct_quantity(product_quantity);
                DTO2.setOrder_detail_id("DETAIL"+String.valueOf(Integer.parseInt(BUS2.ds.get(BUS2.ds.size()-1).getOrder_detail_id().substring(6))+1));
                BUS2.them(DTO2);
                BUS2.reduceQuantityProduct(product_id, product_quantity);
                
            }
            for(int i=0;i<table_tag.tbModel.getRowCount();i++)
            {
                TagDTO DTO2=new TagDTO();
                DTO2.setTag_id(table_tag.tbModel.getValueAt(i, 0).toString());
                DTO2.setTag_date_out(LocalDate.now());
                DTO2.setTag_gate_out("gate3");
                TagBUS BUS2=new TagBUS();
                BUS2.updateTagDate(DTO2);
            }
            table_tag.clear();
            table_export_product.clear();
        }
        else
            JOptionPane.showMessageDialog(null, "Chưa quét đủ tag");
            
    }
    private void XoaQuet_click(){
        table_tag.clear();
    }
    //Hàm khi ấn nút làm mới
    private void LamMoi() {
        table_product.clear();
        for (ProductDTO DTO : ProductBUS.ds) {
                table_product.addRow(DTO);
        }
    }
}
