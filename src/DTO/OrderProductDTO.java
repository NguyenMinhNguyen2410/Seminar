package DTO;

import java.time.LocalDate;

public class OrderProductDTO {
    private String order_id;
    private LocalDate order_date;
    private int status;

    public OrderProductDTO() {
    }

    public OrderProductDTO(String order_id, LocalDate order_date, int status) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.status = status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
