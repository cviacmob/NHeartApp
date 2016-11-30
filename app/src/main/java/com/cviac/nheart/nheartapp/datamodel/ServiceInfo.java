package com.cviac.nheart.nheartapp.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServiceInfo implements Serializable {

    private int imgID;

    private String serviceNAME;
    private String Desc;
    private String price;
    private String price1;

    List<ServiceInfo> sublist;

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }




    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ServiceInfo(int imgID, String serviceNAME, String Desc, String price,String price1) {
        this.imgID = imgID;
        this.serviceNAME = serviceNAME;
        this.Desc=Desc;
        this.price=price;
        this.price1=price1;
        sublist =new ArrayList<ServiceInfo>();
    }



    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public String getServiceNAME() {
        return serviceNAME;
    }

    public void setServiceNAME(String serviceNAME) {
        this.serviceNAME = serviceNAME;
    }

   public void add(ServiceInfo sinfo)
   {
       sublist.add(sinfo);
   }

    public List<ServiceInfo> getSublist()
    {
        return sublist;
    }
}
