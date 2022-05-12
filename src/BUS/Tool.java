package BUS;

import DTO.ProductDTO;
import DTO.TagDTO;
import RFID.ReadTags;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tool {
    public static boolean docDB() throws Exception
    {
        try
        {
            OrderDetailBUS.docDB();
            OrderProductBUS.docDB();
            ProductBUS.docDB();
            TagBUS.docDB();
            ReadTags.ReadTags();
        }catch(NullPointerException e){
            return false;
        }
        return true;
    }
    public static ImageIcon showIcon(int width, int height, String fileName) {
        ImageIcon icon;
        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            icon = new ImageIcon(image.getScaledInstance(width, height, image.SCALE_SMOOTH));
            return icon;
        } catch (IOException e) {
            return null;
        }
    }
    public static ArrayList<TagDTO> searchTag(String searchtext,String type, String from_in, String to_in, String from_out, String to_out) { //có thay đổi
        // phương pháp tìm từ arraylist
        ArrayList<TagDTO> result = new ArrayList<>();
        if(type.equals("tag_id"))
        {
            //duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            TagBUS.ds.forEach((TagDTO) -> {
                if ( TagDTO.getTag_id().toLowerCase().contains(searchtext.toLowerCase()) ) //Tìm kiếm theo chuỗi thường
                {
                    result.add(TagDTO);
                }
            });
        }
        if(type.equals("product_id"))
        {
            //duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            TagBUS.ds.forEach((TagDTO) -> {
                if ( TagDTO.getProduct_id().toLowerCase().contains(searchtext.toLowerCase()) ) //Tìm kiếm theo chuỗi thường
                {
                    result.add(TagDTO);
                }
            });
        }
        if(type.equals("tag_gate_in"))
        {
            //duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            TagBUS.ds.forEach((TagDTO) -> {
                if ( TagDTO.getTag_gate_in().toLowerCase().contains(searchtext.toLowerCase()) ) //Tìm kiếm theo chuỗi thường
                {
                    result.add(TagDTO);
                }
            });
        }
        if(type.equals("tag_gate_out"))
        {
            //duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            TagBUS.ds.forEach((TagDTO) -> {
                if ( TagDTO.getTag_gate_out().toLowerCase().contains(searchtext.toLowerCase())) //Tìm kiếm theo chuỗi thường
                {
                    result.add(TagDTO);
                }
            });
        }

        for (int i = result.size() - 1; i >= 0; i--)
        {
            TagDTO Tag = result.get(i);
            LocalDate tag_date_in = Tag.getTag_date_in();
            Boolean ngayNhapKhongThoa = (!(from_in).equals("") && tag_date_in.isBefore(LocalDate.parse(from_in)))
                    || (!(to_in).equals("") && tag_date_in.isAfter(LocalDate.parse(to_in)));
            if(ngayNhapKhongThoa)
            {
                result.remove(i);
            }

        }

        for (int i = result.size() - 1; i >= 0; i--)
        {
            TagDTO Tag = result.get(i);
            LocalDate tag_date_out = Tag.getTag_date_out();
            Boolean ngayNhapKhongThoa = (!(from_out).equals("") && tag_date_out.isBefore(LocalDate.parse(from_out)))
                    || (!(to_out).equals("") && tag_date_out.isAfter(LocalDate.parse(to_out)));
            if(ngayNhapKhongThoa)
            {
                result.remove(i);
            }

        }
        return result;
    }
    public static ArrayList<ProductDTO> searchProduct(String searchtext,String type, int from_quantity, int to_quantity) { //có thay đổi
        // phương pháp tìm từ arraylist
        ArrayList<ProductDTO> result = new ArrayList<>();
        if(type.equals("product_id"))
        {
            //duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            ProductBUS.ds.forEach((ProductDTO) -> {
                if ( ProductDTO.getProduct_id().toLowerCase().contains(searchtext.toLowerCase()) ) //Tìm kiếm theo chuỗi thường
                {
                    result.add(ProductDTO);
                }
            });
        }
        if(type.equals("product_name"))
        {
            //duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            ProductBUS.ds.forEach((ProductDTO) -> {
                if ( ProductDTO.getProduct_name().toLowerCase().contains(searchtext.toLowerCase()) ) //Tìm kiếm theo chuỗi thường
                {
                    result.add(ProductDTO);
                }
            });
        }
        if(type.equals("product_detail"))
        {
            //duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            ProductBUS.ds.forEach((ProductDTO) -> {
                if ( ProductDTO.getProduct_detail().toLowerCase().contains(searchtext.toLowerCase()) ) //Tìm kiếm theo chuỗi thường
                {
                    result.add(ProductDTO);
                }
            });
        }
        for (int i = result.size() - 1; i >= 0; i--)
        {
            ProductDTO DTO = result.get(i);
            int quantity = DTO.getProduct_quantity();
            Boolean soLuongKhongThoa = (from_quantity != -1 && quantity < from_quantity ) || (to_quantity != -1 && quantity > to_quantity );
            if ( soLuongKhongThoa)
            {
                result.remove(i);
            }
        }

        return result;
    }
    public static boolean isNumber(String number) { //kt có phải là số không
        try {
            if (Integer.parseInt(number) < 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isSpecialChar(String specialChar) {  //kiểm tra có chứa ký tự đặc biệt k
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9 ]*$");
        Matcher matcher = pattern.matcher(specialChar);
        if (matcher.find()) {
            return true;
        }
        return false;

    }
    public static boolean isLength50(String ten)
    {
        if(ten.length() > 50 || ten.length() < 1)
        {
            return false;
        }
        return true;
    }
    private static final char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
            'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
            'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
            'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
            'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
            'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
            'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
            'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
            'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
            'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
            'ữ', 'Ự', 'ự',};

    private static final char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E',
            'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
            'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
            'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
            'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u', 'U', 'u',};

    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS[index];
        }
        return ch;
    }

    public static String removeAccent(String str) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }
}
