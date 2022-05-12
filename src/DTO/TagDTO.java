package DTO;

import java.time.LocalDate;

public class TagDTO {
    private String tag_id,product_id, tag_gate_in, tag_gate_out;
    private LocalDate tag_date_in, tag_date_out;

    public TagDTO() {
    }

    public TagDTO(String tag_id, String product_id, String tag_gate_in, String tag_gate_out, LocalDate tag_date_in, LocalDate tag_date_out) {
        this.tag_id = tag_id;
        this.product_id = product_id;
        this.tag_gate_in = tag_gate_in;
        this.tag_gate_out = tag_gate_out;
        this.tag_date_in = tag_date_in;
        this.tag_date_out = tag_date_out;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTag_gate_in() {
        return tag_gate_in;
    }

    public void setTag_gate_in(String tag_gate_in) {
        this.tag_gate_in = tag_gate_in;
    }

    public String getTag_gate_out() {
        return tag_gate_out;
    }

    public void setTag_gate_out(String tag_gate_out) {
        this.tag_gate_out = tag_gate_out;
    }

    public LocalDate getTag_date_in() {
        return tag_date_in;
    }

    public void setTag_date_in(LocalDate tag_date_in) {
        this.tag_date_in = tag_date_in;
    }

    public LocalDate getTag_date_out() {
        return tag_date_out;
    }

    public void setTag_date_out(LocalDate tag_date_out) {
        this.tag_date_out = tag_date_out;
    }
}
