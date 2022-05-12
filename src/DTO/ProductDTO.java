package DTO;

public class ProductDTO {
    private String product_id,product_name,product_detail;
    private int product_quantity;

    public ProductDTO() {
    }

    public ProductDTO(String product_id, String product_name, String product_detail, int product_quantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_detail = product_detail;
        this.product_quantity = product_quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_detail() {
        return product_detail;
    }

    public void setProduct_detail(String product_detail) {
        this.product_detail = product_detail;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }
}
