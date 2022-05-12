package GUI;

import BUS.OrderDetailBUS;
import BUS.OrderProductBUS;
import DTO.OrderDetailDTO;
import DTO.OrderProductDTO;
import EXT.FormChon;
import EXT.FormContent;
import EXT.MyTable;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import Excel.XuatExcel;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUIOrderProduct extends FormContent {
    //Tạo mảng tiêu đề
    public static String[] header = {"order_id", "order_date", "status"};
    //Các textfield trong thanh tìm kiếm
    public JTextField id, from_date,to_date,textexport;
    //Tạo các nút để chọn ngày
    private static DatePicker dp_from_date, dp_to_date,dp_export;
    private JLabel ExportExcel;
    private JComboBox cbExport;
    private JButton btn_ProductID;
    //Tạo đối tượng cho BUS
    private final OrderProductBUS BUS = new OrderProductBUS();
    public GUIOrderProduct() {
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
        table.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                String id = String.valueOf(table.tbModel.getValueAt(table.tb.getSelectedRow(), 0));
                if (id != null&&cbExport.getSelectedItem().toString().equals("Product Order")) {
                    textexport.setText(id);
                }
            }
        });
        panel.setPreferredSize(new Dimension(0,600));
        add(panel,BorderLayout.SOUTH);
        setVisible(true);
        setSize(GUIMenu.width_content, 870);
    }
    //Viết đè hàm tìm kiếm
    @Override
    protected JPanel TimKiem() {
        JPanel TimKiem = new JPanel(null);
        JLabel lbTen = new JLabel("");
        lbTen.setBorder(new TitledBorder("Tìm kiếm"));
        //Tìm kiếm theo tên
        id = new JTextField();
        id.setBorder(new TitledBorder("ID"));
        id.setBounds(5, 20, 200, 40);
        lbTen.add(id);
        //Gọi sự kiện khi tìm kiếm với Ten
        addDocumentListener(id);
        lbTen.setBounds(300, 0, 215, 70);
        TimKiem.add(lbTen);

        //Tạo cách tìm kiếm bằng ngày
        JLabel NgayLap=new JLabel("");
        NgayLap.setBorder(new TitledBorder("tag_date_in"));

        from_date =new JTextField();
        from_date.setBorder(new TitledBorder("from"));
        from_date.setBounds(5, 20, 100, 40);
        from_date.setEditable(false);
        NgayLap.add(from_date);
        addDocumentListener(from_date);

        // khoang ngay
        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dp_from_date = new DatePicker(pickerSettings);
        dp_from_date.setDateToToday();
        // calendar icon
        JButton calendar= dp_from_date.getComponentToggleCalendarButton();
        calendar.setText("");
        calendar.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/calendar-30.png")));
        calendar.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        dp_from_date.setBounds(105, 25, 35,30);
        NgayLap.add(dp_from_date);
        dp_from_date.addDateChangeListener((dce) -> {
            from_date.setText(dp_from_date.getDateStringOrEmptyString());
        });

        to_date =new JTextField();
        to_date.setBorder(new TitledBorder("to"));
        to_date.setBounds(140, 20, 100, 40);
        to_date.setEditable(false);
        NgayLap.add(to_date);
        addDocumentListener(to_date);

        // khoang ngay
        DatePickerSettings pickerSettings2 = new DatePickerSettings();
        pickerSettings2.setVisibleDateTextField(false);
        dp_to_date = new DatePicker(pickerSettings2);
        dp_to_date.setDateToToday();
        // calendar icon
        JButton calendar2= dp_to_date.getComponentToggleCalendarButton();
        calendar2.setText("");
        calendar2.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/calendar-30.png")));
        calendar2.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        dp_to_date.setBounds(240, 25, 35,30);
        NgayLap.add(dp_to_date);
        dp_to_date.addDateChangeListener((dce) -> {
            to_date.setText(dp_to_date.getDateStringOrEmptyString());
        });
        NgayLap.setBounds(570, 0, 280, 70);
        TimKiem.add(NgayLap);
        from_date.setEditable(false);
        to_date.setEditable(false);
        
        ExportExcel = new JLabel("");
        ExportExcel.setLayout(new FlowLayout());
        ExportExcel.setBorder(new TitledBorder("Export Excel"));
        XuatExcel=new JButton("Export Excel");
        XuatExcel.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xls-30.png")));
        XuatExcel.setFont(new Font("Segoe UI", 0, 14));
        XuatExcel.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        XuatExcel.setBackground(Color.decode("#90CAF9"));
        XuatExcel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                XuatExcel_click();
            }
        });
        textexport=new JTextField();
        textexport.setBorder(new TitledBorder("Date-time"));
        textexport.setPreferredSize(new Dimension(100,40));
        textexport.setEditable(false);
        cbExport=new JComboBox(new String[]{"Date-time","Product Order","Product ID"});
        
        // khoang ngay
        DatePickerSettings pickerSettings3 = new DatePickerSettings();
        pickerSettings3.setVisibleDateTextField(false);
        dp_export = new DatePicker(pickerSettings3);
        dp_export.setDateToToday();
        // calendar icon
        JButton calendar3= dp_export.getComponentToggleCalendarButton();
        calendar3.setText("");
        calendar3.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/calendar-30.png")));
        calendar3.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        dp_export.addDateChangeListener((dce) -> {
            textexport.setText(dp_export.getDateStringOrEmptyString());
        });
        btn_ProductID=new JButton();
        btn_ProductID.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xemchitiet-30.png")));
        btn_ProductID.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        btn_ProductID.addActionListener((ae) -> {
            FormChon formchon=null;
            try {
                formchon = new FormChon("Danh sách Product",textexport);
            } catch (Exception ex) {
                
            }
            formchon.setVisible(true);
        });
        btn_ProductID.setEnabled(false);
        cbExport.addActionListener((ActionEvent e) -> {
            textexport.setText("");
            String item=cbExport.getSelectedItem().toString();
            textexport.setBorder(BorderFactory.createTitledBorder(item));
            if(item.equals("Date-time"))
            {
                dp_export.setEnabled(true);
                btn_ProductID.setEnabled(false);
                return;
            }
            if(item.equals("Product Order")){
                dp_export.setEnabled(false);
                btn_ProductID.setEnabled(false);
            }
            else
            {
                dp_export.setEnabled(false);
                btn_ProductID.setEnabled(true);
            }
            textexport.requestFocus();
        });
        ExportExcel.add(cbExport);
        ExportExcel.add(textexport);
        ExportExcel.add(dp_export);
        ExportExcel.add(btn_ProductID);
        ExportExcel.add(XuatExcel);
        ExportExcel.setBounds(870, 0, 430, 70);
        TimKiem.add(ExportExcel);
        
        return TimKiem;
    }

    @Override
    protected void docDB() {
            try {
                BUS.docDB();
            } catch (Exception ex) {
                Logger.getLogger(GUIOrderProduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        table.setHeaders(header);
        //Chỉ hiện những món ăn ở trạng thái hiện , trạng thái ẩn là khi xóa
        for (OrderProductDTO DTO : OrderProductBUS.ds) {
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

//    //Hàm tìm kiếm mỗi khi thao tác trên field
    public void txtSearchOnChange() {
        //Đẩy dữ liệu đi và nhận lại danh sách đúng với field tìm kiếm
        ArrayList<OrderProductDTO> result = new ArrayList<>();
        OrderProductBUS.ds.forEach((OrderProductDTO) -> {
                if ( OrderProductDTO.getOrder_id().toLowerCase().contains(id.getText().toLowerCase()) ) //Tìm kiếm theo chuỗi thường
                {
                    result.add(OrderProductDTO);
                }
            });
        for (int i = result.size() - 1; i >= 0; i--)
        {
            OrderProductDTO DTO = result.get(i);
            LocalDate order_date = DTO.getOrder_date();
            Boolean ngayNhapKhongThoa = (!(from_date.getText()).equals("") && order_date.isBefore(LocalDate.parse(from_date.getText())))
                    || (!(to_date.getText()).equals("") && order_date.isAfter(LocalDate.parse(to_date.getText())));
            if(ngayNhapKhongThoa)
            {
                result.remove(i);
            }

        }
        table.clear();
        for(OrderProductDTO DTO:result)
            table.addRow(DTO);
    }

    protected void XuatExcel_click() {
        String item=cbExport.getSelectedItem().toString();
        if(textexport.getText().isBlank())
        {
            JOptionPane.showMessageDialog(null, "Không được để trống ô "+item);
            return;
        }
        if(item.equals("Date-time"))
        {
            ArrayList<OrderProductDTO> array=new ArrayList<>();
            for(OrderProductDTO DTO: OrderProductBUS.ds)
            {
                if(DTO.getOrder_date().isEqual(LocalDate.parse(textexport.getText())))
                    array.add(DTO);
            }
            if(array.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Không có OrderProduct nào phù hợp!");
                return;
            }
            XuatExcel Excel=new XuatExcel();
            Excel.xuatDatetime(array);
            return;
        }
        if(item.equals("Product Order"))
        {
            OrderProductDTO object=new OrderProductDTO();
            for(OrderProductDTO DTO: OrderProductBUS.ds)
            {
                if(DTO.getOrder_id().equals(textexport.getText())){
                    object.setOrder_id(DTO.getOrder_id());
                    object.setOrder_date(DTO.getOrder_date());
                    object.setStatus(DTO.getStatus());
                }
                    
            }
            XuatExcel Excel=new XuatExcel();
            Excel.xuatProductOrder(object);
            return;
        }
        if(item.equals("Product ID"))
        {
            ArrayList<OrderProductDTO> array=new ArrayList<>();
            for(OrderDetailDTO DTO: OrderDetailBUS.ds)
            {
                if(DTO.getProduct_id().equals(textexport.getText()))
                {
                    for(OrderProductDTO DTO2: OrderProductBUS.ds)
                    {
                        if(DTO2.getOrder_id().equals(DTO.getOrder_id())){
                            array.add(DTO2);
                        }
                    }
                }
            }
            XuatExcel Excel=new XuatExcel();
            Excel.xuatDatetime(array);
        }
    }

    @Override
    protected void ChiTiet_click(){
        int i=table.tb.getSelectedRow();
        FormChon formchon=null;
        if (i == -1) {
            op.showMessageDialog(null, "Vui lòng chọn 1 hóa đơn");
            return;
        } 
        String MaHoaDon=String.valueOf(table.tbModel.getValueAt(i,0));
        try {
            formchon = new FormChon("Chi tiết hóa đơn",MaHoaDon);
        } catch (Exception ex) {
        }
        if(formchon!=null)
            formchon.setVisible(true);
    }
    @Override
    protected void LamMoi_click(){
        super.LamMoi_click();
        id.setText("");
        from_date.setText("");
        to_date.setText("");
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
}
