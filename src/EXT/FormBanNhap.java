/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EXT;

import GUI.GUIMenu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Nguyen
 */
//Class này tạo bố cục sẵn cho form bán hàng và nhập hàng
public class FormBanNhap extends JPanel{
    //Tạo các panel
    private JPanel pDanhSach,pXuatHang,pXacNhan,pOrderProduct;
    protected JOptionPane op;
    public FormBanNhap(){
        initcomponent();
    }
    public void initcomponent(){
        setLayout(new GridLayout(1,2));
        JPanel left=new JPanel(new GridLayout(2,1));
        JPanel right=new JPanel(new GridLayout(2,1));
        //Dùng để chứa Panel có danh sách món ăn , nguyên liệu
        pDanhSach=panelDanhSach();
        left.add(pDanhSach);
        //Dùng để chứa Panel có thông tin hóa đơn , hóa đơn nhập 
        pXuatHang=panelXuatHang();
        left.add(pXuatHang);
        pOrderProduct=panelOrderProduct();
        right.add(pOrderProduct);
        //Dùng để chứa các món ăn đã order hoặc các nguyên liệu được yêu cầu
        pXacNhan=panelXacNhan();
        right.add(pXacNhan);
        
        add(left);
        add(right);
        setVisible(true);
    }
    protected JPanel panelDanhSach(){
        JPanel panel=new JPanel();
        
        return panel;
    }
    protected JPanel panelXuatHang(){
        JPanel panel=new JPanel();
        
        return panel;
    }
    protected JPanel panelXacNhan(){
        JPanel panel=new JPanel();
        
        return panel;
    }
    protected JPanel panelOrderProduct(){
        JPanel panel=new JPanel();
        
        return panel;
    }
    public JOptionPane getOp() {
        return op;
    }

    public void setOp(JOptionPane op) {
        this.op = op;
    }

//    public FormChon getFormchon() {
//        return formchon;
//    }
    
}






