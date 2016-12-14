package com.cviac.nheart.nheartapp.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin1 on 12/9/2016.
 */

public class Addcartinfo {
    private int imgID;

    private String name;
    //private String Desc;
    private String price;
    private String quantity;

    List<Addcartinfo> sublist;
    public boolean selected;
//
//    public String getDesc() {
//        return Desc;
//    }
//
//    public void setDesc(String desc) {
//        Desc = desc;
//    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String price1) {
        this.quantity = quantity;
    }




    public String getPrice() {
        return price;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Addcartinfo(int imgID, String name, String price, String quantity) {
        this.imgID = imgID;
        this.name = name;
//        this.Desc=Desc;
        this.price=price;
        this.quantity=quantity;
        sublist =new ArrayList<Addcartinfo>();
    }





    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public String getName() {
        return name;
    }

    public void setName(String serviceNAME) {
        this.name = name;
    }

    public void add(Addcartinfo sinfo)
    {
        sublist.add(sinfo);
    }

    public List<Addcartinfo> getSublist()
    {
        return sublist;
    }


}
