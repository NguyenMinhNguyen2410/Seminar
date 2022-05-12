/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BUS.Tool;
import DAO.ConnectDB;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen
 */
//class mở đầu khi form đăng nhập được gọi , nơi sườn của cả bài
public class GUIMenu extends JFrame{

    //Tạo mảng menuleft cho form
    private String arr_listmenu[]={"Tag","Product","OrderProduct","ExportProduct"};
    //Tạo mảng icon cho menuleft
    private String arr_icon[]={"src/Images/Icon/sell1-30.png","src/Images/Icon/nhaphang-30.png","src/Images/Icon/monan-30.png",
            "src/Images/Icon/nguyenlieu-30.png"};
    //Tạo mảng nhãn cho menuleft
    private JLabel lbl_listmenu[]=new JLabel[arr_listmenu.length];
    //Tạo mảng bảng nhỏ để đặt các icon và nhãn của menuleft
    private JPanel pn_listmenu[]=new JPanel[arr_listmenu.length];
    //Tạo mảng panel chứa nội dung từng mục
    private JPanel pn_content[]=new JPanel[arr_listmenu.length];

    //https://stackoverflow.com/questions/3680221/how-can-i-get-screen-resolution-in-java
    //Lệnh này để lấy kích thước màn hình
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //Tạo biến width với 90% độ dài của độ phân giải màn hình
    public static int width = (int)(screenSize.getWidth()*90/100);
    //Tạo biến height với 90% độ rộng của độ phân giải màn hình
    public static int height = (int)(screenSize.getHeight()*90/100);
    //Tạo biến width_menu ,là chiều dài của menuleft , chiếm 15%
    public static int width_menu=width*15/100;
    //Tạo biến width_content ,là chiều dài của phần nội dung , chiếm 85%
    public static int width_content=width*85/100;
    //Tạo nhãn tiêu đề
    private JLabel title;
    //Tạo Panel chứa menu
    private JPanel menu;
    public GUIMenu() throws Exception{
        initcomponent();
    }
    //Tạo kích thước và hình dáng của form
    public void initcomponent() throws Exception{

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(255, 255, 255));
        setLayout(new BorderLayout());
        //làm mất thanh chức năng mặc định của Frame
        //Chú ý : phải set lệnh này trước visible nếu không thì lệnh không chạy và gây lỗi
        setUndecorated(true);
        setVisible(true);
        //Panel chứa menuleft
        menu=menu();
        JScrollPane pane = new JScrollPane(menu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        menu.setAutoscrolls(true);
        menu.setPreferredSize(new Dimension(width_menu,900));
        pane.setPreferredSize(new Dimension(width_menu, height));
        pane.setBorder(BorderFactory.createEmptyBorder());
        // setUnitIncrement giúp thanh cuộn mượt mà hơn , số càng nhỏ càng mượt ( chậm )
        pane.getVerticalScrollBar().setUnitIncrement(5);
        add(pane,BorderLayout.WEST);

        //Panel này xem như thanh công cụ , thay thế thanh công cụ mặc định của java
        JPanel header=header();
        header.setPreferredSize(new Dimension(0, 30));
        add(header,BorderLayout.NORTH);
        //Panel chứa phần nội dung của mỗi mục trong menuleft
        JPanel content=content();
        content.setPreferredSize(new Dimension(width_content,0));
        add(content,BorderLayout.CENTER);


        setSize(width,height);
    }
    private JPanel menu(){
//        JPanel menu=new JPanel(new GridLayout(14, 1));
        JPanel menu=new JPanel(null);

        JLabel logo=new JLabel(Tool.showIcon(width_menu, 200, "src/Images/Icon/Logo-Design-removebg-preview.png"));
        logo.setBounds(0, 0, width_menu, 200);
        //Sự kiện khi ấn vào logo thì hiện tên
        logo.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                title.setText("QUẢN LÝ XUÁT KHO");
            }
        });
        menu.add(logo);
        menu.setBackground(Color.decode("#64B5F6"));
        int y=200;
        //Hàm tạo tự động các nhãn trong thanh menuleft
        for(int i=0;i<lbl_listmenu.length;i++)
        {
            lbl_listmenu[i]=new JLabel(arr_listmenu[i], Tool.showIcon(30, 30, arr_icon[i]),SwingConstants.LEADING);// thêm icon ráng kiếm
            lbl_listmenu[i].setBounds(20, 0,width_menu,50);
            lbl_listmenu[i].setFont(new Font("Segoe UI", 0, 18));
            //Đổi màu chữ thành trắng
            lbl_listmenu[i].setForeground(Color.white);
            pn_listmenu[i]=new JPanel(null);
            pn_listmenu[i].setBackground(Color.decode("#64B5F6")); // mới sửa

            pn_listmenu[i].addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                //Sự kiện khi click vào 1 mục bất kỳ thì sẽ hiện tiêu đề của mục ở thanh header
                public void mousePressed(java.awt.event.MouseEvent evt)
                {
                    for(int i=0;i<pn_listmenu.length;i++)
                    {
                        if(evt.getSource()==pn_listmenu[i])
                        {

                            pn_content[i].setVisible(true);
                            String s="BẢNG ";

                            s+=arr_listmenu[i].toUpperCase();
                            title.setText(s);
                            continue;
                        }

                        pn_content[i].setVisible(false);
                    }
                }
                @Override
                //Sự kiện khi kéo chuột đến đâu thì làm nổi bật ở mục đó lên
                public void mouseEntered(java.awt.event.MouseEvent evt)
                {
                    for(int i=0;i<pn_listmenu.length;i++)
                    {
                        if(evt.getSource()==pn_listmenu[i])
                        {
                            pn_listmenu[i].setBackground(Color.decode("#2962FF"));   //mới sửa
                            continue;
                        }
                        pn_listmenu[i].setBackground(Color.decode("#64B5F6"));     //mới sửa
                    }
                }
                @Override
                //Sự kiện khi kéo chuột đi khỏi mục đó thì trở lại như cũ
                public void mouseExited(java.awt.event.MouseEvent evt)
                {
                    for(int i=0;i<pn_listmenu.length;i++)
                    {
                        if(evt.getSource()==pn_listmenu[i])
                        {
                            pn_listmenu[i].setBackground(Color.decode("#64B5F6"));   //mới sửa
                            return;
                        }

                    }
                }
            });

            pn_listmenu[i].add(lbl_listmenu[i]);
            pn_listmenu[i].setBounds(0, y, width_menu, 50);
            menu.add(pn_listmenu[i]);
            y+=50;


        }

        return menu;
    }
    private JPanel content() throws Exception{
        JPanel trunggian=new JPanel(null);

        //Tạo thanh menu tự động , gồm các button tạo các bảng
        //Tạo đè tất cả các Panel lên 1 Panel trung gian , sử dụng cơ chế setVisible true fasle để xem từng bảng, sự kiện đã được thêm ở phía trên
        for(int i=0;i<arr_listmenu.length;i++)
        {
            //Với mỗi i chỉ tạo 1 panel
            switch(i){
                case 0:
                    GUITag Tag=new GUITag();
                    pn_content[i]=Tag;    break;
                case 1:
                    GUIProduct Product=new GUIProduct();
                    pn_content[i]=Product;    break;
                case 2:
                    GUIOrderProduct OrderProduct=new GUIOrderProduct();
                    pn_content[i]=OrderProduct;    break;
                case 3:
                    GUIExportProduct ExportProduct=new GUIExportProduct();
                    pn_content[i]=ExportProduct;    break;
            }

            pn_content[i].setVisible(false);
            pn_content[i].setBounds(0, 0, width_content, height*95/100);
            trunggian.add(pn_content[i]);
        }
        return trunggian;

    }
    private JPanel header(){
        JPanel header=new JPanel(null);
        header.setBackground(Color.black);

        title=new JLabel("QUẢN LÝ XUÁT KHO");
        title.setBounds(width*50/100, 0, 300, 30);
        title.setFont(new Font("Segoe UI", 0, 18));
        title.setForeground(Color.white);
        header.add(title);
        //Tạo nút lặn frame xuống thanh taskbar
        JLabel minimize=new JLabel();
        minimize.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/icons8_angle_down_30px.png")));
        minimize.setBounds(width*94/100, 0, 30, 30);
        minimize.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                setState(JFrame.ICONIFIED);
            }
        });
        header.add(minimize);
        //Tạo nút thoát
        JLabel exit=new JLabel();
        exit.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/icons8_shutdown_30px_1.png")));
        exit.setBounds(width*97/100, 0, 30, 30);
        exit.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                ConnectDB connect=new ConnectDB();
                try {
                    connect.closeConnect();
                    System.out.println("Closing connection");
                } catch (SQLException ex) {
                    Logger.getLogger(GUIMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
        });
        header.add(exit);

        return header;
    }
    //Hàm dùng để lọc lại thanh menuleft nếu quyền chưa đủ
    public boolean timKiemMotQuyenTrongMoTaQuyen(String moTaQuyen, String motQuyen)
    {
        if(moTaQuyen.indexOf(motQuyen) != -1)
        {
            return true;
        }
        return false;
    }
}
