package com.cviac.nheart.nheartapp.datamodel;

/**
 * Created by sai on 11/23/2016.
 */

public class Gift {

    private String name;
    private String desc;
    private long price;


    public long getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
