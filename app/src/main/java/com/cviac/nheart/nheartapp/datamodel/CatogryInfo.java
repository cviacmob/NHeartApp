package com.cviac.nheart.nheartapp.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 11/28/2016.
 */

public class CatogryInfo implements Serializable{

    private String catogryname;
    private int catogryimg;
    private int catogryimg1;


    public CatogryInfo(String catogryname, int catogryimg, int catogryimg1) {
        this.catogryname = catogryname;
        this.catogryimg = catogryimg;
        this.catogryimg1 = catogryimg1;
    }






    public String getCatogryname() {
        return catogryname;
    }

    public void setCatogryname(String catogryname) {
        this.catogryname = catogryname;
    }

    public int getCatogryimg() {
        return catogryimg;
    }

    public void setCatogryimg(int catogryimg) {
        this.catogryimg = catogryimg;
    }

    public int getCatogryimg1() {
        return catogryimg1;
    }

    public void setCatogryimg1(int catogryimg1) {
        this.catogryimg1 = catogryimg1;
    }




}
    /*private int imgID1,img4;

    private String CatogryName;

    List<CatogryInfo> sublist;


    public List<CatogryInfo> getSublist() {
        return sublist;
    }

    public void setSublist(List<CatogryInfo> sublist) {
        this.sublist = sublist;
    }

    public CatogryInfo(int imgID1, String catogryName, int img4) {
        this.imgID1 = imgID1;
        this.img4 = img4;
        CatogryName = catogryName;

        sublist =new ArrayList<CatogryInfo>();
    }

    public int getImgID1() {
        return imgID1;
    }

    public void setImgID1(int imgID1) {
        this.imgID1 = imgID1;
    }

    public String getCatogryName() {
        return CatogryName;
    }
    public int getImg4() {
        return img4;
    }

    public void setImg4(int img4) {
        this.img4 = img4;
    }

    public void setCatogryName(String catogryName) {
        CatogryName = catogryName;
    }
    *//*public void add(CatogryInfo sinfo)
    {
        sublist.add(sinfo);
    }
    public List<CatogryInfo> getSublist()
    {
        return sublist;
    }*//*

}
*/