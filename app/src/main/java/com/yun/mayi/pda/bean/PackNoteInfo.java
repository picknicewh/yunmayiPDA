package com.yun.mayi.pda.bean;

/**
 * 作者： wh
 * 时间：  2018/4/9
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackNoteInfo {

    /**
     * product_id : 534
     * product_title : 测试商品商品
     * product_sub_title :
     * product_bar_code :
     * product_spec : 45
     * product_unit :
     * product_cost_price : 1000
     * quantity : 3
     * total_cost_price : 3000
     * out_of_stock_num : 0
     * real_quantity : 3
     */

    private int product_id;
    private String product_title;
    private String product_sub_title;
    private String product_bar_code;
    private String product_spec;
    private String product_unit;
    private int product_cost_price;
    private int quantity;
    private int total_cost_price;
    private int out_of_stock_num;
    private int real_quantity;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_sub_title() {
        return product_sub_title;
    }

    public void setProduct_sub_title(String product_sub_title) {
        this.product_sub_title = product_sub_title;
    }

    public String getProduct_bar_code() {
        return product_bar_code;
    }

    public void setProduct_bar_code(String product_bar_code) {
        this.product_bar_code = product_bar_code;
    }

    public String getProduct_spec() {
        return product_spec;
    }

    public void setProduct_spec(String product_spec) {
        this.product_spec = product_spec;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public int getProduct_cost_price() {
        return product_cost_price;
    }

    public void setProduct_cost_price(int product_cost_price) {
        this.product_cost_price = product_cost_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal_cost_price() {
        return total_cost_price;
    }

    public void setTotal_cost_price(int total_cost_price) {
        this.total_cost_price = total_cost_price;
    }

    public int getOut_of_stock_num() {
        return out_of_stock_num;
    }

    public void setOut_of_stock_num(int out_of_stock_num) {
        this.out_of_stock_num = out_of_stock_num;
    }

    public int getReal_quantity() {
        return real_quantity;
    }

    public void setReal_quantity(int real_quantity) {
        this.real_quantity = real_quantity;
    }
}
