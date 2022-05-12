/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EXT;

import BUS.OrderDetailBUS;
import BUS.ProductBUS;
import DTO.OrderDetailDTO;
import DTO.ProductDTO;
import GUI.GUIMenu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author Nguyen
 */
//Class này kế thừa JDialog , dùng để hiện danh sách các thực thể khi cần mã của chúng nhưng người dùng không nhớ hoặc để hiện chi tiết với các mối quan hệ 1-n
public class FormChon extends JDialog{
    //Panel chứa tiêu đề và nút
    private JPanel Title,Button;
    private MyTable Table;
    //Tạo cờ hiệu cho việc các Dialog có được tắt đúng cách hay không
    private int cohieu = 0;
    //Hàm khởi tạo với việc lấy mã thực thể trong danh sách
    private JButton Chon,Thoat;
    //Hàm khởi tạo với việc hiện danh sách các chi tiết 
    public FormChon(String a,String i) throws Exception{
        setLayout(new BorderLayout());
        setSize(800, 500);
        //https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        Title=pnTitle(a);
        Title.setPreferredSize(new Dimension(0,100));
        add(Title,BorderLayout.NORTH);

        pnTable(a,i);
        Table.setPreferredSize(new Dimension(0,350));
        add(Table,BorderLayout.CENTER);
        
        
        Button=pnButton();
        Button.setPreferredSize(new Dimension(0,50));
        add(Button,BorderLayout.SOUTH);
        //Rào lại với mục đích không gây chồng cửa sổ 
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                if (cohieu == 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng tắt Dialog khi muốn làm thao tác khác");
                }
            }

        });
//        setVisible(true);
    }
    
    public FormChon(String a,JTextField txt) throws Exception{
        setLayout(new BorderLayout());
        setSize(800, 500);
        //https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        Title=pnTitle(a);
        Title.setPreferredSize(new Dimension(0,100));
        add(Title,BorderLayout.NORTH);

        pnTable(a);
        Table.setPreferredSize(new Dimension(0,350));
        add(Table,BorderLayout.CENTER);
        
        
        Button=pnButton(txt);
        Button.setPreferredSize(new Dimension(0,50));
        add(Button,BorderLayout.SOUTH);
        //Rào lại với mục đích không gây chồng cửa sổ 
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                if (cohieu == 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng tắt Dialog khi muốn làm thao tác khác");
                }
            }

        });
//        setVisible(true);
    }
    //Hàm tạo tiêu đề
    public JPanel pnTitle(String a){
        JPanel panel=new JPanel(null);
        JLabel lbTitle = new JLabel(a);
        lbTitle.setFont(new Font("Time New Roman", Font.BOLD, 21));
        lbTitle.setForeground(Color.decode("#FF4081"));
        lbTitle.setBounds(350, 0, 200, 40);
        panel.add(lbTitle);
        
        //add thêm thanh tìm kiếm
        return panel;
    }
    //Hàm rẻ nhánh khi tạo danh sách chi tiết
    public void pnTable(String a,String i) throws Exception{
        Table=new MyTable(); 
        if(a.equals("Chi tiết hóa đơn"))
        {
            docDBChiTietHoaDon(i);
            //Dùng để rào lại việc không có dữ liệu cần
            if(Table.tbModel.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "Hóa đơn này không có chi tiết");
                setVisible(false);
            }
            else
                setVisible(true);
        }
       
    }
    public void pnTable(String a) throws Exception{
        Table=new MyTable(); 
        if(a.equals("Danh sách Product"))
        {
            Table.setHeaders(new String[]{"product_id", "product_name", "product_quantity", "product_detail"});
            for(ProductDTO DTO:ProductBUS.ds)
                Table.addRow(DTO);
            setVisible(true);
        }
       
    }
    //Hàm tạo nút khi chỉ xem các danh sách chi tiết
    public JPanel pnButton(){
        JPanel panel=new JPanel(null);

        Thoat = new JButton("Thoát");
        Thoat.setBackground(Color.decode("#90CAF9"));
        Thoat.setBounds(350, 0, 100, 50);
        Thoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //Tắt cờ hiệu đi 
                cohieu = 1;
                dispose();
            }
        });
        panel.add(Thoat);
        return panel;
    }
    //Hàm tạo nút khi có thể chọn trong danh sách
    public JPanel pnButton(JTextField txt){
        JPanel panel=new JPanel(null);
        Chon = new JButton("Chọn");
        Chon.setBackground(Color.decode("#90CAF9"));
        Chon.setBounds(300, 0, 100, 50);
        Chon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) { 
                int i = Table.tb.getSelectedRow();
                if (i == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 hàng");
                } else {
                    txt.setText(String.valueOf(Table.tbModel.getValueAt(i,0)));
                    //Tắt cờ hiệu đi 
                    cohieu = 1;
                    dispose();
                }
            }
        });
        panel.add(Chon);

        JButton Thoat = new JButton("Thoát");
        Thoat.setBackground(Color.decode("#90CAF9"));
        Thoat.setBounds(450, 0, 100, 50);
        Thoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //Tắt cờ hiệu đi 
                cohieu = 1;
                dispose();
            }
        });
        panel.add(Thoat);
        return panel;
    }
    //Load dữ liệu chi tiết hóa đơn lên table
    public void docDBChiTietHoaDon(String i) throws Exception {
        Table.setHeaders(new String[]{"order_detail_id","order_id","product_id","order_quantity"});
        for (OrderDetailDTO DTO : OrderDetailBUS.ds) {
            if (DTO.getOrder_id().equals(i)) {
                Table.addRow(DTO);                    
            }
        }
        Table.pane.setPreferredSize(new Dimension(GUIMenu.width_content*90/100, 300));
    }
    public MyTable getTable() {
        return Table;
    }

    public JButton getChon() {
        return Chon;
    }

    public JButton getThoat() {
        return Thoat;
    }
    
}


























