package com.cviac.nheart.nheartapp.datamodel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 1/3/2017.
 */

public class HugInfo implements Serializable{

    private int imgUrl;



    String mob;

    private String description;

    private String date;

    private String title;



    public HugInfo(int imgUrl,String mob, String description, String date, String title) {
        this.imgUrl = imgUrl;
        this.mob=mob;
        this.description = description;
        this.date = date;
        this.title = title;
    }


    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
