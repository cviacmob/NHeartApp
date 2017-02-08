package com.cviac.nheart.nheartapp.datamodel;

/**
 * Created by admin1 on 12/16/2016.
 */

public class ProductCartInfo {
    private String cart_id;
    private String product_id;
    private String name;
    private String model;
    private boolean stock;
    private String quantity;
    private String shipping;
    private String price;
    private String image;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public ProductCartInfo() {
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    private String total;
    private int reward;



}
