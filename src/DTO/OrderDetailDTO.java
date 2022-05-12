package DTO;

public class OrderDetailDTO {
    private String order_detail_id, order_id, product_id;
    private int product_quantity;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String order_product_id, String order_id, String product_id, int order_quantity) {
        this.order_detail_id = order_product_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.product_quantity = order_quantity;
    }

    public String getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(String order_product_id) {
        this.order_detail_id = order_product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

}
