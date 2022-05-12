package GUI;

import BUS.ProductBUS;
import BUS.TagBUS;
import BUS.Tool;
import DTO.ProductDTO;
import DTO.TagDTO;
import EXT.FormContent;
import EXT.MyTable;
import RFID.ReadTags;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class GUITag extends FormContent {
    //Tạo mảng tiêu đề
    public static String[] header = {"tag_id", "product_id", "tag_gate_in", "tag_date_in", "tag_gate_out", "tag_date_out"};
    //Tạo đối tượng cho BUS
    private final TagBUS BUS = new TagBUS();
    //Combobox để chọn thuộc tính muốn tìm
    private JComboBox cbSearch;
    //Các textfield trong thanh tìm kiếm
    private JTextField searchtext, from_out, to_out, from_in, to_in;
    //Tạo các nút để chọn ngày
    private static DatePicker dp_from_in, dp_to_in,dp_from_out,dp_to_out;
    protected JButton btnBatDau,btnDungLai,btnDeleteScan,btnSelected;
    private MyTable table_tag,table_product;
    JPanel WriteTag;
    public HashMap<String,String> filtertag=new HashMap<>();
    public GUITag() {
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
        TimKiem.setPreferredSize(new Dimension(0, 100));
        add(TimKiem,BorderLayout.CENTER);
        
        //Tạo bảng dữ liệu
        JPanel panel=new JPanel(new BorderLayout());
        Table=Table();
        Table.setPreferredSize(new Dimension(0,300));
        panel.add(Table,BorderLayout.NORTH);
        
        WriteTag=WriteTag();
        panel.add(WriteTag,BorderLayout.SOUTH);
        
        panel.setPreferredSize(new Dimension(0,750));
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
        cbSearch = new JComboBox(new String[]{"tag_id", "product_id", "tag_gate_in", "tag_gate_out"});
        cbSearch.setBounds(5, 20, 100, 40);
        lbTen.add(cbSearch);

        searchtext =new JTextField();
        searchtext.setBorder(new TitledBorder("tag_id"));
        searchtext.setBounds(105, 20, 150, 40);
        lbTen.add(searchtext);
        addDocumentListener(searchtext);
        cbSearch.addActionListener((ActionEvent e) -> {
            searchtext.setBorder(BorderFactory.createTitledBorder(cbSearch.getSelectedItem().toString()));
            searchtext.requestFocus();

        });
        lbTen.setBounds(x, 0, 265, 70);
        TimKiem.add(lbTen);
        //Tạo cách tìm kiếm bằng ngày
        JLabel NgayLap=new JLabel("");
        NgayLap.setBorder(new TitledBorder("tag_date_in"));

        from_in =new JTextField();
        from_in.setBorder(new TitledBorder("from"));
        from_in.setBounds(5, 20, 100, 40);
        from_in.setEditable(false);
        NgayLap.add(from_in);
        addDocumentListener(from_in);

        // khoang ngay
        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dp_from_in = new DatePicker(pickerSettings);
        dp_from_in.setDateToToday();
        // calendar icon
        JButton calendar= dp_from_in.getComponentToggleCalendarButton();
        calendar.setText("");
        calendar.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/calendar-30.png")));
        calendar.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        dp_from_in.setBounds(105, 25, 35,30);
        NgayLap.add(dp_from_in);
        dp_from_in.addDateChangeListener((dce) -> {
            from_in.setText(dp_from_in.getDateStringOrEmptyString());
        });

        to_in =new JTextField();
        to_in.setBorder(new TitledBorder("to"));
        to_in.setBounds(140, 20, 100, 40);
        to_in.setEditable(false);
        NgayLap.add(to_in);
        addDocumentListener(to_in);

        // khoang ngay
        DatePickerSettings pickerSettings2 = new DatePickerSettings();
        pickerSettings2.setVisibleDateTextField(false);
        dp_to_in = new DatePicker(pickerSettings2);
        dp_to_in.setDateToToday();
        // calendar icon
        JButton calendar2= dp_to_in.getComponentToggleCalendarButton();
        calendar2.setText("");
        calendar2.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/calendar-30.png")));
        calendar2.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        dp_to_in.setBounds(240, 25, 35,30);
        NgayLap.add(dp_to_in);
        dp_to_in.addDateChangeListener((dce) -> {
            to_in.setText(dp_to_in.getDateStringOrEmptyString());
        });
        NgayLap.setBounds(x+=270, 0, 280, 70);
        TimKiem.add(NgayLap);

        //Tạo cách tìm kiếm bằng tổng tiền
        JLabel tag_date_out=new JLabel("");
        tag_date_out.setBorder(new TitledBorder("tag_date_out"));

        from_out =new JTextField();
        from_out.setBorder(new TitledBorder("from"));
        from_out.setBounds(5, 20, 100, 40);
        from_out.setEditable(false);
        tag_date_out.add(from_out);
        addDocumentListener(from_out);

        // khoang ngay
        DatePickerSettings pickerSettings3 = new DatePickerSettings();
        pickerSettings3.setVisibleDateTextField(false);
        dp_from_out = new DatePicker(pickerSettings3);
        dp_from_out.setDateToToday();
        // calendar icon
        JButton calendar3= dp_from_out.getComponentToggleCalendarButton();
        calendar3.setText("");
        calendar3.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/calendar-30.png")));
        calendar3.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        dp_from_out.setBounds(105, 25, 35,30);
        tag_date_out.add(dp_from_out);
        dp_from_out.addDateChangeListener((dce) -> {
            from_out.setText(dp_from_out.getDateStringOrEmptyString());
        });

        to_out =new JTextField();
        to_out.setBorder(new TitledBorder("to"));
        to_out.setBounds(140, 20, 100, 40);
        to_out.setEditable(false);
        tag_date_out.add(to_out);
        addDocumentListener(to_out);

        // khoang ngay
        DatePickerSettings pickerSettings4 = new DatePickerSettings();
        pickerSettings4.setVisibleDateTextField(false);
        dp_to_out = new DatePicker(pickerSettings4);
        dp_to_out.setDateToToday();
        // calendar icon
        JButton calendar4= dp_to_out.getComponentToggleCalendarButton();
        calendar4.setText("");
        calendar4.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/calendar-30.png")));
        calendar4.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        dp_to_out.setBounds(240, 25, 35,30);
        tag_date_out.add(dp_to_out);
        dp_to_out.addDateChangeListener((dce) -> {
            to_out.setText(dp_to_out.getDateStringOrEmptyString());
        });

        tag_date_out.setBounds(x+=285, 0, 280, 70);
        TimKiem.add(tag_date_out);

        return TimKiem;
    }

    @Override
    protected void docDB() {
            try {
                BUS.docDB();
            } catch (Exception ex) {
                Logger.getLogger(GUITag.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        table.setHeaders(header);
        //Chỉ hiện những món ăn ở trạng thái hiện , trạng thái ẩn là khi xóa
        for (TagDTO DTO : TagBUS.ds) {
                table.addRow(DTO);

        }
    }


    // để cho hàm tìm kiếm
    private void addDocumentListener(JTextField tx) {
        // https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
        tx.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {//Đẩy dữ liệu đi và nhận lại danh sách đúng với field tìm kiếm
                setDataToTable(Tool.searchTag(searchtext.getText(),cbSearch.getSelectedItem().toString(),from_in.getText(),to_in.getText(),from_out.getText(),to_out.getText()), table);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {//Đẩy dữ liệu đi và nhận lại danh sách đúng với field tìm kiếm
                setDataToTable(Tool.searchTag(searchtext.getText(),cbSearch.getSelectedItem().toString(),from_in.getText(),to_in.getText(),from_out.getText(),to_out.getText()), table);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {//Đẩy dữ liệu đi và nhận lại danh sách đúng với field tìm kiếm
                setDataToTable(Tool.searchTag(searchtext.getText(),cbSearch.getSelectedItem().toString(),from_in.getText(),to_in.getText(),from_out.getText(),to_out.getText()), table);
            }
        });
    }

    //Set dữ liệu lên lại table
    private void setDataToTable(ArrayList<TagDTO> TagDTO, MyTable myTable) {
        myTable.clear();
        for (TagDTO Tag : TagDTO) {
            table.addRow(Tag);
        }
    }
    private JPanel WriteTag(){
        JPanel panel=new JPanel(new BorderLayout());
        
        JPanel north=new JPanel(new GridLayout(1, 4));
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
        north.add(btnBatDau);
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
        north.add(btnDungLai);
        
        btnDeleteScan=new JButton("Xóa quét");
        btnDeleteScan.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/thanhtoan-30.png")));
        btnDeleteScan.setFont(new Font("Segoe UI", 0, 14));
        btnDeleteScan.setBackground(Color.decode("#90CAF9"));
        btnDeleteScan.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                DeleteScan_click();
            }
        });
        btnDeleteScan.setPreferredSize(new Dimension(0,50));
        north.add(btnDeleteScan);
        
        btnSelected=new JButton("Chọn");
        btnSelected.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/thanhtoan-30.png")));
        btnSelected.setFont(new Font("Segoe UI", 0, 14));
        btnSelected.setBackground(Color.decode("#90CAF9"));
        btnSelected.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                Selected_click();
            }
        });
        btnSelected.setPreferredSize(new Dimension(0,50));
        north.add(btnSelected);
        
        north.setPreferredSize(new Dimension(0, 50));
        panel.add(north,BorderLayout.NORTH);
        
        JPanel center=new JPanel(new GridLayout(1, 2));
        
        table_tag =new MyTable();
        table_tag.setHeaders(new String[]{"tag_id"});
        table_tag.pane.setPreferredSize(new Dimension(GUIMenu.width_content*49/100, 400));
        center.add(table_tag);
        
        table_product =new MyTable();
        table_product.setHeaders(new String[]{"product_id", "product_name", "product_quantity", "product_detail"});
        for(ProductDTO DTO : ProductBUS.ds)
        {
                table_product.addRow(DTO);
        }
        table_product.pane.setPreferredSize(new Dimension(GUIMenu.width_content*49/100, 400));
        center.add(table_product);
        center.setPreferredSize(new Dimension(0, 400));
        
        panel.add(center,BorderLayout.CENTER);
        
        return panel;
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
                            
                            filtertag.put(t.getEpc().toString(),"1");
                            System.err.println(t.getEpc().toString());
                            table_tag.clear();
                            for(String key:filtertag.keySet())
                            table_tag.addRow(new String[]{key});
                            System.out.println(filtertag);
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
    private void DeleteScan_click(){
        table_tag.clear();
    }
    private void Selected_click(){
        TagDTO DTO=new TagDTO();
        if(table_tag.tb.getSelectedRow()!=-1)
            DTO.setTag_id(table_tag.tbModel.getValueAt(table_tag.tb.getSelectedRow(), 0).toString());
        else
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hàng ở bảng Tag");
        if(table_product.tb.getSelectedRow()!=-1)
            DTO.setProduct_id(table_product.tbModel.getValueAt(table_product.tb.getSelectedRow(), 0).toString());
        else
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hàng ở bảng Product");
        for(TagDTO DTO2:TagBUS.ds)
            if(DTO2.getTag_id().equals(DTO.getTag_id()))
                JOptionPane.showMessageDialog(null, "Tồn tại trong bảng Tag");
        DTO.setTag_date_in(LocalDate.now());
        DTO.setTag_gate_in("gate1");
        BUS.them(DTO);
        for(ProductDTO DTO2:ProductBUS.ds)
        {
            if(DTO2.getProduct_id().equals(DTO.getProduct_id()))
            {
                ProductBUS BUS2=new ProductBUS();
                DTO2.setProduct_quantity(DTO2.getProduct_quantity()+1);
                BUS2.sua(DTO2);
            }
        }
        filtertag.remove(DTO.getTag_id());
        table_tag.tbModel.removeRow(table_tag.tb.getSelectedRow());
    }
    @Override
    protected void LamMoi_click(){
        super.LamMoi_click();
        searchtext.setText("");
        from_in.setText("");
        to_in.setText("");
        from_out.setText("");
        to_out.setText("");
    }
    @Override
    protected void Them(){
    
    }
    @Override
    protected void Sua(){
    
    }
    @Override
    protected void NhapExcel(){
    
    }
    @Override
    protected void ChiTiet(){
    
    }
}




