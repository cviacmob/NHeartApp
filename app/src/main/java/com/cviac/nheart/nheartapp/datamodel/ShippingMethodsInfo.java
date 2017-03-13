package com.cviac.nheart.nheartapp.datamodel;

/**
 * Created by admin1 on 3/13/2017.
 */

class ShippingMethodsInfo {
    private String code;
    private String title;
    private String cost;
    private String tax_class_id;
    private String text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTax_class_id() {
        return tax_class_id;
    }

    public void setTax_class_id(String tax_class_id) {
        this.tax_class_id = tax_class_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
